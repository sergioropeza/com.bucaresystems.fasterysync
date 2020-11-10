package com.bucaresystems.fasterysync.event;

import org.adempiere.base.event.IEventTopics;
import org.compiere.model.PO;
import org.compiere.util.DB;

import com.bucaresystems.fasterysync.base.CustomEvent;

public class BSCA_EConversionRate extends CustomEvent{

	@Override
	protected void doHandleEvent() {
		PO po = getPO();
		if (getEventType().equals(IEventTopics.PO_AFTER_NEW)|| getEventType().equals(IEventTopics.PO_AFTER_CHANGE)){
			Object MultiplyRate = po.get_Value("MultiplyRate");
			int C_Currency_ID = po.get_ValueAsInt("C_CurrencyTo_ID");
			
			DB.executeUpdateEx("update bsca_currency set multiplyrate = "+MultiplyRate+" where idempiere_ID = "+C_Currency_ID, po.get_TrxName());
		}
	}

}
