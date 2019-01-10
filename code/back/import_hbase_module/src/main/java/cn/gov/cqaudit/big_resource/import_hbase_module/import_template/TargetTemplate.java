package cn.gov.cqaudit.big_resource.import_hbase_module.import_template;

import java.util.List;

import org.springframework.stereotype.Component;


@Component
public class TargetTemplate {
	String tableName;
	public String getTableName() {
		return tableName;
	}
	public TargetTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TargetTemplate(String tableName, String rowKey, List<TargetTemplateDetail> details) {
		super();
		this.tableName = tableName;
		this.rowKey = rowKey;
		this.details = details;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getRowKey() {
		return rowKey;
	}
	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}
	public java.util.List<TargetTemplateDetail> getDetails() {
		return details;
	}
	public void setDetails(java.util.List<TargetTemplateDetail> details) {
		this.details = details;
	}
	String rowKey;
	java.util.List<TargetTemplateDetail> details;

}
