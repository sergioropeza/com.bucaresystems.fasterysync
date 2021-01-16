package com.bucaresystems.fasterysync.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.db.Database;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MBankAccount;
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
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Trx;

import com.bucaresystems.fasterysync.base.CustomProcess;
import com.bucaresystems.fasterysync.model.X_BSCA_POSDetaill;
import com.bucaresystems.fasterysync.model.X_BSCA_POSTaxDetaill;
import com.bucaresystems.fasterysync.model.X_T_BSCA_CloseVPOSLine;
import com.bucaresystems.fasterysync.pos.model.BSCA_ClosedCash;
import com.bucaresystems.fasterysync.pos.model.BSCA_PaymentInstaPago;
import com.bucaresystems.fasterysync.pos.model.BSCA_Payments;
import com.bucaresystems.fasterysync.pos.model.BSCA_SummaryTax;
import com.bucaresystems.fasterysync.pos.model.BSCA_Tax;
import com.bucaresystems.fasterysync.pos.model.BSCA_TickeLines;
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
	protected int p_C_BPartnerDefault_ID;
	protected int p_C_TaxCategory_ID;
	protected boolean isQtyNegate;
	protected int p_C_BankAccount_ID;
	protected static String NATIVE_MARKER = "NATIVE_"+Database.DB_POSTGRESQL+"_KEYWORK";
	protected String LIMIT_1 = " "+NATIVE_MARKER + "LIMIT 1"+ NATIVE_MARKER;
	protected int AD_Org_ID;
	private int M_Warehouse_ID;
	protected Integer p_LIMIT;
	private String c_caja;
	private String c_sucursal;
	
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
		return importOrders();
	
	}
	
	protected String importOrders() {
		if (trxName==null){
			trxName = get_TrxName();	
			trx = Trx.get(trxName, false);
		}
		c_sucursal = validateSucursal(AD_Org_ID);
		if (c_sucursal ==null || c_sucursal.isEmpty())
			return "";
		UpdateBSCA_Route();

		
		String whereBankAccount = "";
//		if (p_C_BankAccount_ID!=0){
//			 String value = DB.getSQLValueStringEx(trxName, "Select value from C_BankAccount where C_BankAccount_ID=? ", p_C_BankAccount_ID);
//			 whereBankAccount = " and C_BankAccount.value = '"+value+"'";
//		}
			
		List<PO> lstBSCA_Route = new Query(getCtx(), "BSCA_Route", " C_DocTypeTarget_ID =?  and AD_Org_ID = ? and ClosedCashID IN ("
				+ "select distinct money from pos.receipts where bsca_Isimported = false and orgValue = ?) "+whereBankAccount , trxName).
				setParameters(C_DocTypeLot_ID,AD_Org_ID,c_sucursal).setOrderBy("StartDate, AD_Org_ID,C_BankAccount_ID").list();
			
		
		forRoutes: for (PO route : lstBSCA_Route) {
						int BSCA_Route_ID = route.get_ID();
						int C_BankAccount_ID = route.get_ValueAsInt("C_BankAccount_ID");
						boolean isActive = route.isActive();
						c_caja = DB.getSQLValueStringEx(trxName, "select value from C_BankAccount where C_BankAccount_ID=? ", C_BankAccount_ID);
						String DocumentNo = route.get_ValueAsString("DocumentNo");
						if (c_caja==null || DocumentNo ==null)
							continue forRoutes;
						
						String closedCash = route.get_ValueAsString("ClosedCashID");
						
			            //// VALIDA QUE TODOS LOS REGISTROS DE TICKETS SE HAYAN SINCRONIZADO /////////////////////////
						if (!isAllSales(route))
							continue forRoutes;	
						
						importOrdersNotSummary(closedCash,BSCA_Route_ID, C_BankAccount_ID, c_sucursal);
						if (isActive) 
							importOrdersSummary(closedCash,BSCA_Route_ID, C_BankAccount_ID, c_sucursal);

						////// Importa las ordernes resumidas 
						updateFieldsSumNCSumInvoiced(BSCA_Route_ID);
											
					}
		createPaymentVPOS();
	
		return null;
	}

	private void createPaymentVPOS() {
		List<BSCA_PaymentInstaPago> lstPaymentVPOS = BSCA_Tickets.getPaymentVPOSNotImported();
		for (BSCA_PaymentInstaPago instaPago : lstPaymentVPOS) {
			X_T_BSCA_CloseVPOSLine vPosLine = new X_T_BSCA_CloseVPOSLine(getCtx(), 0, trxName);
			vPosLine.setC_BankTo_ID(getC_Bank_ID(instaPago.getBank()));
			vPosLine.setTerminalValue(instaPago.getTerminal());
			vPosLine.setDateTrx(instaPago.getDatetime());
			vPosLine.setLotValue(instaPago.getLote());
			vPosLine.setSeqValue(instaPago.getSequence());
			vPosLine.setRefValue(instaPago.getReference());
			vPosLine.setCardValue(instaPago.getCardnumber());
			vPosLine.set_ValueOfColumn("BSCA_Route_ID", instaPago.getBSCA_Route_ID());
		}
		
	}
	
	private int getC_Bank_ID (String bankValue) {
		return DB.getSQLValueEx(trxName, "select C_Bank_ID C_Bank where RoutingNo = '"+bankValue+"' and isActive = 'Y'");
	}

	private String validateSucursal(int  AD_Org_ID ) {
		
		M_Warehouse_ID =getM_WarehouseOrg_ID(AD_Org_ID);
		String c_sucursal = (new MOrg(getCtx(), AD_Org_ID, trxName)).getValue();
		if(M_Warehouse_ID==-1) {
			String msj = "ERROR: La Organización "+c_sucursal +" No tiene un almacén configurado.";
			log.severe(msj);
			return "";
		}
		return c_sucursal;
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
					int ticketType = tickets.getTickettype();
					
					Integer order_ID =-1;
					
					int DocTypeTarget_ID = 0;
					if (BSCA_Tickets.RECEIPT_REFUND==ticketType){
						DocTypeTarget_ID  = C_DocTypeTargetNC_ID;
					}else if (BSCA_Tickets.RECEIPT_NORMAL==ticketType){
						DocTypeTarget_ID = C_DocTypeTarget_ID;
					}
					String documentNo = orgValue+c_caja+minDocStellar;
					
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
					order.setC_DocType_ID(DocTypeTarget_ID);
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
					
					List<BSCA_TickeLines> lstTicketLines = tickets.getListSummaryTicketsLine(closeCash_ID,ticketType); 
					MPriceList priceList = new MPriceList(Env.getCtx(), M_PriceList_ID, trxName);
	
	transaccion:for (BSCA_TickeLines ticketLine : lstTicketLines) {
						String valueProduct = ticketLine.getProductCode();
//						String BSCA_ValuePOS = ma_Transaccion.getCodigo();
						BigDecimal cantidad =  new BigDecimal(ticketLine.getUnits());
						BigDecimal price = new BigDecimal(ticketLine.getPrice()).setScale(order.getPrecision(), RoundingMode.HALF_UP);
						String  taxID = ticketLine.getTaxid();
						Timestamp POSDate = ticketLine.getPOSDate();
						int M_Product_ID = p_M_Product_ID;
						String descriptionLine = "";
						BigDecimal Qty = Env.ZERO;
						if (BSCA_Tickets.RECEIPT_REFUND==ticketType){
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
						
			
						int C_Tax_ID = Integer.parseInt(taxID);
						BSCA_Tax tax = new BSCA_Tax(getCtx(), C_Tax_ID, trxName);
						
						BigDecimal PurchaseAmt = getPurchaseAmt(M_Product_ID, POSDate,AD_Org_ID);
						BigDecimal Price = Env.ZERO;
						MOrderLine orderLine = new MOrderLine(order);
						orderLine.setQty(Qty.setScale(mProduct.getC_UOM().getStdPrecision(), RoundingMode.HALF_UP));
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
	
					if (BSCA_Tickets.RECEIPT_REFUND!=ticketType){
						// sincroniza los tipos de pagos
						List<BSCA_Payments> listPayments = tickets.getListSummaryPayments(closeCash_ID,ticketType);
						
						for (BSCA_Payments payment : listPayments) {

							String n_monto =payment.getTotal();
							BigDecimal n_monto2 = new BigDecimal(n_monto);
							BigDecimal PayAmt =  n_monto2.setScale(priceList.getPricePrecision(), RoundingMode.HALF_UP);
							BigDecimal MultiplyRate = new BigDecimal(payment.getMultiplyrate());
							int C_POSTenderType_ID = Integer.parseInt(payment.getBsca_postendertype_id());
							
							BigDecimal BSCA_QtyCurrency = Env.ZERO;
							if  (MultiplyRate!=null || !Env.ZERO.equals(MultiplyRate))
								BSCA_QtyCurrency = PayAmt.divide(MultiplyRate,2,RoundingMode.HALF_UP);
							
							if (C_POSTenderType_ID!=0){
								X_C_POSTenderType tenderType = new X_C_POSTenderType(getCtx(), C_POSTenderType_ID, trxName);
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
								log.severe("No hay un registro en POSTenderType con ID = "+C_POSTenderType_ID);
								trx.rollback(savePoint);
								continue forPagos;
							}
						}	
					}
				
					BigDecimal granTotal = DB.getSQLValueBDEx(order.get_TrxName(),"Select GrandTotal from C_Order where C_Order_ID= " +order.get_ID());
					if (BSCA_Tickets.RECEIPT_REFUND!=ticketType){
																		
						if (totalPOSPayments.compareTo(granTotal) != 0){
							BigDecimal diff = totalPOSPayments.subtract(granTotal);
							MOrderLine orderLine = new MOrderLine(order);
							orderLine.setQty(Env.ONE);
							orderLine.setPrice(diff.setScale(priceList.getPricePrecision(), RoundingMode.HALF_UP)); 
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
								posPayment.setPayAmt(granTotal.negate().setScale(priceList.getPricePrecision(), RoundingMode.HALF_UP));
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
					createPOSDetaillSummay( closeCash_ID,  orgValue,  ticketType, order,tickets.getDate());
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

	private boolean isAllSales(PO route) {
		
		String closedCashID  = route.get_ValueAsString("closedCashID");
		Timestamp dateEnd = DB.getSQLValueTS(trxName, "select dateend from pos.closedcash c where money = '"+closedCashID+"'");
		if (dateEnd ==null) {
			log.severe("turno sin fecha de cierre "+route.get_ValueAsString("DocumentNo"));
			return false;
		}
			
		String sqlSales = "select count(t.id) from pos.receipts r\n" + 
				"join pos.tickets t on t.id = r.id \n" + 
				"where t.tickettype = 0 and  money = '"+closedCashID+"'\n";
		
		String sqlRefund = "select count(t.id) from pos.receipts r\n" + 
				"join pos.tickets t on t.id = r.id \n" + 
				"where t.tickettype = 1 and money = '"+closedCashID+"'\n";
		
		int qytSalesSync = DB.getSQLValueEx(trxName, sqlSales);
		int qtyRefundSync = DB.getSQLValueEx(trxName, sqlRefund);
		
		int qtySalesRoute = route.get_ValueAsInt("QtySales");
		int qtyRefundRoute = route.get_ValueAsInt("QtyRefunds");
			
		if ((qtyRefundSync!=qtyRefundRoute) || (qtySalesRoute!=qytSalesSync)) {
			log.severe("Cantidad de registros sincronizados de "
					+ "  la tabla pos.tickets (Ventas = "+qytSalesSync+", Devolución = "+qtyRefundSync+") es distinto a la "
					+ "   cantidad registrada en el turno (Ventas = "+qtySalesRoute+", Devolución = "+qtyRefundRoute+")"
					+ ", "+route.get_ValueAsString("DocumentNo")
					+ ", closedCashID = "+route.get_ValueAsString("closedcashID"));
			return false;
		}	
		return true;
	}

	private void createPOSDetaillNotSummary( String closedcash_ID, String orgValue, int ticketType,MOrder order, Timestamp date){
		List<BSCA_Tickets> lsttickets = BSCA_Tickets.getTicketsDetaillPaySummary(closedcash_ID, orgValue, ticketType,date);
		createPOSDetaill(lsttickets,order,ticketType);
	}
	
	
	private void createPOSDetaillSummay( String closedcash_ID, String orgValue, int ticketType,MOrder order, Timestamp date){
		List<BSCA_Tickets> lstTickets = BSCA_Tickets.getTicketsDetaillNotPaySummary(closedcash_ID, orgValue, ticketType,date);
		createPOSDetaill(lstTickets,order,ticketType);
		
	}

	private void createPOSDetaill(List<BSCA_Tickets> lstTickets, MOrder order, int ticketType){
			
		lstTickets.forEach(ticket-> {	
				
				int cs_documento_rel = ticket.getTicketRefundID();
				String cu_documentofiscal = ticket.getFiscaldocumentno();
				String cu_serialimpresora = ticket.getMachinefiscalnumber();
				int c_numero = ticket.getTicketid();
				String orgValue = ticket.getOrgValue();
				String c_rif = ticket.getTaxid();
				String c_desc_cliente =ticket.getCustomerName();
				String cu_direccion_cliente = ticket.getCustomerAddress();
				
				String id= ticket.getId();
				
				BigDecimal n_impuesto =  new BigDecimal(ticket.getTaxAmt()).setScale(order.getPrecision(), RoundingMode.HALF_UP);
				BigDecimal n_total = new BigDecimal(ticket.getGrandTotal()).setScale(order.getPrecision(), RoundingMode.HALF_UP);
				Boolean  BSCA_isTaxPayer = false;
				if(c_rif!=null){
					c_rif=c_rif.toUpperCase().replaceAll("[^VEJGP0-9]", "");
					if (c_rif.matches("^[VJGP][0-9]{9}$")){ // verifica RIF
						BSCA_isTaxPayer = true;
					}
				}
				
				BigDecimal TaxAmtOrder = Env.ZERO;
				BigDecimal GrandTotal = Env.ZERO;
				if (BSCA_Tickets.RECEIPT_REFUND==ticketType){
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
				posDetaill.setBSCA_Pos_invoiceaffected(cs_documento_rel+"");
				posDetaill.setFiscalPrinterSerial(cu_serialimpresora);
				posDetaill.setBSCA_StellarDocumento(c_numero+"");
				posDetaill.setBSCA_StellarRif(c_rif);
				posDetaill.setClientName(c_desc_cliente);
				posDetaill.setLocationName(cu_direccion_cliente);
				posDetaill.setBSCA_isTaxPayer(BSCA_isTaxPayer);
				posDetaill.saveEx();	
				
				
				List<BSCA_SummaryTax> lstSummaryTax = ticket.getListSummaryTax();
				for (BSCA_SummaryTax bsca_MA_SummaryTax : lstSummaryTax) {
					
					int C_Tax_ID = Integer.parseInt(bsca_MA_SummaryTax.getTax_ID());
					MTax tax = new MTax(getCtx(), C_Tax_ID, trxName);
					BigDecimal TaxBaseAmt = new BigDecimal(bsca_MA_SummaryTax.getLineNetAmt()).setScale(order.getPrecision(), RoundingMode.HALF_UP);			
					BigDecimal TaxAmt = new BigDecimal(bsca_MA_SummaryTax.getPriceTax()).setScale(order.getPrecision(), RoundingMode.HALF_UP);
//					MTax tax = MTax.get(getCtx(), C_Tax_ID);
//					BigDecimal TaxAmt = tax.calculateTax(TaxBaseAmt, false, order.getPrecision()).setScale(order.getPrecision(), RoundingMode.HALF_UP);
					
					X_BSCA_POSTaxDetaill posTaxDetaill = new X_BSCA_POSTaxDetaill(getCtx(), 0, get_TrxName());
					posTaxDetaill.setBSCA_POSDetaill_ID(posDetaill.get_ID());
					posTaxDetaill.setAD_Org_ID(posDetaill.getAD_Org_ID());	
					posTaxDetaill.setC_Tax_ID(C_Tax_ID);
					posTaxDetaill.setTaxAmt(TaxAmt);
					posTaxDetaill.setTaxBaseAmt(TaxBaseAmt);
					posTaxDetaill.setRate(tax.getRate());
					posTaxDetaill.saveEx();
						
				}
				System.out.println(id);
				DB.executeUpdateEx("Update pos.receipts set bsca_isimported = true where id ='"+id +"' and orgValue = "+DB.TO_STRING(orgValue), trx.getTrxName());
		});	
	}
	private void importOrdersNotSummary(String closeCash_ID, int BSCA_Route_ID,int C_BankAccount_ID,String orgValue){

		int seq= 0;
		Savepoint savePoint = null;
		List<BSCA_Tickets> lstTickets = BSCA_Tickets.getTicketsNotSummaryNotImported(closeCash_ID,orgValue);	
		forPagos: for (BSCA_Tickets ticket : lstTickets) {
					
					seq+=10;
					boolean error = false;
					try{
						savePoint = trx.setSavepoint(null);
						
						String bPartnerValue = ticket.getTaxid();
						String documentNo = orgValue+ticket.getTicketid();
						String c_desc_cliente =ticket.getCustomerName();
						int ticketType =ticket.getTickettype();
						int cs_documento_rel = ticket.getTicketRefundID();
						Timestamp f_fecha = ticket.getDate();
						Timestamp POSDate = ticket.getDate();
						String id = ticket.getId();
						
						
						int DocTypeTarget_ID = BSCA_Tickets.RECEIPT_REFUND==ticketType?C_DocTypeTargetNC_ID:C_DocTypeTarget_ID;
						Integer AD_Org_ID = getAD_Org_ID(orgValue);
						Integer order_ID =-1;
						if (AD_Org_ID!=-1)
							order_ID= getC_Order_IDfromStellarDocumento(documentNo,DocTypeTarget_ID,AD_Org_ID); // verifica si la orden  está registrada
						if (order_ID!=-1){
							System.out.println(id);
							DB.executeUpdateEx("Update pos.receipts set bsca_isimported = true where id ='"+id +"' and orgValue = "+DB.TO_STRING(orgValue), trx.getTrxName());
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
							addLog("ERROR: Organización con código "+orgValue+ " no está registrado. Factura "+ documentNo +"no Importada");
							log.severe("ERROR: Organización con código "+orgValue+ " no está registrado. Factura "+ documentNo +"no Importada");
							error = true;
							trx.rollback(savePoint);
							continue forPagos;
						}else{
							M_Warehouse_ID =getM_WarehouseOrg_ID(AD_Org_ID);
							if(M_Warehouse_ID==-1) {
								addLog("ERROR: La Organización "+orgValue +" No tiene un almacén configurado. Factura "+documentNo);
								log.severe("ERROR: La Organización "+orgValue +" No tiene un almacén configurado. Factura "+documentNo);
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
						order.setDocumentNo(documentNo);
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
						
						if ( BSCA_Tickets.RECEIPT_REFUND==ticketType){
							Integer orderAffected_ID = getC_Order_ID(orgValue+cs_documento_rel,AD_Org_ID);
							if (orderAffected_ID!=-1) 
								order.set_ValueOfColumn("BSCA_OrderAffected_ID", orderAffected_ID);
						}

						order.set_ValueOfColumn("BSCA_SeqNo", seq);
						order.set_ValueOfColumn("BSCA_StellarRif", bPartnerValue);
						order.set_ValueOfColumn("BSCA_StellarName", c_desc_cliente);
						order.saveEx();	
						log.warning("Orden estelar: "+documentNo);
						log.warning("Tercero: "+bPartnerValue);
															
						createPOSDetaillNotSummary( closeCash_ID,  orgValue,  ticketType, order,ticket.getDate());
						
						//sincroniza las lineas de las ordenes 
						error = false;
						MPriceList priceList = new MPriceList(Env.getCtx(), M_PriceList_ID, trxName);
						List<BSCA_TickeLines> lstTicketsLine = ticket.getTicketLines();

						forTransaccion:for (BSCA_TickeLines ticketLine : lstTicketsLine) {
							String valueProduct = ticketLine.getProductCode();
							String BSCA_ValuePOS = ticketLine.getBsca_productValue();
							BigDecimal cantidad =  new BigDecimal(ticketLine.getUnits());
							BigDecimal price = new BigDecimal(ticketLine.getPrice()).setScale(order.getPrecision(), RoundingMode.HALF_UP);
	
							int M_Product_ID = p_M_Product_ID;
							String descriptionLine = "";
							BigDecimal Qty = Env.ZERO;
							if (BSCA_Tickets.RECEIPT_REFUND==ticketType){
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
							
							
						    int  C_Tax_ID = Integer.parseInt(ticketLine.getTaxid());
							BSCA_Tax tax = new BSCA_Tax(getCtx(), C_Tax_ID, trxName);
	
							BigDecimal PurchaseAmt = getPurchaseAmt(M_Product_ID, POSDate,AD_Org_ID);
							BigDecimal Price = Env.ZERO;
							MOrderLine orderLine = new MOrderLine(order);
							orderLine.setQty(Qty.setScale(mProduct.getC_UOM().getStdPrecision(), RoundingMode.HALF_UP));
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
						
						
						error = false;
						BigDecimal totalPOSPayments = Env.ZERO; 
						
						if (BSCA_Tickets.RECEIPT_REFUND!=ticketType){
							// sincroniza los tipos de pagos
							List<BSCA_Payments> listPayments = ticket.getPaymentLines();
							
							for (BSCA_Payments payment : listPayments) {
				
								String n_monto =payment.getTotal();
								BigDecimal n_monto2 = new BigDecimal(n_monto);
								BigDecimal PayAmt =  n_monto2.setScale(priceList.getPricePrecision(), RoundingMode.HALF_UP);
								BigDecimal MultiplyRate = new BigDecimal(payment.getMultiplyrate());
								int C_POSTenderType_ID = Integer.parseInt(payment.getBsca_postendertype_id());
								X_C_POSTenderType tenderType = new X_C_POSTenderType(getCtx(),C_POSTenderType_ID,trxName );
								BigDecimal BSCA_QtyCurrency = Env.ZERO;
								if  (MultiplyRate!=null || !Env.ZERO.equals(MultiplyRate))
									BSCA_QtyCurrency = PayAmt.divide(MultiplyRate,2,RoundingMode.HALF_UP);
																				
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
						if (BSCA_Tickets.RECEIPT_REFUND!=ticketType){
																			
							if (totalPOSPayments.compareTo(granTotal) != 0){
								BigDecimal diff = totalPOSPayments.subtract(granTotal);
								MOrderLine orderLine = new MOrderLine(order);
								orderLine.setQty(Env.ONE);
								orderLine.setPrice(diff.setScale(priceList.getPricePrecision(), RoundingMode.HALF_UP)); 
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
								posPayment.setPayAmt(granTotal.negate().setScale(priceList.getPricePrecision(), RoundingMode.HALF_UP));
								posPayment.setC_Order_ID(order.get_ID());
								posPayment.set_ValueOfColumn("C_BankAccount_ID", C_BankAccount_ID);
								posPayment.setAD_Org_ID(order.getAD_Org_ID());
								posPayment.saveEx();
							}
						}
						
						MDocType docTarget_ID = (MDocType)order.getC_DocTypeTarget();	
						System.out.println(id);
						DB.executeUpdateEx("Update pos.receipts set bsca_isimported = true where id ='"+id +"' and orgValue = "+DB.TO_STRING(orgValue), trx.getTrxName());	
						
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
	private void UpdateBSCA_Route(){
		
		String sql = null;
		ResultSet rsCaja = null;
		PreparedStatement pstmtCaja  = null;

		try{

			List<BSCA_ClosedCash> lstClosedCash = BSCA_ClosedCash.getListClosedCash(c_sucursal);
			
			for (BSCA_ClosedCash closedCash : lstClosedCash) {
				int BSCA_Route_ID = closedCash.getBSCA_Route_ID();
				String host = closedCash.getHost();
				int hostsequence = closedCash.getHostsequence();
				String EndDate = closedCash.getDateend()==null?null:"'"+closedCash.getDateend()+"'" ;
				String isActive = closedCash.getDateend()!=null? "'Y'":"'N'";
				int userPOS_ID =closedCash.getAD_User_ID();
				if (userPOS_ID==0){
					userPOS_ID = Env.getAD_User_ID(getCtx());
				}
				int qtySales = closedCash.getQtySales();
				int qtyRefunds = closedCash.getQtyRefunds();
				 
				if (BSCA_Route_ID==0){
					
					Timestamp StartDate = closedCash.getDatestart();
					int C_DocType_ID = C_DocTypeLot_ID;
					String DocStatus = "DR";
					String DeliveryViaRule = "D";
					String DocumentNo = host+hostsequence;
					Integer AD_User_ID = Env.getAD_User_ID(getCtx());
					BSCA_Route_ID = getBSCA_Route_ID();
					Integer AD_Client_ID = Env.getAD_Client_ID(getCtx());
					String BSCA_Route_UU =DB.TO_STRING(UUID.randomUUID().toString());
					String closedCashID = DB.TO_STRING(closedCash.getMoney());
					
					Integer C_BankAccount_ID = getC_BankAccount_ID(host,AD_Org_ID);				
					Timestamp DateAcct = closedCash.getDatestart();			
				
					
					String sqlInsert = "Insert into BSCA_Route (BSCA_Route_ID,BSCA_Route_UU,AD_Org_ID,AD_Client_ID, IsActive,CreatedBy,UpdatedBy,Created,Updated,"
									 + "DateAcct,StartDate,EndDate,C_DocTypeTarget_ID,DocStatus,DeliveryViaRule,DocumentNo,processed, AD_User_ID,C_BankAccount_ID,QtySales,QtyRefunds,ClosedCashID) Values "
									 +"("+BSCA_Route_ID +","+BSCA_Route_UU+","+AD_Org_ID+","+AD_Client_ID+","+isActive+","+AD_User_ID+","+AD_User_ID+
									 ",SysDate,SysDate,'"+DateAcct+"','"+StartDate+"',"+EndDate+","+C_DocType_ID+",'"+DocStatus+"','"
									 +DeliveryViaRule+"','"+DocumentNo+"','N',"+userPOS_ID+","+C_BankAccount_ID+","+qtySales+","+qtyRefunds+","+closedCashID+")";
					DB.executeUpdateEx(sqlInsert, trxName);
					addLog(0, null, null, "Ruta Registrada: "+DocumentNo,MTable.getTable_ID("BSCA_Route"), BSCA_Route_ID);
				}else{

						
					String setEndate="";
					if (EndDate!=null)
						setEndate =", EndDate = "+EndDate;
					
					String sqlUpdate = "Update BSCA_Route set isActive = "+isActive
							+ ", qtySales = "+qtySales+", qtyRefunds = "+qtyRefunds
							+" , AD_User_ID = "+userPOS_ID
							+ setEndate
							+" where BSCA_Route_ID = "+BSCA_Route_ID ;
					DB.executeUpdateEx(sqlUpdate , trxName);	
					trx.commit();
					
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
	
	
	private Integer getC_BankAccount_ID(String name, Integer AD_Org_ID) {
		int C_BankAccount_ID = DB.getSQLValueEx(trxName, "Select C_BankAccount_ID from C_BankAccount where value = '"
										+name+"' and AD_Org_ID = "+AD_Org_ID + " and isActive = 'Y' and isPosBankAccount = 'Y'");
	
		 if (C_BankAccount_ID==-1){
			 int C_Bank_ID = DB.getSQLValueEx(trxName, "select C_Bank_ID from C_Bank where isPOSBank= 'Y'");
			 if (C_Bank_ID==-1) {
				 throw new AdempiereException("No existe un banco POS (isPOSBank='Y')");
			 }else {
				MBankAccount bankAccount = new MBankAccount(getCtx(), 0, trxName); 
				bankAccount.setAD_Org_ID(AD_Org_ID);
				bankAccount.setC_Bank_ID(C_Bank_ID);
				bankAccount.setValue(name);
				bankAccount.setName(name);
				bankAccount.set_ValueOfColumn("isPosBankAccount", true);
				bankAccount.setAccountNo("000");
				bankAccount.setC_Currency_ID(205);
				bankAccount.saveEx();
				C_BankAccount_ID =bankAccount.get_ID();				
			 }
		 }
		 return C_BankAccount_ID;
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
			C_BPartner_ID, Qty, true,trxName);
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