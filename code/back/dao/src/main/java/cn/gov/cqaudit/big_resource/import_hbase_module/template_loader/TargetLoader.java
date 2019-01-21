package cn.gov.cqaudit.big_resource.import_hbase_module.template_loader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.TargetTemplate;
import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.TargetTemplateDetail;
import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.TargetTemplateTypeEnum;

@Component
public class TargetLoader {
	
	@Autowired
	TargetTemplate targetTemplate;

	public TargetTemplate load(String inputString) throws JSONException {
		//System.out.println(inputString);
		// 将读取的数据转换为JSONObject
				JSONObject templateObject = new JSONObject(inputString);

				
		/*
		 * JSONObject templateObject = null; if (jsonObject != null) { // 取出按钮权限的数据
		 * templateObject = jsonObject.getJSONObject("import_template"); }
		 */

				targetTemplate.setTableName(templateObject.getString("tableName"));
				targetTemplate.setRowKey(templateObject.getString("rowKey"));
				String overWrite=templateObject.getString("overWrite");
				targetTemplate.setOverWrite(overWrite.equals("true")?true:false);
				java.util.List<TargetTemplateDetail> rows = new java.util.ArrayList<TargetTemplateDetail>();
				JSONArray rows_array = templateObject.getJSONArray("rows");

				for (int i = 0; i < rows_array.length(); i++) {
					JSONObject row = rows_array.getJSONObject(i);
					String colName = row.getString("colName");
					TargetTemplateDetail i_row = null;
					switch (row.getString("type")) {
					case "DATE":
						i_row = new TargetTemplateDetail(colName, TargetTemplateTypeEnum.DATE);
						break;
					case "DATETIME":
						i_row = new TargetTemplateDetail(colName, TargetTemplateTypeEnum.DATETIME);
						break;
					case "NUMBER":
						i_row = new TargetTemplateDetail(colName, TargetTemplateTypeEnum.NUMBER);
						break;
					case "STRING":
						i_row = new TargetTemplateDetail(colName, TargetTemplateTypeEnum.STRING);
						break;
					default:
						break;
					}

					rows.add(i_row);

				}
				targetTemplate.setDetails(rows);
				return targetTemplate;
	}
}
