package com.bucaresystems.fasterysync.model;

import java.util.List;

import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class BSCA_Products extends Products{

	public BSCA_Products(String trxName, Object idempiere_ID, Object AD_Org_ID) {
		super(trxName, idempiere_ID);
		this.ad_org_id = AD_Org_ID;
	}
	
	public  boolean isAllRegister(){
		
		int i = DB.getSQLValueEx( trxName, "select BSCA_ProductValue_ID FROM BSCA_ProductValue where M_Product_ID = "+idempiere_id +" and isActive = 'Y'");
		if (i<=0)
			return false;

		i = DB.getSQLValueEx( trxName, "select BSCA_ProductOrg_ID FROM BSCA_ProductOrg where M_Product_ID = "+idempiere_id+" and AD_Org_ID = "+ad_org_id+"and isActive = 'Y'");
		if (i<=0) 
			return false;
		
		i = DB.getSQLValueEx( trxName, "select M_ProductPrice_ID FROM M_ProductPrice where M_Product_ID = "+idempiere_id+" and AD_Org_ID = "+ad_org_id+"and isActive = 'Y'");
		if (i<=0) 
			return false;

		return true;
	
	}
	
	private boolean isRegister() {

		String sql = "select idempiere_ID from "+Table_Name+" where idempiere_ID='"+idempiere_id+"' and AD_Org_ID = "+ad_org_id;
		int l = DB.getSQLValueEx( trxName, sql);
		return l>0;
	}
	
	@Override
	public void save(){
		
		if (!isRegister()){
			save("and idempiere_ID = "+idempiere_id+ " and AD_Org_ID ="+ad_org_id);
		}else{
			update("and b.idempiere_ID = "+idempiere_id+ " and b.AD_Org_ID ="+ad_org_id);
		}
	}
	
	public void saveProductsValue() {
		List<PO> lstBSCA_ProductValue = new Query(Env.getCtx(), "BSCA_ProductValue", "M_Product_ID = ?", trxName)
				.setParameters(idempiere_id).setOnlyActiveRecords(true).list(); 
		for (PO productValue : lstBSCA_ProductValue) {
			BSCA_Products_Value products_value = new BSCA_Products_Value(trxName, productValue.get_ID(), ad_org_id);
			products_value.setM_product_id(idempiere_id);
			if (products_value.isPriceRegister()){ // Producto, Organización, Precio y Código
				products_value.save();
			}
		}
		
	}

}
