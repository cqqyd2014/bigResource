package cn.gov.cqaudit.big_resource.import_hbase_module.template;

public class ImporterRow {
	
	String column;
	
	public ImporterRow(String column) {
		super();
		
		this.column = column;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
}
