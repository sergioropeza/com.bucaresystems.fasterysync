package com.bucaresystems.fasterysync.pos.model;

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

	public static List<BSCA_Tickets> getTicketsNotImported(String id){
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
	
	
	public static List<BSCA_Tickets> getTicketsSummaryNotImported(String closedcash_ID, String orgvalue){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		List<BSCA_Tickets> lst = new ArrayList<BSCA_Tickets>();	
				
		String sql = "select date_trunc('month', r.datenew) as datenew, t.tickettype , min(ticketid) as TicketID FROM pos.tickets t \n" + 
				"JOIN pos.receipts r ON r.id = t.id \n" + 
				"where\n" + 
				"r.money = ? and \n" + 
				"r.bsca_isimported = false and r.orgvalue  = '"+orgvalue+"' and \n" + 
				"t.id not in (select t.id from pos.tickets t \n" + 
				"\t\t\tjoin pos.receipts r on t.id = r.id \n" + 
				"\t\t\tjoin pos.payments p on p.receipt  = t.id \n" + 
				"\t\t\tJOIN C_POSTenderType pt ON pt.c_postendertype_id = p.bsca_postendertype_id::numeric \n" + 
				"\t\t\twhere r.bsca_isimported  = false and r.orgvalue  ='"+orgvalue+"' and pt.BSCA_IsNotPaySummary = 'Y')\n" + 
				"group by date_trunc('month', r.datenew), t.tickettype\n";
		try {
			pstmt = DB.prepareStatement(sql, null);
			
			pstmt.setString(1, closedcash_ID);
			rs = pstmt.executeQuery();
			while(rs.next()){
				BSCA_Tickets ticket = new BSCA_Tickets();		
				 ticket.setTicketid(rs.getInt("Ticketid"));
				 ticket.setTickettype(rs.getInt("tickettype"));
				 ticket.setDate(rs.getTimestamp("dateNew"));
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
	
	public static List<BSCA_Tickets> getTicketsDetaillNotImported(String closedcash_ID, String orgvalue){
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		List<BSCA_Tickets> lstMA_Pagos = new ArrayList<BSCA_Tickets>();

	
		String sql = "";
		 
		sql = " Select n_impuesto, n_total, f_fecha,p.Cs_documento_rel,"
				  +" cu_documentostellar, cu_documentofiscal, cu_serialimpresora,p.C_numero, p.id ,p.c_rif,p.cu_direccion_cliente,p.c_desc_cliente "
				  +" FROM MA_Pagos p  "
				  +" LEFT JOIN ma_documentos_fiscal df ON df.cu_documentostellar = p.c_numero And p.c_sucursal = df.cu_localidad and cu_documentoTipo = p.C_concepto "  
				  +" where p.c_caja = '"+c_caja+"' and p.turno ="+turno+" and p.c_sucursal= '"+c_sucursal+"' and p.f_fecha = '"+f_fecha+"' and p.c_concepto = '"+c_concepto+"' and bsca_isimported = false"
				  +" and p.C_Numero NOT IN ("
				  +" select C_factura FROM MA_Pagos p  "
				  +	" JOIN MA_detallePAgo dp ON dp.C_factura = p.c_numero"
				  +	" JOIN C_POSTenderType pt ON pt.value = dp.C_coddenominacion"
				  +	" where (p.C_caja ||p.turno)::text = "+DB.TO_STRING(c_caja+turno) + " and pt.BSCA_IsNotPaySummary = 'Y' and p.c_sucursal = "+DB.TO_STRING(c_sucursal) + " and bsca_isimported = false)";
		
		pstmt = DB.prepareStatement(sql, null);
		try {
			rs = pstmt.executeQuery();
			while(rs.next()){
				BSCA_Tickets ma_Pagos = new BSCA_Tickets();							
				ma_Pagos.setC_sucursal(c_sucursal);
				ma_Pagos.setN_impuesto(rs.getString("n_impuesto"));
				ma_Pagos.setN_total( rs.getString("n_total"));
				ma_Pagos.setCu_documentostellar( rs.getString("cu_documentostellar"));
				ma_Pagos.setCs_documento_rel(rs.getString("cs_documento_rel"));
				ma_Pagos.setCu_documentofiscal(rs.getString("cu_documentofiscal"));
				ma_Pagos.setCu_serialimpresora(rs.getString("cu_serialimpresora"));
				ma_Pagos.setF_fecha(rs.getTimestamp("f_fecha"));
				ma_Pagos.setC_numero(rs.getString("C_numero"));
				ma_Pagos.setId(rs.getInt("id"));
				ma_Pagos.setC_rif(rs.getString("c_rif"));
				ma_Pagos.setCu_direccion_cliente(rs.getString("cu_direccion_cliente"));
				ma_Pagos.setC_desc_cliente(rs.getString("c_desc_cliente"));
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
