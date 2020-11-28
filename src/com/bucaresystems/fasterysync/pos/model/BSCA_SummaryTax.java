package com.bucaresystems.fasterysync.pos.model;

public class BSCA_SummaryTax {
	
	private String lineNetAmt; 
	private String priceTax; 
	private String total; 
	private String tax_ID;
	public String getLineNetAmt() {
		return lineNetAmt;
	}
	public void setLineNetAmt(String lineNetAmt) {
		this.lineNetAmt = lineNetAmt;
	}
	public String getPriceTax() {
		return priceTax;
	}
	public void setPriceTax(String priceTax) {
		this.priceTax = priceTax;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getTax_ID() {
		return tax_ID;
	}
	public void setTax_ID(String tax_ID) {
		this.tax_ID = tax_ID;
	} 
	

}
