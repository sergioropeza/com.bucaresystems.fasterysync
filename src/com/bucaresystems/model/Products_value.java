package com.bucaresystems.model;

import org.compiere.util.DB;

public class Products_value{

	public static final String Table_Name = "products_value";
	protected Object id;
	protected Object product;
	protected Object value;
	protected Object isactive;
	protected Object ismastervalue;
	protected Object isplucode;
	protected Object isdefault;
	protected Object isdefaultvalue;
	protected Object node_id;
	protected Object idempiere_id;
	protected Object ad_org_id;
	protected Object m_product_id;
	protected Object qty;
	private String trxName;

	public Products_value(String trxName){
		this.trxName = trxName;
	}
	public Products_value(String trxName, Object idempiere_ID){
		this(trxName);
		this.idempiere_id = idempiere_ID;
	}
	public Object getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = id;
	}
	public Object getProduct() {
		return product;
	}
	public void setProduct(Object product) {
		this.product = product;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public Object getIsactive() {
		return isactive;
	}
	public void setIsactive(Object isactive) {
		this.isactive = isactive;
	}
	public Object getIsmastervalue() {
		return ismastervalue;
	}
	public void setIsmastervalue(Object ismastervalue) {
		this.ismastervalue = ismastervalue;
	}
	public Object getIsplucode() {
		return isplucode;
	}
	public void setIsplucode(Object isplucode) {
		this.isplucode = isplucode;
	}
	public Object getIsdefault() {
		return isdefault;
	}
	public void setIsdefault(Object isdefault) {
		this.isdefault = isdefault;
	}
	public Object getIsdefaultvalue() {
		return isdefaultvalue;
	}
	public void setIsdefaultvalue(Object isdefaultvalue) {
		this.isdefaultvalue = isdefaultvalue;
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
	public Object getAd_org_id() {
		return ad_org_id;
	}
	public void setAd_org_id(Object ad_org_id) {
		this.ad_org_id = ad_org_id;
	}
	public Object getM_product_id() {
		return m_product_id;
	}
	public void setM_product_id(Object m_product_id) {
		this.m_product_id = m_product_id;
	}
	public Object getQty() {
		return qty;
	}
	public void setQty(Object qty) {
		this.qty = qty;
	}
	public void save(String whereClause) {

		String sql ="Insert Into pos.products_value(id,product,value,isactive,ismastervalue,isplucode,isdefault,isdefaultvalue,node_id,idempiere_id,ad_org_id,m_product_id,qty)"+
		"(Select id,product,value,isactive,ismastervalue,isplucode,isdefault,isdefaultvalue,node_id,idempiere_id,ad_org_id,m_product_id,qty from pos.bsca_products_value_v where 1=1 "+whereClause+")";
		DB.executeUpdateEx(sql, trxName);
	};
	public void update(String whereClause) {

		String sql ="Update pos.products_value a set "+
		"id= b.id,"+
		"product= b.product,"+
		"value= b.value,"+
		"isactive= b.isactive,"+
		"ismastervalue= b.ismastervalue,"+
		"isplucode= b.isplucode,"+
		"isdefault= b.isdefault,"+
		"isdefaultvalue= b.isdefaultvalue,"+
		"node_id= b.node_id,"+
		"idempiere_id= b.idempiere_id,"+
		"ad_org_id= b.ad_org_id,"+
		"m_product_id= b.m_product_id,"+
		"qty= b.qty "+
		"from pos.bsca_products_value_v b where  a.id = cast(b.ID as text)  "+whereClause;
		DB.executeUpdateEx(sql, trxName);
	};
	private boolean isRegister() {

		String sql = "select idempiere_ID from pos.products_value where idempiere_ID='"+idempiere_id+"'";
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
