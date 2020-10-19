package com.bucaresystems.fasterysync.model;

import java.util.List;

import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class BSCA_Products_Value extends Products_value{

	public BSCA_Products_Value(String trxName, Object idempiere_ID) {
		super(trxName, idempiere_ID);
	}

	
	public void saveFromOrg(int M_Product_ID,int AD_Org_ID){
		
		 String sql = "select Count(M_Product_ID) from Products where M_Product_ID='"+M_Product_ID+"' and AD_Org_ID = "+AD_Org_ID;
		 int l = DB.getSQLValueEx( trxName, sql);
		 
		if (l==0)
			return;
			
	     List<PO> lstBSCA_ProductValue = new Query(Env.getCtx(), "BSCA_ProductValue", "M_Product_ID = ?", trxName).setParameters(M_Product_ID).setOnlyActiveRecords(true).list(); 
		
		 for (PO po : lstBSCA_ProductValue) {
			 int BSCA_ProductValue_ID = po.get_ID();
			 String ID = BSCA_ProductValue_ID +""+ M_Product_ID+""+AD_Org_ID+"";
			 sql = "";	 
			 if (isRegisterProducts_Value(BSCA_ProductValue_ID, AD_Org_ID)){
				 update("and a.id = '"+ID+"'");				 
			 }else{
				 save("and ID='"+ID+"'");
			 }

		}
	}
	
	private boolean isRegisterProducts_Value(int BSCA_ProductValue_ID,int AD_Org_ID) {
		String sql = "select count(ID) from Products_Value where ID2='"+BSCA_ProductValue_ID+"' and AD_Org_ID = "+AD_Org_ID;
		int l = DB.getSQLValueEx(trxName, sql);
		return l>0;
	} 
}
