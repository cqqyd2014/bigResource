package cn.gov.cqaudit.big_resource.import_hbase_module.abs;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.json.JSONArray;
import org.json.JSONObject;

import cn.gov.cqaudit.big_resource.dao.common.HbaseDaoCommon;
import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.ImportDataTypeEnum;
import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.Importer;
import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.ImporterRow;
import cn.gov.cqaudit.big_resource.import_hbase_module.source_template.Source;
import cn.gov.cqaudit.big_resource.import_hbase_module.source_template.SourceTypeEnum;
import cn.gov.cqaudit.tools.ArrayTools;



public abstract class DataImportOperationAbs<T,S> extends HbaseDaoCommon{
	public long bufferSize;
	public Table table = null;
	public TableName tName;
	public Importer importer;
	public Source source;
	String templateFileName;
	String sourceFileName;
	public 	int batchCount;
	public static byte[] CF_INFO = Bytes.toBytes("cf1");
	public long rowCountAll=0;
		public int rowCount=0;//记录计数
		public int batchDoCount=0;//已经处理的批次计数
		//java.util.ArrayList<Put> puts;
		Put[] puts;
	
		
	
	//读出的原始数据变为Put对象
	public abstract Put getPut(S record) throws Exception;
	
	
	//Put对象放入puts，根据批处理的参数，进行批处理
	public void processOneRow(S record,Connection hconn) throws Exception {
		
		
		rowCountAll++;
		//System.out.println("处理明细数据中"+rowCountAll);
		//将一行数据转换为Put
		Put put =getPut(record);
		//为空的时候，忽略该列
		if (put==null) {
			return;
		}
		
		//System.out.println("rowCount:"+rowCount);
		//puts.add(put);
		puts[rowCount]=put;
		rowCount++;
		//System.out.println(Bytes.toString(put.getRow()));
		put=null;
		//达到阈值，插入数据
		if (rowCount==batchCount) {
			//System.out.println("开始批处理插入");
			//tablePuts(puts); // 数据量达到某个阈值时提交，不达到不提交
			putManualBatch(hconn,table.getName().getNameAsString(),ArrayTools.convertList(puts) ,bufferSize);
			//处理完之后的操作
			rowCount=0;//重置为0
			//清理数组
			cleanArray();
			//puts.clear();
			//puts.trimToSize();
			//System.out.println(batchDoCount);
			//System.out.println(batchCount);
			batchDoCount++;
			//System.out.println("批处理插入结束");
			System.out.println("已经导入数据"+batchCount*batchDoCount);
			System.gc();
		}
	}
	
	//处理批量之后剩下的零星
	public void afterProcessOneRow(Connection hconn) throws IOException {
		
		//tablePuts(puts);
		putManualBatch(hconn,table.getName().getNameAsString(), ArrayTools.convertList(puts) ,bufferSize);
		//puts.clear();
		cleanArray();
		System.out.println("结束导入，一共导入数据"+(batchCount*batchDoCount+puts.length));
	}
	
	//初始化环境
	public void init(Connection hConn,String sourceFileName,String templateFileName,int batchCount,int bufferSize) throws IOException {
		this.bufferSize=bufferSize;
		System.out.println("读取导入表模板");
		importer=readImportTemplate(templateFileName);
		System.out.println("读取数据源");
		source=readSourceTemplate(sourceFileName);
		this.batchCount=batchCount;
		
		tName= TableName.valueOf(importer.getTableName());
		System.out.println("连接操作表"+tName);
        table = hConn.getTable(tName);
        //puts=new java.util.ArrayList<Put>();
        puts=new Put[batchCount];
        System.out.println("初始化成功");
	}

	
	//获取数据源
	public abstract  T getResultset()  throws Exception ;


	/**
	 * 批量导入
	 * @param list
	 * @param batchCount 一次批量处理的数量
	 * @return
	 */
	public abstract long do_import_hbase_batch(T resultset,Connection hconn)throws Exception;
	
	//读取数据源参数
	public Source readSourceTemplate(String sourceFieName) {
		Source source=null;
		try {
			source=new Source();
			String sourceString = FileUtils.readFileToString(new File(sourceFieName), "UTF-8");
	        //将读取的数据转换为JSONObject
	        JSONObject jsonObject = new JSONObject(sourceString);
	        switch(jsonObject.getString("type")){
	        case "CSV":
	        	source.setType(SourceTypeEnum.CSV);
	            break;
	        case "Database":
	        	source.setType(SourceTypeEnum.Database);
	            break;
	         
	        default:
	        	source.setType(SourceTypeEnum.Database);
	            break;
	        }
	        source.setCvsFileName(jsonObject.getString("cvsFileName"));
	        source.setJdbcDriver(jsonObject.getString("jdbcDriver"));
	        source.setJdbcPassWord(jsonObject.getString("jdbcPassWord"));
	        source.setJdbcUrl(jsonObject.getString("jdbcUrl"));
	        source.setJdbcUserName(jsonObject.getString("jdbcUserName"));
	        source.setSql(jsonObject.getString("sql"));
	        
	        
			
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		
		
		return source;
	}
	
	

	//读取表的模板
	public Importer readImportTemplate(String fileName) {
		Importer importer;
		try {
			importer=new Importer();
			JSONObject templateObject = null;

	        //读取文件
	        String inputString = FileUtils.readFileToString(new File(fileName), "UTF-8");
	        //将读取的数据转换为JSONObject
	        JSONObject jsonObject = new JSONObject(inputString);

	        if (jsonObject != null) {
	            //取出按钮权限的数据
	        	templateObject = jsonObject.getJSONObject("import_template");
	        }

	        importer.setTableName(templateObject.getString("tableName"));
	        importer.setRowKey(templateObject.getString("rowKey"));
	        java.util.List<ImporterRow> rows=new java.util.ArrayList<ImporterRow>();
	        JSONArray rows_array=templateObject.getJSONArray("rows");

	        for (int i=0;i<rows_array.length();i++) {
	        	JSONObject row=rows_array.getJSONObject(i);
	        	String colName=row.getString("colName");
	        	ImporterRow i_row=null;
	        	switch (row.getString("type")) {
	        	case "DATE":
	        		i_row=new ImporterRow(colName,ImportDataTypeEnum.DATE);
	        		break;
	        	case "DATETIME":
	        		i_row=new ImporterRow(colName,ImportDataTypeEnum.DATETIME);
	        		break;
	        	case "NUMBER":
	        		i_row=new ImporterRow(colName,ImportDataTypeEnum.NUMBER);
	        		break;
	        	case "STRING":
	        		i_row=new ImporterRow(colName,ImportDataTypeEnum.STRING);
	        		break;
	        	default:
	        		break;
	        	}
	        	
	        	
	        	
	        		
	        	    
	        	rows.add(i_row);

	        }
	        importer.setRows(rows);


	        System.out.println(importer.getTableName());


	        return importer;
		}
		catch(Exception e) {
			System.out.print(e.toString());
			return null;
		}




	}
	
	private void cleanArray() {
		for (int i=0;i<puts.length;i++) {
			puts[i]=null;
		}
	}

}
