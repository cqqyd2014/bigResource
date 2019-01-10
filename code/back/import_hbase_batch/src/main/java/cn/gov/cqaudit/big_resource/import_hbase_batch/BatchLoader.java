package cn.gov.cqaudit.big_resource.import_hbase_batch;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.hbase.client.Connection;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gov.cqaudit.big_resource.import_hbase_batch.template.BatchTemplate;
import cn.gov.cqaudit.big_resource.import_hbase_batch.template.BatchTemplateDetail;
import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.TargetTemplate;
import cn.gov.cqaudit.big_resource.import_hbase_module.source_template.SourceTemplate;
import cn.gov.cqaudit.big_resource.import_hbase_module.template_loader.SourceLoader;
import cn.gov.cqaudit.big_resource.import_hbase_module.template_loader.TargetLoader;

@Component
public class BatchLoader {
	@Autowired
	BatchTemplate	batchTemplate;
	@Autowired
	TargetLoader	targetLoader;
	@Autowired
	SourceLoader	sourceLoader;
	
	public BatchTemplate load(String sourceFileName) throws IOException {
		String inputString = FileUtils.readFileToString(new File(sourceFileName), "UTF-8");
		// 将读取的数据转换为JSONObject
		JSONObject jsonObject = new JSONObject(inputString);
		batchTemplate.setBatchCount(jsonObject.getInt("batchCount"));
		JSONArray  array=jsonObject.getJSONArray("detail");
		java.util.ArrayList<BatchTemplateDetail> details=new java.util.ArrayList<BatchTemplateDetail>();
		for (int i=0;i<array.length();i++) {
			JSONObject o=array.getJSONObject(i);
			JSONObject templates=o.getJSONObject("templates");
			//System.out.println(templates.getJSONObject("target_template").toString());
			TargetTemplate targetTemplate=targetLoader.load(templates.getJSONObject("target_template").toString());
			System.out.println(targetTemplate);
			SourceTemplate sourceTemplate=sourceLoader.load(templates.getJSONObject("source_template").toString());
			BatchTemplateDetail detail=new BatchTemplateDetail(targetTemplate,sourceTemplate);
			detail.setTemplate_name(templates.getString("template_name"));
			details.add(detail);
			
			
		}
		batchTemplate.setDetails(details);
		return batchTemplate;
	}
}
