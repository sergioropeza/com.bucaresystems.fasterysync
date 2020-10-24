package com.bucaresystems.fasterysync.model;

import org.compiere.util.DB;

public class Postendertype{

	public static final String Table_Name = "pos.bsca_postendertype";
	protected Object id;
	protected Object name;
	protected Object bsca_currency_id;
	protected Object isallowchange;
	protected Object issetdifference;
	protected Object isactive;
	protected Object classname;
	protected Object node_id;
	protected Object idempiere_id;
	protected String trxName;

	public Postendertype(String trxName){
		this.trxName = trxName;
	}
	public Postendertype(String trxName, Object idempiere_ID){
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
	public Object getBsca_currency_id() {
		return bsca_currency_id;
	}
	public void setBsca_currency_id(Object bsca_currency_id) {
		this.bsca_currency_id = bsca_currency_id;
	}
	public Object getIsallowchange() {
		return isallowchange;
	}
	public void setIsallowchange(Object isallowchange) {
		this.isallowchange = isallowchange;
	}
	public Object getIssetdifference() {
		return issetdifference;
	}
	public void setIssetdifference(Object issetdifference) {
		this.issetdifference = issetdifference;
	}
	public Object getIsactive() {
		return isactive;
	}
	public void setIsactive(Object isactive) {
		this.isactive = isactive;
	}
	public Object getClassname() {
		return classname;
	}
	public void setClassname(Object classname) {
		this.classname = classname;
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

		String sql ="Insert Into "+Table_Name+" (id,name,bsca_currency_id,isallowchange,issetdifference,isactive,classname,node_id,idempiere_id)"+
		"(Select id,name,bsca_currency_id,isallowchange,issetdifference,isactive,classname,node_id,idempiere_id from pos.bsca_postendertype_v where 1=1 "+whereClause+")";
		DB.executeUpdateEx(sql, trxName);
	};
	public void update(String whereClause) {

		String sql ="Update "+Table_Name+" a set "+
		"id= b.id,"+
		"name= b.name,"+
		"bsca_currency_id= b.bsca_currency_id,"+
		"isallowchange= b.isallowchange,"+
		"issetdifference= b.issetdifference,"+
		"isactive= b.isactive,"+
		"classname= b.classname,"+
		"node_id= b.node_id,"+
		"idempiere_id= b.idempiere_id "+
		"from pos.bsca_postendertype_v b where  a.id = cast(b.ID as text)  "+whereClause;
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
