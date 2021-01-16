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
package com.bucaresystems.fasterysync.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for T_BSCA_CloseVPOSLine
 *  @author iDempiere (generated) 
 *  @version Release 7.1
 */
@SuppressWarnings("all")
public interface I_T_BSCA_CloseVPOSLine 
{

    /** TableName=T_BSCA_CloseVPOSLine */
    public static final String Table_Name = "T_BSCA_CloseVPOSLine";

    /** AD_Table_ID=1000388 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AffiliateValue */
    public static final String COLUMNNAME_AffiliateValue = "AffiliateValue";

	/** Set Affiliate Value	  */
	public void setAffiliateValue (String AffiliateValue);

	/** Get Affiliate Value	  */
	public String getAffiliateValue();

    /** Column name Amt */
    public static final String COLUMNNAME_Amt = "Amt";

	/** Set Amount.
	  * Amount
	  */
	public void setAmt (BigDecimal Amt);

	/** Get Amount.
	  * Amount
	  */
	public BigDecimal getAmt();

    /** Column name AuthorizationValue */
    public static final String COLUMNNAME_AuthorizationValue = "AuthorizationValue";

	/** Set Authorization Value	  */
	public void setAuthorizationValue (String AuthorizationValue);

	/** Get Authorization Value	  */
	public String getAuthorizationValue();

    /** Column name BSCA_BINBank_ID */
    public static final String COLUMNNAME_BSCA_BINBank_ID = "BSCA_BINBank_ID";

	/** Set BIN Bank	  */
	public void setBSCA_BINBank_ID (int BSCA_BINBank_ID);

	/** Get BIN Bank	  */
	public int getBSCA_BINBank_ID();

	public I_BSCA_BINBank getBSCA_BINBank() throws RuntimeException;

    /** Column name BSCA_Route_ID */
    public static final String COLUMNNAME_BSCA_Route_ID = "BSCA_Route_ID";

	/** Set Route	  */
	public void setBSCA_Route_ID (int BSCA_Route_ID);

	/** Get Route	  */
	public int getBSCA_Route_ID();



    /** Column name C_BankTo_ID */
    public static final String COLUMNNAME_C_BankTo_ID = "C_BankTo_ID";

	/** Set C_BankTo_ID	  */
	public void setC_BankTo_ID (int C_BankTo_ID);

	/** Get C_BankTo_ID	  */
	public int getC_BankTo_ID();

	public org.compiere.model.I_C_Bank getC_BankTo() throws RuntimeException;

    /** Column name CardValue */
    public static final String COLUMNNAME_CardValue = "CardValue";

	/** Set Card Value	  */
	public void setCardValue (String CardValue);

	/** Get Card Value	  */
	public String getCardValue();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/** Set Transaction Date.
	  * Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx);

	/** Get Transaction Date.
	  * Transaction Date
	  */
	public Timestamp getDateTrx();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name LotValue */
    public static final String COLUMNNAME_LotValue = "LotValue";

	/** Set Lot Value	  */
	public void setLotValue (String LotValue);

	/** Get Lot Value	  */
	public String getLotValue();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name RefValue */
    public static final String COLUMNNAME_RefValue = "RefValue";

	/** Set Ref Value	  */
	public void setRefValue (String RefValue);

	/** Get Ref Value	  */
	public String getRefValue();

    /** Column name SeqValue */
    public static final String COLUMNNAME_SeqValue = "SeqValue";

	/** Set Seq Value	  */
	public void setSeqValue (String SeqValue);

	/** Get Seq Value	  */
	public String getSeqValue();

    /** Column name T_BSCA_CloseVPOSHeader_ID */
    public static final String COLUMNNAME_T_BSCA_CloseVPOSHeader_ID = "T_BSCA_CloseVPOSHeader_ID";

	/** Set Close VPOS	  */
	public void setT_BSCA_CloseVPOSHeader_ID (int T_BSCA_CloseVPOSHeader_ID);

	/** Get Close VPOS	  */
	public int getT_BSCA_CloseVPOSHeader_ID();


    /** Column name T_BSCA_CloseVPOSLine_ID */
    public static final String COLUMNNAME_T_BSCA_CloseVPOSLine_ID = "T_BSCA_CloseVPOSLine_ID";

	/** Set Close VPOS Line	  */
	public void setT_BSCA_CloseVPOSLine_ID (int T_BSCA_CloseVPOSLine_ID);

	/** Get Close VPOS Line	  */
	public int getT_BSCA_CloseVPOSLine_ID();

    /** Column name T_BSCA_CloseVPOSLine_UU */
    public static final String COLUMNNAME_T_BSCA_CloseVPOSLine_UU = "T_BSCA_CloseVPOSLine_UU";

	/** Set T_BSCA_CloseVPOSLine_UU	  */
	public void setT_BSCA_CloseVPOSLine_UU (String T_BSCA_CloseVPOSLine_UU);

	/** Get T_BSCA_CloseVPOSLine_UU	  */
	public String getT_BSCA_CloseVPOSLine_UU();

    /** Column name TenderType */
    public static final String COLUMNNAME_TenderType = "TenderType";

	/** Set Tender type.
	  * Method of Payment
	  */
	public void setTenderType (String TenderType);

	/** Get Tender type.
	  * Method of Payment
	  */
	public String getTenderType();

    /** Column name TerminalValue */
    public static final String COLUMNNAME_TerminalValue = "TerminalValue";

	/** Set Terminal Value	  */
	public void setTerminalValue (String TerminalValue);

	/** Get Terminal Value	  */
	public String getTerminalValue();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
