package com.bucaresystems.fasterysync.pos.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.compiere.util.DB;

public class BSCA_ClosedCash {
	
	  private String money;
	  private String host; // caja
	  private int hostsequence;  // turno
	  private Timestamp datestart;
	  private Timestamp dateend;
	  private int nosales;
	  private int BSCA_Route_ID;
	  private int AD_User_ID;
	  private int qtySales; 
	  private int qtyRefunds;
	  
	  
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getHostsequence() {
		return hostsequence;
	}
	public void setHostsequence(int hostsequence) {
		this.hostsequence = hostsequence;
	}
	public Timestamp getDatestart() {
		return datestart;
	}
	public void setDatestart(Timestamp datestart) {
		this.datestart = datestart;
	}
	public Timestamp getDateend() {
		return dateend;
	}
	public void setDateend(Timestamp dateend) {
		this.dateend = dateend;
	}
	public int getNosales() {
		return nosales;
	}
	public void setNosales(int nosales) {
		this.nosales = nosales;
	}
	
	public int getBSCA_Route_ID() {
		return BSCA_Route_ID;
	}
	public void setBSCA_Route_ID(int bSCA_Route_ID) {
		BSCA_Route_ID = bSCA_Route_ID;
	}
	  
  public static List<BSCA_ClosedCash> getListClosedCash(String orgValue){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		List<BSCA_ClosedCash> lstClosedCash = new ArrayList<BSCA_ClosedCash>();	
		String sql = "Select cs.*,r.BSCA_Route_ID,p.idempiere_ID as AD_User_ID "
				+ " FROM pos.closedcash cs "
				+ " LEFT JOIN pos.people p ON p.id = cs.people"
				+ " LEFT JOIN BSCA_Route r ON r.closedcashID = money "
				+ " WHERE (r.docstatus is null or (r.DocStatus NOT IN  ('CO', 'VO')))"
				+ " and money IN (select distinct money from pos.receipts where bsca_Isimported = false) and cs.orgValue = '"+orgValue+"'";
		try {
			pstmt = DB.prepareStatement(sql, null);
			rs = pstmt.executeQuery();
			while(rs.next()){
				BSCA_ClosedCash closedCash = new BSCA_ClosedCash();		
				closedCash.setMoney(rs.getString("money"));
				closedCash.setHost(rs.getString("host"));
				closedCash.setHostsequence(rs.getInt("hostsequence"));
				closedCash.setDatestart(rs.getTimestamp("datestart"));
				closedCash.setDateend(rs.getTimestamp("dateend"));
				closedCash.setNosales(rs.getInt("nosales"));
				closedCash.setBSCA_Route_ID(rs.getInt("BSCA_Route_ID"));
				closedCash.setAD_User_ID(rs.getInt("AD_User_ID"));
				closedCash.setQtySales(rs.getInt("QtySales"));
				closedCash.setQtyRefunds(rs.getInt("QtyRefund"));
				lstClosedCash.add(closedCash);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return lstClosedCash;
	}
  
	public int getAD_User_ID() {
		return AD_User_ID;
	}
	public void setAD_User_ID(int aD_User_ID) {
		AD_User_ID = aD_User_ID;
	}
	public int getQtySales() {
		return qtySales;
	}
	public void setQtySales(int qtySales) {
		this.qtySales = qtySales;
	}
	public int getQtyRefunds() {
		return qtyRefunds;
	}
	public void setQtyRefunds(int qtyRefunds) {
		this.qtyRefunds = qtyRefunds;
	}
	

}
