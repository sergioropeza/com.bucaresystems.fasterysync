package com.bucaresystems.fasterysync.event;

import org.adempiere.base.event.IEventTopics;
import org.compiere.model.PO;
import org.compiere.util.DB;

import com.bucaresystems.fasterysync.base.CustomEvent;

public class BSCA_ECloseVPosline extends CustomEvent{

	@Override
	protected void doHandleEvent() {
		PO po = getPO();
		if (getEventType().equals(IEventTopics.PO_BEFORE_DELETE)) {
			String instaPago_ID = po.get_ValueAsString("fasteryID");
			String sql = "update pos.bsca_paymentinstapago set bsca_isimported  = false where id = '"+instaPago_ID+"'";
			DB.executeUpdateEx(sql, po.get_TrxName());		
		}
		
	}

}
