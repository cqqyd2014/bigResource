package cn.gov.cqaudit.big_resource.import_hbase_module.impl;


import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.csv.CSVRecord;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gov.cqaudit.big_resource.import_hbase_module.abs.DataImportOperationAbs;
import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.ImporterRow;
import cn.gov.cqaudit.tools.DateTools;
import cn.gov.cqaudit.tools.StringTools;

@Component("dbImporter")
public class DbImporter extends DataImportOperationAbs<ResultSet,ResultSet>{
	@Autowired
	private Connection hConn;

	@Override
	public Put getPut(ResultSet record) throws Exception {

		Put put = new Put(Bytes.toBytes(record.getString(importer.getRowKey())));
		// 依次读出其他列
		java.util.List<ImporterRow> template_rows = importer.getRows();
		for (ImporterRow importerRow : template_rows) {
			String colName = importerRow.getColumn();

			Object colObject=record.getObject(colName);
			
			//如果为空，该列忽略
			if (colObject==null) {
				put=null;
				return put;
			}
			String colValue=null;
			//根据不同类型进行转换
			switch (importerRow.getType()) {
			case DATE:
				java.util.Date dateValue= record.getDate(colName);
				colValue=DateTools.convertString(dateValue, "YYYY-MM-DD");
				
				
				break;
			case DATETIME:
				java.util.Date datetimeValue= record.getDate(colName);
				colValue=DateTools.convertString(datetimeValue, "YYYY-MM-dd HH:mi:ss.SSS");
				
				break;
			case NUMBER:
				Float floatValue= record.getFloat(colName);
				colValue=floatValue.toString();
				break;
			case STRING:
				colValue= record.getString(colName);
				break;
				
			default:
				break;
				
			}

			put.addColumn(CF_INFO, Bytes.toBytes(colName), Bytes.toBytes(colValue));
		}
		return put;
	}

	@Override
	public ResultSet getResultset() throws Exception {
		// TODO Auto-generated method stub
		ResultSet rs=null;
		try {
	        Class.forName(source.getJdbcDriver()); //classLoader,加载对应驱动
	        java.sql.Connection conn = (java.sql.Connection) DriverManager.getConnection(source.getJdbcUrl(), source.getJdbcUserName(), source.getJdbcPassWord());
	        PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(source.getSql());
	        rs = pstmt.executeQuery();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		
		
		return rs;
	}

	@Override
	public int do_import_hbase_batch(ResultSet resultset) throws Exception {
		while (resultset.next()) {
			processOneRow(resultset);
		}
		afterProcessOneRow();
		return rowCountAll;
	}



}
