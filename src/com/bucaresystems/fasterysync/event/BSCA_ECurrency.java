package com.bucaresystems.fasterysync.event;

import org.adempiere.base.event.IEventTopics;

import com.bucaresystems.fasterysync.base.CustomEvent;
import com.bucaresystems.fasterysync.model.Currency;

public class BSCA_ECurrency extends CustomEvent{

	@Override
	protected void doHandleEvent() {
		
		if (getEventType().equals(IEventTopics.PO_AFTER_NEW)|| getEventType().equals(IEventTopics.PO_AFTER_CHANGE)){
			Currency currency = new Currency(getPO().get_TrxName(), getPO().get_ID());
			currency.save();
		}
		
	}

}
