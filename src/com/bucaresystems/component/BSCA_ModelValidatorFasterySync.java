package com.bucaresystems.component;

import org.adempiere.base.event.AbstractEventHandler;
import org.adempiere.base.event.IEventTopics;
import org.compiere.model.MProduct;
import org.compiere.model.MProductCategory;
import org.compiere.model.PO;
import org.osgi.service.event.Event;

import com.bucaresystems.model.Categories;


public class BSCA_ModelValidatorFasterySync extends AbstractEventHandler{


	private PO po;
	@Override
	protected void initialize() {
		registerTableEvent(IEventTopics.PO_AFTER_NEW, MProductCategory.Table_Name);
		registerTableEvent(IEventTopics.PO_AFTER_CHANGE, MProductCategory.Table_Name);
		
	}
	@Override
	protected void doHandleEvent(Event event) {
		
		String type = event.getTopic();
		po = getPO(event);
		
		if (po.get_TableName().equals(MProductCategory.Table_Name)){
			MProductCategory productCategory = (MProductCategory)po;
			
			Categories categories = new Categories(po.get_TrxName(), po.get_ID());
			categories.save();
		
		
		}
	}
	
	
}


