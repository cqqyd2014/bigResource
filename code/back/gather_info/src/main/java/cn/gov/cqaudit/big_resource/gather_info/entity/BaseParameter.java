package cn.gov.cqaudit.big_resource.gather_info.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class BaseParameter   implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1988272583436159276L;
	@EmbeddedId
	private BaseParameterPK pk;
	public BaseParameterPK getPk() {
		return pk;
	}
	public BaseParameter() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BaseParameter(BaseParameterPK pk, String pvalue) {
		super();
		this.pk = pk;
		this.pvalue = pvalue;
	}
	public void setPk(BaseParameterPK pk) {
		this.pk = pk;
	}
	public String getPvalue() {
		return pvalue;
	}
	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}
	private String pvalue;

}
