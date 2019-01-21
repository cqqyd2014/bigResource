package cn.gov.cqaudit.big_resource.import_hbase_module.impl;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import cn.gov.cqaudit.big_resource.import_hbase_module.abs.DataImportOperationAbs;
import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.TargetTemplateDetail;
import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.TargetTemplateTypeEnum;
import cn.gov.cqaudit.big_resource.import_hbase_module.template_loader.SourceLoader;
import cn.gov.cqaudit.big_resource.import_hbase_module.template_loader.TargetLoader;
import cn.gov.cqaudit.tools.DateTools;

import cn.gov.cqaudit.big_resource.hbase_module.jdbc.*;

@Component("dbImporter")
@Scope("prototype")
public class DbImporter extends DataImportOperationAbs<ResultSet, ResultSet> {

	
	@Autowired
	TargetLoader targetLoader;
	@Autowired
	SourceLoader sourceLoader;
	
	@Override
	public Put getPut(ResultSet record) throws Exception {
		Put put = null;
		

			if (record.getString(targetTemplate.getRowKey()).length()==0) {
				return null;
			}
			put = new Put(Bytes.toBytes(record.getString(targetTemplate.getRowKey())));
			// 依次读出其他列
			java.util.List<TargetTemplateDetail> template_rows = targetTemplate.getDetails();
			for (TargetTemplateDetail importerRow : template_rows) {
				String colName = importerRow.getColumn();

				Object colObject = record.getObject(colName);

				// 如果为空，该列忽略
				if (colObject != null) {

					String colValue = null;
					// 根据不同类型进行转换
					switch (importerRow.getType()) {
					case DATE:
						java.util.Date dateValue = record.getDate(colName);
						colValue = DateTools.formatLocalDate(DateTools.dateToLocalDate(dateValue), "yyyy-MM-dd");
						dateValue = null;
						break;
					case DATETIME:
						java.util.Date datetimeValue = record.getTimestamp(colName);
						colValue = DateTools.formatLocalDateTime(DateTools.dateToLocalDateTime(datetimeValue), "yyyy-MM-dd HH:mm:ss.SSS");
						datetimeValue = null;
						break;
					case NUMBER:
						Float floatValue = record.getFloat(colName);
						colValue = floatValue.toString();
						floatValue = null;
						break;
					case STRING:
						colValue = record.getString(colName);
						//colValue = colValue.replace(" ", "");
						break;

					default:
						break;

					}

					put.addColumn(CF_INFO, Bytes.toBytes(colName), Bytes.toBytes(colValue));
					
					colName = null;
					colValue = null;
					colObject = null;
				}
			}
		
		return put;
	}

	@Override
	public ResultSet getResultset() throws Exception  {
		// TODO Auto-generated method stub
		ResultSet rs = null;
		
			Class.forName(sourceTemplate.getJdbcDriver()); // classLoader,加载对应驱动
			java.sql.Connection conn = (java.sql.Connection) DriverManager.getConnection(sourceTemplate.getJdbcUrl(),
					sourceTemplate.getJdbcUserName(), sourceTemplate.getJdbcPassWord());
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sourceTemplate.getSql());
			rs = pstmt.executeQuery();
			System.out.println(sourceTemplate.getSql());
			// 得到结果列
			ResultSetMetaData rsmd = rs.getMetaData();
			java.util.ArrayList<JdbcResultItem> items = new java.util.ArrayList<JdbcResultItem>();
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				JdbcResultItem item = new JdbcResultItem(rsmd.getColumnClassName(i + 1), rsmd.getColumnName(i + 1), i);
				items.add(item);
			}

			// 测试列的数量对不对
			System.out.println("测试列的数量对不对");
			java.util.List<TargetTemplateDetail> template_rows = targetTemplate.getDetails();
			if ((template_rows.size() + 1) != items.size()) {
				throw new Exception(
						"数据库返回列数量与导入定义数量不同,模板中列的数量为" + (template_rows.size() + 1) + ",数据库返回列数量为" + items.size());
			}
			// 以模板为标准检查每个名字的列是否存在，类型是否正确
			System.out.println("以模板为标准检查每个名字的列是否存在，类型是否正确");
			for (int i = 0; i < template_rows.size(); i++) {
				System.out.println("测试模板中的第" + (i + 1) + "个列");
				TargetTemplateDetail importer_row = template_rows.get(i);
				String colName = importer_row.getColumn();
				TargetTemplateTypeEnum colType = importer_row.getType();
				boolean checkCol = false;
				for (int j = 0; j < items.size(); j++) {
					if (items.get(j).getColumn().equals(colName)) {
						// 这个列存在，继续测试类型
						System.out.println(colName + "列存在");
						String type = items.get(j).getType();

						checkCol = JdbcDataCompareHbaseData.compare(type, colType);
						if (!checkCol) {
							throw new Exception(colName + "列类型不正确");
						}
					}
				}
				if (!checkCol) {
					throw new Exception(colName + "列不在数据源中");
				}
			}

		
		System.out.println("成功获取数据库数据");

		return rs;
	}

	

	@Override
	public void readTartgetTemplate(String inputString) throws JSONException {
		// TODO Auto-generated method stub
		targetTemplate=targetLoader.load(inputString);
	}

	@Override
	public void readSourceTemplate(String sourceString) throws JSONException {
		// TODO Auto-generated method stub
		sourceTemplate=sourceLoader.load(sourceString);
		
	}

	@Override
	public void loopResult(ResultSet resultset, HbaseTemplate hbaseTemplate) throws Exception {
		// TODO Auto-generated method stub
		while (resultset.next()) {
			processOneRow(resultset, hbaseTemplate);
		}
	}

}
