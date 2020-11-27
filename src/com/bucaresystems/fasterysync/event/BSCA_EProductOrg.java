package com.bucaresystems.fasterysync.event;

import org.adempiere.base.event.IEventTopics;

import com.bucaresystems.fasterysync.base.CustomEvent;
import com.bucaresystems.fasterysync.pos.model.BSCA_Products;

public class BSCA_EProductOrg extends CustomEvent{

	private String trxName;

	@Override
	protected void doHandleEvent() {
		int M_Product_ID = getPO().get_ValueAsInt("M_Product_ID");
		int AD_Org_ID = getPO().getAD_Org_ID();
		trxName = getPO().get_TrxName();
		
		if (getEventType().equals(IEventTopics.PO_AFTER_NEW)){
			BSCA_Products products  = new BSCA_Products(trxName, M_Product_ID,AD_Org_ID);
			if (products.isAllRegister()){ // Producto, Organización, Precio y Código
				products.save();
				products.saveProductsValue();
			}
		}		
	}
}
