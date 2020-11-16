package com.bucaresystems.fasterysync.model;

import org.compiere.util.DB;

public class Roles{

	public static final String Table_Name = "pos.roles";
	public static final String View_Name = "pos.BSCA_roles_v";
	protected Object id;
	protected Object permissions;
	protected Object idempiere_id;
	protected Object name;
	protected Object node_id;
	protected Object bsca_permissions;
	protected String trxName;

	public Roles(String trxName){
		this.trxName = trxName;
	}
	public Roles(String trxName, Object idempiere_ID){
		this(trxName);
		this.idempiere_id = idempiere_ID;
	}
	public Object getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = id;
	}
	public Object getPermissions() {
		return permissions;
	}
	public void setPermissions(Object permissions) {
		this.permissions = permissions;
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
	public Object getNode_id() {
		return node_id;
	}
	public void setNode_id(Object node_id) {
		this.node_id = node_id;
	}
	public Object getBsca_permissions() {
		return bsca_permissions;
	}
	public void setBsca_permissions(Object bsca_permissions) {
		this.bsca_permissions = bsca_permissions;
	}
	public void save(String whereClause) {

		String sql ="Insert Into "+Table_Name+" (id,permissions,idempiere_id,name,node_id,bsca_permissions)"+
		"(Select id,permissions,idempiere_id,name,node_id,bsca_permissions from "+View_Name+" where 1=1 "+whereClause+")";
		DB.executeUpdateEx(sql, trxName);
	};
	public void update(String whereClause) {

		String sql ="Update "+Table_Name+" a set "+
		"id= b.id,"+
		"permissions= b.permissions,"+
		"idempiere_id= b.idempiere_id,"+
		"name= b.name,"+
		"node_id= b.node_id,"+
		"bsca_permissions= b.bsca_permissions "+
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
