package com.bucaresystems.fasterysync.process;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;

import org.compiere.db.Database;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MOrg;
import org.compiere.model.MPriceList;
import org.compiere.model.MProduct;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.model.X_C_POSPayment;
import org.compiere.model.X_C_POSTenderType;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import com.bucaresystems.fasterysync.base.CustomProcess;
import com.bucaresystems.fasterysync.pos.model.BCSA_Customers;
import com.bucaresystems.fasterysync.pos.model.BSCA_ClosedCash;
import com.bucaresystems.fasterysync.pos.model.BSCA_Payments;
import com.bucaresystems.fasterysync.pos.model.BSCA_Tax;
import com.bucaresystems.fasterysync.pos.model.BSCA_TickeLines;
import com.bucaresystems.fasterysync.pos.model.BSCA_Tickets;


public class BSCA_ImportDetaillSales extends CustomProcess{

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

	private int M_Warehouse_ID;
	protected Integer p_LIMIT;
	private Integer AD_Org_ID;
	private String c_sucursal;
	

	@Override
	protected void prepare() {
		
		
	}

	@Override
	protected String doIt() throws Exception {
		importOrders();
	
		return null;
	}
	
	protected String importOrders(){
		if (trxName==null){
			trxName = get_TrxName();	
			trx = Trx.get(trxName, false);
		}
	  c_sucursal = (new MOrg(Env.getCtx(), AD_OrgOrder_ID, null)).getValue();
		
		if (!validateSucursal(c_sucursal))
			return "";
		UpdateBSCA_Route(c_sucursal);
		importOrderDetaill();
		
		return "";
		
	}
	

