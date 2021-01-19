package com.bucaresystems.fasterysync.event;

import org.adempiere.base.event.IEventTopics;
import org.compiere.model.PO;
import org.compiere.util.DB;

import com.bucaresystems.fasterysync.base.CustomEvent;

public class BSCA_ERoute extends CustomEvent{

	@Override
	protected void doHandleEvent() {
		PO po = getPO();
		if (getEventType().equals(IEventTopics.PO_BEFORE_DELETE)) {
			int BSCA_Route_ID = po.get_ID();
			
			String sql = "update pos.bsca_paymentinstapago set bsca_isimported = false \n" + 
					" where id in (select fasteryid from t_bsca_closevposline where bsca_route_id  = "+BSCA_Route_ID+")";
			DB.executeUpdateEx(sql, po.get_TrxName());
			
		}
		
	}

}
