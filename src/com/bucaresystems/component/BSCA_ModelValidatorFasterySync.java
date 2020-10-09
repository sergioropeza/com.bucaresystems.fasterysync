package com.bucaresystems.component;

import java.util.List;

import org.adempiere.base.event.AbstractEventHandler;
import org.adempiere.base.event.IEventTopics;
import org.compiere.model.MProduct;
import org.compiere.model.MProductCategory;
import org.compiere.model.MProductPrice;
import org.compiere.model.MTax;
import org.compiere.model.MTaxCategory;
import org.compiere.model.MUser;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.osgi.service.event.Event;

import com.bucaresystems.model.Categories;
import com.bucaresystems.model.People;
import com.bucaresystems.model.Products;
import com.bucaresystems.model.Roles;
import com.bucaresystems.model.Taxcategories;
import com.bucaresystems.model.Taxes;


public class BSCA_ModelValidatorFasterySync extends AbstractEventHandler{


	private PO po;
	@Override
	protected void initialize() {
		
		registerTableEvent(IEventTopics.PO_AFTER_CHANGE, "BSCA_RolesPOS");	
		registerTableEvent(IEventTopics.PO_AFTER_NEW, "BSCA_RolesPOS");
		registerTableEvent(IEventTopics.PO_AFTER_CHANGE, MUser.Table_Name);	
		registerTableEvent(IEventTopics.PO_AFTER_NEW, MUser.Table_Name);
		registerTableEvent(IEventTopics.PO_AFTER_NEW, MTaxCategory.Table_Name);
		registerTableEvent(IEventTopics.PO_AFTER_CHANGE, MTaxCategory.Table_Name);
		registerTableEvent(IEventTopics.PO_AFTER_CHANGE, MTax.Table_Name);	
		registerTableEvent(IEventTopics.PO_AFTER_NEW, MTax.Table_Name);
		registerTableEvent(IEventTopics.PO_AFTER_NEW, MProductCategory.Table_Name);
		registerTableEvent(IEventTopics.PO_AFTER_CHANGE, MProductCategory.Table_Name);
		registerTableEvent(IEventTopics.PO_AFTER_CHANGE, MProduct.Table_Name);	
		registerTableEvent(IEventTopics.PO_AFTER_NEW, MProduct.Table_Name);
		registerTableEvent(IEventTopics.PO_AFTER_CHANGE, "BSCA_ProductValue");	
		registerTableEvent(IEventTopics.PO_AFTER_NEW, "BSCA_ProductValue");
		registerTableEvent(IEventTopics.PO_BEFORE_DELETE, "BSCA_ProductValue");
		registerTableEvent(IEventTopics.PO_AFTER_CHANGE, MProductPrice.Table_Name);	
		registerTableEvent(IEventTopics.PO_AFTER_NEW, MProductPrice.Table_Name);
		registerTableEvent(IEventTopics.PO_AFTER_NEW, "BSCA_ProductOrg");
		registerTableEvent(IEventTopics.PO_BEFORE_DELETE, "BSCA_ProductOrg");
		
	}
	@Override
	protected void doHandleEvent(Event event) {
		
		String type = event.getTopic();
		po = getPO(event);
		String trxName = po.get_TrxName();
		if (po.get_TableName().equals(MProductCategory.Table_Name)){		
			Categories categories = new Categories(trxName, po.get_ID());
			categories.save();	
		}else if (po.get_TableName().equals("BSCA_RolesPOS")){		
			Roles roles = new Roles(trxName,  po.get_ID());
			roles.save();
		}else if (po.get_TableName().equals(MUser.Table_Name)){
			People people = new People(trxName, po.get_ID());
			people.save();
		}else if (po.get_TableName().equals(MTaxCategory.Table_Name)){
			Taxcategories taxcategories = new Taxcategories(trxName, po.get_ID());
			taxcategories.save();
		}else if (po.get_TableName().equals(MTax.Table_Name)){
			Taxes taxes= new Taxes(trxName, po.get_ID());
			taxes.save();
		}else if (po.get_TableName().equals(MProduct.Table_Name)){
			int M_Product_ID = po.get_ValueAsInt("M_Product_ID");
			Products products  = new Products(trxName, M_Product_ID);
			
			if (type.equals(IEventTopics.PO_AFTER_NEW)|| type.equals(IEventTopics.PO_AFTER_CHANGE)){
				List<PO> lstProductOrg = new Query(Env.getCtx(), "BSCA_ProductOrg", "M_Product_ID= ?", trxName).
						setParameters(M_Product_ID).
						setOnlyActiveRecords(true).list();
//				for (PO productOrg : lstProductOrg) {
//					
//					if (BSCA_Products.isAllRegister(trxName,M_Product_ID, productOrg.getAD_Org_ID())){ // Producto, Organización, Precio y Código
//						products.save();
//						insertOrUpdateProducts_ValueFromPOrg(M_Product_ID,productOrg.getAD_Org_ID());
//					}
//				}
				
			}
			
		}
	}
	
	
}


