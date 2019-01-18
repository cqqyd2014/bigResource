package cn.gov.cqaudit.big_resource.import_hbase_module.template_loader;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gov.cqaudit.big_resource.import_hbase_module.source_template.SourceTemplate;
import cn.gov.cqaudit.big_resource.import_hbase_module.source_template.SourceTemplateTypeEnum;


@Component
public class SourceLoader {
	
	@Autowired
	SourceTemplate sourceTemplate;

	public SourceTemplate load(String inputString) throws JSONException {
		
		// 将读取的数据转换为JSONObject
				JSONObject jsonObject = new JSONObject(inputString);
				switch (jsonObject.getString("type")) {
				case "CSV":
					sourceTemplate.setType(SourceTemplateTypeEnum.CSV);
					break;
				case "Database":
					sourceTemplate.setType(SourceTemplateTypeEnum.Database);
					break;

				default:
					sourceTemplate.setType(SourceTemplateTypeEnum.Database);
					break;
				}
				sourceTemplate.setCvsFileName(jsonObject.getString("cvsFileName"));
				sourceTemplate.setJdbcDriver(jsonObject.getString("jdbcDriver"));
				sourceTemplate.setJdbcPassWord(jsonObject.getString("jdbcPassWord"));
				sourceTemplate.setJdbcUrl(jsonObject.getString("jdbcUrl"));
				sourceTemplate.setJdbcUserName(jsonObject.getString("jdbcUserName"));
				sourceTemplate.setSql(jsonObject.getString("sql"));
				return sourceTemplate;
	}
}
