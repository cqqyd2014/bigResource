package cn.gov.cqaudit.big_resource.import_hbase_batch.template;

import org.springframework.stereotype.Component;

@Component
public class BatchTemplate {

	String desc;
	int	batchCount;
	public int getBatchCount() {
		return batchCount;
	}
	public void setBatchCount(int batchCount) {
		this.batchCount = batchCount;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public java.util.ArrayList<BatchTemplateDetail> getDetails() {
		return details;
	}
	public void setDetails(java.util.ArrayList<BatchTemplateDetail> details) {
		this.details = details;
	}
	java.util.ArrayList<BatchTemplateDetail> details;
}
