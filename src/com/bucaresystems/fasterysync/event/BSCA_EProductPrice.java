package com.bucaresystems.fasterysync.event;

import org.adempiere.base.event.IEventTopics;

import com.bucaresystems.fasterysync.base.CustomEvent;
import com.bucaresystems.fasterysync.model.BSCA_Products;

public class BSCA_EProductPrice extends CustomEvent{

	@Override
	protected void doHandleEvent() {
		int M_Product_ID = getPO().get_ValueAsInt("M_Product_ID");
		int AD_Org_ID = getPO().getAD_Org_ID();
		
		if (getEventType().equals(IEventTopics.PO_AFTER_NEW)|| getEventType().equals(IEventTopics.PO_AFTER_CHANGE)){

			BSCA_Products products  = new BSCA_Products(getPO().get_TrxName(), M_Product_ID,AD_Org_ID);
			if (products.isAllRegister()){ // Producto, Organización, Precio y Código
				products.save();
				products.saveProductsValue();
			}
			
		}
	}

}
