package cn.gov.cqaudit.big_resource.gather_info.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Embeddable
public class SheetItemPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1516719921202623481L;

	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn()
	private SheetDPK sheetDPK;

	public SheetDPK getSheetDPK() {
		return sheetDPK;
	}
	public void setSheetDPK(SheetDPK sheetDPK) {
		this.sheetDPK = sheetDPK;
	}

	public String getItemuuid() {
		return itemuuid;
	}
	public void setItemuuid(String itemuuid) {
		this.itemuuid = itemuuid;
	}

	@Column(length=32)
	private String itemuuid;
}
