package com.bucaresystems.fasterysync.model;

import org.compiere.util.DB;

public class Products{

	public static final String Table_Name = "products";
	protected Object id;
	protected Object reference;
	protected Object code;
	protected Object codetype;
	protected Object name;
	protected Object pricebuy;
	protected Object pricesell;
	protected Object category;
	protected Object taxcat;
	protected Object node_id;
	protected Object idempiere_id;
	protected Object m_pricelist_version_id;
	protected Object ad_org_id;
	private String trxName;

	public Products(String trxName){
		this.trxName = trxName;
	}
	public Products(String trxName, Object idempiere_ID){
		this(trxName);
		this.idempiere_id = idempiere_ID;
	}
	public Object getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = id;
	}
	public Object getReference() {
		return reference;
	}
	public void setReference(Object reference) {
		this.reference = reference;
	}
	public Object getCode() {
		return code;
	}
	public void setCode(Object code) {
		this.code = code;
	}
	public Object getCodetype() {
		return codetype;
	}
	public void setCodetype(Object codetype) {
		this.codetype = codetype;
	}
	public Object getName() {
		return name;
	}
	public void setName(Object name) {
		this.name = name;
	}
	public Object getPricebuy() {
		return pricebuy;
	}
	public void setPricebuy(Object pricebuy) {
		this.pricebuy = pricebuy;
	}
	public Object getPricesell() {
		return pricesell;
	}
	public void setPricesell(Object pricesell) {
		this.pricesell = pricesell;
	}
	public Object getCategory() {
		return category;
	}
	public void setCategory(Object category) {
		this.category = category;
	}
	public Object getTaxcat() {
		return taxcat;
	}
	public void setTaxcat(Object taxcat) {
		this.taxcat = taxcat;
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
	public Object getM_pricelist_version_id() {
		return m_pricelist_version_id;
	}
	public void setM_pricelist_version_id(Object m_pricelist_version_id) {
		this.m_pricelist_version_id = m_pricelist_version_id;
	}
	public Object getAd_org_id() {
		return ad_org_id;
	}
	public void setAd_org_id(Object ad_org_id) {
		this.ad_org_id = ad_org_id;
	}
	public void save(String whereClause) {

		String sql ="Insert Into pos.products(id,reference,code,codetype,name,pricebuy,pricesell,category,taxcat,node_id,idempiere_id,m_pricelist_version_id,ad_org_id)"+
		"(Select id,reference,code,codetype,name,pricebuy,pricesell,category,taxcat,node_id,idempiere_id,m_pricelist_version_id,ad_org_id from pos.bsca_products_v where 1=1 "+whereClause+")";
		DB.executeUpdateEx(sql, trxName);
	};
	public void update(String whereClause) {

		String sql ="Update pos.products a set "+
		"id= b.id,"+
		"reference= b.reference,"+
		"code= b.code,"+
		"codetype= b.codetype,"+
		"name= b.name,"+
		"pricebuy= b.pricebuy,"+
		"pricesell= b.pricesell,"+
		"category= b.category,"+
		"taxcat= b.taxcat,"+
		"node_id= b.node_id,"+
		"idempiere_id= b.idempiere_id,"+
		"m_pricelist_version_id= b.m_pricelist_version_id,"+
		"ad_org_id= b.ad_org_id "+
		"from pos.bsca_products_v b where  a.id = cast(b.ID as text)  "+whereClause;
		DB.executeUpdateEx(sql, trxName);
	};
	private boolean isRegister() {

		String sql = "select idempiere_ID from pos.products where idempiere_ID='"+idempiere_id+"'";
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
