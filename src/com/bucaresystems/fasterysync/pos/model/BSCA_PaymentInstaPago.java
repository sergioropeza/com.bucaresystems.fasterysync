/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bucaresystems.fasterysync.pos.model;

import java.sql.Timestamp;

/**
 *
 * @author soropeza
 */
public class BSCA_PaymentInstaPago {
    
    private String  deferre;
    private String  code;
    private String  cardholderidL;
    private String  voucher;
    private String  lote;
    private String  description;
    private String  cardline;
    private String  tsi;
    private String  commerce;
    private Timestamp  datetime;
    private String  ordernumber;
    private String  id;
    private String  cardtype;
    private String  idmerchant;
    private String  responsecode;
    private String  amount;
    private String  approval;
    private String  terminal;
    private String  message;
    private String  authid;
    private String  arqc;
    private String  tc;
    private String  sequence;
    private String  tvr;
    private String  na;
    private String  success;
    private String  name;
    private String  cardnumber;
    private String  aid;
    private String  bank;
    private String  reference;
    private String  orgValue;
    private String  json;
    public String  host;
    public int BSCA_Route_ID;

    /**
     * @return the deferre
     */
    public String getDeferre() {
        return deferre==null?"":deferre;
    }

    /**
     * @param deferre the deferre to set
     */
    public void setDeferre(String deferre) {
        this.deferre = deferre;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code==null?"":code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the cardholderidL
     */
    public String getCardholderidL() {
        return cardholderidL==null?"":cardholderidL;
    }

    /**
     * @param cardholderidL the cardholderidL to set
     */
    public void setCardholderidL(String cardholderidL) {
        this.cardholderidL = cardholderidL;
    }

    /**
     * @return the voucher
     */
    public String getVoucher() {
        return voucher==null?"":voucher;
    }

    /**
     * @param voucher the voucher to set
     */
    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    /**
     * @return the lote
     */
    public String getLote() {
        return lote==null?"":lote;

    }

    /**
     * @param lote the lote to set
     */
    public void setLote(String lote) {
        this.lote = lote;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description==null?"":description;

    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the cardline
     */
    public String getCardline() {
        return cardline==null?"":cardline;
    }

    /**
     * @param cardline the cardline to set
     */
    public void setCardline(String cardline) {
        this.cardline = cardline;
    }

    /**
     * @return the tsi
     */
    public String getTsi() {
        return tsi==null?"":tsi;
    }

    /**
     * @param tsi the tsi to set
     */
    public void setTsi(String tsi) {
        this.tsi = tsi;
    }

    /**
     * @return the commerce
     */
    public String getCommerce() {
        return commerce==null?"":commerce;

    }

    /**
     * @param commerce the commerce to set
     */
    public void setCommerce(String commerce) {
        this.commerce = commerce;
    }

    /**
     * @return the datetime
     */
    public Timestamp getDatetime() {
        return datetime;

    }

    /**
     * @param datetime the datetime to set
     */
    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    /**
     * @return the ordernumber
     */
    public String getOrdernumber() {
        return ordernumber==null?"":ordernumber;

    }

    /**
     * @param ordernumber the ordernumber to set
     */
    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id==null?"":id;

    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the cardtype
     */
    public String getCardtype() {
        return cardtype==null?"":cardtype;

    }

    /**
     * @param cardtype the cardtype to set
     */
    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    /**
     * @return the idmerchant
     */
    public String getIdmerchant() {
        return idmerchant==null?"":idmerchant;

    }

    /**
     * @param idmerchant the idmerchant to set
     */
    public void setIdmerchant(String idmerchant) {
        this.idmerchant = idmerchant;
    }

    /**
     * @return the responsecode
     */
    public String getResponsecode() {
        return responsecode==null?"":responsecode;

    }

    /**
     * @param responsecode the responsecode to set
     */
    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode;
    }

    /**
     * @return the amount
     */
    public String getAmount() {
        if(amount==null)
            return "";
        StringBuffer amtOrig=new StringBuffer(amount);
        if(!amount.contains(".")){
            amtOrig=amtOrig.insert(amtOrig.length()-2, ".");
        }
        return amtOrig.toString();

    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return the approval
     */
    public String getApproval() {
        return approval==null?"":approval;

    }

    /**
     * @param approval the approval to set
     */
    public void setApproval(String approval) {
        this.approval = approval;
    }

    /**
     * @return the terminal
     */
    public String getTerminal() {
        return terminal==null?"":terminal;

    }

    /**
     * @param terminal the terminal to set
     */
    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message==null?"":message;

    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the authid
     */
    public String getAuthid() {
        return authid==null?"":authid;

    }

    /**
     * @param authid the authid to set
     */
    public void setAuthid(String authid) {
        this.authid = authid;
    }

    /**
     * @return the arqc
     */
    public String getArqc() {
        return arqc==null?"":arqc;

    }

    /**
     * @param arqc the arqc to set
     */
    public void setArqc(String arqc) {
        this.arqc = arqc;
    }

    /**
     * @return the tc
     */
    public String getTc() {
        return tc==null?"":tc;

    }

    /**
     * @param tc the tc to set
     */
    public void setTc(String tc) {
        this.tc = tc;
    }

    /**
     * @return the sequence
     */
    public String getSequence() {
        return sequence==null?"":sequence;

    }

    /**
     * @param sequence the sequence to set
     */
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    /**
     * @return the tvr
     */
    public String getTvr() {
        return tvr==null?"":tvr;

    }

    /**
     * @param tvr the tvr to set
     */
    public void setTvr(String tvr) {
        this.tvr = tvr;
    }

    /**
     * @return the na
     */
    public String getNa() {
        return na==null?"":na;

    }

    /**
     * @param na the na to set
     */
    public void setNa(String na) {
        this.na = na;
    }

    /**
     * @return the success
     */
    public String getSuccess() {
        return success;
        
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(String success) {
        this.success = success;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name==null?"":name;

    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the cardnumber
     */
    public String getCardnumber() {
        return cardnumber==null?"":cardnumber;

    }

    /**
     * @param cardnumber the cardnumber to set
     */
    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    /**
     * @return the aid
     */
    public String getAid() {
        return aid==null?"":aid;

    }

    /**
     * @param aid the aid to set
     */
    public void setAid(String aid) {
        this.aid = aid;
    }

    /**
     * @return the bank
     */
    public String getBank() {
        return bank==null?"":bank;

    }

    /**
     * @param bank the bank to set
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * @return the reference
     */
    public String getReference() {
        return reference==null?"":reference;

    }

    /**
     * @param reference the reference to set
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * @return the orgValue
     */
    public String getOrgValue() {
        return orgValue;
    }

    /**
     * @param orgValue the orgValue to set
     */
    public void setOrgValue(String orgValue) {
        this.orgValue = orgValue;
    }

    /**
     * @return the json
     */
    public String getJson() {
        return json;
    }

    /**
     * @param json the json to set
     */
    public void setJson(String json) {
        this.json = json;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

	public int getBSCA_Route_ID() {
		return BSCA_Route_ID;
	}

	public void setBSCA_Route_ID(int bSCA_Route_ID) {
		BSCA_Route_ID = bSCA_Route_ID;
	}
    
    
    
}
