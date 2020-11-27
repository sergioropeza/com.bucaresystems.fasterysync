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

/** Generated Model for BSCA_POSDetaill
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_BSCA_POSDetaill extends PO implements I_BSCA_POSDetaill, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20181005L;

    /** Standard Constructor */
    public X_BSCA_POSDetaill (Properties ctx, int BSCA_POSDetaill_ID, String trxName)
    {
      super (ctx, BSCA_POSDetaill_ID, trxName);
      /** if (BSCA_POSDetaill_ID == 0)
        {
			setBSCA_isTaxPayer (false);
// N
			setBSCA_POSDetaill_ID (0);
        } */
    }

    /** Load Constructor */
    public X_BSCA_POSDetaill (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_BSCA_POSDetaill[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Fiscal DocumentNo.
		@param BSCA_FiscalDocumentNo Fiscal DocumentNo	  */
	public void setBSCA_FiscalDocumentNo (String BSCA_FiscalDocumentNo)
	{
		set_Value (COLUMNNAME_BSCA_FiscalDocumentNo, BSCA_FiscalDocumentNo);
	}

	/** Get Fiscal DocumentNo.
		@return Fiscal DocumentNo	  */
	public String getBSCA_FiscalDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_BSCA_FiscalDocumentNo);
	}

	/** Set Tax Payer.
		@param BSCA_isTaxPayer Tax Payer	  */
	public void setBSCA_isTaxPayer (boolean BSCA_isTaxPayer)
	{
		set_Value (COLUMNNAME_BSCA_isTaxPayer, Boolean.valueOf(BSCA_isTaxPayer));
	}

	/** Get Tax Payer.
		@return Tax Payer	  */
	public boolean isBSCA_isTaxPayer () 
	{
		Object oo = get_Value(COLUMNNAME_BSCA_isTaxPayer);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

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

	/** Set BSCA_POSDetaill_UU.
		@param BSCA_POSDetaill_UU BSCA_POSDetaill_UU	  */
	public void setBSCA_POSDetaill_UU (String BSCA_POSDetaill_UU)
	{
		set_Value (COLUMNNAME_BSCA_POSDetaill_UU, BSCA_POSDetaill_UU);
	}

	/** Get BSCA_POSDetaill_UU.
		@return BSCA_POSDetaill_UU	  */
	public String getBSCA_POSDetaill_UU () 
	{
		return (String)get_Value(COLUMNNAME_BSCA_POSDetaill_UU);
	}

	/** Set Pos Invoiceaffected.
		@param BSCA_Pos_invoiceaffected Pos Invoiceaffected	  */
	public void setBSCA_Pos_invoiceaffected (String BSCA_Pos_invoiceaffected)
	{
		set_Value (COLUMNNAME_BSCA_Pos_invoiceaffected, BSCA_Pos_invoiceaffected);
	}

	/** Get Pos Invoiceaffected.
		@return Pos Invoiceaffected	  */
	public String getBSCA_Pos_invoiceaffected () 
	{
		return (String)get_Value(COLUMNNAME_BSCA_Pos_invoiceaffected);
	}

	/** Set Stellar Documento.
		@param BSCA_StellarDocumento Stellar Documento	  */
	public void setBSCA_StellarDocumento (String BSCA_StellarDocumento)
	{
		set_Value (COLUMNNAME_BSCA_StellarDocumento, BSCA_StellarDocumento);
	}

	/** Get Stellar Documento.
		@return Stellar Documento	  */
	public String getBSCA_StellarDocumento () 
	{
		return (String)get_Value(COLUMNNAME_BSCA_StellarDocumento);
	}

	/** Set Stellar Rif.
		@param BSCA_StellarRif Stellar Rif	  */
	public void setBSCA_StellarRif (String BSCA_StellarRif)
	{
		set_Value (COLUMNNAME_BSCA_StellarRif, BSCA_StellarRif);
	}

	/** Get Stellar Rif.
		@return Stellar Rif	  */
	public String getBSCA_StellarRif () 
	{
		return (String)get_Value(COLUMNNAME_BSCA_StellarRif);
	}

	/** Set Client Name.
		@param ClientName Client Name	  */
	public void setClientName (String ClientName)
	{
		set_ValueNoCheck (COLUMNNAME_ClientName, ClientName);
	}

	/** Get Client Name.
		@return Client Name	  */
	public String getClientName () 
	{
		return (String)get_Value(COLUMNNAME_ClientName);
	}

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
    {
		return (org.compiere.model.I_C_Order)MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_Name)
			.getPO(getC_Order_ID(), get_TrxName());	}

	/** Set Order.
		@param C_Order_ID 
		Order
	  */
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Order.
		@return Order
	  */
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Fiscal Printer Serial.
		@param FiscalPrinterSerial Fiscal Printer Serial	  */
	public void setFiscalPrinterSerial (String FiscalPrinterSerial)
	{
		set_Value (COLUMNNAME_FiscalPrinterSerial, FiscalPrinterSerial);
	}

	/** Get Fiscal Printer Serial.
		@return Fiscal Printer Serial	  */
	public String getFiscalPrinterSerial () 
	{
		return (String)get_Value(COLUMNNAME_FiscalPrinterSerial);
	}

	/** Set Grand Total.
		@param GrandTotal 
		Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal)
	{
		set_ValueNoCheck (COLUMNNAME_GrandTotal, GrandTotal);
	}

	/** Get Grand Total.
		@return Total amount of document
	  */
	public BigDecimal getGrandTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrandTotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set LocationName.
		@param LocationName LocationName	  */
	public void setLocationName (String LocationName)
	{
		set_ValueNoCheck (COLUMNNAME_LocationName, LocationName);
	}

	/** Get LocationName.
		@return LocationName	  */
	public String getLocationName () 
	{
		return (String)get_Value(COLUMNNAME_LocationName);
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

	/** Set TaxAmtOrder.
		@param TaxAmtOrder TaxAmtOrder	  */
	public void setTaxAmtOrder (BigDecimal TaxAmtOrder)
	{
		set_Value (COLUMNNAME_TaxAmtOrder, TaxAmtOrder);
	}

	/** Get TaxAmtOrder.
		@return TaxAmtOrder	  */
	public BigDecimal getTaxAmtOrder () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TaxAmtOrder);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}