	private void importOrderDetaill(){
		
		List<PO> lstBSCA_Route = new Query(getCtx(), "BSCA_Route", " C_DocTypeTarget_ID =?  and BSCA_Route_UU IN ("
				+ "select distinct money from receipts where bsca_Isimported = false)" , trxName).
				setParameters(C_DocTypeLot_ID).setOrderBy("StartDate, AD_Org_ID,C_BankAccount_ID").list();
		for (PO route : lstBSCA_Route) {
			int seq = 0;
			Savepoint savePoint = null;
			
				int BSCA_Route_ID = route.get_ID();
				String DocumentNo = route.get_ValueAsString("DocumentNo");
				int C_BankAccount_ID = route.get_ValueAsInt("C_BankAccount_ID");
				List<BSCA_Tickets> lstTickets = BSCA_Tickets.getTicketsNotSummaryNotImported(route.get_ValueAsString("BSCA_Route_UU"),c_sucursal);
	
		forTicket:for (BSCA_Tickets bsca_Tickets : lstTickets) {
				try{
					seq+=10;
					savePoint = trx.setSavepoint(null);
					seq+=10;
					

					String bPartnerValue = bsca_Tickets.getTaxid();
					Boolean isValidRif = true;
					Integer taxIdType=0;
					
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
					if (isValidRif){ 
						Integer bpartner_ID = getBParnert_ID(bPartnerValue);
						String nameBPartner = bsca_Tickets.getCustomerName();
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
						}
	
					}else{
						C_BPartner_ID =  p_C_BPartnerDefault_ID;
					}
					
					MOrder order = new MOrder(Env.getCtx(), 0, trxName);
					order.setAD_Org_ID(AD_Org_ID);
					order.setC_BPartner_ID(C_BPartner_ID);
					order.setIsSOTrx(true);
					order.setM_Warehouse_ID(M_Warehouse_ID);
					order.setC_DocTypeTarget_ID(C_DocTypeTarget_ID);
					order.setDocumentNo(DocumentNo+bsca_Tickets.getTicketid());
					order.saveEx();	
					order.setM_PriceList_ID(M_PriceList_ID);
					order.setPaymentRule(PaymentRule);
					order.set_ValueNoCheck("BSCA_Route_ID", BSCA_Route_ID);
					order.setPaymentRule("M");
	
					order.setC_PaymentTerm_ID(C_PaymentTerm_ID);
					order.setDateOrdered(bsca_Tickets.getDate());
					order.set_ValueOfColumn("POSDate", bsca_Tickets.getDate());
					if (isDateStellar)
						order.setDateAcct(bsca_Tickets.getDate());
					
					order.setDocAction(DocAction.ACTION_Complete);
					
	
					order.set_ValueOfColumn("BSCA_SeqNo", seq);
					order.saveEx();	
					log.warning("Tercero: "+bPartnerValue);	
					
					MPriceList priceList = new MPriceList(Env.getCtx(), M_PriceList_ID, trxName);
					List<BSCA_TickeLines> lstTicketsLine = bsca_Tickets.getTicketLines();
					for (BSCA_TickeLines bsca_TickeLines : lstTicketsLine) {
						String codeProduct = bsca_TickeLines.getProductCode();
						int M_Product_ID = DB.getSQLValueEx(trxName, "select M_Product_ID from M_product where sku = ?", codeProduct);
						int C_Tax_ID = DB.getSQLValueEx(trxName, "select C_Tax_ID from C_Tax where C_Tax_ID = "+ bsca_TickeLines.getTaxid());
						BigDecimal Qty = new BigDecimal(bsca_TickeLines.getUnits()).setScale(priceList.getPricePrecision(),  RoundingMode.HALF_UP);
						BigDecimal price = new BigDecimal(bsca_TickeLines.getPrice()).setScale(priceList.getPricePrecision(),  RoundingMode.HALF_UP);
						
						MProduct mProduct = new MProduct(getCtx(), M_Product_ID, trxName);
						
						BSCA_Tax tax = new BSCA_Tax(getCtx(), C_Tax_ID, trxName);
	
						BigDecimal PurchaseAmt = getPurchaseAmt(M_Product_ID,  bsca_Tickets.getDate(),AD_Org_ID);
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
						orderLine.setM_Product_ID(M_Product_ID,true);	
						orderLine.setC_Tax_ID(C_Tax_ID);
						orderLine.set_ValueOfColumn("BSCA_Route_ID", BSCA_Route_ID);
						orderLine.set_ValueOfColumn("PurchaseAmt", PurchaseAmt); 
						orderLine.set_ValueOfColumn("ProfitAmt", Price.subtract(PurchaseAmt));
						orderLine.saveEx();
					}

					BigDecimal totalPOSPayments = Env.ZERO; 
					List<BSCA_Payments> lstPayments =bsca_Tickets.getPaymentLines();
					for (BSCA_Payments bsca_Payments : lstPayments) {
						String valueTenderType =bsca_Payments.getPayment();
						String total =bsca_Payments.getTotal();

						BigDecimal total2 = new BigDecimal(total);
						BigDecimal PayAmt =  total2.setScale(priceList.getPricePrecision(), RoundingMode.HALF_UP);

						X_C_POSTenderType tenderType = getPOSTenderType(valueTenderType);

						
						if (tenderType!=null){
							X_C_POSPayment posPayment = new X_C_POSPayment(getCtx(), 0, trxName);
							posPayment.setC_POSTenderType_ID(tenderType.get_ID());
							posPayment.setTenderType(tenderType.getTenderType());
							posPayment.setPayAmt(PayAmt);
							posPayment.setC_Order_ID(order.get_ID());
							posPayment.setAD_Org_ID(order.getAD_Org_ID());
							posPayment.set_ValueOfColumn("C_BankAccount_ID", C_BankAccount_ID);
							posPayment.setIsPostDated(tenderType.isPostDated());
							posPayment.saveEx();
							
							totalPOSPayments = totalPOSPayments.add(PayAmt);
						}else{
							log.severe("No hay un registro en POSTenderType con codigo = "+valueTenderType);
							trx.rollback(savePoint);
							continue forTicket;
						}
					}
					BigDecimal granTotal = DB.getSQLValueBDEx(order.get_TrxName(),"Select GrandTotal from C_Order where C_Order_ID= " +order.get_ID());
																			
					if (totalPOSPayments.compareTo(granTotal) != 0){
							BigDecimal diff = totalPOSPayments.subtract(granTotal);
							MOrderLine orderLine = new MOrderLine(order);
							orderLine.setQty(Env.ONE);
							orderLine.setPrice(diff.setScale(priceList.getPricePrecision(),  RoundingMode.HALF_UP)); 
							orderLine.setC_Charge_ID(C_Charge_ID);	
							orderLine.set_ValueOfColumn("BSCA_Route_ID", BSCA_Route_ID);
							orderLine.saveEx();
					}
					
					
					DB.executeUpdateEx("update receipts set bsca_isImported = true where id = '"+bsca_Tickets.getId()+"'", trxName);
				}catch (Exception e){
					addLog(e.getMessage());
					log.severe("ERROR: ocurrio una excepción: "+e.getMessage());
					try {
						trx.rollback(savePoint);
						
					} catch (SQLException e1) {
						log.severe("ERROR: ocurrio una excepción en RollBack: "+e1.getMessage());
					} finally {
						if (savePoint != null) {
							try {
								trx.releaseSavepoint(savePoint);
								continue forTicket;
							} catch (SQLException e2) {
								log.severe("ERROR: ocurrio una excepción en Finally savePoint: "+e2.getMessage());
							}
						}
					}
				}
				trx.commit();
				savePoint = null;
		}
 			
			
	}
			
		
	}
	
	private X_C_POSTenderType getPOSTenderType(String valueTenderType) {
		return new Query(Env.getCtx(),X_C_POSTenderType.Table_Name,"C_POSTenderType.Name = ?",trxName).
				setOnlyActiveRecords(true).
				setParameters(valueTenderType).first();
	
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

			List<BSCA_ClosedCash> lstClosedCash = BSCA_ClosedCash.getListClosedCash(c_sucursal);
			
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
	
	private int getAD_Org_ID(String c_localidad) {
		return DB.getSQLValueEx(trxName, "Select AD_Org_ID from AD_Org where value = '"+c_localidad +"'");
	}
	
	private int getM_WarehouseOrg_ID(Integer AD_Org_ID) {
		return DB.getSQLValueEx(trxName, "select M_Warehouse_ID FROM AD_OrgInfo where AD_Org_ID = "+AD_Org_ID);
	}

	private Integer getCaja(String c_caja, Integer AD_Org_ID) {
		return DB.getSQLValueEx(trxName, "Select C_BankAccount_ID from C_BankAccount where value = '"+c_caja+"' and AD_Org_ID = "+AD_Org_ID + " and isActive = 'Y'");
	}
	
	private Integer getBSCA_Route_ID() {
		return DB.getNextID(Env.getAD_Client_ID(getCtx()), "BSCA_Route", trxName);
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
	
	private BigDecimal getPurchaseAmt(int M_Product_ID, Timestamp dateOrdered, int AD_Org_ID){
		
		String sql = "select  COALESCE(PriceLastInv,0) FROM BSCA_PriceChange where DOCStatus = 'CO' and M_Product_ID =  "+ M_Product_ID+
				" and isActive = 'Y' and to_timestamp(processedOn/1000) <= '"+dateOrdered+"' and AD_Org_ID = "+AD_Org_ID+" order by processedOn desc"+LIMIT_1;
		String PriceLastInv = DB.getSQLValueStringEx(trxName,sql );
		return new BigDecimal(PriceLastInv==null?"0":PriceLastInv);
	}
}
