package cn.gov.cqaudit.big_resource.import_hbase_module.import_template;

public class ImporterRow {
	
	String column;
	public ImporterRow(String column, ImportDataTypeEnum type) {
		super();
		this.column = column;
		this.type = type;
	}
	ImportDataTypeEnum type;
	
	public ImportDataTypeEnum getType() {
		return type;
	}
	public void setType(ImportDataTypeEnum type) {
		this.type = type;
	}

	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
}
