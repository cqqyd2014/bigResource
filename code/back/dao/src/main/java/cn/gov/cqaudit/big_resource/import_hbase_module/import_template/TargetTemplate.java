package cn.gov.cqaudit.big_resource.import_hbase_module.import_template;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class TargetTemplate {
	boolean overWrite;
	String tableName;
	@Override
	public String toString() {
		return "TargetTemplate [overWrite=" + overWrite + ", tableName=" + tableName + ", rowKey=" + rowKey
				+ ", details=" + details + "]";
	}
	public String getTableName() {
		return tableName;
	}
	public TargetTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean isOverWrite() {
		return overWrite;
	}
	public TargetTemplate(boolean overWrite, String tableName, String rowKey, List<TargetTemplateDetail> details) {
		super();
		this.overWrite = overWrite;
		this.tableName = tableName;
		this.rowKey = rowKey;
		this.details = details;
	}
	public void setOverWrite(boolean overWrite) {
		this.overWrite = overWrite;
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
