package cn.gov.cqaudit.big_resource.gather_info.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class SheetD   implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2467962088950898840L;
	/*
	 * 
	 * id:'favor',
              label:'爱好',
              input_type:'checkbox',
              placeholder:'勾选爱好',
              group_tag:'fieldset',
              default_value:[],
              //legend:'勾选爱好',
              manual:true,
              items:[
                {
                  text:'足球',
                  value:'football'
                },
                {
                  text:'跑步',
                  value:'run'
                }
              ],//fieldset,
              col_length:4
	 */
	@EmbeddedId
	private SheetDPK pk;

	public SheetD() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SheetDPK getPk() {
		return pk;
	}

	public void setPk(SheetDPK pk) {
		this.pk = pk;
	}
    @Column(length=256)
	private String label;
    @Column(length=32)
	private String inputtype;
    @Column(length=64)
	private String placeholder;
    @Column(length=32)
	private String grouptag;
    @Column
	private boolean manual;
    @Column(length=256)
	private String defaultvalue;
    @Column
	private Integer collength;

	public String getDefaultvalue() {
		return defaultvalue;
	}

	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}



	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}



	public boolean isManual() {
		return manual;
	}

	public void setManual(boolean manual) {
		this.manual = manual;
	}





	public String getInputtype() {
		return inputtype;
	}

	public void setInputtype(String inputtype) {
		this.inputtype = inputtype;
	}

	public String getGrouptag() {
		return grouptag;
	}

	public void setGrouptag(String grouptag) {
		this.grouptag = grouptag;
	}

	public Integer getCollength() {
		return collength;
	}

	public void setCollength(Integer collength) {
		this.collength = collength;
	}



	public Set<SheetItem> getSheetItems() {
		return sheetItems;
	}

	public void setSheetItems(Set<SheetItem> sheetItems) {
		this.sheetItems = sheetItems;
	}
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="pk.sheetD")
	 private Set<SheetItem> sheetItems = new HashSet<SheetItem>();


	
}
