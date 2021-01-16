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

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for BSCA_BINBank
 *  @author iDempiere (generated) 
 *  @version Release 7.1 - $Id$ */
public class X_BSCA_BINBank extends PO implements I_BSCA_BINBank, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210115L;

    /** Standard Constructor */
    public X_BSCA_BINBank (Properties ctx, int BSCA_BINBank_ID, String trxName)
    {
      super (ctx, BSCA_BINBank_ID, trxName);
      /** if (BSCA_BINBank_ID == 0)
        {
			setBSCA_BINBank_ID (0);
			setC_Bank_ID (0);
        } */
    }

    /** Load Constructor */
    public X_BSCA_BINBank (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_BSCA_BINBank[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set BIN Bank.
		@param BSCA_BINBank_ID BIN Bank	  */
	public void setBSCA_BINBank_ID (int BSCA_BINBank_ID)
	{
		if (BSCA_BINBank_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_BSCA_BINBank_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_BSCA_BINBank_ID, Integer.valueOf(BSCA_BINBank_ID));
	}

	/** Get BIN Bank.
		@return BIN Bank	  */
	public int getBSCA_BINBank_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BSCA_BINBank_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set BSCA_BINBank_UU.
		@param BSCA_BINBank_UU BSCA_BINBank_UU	  */
	public void setBSCA_BINBank_UU (String BSCA_BINBank_UU)
	{
		set_Value (COLUMNNAME_BSCA_BINBank_UU, BSCA_BINBank_UU);
	}

	/** Get BSCA_BINBank_UU.
		@return BSCA_BINBank_UU	  */
	public String getBSCA_BINBank_UU () 
	{
		return (String)get_Value(COLUMNNAME_BSCA_BINBank_UU);
	}

	public org.compiere.model.I_C_Bank getC_Bank() throws RuntimeException
    {
		return (org.compiere.model.I_C_Bank)MTable.get(getCtx(), org.compiere.model.I_C_Bank.Table_Name)
			.getPO(getC_Bank_ID(), get_TrxName());	}

	/** Set Bank.
		@param C_Bank_ID 
		Bank
	  */
	public void setC_Bank_ID (int C_Bank_ID)
	{
		if (C_Bank_ID < 1) 
			set_Value (COLUMNNAME_C_Bank_ID, null);
		else 
			set_Value (COLUMNNAME_C_Bank_ID, Integer.valueOf(C_Bank_ID));
	}

	/** Get Bank.
		@return Bank
	  */
	public int getC_Bank_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Bank_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Default.
		@param IsDefault 
		Default value
	  */
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Default.
		@return Default value
	  */
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}