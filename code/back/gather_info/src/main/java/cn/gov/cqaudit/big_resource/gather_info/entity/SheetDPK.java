package cn.gov.cqaudit.big_resource.gather_info.entity;

import java.io.Serializable;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class SheetDPK  implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4872392915890907818L;
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn()
	private SheetM sheetM;
	public SheetDPK() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Column(length=32)
	private String sheetduuid;

	public SheetM getSheetM() {
		return sheetM;
	}
	public void setSheetM(SheetM sheetM) {
		this.sheetM = sheetM;
	}
	public String getSheetduuid() {
		return sheetduuid;
	}
	public void setSheetduuid(String sheetduuid) {
		this.sheetduuid = sheetduuid;
	}

}
