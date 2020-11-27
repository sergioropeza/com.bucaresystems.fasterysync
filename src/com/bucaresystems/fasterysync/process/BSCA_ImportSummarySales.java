package com.bucaresystems.fasterysync.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.compiere.db.Database;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MDocType;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MOrg;
import org.compiere.model.MPriceList;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPricing;
import org.compiere.model.MTable;
import org.compiere.model.MTax;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.model.X_C_POSPayment;
import org.compiere.model.X_C_POSTenderType;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Trx;

import com.bucaresystems.fasterysync.base.CustomProcess;
import com.bucaresystems.fasterysync.model.X_BSCA_POSDetaill;
import com.bucaresystems.fasterysync.model.X_BSCA_POSTaxDetaill;
import com.bucaresystems.fasterysync.pos.model.BSCA_ClosedCash;
import com.bucaresystems.fasterysync.pos.model.BSCA_Tickets;

public class BSCA_ImportSummarySales extends CustomProcess{

	protected int C_DocTypeTarget_ID;
	protected int C_BPartner_ID;
//	protected int M_Warehouse_ID;
	protected int M_PriceList_ID;
	protected int C_PaymentTerm_ID;
	protected int C_BP_Group_ID;
	protected int M_PriceListBPartner_ID;
	protected String trxName;
	protected int AD_OrgBPartner_ID;
	protected int C_DocTypeLot_ID;
	protected String PaymentRule;
	protected Integer p_M_Product_ID;
	protected int C_DocTypeTargetNC_ID;
	protected Trx trx;
	protected int C_Charge_ID;
	protected boolean isDateStellar;
	protected int AD_OrgOrder_ID;
	protected int p_C_BPartnerDefault_ID;
	protected int p_C_TaxCategory_ID;
	protected boolean isQtyNegate;
	protected int p_C_BankAccount_ID;
	protected static String NATIVE_MARKER = "NATIVE_"+Database.DB_POSTGRESQL+"_KEYWORK";
	protected String LIMIT_1 = " "+NATIVE_MARKER + "LIMIT 1"+ NATIVE_MARKER;
	private int AD_Org_ID;
	private int M_Warehouse_ID;
	protected Integer p_LIMIT;
	
	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] para = getParameter();	
		for (int i = 0; i < para.length; i++) {
			if (para[i].getParameterName().equals("AD_OrgBPartner_ID")){
				AD_OrgBPartner_ID = para[i].getParameterAsInt();
			}else if (para[i].getParameterName().equals("C_DocTypeTarget_ID")){
				C_DocTypeTarget_ID = para[i].getParameterAsInt();
			}else if (para[i].getParameterName().equals("C_DocTypeTargetNC_ID")){
				C_DocTypeTargetNC_ID = para[i].getParameterAsInt();
			}else if (para[i].getParameterName().equals("M_Warehouse_ID")){
//				M_Warehouse_ID = para[i].getParameterAsInt();
			}else if (para[i].getParameterName().equals("M_PriceList_ID")){
				M_PriceList_ID = para[i].getParameterAsInt();
			}else if (para[i].getParameterName().equals("C_PaymentTerm_ID")){
				C_PaymentTerm_ID = para[i].getParameterAsInt();
			}else if (para[i].getParameterName().equals("C_BP_Group_ID")){
				C_BP_Group_ID = para[i].getParameterAsInt();
			}else if(para[i].getParameterName().equals("M_PriceListBPartner_ID")){
				M_PriceListBPartner_ID = para[i].getParameterAsInt();
			}else if(para[i].getParameterName().equals("C_DocTypeLot_ID")){
				C_DocTypeLot_ID = para[i].getParameterAsInt();
			}else if(para[i].getParameterName().equals("PaymentRule")){
				PaymentRule = para[i].getParameterAsString();
			}else if(para[i].getParameterName().equals("M_Product_ID")){
				p_M_Product_ID = para[i].getParameterAsInt();
			}else if(para[i].getParameterName().equals("C_Charge_ID")){
				C_Charge_ID = para[i].getParameterAsInt();
			}else if(para[i].getParameterName().equals("isDateStellar")){
				isDateStellar = para[i].getParameterAsBoolean();
			}else if(para[i].getParameterName().equals("AD_OrgOrder_ID")){
				AD_OrgOrder_ID = para[i].getParameterAsInt();
			}else if(para[i].getParameterName().equals("C_BPartnerDefault_ID")){
				p_C_BPartnerDefault_ID = para[i].getParameterAsInt();
			}else if(para[i].getParameterName().equals("isQtyNegate")){
				isQtyNegate = para[i].getParameterAsBoolean();
			}else if(para[i].getParameterName().equals("C_TaxCategory_ID")){
				p_C_TaxCategory_ID = para[i].getParameterAsInt();
			}else if(para[i].getParameterName().equals("C_BankAccount_ID")){
				p_C_BankAccount_ID = para[i].getParameterAsInt();
			}	
		}
	}

	@Override
	protected String doIt() throws Exception {
		return importSummaryOrders();
	
	}
	
	protected String importSummaryOrders() {
		if (trxName==null){
			trxName = get_TrxName();	
			trx = Trx.get(trxName, false);
		}
		String c_sucursal = (new MOrg(Env.getCtx(), AD_OrgOrder_ID, null)).getValue();
		UpdateBSCA_Route(c_sucursal);
		if (!validateSucursal(c_sucursal))
			return "";
		
		String whereBankAccount = "";
		if (p_C_BankAccount_ID!=0){
			 String value = DB.getSQLValueStringEx(trxName, "Select value from C_BankAccount where C_BankAccount_ID=? ", p_C_BankAccount_ID);
			 whereBankAccount = " and C_BankAccount.value = '"+value+"'";
		}
			
		List<PO> lstBSCA_Route = new Query(getCtx(), "BSCA_Route", " C_DocTypeTarget_ID =?  and BSCA_Route_UU IN ("
				+ "select distinct money from receipts where bsca_Isimported = false) "+whereBankAccount , trxName).
				setParameters(C_DocTypeLot_ID).setOrderBy("StartDate, AD_Org_ID,C_BankAccount_ID").list();
			
		
		forRoutes: for (PO route : lstBSCA_Route) {
						int BSCA_Route_ID = route.get_ID();
						int C_BankAccount_ID = route.get_ValueAsInt("C_BankAccount_ID");
						boolean isActive = route.isActive();
						String c_caja = DB.getSQLValueStringEx(trxName, "select value from C_BankAccount where C_BankAccount_ID=? ", C_BankAccount_ID);
						String DocumentNo = route.get_ValueAsString("DocumentNo");
						if (c_caja==null || DocumentNo ==null)
							continue forRoutes;
						
						String turno = DocumentNo.replaceFirst(c_caja, "");
						String closedCash = route.get_ValueAsString("BSCA_Route_UU");
						
			            //// VALIDA QUE TODOS LOS REGISTROS DE MA_PAGOS SE HAYAN SINCRONIZADO /////////////////////////
						if (!isAllMA_PagosSync(route,c_sucursal))
							continue forRoutes;					
						
						importOrdersNotSummary(closedCash,BSCA_Route_ID, C_BankAccount_ID, c_sucursal);
						if (isActive) 
							importOrdersSummary(closedCash,BSCA_Route_ID, C_BankAccount_ID, c_sucursal);

						////// Importa las ordernes resumidas 
						updateFieldsSumNCSumInvoiced(BSCA_Route_ID);
						
					}
	
		return null;
	}

	private boolean validateSucursal(String c_sucursal ) {
		AD_Org_ID = getAD_Org_ID(c_sucursal);
		M_Warehouse_ID = 0;
		if (AD_Org_ID==-1){
			String msj = "ERROR: Organización con código "+c_sucursal+ " no está registrado. ";
			log.severe(msj);
			return false;
		}else{
			M_Warehouse_ID =getM_WarehouseOrg_ID(AD_Org_ID);
			if(M_Warehouse_ID==-1) {
				String msj = "ERROR: La Organización "+c_sucursal +" No tiene un almacén configurado.";
				log.severe(msj);
				return false;
			}
		}
		return true;
	}

	private void importOrdersSummary(String closeCash_ID, int BSCA_Route_ID,int C_BankAccount_ID,String orgValue) {
		
		Savepoint savePoint= null;
		
		List<BSCA_Tickets> lstTickets = BSCA_Tickets.getTicketsSummaryNotImported(closeCash_ID,orgValue);
			int seq = 0;
	    forPagos:for (BSCA_Tickets tickets : lstTickets) {
				
				try{
					seq+=10;
					savePoint = trx.setSavepoint(null);
					
					String minDocStellar =tickets.getTicketid()+"";
					Timestamp f_fecha = tickets.getDate();
					int c_concepto = tickets.getTickettype();
					
					Integer order_ID =-1;
					
					int DocTypeTarget_ID = c_concepto==0?C_DocTypeTarget_ID:C_DocTypeTargetNC_ID;
					String documentNo = orgValue+minDocStellar;
					
					order_ID= getC_Order_ID(documentNo,DocTypeTarget_ID,AD_Org_ID); // verifica si la orden  está registrada
					if (order_ID!=-1){
						continue forPagos;
					}
					
				    MOrder order = new MOrder(Env.getCtx(), 0, trxName);
					order.setAD_Org_ID(AD_Org_ID);
					order.setC_BPartner_ID(p_C_BPartnerDefault_ID);
					order.setIsSOTrx(true);
					order.setM_Warehouse_ID(M_Warehouse_ID);
					order.setDocumentNo(documentNo);
					order.setC_DocTypeTarget_ID(DocTypeTarget_ID);
					order.saveEx();	
					order.setM_PriceList_ID(M_PriceList_ID);
					order.setPaymentRule(PaymentRule);
					order.set_ValueNoCheck("BSCA_Route_ID", BSCA_Route_ID);
					order.setPaymentRule("M");
					order.setC_PaymentTerm_ID(C_PaymentTerm_ID);
					order.setDateOrdered(f_fecha);
					//order.set_ValueOfColumn("POSDate", POSDate);
					if (isDateStellar)
						order.setDateAcct(f_fecha);		
					order.setDocAction(DocAction.ACTION_Complete);
					order.set_ValueOfColumn("BSCA_SeqNo", seq);
					order.saveEx();	
					log.warning("Orden estelar: "+documentNo);
					
					createPOSDetaillSummay(c_sucursal, c_caja, turno, f_fecha, c_concepto, order);
					
					List<BSCA_MA_Transaccion> lstTransaccion = bsca_Ma_Pagos.getListSummaryMA_Transaccion(DocumentNo, f_fecha);
					MPriceList priceList = new MPriceList(Env.getCtx(), M_PriceList_ID, trxName);
	
	transaccion:for (BSCA_MA_Transaccion ma_Transaccion : lstTransaccion) {
						String valueProduct = ma_Transaccion.getCod_Principal();
//						String BSCA_ValuePOS = ma_Transaccion.getCodigo();
						BigDecimal cantidad =  new BigDecimal(ma_Transaccion.getCantidad());
						BigDecimal price = new BigDecimal(ma_Transaccion.getPrecio()).setScale(order.getPrecision(), BigDecimal.ROUND_HALF_UP);
						String  impuesto1 = ma_Transaccion.getImpuesto1();
						Timestamp POSDate = ma_Transaccion.getPOSDate();
						c_concepto =bsca_Ma_Pagos.getC_concepto();
						int M_Product_ID = p_M_Product_ID;
						String descriptionLine = "";
						BigDecimal Qty = Env.ZERO;
						if (c_concepto.equals("DEV")){
							if (!isQtyNegate)
								Qty = cantidad.negate();
							else 
								Qty = cantidad;
						}else
							Qty =cantidad;
						
						KeyNamePair product = getM_ProductValue(valueProduct);
						Integer BSCA_ProductValue_ID = null;
						String codeNotFound = "";
						log.warning("Producto:"+valueProduct);
						
						if (product==null && p_M_Product_ID ==0){
							String msj = "ERROR: Producto con codigo "+valueProduct+ " no está registrado. Factura "+ documentNo +" no Importada";
							log.severe(msj);
							trx.rollback(savePoint);
							break transaccion;
						}
						if (product!=null){
							BSCA_ProductValue_ID = product.getKey();
							M_Product_ID = Integer.parseInt(product.getName());
							if(!isProductPriceList( M_Product_ID, Qty, order)){
								MProduct mProduct = new MProduct(getCtx(), M_Product_ID, trxName);												
								descriptionLine =  "Producto no esta en lista de precios - Producto: "+mProduct.getName() +", Lista de Precios:"+
								order.getM_PriceList().getName();	
								M_Product_ID = p_M_Product_ID;
							}			
						}else{
							descriptionLine = "Producto stellar "+valueProduct+" no está registrado";
							codeNotFound = valueProduct;
						}
							
						MProduct mProduct = new MProduct(getCtx(), M_Product_ID, trxName);
						
						MTax tax = getTax(impuesto1, mProduct.getC_TaxCategory_ID());	
						int C_Tax_ID = 0;
						if (tax == null){
							descriptionLine+=" ERROR: Impuesto con tasa "+impuesto1+ " no está registrado o no está asignado al Producto "+mProduct.getName();
							tax = getTax(impuesto1,p_C_TaxCategory_ID);
							if (tax==null){
								log.severe("ERROR: Tasa de impuesto  "+impuesto1+ " no está registrado en la categoria de impuesto DEFAULT. Factura "+ documentNo +" no Importada");
								trx.rollback(savePoint);
								break transaccion;
							}else
								C_Tax_ID = tax.get_ID();
						}
	
						BigDecimal PurchaseAmt = getPurchaseAmt(M_Product_ID, POSDate,AD_Org_ID);
						BigDecimal Price = Env.ZERO;
						MOrderLine orderLine = new MOrderLine(order);
						orderLine.setQty(Qty.setScale(mProduct.getC_UOM().getStdPrecision(), BigDecimal.ROUND_HALF_UP));
						C_Tax_ID = tax.get_ID();
						if(priceList.isTaxIncluded()){
							BigDecimal imp=tax.calculateTax(price.abs(), false, order.getPrecision(), order, orderLine);
							Price = price.abs().add(imp);								
						}
						else{
							Price = price.abs();
						}
						orderLine.setPrice(Price); 
						orderLine.set_ValueOfColumn("BSCA_ProductValue_ID", BSCA_ProductValue_ID);
						orderLine.setM_Product_ID(M_Product_ID,true);	
						orderLine.setDescription(descriptionLine);
						orderLine.set_ValueOfColumn("BSCA_CodeNotFound", codeNotFound);
						orderLine.setC_Tax_ID(C_Tax_ID);
						orderLine.set_ValueOfColumn("BSCA_Route_ID", BSCA_Route_ID);
//						orderLine.set_ValueOfColumn("BSCA_ValuePOS", BSCA_ValuePOS);
						orderLine.set_ValueOfColumn("PurchaseAmt", PurchaseAmt); 
						orderLine.set_ValueOfColumn("ProfitAmt", Price.subtract(PurchaseAmt));
						orderLine.saveEx();
					}
					
					BigDecimal totalPOSPayments = Env.ZERO; 
	
					if (!c_concepto.equals("DEV"))  {
						// sincroniza los tipos de pagos
						List<BSCA_MA_DetallePago> listMA_DetallePago = bsca_Ma_Pagos.getListSummaryMA_DetallePago(DocumentNo,f_fecha);
						
						for (BSCA_MA_DetallePago bsca_MA_DetallePago : listMA_DetallePago) {
							String valueTenderType =bsca_MA_DetallePago.getC_coddenominacion();
							String n_monto =bsca_MA_DetallePago.getN_monto();
							String c_codmoneda = bsca_MA_DetallePago.getC_codmoneda();
							BigDecimal n_monto2 = new BigDecimal(n_monto);
							BigDecimal PayAmt =  n_monto2.setScale(priceList.getPricePrecision(), BigDecimal.ROUND_HALF_UP);
							BigDecimal MultiplyRate = new BigDecimal(bsca_MA_DetallePago.getN_factor());
							X_C_POSTenderType tenderType = getPOSTenderType(valueTenderType,c_codmoneda);
							BigDecimal BSCA_QtyCurrency = Env.ZERO;
							if  (MultiplyRate!=null || !Env.ZERO.equals(MultiplyRate))
								BSCA_QtyCurrency = PayAmt.divide(MultiplyRate,2,BigDecimal.ROUND_UP);
							
							if (tenderType!=null){
								X_C_POSPayment posPayment = new X_C_POSPayment(getCtx(), 0, trxName);
								posPayment.setC_POSTenderType_ID(tenderType.get_ID());
								posPayment.setTenderType(tenderType.getTenderType());
								posPayment.setPayAmt(PayAmt);
								posPayment.setC_Order_ID(order.get_ID());
								posPayment.setAD_Org_ID(order.getAD_Org_ID());
								posPayment.set_ValueOfColumn("C_BankAccount_ID", C_BankAccount_ID);
								posPayment.setIsPostDated(tenderType.isPostDated());
								posPayment.set_ValueOfColumn("MultiplyRate", MultiplyRate);
								posPayment.set_ValueOfColumn("BSCA_QtyCurrency", BSCA_QtyCurrency);
								posPayment.saveEx();
								
								totalPOSPayments = totalPOSPayments.add(PayAmt);
							}else{
								log.severe("No hay un registro en POSTenderType con codigo = "+valueTenderType+ " y moneda "+c_codmoneda);
								trx.rollback(savePoint);
								continue forPagos;
							}
						}	
					}
				
					BigDecimal granTotal = DB.getSQLValueBDEx(order.get_TrxName(),"Select GrandTotal from C_Order where C_Order_ID= " +order.get_ID());
					if (!c_concepto.equals("DEV")){
																		
						if (totalPOSPayments.compareTo(granTotal) != 0){
							BigDecimal diff = totalPOSPayments.subtract(granTotal);
							MOrderLine orderLine = new MOrderLine(order);
							orderLine.setQty(Env.ONE);
							orderLine.setPrice(diff.setScale(priceList.getPricePrecision(), BigDecimal.ROUND_HALF_UP)); 
							orderLine.setC_Charge_ID(C_Charge_ID);	
							orderLine.set_ValueOfColumn("BSCA_Route_ID", BSCA_Route_ID);
							orderLine.saveEx();
						}
					}else {
						if (isQtyNegate){
							X_C_POSTenderType tenderType = new Query(Env.getCtx(),X_C_POSTenderType.Table_Name, "name = ?" , trxName)
							.setParameters("EFECTIVO").first();
							
							if (tenderType!=null){ 
							X_C_POSPayment posPayment = new X_C_POSPayment(getCtx(), 0, trxName);
								posPayment.setC_POSTenderType_ID(tenderType.get_ID());
								posPayment.setTenderType(tenderType.getTenderType());
								posPayment.setPayAmt(granTotal.negate().setScale(priceList.getPricePrecision(), BigDecimal.ROUND_HALF_UP));
								posPayment.setC_Order_ID(order.get_ID());
								posPayment.set_ValueOfColumn("C_BankAccount_ID", C_BankAccount_ID);
								posPayment.setAD_Org_ID(order.getAD_Org_ID());
								posPayment.saveEx();
							}else {
								log.severe("No hay un registro en POSTenderType con nombre = EFECTIVO");
								trx.rollback(savePoint);
								continue forPagos;
							}
						}
					}
					
					MDocType docTarget_ID = (MDocType)order.getC_DocTypeTarget();					
					
					if (docTarget_ID.get_ValueAsBoolean("BSCA_IsCompleitOnImport")){
					
						try{
							if (order.processIt(DocAction.ACTION_Complete)){
								order.saveEx();
							}else 
								log.severe("Orden stellar no completada: "+order.getProcessMsg());
							}
						catch (Exception e){
							addLog(e.getMessage());
							log.severe("ERROR: ocurrio una excepción: "+e.getMessage());
						}
					}
					
						
				}catch (Exception e){
					addLog(e.getMessage());
					log.severe("ERROR: ocurrio una excepción: "+e.getMessage());
					try {
						trx.rollback(savePoint);
						
					} catch (SQLException e1) {
						log.severe("ERROR: ocurrio una excepción en RollBack: "+e1.getMessage());
					}
					continue forPagos;
				} finally {
					if (savePoint != null) {
						try {
							trx.releaseSavepoint(savePoint);
						} catch (SQLException e) {
							log.severe("ERROR: ocurrio una excepción en Finally savePoint: "+e.getMessage());
						}
					}
					trx.commit();
					savePoint = null;
				}							

		}// FOR PAGOS	
	}

	private boolean isAllMA_PagosSync(PO route, String c_sucursal) {
		
	
		String DocumentNo = route.get_ValueAsString("DocumentNo");
		String queryQty = " Select count(*) as Qty "
						+" FROM MA_Pagos p"
						+" JOIN ma_documentos_fiscal df ON df.cu_documentostellar = p.c_numero And p.c_sucursal = df.cu_localidad and cu_documentoTipo = p.C_concepto "
						+" where  (p.C_caja ||p.turno)::text = "+DB.TO_STRING(DocumentNo)
						+" and p.C_sucursal = "+DB.TO_STRING(c_sucursal)+" and p.c_concepto = 'VEN' group by p.C_sucursal,p.C_caja, p.turno "
						+" order by p.C_caja";
		
		int qtyOrders = DB.getSQLValueEx(trxName, queryQty);
		if (qtyOrders==-1)
			qtyOrders=0;
		
		String StellarQtySales = route.get_ValueAsString("BSCA_StellarQtySales");
		if (StellarQtySales!=null && !"".equals(StellarQtySales)){
			int stellarQty = Integer.parseInt(StellarQtySales);
			if ( qtyOrders!=stellarQty){
				log.severe("La Cantidad Venta Stellar("+StellarQtySales+") y El numero de ventas a importar ("+qtyOrders+") no coinciden ");
				return false;
			}
		}
		
		return true;
	}

	private void createPOSDetaillNotSummary( String c_sucursal, String c_concepto,String c_numero,BSCA_MOrder order){
		List<BSCA_MA_Pagos> lstMA_Pagos = BSCA_MA_Pagos.getLstMA_PagosPOSDetaillNotSummary(c_sucursal, c_concepto, c_numero);
		createPOSDetaill(lstMA_Pagos,order,c_concepto);
	}
	
	
	private void createPOSDetaillSummay( String c_sucursal, String c_caja, String turno,Timestamp f_fecha, String c_concepto,BSCA_MOrder order){
		List<BSCA_MA_Pagos> lstMA_Pagos = BSCA_MA_Pagos.getLstMA_PagosPOSDetaillSummary(c_sucursal, c_caja, turno, f_fecha, c_concepto);
		createPOSDetaill(lstMA_Pagos,order,c_concepto);
		
	
	}

	private void createPOSDetaill(List<BSCA_MA_Pagos> lstMA_Pagos, BSCA_MOrder order, String c_concepto){
		for (BSCA_MA_Pagos bsca_Ma_Pagos : lstMA_Pagos) {			
				
				String cs_documento_rel = bsca_Ma_Pagos.getCs_documento_rel();
				String cu_documentofiscal = bsca_Ma_Pagos.getCu_documentofiscal();
				String cu_serialimpresora = bsca_Ma_Pagos.getCu_serialimpresora();
				String c_numero = bsca_Ma_Pagos.getC_numero();
				String c_sucursal = bsca_Ma_Pagos.getC_sucursal();
				String c_rif = bsca_Ma_Pagos.getC_rif();
				String c_desc_cliente =bsca_Ma_Pagos.getC_desc_cliente();
				String cu_direccion_cliente = bsca_Ma_Pagos.getCu_direccion_cliente();
				
				int id= bsca_Ma_Pagos.getId();
				
				BigDecimal n_impuesto =  new BigDecimal(bsca_Ma_Pagos.getN_impuesto()).setScale(order.getPrecision(), BigDecimal.ROUND_HALF_UP);
				BigDecimal n_total = new BigDecimal(bsca_Ma_Pagos.getN_total()).setScale(order.getPrecision(), BigDecimal.ROUND_HALF_UP);
				Boolean  BSCA_isTaxPayer = false;
				if(c_rif!=null){
					c_rif=c_rif.toUpperCase().replaceAll("[^VEJGP0-9]", "");
					if (c_rif.matches("^[VJGP][0-9]{9}$")){ // verifica RIF
						BSCA_isTaxPayer = true;
					}
				}
				
				BigDecimal TaxAmtOrder = Env.ZERO;
				BigDecimal GrandTotal = Env.ZERO;
				if (c_concepto.equals("DEV")){
					if (!isQtyNegate){
						TaxAmtOrder = n_impuesto.negate();
						GrandTotal = n_total.negate();
					}else{ 
						TaxAmtOrder = n_impuesto;
						GrandTotal = n_total;
					}
				}else{
					TaxAmtOrder = n_impuesto;
					GrandTotal = n_total;
				}
				log.warning("Registrando POS Detaill, orden stellar: "+c_numero+ ", impresora fiscal:"+cu_serialimpresora);
	
				X_BSCA_POSDetaill posDetaill = new X_BSCA_POSDetaill(getCtx(), 0, trxName);
				posDetaill.setAD_Org_ID(order.getAD_Org_ID());
				posDetaill.setC_Order_ID(order.get_ID());
				posDetaill.setTaxAmtOrder(TaxAmtOrder);
				posDetaill.setGrandTotal(GrandTotal);
				
				posDetaill.setBSCA_FiscalDocumentNo(cu_documentofiscal);
				posDetaill.setBSCA_Pos_invoiceaffected(cs_documento_rel);
				posDetaill.setFiscalPrinterSerial(cu_serialimpresora);
				posDetaill.setBSCA_StellarDocumento(c_numero);
				posDetaill.setBSCA_StellarRif(c_rif);
				posDetaill.setClientName(c_desc_cliente);
				posDetaill.setLocationName(cu_direccion_cliente);
				posDetaill.setBSCA_isTaxPayer(BSCA_isTaxPayer);
				posDetaill.saveEx();	
				
				
				List<BSCA_MA_SummaryTax> lstSummaryTax = bsca_Ma_Pagos.getListSummaryTax(c_concepto, c_sucursal);
				for (BSCA_MA_SummaryTax bsca_MA_SummaryTax : lstSummaryTax) {
					
					int C_Tax_ID = DB.getSQLValueEx(trxName, " select C_Tax_ID FROM  C_Tax where rate = ? And C_TaxCategory_ID = ? and isActive = 'Y'",bsca_MA_SummaryTax.getRate(), p_C_TaxCategory_ID );
					BigDecimal TaxBaseAmt = bsca_MA_SummaryTax.getTaxBaseAmt().setScale(order.getPrecision(), BigDecimal.ROUND_HALF_UP);			
					BigDecimal TaxAmt = bsca_MA_SummaryTax.getTaxAmt().setScale(order.getPrecision(), BigDecimal.ROUND_HALF_UP);
//					MTax tax = MTax.get(getCtx(), C_Tax_ID);
//					BigDecimal TaxAmt = tax.calculateTax(TaxBaseAmt, false, order.getPrecision()).setScale(order.getPrecision(), BigDecimal.ROUND_HALF_UP);
					
					X_BSCA_POSTaxDetaill posTaxDetaill = new X_BSCA_POSTaxDetaill(getCtx(), 0, get_TrxName());
					posTaxDetaill.setBSCA_POSDetaill_ID(posDetaill.get_ID());
					posTaxDetaill.setAD_Org_ID(posDetaill.getAD_Org_ID());	
					posTaxDetaill.setC_Tax_ID(C_Tax_ID);
					posTaxDetaill.setTaxAmt(TaxAmt);
					posTaxDetaill.setTaxBaseAmt(TaxBaseAmt);
					posTaxDetaill.setRate(bsca_MA_SummaryTax.getRate());
					posTaxDetaill.saveEx();
						
				}
				
				DB.executeUpdateEx("Update MA_Pagos set bsca_isimported = true where id ="+id +" and C_sucursal = "+DB.TO_STRING(c_sucursal), trx.getTrxName());
			}
	}
	private void importOrdersNotSummary(int BSCA_Route_ID,int C_BankAccount_ID, String c_sucursal, String c_caja, String turno){

		int seq= 0;
		Savepoint savePoint = null;
		List<BSCA_MA_Pagos> lstMA_Pagos = BSCA_MA_Pagos.getLstMA_PagosNotSummary(c_sucursal,c_caja, turno);		
		forPagos: for (BSCA_MA_Pagos bsca_Ma_Pagos : lstMA_Pagos) {
					
					seq+=10;
					boolean error = false;
					try{
						savePoint = trx.setSavepoint(null);
						
						String bPartnerValue = bsca_Ma_Pagos.getC_rif();
						String documentNo = bsca_Ma_Pagos.getC_numero();
						String c_desc_cliente =bsca_Ma_Pagos.getC_desc_cliente();
						String c_concepto =bsca_Ma_Pagos.getC_concepto();
						String cs_documento_rel = bsca_Ma_Pagos.getCs_documento_rel();
						Timestamp f_fecha = bsca_Ma_Pagos.getF_fecha();
						Timestamp POSDate = bsca_Ma_Pagos.getPOSDate();
						int id = bsca_Ma_Pagos.getId();
						
						
						int DocTypeTarget_ID = c_concepto.equals("DEV")?C_DocTypeTargetNC_ID:C_DocTypeTarget_ID;
						Integer AD_Org_ID = getAD_Org_ID(c_sucursal);
						Integer order_ID =-1;
						if (AD_Org_ID!=-1)
							order_ID= getC_Order_IDfromStellarDocumento(documentNo,DocTypeTarget_ID,AD_Org_ID); // verifica si la orden  está registrada
						if (order_ID!=-1){
							DB.executeUpdateEx("Update MA_Pagos set bsca_isimported = true where id ="+id +" and C_sucursal = "+DB.TO_STRING(c_sucursal), trx.getTrxName());
							continue forPagos;
						}
						
						Integer taxIdType=0;
						
						Boolean isValidRif = true;
						if(bPartnerValue!=null){
							bPartnerValue=bPartnerValue.toUpperCase().replaceAll("[^VEJG0-9]", "");
							if (bPartnerValue.matches("^[VJG][0-9]{9}$")){ // verifica RIF
								taxIdType=getLCO_TaxIdType_IDByName("RIF");
							}
							else if(bPartnerValue.matches("^V[0-9]{6,8}$")){//verifica cedula
								taxIdType=getLCO_TaxIdType_IDByName("CEDULA");
							}
							else if(bPartnerValue.matches("^[EP][0-9]{6,}$")){//verifica extranjero o pasaporte
								if(bPartnerValue.startsWith("E")){
									taxIdType=getLCO_TaxIdType_IDByName("EXTRANJERO");
								}else{
									taxIdType=getLCO_TaxIdType_IDByName("PASAPORTE");
								}
							}
							else{
								taxIdType=getLCO_TaxIdType_IDByName("CEDULA");	
								isValidRif = false;
							}
						}
						String description = "";
						if (isValidRif){ 
							Integer bpartner_ID = getBParnert_ID(bPartnerValue);
							String nameBPartner = c_desc_cliente == null || c_desc_cliente.equals("")?"** NOMBRE NO VALIDO ** ":c_desc_cliente;
							MBPartner bpartner =null;
							if (bpartner_ID==-1){
								bpartner = new MBPartner(getCtx(), 0, null);
								bpartner.setAD_Org_ID(AD_OrgBPartner_ID);
								bpartner.setTaxID(bPartnerValue);
								bpartner.setName(nameBPartner);
								bpartner.setIsCustomer(true);
								bpartner.setValue(bPartnerValue);
								bpartner.setPaymentRule(PaymentRule);
								bpartner.setC_BP_Group_ID(C_BP_Group_ID);
								bpartner.set_ValueOfColumn("BSCA_isDefaultAddress", true);
								bpartner.set_ValueOfColumn("LCO_TaxPayerType_ID", getLCO_TaxPayerType(bPartnerValue));
								bpartner.set_ValueOfColumn("LCO_TaxIdType_ID", taxIdType);
								bpartner.setC_PaymentTerm_ID(C_PaymentTerm_ID);
								bpartner.setM_PriceList_ID(M_PriceListBPartner_ID);
								bpartner.saveEx();
								
								bpartner_ID = bpartner.get_ID();
							}else 
								bpartner = new MBPartner(getCtx(), bpartner_ID, trx.getTrxName());
								
							
							DB.executeUpdateEx("Update C_BPartner set name = ? , isCustomer = 'Y' where C_BPartner_ID = ?",new Object[]{nameBPartner,bpartner_ID}, trx.getTrxName());
							
							if (isBPartnerLocation(bpartner))
								C_BPartner_ID = bpartner.getC_BPartner_ID();
							else{
								C_BPartner_ID =  p_C_BPartnerDefault_ID;
								description = "Tercero no tiene dirección de entrega -"+ bpartner.getValue() +"_"+ bpartner.getName();
							}

						}else{
							C_BPartner_ID =  p_C_BPartnerDefault_ID;
							description = " Rif "+bPartnerValue + " No válido";
						}
						
						int M_Warehouse_ID = 0;
						if (AD_Org_ID==-1){
							addLog("ERROR: Organización con código "+c_sucursal+ " no está registrado. Factura "+ documentNo +"no Importada");
							log.severe("ERROR: Organización con código "+c_sucursal+ " no está registrado. Factura "+ documentNo +"no Importada");
							error = true;
							trx.rollback(savePoint);
							continue forPagos;
						}else{
							M_Warehouse_ID =getM_WarehouseOrg_ID(AD_Org_ID);
							if(M_Warehouse_ID==-1) {
								addLog("ERROR: La Organización "+c_sucursal +" No tiene un almacén configurado. Factura "+documentNo);
								log.severe("ERROR: La Organización "+c_sucursal +" No tiene un almacén configurado. Factura "+documentNo);
								trx.rollback(savePoint);
								continue forPagos;
							}
						}
					
						//Sincroniza las Ordenes 
					    MOrder order = new MOrder(Env.getCtx(), 0, trxName);
						order.setAD_Org_ID(AD_Org_ID);
						order.setC_BPartner_ID(C_BPartner_ID);
						order.setIsSOTrx(true);
						order.setM_Warehouse_ID(M_Warehouse_ID);
						order.setDocumentNo(c_sucursal+documentNo);
						order.setC_DocTypeTarget_ID(DocTypeTarget_ID);
						order.saveEx();	
						order.setM_PriceList_ID(M_PriceList_ID);
						order.setDescription(description);
						order.setPaymentRule(PaymentRule);
						order.set_ValueNoCheck("BSCA_Route_ID", BSCA_Route_ID);
						order.setPaymentRule("M");
						order.set_ValueOfColumn("BSCA_Pos_invoiceaffected",cs_documento_rel );
						order.setC_PaymentTerm_ID(C_PaymentTerm_ID);
						order.setDateOrdered(f_fecha);
						order.set_ValueOfColumn("POSDate", POSDate);
						if (isDateStellar)
							order.setDateAcct(f_fecha);
						
						order.setDocAction(DocAction.ACTION_Complete);
						
						if (c_concepto.equals("DEV")){
							Integer orderAffected_ID = getC_Order_ID(cs_documento_rel,AD_Org_ID);
							if (orderAffected_ID!=-1) 
								order.set_ValueOfColumn("BSCA_OrderAffected_ID", orderAffected_ID);
						}

						order.set_ValueOfColumn("BSCA_SeqNo", seq);
						order.set_ValueOfColumn("BSCA_StellarRif", bsca_Ma_Pagos.getC_rif());
						order.set_ValueOfColumn("BSCA_StellarName", bsca_Ma_Pagos.getC_desc_cliente());
						order.saveEx();	
						log.warning("Orden estelar: "+documentNo);
						log.warning("Tercero: "+bPartnerValue);
															
						createPOSDetaillNotSummary(c_sucursal, c_concepto, documentNo, order);
						
						//sincroniza las lineas de las ordenes 
						error = false;
						MPriceList priceList = new MPriceList(Env.getCtx(), M_PriceList_ID, trxName);
						List<BSCA_MA_Transaccion> listTransaccion = bsca_Ma_Pagos.getListMA_Transaccion();

						forTransaccion:for (BSCA_MA_Transaccion ma_Transaccion : listTransaccion) {
							String valueProduct = ma_Transaccion.getCod_Principal();
							String BSCA_ValuePOS = ma_Transaccion.getCodigo();
							BigDecimal cantidad =  new BigDecimal(ma_Transaccion.getCantidad());
							BigDecimal price = new BigDecimal(ma_Transaccion.getPrecio()).setScale(order.getPrecision(), BigDecimal.ROUND_HALF_UP);
							String  impuesto1 = ma_Transaccion.getImpuesto1();
							c_concepto =bsca_Ma_Pagos.getC_concepto();
							int M_Product_ID = p_M_Product_ID;
							String descriptionLine = "";
							BigDecimal Qty = Env.ZERO;
							if (c_concepto.equals("DEV")){
								if (!isQtyNegate)
									Qty = cantidad.negate();
								else 
									Qty = cantidad;
							}else
								Qty =cantidad;
							
							KeyNamePair product = getM_ProductValue(valueProduct);
							Integer BSCA_ProductValue_ID = null;
							String codeNotFound = "";
							log.warning("Producto:"+valueProduct);
							
							if (product==null && p_M_Product_ID ==0){
								addLog("ERROR: Producto con codigo "+valueProduct+ " no está registrado. Factura "+ documentNo +" no Importada");
								log.severe("ERROR: Producto con codigo "+valueProduct+ " no está registrado. Factura "+ documentNo +" no Importada");
								error = true;
								break forTransaccion;
							}
							if (product!=null){
								BSCA_ProductValue_ID = product.getKey();
								M_Product_ID = Integer.parseInt(product.getName());
								if(!isProductPriceList( M_Product_ID, Qty, order)){
									MProduct mProduct = new MProduct(getCtx(), M_Product_ID, trxName);												
									descriptionLine =  "Producto no esta en lista de precios - Producto: "+mProduct.getName() +", Lista de Precios:"+
									order.getM_PriceList().getName();	
									M_Product_ID = p_M_Product_ID;
								}			
							}else{
								descriptionLine = "Producto stellar "+valueProduct+" no está registrado";
								codeNotFound = valueProduct;
							}
								
							MProduct mProduct = new MProduct(getCtx(), M_Product_ID, trxName);
							
							MTax tax = getTax(impuesto1, mProduct.getC_TaxCategory_ID());	
							int C_Tax_ID = 0;
							if (tax == null){
								descriptionLine+=" ERROR: Impuesto con tasa "+impuesto1+ " no está registrado o no está asignado al Producto "+mProduct.getName();
								tax = getTax(impuesto1,p_C_TaxCategory_ID);
								if (tax==null){
									addLog("ERROR: Tasa de impuesto  "+impuesto1+ " no está registrado en la categoria de impuesto DEFAULT. Factura "+ documentNo +" no Importada");
									log.severe("ERROR: Tasa de impuesto  "+impuesto1+ " no está registrado en la categoria de impuesto DEFAULT. Factura "+ documentNo +" no Importada");
									error = true;
									break forTransaccion;
								}else
									C_Tax_ID = tax.get_ID();
							}
	
							BigDecimal PurchaseAmt = getPurchaseAmt(M_Product_ID, POSDate,AD_Org_ID);
							BigDecimal Price = Env.ZERO;
							MOrderLine orderLine = new MOrderLine(order);
							orderLine.setQty(Qty.setScale(mProduct.getC_UOM().getStdPrecision(), BigDecimal.ROUND_HALF_UP));
							C_Tax_ID = tax.get_ID();
							if(priceList.isTaxIncluded()){
								BigDecimal imp=tax.calculateTax(price.abs(), false, order.getPrecision(), order, orderLine);
								Price = price.abs().add(imp);								
							}
							else{
								Price = price.abs();
							}
							orderLine.setPrice(Price); 
							orderLine.set_ValueOfColumn("BSCA_ProductValue_ID", BSCA_ProductValue_ID);
							orderLine.setM_Product_ID(M_Product_ID,true);	
							orderLine.setDescription(descriptionLine);
							orderLine.set_ValueOfColumn("BSCA_CodeNotFound", codeNotFound);
							orderLine.setC_Tax_ID(C_Tax_ID);
							orderLine.set_ValueOfColumn("BSCA_Route_ID", BSCA_Route_ID);
							orderLine.set_ValueOfColumn("BSCA_ValuePOS", BSCA_ValuePOS);
							orderLine.set_ValueOfColumn("PurchaseAmt", PurchaseAmt); 
							orderLine.set_ValueOfColumn("ProfitAmt", Price.subtract(PurchaseAmt));
							orderLine.saveEx();
						}
						if (error){
							log.severe("ROLLBACK: de los Erorres en la Orden y Lineas");
							trx.rollback(savePoint);
							continue forPagos;
						}
						
						
						// sincroniza los tipos de pagos
						List<BSCA_MA_DetallePago> listMA_DetallePago = bsca_Ma_Pagos.getListMA_DetallePago();
						error = false;
						BigDecimal totalPOSPayments = Env.ZERO; 
						if (!c_concepto.equals("DEV"))  {
							for (BSCA_MA_DetallePago bsca_MA_DetallePago : listMA_DetallePago) {
								String valueTenderType =bsca_MA_DetallePago.getC_coddenominacion();
								String n_monto =bsca_MA_DetallePago.getN_monto();
								BigDecimal n_monto2 = new BigDecimal(n_monto);
								BigDecimal PayAmt =  n_monto2.setScale(priceList.getPricePrecision(), BigDecimal.ROUND_HALF_UP);
								String  banco = bsca_MA_DetallePago.getC_banco();
								String c_numero =bsca_MA_DetallePago.getC_numero();
								String c_codMoneda = bsca_MA_DetallePago.getC_codmoneda();
								BigDecimal MultiplyRate = new BigDecimal(bsca_MA_DetallePago.getN_factor());
								X_C_POSTenderType tenderType = getPOSTenderType(valueTenderType,c_codMoneda);
								BigDecimal BSCA_QtyCurrency = Env.ZERO;
								if  (MultiplyRate!=null || !Env.ZERO.equals(MultiplyRate))
									BSCA_QtyCurrency = PayAmt.divide(MultiplyRate,2,BigDecimal.ROUND_UP);
																				
								if (tenderType!=null){
									X_C_POSPayment posPayment = new X_C_POSPayment(getCtx(), 0, trxName);
									posPayment.setC_POSTenderType_ID(tenderType.get_ID());
									posPayment.setTenderType(tenderType.getTenderType());
									posPayment.setPayAmt(PayAmt);
									posPayment.setC_Order_ID(order.get_ID());
									posPayment.setAD_Org_ID(order.getAD_Org_ID());
									posPayment.set_ValueOfColumn("C_BankAccount_ID", C_BankAccount_ID);
									posPayment.setIsPostDated(tenderType.isPostDated());
									posPayment.set_ValueOfColumn("MultiplyRate", MultiplyRate);
									posPayment.set_ValueOfColumn("BSCA_QtyCurrency", BSCA_QtyCurrency);
									posPayment.setHelp(banco + "-" + c_numero);
									posPayment.saveEx();
									
									totalPOSPayments = totalPOSPayments.add(PayAmt);
								}else{
									error = true;
									break;
								}
							}	
						}
					
						if (error){
							log.severe("ROLLBACK: de los Erorres en PosPayment");
							trx.rollback(savePoint);
							continue forPagos;
						}
						BigDecimal granTotal = DB.getSQLValueBDEx(order.get_TrxName(),"Select GrandTotal from C_Order where C_Order_ID= " +order.get_ID());
						if (!c_concepto.equals("DEV")){
																			
							if (totalPOSPayments.compareTo(granTotal) != 0){
								BigDecimal diff = totalPOSPayments.subtract(granTotal);
								MOrderLine orderLine = new MOrderLine(order);
								orderLine.setQty(Env.ONE);
								orderLine.setPrice(diff.setScale(priceList.getPricePrecision(), BigDecimal.ROUND_HALF_UP)); 
								orderLine.setC_Charge_ID(C_Charge_ID);	
								orderLine.set_ValueOfColumn("BSCA_Route_ID", BSCA_Route_ID);
								orderLine.saveEx();
							}
						}else {
							if (isQtyNegate){
								X_C_POSTenderType tenderType = new Query(Env.getCtx(),X_C_POSTenderType.Table_Name, "name = ?" , trxName).setParameters("EFECTIVO").first();
								
								X_C_POSPayment posPayment = new X_C_POSPayment(getCtx(), 0, trxName);
								posPayment.setC_POSTenderType_ID(tenderType.get_ID());
								posPayment.setTenderType(tenderType.getTenderType());
								posPayment.setPayAmt(granTotal.negate().setScale(priceList.getPricePrecision(), BigDecimal.ROUND_HALF_UP));
								posPayment.setC_Order_ID(order.get_ID());
								posPayment.set_ValueOfColumn("C_BankAccount_ID", C_BankAccount_ID);
								posPayment.setAD_Org_ID(order.getAD_Org_ID());
								posPayment.saveEx();
							}
						}
						
						MDocType docTarget_ID = (MDocType)order.getC_DocTypeTarget();				
						DB.executeUpdateEx("Update MA_Pagos set bsca_isimported = true where id ="+id +" and C_sucursal = "+DB.TO_STRING(c_sucursal), trx.getTrxName());	
						
						if (docTarget_ID.get_ValueAsBoolean("BSCA_IsCompleitOnImport")){
						
							try{
								if (order.processIt(DocAction.ACTION_Complete)){
									order.saveEx();
								}else 
									log.severe("Orden stellar no completada: "+order.getProcessMsg());
								}
							catch (Exception e){
								addLog(e.getMessage());
								log.severe("ERROR: ocurrio una excepción: "+e.getMessage());
							}
						}
												

				}catch (Exception e){
					addLog(e.getMessage());
					log.severe("ERROR: ocurrio una excepción: "+e.getMessage());
					try {
						trx.rollback(savePoint);
					} catch (SQLException e1) {
						log.severe("ERROR: ocurrio una excepción en RollBack: "+e1.getMessage());
					}
					continue forPagos;
				} finally {
					if (savePoint != null) {
						try {
							trx.releaseSavepoint(savePoint);
						} catch (SQLException e) {
							log.severe("ERROR: ocurrio una excepción en Finally savePoint: "+e.getMessage());
						}
					}
					trx.commit();
				}
				
//			} // FOR PAGO	
		}// While LST PAGOS 
		
	}
	
	private void updateFieldsSumNCSumInvoiced(int BSCA_Route_ID) {
		String sqlInv ="select COALESCE(SUM(grandTotal),0)  from C_Order o " +
				"JOIN C_DocType dt ON dt.C_DocType_ID = o.C_DocTypeTarget_ID "+
				"JOIN C_DocType dt2 ON dt2.C_DocType_ID = dt.C_DocTypeInvoice_ID "+
				"where BSCA_Route_ID = "+BSCA_Route_ID+"  and dt2.Docbasetype = 'ARI'";
				
		String SumInvoiced = DB.getSQLValueStringEx(trxName, sqlInv);
				
		String sqlNC ="select COALESCE(SUM(grandTotal),0) from C_Order o " +
						"JOIN C_DocType dt ON dt.C_DocType_ID = o.C_DocTypeTarget_ID "+
						"JOIN C_DocType dt2 ON dt2.C_DocType_ID = dt.C_DocTypeInvoice_ID "+
						"where BSCA_Route_ID = "+BSCA_Route_ID+"  and dt2.Docbasetype = 'ARC'";
				
		String SumNC = DB.getSQLValueStringEx(trxName, sqlNC);
				
		String sqlTotals = "Update BSCA_Route set "+
						"SumInvoiced =  "+SumInvoiced+
						",SumNC =  "+	SumNC+
						"where BSCA_Route_ID="+BSCA_Route_ID;
		DB.executeUpdateEx(sqlTotals,trxName);	
		trx.commit();
	}
