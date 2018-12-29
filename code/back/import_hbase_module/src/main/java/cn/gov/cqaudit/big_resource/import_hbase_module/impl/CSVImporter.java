package cn.gov.cqaudit.big_resource.import_hbase_module.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
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
import cn.gov.cqaudit.big_resource.import_hbase_module.template.ImportDataTypeEnum;
import cn.gov.cqaudit.big_resource.import_hbase_module.template.Importer;
import cn.gov.cqaudit.big_resource.import_hbase_module.template.ImporterRow;
import cn.gov.cqaudit.tools.DateTools;
import cn.gov.cqaudit.tools.Province_city;




@Component("cSVImporter")
public class CSVImporter extends DataImportOperationAbs<CSVRecord>{

	@Autowired
	private Connection hConn;

	Importer importer;
	String templateFileName;
	String dataFileName;


	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public String getDataFileName() {
		return dataFileName;
	}

	public void setDataFileName(String dataFileName) {
		this.dataFileName = dataFileName;
	}

	public int getBatchCount() {
		return batchCount;
	}

	public void setBatchCount(int batchCount) {
		this.batchCount = batchCount;
	}

	int batchCount;
	public static byte[] CF_INFO = Bytes.toBytes("cf1");
	


	@Override
	public Iterable<CSVRecord> getList() throws IOException {
		// TODO Auto-generated method stub
		Reader in = new FileReader(dataFileName);
		// Reader in= new java.io.FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		return records;
	}

	@Override
	public int do_import_hbase_batch(Iterable<CSVRecord> list) throws IOException {
		// TODO Auto-generated method stub
		Table table = null;
		TableName tName = TableName.valueOf(importer.getTableName());
        table = hConn.getTable(tName);
        int rowCountAll=0;
		int rowCount=0;//记录计数
		int batchDoCount=0;//已经处理的批次计数
		java.util.ArrayList<Put> puts=new java.util.ArrayList<Put>();
		
		for (CSVRecord record : list) {
			rowCount++;
			rowCountAll++;
			
			java.util.ArrayList<String> oneLine=new java.util.ArrayList<String>();
			//oneLine第一个元素是rowKey
			oneLine.add(record.get(importer.getRowKey()));
			java.util.List<ImporterRow> template_rows=importer.getRows();
			for (ImporterRow importerRow:template_rows) {
				String colName=importerRow.getColumn();
				
				String colValue=record.get(colName);
				oneLine.add(colValue);
			}
			
			//将一行数据转换为Put
			
			Put put =mapPut(oneLine,rowCount++);
			puts.add(put);
			//达到阈值，插入数据
			if (rowCount==batchCount) {
				//System.out.println("开始批处理插入");
				table.put(puts); // 数据量达到某个阈值时提交，不达到不提交
				//处理完之后的操作
				rowCount=0;//重置为0
				puts.clear();
				batchDoCount++;
				//System.out.println("批处理插入结束");
				System.out.println("已经导入数据"+batchCount*batchDoCount);
			}
		}
		//处理批量之后剩下的零星
		table.put(puts);
		System.out.println("结束导入，一共导入数据"+(batchCount*batchDoCount+puts.size()));
		return rowCountAll;
	}



	@Override
	public void init() {
		// TODO Auto-generated method stub
		importer=readTemplate(templateFileName);
		
	}

	@Override
	public Put mapPut(ArrayList<String> oneLine, int rowNum) {
		// TODO Auto-generated method stub
		Put put = new Put(Bytes.toBytes(oneLine.get(0)));
		//System.out.println(oneLine.get(0));
		java.util.List<ImporterRow> template_rows=importer.getRows();
		int column_index=0;
		for (ImporterRow importerRow:template_rows) {
			String colName=importerRow.getColumn();
			put.addColumn(CF_INFO, Bytes.toBytes(colName), Bytes.toBytes(oneLine.get(column_index+1)));
			//System.out.println(oneLine.get(column_index+1));
		}

		return put;
	}



}
