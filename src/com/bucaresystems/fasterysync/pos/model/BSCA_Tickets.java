package com.bucaresystems.fasterysync.pos.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.compiere.model.PO;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.DB;

public class BSCA_Tickets {
	
    public static final int RECEIPT_NORMAL = 0;
    public static final int RECEIPT_REFUND = 1;
    public static final int RECEIPT_PAYMENT = 2;
    public static final int RECEIPT_NOSALE = 3;
    
    public static final int REFUND_NOT = 0; // is a non-refunded ticket    
    public static final int REFUND_PARTIAL = 1;
    public static final int REFUND_ALL = 2;
	  
	private String id;
	private int tickettype;
	private int ticketid;
	private String person;
	private String customer;
	private Timestamp date;
	private int status;
	private String orgValue; 
	private String taxAmt;
	private String grandTotal; 
	private String fiscaldocumentno; 
	private String machinefiscalnumber;
	private String taxid; 
	private String customerAddress;
	private String customerName;
	  
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
	public int getTicketRefundID() {
		return status;
	}
	public void setRefundTicketID(int status) {
		this.status = status;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}

	public static List<BSCA_Tickets> getTicketsNotSummaryNotImported(String id,String orgvalue){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		List<BSCA_Tickets> lst = new ArrayList<BSCA_Tickets>();	
				
		String sql = "select t.*, r.datenew "
					+ ",c.taxid , c.name as customername "
					+ " FROM pos.tickets t "
					+"JOIN pos.receipts r ON r.id = t.id "
					+" join pos.customers c on c.id  = t.customer "+
					"where\n" + 
					"r.money = '"+id+"' and \n" + 
					"r.bsca_isimported = false and r.orgvalue  = '"+orgvalue+"' and \n" + 
					"t.id  in (select t.id from pos.tickets t \n" + 
					"\t\t\tjoin pos.receipts r on t.id = r.id \n" + 
					"\t\t\tjoin pos.payments p on p.receipt  = t.id \n" + 
					"\t\t\tJOIN C_POSTenderType pt ON pt.c_postendertype_id = p.bsca_postendertype_id::numeric \n" + 
					"\t\t\twhere r.bsca_isimported  = false and r.orgvalue  ='"+orgvalue+"' and pt.BSCA_IsNotPaySummary = 'Y')\n" ;
		try {
			pstmt = DB.prepareStatement(sql, null);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				BSCA_Tickets ticket = new BSCA_Tickets();		
				 ticket.setId(rs.getString("id"));
				 ticket.setTicketid(rs.getInt("Ticketid"));
				 ticket.setTickettype(rs.getInt("tickettype"));
				 ticket.setPerson(rs.getString("person"));
				 ticket.setCustomer(rs.getString("customer"));
				 ticket.setDate(rs.getTimestamp("datenew"));
				 ticket.setTaxid(rs.getString("taxid"));
				 ticket.setCustomerName(rs.getString("customername"));
				 
				 
				 
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
	
	
	public static List<BSCA_PaymentInstaPago> getPaymentVPOS(PO route, String orgValue, String host){
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Timestamp startDate = Timestamp.valueOf(route.get_ValueAsString("startDate"));
		Timestamp endDate = Timestamp.valueOf(route.get_ValueAsString("endDate"));
		
		List<BSCA_PaymentInstaPago> lst = new ArrayList<BSCA_PaymentInstaPago>();	
				
		String sql = "select *,to_timestamp(bp.datetime, 'MM-dd-YYYY HH24:mi:ss AM')::timestamp as dateTrx from pos.bsca_paymentinstapago bp \n" + 
				"where to_timestamp(bp.datetime, 'MM-dd-YYYY HH24:mi:ss AM')::timestamp >= ? "
				+" and to_timestamp(bp.datetime, 'MM-dd-YYYY HH24:mi:ss AM')::timestamp <= ? and bp.orgvalue = ?\n" + 
				" and bp.host  = ?";
		try {
			pstmt = DB.prepareStatement(sql, null);
			
			pstmt.setTimestamp(1, startDate);
			pstmt.setTimestamp(2, endDate);
			pstmt.setString(3, orgValue);
			pstmt.setString(4, host);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				 BSCA_PaymentInstaPago paymentVPOS = new BSCA_PaymentInstaPago();	
			 	 paymentVPOS.setDeferre(rs.getString("deferre"));
				 paymentVPOS.setCode(rs.getString("code"));
				 paymentVPOS.setCardholderidL(rs.getString("cardholderidL"));
				 paymentVPOS.setVoucher(rs.getString("voucher"));
				 paymentVPOS.setLote(rs.getString("lote"));
				 paymentVPOS.setDescription(rs.getString("description"));
				 paymentVPOS.setCardline(rs.getString("cardline"));
				 paymentVPOS.setTsi(rs.getString("tsi"));
				 paymentVPOS.setCommerce(rs.getString("commerce"));
				 paymentVPOS.setDatetime(rs.getTimestamp("datetrx"));
				 paymentVPOS.setOrdernumber(rs.getString("ordernumber"));
				 paymentVPOS.setId(rs.getString("id"));
				 paymentVPOS.setCardtype(rs.getString("cardtype"));
				 paymentVPOS.setIdmerchant(rs.getString("idmerchant"));
				 paymentVPOS.setResponsecode(rs.getString("responsecode"));
				 paymentVPOS.setAmount(rs.getString("amount"));
				 paymentVPOS.setApproval(rs.getString("approval"));
				 paymentVPOS.setTerminal(rs.getString("terminal"));
				 paymentVPOS.setMessage(rs.getString("message"));
				 paymentVPOS.setAuthid(rs.getString("authid"));
				 paymentVPOS.setArqc(rs.getString("arqc"));
				 paymentVPOS.setTc(rs.getString("tc"));
				 paymentVPOS.setSequence(rs.getString("sequence"));
				 paymentVPOS.setTvr(rs.getString("tvr"));
				 paymentVPOS.setNa(rs.getString("na"));
				 paymentVPOS.setSuccess(rs.getString("success"));
				 paymentVPOS.setName(rs.getString("name"));
				 paymentVPOS.setCardnumber(rs.getString("cardnumber"));
				 paymentVPOS.setAid(rs.getString("aid"));
				 paymentVPOS.setBank(rs.getString("bank"));
				 paymentVPOS.setReference(rs.getString("reference"));
				 paymentVPOS.setOrgValue(rs.getString("orgValue"));
				 paymentVPOS.setJson(rs.getString("json"));
				 paymentVPOS.setHost(rs.getString("host"));
	  			 lst.add(paymentVPOS);
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
	
	public static List<BSCA_PaymentInstaPago> getPaymentVPOSNotImported(){
			
		List<BSCA_PaymentInstaPago> lst = new ArrayList<BSCA_PaymentInstaPago>();	
				
		String sql = "select bp.*,coalesce (br.bsca_route_id,-1) as bsca_route_ID,to_timestamp(bp.datetime, 'MM-dd-YYYY HH24:mi:ss AM')::timestamp as dateTrx  from pos.bsca_paymentinstapago bp \n" + 
				"left join pos.closedcash c on c.hostsequence::text  = bp.ordernumber and c.orgvalue  = bp.orgvalue and c.host  = bp.host \n" + 
				"left join bsca_route br  on br.closedcashid  = c.money \n" + 
				"where bsca_isimported = false ";
		CPreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = DB.prepareStatement(sql, null);
					
			rs = pstmt.executeQuery();
			while(rs.next()){
				 BSCA_PaymentInstaPago paymentVPOS = new BSCA_PaymentInstaPago();	
			 	 paymentVPOS.setDeferre(rs.getString("deferre"));
				 paymentVPOS.setCode(rs.getString("code"));
				 paymentVPOS.setCardholderidL(rs.getString("cardholderidL"));
				 paymentVPOS.setVoucher(rs.getString("voucher"));
				 paymentVPOS.setLote(rs.getString("lote"));
				 paymentVPOS.setDescription(rs.getString("description"));
				 paymentVPOS.setCardline(rs.getString("cardline"));
				 paymentVPOS.setTsi(rs.getString("tsi"));
				 paymentVPOS.setCommerce(rs.getString("commerce"));
				 paymentVPOS.setDatetime(rs.getTimestamp("dateTrx"));
				 paymentVPOS.setOrdernumber(rs.getString("ordernumber"));
				 paymentVPOS.setId(rs.getString("id"));
				 paymentVPOS.setCardtype(rs.getString("cardtype"));
				 paymentVPOS.setIdmerchant(rs.getString("idmerchant"));
				 paymentVPOS.setResponsecode(rs.getString("responsecode"));
				 paymentVPOS.setAmount(rs.getString("amount"));
				 paymentVPOS.setApproval(rs.getString("approval"));
				 paymentVPOS.setTerminal(rs.getString("terminal"));
				 paymentVPOS.setMessage(rs.getString("message"));
				 paymentVPOS.setAuthid(rs.getString("authid"));
				 paymentVPOS.setArqc(rs.getString("arqc"));
				 paymentVPOS.setTc(rs.getString("tc"));
				 paymentVPOS.setSequence(rs.getString("sequence"));
				 paymentVPOS.setTvr(rs.getString("tvr"));
				 paymentVPOS.setNa(rs.getString("na"));
				 paymentVPOS.setSuccess(rs.getString("success"));
				 paymentVPOS.setName(rs.getString("name"));
				 paymentVPOS.setCardnumber(rs.getString("cardnumber"));
				 paymentVPOS.setAid(rs.getString("aid"));
				 paymentVPOS.setBank(rs.getString("bank"));
				 paymentVPOS.setReference(rs.getString("reference"));
				 paymentVPOS.setOrgValue(rs.getString("orgValue"));
				 paymentVPOS.setJson(rs.getString("json"));
				 paymentVPOS.setHost(rs.getString("host"));
				 paymentVPOS.setBSCA_Route_ID(rs.getInt("BSCA_Route_ID"));
	  			 lst.add(paymentVPOS);
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

	public static List<BSCA_Tickets> getTicketsSummaryNotImported(String closedcash_ID, String orgvalue){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		List<BSCA_Tickets> lst = new ArrayList<BSCA_Tickets>();	
				
		String sql = "select date_trunc('day', r.datenew) as datenew, t.tickettype , min(ticketid) as TicketID ,r.orgValue"
				+ " FROM pos.tickets t \n" + 
				"JOIN pos.receipts r ON r.id = t.id \n" + 
				"where\n" + 
				"r.money = ? and \n" + 
				"r.bsca_isimported = false and r.orgvalue  = '"+orgvalue+"' and \n" + 
				"t.id not in (select t.id from pos.tickets t \n" + 
				"\t\t\tjoin pos.receipts r on t.id = r.id \n" + 
				"\t\t\tjoin pos.payments p on p.receipt  = t.id \n" + 
				"\t\t\tJOIN C_POSTenderType pt ON pt.c_postendertype_id = p.bsca_postendertype_id::numeric \n" + 
				"\t\t\twhere r.bsca_isimported  = false and r.orgvalue  ='"+orgvalue+"' and pt.BSCA_IsNotPaySummary = 'Y')\n" + 
				"group by date_trunc('day', r.datenew), t.tickettype,r.orgValue \n";
		try {
			pstmt = DB.prepareStatement(sql, null);
			
			pstmt.setString(1, closedcash_ID);
			rs = pstmt.executeQuery();
			while(rs.next()){
				BSCA_Tickets ticket = new BSCA_Tickets();	
				 ticket.setTicketid(rs.getInt("Ticketid"));
				 ticket.setTickettype(rs.getInt("tickettype"));
				 ticket.setDate(rs.getTimestamp("dateNew"));
				 ticket.setOrgValue(rs.getString("OrgValue"));
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
	
	public static List<BSCA_Tickets> getTicketsDetaillSummary(String closedcash_ID, String orgValue, int ticketType, Timestamp dateTicket){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		List<BSCA_Tickets> lstMA_Pagos = new ArrayList<BSCA_Tickets>();

	
		String sql = "";
		 
		sql = "select r.TaxAmt,r.grandTotal,r.datenew,t.status,t.bsca_fiscaldocumentno ,t.bsca_machinefiscalnumber,\n" + 
				" t.ticketid, t.id, c.taxid, c.address, c.\"name\" \n" + 
				" FROM pos.tickets t\n" + 
				" JOIN pos.receipts r ON r.id = t.id \n" + 
				" left join pos.customers c  on t.customer  = c.id \n" + 
				" where\n" + 
				" r.money = '"+closedcash_ID+"' and \n" + 
				" r.bsca_isimported = false and r.orgvalue  = '"+orgValue+"' and  t.tickettype = " +ticketType + " and date_trunc('day', r.datenew) = '"+dateTicket+"'"+
				" and t.id not in (select t.id from pos.tickets t \n" + 
				" join pos.receipts r on t.id = r.id \n" + 
				" join pos.payments p on p.receipt  = t.id \n" + 
				" JOIN C_POSTenderType pt ON pt.c_postendertype_id = p.bsca_postendertype_id::numeric \n" + 
				" where r.bsca_isimported  = false and r.orgvalue  ='"+orgValue+"' and pt.BSCA_IsNotPaySummary = 'Y')\n";
		
		pstmt = DB.prepareStatement(sql, null);
		try {
			rs = pstmt.executeQuery();
			while(rs.next()){
				BSCA_Tickets ma_Pagos = new BSCA_Tickets();							
				ma_Pagos.setOrgValue(orgValue);
				ma_Pagos.setTaxAmt(rs.getString("TaxAmt"));
				ma_Pagos.setGrandTotal(rs.getString("grandTotal"));
				ma_Pagos.setTicketid( rs.getInt("ticketid"));
				ma_Pagos.setRefundTicketID(rs.getInt("status"));
				ma_Pagos.setFiscaldocumentno(rs.getString("bsca_fiscaldocumentno"));
				ma_Pagos.setMachinefiscalnumber(rs.getString("bsca_machinefiscalnumber"));
				ma_Pagos.setDate(rs.getTimestamp("datenew"));
				ma_Pagos.setId(rs.getString("id"));
				ma_Pagos.setTaxid(rs.getString("taxid"));
				ma_Pagos.setCustomerAddress(rs.getString("address"));
				ma_Pagos.setCustomerName(rs.getString("name"));
				lstMA_Pagos.add(ma_Pagos);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return lstMA_Pagos;
	}
	
	public static List<BSCA_Tickets> getTicketsDetaillNotSummary(String closedcash_ID, String orgValue, int ticketType, Timestamp dateTicket){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		List<BSCA_Tickets> lstMA_Pagos = new ArrayList<BSCA_Tickets>();
		 
		String sql = "select r.TaxAmt,r.grandTotal,r.datenew,t.status,t.bsca_fiscaldocumentno ,t.bsca_machinefiscalnumber,\n" + 
				" t.ticketid, t.id, c.taxid, c.address, c.\"name\" \n" + 
				" FROM pos.tickets t\n" + 
				" JOIN pos.receipts r ON r.id = t.id \n" + 
				" left join pos.customers c  on t.customer  = c.id \n" + 
				" where\n" + 
				" r.money = '"+closedcash_ID+"' and \n" + 
				" r.bsca_isimported = false and r.orgvalue  = '"+orgValue+"' \n" + 
				" and t.tickettype = "+ticketType + " and r.datenew = '"+dateTicket+"'"	
				+ " and t.id  in (select t.id from pos.tickets t \n" + 
						" join pos.receipts r on t.id = r.id \n" + 
						" join pos.payments p on p.receipt  = t.id \n" + 
						" JOIN C_POSTenderType pt ON pt.c_postendertype_id = p.bsca_postendertype_id::numeric \n" + 
						" where r.bsca_isimported  = false and r.orgvalue  ='"+orgValue+"' and pt.BSCA_IsNotPaySummary = 'Y')\n";
		
		pstmt = DB.prepareStatement(sql, null);
		try {
			rs = pstmt.executeQuery();
			while(rs.next()){
				BSCA_Tickets ma_Pagos = new BSCA_Tickets();							
				ma_Pagos.setOrgValue(orgValue);
				ma_Pagos.setTaxAmt(rs.getString("TaxAmt"));
				ma_Pagos.setGrandTotal(rs.getString("grandTotal"));
				ma_Pagos.setTicketid( rs.getInt("ticketid"));
				ma_Pagos.setRefundTicketID(rs.getInt("status"));
				ma_Pagos.setFiscaldocumentno(rs.getString("bsca_fiscaldocumentno"));
				ma_Pagos.setMachinefiscalnumber(rs.getString("bsca_machinefiscalnumber"));
				ma_Pagos.setDate(rs.getTimestamp("datenew"));
				ma_Pagos.setId(rs.getString("id"));
				ma_Pagos.setTaxid(rs.getString("taxid"));
				ma_Pagos.setCustomerAddress(rs.getString("address"));
				ma_Pagos.setCustomerName(rs.getString("name"));
				lstMA_Pagos.add(ma_Pagos);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		
		return lstMA_Pagos;
	}
	
	public static List<BSCA_Tickets> getListBSCA_Tickets(String id){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		List<BSCA_Tickets> lst = new ArrayList<BSCA_Tickets>();	
				
		String sql = "select t.*, r.datenew FROM pos.tickets t "
					+"JOIN pos.receipts r ON r.id = t.id "
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
			String sql = "Select tl.*,p.code FROM pos.ticketlines tl "
					+ "JOIN pos.Products p ON tl.product = p.id "
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
					ticketLines.setBsca_productValue(rs.getString("productCode"));
					ticketLines.setProductCode(rs.getString("code"));
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
			String sql = "Select * FROM pos.payments where receipt = ?";
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
					payment.setMultiplyrate(rs.getString("multiplyrate"));
					payment.setBsca_postendertype_id(rs.getString("bsca_postendertype_id"));
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
	 
	 public List<BSCA_TickeLines> getListSummaryTicketsLine(String closeCash_ID, int ticketType){
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			
			List<BSCA_TickeLines> lstTicketsLine = new ArrayList<BSCA_TickeLines>();		
			
			String sql = "select tl.productcode, sum(tl.units) as units, tl.price,t2.idempiere_id as taxid, \n" + 
					"MIN(r.datenew) as dateNew\n" + 
					"from pos.ticketlines tl \n" +
				    "join pos.receipts r on r.id = tl.ticket " + 
					"join pos.tickets t on t.id = r.id \n" + 
					"join pos.taxes t2 on tl.taxid  = t2.id "+
					"where r.money = '"+closeCash_ID+"' and t.ticketType = "+ticketType+" and date_trunc('day', r.datenew) = '"+date+"' and r.bsca_isimported = false and r.orgvalue = '"+orgValue+"'\n" + 
					"and   t.id not in (select t.id from pos.tickets t \n" + 
					" join pos.receipts r on t.id = r.id \n" + 
					" join pos.payments p on p.receipt  = t.id \n" + 
					" JOIN C_POSTenderType pt ON pt.c_postendertype_id = p.bsca_postendertype_id::numeric \n" + 
					" where r.bsca_isimported  = false and r.orgvalue  ='"+orgValue+"'  and pt.BSCA_IsNotPaySummary = 'Y')\n" + 
					"group by  tl.productcode, tl.price, t2.idempiere_id \n" + 
					" \n";

			try { 
				pstmt = DB.prepareStatement(sql, null);
				rs = pstmt.executeQuery();
				while(rs.next()){
					BSCA_TickeLines ticketLine = new BSCA_TickeLines();
					ticketLine.setUnits(rs.getString("units"));
					ticketLine.setPrice(rs.getString("price"));
					ticketLine.setTaxid(rs.getString("taxid"));
					ticketLine.setPOSDate(rs.getTimestamp("dateNew"));
					ticketLine.setProductCode(rs.getString("productcode"));
					lstTicketsLine.add(ticketLine);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
			
			return lstTicketsLine;
		}
	 
	 public List<BSCA_Payments> getListSummaryPayments(String closeCash_ID, int ticketType){
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			
			List<BSCA_Payments> lstPayments = new ArrayList<BSCA_Payments>();	
			
			String sql = " select bsca_postendertype_id,multiplyrate, sum(p.total) as total\n" + 
					" from pos.payments p \n" + 
					" join pos.receipts r on r.id = p.receipt\n" + 
					" join pos.tickets t on t.id = r.id \n" + 
					" where r.bsca_isimported = false and r.money  = '"+closeCash_ID+"' and date_trunc('day', r.datenew) = '"+date+"' and t.ticketType = "+ticketType+"  and r.orgvalue  = '"+orgValue+"' \n" + 
					" and   r.id not in (select r.id from pos.receipts r \n" + 
					" join pos.payments p on p.receipt  = r.id \n" + 
					" JOIN C_POSTenderType pt ON pt.c_postendertype_id = p.bsca_postendertype_id::numeric \n" + 
					" where r.bsca_isimported  = false and r.orgvalue  ='"+orgValue+"'  and pt.BSCA_IsNotPaySummary = 'Y')\n" + 
					" group by bsca_postendertype_id,multiplyrate\n";
			try {
				pstmt = DB.prepareStatement(sql, null);
				rs = pstmt.executeQuery();
				while(rs.next()){
					BSCA_Payments payment = new BSCA_Payments();
					
					payment.setTotal(rs.getString("total"));	
					payment.setBsca_postendertype_id(rs.getString("bsca_postendertype_id"));
					payment.setMultiplyrate(rs.getString("multiplyrate"));
					lstPayments.add(payment);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
			
			return lstPayments;
		}
	 
	 public List<BSCA_SummaryTax> getListSummaryTax(){
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			
			List<BSCA_SummaryTax> lstsummaryTax = new ArrayList<BSCA_SummaryTax>();		
			
			String sql = "select sum(tl.linenetamt) as linenetamt, sum(tl.pricetax) as pricetax, \n" + 
					"sum(tl.linenetamt + tl.pricetax) as total, t2.idempiere_ID  "+
				     "from pos.ticketlines tl\n" + 
					"join pos.tickets t on tl.ticket = t.id \n" + 
					"join pos.receipts r on r.id = t.id \n" + 
					"join pos.taxes t2 on tl.taxid  = t2.id "+
					" where r.bsca_isimported = false and t.id  = '"+id+"' and r.orgvalue  = '"+orgValue+"' \n" + 
					"group by  t2.idempiere_ID\n";

			try { 
				pstmt = DB.prepareStatement(sql, null);
				rs = pstmt.executeQuery();
				while(rs.next()){
					BSCA_SummaryTax summaryTax = new BSCA_SummaryTax();
					summaryTax.setLineNetAmt(rs.getString("linenetamt"));
					summaryTax.setPriceTax(rs.getString("pricetax"));
					summaryTax.setTax_ID(rs.getString("idempiere_ID"));
					summaryTax.setTotal(rs.getString("total"));
					lstsummaryTax.add(summaryTax);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}		
			return lstsummaryTax;
		}
	 
	public String getOrgValue() {
		return orgValue;
	}
	public void setOrgValue(String orgValue) {
		this.orgValue = orgValue;
	}
	public String getTaxAmt() {
		return taxAmt;
	}
	public void setTaxAmt(String taxAmt) {
		this.taxAmt = taxAmt;
	}
	public String getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}
	public String getFiscaldocumentno() {
		return fiscaldocumentno;
	}
	public void setFiscaldocumentno(String fiscaldocumentno) {
		this.fiscaldocumentno = fiscaldocumentno;
	}
	public String getMachinefiscalnumber() {
		return machinefiscalnumber;
	}
	public void setMachinefiscalnumber(String machinefiscalnumber) {
		this.machinefiscalnumber = machinefiscalnumber;
	}
	public String getTaxid() {
		return taxid;
	}
	public void setTaxid(String taxid) {
		this.taxid = taxid;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String custotmerAddress) {
		this.customerAddress = custotmerAddress;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


}
