package com.bucaresystems.fasterysync.pos.model;

import java.sql.Timestamp;

public class BSCA_TickeLines {

	  private String ticket;
	  private int line;
	  private String product;
	  private String attributesetinstance_id;
	  private String units;
	  private String price;
	  private String taxid;
	  private String productCode; 
	  private Timestamp pOSDate;
	  private String bsca_productValue;
	  
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getAttributesetinstance_id() {
		return attributesetinstance_id;
	}
	public void setAttributesetinstance_id(String attributesetinstance_id) {
		this.attributesetinstance_id = attributesetinstance_id;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getTaxid() {
		return taxid;
	}
	public void setTaxid(String taxid) {
		this.taxid = taxid;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String codeProduct) {
		this.productCode = codeProduct;
	}
	public Timestamp getPOSDate() {
		return pOSDate;
	}
	public void setPOSDate(Timestamp pOSDate) {
		this.pOSDate = pOSDate;
	}
	public String getBsca_productValue() {
		return bsca_productValue;
	}
	public void setBsca_productValue(String bsca_productValue) {
		this.bsca_productValue = bsca_productValue;
	}
	
}
