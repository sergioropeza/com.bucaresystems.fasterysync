package com.bucaresystems.fasterysync.pos.model;

import java.math.BigDecimal;

public class BSCA_Payments {
	
	  private String id; 
	  private String receipt; 
	  private String payment ;
	  private String total;
	  private String tip;
	  private String transid;
	  private Boolean isprocessed;
	  private String notes;
	  private String  tendered;
	  private String cardname;
	  private String voucher;
	  private String bsca_postendertype_id; 
	  private String multiplyrate; 
	  
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReceipt() {
		return receipt;
	}
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public String getTransid() {
		return transid;
	}
	public void setTransid(String transid) {
		this.transid = transid;
	}
	public Boolean getIsprocessed() {
		return isprocessed;
	}
	public void setIsprocessed(Boolean isprocessed) {
		this.isprocessed = isprocessed;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getTendered() {
		return tendered;
	}
	public void setTendered(String tendered) {
		this.tendered = tendered;
	}
	public String getCardname() {
		return cardname;
	}
	public void setCardname(String cardname) {
		this.cardname = cardname;
	}
	public String getVoucher() {
		return voucher;
	}
	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}
	public String getBsca_postendertype_id() {
		return bsca_postendertype_id;
	}
	public void setBsca_postendertype_id(String bsca_postendertype_id) {
		this.bsca_postendertype_id = bsca_postendertype_id;
	}
	public String getMultiplyrate() {
		return multiplyrate;
	}
	public void setMultiplyrate(String multiplyrate) {
		this.multiplyrate = multiplyrate;
	}

	  
}
