package com.bucaresystems.fasterysync.pos.model;

import org.compiere.util.DB;

public class Taxes{

	public static final String Table_Name = "pos.taxes";
	public static final String View_Name = "pos.BSCA_taxes_v";
	protected Object id;
	protected Object category;
	protected Object rate;
	protected Object parentid;
	protected Object idempiere_id;
	protected Object name;
	protected String trxName;

	public Taxes(String trxName){
		this.trxName = trxName;
	}
	public Taxes(String trxName, Object idempiere_ID){
		this(trxName);
		this.idempiere_id = idempiere_ID;
	}
	public Object getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = id;
	}
	public Object getCategory() {
		return category;
	}
	public void setCategory(Object category) {
		this.category = category;
	}
	public Object getRate() {
		return rate;
	}
	public void setRate(Object rate) {
		this.rate = rate;
	}
	public Object getParentid() {
		return parentid;
	}
	public void setParentid(Object parentid) {
		this.parentid = parentid;
	}
	public Object getIdempiere_id() {
		return idempiere_id;
	}
	public void setIdempiere_id(Object idempiere_id) {
		this.idempiere_id = idempiere_id;
	}
	public Object getName() {
		return name;
	}
	public void setName(Object name) {
		this.name = name;
	}
	public void save(String whereClause) {

		String sql ="Insert Into "+Table_Name+" (id,category,rate,parentid,idempiere_id,name)"+
		"(Select id,category,rate,parentid,idempiere_id,name from "+View_Name+" where 1=1 "+whereClause+")";
		DB.executeUpdateEx(sql, trxName);
	};
	public void update(String whereClause) {

		String sql ="Update "+Table_Name+" a set "+
		"id= b.id,"+
		"category= b.category,"+
		"rate= b.rate,"+
		"parentid= b.parentid,"+
		"idempiere_id= b.idempiere_id,"+
		"name= b.name "+
		"from "+View_Name+" b where  a.id = cast(b.ID as text)  "+whereClause;
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