private void UpdateBSCA_Route(String c_sucursal ){
		
		String sql = null;
		ResultSet rsCaja = null;
		PreparedStatement pstmtCaja  = null;
		String whereBankAccount = "";
		if (p_C_BankAccount_ID!=0){
			 String value = DB.getSQLValueStringEx(trxName, "Select value from C_BankAccount where C_BankAccount_ID=? ", p_C_BankAccount_ID);
			 whereBankAccount = " and tc.c_codigo = '"+value+"' ";
		}
		try{

			List<BSCA_ClosedCash> lstClosedCash = BSCA_ClosedCash.getListClosedCash();
			
			for (BSCA_ClosedCash bsca_ClosedCash : lstClosedCash) {
				int BSCA_Route_ID = bsca_ClosedCash.getBSCA_Route_ID();
				String host = bsca_ClosedCash.getHost();
				int hostsequence = bsca_ClosedCash.getHostsequence();
				String EndDate = bsca_ClosedCash.getDateend()==null?null:"'"+bsca_ClosedCash.getDateend()+"'" ;
				String isActive = bsca_ClosedCash.getDateend()!=null? "'Y'":"'N'";
				if (BSCA_Route_ID==0){
					
					Timestamp StartDate = bsca_ClosedCash.getDatestart();
					int C_DocType_ID = C_DocTypeLot_ID;
					String DocStatus = "DR";
					String DeliveryViaRule = "D";
					String DocumentNo = host+hostsequence;
					BSCA_Route_ID = getBSCA_Route_ID();
					Integer AD_User_ID = Env.getAD_User_ID(getCtx());
					Integer AD_Client_ID = Env.getAD_Client_ID(getCtx());
					String BSCA_Route_UU = DB.TO_STRING(bsca_ClosedCash.getMoney());//DB.TO_STRING(UUID.randomUUID().toString());
					Integer Cajero_ID = Env.getAD_User_ID(getCtx());//TODO:getCajero(c_cajero);
					Integer Caja_ID = getCaja(host,AD_Org_ID);
					
					
					Timestamp DateAcct = bsca_ClosedCash.getDatestart();
								
					if (Caja_ID==-1){
						Caja_ID = null;
					}
					
					if (Cajero_ID==-1){
						Cajero_ID = AD_User_ID;
					}
					
					String sqlInsert = "Insert into BSCA_Route (BSCA_Route_ID,BSCA_Route_UU,AD_Org_ID,AD_Client_ID, IsActive,CreatedBy,UpdatedBy,Created,Updated,"
									 + "DateAcct,StartDate,EndDate,C_DocTypeTarget_ID,DocStatus,DeliveryViaRule,DocumentNo,processed, AD_User_ID,C_BankAccount_ID,BSCA_StellarQtySales) Values "
									 +"("+BSCA_Route_ID +","+BSCA_Route_UU+","+AD_Org_ID+","+AD_Client_ID+","+isActive+","+AD_User_ID+","+AD_User_ID+
									 ",SysDate,SysDate,'"+DateAcct+"','"+StartDate+"',"+EndDate+","+C_DocType_ID+",'"+DocStatus+"','"
									 +DeliveryViaRule+"','"+DocumentNo+"','N',"+Cajero_ID+","+Caja_ID+","+0+")";
					DB.executeUpdateEx(sqlInsert, trxName);
					addLog(0, null, null, "Ruta Registrada: "+DocumentNo,MTable.getTable_ID("BSCA_Route"), BSCA_Route_ID);
				}else{
					DB.executeUpdateEx("Update BSCA_Route set BSCA_StellarQtySales = "+0+"  where BSCA_Route_ID = "+BSCA_Route_ID , trxName);
						
					String setEndate="";
					if (EndDate!=null)
						setEndate =", EndDate = "+EndDate;
					DB.executeUpdateEx("Update BSCA_Route set isActive = "+isActive+" "+setEndate+" where BSCA_Route_ID = "+BSCA_Route_ID , trxName);				
					
				}
				
	
			}
				
		}catch (Exception e){
			log.log(Level.SEVERE, sql, e);
			addLog(e.getMessage());
			trx.rollback();
		}
		finally{
			DB.close(rsCaja, pstmtCaja);
			rsCaja = null;
			pstmtCaja = null;
		}
	}
	
	
	private Timestamp getEndDate(Timestamp date, Timestamp hour){
		
		Calendar calDate = Calendar.getInstance();
		calDate.setTimeInMillis(date.getTime());
		
		Calendar calHour = Calendar.getInstance();
		calHour.setTimeInMillis(hour.getTime());
	
		calDate.set(Calendar.HOUR_OF_DAY, calHour.get(Calendar.HOUR_OF_DAY));
		calDate.set(Calendar.MINUTE, calHour.get(Calendar.MINUTE));
		calDate.set(Calendar.SECOND, calHour.get(Calendar.SECOND));
		calDate.set(Calendar.MILLISECOND,calHour.get(Calendar.MILLISECOND));
		
		return new Timestamp(calDate.getTimeInMillis());
	}
	
	private MTax getTax(String n_montoimp, Integer C_TaxCategory_ID) {
		MTax tax= new Query(Env.getCtx(),MTax.Table_Name,"rate = ? and AD_Client_ID = ? and C_TaxCategory_ID = ?",trxName).
				setParameters(new BigDecimal(n_montoimp), Env.getAD_Client_ID(getCtx()),C_TaxCategory_ID).first();
		return tax;
	}
	
	private Integer getCaja(String c_caja, Integer AD_Org_ID) {
		return DB.getSQLValueEx(trxName, "Select C_BankAccount_ID from C_BankAccount where value = '"+c_caja+"' and AD_Org_ID = "+AD_Org_ID + " and isActive = 'Y'");
	}

	private Integer getCajero(String c_cajero) {
		return DB.getSQLValueEx(trxName, "Select AD_User_ID from BSCA_MA_Usuarios where codusuario = '"+c_cajero+"'");
	}

	private Integer getBSCA_Route_ID() {
		return DB.getNextID(Env.getAD_Client_ID(getCtx()), "BSCA_Route", trxName);
	}

	private int getAD_Org_ID(String c_localidad) {
		return DB.getSQLValueEx(trxName, "Select AD_Org_ID from AD_Org where value = '"+c_localidad +"'");
	}
	
	private int getM_WarehouseOrg_ID(Integer AD_Org_ID) {
		return DB.getSQLValueEx(trxName, "select M_Warehouse_ID FROM AD_OrgInfo where AD_Org_ID = "+AD_Org_ID);
	}
	
	private Integer getC_Order_ID(String documentNo, int DocTypeTarget_ID, int AD_Org_ID) {
		return DB.getSQLValueEx(trxName, "Select C_Order_ID from C_Order where DocumentNo = '"+documentNo+"' AND C_DocTypeTarget_ID = "+DocTypeTarget_ID
				+" AND DocStatus IN ('DR','IP','CO')  and isActive = 'Y' AND AD_Org_ID="+AD_Org_ID);
	}
	
	private Integer getC_Order_IDfromStellarDocumento(String BSCA_StellarDocumento, int DocTypeTarget_ID, int AD_Org_ID) {
		return DB.getSQLValueEx(trxName, "Select C_Order_ID from C_Order where BSCA_StellarDocumento = '"+BSCA_StellarDocumento+"' AND C_DocTypeTarget_ID = "+DocTypeTarget_ID
				+" AND DocStatus IN ('DR','IP','CO')  and isActive = 'Y' AND AD_Org_ID="+AD_Org_ID+LIMIT_1);
	}
	
	private KeyNamePair getM_ProductValue(String value) {
		KeyNamePair[] keyNamePairs =  DB.getKeyNamePairs("Select BSCA_ProductValue_ID,M_Product_ID  from BSCA_ProductValue where value = '"+value+"'"+ " and isActive = 'Y'",false);
		if (keyNamePairs.length>0)
			return keyNamePairs[0];
		else 
			return null;
	}
	
	protected boolean isProductPriceList (int M_Product_ID, BigDecimal Qty, MOrder order){
		MProductPricing m_productPrice = new MProductPricing (M_Product_ID, 
			C_BPartner_ID, Qty, true);
		m_productPrice.setM_PriceList_ID(order.getM_PriceList_ID());
		m_productPrice.setPriceDate(order.getDateOrdered());
		m_productPrice.calculatePrice();
		return m_productPrice.isCalculated();
	}
	
	private BigDecimal getPurchaseAmt(int M_Product_ID, Timestamp dateOrdered, int AD_Org_ID){
		
		String sql = "select  COALESCE(PriceLastInv,0) FROM BSCA_PriceChange where DOCStatus = 'CO' and M_Product_ID =  "+ M_Product_ID+
				" and isActive = 'Y' and to_timestamp(processedOn/1000) <= '"+dateOrdered+"' and AD_Org_ID = "+AD_Org_ID+" order by processedOn desc"+LIMIT_1;
		String PriceLastInv = DB.getSQLValueStringEx(trxName,sql );
		return new BigDecimal(PriceLastInv==null?"0":PriceLastInv);
	}
	
	private X_C_POSTenderType getPOSTenderType(String value, String c_codmoneda){
		return new Query(Env.getCtx(),X_C_POSTenderType.Table_Name,"C_POSTenderType.Value = ? and C_Currency.POSCodeCurrency = ?",trxName).
				addJoinClause("JOIN C_Currency ON C_Currency.C_Currency_ID = C_POSTenderType.C_Currency_ID").
				setOnlyActiveRecords(true).
				setParameters(value, c_codmoneda).first();
	}
	
	private Integer getLCO_TaxPayerType(String rif) {
		if (rif!=null && !rif.equals("")){
			String type = rif.substring(0,1);
			if (type.equals("J"))
				return 6000007;
			else
				return 6000006; 	
		} 
		return null;
	}
	
	private Integer getLCO_TaxIdType_IDByName(String name) {
		return new Query(Env.getCtx(),"LCO_TaxIdType","Name = ?",trxName).setOnlyActiveRecords(true).
				setParameters(name).firstId();
	}
	
	private int getBParnert_ID(String bPartnerValue){	
		String whereOrg = "";
		if (AD_OrgBPartner_ID !=0)
			whereOrg = " and AD_Org_ID = "+AD_OrgBPartner_ID+ " ";
		String sql = "Select C_BPartner_ID from C_BPartner where TaxID = '"+bPartnerValue+"' "+whereOrg+" and isActive = 'Y'"  +LIMIT_1;
		return DB.getSQLValueEx(trxName, sql);

	}
	
	private boolean isBPartnerLocation(MBPartner bp){
		
		int C_BPartner_Location_ID = 0;
		
		MBPartnerLocation[] locs = bp.getLocations(false);
		if (locs != null){
			for (int i = 0; i < locs.length; i++){
				if (locs[i].isShipTo())
					C_BPartner_Location_ID = locs[i].getC_BPartner_Location_ID();
				if (locs[i].isBillTo())
					C_BPartner_Location_ID = locs[i].getC_BPartner_Location_ID();
			}
			//	set to first
			if (C_BPartner_Location_ID == 0 && locs.length > 0)
				C_BPartner_Location_ID = locs[0].getC_BPartner_Location_ID();
		}
		return C_BPartner_Location_ID!=0;
	}
	
	private Integer getC_Order_ID(String documentNo,int AD_Org_ID) {
		return DB.getSQLValueEx(trxName, "Select C_Order_ID from C_Order where BSCA_StellarDocumento = '"+documentNo+"' AND DocStatus IN ('DR','IP','CO') "+ 
	" and isActive = 'Y' AND AD_Org_ID="+AD_Org_ID+LIMIT_1);
	}
	
}