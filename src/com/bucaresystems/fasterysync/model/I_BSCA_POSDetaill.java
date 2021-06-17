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

/** Generated Interface for BSCA_POSDetaill
 *  @author iDempiere (generated) 
 *  @version Release 7.1
 */
@SuppressWarnings("all")
public interface I_BSCA_POSDetaill 
{

    /** TableName=BSCA_POSDetaill */
    public static final String Table_Name = "BSCA_POSDetaill";

    /** AD_Table_ID=1000497 */
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

    /** Column name BSCA_FiscalDocumentNo */
    public static final String COLUMNNAME_BSCA_FiscalDocumentNo = "BSCA_FiscalDocumentNo";

	/** Set Factura POS Fiscal	  */
	public void setBSCA_FiscalDocumentNo (String BSCA_FiscalDocumentNo);

	/** Get Factura POS Fiscal	  */
	public String getBSCA_FiscalDocumentNo();

    /** Column name BSCA_POSDetaill_ID */
    public static final String COLUMNNAME_BSCA_POSDetaill_ID = "BSCA_POSDetaill_ID";

	/** Set POS Detaill	  */
	public void setBSCA_POSDetaill_ID (int BSCA_POSDetaill_ID);

	/** Get POS Detaill	  */
	public int getBSCA_POSDetaill_ID();

    /** Column name BSCA_POSDetaill_UU */
    public static final String COLUMNNAME_BSCA_POSDetaill_UU = "BSCA_POSDetaill_UU";

	/** Set BSCA_POSDetaill_UU	  */
	public void setBSCA_POSDetaill_UU (String BSCA_POSDetaill_UU);

	/** Get BSCA_POSDetaill_UU	  */
	public String getBSCA_POSDetaill_UU();

    /** Column name BSCA_Pos_invoiceaffected */
    public static final String COLUMNNAME_BSCA_Pos_invoiceaffected = "BSCA_Pos_invoiceaffected";

	/** Set Pos Invoiceaffected	  */
	public void setBSCA_Pos_invoiceaffected (String BSCA_Pos_invoiceaffected);

	/** Get Pos Invoiceaffected	  */
	public String getBSCA_Pos_invoiceaffected();

    /** Column name BSCA_StellarDocumento */
    public static final String COLUMNNAME_BSCA_StellarDocumento = "BSCA_StellarDocumento";

	/** Set Stellar Documento	  */
	public void setBSCA_StellarDocumento (String BSCA_StellarDocumento);

	/** Get Stellar Documento	  */
	public String getBSCA_StellarDocumento();

    /** Column name BSCA_StellarRif */
    public static final String COLUMNNAME_BSCA_StellarRif = "BSCA_StellarRif";

	/** Set Stellar Rif	  */
	public void setBSCA_StellarRif (String BSCA_StellarRif);

	/** Get Stellar Rif	  */
	public String getBSCA_StellarRif();

    /** Column name BSCA_isTaxPayer */
    public static final String COLUMNNAME_BSCA_isTaxPayer = "BSCA_isTaxPayer";

	/** Set Tax Payer	  */
	public void setBSCA_isTaxPayer (boolean BSCA_isTaxPayer);

	/** Get Tax Payer	  */
	public boolean isBSCA_isTaxPayer();

    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/** Set Order.
	  * Order
	  */
	public void setC_Order_ID (int C_Order_ID);

	/** Get Order.
	  * Order
	  */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException;

    /** Column name ClientName */
    public static final String COLUMNNAME_ClientName = "ClientName";

	/** Set Client Name	  */
	public void setClientName (String ClientName);

	/** Get Client Name	  */
	public String getClientName();

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

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name FiscalPrinterSerial */
    public static final String COLUMNNAME_FiscalPrinterSerial = "FiscalPrinterSerial";

	/** Set Fiscal Printer Serial	  */
	public void setFiscalPrinterSerial (String FiscalPrinterSerial);

	/** Get Fiscal Printer Serial	  */
	public String getFiscalPrinterSerial();

    /** Column name GrandTotal */
    public static final String COLUMNNAME_GrandTotal = "GrandTotal";

	/** Set Grand Total.
	  * Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal);

	/** Get Grand Total.
	  * Total amount of document
	  */
	public BigDecimal getGrandTotal();

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

    /** Column name LocationName */
    public static final String COLUMNNAME_LocationName = "LocationName";

	/** Set LocationName	  */
	public void setLocationName (String LocationName);

	/** Get LocationName	  */
	public String getLocationName();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name TaxAmtOrder */
    public static final String COLUMNNAME_TaxAmtOrder = "TaxAmtOrder";

	/** Set TaxAmtOrder	  */
	public void setTaxAmtOrder (BigDecimal TaxAmtOrder);

	/** Get TaxAmtOrder	  */
	public BigDecimal getTaxAmtOrder();

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

    /** Column name ticket */
    public static final String COLUMNNAME_ticket = "ticket";

	/** Set ticket	  */
	public void setticket (String ticket);

	/** Get ticket	  */
	public String getticket();
}
