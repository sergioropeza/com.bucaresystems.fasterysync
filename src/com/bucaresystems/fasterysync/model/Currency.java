package com.bucaresystems.fasterysync.model;

import org.compiere.util.DB;

public class Currency{

	public static final String Table_Name = "pos.bsca_currency";
	protected Object id;
	protected Object name;
	protected Object isocode;
	protected Object cursymbol;
	protected Object multiplyrate;
	protected Object isactive;
	protected Object node_id;
	protected Object idempiere_id;
	protected String trxName;

	public Currency(String trxName){
		this.trxName = trxName;
	}
	public Currency(String trxName, Object idempiere_ID){
		this(trxName);
		this.idempiere_id = idempiere_ID;
	}
	public Object getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = id;
	}
	public Object getName() {
		return name;
	}
	public void setName(Object name) {
		this.name = name;
	}
	public Object getIsocode() {
		return isocode;
	}
	public void setIsocode(Object isocode) {
		this.isocode = isocode;
	}
	public Object getCursymbol() {
		return cursymbol;
	}
	public void setCursymbol(Object cursymbol) {
		this.cursymbol = cursymbol;
	}
	public Object getMultiplyrate() {
		return multiplyrate;
	}
	public void setMultiplyrate(Object multiplyrate) {
		this.multiplyrate = multiplyrate;
	}
	public Object getIsactive() {
		return isactive;
	}
	public void setIsactive(Object isactive) {
		this.isactive = isactive;
	}
	public Object getNode_id() {
		return node_id;
	}
	public void setNode_id(Object node_id) {
		this.node_id = node_id;
	}
	public Object getIdempiere_id() {
		return idempiere_id;
	}
	public void setIdempiere_id(Object idempiere_id) {
		this.idempiere_id = idempiere_id;
	}
	public void save(String whereClause) {

		String sql ="Insert Into "+Table_Name+" (id,name,isocode,cursymbol,multiplyrate,isactive,node_id,idempiere_id)"+
		"(Select id,name,isocode,cursymbol,multiplyrate,isactive,node_id,idempiere_id from pos.bsca_currency_v where 1=1 "+whereClause+")";
		DB.executeUpdateEx(sql, trxName);
	};
	public void update(String whereClause) {

		String sql ="Update "+Table_Name+" a set "+
		"id= b.id,"+
		"name= b.name,"+
		"isocode= b.isocode,"+
		"cursymbol= b.cursymbol,"+
		"multiplyrate= b.multiplyrate,"+
		"isactive= b.isactive,"+
		"node_id= b.node_id,"+
		"idempiere_id= b.idempiere_id "+
		"from pos.bsca_currency_v b where  a.id = cast(b.ID as text)  "+whereClause;
		DB.executeUpdateEx(sql, trxName);
	};
	private boolean isRegister() {

		String sql = "select idempiere_ID from "+Table_Name+" where idempiere_ID='"+idempiere_id+"'";
		int l = DB.getSQLValueEx( trxName, sql);
		return l>0;
	}
	public void save(){

		if (!isRegister()){
			save("and idempiere_ID = "+idempiere_id);
		}else{
			update("and b.idempiere_ID = "+idempiere_id);
		}
	}
};
