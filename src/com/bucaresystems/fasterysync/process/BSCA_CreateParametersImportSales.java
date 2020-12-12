package com.bucaresystems.fasterysync.process;

import java.math.BigDecimal;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.util.DB;

import com.bucaresystems.fasterysync.base.CustomProcess;
import com.bucaresystems.fasterysync.model.X_BSCA_SyncInvoice_Para;

public class BSCA_CreateParametersImportSales extends CustomProcess{

	private BigDecimal AD_Org_ID;

	@Override
	protected void prepare() {
		AD_Org_ID = (BigDecimal) getParameter("AD_OrgOrder_ID");
	}

	@Override
	protected String doIt() throws Exception {
		
		DB.executeUpdateEx("delete from BSCA_SyncInvoice_Para where AD_Org_ID = "+AD_Org_ID, get_TrxName());

		ProcessInfoParameter[] para = getParameter();	
		for (ProcessInfoParameter processInfoParameter : para) {
			X_BSCA_SyncInvoice_Para synInvoice = new X_BSCA_SyncInvoice_Para(getCtx(), 0, get_TrxName());
			synInvoice.setAD_Org_ID(AD_Org_ID.intValue());
			synInvoice.setName(processInfoParameter.getParameterName());
			synInvoice.setValue(processInfoParameter.getParameter() == null?"":processInfoParameter.getParameter().toString());
			synInvoice.saveEx();
			
		}
		return "OK"; 
	}

}
