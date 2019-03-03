package cn.gov.cqaudit.big_resource.gather_info.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class BaseParameterPK implements Serializable{
	private String ptype;
	private String pcode;
	public BaseParameterPK() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BaseParameterPK(String ptype, String pcode) {
		super();
		this.ptype = ptype;
		this.pcode = pcode;
	}
	public String getPtype() {
		return ptype;
	}
	public void setPtype(String ptype) {
		this.ptype = ptype;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

}
