package cn.gov.cqaudit.big_resource.import_hbase_module.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gov.cqaudit.big_resource.import_hbase_module.abs.DataImportOperationAbs;
import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.ImportDataTypeEnum;
import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.Importer;
import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.ImporterRow;
import cn.gov.cqaudit.tools.DateTools;
import cn.gov.cqaudit.tools.Province_city;
import cn.gov.cqaudit.tools.StringTools;

@Component("cSVImporter")
public class CSVImporter extends DataImportOperationAbs<CSVParser, CSVRecord> {

	@Autowired
	private Connection hConn;

	String dataFileName;

	public String getDataFileName() {
		return dataFileName;
	}

	public void setDataFileName(String dataFileName) {
		this.dataFileName = dataFileName;
	}

	@Override
	public CSVParser getResultset() throws Exception {

		BufferedReader in = new BufferedReader(new FileReader(dataFileName));

		CSVParser parser = CSVParser.parse(in, CSVFormat.RFC4180);

		return parser;
	}

	// 生成一行put
	public Put getPut(CSVRecord record) throws Exception{
		
		Put put = new Put(Bytes.toBytes(record.get(importer.getRowKey())));
		// 依次读出其他列
		java.util.List<ImporterRow> template_rows = importer.getRows();
		for (ImporterRow importerRow : template_rows) {
			String colName = importerRow.getColumn();

			String colValue = record.get(colName);
			//如果为空，该列忽略
			if (colValue.length()==0) {
				put=null;
				return put;
			}
			//根据不同类型进行转换
			switch (importerRow.getType()) {
			case DATE:
				//是否满足标准格式YYYY-MM-DD
				if (!StringTools.checkDate(colValue)) {
					put=null;
					return put;
				}
				
				break;
			case DATETIME:
				if (!StringTools.checkDateMiSecond(colValue)) {
					put=null;
					return put;
				}
				
				break;
			case NUMBER:
				if(!StringTools.checkFloat(colValue)) {
					
				}
				break;
			case STRING:
				break;
				
			default:
				break;
				
			}

			put.addColumn(CF_INFO, Bytes.toBytes(colName), Bytes.toBytes(colValue));
		}
		return put;
	}

	@Override
	public int do_import_hbase_batch(CSVParser resultset) throws Exception {

		for (CSVRecord record : resultset) {
			processOneRow(record);
		}
		afterProcessOneRow();
		return rowCountAll;
	}

}
