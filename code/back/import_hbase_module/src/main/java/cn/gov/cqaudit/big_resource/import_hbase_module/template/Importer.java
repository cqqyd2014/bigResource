package cn.gov.cqaudit.big_resource.import_hbase_module.template;

public class Importer {
	String tableName;
	String rowKey;
	public String getRowKey() {
		return rowKey;
	}
	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}
	java.util.List<ImporterRow> rows;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public java.util.List<ImporterRow> getRows() {
		return rows;
	}
	public void setRows(java.util.List<ImporterRow> rows) {
		this.rows = rows;
	}

}
