package cn.gov.cqaudit.big_resource.import_hbase_batch.template;


import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.TargetTemplate;

import cn.gov.cqaudit.big_resource.import_hbase_module.source_template.SourceTemplate;

public class BatchTemplateDetail {
	@Override
	public String toString() {
		return "BatchTemplateDetail [template_name=" + template_name + ", targetTemplate=" + targetTemplate
				+ ", sourceTemplate=" + sourceTemplate + "]";
	}
	String template_name;
	public String getTemplate_name() {
		return template_name;
	}
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}
	TargetTemplate targetTemplate;
	SourceTemplate sourceTemplate;

	public TargetTemplate getTargetTemplate() {
		return targetTemplate;
	}
	public BatchTemplateDetail(TargetTemplate targetTemplate, SourceTemplate sourceTemplate) {
		super();
		this.targetTemplate = targetTemplate;
		this.sourceTemplate = sourceTemplate;
	}
	public void setTargetTemplate(TargetTemplate targetTemplate) {
		this.targetTemplate = targetTemplate;
	}
	public SourceTemplate getSourceTemplate() {
		return sourceTemplate;
	}
	public void setSourceTemplate(SourceTemplate sourceTemplate) {
		this.sourceTemplate = sourceTemplate;
	}



}
