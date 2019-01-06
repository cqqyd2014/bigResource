package cn.gov.cqaudit.big_resource.import_hbase_module.impl;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.csv.CSVRecord;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gov.cqaudit.big_resource.import_hbase_module.abs.DataImportOperationAbs;
import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.ImportDataTypeEnum;
import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.ImporterRow;
import cn.gov.cqaudit.tools.DateTools;
import cn.gov.cqaudit.tools.StringTools;

@Component("dbImporter")
public class DbImporter extends DataImportOperationAbs<ResultSet, ResultSet> {

	@Override
	public Put getPut(ResultSet record) throws Exception {
		Put put = null;
		try {

			if (record.getString(importer.getRowKey()).length()==0) {
				return null;
			}
			put = new Put(Bytes.toBytes(record.getString(importer.getRowKey())));
			// 依次读出其他列
			java.util.List<ImporterRow> template_rows = importer.getRows();
			for (ImporterRow importerRow : template_rows) {
				String colName = importerRow.getColumn();

				Object colObject = record.getObject(colName);

				// 如果为空，该列忽略
				if (colObject != null) {

					String colValue = null;
					// 根据不同类型进行转换
					switch (importerRow.getType()) {
					case DATE:
						java.util.Date dateValue = record.getDate(colName);
						colValue = DateTools.convertString(dateValue, "yyyy-MM-dd");
						dateValue = null;
						break;
					case DATETIME:
						java.util.Date datetimeValue = record.getDate(colName);
						colValue = DateTools.convertString(datetimeValue, "yyyy-MM-dd HH:mm:ss.SSS");
						datetimeValue = null;
						break;
					case NUMBER:
						Float floatValue = record.getFloat(colName);
						colValue = floatValue.toString();
						floatValue = null;
						break;
					case STRING:
						colValue = record.getString(colName);
						colValue = colValue.replace(" ", "");
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
		} catch (Exception e) {
			System.out.println("生成Put出现错误，RowKey:" + record.getString(importer.getRowKey()) + "出错项目：" + e.toString());
			throw e;
		}
		return put;
	}

	@Override
	public ResultSet getResultset() throws Exception {
		// TODO Auto-generated method stub
		ResultSet rs = null;
		try {
			Class.forName(source.getJdbcDriver()); // classLoader,加载对应驱动
			java.sql.Connection conn = (java.sql.Connection) DriverManager.getConnection(source.getJdbcUrl(),
					source.getJdbcUserName(), source.getJdbcPassWord());
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(source.getSql());
			rs = pstmt.executeQuery();
			System.out.println(source.getSql());
			// 得到结果列
			ResultSetMetaData rsmd = rs.getMetaData();
			java.util.ArrayList<JdbcResultItem> items = new java.util.ArrayList<JdbcResultItem>();
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				JdbcResultItem item = new JdbcResultItem(rsmd.getColumnClassName(i + 1), rsmd.getColumnName(i + 1), i);
				items.add(item);
			}

			// 测试列的数量对不对
			System.out.println("测试列的数量对不对");
			java.util.List<ImporterRow> template_rows = importer.getRows();
			if ((template_rows.size() + 1) != items.size()) {
				throw new Exception(
						"数据库返回列数量与导入定义数量不同,模板中列的数量为" + (template_rows.size() + 1) + ",数据库返回列数量为" + items.size());
			}
			// 以模板为标准检查每个名字的列是否存在，类型是否正确
			System.out.println("以模板为标准检查每个名字的列是否存在，类型是否正确");
			for (int i = 0; i < template_rows.size(); i++) {
				System.out.println("测试模板中的第" + (i + 1) + "个列");
				ImporterRow importer_row = template_rows.get(i);
				String colName = importer_row.getColumn();
				ImportDataTypeEnum colType = importer_row.getType();
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

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("成功获取数据库数据");

		return rs;
	}

	@Override
	public long do_import_hbase_batch(ResultSet resultset, Connection hconn) throws Exception {
		System.out.println("开始导入数据");

		while (resultset.next()) {
			processOneRow(resultset, hconn);

		}
		afterProcessOneRow(hconn);
		return rowCountAll;
	}

}
