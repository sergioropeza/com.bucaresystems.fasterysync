package com.bucaresystems.fasterysync.event;

import com.bucaresystems.fasterysync.base.CustomEvent;
import com.bucaresystems.fasterysync.model.Taxes;

public class BSCA_ETax extends CustomEvent{

	@Override
	protected void doHandleEvent() {
		Taxes taxes= new Taxes(getPO().get_TrxName(), getPO().get_ID());
		taxes.save();		
	}

}
