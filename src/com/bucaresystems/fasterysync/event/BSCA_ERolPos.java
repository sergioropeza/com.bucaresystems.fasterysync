package com.bucaresystems.fasterysync.event;

import com.bucaresystems.fasterysync.base.CustomEvent;
import com.bucaresystems.fasterysync.model.Roles;

public class BSCA_ERolPos  extends CustomEvent{

	private String trxName;

	@Override
	protected void doHandleEvent() {
		trxName = getPO().get_TrxName();	
		Roles roles = new Roles(trxName,  getPO().get_ID());
		roles.save();

		
	}

}
