package com.bucaresystems.fasterysync.model;

import org.compiere.util.DB;

public class People{

	public static final String Table_Name = "people";
	protected Object role;
	protected Object ad_user_id;
	protected Object idempiere_id;
	protected Object id;
	protected Object node_id;
	protected Object name;
	protected Object apppassword;
	protected String trxName;

	public People(String trxName){
		this.trxName = trxName;
	}
	public People(String trxName, Object idempiere_ID){
		this(trxName);
		this.idempiere_id = idempiere_ID;
	}
	public Object getRole() {
		return role;
	}
	public void setRole(Object role) {
		this.role = role;
	}
	public Object getAd_user_id() {
		return ad_user_id;
	}
	public void setAd_user_id(Object ad_user_id) {
		this.ad_user_id = ad_user_id;
	}
	public Object getIdempiere_id() {
		return idempiere_id;
	}
	public void setIdempiere_id(Object idempiere_id) {
		this.idempiere_id = idempiere_id;
	}
	public Object getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = id;
	}
	public Object getNode_id() {
		return node_id;
	}
	public void setNode_id(Object node_id) {
		this.node_id = node_id;
	}
	public Object getName() {
		return name;
	}
	public void setName(Object name) {
		this.name = name;
	}
	public Object getApppassword() {
		return apppassword;
	}
	public void setApppassword(Object apppassword) {
		this.apppassword = apppassword;
	}
	public void save(String whereClause) {

		String sql ="Insert Into "+Table_Name+" (role,ad_user_id,idempiere_id,id,node_id,name,apppassword)"+
		"(Select role,ad_user_id,idempiere_id,id,node_id,name,apppassword from pos.bsca_people_v where 1=1 "+whereClause+")";
		DB.executeUpdateEx(sql, trxName);
	};
	public void update(String whereClause) {

		String sql ="Update "+Table_Name+" a set "+
		"role= b.role,"+
		"ad_user_id= b.ad_user_id,"+
		"idempiere_id= b.idempiere_id,"+
		"id= b.id,"+
		"node_id= b.node_id,"+
		"name= b.name,"+
		"apppassword= b.apppassword "+
		"from pos.bsca_people_v b where  a.id = cast(b.ID as text)  "+whereClause;
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
