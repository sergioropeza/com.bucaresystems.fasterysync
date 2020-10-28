package com.bucaresystems.fasterysync.model;

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
	  
  public static List<BSCA_ClosedCash> getListClosedCash(){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		List<BSCA_ClosedCash> lstClosedCash = new ArrayList<BSCA_ClosedCash>();	
		String sql = "Select cs.*,r.BSCA_Route_ID FROM closedcash cs "
				+ " LEFT JOIN BSCA_Route r ON r.BSCA_Route_UU = money "
				+ " WHERE (r.docstatus is null or (r.DocStatus NOT IN  ('CO', 'VO')))";
			//	+ " and money IN (select distinct money from receipts where bsca_Isimported = false) ";
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
	

}
