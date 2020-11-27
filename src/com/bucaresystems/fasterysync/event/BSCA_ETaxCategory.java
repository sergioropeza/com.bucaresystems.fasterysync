package com.bucaresystems.fasterysync.event;

import com.bucaresystems.fasterysync.base.CustomEvent;
import com.bucaresystems.fasterysync.pos.model.Taxcategories;

public class BSCA_ETaxCategory extends CustomEvent{

	@Override
	protected void doHandleEvent() {
		Taxcategories taxcategories = new Taxcategories(getPO().get_TrxName(), getPO().get_ID());
		taxcategories.save();	
	}

}
