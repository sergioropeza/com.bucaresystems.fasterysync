package com.bucaresystems.fasterysync.event;

import com.bucaresystems.fasterysync.base.CustomEvent;
import com.bucaresystems.fasterysync.pos.model.People;

public class BSCA_EUser extends CustomEvent{

	private String trxName;

	@Override
	protected void doHandleEvent() {
		trxName = getPO().get_TrxName();
		
		People people = new People(trxName, getPO().get_ID());
		people.save();		
	}

}
