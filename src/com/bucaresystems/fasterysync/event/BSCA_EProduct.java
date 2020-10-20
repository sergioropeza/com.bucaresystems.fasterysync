package com.bucaresystems.fasterysync.event;

import java.util.List;

import org.adempiere.base.event.IEventTopics;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.Env;

import com.bucaresystems.fasterysync.base.CustomEvent;
import com.bucaresystems.fasterysync.model.BSCA_Products;

public class BSCA_EProduct extends CustomEvent{

	@Override
	protected void doHandleEvent() {
		String trxName = getPO().get_TrxName();
		int M_Product_ID = getPO().get_ValueAsInt("M_Product_ID");		
		
		if (getEventType().equals(IEventTopics.PO_AFTER_NEW)|| getEventType().equals(IEventTopics.PO_AFTER_CHANGE)){
			List<PO> lstProductOrg = new Query(Env.getCtx(), "BSCA_ProductOrg", "M_Product_ID= ?", trxName).
					setParameters(M_Product_ID).
					setOnlyActiveRecords(true).list();
				for (PO productOrg : lstProductOrg) {
					BSCA_Products products  = new BSCA_Products(trxName, M_Product_ID,productOrg.getAD_Org_ID());
					if (products.isAllRegister()){ // Producto, Organización, Precio y Código
						products.save();
					}
			}		
		}
		
	}

}
