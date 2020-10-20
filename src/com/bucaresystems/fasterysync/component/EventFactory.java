/**
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Copyright (C) 2020 BUCARE SYSTEM C.A. <http://www.bucaresystems.com> and contributors (see README.md file).
 */

package com.bucaresystems.fasterysync.component;

import org.adempiere.base.event.IEventTopics;
import org.compiere.model.MProduct;
import org.compiere.model.MProductCategory;
import org.compiere.model.MProductPrice;
import org.compiere.model.MTax;
import org.compiere.model.MTaxCategory;
import org.compiere.model.MUser;

import com.bucaresystems.fasterysync.base.CustomEventFactory;
import com.bucaresystems.fasterysync.event.BSCA_EProduct;
import com.bucaresystems.fasterysync.event.BSCA_EProductCategory;
import com.bucaresystems.fasterysync.event.BSCA_EProductOrg;
import com.bucaresystems.fasterysync.event.BSCA_EProductPrice;
import com.bucaresystems.fasterysync.event.BSCA_EProductValue;
import com.bucaresystems.fasterysync.event.BSCA_ERolPos;
import com.bucaresystems.fasterysync.event.BSCA_ETax;
import com.bucaresystems.fasterysync.event.BSCA_ETaxCategory;
import com.bucaresystems.fasterysync.event.BSCA_EUser;

/**
 * Event Factory
 */
public class EventFactory extends CustomEventFactory {

	/**
	 * For initialize class. Register the custom events to build
	 * 
	 * <pre>
	 * protected void initialize() {
	 * 	registerEvent(IEventTopics.DOC_BEFORE_COMPLETE, MTableExample.Table_Name, EPrintPluginInfo.class);
	 * }
	 * </pre>
	 */
	@Override
	protected void initialize() {
		registerEvent(IEventTopics.PO_AFTER_NEW, MProductCategory.Table_Name, BSCA_EProductCategory.class);
		registerEvent(IEventTopics.PO_AFTER_CHANGE, MProductCategory.Table_Name, BSCA_EProductCategory.class);
		registerEvent(IEventTopics.PO_AFTER_CHANGE, "BSCA_RolesPOS", BSCA_ERolPos.class);	
		registerEvent(IEventTopics.PO_AFTER_NEW, "BSCA_RolesPOS",BSCA_ERolPos.class);
		registerEvent(IEventTopics.PO_AFTER_CHANGE, MUser.Table_Name, BSCA_EUser.class);	
		registerEvent(IEventTopics.PO_AFTER_NEW, MUser.Table_Name,BSCA_EUser.class);
		registerEvent(IEventTopics.PO_AFTER_NEW, MTaxCategory.Table_Name, BSCA_ETaxCategory.class);
		registerEvent(IEventTopics.PO_AFTER_CHANGE, MTaxCategory.Table_Name,BSCA_ETaxCategory.class);
		registerEvent(IEventTopics.PO_AFTER_CHANGE, MTax.Table_Name, BSCA_ETax.class);	
		registerEvent(IEventTopics.PO_AFTER_NEW, MTax.Table_Name, BSCA_ETax.class);
		registerEvent(IEventTopics.PO_AFTER_CHANGE, MProduct.Table_Name,BSCA_EProduct.class);	
		registerEvent(IEventTopics.PO_AFTER_NEW, MProduct.Table_Name,BSCA_EProduct.class);
		registerEvent(IEventTopics.PO_AFTER_CHANGE, "BSCA_ProductValue", BSCA_EProductValue.class);	
		registerEvent(IEventTopics.PO_AFTER_NEW, "BSCA_ProductValue",BSCA_EProductValue.class);
		registerEvent(IEventTopics.PO_AFTER_NEW, "BSCA_ProductOrg",BSCA_EProductOrg.class);
		registerEvent(IEventTopics.PO_BEFORE_DELETE, "BSCA_ProductOrg", BSCA_EProductOrg.class);
		registerEvent(IEventTopics.PO_AFTER_CHANGE, MProductPrice.Table_Name,BSCA_EProductPrice.class);	
		registerEvent(IEventTopics.PO_AFTER_NEW, MProductPrice.Table_Name,BSCA_EProductPrice.class);
	}

}
