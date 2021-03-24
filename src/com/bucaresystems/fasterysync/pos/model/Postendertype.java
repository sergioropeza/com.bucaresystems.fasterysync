package com.bucaresystems.fasterysync.pos.model;

import org.compiere.util.DB;

public class Postendertype{

	public static final String Table_Name = "pos.bsca_postendertype";
	public static final String View_Name = "pos.BSCA_postendertype_v";
	protected Object idempiere_id;
	protected Object issetdifference;
	protected Object isactive;
	protected Object id;
	protected Object bsca_currency_id;
	protected Object isallowchange;
	protected Object name;
	protected Object classname;
	protected Object amtFrom;
	protected Object amtTo;
	private Object movementType;
	protected Object node_id;
	protected String trxName;

	public Postendertype(String trxName){
		this.trxName = trxName;
	}
	public Postendertype(String trxName, Object idempiere_ID){
		this(trxName);
		this.idempiere_id = idempiere_ID;
	}
	public Object getIdempiere_id() {
		return idempiere_id;
	}
	public void setIdempiere_id(Object idempiere_id) {
		this.idempiere_id = idempiere_id;
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
	public Object getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = id;
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
	public Object getName() {
		return name;
	}
	public void setName(Object name) {
		this.name = name;
	}
	public Object getClassname() {
		return classname;
	}
	public void setClassname(Object classname) {
		this.classname = classname;
	}
	protected Object getAmtTo() {
		return amtTo;
	}
	protected void setAmtTo(Object amtTo) {
		this.amtTo = amtTo;
	}
	
	protected Object getMovementType() {
		return movementType;
	}
	protected void setMovementType(Object movementType) {
		this.movementType = movementType;
	}
	
	public Object getNode_id() {
		return node_id;
	}
	public void setNode_id(Object node_id) {
		this.node_id = node_id;
	}
	public void save(String whereClause) {

		String sql ="Insert Into "+Table_Name+" (idempiere_id,issetdifference,isactive,id,bsca_currency_id,isallowchange,name,classname,amtFrom,amtTo,movementType,node_id)"+
		"(Select idempiere_id,issetdifference,isactive,id,bsca_currency_id,isallowchange,name,classname,amtFrom,amtTo,movementType,node_id from "+View_Name+" where 1=1 "+whereClause+")";
		DB.executeUpdateEx(sql, trxName);
	};
	public void update(String whereClause) {

		String sql ="Update "+Table_Name+" a set "+
		"idempiere_id= b.idempiere_id,"+
		"issetdifference= b.issetdifference,"+
		"isactive= b.isactive,"+
		"id= b.id,"+
		"bsca_currency_id= b.bsca_currency_id,"+
		"isallowchange= b.isallowchange,"+
		"name= b.name,"+
		"classname= b.classname,"+
		"amtFrom= b.amtFrom,"+
		"amtTo= b.amtTo,"+
		"movementType = b.movementType,"+
		"node_id= b.node_id "+
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
