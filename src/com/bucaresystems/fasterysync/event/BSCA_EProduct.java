package com.bucaresystems.fasterysync.event;

import java.util.List;

import org.adempiere.base.event.IEventTopics;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.Env;

import com.bucaresystems.fasterysync.base.CustomEvent;
import com.bucaresystems.fasterysync.model.BSCA_Products;
import com.bucaresystems.fasterysync.model.BSCA_Products_Value;
import com.bucaresystems.fasterysync.model.Products;

public class BSCA_EProduct extends CustomEvent{

	@Override
	protected void doHandleEvent() {
		String trxName = getPO().get_TrxName();
		int M_Product_ID = getPO().get_ValueAsInt("M_Product_ID");
		Products products  = new Products(trxName, M_Product_ID);
		
		int i= 1;
		if (getEventType().equals(IEventTopics.PO_AFTER_NEW)|| getEventType().equals(IEventTopics.PO_AFTER_CHANGE)){
			List<PO> lstProductOrg = new Query(Env.getCtx(), "BSCA_ProductOrg", "M_Product_ID= ?", trxName).
					setParameters(M_Product_ID).
					setOnlyActiveRecords(true).list();
				for (PO productOrg : lstProductOrg) {
				
					if (BSCA_Products.isAllRegister(trxName,M_Product_ID, productOrg.getAD_Org_ID())){ // Producto, Organización, Precio y Código
						if (i==1) {
							products.save();
						}
						BSCA_Products_Value productValue = new BSCA_Products_Value(trxName, 1);
						productValue.saveFromOrg(M_Product_ID,productOrg.getAD_Org_ID());
						i++;
					}
			}
			
		}
		
	}

}
