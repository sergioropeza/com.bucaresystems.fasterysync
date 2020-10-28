package com.bucaresystems.fasterysync.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.compiere.util.DB;

public class BSCA_Tickets {
	  
	private String id;
	private int tickettype;
	private int ticketid;
	private String person;
	private String customer;
	private Timestamp date;
	private int status;
	  
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getTickettype() {
		return tickettype;
	}
	public void setTickettype(int tickettype) {
		this.tickettype = tickettype;
	}
	public int getTicketid() {
		return ticketid;
	}
	public void setTicketid(int ticketid) {
		this.ticketid = ticketid;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}

	public static List<BSCA_Tickets> getListBSCA_TicketsNotImported(String id){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		List<BSCA_Tickets> lst = new ArrayList<BSCA_Tickets>();	
				
		String sql = "select t.*, r.datenew FROM tickets t "
					+"JOIN receipts r ON r.id = t.id "
					+"where r.money = ? and r.bsca_isimported = false";
		try {
			pstmt = DB.prepareStatement(sql, null);
			
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				BSCA_Tickets ticket = new BSCA_Tickets();		
				 ticket.setId(rs.getString("id"));
				 ticket.setTicketid(rs.getInt("Ticketid"));
				 ticket.setTickettype(rs.getInt("tickettype"));
				 ticket.setPerson(rs.getString("person"));
				 ticket.setCustomer(rs.getString("customer"));
				 ticket.setDate(rs.getTimestamp("datenew"));
				 
				 
				lst.add(ticket);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return lst;
	}
	
	public static List<BSCA_Tickets> getListBSCA_Tickets(String id){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		List<BSCA_Tickets> lst = new ArrayList<BSCA_Tickets>();	
				
		String sql = "select t.*, r.datenew FROM tickets t "
					+"JOIN receipts r ON r.id = t.id "
					+"where r.money = ?";
		try {
			pstmt = DB.prepareStatement(sql, null);
			
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				BSCA_Tickets ticket = new BSCA_Tickets();		
				 ticket.setId(rs.getString("id"));
				 ticket.setTicketid(rs.getInt("tickettype"));
				 ticket.setTickettype(rs.getInt("tickettype"));
				 ticket.setPerson(rs.getString("person"));
				 ticket.setCustomer(rs.getString("customer"));
				 ticket.setDate(rs.getTimestamp("datenew"));
				 
				 
				lst.add(ticket);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return lst;
	}
	
	 public  List<BSCA_TickeLines> getTicketLines(){
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			
			List<BSCA_TickeLines> lst = new ArrayList<BSCA_TickeLines>();	
			String sql = "Select tl.*,p.code FROM ticketlines tl "
					+ "JOIN Products p ON tl.product = p.id "
					+ "where tl.ticket = ?";
			try {
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setString(1, getId());
				rs = pstmt.executeQuery();
				while(rs.next()){
					BSCA_TickeLines ticketLines = new BSCA_TickeLines();		
					ticketLines.setTicket(getId());
					ticketLines.setLine(rs.getInt("line"));
					ticketLines.setProduct(rs.getString("product"));
					ticketLines.setAttributesetinstance_id(rs.getString("attributesetinstance_id"));
					ticketLines.setUnits(rs.getString("units"));
					ticketLines.setPrice(rs.getString("price"));
					ticketLines.setTaxid(rs.getString("taxid"));
					ticketLines.setProductCode(rs.getString("productCode"));		
					lst.add(ticketLines);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
			
			return lst;
		}
	 
	 public  List<BSCA_Payments> getPaymentLines(){
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			
			List<BSCA_Payments> lst = new ArrayList<BSCA_Payments>();	
			String sql = "Select * FROM payments where receipt = ?";
			try {
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setString(1, getId());
				rs = pstmt.executeQuery();
				while(rs.next()){
					BSCA_Payments payment = new BSCA_Payments();
					payment.setId(rs.getString("id"));
					payment.setReceipt(rs.getString("receipt"));
					payment.setPayment(rs.getString("payment"));
					payment.setTotal(rs.getString("total"));
					payment.setTip(rs.getString("tip"));
					payment.setTransid(rs.getString("Transid"));
					payment.setIsprocessed(rs.getBoolean("isProcessed"));
					payment.setNotes(rs.getString("notes"));
					payment.setTendered(rs.getString("tendered"));
					payment.setCardname(rs.getString("cardName"));
					payment.setVoucher(rs.getString("voucher"));
					lst.add(payment);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
			
			return lst;
		}


}
