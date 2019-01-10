package cn.gov.cqaudit.big_resource.import_hbase_module.import_template;

public class TargetTemplateDetail {
	
	TargetTemplateTypeEnum type;

	public TargetTemplateDetail(String column,TargetTemplateTypeEnum type) {
		super();
		this.type = type;
		this.column = column;
	}

	String column;

	public TargetTemplateTypeEnum getType() {
		return type;
	}

	public void setType(TargetTemplateTypeEnum type) {
		this.type = type;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}
}
