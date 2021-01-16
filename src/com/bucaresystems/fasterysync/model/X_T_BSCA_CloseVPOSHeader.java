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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for T_BSCA_CloseVPOSHeader
 *  @author iDempiere (generated) 
 *  @version Release 7.1 - $Id$ */
public class X_T_BSCA_CloseVPOSHeader extends PO implements I_T_BSCA_CloseVPOSHeader, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210115L;

    /** Standard Constructor */
    public X_T_BSCA_CloseVPOSHeader (Properties ctx, int T_BSCA_CloseVPOSHeader_ID, String trxName)
    {
      super (ctx, T_BSCA_CloseVPOSHeader_ID, trxName);
      /** if (T_BSCA_CloseVPOSHeader_ID == 0)
        {
			setT_BSCA_CloseVPOSHeader_ID (0);
        } */
    }

    /** Load Constructor */
    public X_T_BSCA_CloseVPOSHeader (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_T_BSCA_CloseVPOSHeader[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Create From Attach.
		@param BSCA_CreateFromAttach Create From Attach	  */
	public void setBSCA_CreateFromAttach (String BSCA_CreateFromAttach)
	{
		set_Value (COLUMNNAME_BSCA_CreateFromAttach, BSCA_CreateFromAttach);
	}

	/** Get Create From Attach.
		@return Create From Attach	  */
	public String getBSCA_CreateFromAttach () 
	{
		return (String)get_Value(COLUMNNAME_BSCA_CreateFromAttach);
	}

	public org.compiere.model.I_C_BankAccount getC_BankAccount() throws RuntimeException
    {
		return (org.compiere.model.I_C_BankAccount)MTable.get(getCtx(), org.compiere.model.I_C_BankAccount.Table_Name)
			.getPO(getC_BankAccount_ID(), get_TrxName());	}

	/** Set Bank Account.
		@param C_BankAccount_ID 
		Account at the Bank
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID)
	{
		if (C_BankAccount_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BankAccount_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BankAccount_ID, Integer.valueOf(C_BankAccount_ID));
	}

	/** Get Bank Account.
		@return Account at the Bank
	  */
	public int getC_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Close Date.
		@param CloseDate 
		Close Date
	  */
	public void setCloseDate (Timestamp CloseDate)
	{
		set_ValueNoCheck (COLUMNNAME_CloseDate, CloseDate);
	}

	/** Get Close Date.
		@return Close Date
	  */
	public Timestamp getCloseDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_CloseDate);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Error Msg.
		@param ErrorMsg Error Msg	  */
	public void setErrorMsg (String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	/** Get Error Msg.
		@return Error Msg	  */
	public String getErrorMsg () 
	{
		return (String)get_Value(COLUMNNAME_ErrorMsg);
	}

	/** Set Close VPOS.
		@param T_BSCA_CloseVPOSHeader_ID Close VPOS	  */
	public void setT_BSCA_CloseVPOSHeader_ID (int T_BSCA_CloseVPOSHeader_ID)
	{
		if (T_BSCA_CloseVPOSHeader_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_T_BSCA_CloseVPOSHeader_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_T_BSCA_CloseVPOSHeader_ID, Integer.valueOf(T_BSCA_CloseVPOSHeader_ID));
	}

	/** Get Close VPOS.
		@return Close VPOS	  */
	public int getT_BSCA_CloseVPOSHeader_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_BSCA_CloseVPOSHeader_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set T_BSCA_CloseVPOSHeader_UU.
		@param T_BSCA_CloseVPOSHeader_UU T_BSCA_CloseVPOSHeader_UU	  */
	public void setT_BSCA_CloseVPOSHeader_UU (String T_BSCA_CloseVPOSHeader_UU)
	{
		set_ValueNoCheck (COLUMNNAME_T_BSCA_CloseVPOSHeader_UU, T_BSCA_CloseVPOSHeader_UU);
	}

	/** Get T_BSCA_CloseVPOSHeader_UU.
		@return T_BSCA_CloseVPOSHeader_UU	  */
	public String getT_BSCA_CloseVPOSHeader_UU () 
	{
		return (String)get_Value(COLUMNNAME_T_BSCA_CloseVPOSHeader_UU);
	}
}