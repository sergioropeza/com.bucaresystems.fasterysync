package com.bucaresystems.model;

import org.compiere.util.DB;

public class BSCA_Products extends Products{

	public BSCA_Products(String trxName, Object idempiere_ID) {
		super(trxName, idempiere_ID);
	}
	
	public static boolean isAllRegister(String trxName,int M_Product_ID, int AD_Org_ID){
		
		int i = DB.getSQLValueEx( trxName, "select BSCA_ProductValue_ID FROM BSCA_ProductValue where M_Product_ID = "+M_Product_ID +" and isActive = 'Y'");
		if (i<=0)
			return false;

		i = DB.getSQLValueEx( trxName, "select BSCA_ProductOrg_ID FROM BSCA_ProductOrg where M_Product_ID = "+M_Product_ID+" and AD_Org_ID = "+AD_Org_ID+"and isActive = 'Y'");
		if (i<=0) 
			return false;
		
		i = DB.getSQLValueEx( trxName, "select M_ProductPrice_ID FROM M_ProductPrice where M_Product_ID = "+M_Product_ID+" and AD_Org_ID = "+AD_Org_ID+"and isActive = 'Y'");
		if (i<=0) 
			return false;

		return true;
		
	}

}
