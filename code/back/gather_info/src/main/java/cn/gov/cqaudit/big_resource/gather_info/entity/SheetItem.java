package cn.gov.cqaudit.big_resource.gather_info.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class SheetItem  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1921538781527330566L;
	@EmbeddedId
	private SheetItemPK pk;
	@Column(length=128)
	private String text;
	public SheetItemPK getPk() {
		return pk;
	}
	public void setPk(SheetItemPK pk) {
		this.pk = pk;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Column(length=128)
	private String value;

}
