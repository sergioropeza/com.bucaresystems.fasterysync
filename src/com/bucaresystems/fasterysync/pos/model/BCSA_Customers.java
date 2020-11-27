package com.bucaresystems.fasterysync.pos.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.compiere.util.DB;

public class BCSA_Customers {
	
	  private String id;
	  private String searchkey;
	  private String taxid;
	  private String name;
	  private String taxcategory;
	  private String card;
	  private BigDecimal maxdebt;
	  private String address;
	  private String address2;
	  private String postal;
	  private String city;
	  private String region;
	  private String country;
	  private String firstname;
	  private String lastname;
	  private String email;
	  private String phone;
	  private String phone2;
	  private String fax;
	  private String notes;
	  private Boolean visible;
	  private Timestamp curdate;
	  private BigDecimal curdebt;
	  private Boolean isvip;
	  private BigDecimal discount;
	  private Timestamp memodate;
	  private Timestamp gender;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSearchkey() {
		return searchkey;
	}
	public void setSearchkey(String searchkey) {
		this.searchkey = searchkey;
	}
	public String getTaxid() {
		return taxid;
	}
	public void setTaxid(String taxid) {
		this.taxid = taxid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTaxcategory() {
		return taxcategory;
	}
	public void setTaxcategory(String taxcategory) {
		this.taxcategory = taxcategory;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public BigDecimal getMaxdebt() {
		return maxdebt;
	}
	public void setMaxdebt(BigDecimal maxdebt) {
		this.maxdebt = maxdebt;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getPostal() {
		return postal;
	}
	public void setPostal(String postal) {
		this.postal = postal;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	public Timestamp getCurdate() {
		return curdate;
	}
	public void setCurdate(Timestamp curdate) {
		this.curdate = curdate;
	}
	public BigDecimal getCurdebt() {
		return curdebt;
	}
	public void setCurdebt(BigDecimal curdebt) {
		this.curdebt = curdebt;
	}
	public Boolean getIsvip() {
		return isvip;
	}
	public void setIsvip(Boolean isvip) {
		this.isvip = isvip;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public Timestamp getMemodate() {
		return memodate;
	}
	public void setMemodate(Timestamp memodate) {
		this.memodate = memodate;
	}
	public Timestamp getGender() {
		return gender;
	}
	public void setGender(Timestamp gender) {
		this.gender = gender;
	}
	  
	public static BCSA_Customers getCustomer(String id){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		BCSA_Customers customers = new BCSA_Customers();
				
		String sql = "select * FROM customers where id = ?";
		try {
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				customers.setTaxid(rs.getString("taxid"));
				customers.setName(rs.getString("name"));
				customers.setEmail(rs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return customers;
	}

}
