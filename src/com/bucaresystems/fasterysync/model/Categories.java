package com.bucaresystems.fasterysync.model;

import org.compiere.util.DB;

public class Categories{

	public static final String Table_Name = "categories";
	protected Object id;
	protected Object name;
	protected Object idempiere_id;
	private String trxName;

	public Categories(String trxName){
		this.trxName = trxName;
	}
	public Categories(String trxName, Object idempiere_ID){
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
	public Object getIdempiere_id() {
		return idempiere_id;
	}
	public void setIdempiere_id(Object idempiere_id) {
		this.idempiere_id = idempiere_id;
	}
	public void save(String whereClause) {

		String sql ="Insert Into pos.categories(id,name,idempiere_id)"+
		"(Select id,name,idempiere_id from pos.bsca_categories_v where 1=1 "+whereClause+")";
		DB.executeUpdateEx(sql, trxName);
	};
	public void update(String whereClause) {

		String sql ="Update pos.categories a set "+
		"id= b.id,"+
		"name= b.name,"+
		"idempiere_id= b.idempiere_id "+
		"from pos.bsca_categories_v b where  a.id = cast(b.ID as text)  "+whereClause;
		DB.executeUpdateEx(sql, trxName);
	};
	private boolean isRegister() {

		String sql = "select idempiere_ID from pos.categories where idempiere_ID='"+idempiere_id+"'";
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
