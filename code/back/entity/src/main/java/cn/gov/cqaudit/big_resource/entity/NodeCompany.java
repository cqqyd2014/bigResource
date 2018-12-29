package cn.gov.cqaudit.big_resource.entity;

import java.util.Date;

public class NodeCompany extends Node{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NodeCompany(String id, String name) {
		super(id, name);
		// TODO Auto-generated constructor stub
	}
	String type;
	String address;
	String law_man;
	float capital;
	java.util.Date start_date;
	java.util.Date end_date;
	boolean forever;
	public NodeCompany(String id, String name, String type, String address, String law_man, float capital,
			Date start_date, Date end_date, boolean forever, String business_scope, String register) {
		super(id, name);
		this.type = type;
		this.address = address;
		this.law_man = law_man;
		this.capital = capital;
		this.start_date = start_date;
		this.end_date = end_date;
		this.forever = forever;
		this.business_scope = business_scope;
		this.register = register;
	}
	String business_scope;
	String register;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLaw_man() {
		return law_man;
	}
	public void setLaw_man(String law_man) {
		this.law_man = law_man;
	}
	public float getCapital() {
		return capital;
	}
	public void setCapital(float capital) {
		this.capital = capital;
	}
	public java.util.Date getStart_date() {
		return start_date;
	}
	public void setStart_date(java.util.Date start_date) {
		this.start_date = start_date;
	}
	public java.util.Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(java.util.Date end_date) {
		this.end_date = end_date;
	}
	public boolean isForever() {
		return forever;
	}
	public void setForever(boolean forever) {
		this.forever = forever;
	}
	public String getBusiness_scope() {
		return business_scope;
	}
	public void setBusiness_scope(String business_scope) {
		this.business_scope = business_scope;
	}
	public String getRegister() {
		return register;
	}
	public void setRegister(String register) {
		this.register = register;
	}

}
