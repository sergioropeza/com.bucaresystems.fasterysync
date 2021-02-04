package com.bucaresystems.fasterysync.process;

import java.util.List;

import org.compiere.model.MOrg;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.util.Env;
import org.compiere.util.Trx;

public class BSCA_ImportAllSales extends BSCA_ImportSummarySales{
	private int p_AD_Org_ID;
	private List<PO> lstPara;
	protected Integer p_LIMIT;
	
	
	@Override
	protected void prepare() {
	
		ProcessInfoParameter[] para = getParameter();	
		for (int i = 0; i < para.length; i++) {
			if (para[i].getParameterName().equals("AD_Org_ID")){
				p_AD_Org_ID = para[i].getParameterAsInt();
			}
		}
		loadPaymentVPOSConfig();
	}
	
	private void loadParametersOrg(int AD_Org_ID){
		
		lstPara = new Query(Env.getCtx(), "BSCA_SyncInvoice_Para", "AD_Org_ID = ?" , trxName)
		.setOnlyActiveRecords(true).setParameters(AD_Org_ID).list();
		for (PO para : lstPara) {
	
			if (para.get_ValueAsString("Name").equals("AD_OrgBPartner_ID")){
				AD_OrgBPartner_ID = para.get_ValueAsInt("Value");
			}else if (para.get_ValueAsString("Name").equals("C_DocTypeTarget_ID")){
				C_DocTypeTarget_ID = para.get_ValueAsInt("Value");
			}else if (para.get_ValueAsString("Name").equals("C_DocTypeTargetNC_ID")){
				C_DocTypeTargetNC_ID = para.get_ValueAsInt("Value");
			}else if (para.get_ValueAsString("Name").equals("M_Warehouse_ID")){
	//			M_Warehouse_ID = para.get_ValueAsInt("Value");
			}else if (para.get_ValueAsString("Name").equals("M_PriceList_ID")){
				M_PriceList_ID = para.get_ValueAsInt("Value");
			}else if (para.get_ValueAsString("Name").equals("C_PaymentTerm_ID")){
				C_PaymentTerm_ID = para.get_ValueAsInt("Value");
			}else if (para.get_ValueAsString("Name").equals("C_BP_Group_ID")){
				C_BP_Group_ID = para.get_ValueAsInt("Value");
			}else if(para.get_ValueAsString("Name").equals("M_PriceListBPartner_ID")){
				M_PriceListBPartner_ID = para.get_ValueAsInt("Value");
			}else if(para.get_ValueAsString("Name").equals("C_DocTypeLot_ID")){
				C_DocTypeLot_ID = para.get_ValueAsInt("Value");
			}else if(para.get_ValueAsString("Name").equals("PaymentRule")){
				PaymentRule =para.get_ValueAsString("Value");
			}else if(para.get_ValueAsString("Name").equals("M_Product_ID")){
				p_M_Product_ID = para.get_ValueAsInt("Value");
			}else if(para.get_ValueAsString("Name").equals("C_Charge_ID")){
				C_Charge_ID = para.get_ValueAsInt("Value");
			}else if(para.get_ValueAsString("Name").equals("isDateFastery")){
				isDateStellar =para.get_ValueAsString("Value").equals("Y")?true:false;
			}else if(para.get_ValueAsString("Name").equals("C_BPartnerDefault_ID")){
				p_C_BPartnerDefault_ID = para.get_ValueAsInt("Value");
			}else if(para.get_ValueAsString("Name").equals("isQtyNegate")){
				isQtyNegate = para.get_ValueAsString("Value").equals("Y")?true:false;
			}else if(para.get_ValueAsString("Name").equals("C_TaxCategory_ID")){
				p_C_TaxCategory_ID = para.get_ValueAsInt("Value");
			}else if(para.get_ValueAsString("Name").equals("C_BankAccount_ID")){
				p_C_BankAccount_ID = para.get_ValueAsInt("Value");
			}else if(para.get_ValueAsString("Name").equals("LimitSQL")){
				int limit=para.get_ValueAsInt("Value"); 
				p_LIMIT = limit==0?null:limit;
			}
		}
	}
	
	@Override
	protected String doIt() throws Exception {
		trxName = get_TrxName();	
		trx = Trx.get(trxName, false);
		StringBuilder sbMjs = new StringBuilder();
		String whereOrg = "";
		if (p_AD_Org_ID>0)
			whereOrg = " AND AD_Org.AD_Org_ID ="+p_AD_Org_ID;
	
		List<MOrg> org = new Query(Env.getCtx(),MOrg.Table_Name, "AD_OrgInfo.BSCA_isSyncImportInvoice = 'Y'"+whereOrg, trxName)
		.addJoinClause("JOIN AD_OrgInfo ON AD_OrgInfo.AD_Org_ID = AD_Org.AD_Org_ID ")
		.list();
		for (MOrg mOrg : org) {
			AD_Org_ID = mOrg.get_ID();
			loadParametersOrg(AD_Org_ID);
			String msj = "";
			if(!lstPara.isEmpty())
				msj = importOrders();
			sbMjs.append(msj);
		}
		createPaymentVPOS();
		
		return sbMjs.toString();
	}
	
}