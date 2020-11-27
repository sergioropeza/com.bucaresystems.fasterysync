package com.bucaresystems.fasterysync.event;

import java.util.List;

import org.adempiere.base.event.IEventTopics;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.Env;

import com.bucaresystems.fasterysync.base.CustomEvent;
import com.bucaresystems.fasterysync.pos.model.BSCA_Products_Value;

public class BSCA_EProductValue extends CustomEvent{

	@Override
	protected void doHandleEvent() {
		
		int M_Product_ID = getPO().get_ValueAsInt("M_Product_ID");
		int BSCA_ProductValue_ID = getPO().get_ID();
		
		if (getEventType().equals(IEventTopics.PO_AFTER_NEW)|| getEventType().equals(IEventTopics.PO_AFTER_CHANGE)){
			
			List<PO> lstProductOrg = new Query(Env.getCtx(), "BSCA_ProductOrg", "M_Product_ID= ?", getPO().get_TrxName()).
					setParameters(M_Product_ID).
					setOnlyActiveRecords(true).list();
			for (PO productOrg : lstProductOrg) {
				BSCA_Products_Value products_value = new BSCA_Products_Value(getPO().get_TrxName(), BSCA_ProductValue_ID, productOrg.getAD_Org_ID());
				products_value.setM_product_id(M_Product_ID);
				if (products_value.isPriceRegister()){ // Producto, Organización, Precio y Código
					products_value.save();
				}
			}
		}	
	}

}
