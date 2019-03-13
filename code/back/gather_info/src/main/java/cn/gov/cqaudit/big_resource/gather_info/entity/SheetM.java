package cn.gov.cqaudit.big_resource.gather_info.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class SheetM   implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1928923418150800366L;
	@Id
    @Column(length=32)
    private String sheetmuuid;
    @Column(length=128)
    private String warn;


	public String getSheetmuuid() {
		return sheetmuuid;
	}

	public void setSheetmuuid(String sheetmuuid) {
		this.sheetmuuid = sheetmuuid;
	}

	public String getWarn() {
		return warn;
	}

	public void setWarn(String warn) {
		this.warn = warn;
	}


	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}


	@Column(length=256)
	private String describe;
    
    public SheetM() {
		super();
		// TODO Auto-generated constructor stub
	}





	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Column(length=64)
	private String title;
	 public Set<SheetD> getSheetDs() {
		return sheetDs;
	}

	public void setSheetDs(Set<SheetD> sheetDs) {
		this.sheetDs = sheetDs;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="pk.sheetM")
	 private Set<SheetD> sheetDs = new HashSet<SheetD>();
	
    
}
