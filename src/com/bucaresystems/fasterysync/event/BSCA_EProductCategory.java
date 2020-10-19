package com.bucaresystems.fasterysync.event;

import com.bucaresystems.fasterysync.base.CustomEvent;
import com.bucaresystems.fasterysync.model.Categories;

public class BSCA_EProductCategory extends CustomEvent{

	private String trxName; 
	
	@Override
	protected void doHandleEvent() {
		trxName = getPO().get_TrxName();
		
		Categories categories = new Categories(trxName, getPO().get_ID());
		categories.save();
	}

}
