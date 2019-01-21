package cn.gov.cqaudit.big_resource.import_hbase_module.impl;

import java.sql.Types;

public class JdbcResultItem {
	String type;
	public JdbcResultItem(String type, String column, int column_index) {
		super();
		this.type = type;
		this.column = column;
		this.column_index = column_index;
	}
	String column;
	int column_index;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public int getColumn_index() {
		return column_index;
	}
	public void setColumn_index(int column_index) {
		this.column_index = column_index;
	}

}
