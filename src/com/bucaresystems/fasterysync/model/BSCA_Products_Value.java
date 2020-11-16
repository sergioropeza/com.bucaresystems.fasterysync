package com.bucaresystems.fasterysync.model;

import org.compiere.util.DB;

public class BSCA_Products_Value extends Products_value{

	public BSCA_Products_Value(String trxName, Object idempiere_ID, Object AD_Org_ID) {
		super(trxName, idempiere_ID);
		this.ad_org_id = AD_Org_ID;
	}

	
	public  boolean isPriceRegister(){	

		int i = DB.getSQLValueEx( trxName, "select M_ProductPrice_ID FROM M_ProductPrice where M_Product_ID = "+m_product_id+" and AD_Org_ID = "+ad_org_id+"and isActive = 'Y'");
		if (i<=0) 
			return false;
		return true;	
	}
	
	private boolean isRegister() {

		String sql = "select idempiere_ID from "+Products_value.Table_Name+" where idempiere_ID='"+idempiere_id+"' and AD_Org_ID = "+ad_org_id;
		int l = DB.getSQLValueEx( trxName, sql);
		return l>0;
	}
	
	@Override
	public void save(){
		String sql = "select Count(idempiere_ID) from "+Products.Table_Name+" where idempiere_ID='"+m_product_id+"' and AD_Org_ID = "+ad_org_id;
		int l = DB.getSQLValueEx( trxName, sql);
		 
		if (l==0)
			return;
		
		if (!isRegister()){
			save("and idempiere_ID = "+idempiere_id+ " and AD_Org_ID ="+ad_org_id);
		}else{
			update("and b.idempiere_ID = "+idempiere_id+ " and b.AD_Org_ID ="+ad_org_id);
		}
	}
}
