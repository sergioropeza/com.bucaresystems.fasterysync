/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package com.bucaresystems.fasterysync.pos.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Tax;
import org.compiere.model.MRule;
import org.compiere.model.MTax;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.model.Scriptlet;
import org.compiere.util.Env;

/**
 *  Tax Model
 *
 *	@author Jorg Janke
 *	@version $Id: MTax.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 * 	red1 - FR: [ 2214883 ] Remove SQL code and Replace for Query
 *  trifonnt - BF [2913276] - Allow only one Default Tax Rate per Tax Category
 *  mjmckay - BF [2948632] - Allow edits to the Default Tax Rate 
 */
public class BSCA_Tax extends MTax{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BSCA_Tax(Properties ctx, int C_Tax_ID, String trxName) {
		super(ctx, C_Tax_ID, trxName);
	
	}

	public BigDecimal calculateTax (BigDecimal amount, boolean taxIncluded, int scale, PO header, PO line)
	{
		
		BigDecimal taxAmt = Env.ZERO;
		
		List<BSCA_Tax> taxes = new ArrayList<BSCA_Tax>();
		
		if(taxIncluded)
		{
			amount=getBaseAmt(amount, header, line,taxIncluded);
			taxIncluded=false;
		}	
		
		if(this.isSummary())
			taxes = Arrays.asList(this.getChildTaxes(true));
		else
			taxes.add(this);
		

		for(BSCA_Tax tax:taxes)
		{
			if(tax.getAD_Rule_ID()!=0)			
				taxAmt = taxAmt.add(tax.executeRule(header, line)[0]);
			else			
				taxAmt = taxAmt.add(tax.calculateTax(amount, taxIncluded, scale));
		}
	
		
		return taxAmt;
	}
	
	public BigDecimal getBaseAmt(BigDecimal amount, PO header, PO line, boolean isTaxInclude)
	{
		if(line == null)
			return amount;
		
		BigDecimal baseAmt=null;
		
		if(getAD_Rule_ID()!=0)
			baseAmt=executeRule(header, line)[1];
		
		if(baseAmt!=null){
			return baseAmt.setScale(2, RoundingMode.HALF_UP);
		}
		if(!isTaxInclude){
			return amount;
		}
		
		int C_ParentTax_ID;
		
		if(this.isSummary())
			C_ParentTax_ID = this.getC_Tax_ID();
		else
		{
			C_ParentTax_ID = this.getParent_Tax_ID();

			if(this.getC_Tax_ID()==(Integer)line.get_Value(MTax.COLUMNNAME_C_Tax_ID))
				C_ParentTax_ID =0;
		}
		
		if(C_ParentTax_ID!=0)
		{
			BSCA_Tax parentTax = new BSCA_Tax(getCtx(), C_ParentTax_ID, get_TrxName());
			BSCA_Tax[] childsTax = parentTax.getChildTaxes(true);
						
			BigDecimal ruledTaxes = Env.ZERO;
			BigDecimal ratedTaxes = Env.ZERO;
			
			for(BSCA_Tax child: childsTax)
			{
				if(child.getAD_Rule_ID()!=0)
				{
					ruledTaxes = ruledTaxes.add(child.executeRule(header, line)[0]);
				}
				else
					ratedTaxes= ratedTaxes.add(child.getRate().divide(Env.ONEHUNDRED, 12, RoundingMode.HALF_UP));
			}
			ratedTaxes = ratedTaxes.add(Env.ONE);
			baseAmt = amount.subtract(ruledTaxes);
			baseAmt = baseAmt.divide(ratedTaxes, 12, RoundingMode.HALF_UP); 			
		}
		else
		{
			if(getAD_Rule_ID()!=0)
			{
				baseAmt = amount.subtract(executeRule(header, line)[0]);
			}
			else
			{
				BigDecimal multiplier = getRate().divide(Env.ONEHUNDRED, 12, RoundingMode.HALF_UP);		
				multiplier = multiplier.add(Env.ONE);
				baseAmt = amount.divide(multiplier, 12, RoundingMode.HALF_UP); 
			}
		}
		return baseAmt.setScale(2, RoundingMode.HALF_UP);
	}	
	
	private BigDecimal[] executeRule(PO header, PO line)
	{
		
		BigDecimal resultScript[] = new BigDecimal[2];
		MRule rule = MRule.get(getCtx(), this.getAD_Rule_ID());
		try 
		{
			HashMap<String, Object> m_scriptCtx = new HashMap<String, Object>();
			
			m_scriptCtx.put("A_POHeader", header);
			m_scriptCtx.put("A_POLine", line);
			String  script = rule.getScript().trim();
	
			Scriptlet engine = new Scriptlet (Scriptlet.VARIABLE, script, m_scriptCtx);	
			
			Exception ex =engine.execute();
			if (ex != null)
			{
				
					throw ex;
				
			}
			
			resultScript[0] = new BigDecimal((Double)engine.getResult(false)).setScale(2, RoundingMode.HALF_UP);
			
			if(engine.getDescription()!=null)
				resultScript[1] = new BigDecimal((Double)engine.getDescription());
		
		} catch (Exception e) 
		{
			throw new AdempiereException("Execution error - @AD_Rule_ID@="+rule.getValue());
		}
		return resultScript;
	}
	
	@Override
	public BSCA_Tax[] getChildTaxes (boolean requery){
		BSCA_Tax[]			m_childTaxes = null;
		
		if (!isSummary())
			return null;
		//
		//FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
		final String whereClause = COLUMNNAME_Parent_Tax_ID+"=?";
		List<MTax> list = new Query(getCtx(), I_C_Tax.Table_Name, whereClause,  get_TrxName())
			.setParameters(getC_Tax_ID())
			.setOnlyActiveRecords(true)
			.list();	
		//red1 - end -
	 
		m_childTaxes = new BSCA_Tax[list.size ()];
		list.toArray (m_childTaxes);
		return m_childTaxes;
	}	//	getChildTaxes
	

}	//	MTax
