package cn.gov.cqaudit.big_resource.import_hbase_module.impl;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;

import org.apache.hadoop.hbase.util.Bytes;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.gov.cqaudit.big_resource.hbase_module.jdbc.HbaseTemplate;
import cn.gov.cqaudit.big_resource.import_hbase_module.abs.DataImportOperationAbs;

import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.TargetTemplateDetail;
import cn.gov.cqaudit.big_resource.import_hbase_module.template_loader.SourceLoader;
import cn.gov.cqaudit.big_resource.import_hbase_module.template_loader.TargetLoader;
import cn.gov.cqaudit.tools.StringTools;

@Component("cSVImporter")
@Scope("prototype")
public class CSVImporter extends DataImportOperationAbs<CSVParser, CSVRecord> {

	@Autowired
	TargetLoader targetLoader;
	public CSVImporter() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	SourceLoader sourceLoader;
	
	



	@Override
	public CSVParser getResultset() throws Exception {

		BufferedReader in = new BufferedReader(new FileReader(sourceTemplate.getCvsFileName()));
		System.out.println("打开文件");
		CSVParser parser = CSVParser.parse(in, CSVFormat.RFC4180.withHeader());
		System.out.println("识别CSV文件");
		return parser;
	}

	// 生成一行put
	public Put getPut(CSVRecord record) throws Exception{
		
		//System.out.println(importer.getRowKey());
		if (record.get(targetTemplate.getRowKey()).length()==0) {
			return null;
		}
		Put put = new Put(Bytes.toBytes(record.get(targetTemplate.getRowKey())));
		
		// 依次读出其他列
		java.util.List<TargetTemplateDetail> template_rows = targetTemplate.getDetails();
		for (TargetTemplateDetail importerRow : template_rows) {
			String colName = importerRow.getColumn();

			String colValue = record.get(colName);
			//如果为空，该列忽略
			if (colValue.length()!=0) {
				
			
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
				colValue=colValue.replace(" ", "");
				break;
				
			default:
				break;
				
			}

			put.addColumn(CF_INFO, Bytes.toBytes(colName), Bytes.toBytes(colValue));
			}
		}
		return put;
	}

	@Override
	public long do_import_hbase_batch(HbaseTemplate hbaseTemplate) throws Exception {
		//检察表状态，是否有表。如没有表，新建表
		
		
		CSVParser resultset=getResultset();
		for (CSVRecord record : resultset) {
			
			processOneRow(record,hbaseTemplate);
			
		}
		afterProcessOneRow(hbaseTemplate);
		return rowCountAll;
	}

	@Override
	public void readTartgetTemplate(String inputString) throws JSONException {
		// TODO Auto-generated method stub
		targetTemplate=targetLoader.load(inputString);
	}

	@Override
	public void readSourceTemplate(String sourceString) throws JSONException  {
		// TODO Auto-generated method stub
		sourceTemplate=sourceLoader.load(sourceString);
				
		
	}

}
