package com.bucaresystems.fasterysync.pos.model;

import org.compiere.util.DB;

public class Products{

	public static final String Table_Name = "pos.products";
	public static final String View_Name = "pos.BSCA_products_v";
	protected Object isscale;
	protected Object pricesell;
	protected Object category;
	protected Object taxcat;
	protected Object idempiere_id;
	protected Object m_pricelist_version_id;
	protected Object ad_org_id;
	protected Object pricebuy;
	protected Object reference;
	protected Object code;
	protected Object codetype;
	protected Object name;
	protected Object node_id;
	protected Object id;
	protected String trxName;

	public Products(String trxName){
		this.trxName = trxName;
	}
	public Products(String trxName, Object idempiere_ID){
		this(trxName);
		this.idempiere_id = idempiere_ID;
	}
	public Object getIsscale() {
		return isscale;
	}
	public void setIsscale(Object isscale) {
		this.isscale = isscale;
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
	public Object getPricebuy() {
		return pricebuy;
	}
	public void setPricebuy(Object pricebuy) {
		this.pricebuy = pricebuy;
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
	public Object getNode_id() {
		return node_id;
	}
	public void setNode_id(Object node_id) {
		this.node_id = node_id;
	}
	public Object getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = id;
	}
	public void save(String whereClause) {

		String sql ="Insert Into "+Table_Name+" (isscale,pricesell,category,taxcat,idempiere_id,m_pricelist_version_id,ad_org_id,pricebuy,reference,code,codetype,name,node_id,id)"+
		"(Select isscale,pricesell,category,taxcat,idempiere_id,m_pricelist_version_id,ad_org_id,pricebuy,reference,code,codetype,name,node_id,id from "+View_Name+" where 1=1 "+whereClause+")";
		DB.executeUpdateEx(sql, trxName);
	};
	public void update(String whereClause) {

		String sql ="Update "+Table_Name+" a set "+
		"isscale= b.isscale,"+
		"pricesell= b.pricesell,"+
		"category= b.category,"+
		"taxcat= b.taxcat,"+
		"idempiere_id= b.idempiere_id,"+
		"m_pricelist_version_id= b.m_pricelist_version_id,"+
		"ad_org_id= b.ad_org_id,"+
		"pricebuy= b.pricebuy,"+
		"reference= b.reference,"+
		"code= b.code,"+
		"codetype= b.codetype,"+
		"name= b.name,"+
		"node_id= b.node_id,"+
		"id= b.id "+
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