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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for T_BSCA_CloseVPOSLine
 *  @author iDempiere (generated) 
 *  @version Release 7.1 - $Id$ */
public class X_T_BSCA_CloseVPOSLine extends PO implements I_T_BSCA_CloseVPOSLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210115L;

    /** Standard Constructor */
    public X_T_BSCA_CloseVPOSLine (Properties ctx, int T_BSCA_CloseVPOSLine_ID, String trxName)
    {
      super (ctx, T_BSCA_CloseVPOSLine_ID, trxName);
      /** if (T_BSCA_CloseVPOSLine_ID == 0)
        {
			setT_BSCA_CloseVPOSHeader_ID (0);
        } */
    }

    /** Load Constructor */
    public X_T_BSCA_CloseVPOSLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_T_BSCA_CloseVPOSLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Affiliate Value.
		@param AffiliateValue Affiliate Value	  */
	public void setAffiliateValue (String AffiliateValue)
	{
		set_Value (COLUMNNAME_AffiliateValue, AffiliateValue);
	}

	/** Get Affiliate Value.
		@return Affiliate Value	  */
	public String getAffiliateValue () 
	{
		return (String)get_Value(COLUMNNAME_AffiliateValue);
	}

	/** Set Amount.
		@param Amt 
		Amount
	  */
	public void setAmt (BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	/** Get Amount.
		@return Amount
	  */
	public BigDecimal getAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Authorization Value.
		@param AuthorizationValue Authorization Value	  */
	public void setAuthorizationValue (String AuthorizationValue)
	{
		set_Value (COLUMNNAME_AuthorizationValue, AuthorizationValue);
	}

	/** Get Authorization Value.
		@return Authorization Value	  */
	public String getAuthorizationValue () 
	{
		return (String)get_Value(COLUMNNAME_AuthorizationValue);
	}

	public I_BSCA_BINBank getBSCA_BINBank() throws RuntimeException
    {
		return (I_BSCA_BINBank)MTable.get(getCtx(), I_BSCA_BINBank.Table_Name)
			.getPO(getBSCA_BINBank_ID(), get_TrxName());	}

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

	/** Set Route.
		@param BSCA_Route_ID Route	  */
	public void setBSCA_Route_ID (int BSCA_Route_ID)
	{
		if (BSCA_Route_ID < 1) 
			set_Value (COLUMNNAME_BSCA_Route_ID, null);
		else 
			set_Value (COLUMNNAME_BSCA_Route_ID, Integer.valueOf(BSCA_Route_ID));
	}

	/** Get Route.
		@return Route	  */
	public int getBSCA_Route_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BSCA_Route_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Bank getC_BankTo() throws RuntimeException
    {
		return (org.compiere.model.I_C_Bank)MTable.get(getCtx(), org.compiere.model.I_C_Bank.Table_Name)
			.getPO(getC_BankTo_ID(), get_TrxName());	}

	/** Set C_BankTo_ID.
		@param C_BankTo_ID C_BankTo_ID	  */
	public void setC_BankTo_ID (int C_BankTo_ID)
	{
		if (C_BankTo_ID < 1) 
			set_Value (COLUMNNAME_C_BankTo_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankTo_ID, Integer.valueOf(C_BankTo_ID));
	}

	/** Get C_BankTo_ID.
		@return C_BankTo_ID	  */
	public int getC_BankTo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankTo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Card Value.
		@param CardValue Card Value	  */
	public void setCardValue (String CardValue)
	{
		set_Value (COLUMNNAME_CardValue, CardValue);
	}

	/** Get Card Value.
		@return Card Value	  */
	public String getCardValue () 
	{
		return (String)get_Value(COLUMNNAME_CardValue);
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_ValueNoCheck (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** Set Lot Value.
		@param LotValue Lot Value	  */
	public void setLotValue (String LotValue)
	{
		set_Value (COLUMNNAME_LotValue, LotValue);
	}

	/** Get Lot Value.
		@return Lot Value	  */
	public String getLotValue () 
	{
		return (String)get_Value(COLUMNNAME_LotValue);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Ref Value.
		@param RefValue Ref Value	  */
	public void setRefValue (String RefValue)
	{
		set_Value (COLUMNNAME_RefValue, RefValue);
	}

	/** Get Ref Value.
		@return Ref Value	  */
	public String getRefValue () 
	{
		return (String)get_Value(COLUMNNAME_RefValue);
	}

	/** Set Seq Value.
		@param SeqValue Seq Value	  */
	public void setSeqValue (String SeqValue)
	{
		set_Value (COLUMNNAME_SeqValue, SeqValue);
	}

	/** Get Seq Value.
		@return Seq Value	  */
	public String getSeqValue () 
	{
		return (String)get_Value(COLUMNNAME_SeqValue);
	}

	public I_T_BSCA_CloseVPOSHeader getT_BSCA_CloseVPOSHeader() throws RuntimeException
    {
		return (I_T_BSCA_CloseVPOSHeader)MTable.get(getCtx(), I_T_BSCA_CloseVPOSHeader.Table_Name)
			.getPO(getT_BSCA_CloseVPOSHeader_ID(), get_TrxName());	}

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

	/** Set Close VPOS Line.
		@param T_BSCA_CloseVPOSLine_ID Close VPOS Line	  */
	public void setT_BSCA_CloseVPOSLine_ID (int T_BSCA_CloseVPOSLine_ID)
	{
		if (T_BSCA_CloseVPOSLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_T_BSCA_CloseVPOSLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_T_BSCA_CloseVPOSLine_ID, Integer.valueOf(T_BSCA_CloseVPOSLine_ID));
	}

	/** Get Close VPOS Line.
		@return Close VPOS Line	  */
	public int getT_BSCA_CloseVPOSLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_BSCA_CloseVPOSLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set T_BSCA_CloseVPOSLine_UU.
		@param T_BSCA_CloseVPOSLine_UU T_BSCA_CloseVPOSLine_UU	  */
	public void setT_BSCA_CloseVPOSLine_UU (String T_BSCA_CloseVPOSLine_UU)
	{
		set_ValueNoCheck (COLUMNNAME_T_BSCA_CloseVPOSLine_UU, T_BSCA_CloseVPOSLine_UU);
	}

	/** Get T_BSCA_CloseVPOSLine_UU.
		@return T_BSCA_CloseVPOSLine_UU	  */
	public String getT_BSCA_CloseVPOSLine_UU () 
	{
		return (String)get_Value(COLUMNNAME_T_BSCA_CloseVPOSLine_UU);
	}

	/** TenderType AD_Reference_ID=214 */
	public static final int TENDERTYPE_AD_Reference_ID=214;
	/** Credit Card = C */
	public static final String TENDERTYPE_CreditCard = "C";
	/** Check = K */
	public static final String TENDERTYPE_Check = "K";
	/** Direct Deposit = A */
	public static final String TENDERTYPE_DirectDeposit = "A";
	/** Direct Debit = D */
	public static final String TENDERTYPE_DirectDebit = "D";
	/** Account = T */
	public static final String TENDERTYPE_Account = "T";
	/** Cash = X */
	public static final String TENDERTYPE_Cash = "X";
	/** Withholding = Z */
	public static final String TENDERTYPE_Withholding = "Z";
	/** Power Card = P */
	public static final String TENDERTYPE_PowerCard = "P";
	/** Paper Ticket = E */
	public static final String TENDERTYPE_PaperTicket = "E";
	/** Virtual = V */
	public static final String TENDERTYPE_Virtual = "V";
	/** Merchant Card = M */
	public static final String TENDERTYPE_MerchantCard = "M";
	/** Pos Card = O */
	public static final String TENDERTYPE_PosCard = "O";
	/** Debito Maestro = F */
	public static final String TENDERTYPE_DebitoMaestro = "F";
	/** MasterCard = G */
	public static final String TENDERTYPE_MasterCard = "G";
	/** Visa = H */
	public static final String TENDERTYPE_Visa = "H";
	/** Visa Electron = I */
	public static final String TENDERTYPE_VisaElectron = "I";
	/** Undefined = J */
	public static final String TENDERTYPE_Undefined = "J";
	/** American Express = L */
	public static final String TENDERTYPE_AmericanExpress = "L";
	/** Diners = Q */
	public static final String TENDERTYPE_Diners = "Q";
	/** On Credit = R */
	public static final String TENDERTYPE_OnCredit = "R";
	/** Convenio BioCard = B */
	public static final String TENDERTYPE_ConvenioBioCard = "B";
	/** Crédito Especial = U */
	public static final String TENDERTYPE_CréditoEspecial = "U";
	/** Nota de Crédito = S */
	public static final String TENDERTYPE_NotaDeCrédito = "S";
	/** Credit Sambil = W */
	public static final String TENDERTYPE_CreditSambil = "W";
	/** E-Commerce = N */
	public static final String TENDERTYPE_E_Commerce = "N";
	/** Efectivo D = 1 */
	public static final String TENDERTYPE_EfectivoD = "1";
	/** Efectivo E = 0 */
	public static final String TENDERTYPE_EfectivoE = "0";
	/** Set Tender type.
		@param TenderType 
		Method of Payment
	  */
	public void setTenderType (String TenderType)
	{

		set_ValueNoCheck (COLUMNNAME_TenderType, TenderType);
	}

	/** Get Tender type.
		@return Method of Payment
	  */
	public String getTenderType () 
	{
		return (String)get_Value(COLUMNNAME_TenderType);
	}

	/** Set Terminal Value.
		@param TerminalValue Terminal Value	  */
	public void setTerminalValue (String TerminalValue)
	{
		set_Value (COLUMNNAME_TerminalValue, TerminalValue);
	}

	/** Get Terminal Value.
		@return Terminal Value	  */
	public String getTerminalValue () 
	{
		return (String)get_Value(COLUMNNAME_TerminalValue);
	}
}