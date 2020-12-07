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
import org.compiere.model.*;

/** Generated Model for BSCA_SyncInvoice_Para
 *  @author iDempiere (generated) 
 *  @version Release 7.1 - $Id$ */
public class X_BSCA_SyncInvoice_Para extends PO implements I_BSCA_SyncInvoice_Para, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20201206L;

    /** Standard Constructor */
    public X_BSCA_SyncInvoice_Para (Properties ctx, int BSCA_SyncInvoice_Para_ID, String trxName)
    {
      super (ctx, BSCA_SyncInvoice_Para_ID, trxName);
      /** if (BSCA_SyncInvoice_Para_ID == 0)
        {
			setBSCA_SyncInvoice_Para_ID (0);
        } */
    }

    /** Load Constructor */
    public X_BSCA_SyncInvoice_Para (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_BSCA_SyncInvoice_Para[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Organization Info.
		@param BSCA_SyncInvoice_Para_ID Organization Info	  */
	public void setBSCA_SyncInvoice_Para_ID (int BSCA_SyncInvoice_Para_ID)
	{
		if (BSCA_SyncInvoice_Para_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_BSCA_SyncInvoice_Para_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_BSCA_SyncInvoice_Para_ID, Integer.valueOf(BSCA_SyncInvoice_Para_ID));
	}

	/** Get Organization Info.
		@return Organization Info	  */
	public int getBSCA_SyncInvoice_Para_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BSCA_SyncInvoice_Para_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set BSCA_SyncInvoice_Para_UU.
		@param BSCA_SyncInvoice_Para_UU BSCA_SyncInvoice_Para_UU	  */
	public void setBSCA_SyncInvoice_Para_UU (String BSCA_SyncInvoice_Para_UU)
	{
		set_Value (COLUMNNAME_BSCA_SyncInvoice_Para_UU, BSCA_SyncInvoice_Para_UU);
	}

	/** Get BSCA_SyncInvoice_Para_UU.
		@return BSCA_SyncInvoice_Para_UU	  */
	public String getBSCA_SyncInvoice_Para_UU () 
	{
		return (String)get_Value(COLUMNNAME_BSCA_SyncInvoice_Para_UU);
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

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
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