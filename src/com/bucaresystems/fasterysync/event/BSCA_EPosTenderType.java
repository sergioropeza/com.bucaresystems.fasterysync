package com.bucaresystems.fasterysync.event;

import com.bucaresystems.fasterysync.base.CustomEvent;
import com.bucaresystems.fasterysync.pos.model.Postendertype;

public class BSCA_EPosTenderType extends CustomEvent{

	@Override
	protected void doHandleEvent() {
		Postendertype tenderType= new Postendertype(getPO().get_TrxName(), getPO().get_ID());
		tenderType.save();		
	}

}
