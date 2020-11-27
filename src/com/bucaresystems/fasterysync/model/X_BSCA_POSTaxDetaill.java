/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package com.bucaresystems.fasterysync.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for BSCA_POSTaxDetaill
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_BSCA_POSTaxDetaill extends PO implements I_BSCA_POSTaxDetaill, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20181003L;

    /** Standard Constructor */
    public X_BSCA_POSTaxDetaill (Properties ctx, int BSCA_POSTaxDetaill_ID, String trxName)
    {
      super (ctx, BSCA_POSTaxDetaill_ID, trxName);
      /** if (BSCA_POSTaxDetaill_ID == 0)
        {
			setBSCA_POSTaxDetaill_ID (0);
        } */
    }

    /** Load Constructor */
    public X_BSCA_POSTaxDetaill (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_BSCA_POSTaxDetaill[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public com.bucaresystems.fasterysync.model.I_BSCA_POSDetaill getBSCA_POSDetaill() throws RuntimeException
    {
		return (com.bucaresystems.fasterysync.model.I_BSCA_POSDetaill)MTable.get(getCtx(), com.bucaresystems.fasterysync.model.I_BSCA_POSDetaill.Table_Name)
			.getPO(getBSCA_POSDetaill_ID(), get_TrxName());	}

	/** Set POS Detaill.
		@param BSCA_POSDetaill_ID POS Detaill	  */
	public void setBSCA_POSDetaill_ID (int BSCA_POSDetaill_ID)
	{
		if (BSCA_POSDetaill_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_BSCA_POSDetaill_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_BSCA_POSDetaill_ID, Integer.valueOf(BSCA_POSDetaill_ID));
	}

	/** Get POS Detaill.
		@return POS Detaill	  */
	public int getBSCA_POSDetaill_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BSCA_POSDetaill_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set POS Tax Detaill.
		@param BSCA_POSTaxDetaill_ID POS Tax Detaill	  */
	public void setBSCA_POSTaxDetaill_ID (int BSCA_POSTaxDetaill_ID)
	{
		if (BSCA_POSTaxDetaill_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_BSCA_POSTaxDetaill_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_BSCA_POSTaxDetaill_ID, Integer.valueOf(BSCA_POSTaxDetaill_ID));
	}

	/** Get POS Tax Detaill.
		@return POS Tax Detaill	  */
	public int getBSCA_POSTaxDetaill_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BSCA_POSTaxDetaill_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set BSCA_POSTaxDetaill_UU.
		@param BSCA_POSTaxDetaill_UU BSCA_POSTaxDetaill_UU	  */
	public void setBSCA_POSTaxDetaill_UU (String BSCA_POSTaxDetaill_UU)
	{
		set_Value (COLUMNNAME_BSCA_POSTaxDetaill_UU, BSCA_POSTaxDetaill_UU);
	}

	/** Get BSCA_POSTaxDetaill_UU.
		@return BSCA_POSTaxDetaill_UU	  */
	public String getBSCA_POSTaxDetaill_UU () 
	{
		return (String)get_Value(COLUMNNAME_BSCA_POSTaxDetaill_UU);
	}

	public org.compiere.model.I_C_Tax getC_Tax() throws RuntimeException
    {
		return (org.compiere.model.I_C_Tax)MTable.get(getCtx(), org.compiere.model.I_C_Tax.Table_Name)
			.getPO(getC_Tax_ID(), get_TrxName());	}

	/** Set Tax.
		@param C_Tax_ID 
		Tax identifier
	  */
	public void setC_Tax_ID (int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
	}

	/** Get Tax.
		@return Tax identifier
	  */
	public int getC_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Rate.
		@param Rate 
		Rate or Tax or Exchange
	  */
	public void setRate (BigDecimal Rate)
	{
		set_ValueNoCheck (COLUMNNAME_Rate, Rate);
	}

	/** Get Rate.
		@return Rate or Tax or Exchange
	  */
	public BigDecimal getRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Rate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tax Amount.
		@param TaxAmt 
		Tax Amount for a document
	  */
	public void setTaxAmt (BigDecimal TaxAmt)
	{
		set_Value (COLUMNNAME_TaxAmt, TaxAmt);
	}

	/** Get Tax Amount.
		@return Tax Amount for a document
	  */
	public BigDecimal getTaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tax base Amount.
		@param TaxBaseAmt 
		Base for calculating the tax amount
	  */
	public void setTaxBaseAmt (BigDecimal TaxBaseAmt)
	{
		set_ValueNoCheck (COLUMNNAME_TaxBaseAmt, TaxBaseAmt);
	}

	/** Get Tax base Amount.
		@return Base for calculating the tax amount
	  */
	public BigDecimal getTaxBaseAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TaxBaseAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}