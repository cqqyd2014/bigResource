package cn.gov.cqaudit.big_resource.entity;

public class NodePerson extends Node {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	

	public NodePerson(String id, String name) {
		super(id, name);
		// TODO Auto-generated constructor stub
	}

	boolean sex;

	public NodePerson(String id, String name, boolean sex, String mobile_phone, float year_in_come) {
		super(id, name);
		this.sex = sex;
		this.mobile_phone = mobile_phone;
		this.year_in_come = year_in_come;
	}

	String mobile_phone;
	
	

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getMobile_phone() {
		return mobile_phone;
	}

	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}

	public float getYear_in_come() {
		return year_in_come;
	}

	public void setYear_in_come(float year_in_come) {
		this.year_in_come = year_in_come;
	}

	float year_in_come;

}
