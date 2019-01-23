package cn.gov.cqaudit.big_resource.import_hbase_module.abs;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.hbase.TableName;

import org.apache.hadoop.hbase.client.Put;

import org.apache.hadoop.hbase.util.Bytes;

import org.json.JSONException;


import cn.gov.cqaudit.big_resource.dao.common.HbaseDaoCommon;
import cn.gov.cqaudit.big_resource.hbase_module.jdbc.HbaseTemplate;
import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.TargetTemplate;
import cn.gov.cqaudit.big_resource.hive_module.jdbc.*;

import cn.gov.cqaudit.big_resource.import_hbase_module.source_template.SourceTemplate;
import cn.gov.cqaudit.tools.DateTools;


public abstract class DataImportOperationAbs<T, S> extends HbaseDaoCommon {
	private final static Logger logger = LoggerFactory.getLogger(DataImportOperationAbs.class); 
	public int bufferSize;
	public String templateName;

	public TableName tName;
	public TargetTemplate targetTemplate;
	public SourceTemplate sourceTemplate;
	String templateFileName;
	String sourceFileName;
	public int batchCount;
	public static byte[] CF_INFO = Bytes.toBytes("cf1");
	public long rowCountAll = 0;
	public int rowCount = 0;// 记录计数
	public int batchDoCount = 0;// 已经处理的批次计数
	// java.util.ArrayList<Put> puts;
	Put[] puts;

	// 读出的原始数据变为Put对象
	public abstract Put getPut(S record) throws Exception;

	// Put对象放入puts，根据批处理的参数，进行批处理
	public void processOneRow(S record,HbaseTemplate hbaseTemplate) throws Exception {

		rowCountAll++;
		//System.out.println(templateName+"处理明细数据中"+rowCountAll);
		// 将一行数据转换为Put
		Put put = getPut(record);
		// 为空的时候，忽略该列
		if (put == null) {
			return;
		}

		// System.out.println("rowCount:"+rowCount);
		// puts.add(put);
		puts[rowCount] = put;
		rowCount++;
		// System.out.println(Bytes.toString(put.getRow()));
		put = null;
		// 达到阈值，插入数据
		if (rowCount == batchCount) {
			// System.out.println("开始批处理插入");
			// tablePuts(puts); // 数据量达到某个阈值时提交，不达到不提交
			//putManualBatch(hbaseTemplate, Arrays.asList(puts));
			hbaseTemplate.put_batch(tName.getNameAsString(), Arrays.asList(puts));
			
			
			// 处理完之后的操作
			rowCount = 0;// 重置为0
			// 清理数组
			cleanArray();
			// puts.clear();
			// puts.trimToSize();
			// System.out.println(batchDoCount);
			// System.out.println(batchCount);
			batchDoCount++;
			// System.out.println("批处理插入结束");
			logger.info(templateName+"已经导入数据" + batchCount * batchDoCount);
			//System.gc();
		}
	}

	// 处理批量之后剩下的零星
	public void afterProcessOneRow(HbaseTemplate hbaseTemplate) throws IOException {

		// tablePuts(puts);
		//putManualBatch(hconn, getFragmentaryPuts());
		hbaseTemplate.put_batch(tName.getNameAsString(), getFragmentaryPuts());
		// puts.clear();
		cleanArray();
		logger.info(templateName+"结束导入，一共导入数据" + (batchCount * batchDoCount + puts.length));
	}

	// 初始化环境
	public void init( String sourceFileName, String templateFileName, int batchCount)
			throws IOException,JSONException {

		
		readTargetTemplateFromFile( templateFileName);
		//afterReadTargetTemplate(hConn);
		
		readSourceTemplate(sourceFileName);
		this.batchCount = batchCount;

		tName = TableName.valueOf(targetTemplate.getTableName());
		

		// puts=new java.util.ArrayList<Put>();
		puts = new Put[batchCount];
		logger.info("初始化成功");
	}

	// 初始化环境
	public void init(SourceTemplate sourceTemplate, TargetTemplate targetTemplate, int batchCount,String templateName) throws IOException {
		this.targetTemplate = targetTemplate;
		this.sourceTemplate = sourceTemplate;
		this.batchCount = batchCount;
		puts = new Put[batchCount];
		this.templateName=templateName;
		//afterReadTargetTemplate(hConn);
		logger.info("初始化成功");

		
	}
	
	

	// 获取数据源
	public abstract T getResultset() throws Exception;

	/**
	 * 批量导入
	 * 
	 * @param list
	 * @param batchCount 一次批量处理的数量
	 * @return
	 */
	//public abstract long do_import_hbase_batch(HbaseTemplate hbaseTemplate) throws Exception;

	public long do_import_hbase_batch(HbaseTemplate hbaseTemplate,HiveTemplate hiveTemplate) throws Exception {
		//检查表状态，如果为覆盖模式，在hive和hbase中都删除该表，再创建从hive到hbase的表
		//System.out.println("表"+targetTemplate.getTableName()+"状态是"+targetTemplate.isOverWrite());
		logger.info("表"+targetTemplate.getTableName()+"状态是"+targetTemplate.isOverWrite());
		if (targetTemplate.isOverWrite()) {
			System.out.println("表"+targetTemplate.getTableName()+"存在状态是"+hiveTemplate.tableExists(targetTemplate.getTableName()));
			if (hiveTemplate.tableExists(targetTemplate.getTableName())) {
				
				logger.info("hive中存在表"+targetTemplate.getTableName()+"需要删除");
				hiveTemplate.deleteTable(targetTemplate.getTableName());
			}
			if (hbaseTemplate.tableExists(targetTemplate.getTableName())) {
				logger.info("hbase中存在表"+targetTemplate.getTableName()+"需要删除");
				hbaseTemplate.deleteTable(targetTemplate.getTableName());
			}
			
			//hbase创建表
			hbaseTemplate.createTableWithRegions(targetTemplate.getTableName(), "cf1", "0", "f", 16);
			hiveTemplate.createHiveOuterTableIntegrationHBase(targetTemplate);
			
		}
		else {
			//非覆盖模式为追加记录，需要hbase表存在,如没有表，新建表,hive重建映射
			if (!hbaseTemplate.tableExists(targetTemplate.getTableName())) {
				hbaseTemplate.createTableWithRegions(targetTemplate.getTableName(), "cf1", "0", "f", 16);
			}
			if (hiveTemplate.tableExists(targetTemplate.getTableName())) {
				hiveTemplate.deleteTable(targetTemplate.getTableName());
			}
			hiveTemplate.createHiveOuterTableIntegrationHBase(targetTemplate);
		}
		
		
		
		
		
		beforeGetResult();
		T resultset=getResultset();
		afterGetResult();
		beforFetchResult();
		loopResult(resultset,hbaseTemplate);
		afterFetchResult();
		afterProcessOneRow(hbaseTemplate);
		return rowCountAll;
	}
	/*
	 * 循环读取数据
	 * 
	 */
	public abstract void loopResult(T resultset,HbaseTemplate hbaseTemplate)throws Exception;
	
	
	// 读取数据源参数
	public void readSourceTemplateFromFile(String sourceFieName) throws IOException,JSONException {

		
		String sourceString = FileUtils.readFileToString(new File(sourceFieName), "UTF-8");
		readSourceTemplate(sourceString);

	}
	public abstract void readSourceTemplate(String sourceString) throws JSONException ;

	public abstract void readTartgetTemplate(String inputString)  throws JSONException ;
	// 读取表的模板
	public void readTargetTemplateFromFile(String fileName) throws IOException,JSONException {

		
		

		// 读取文件
		String inputString = FileUtils.readFileToString(new File(fileName), "UTF-8");
		readTartgetTemplate(inputString);

		logger.info("任务"+templateName+"导入的数据表为：" + targetTemplate.getTableName());

		

	}

	/*
	 * 读取导入模板之后，初始化Hbase批处理
	 * 
	 */
	/*
	 * private void afterReadTargetTemplate(Connection hConn) throws IOException {
	 * TableName tName = TableName.valueOf(targetTemplate.getTableName());
	 * BufferedMutatorParams params = new BufferedMutatorParams(tName);
	 * params.writeBufferSize(bufferSize * 1024 * 1024); // 可以自己设定阈值 5M 达到5M则提交一次
	 * 
	 * mutator = hConn.getBufferedMutator(params);
	 * 
	 * }
	 */

	private void cleanArray() {
		for (int i = 0; i < puts.length; i++) {
			puts[i] = null;
		}
	}
	private java.util.ArrayList<Put> getFragmentaryPuts(){
		java.util.ArrayList<Put> puts_list=new java.util.ArrayList<Put>();
		for (int i = 0; i < puts.length; i++) {
			if (puts[i] != null) {
				puts_list.add(puts[i]);
			}
		}
		return puts_list;
	}
	java.time.LocalDateTime getResultEndTime=null;
	java.time.LocalDateTime getResultStartTime=null;
	/*
	 * 获取数据、数据库啊查询花费时间
	 */
	public void beforeGetResult() {
		getResultStartTime=LocalDateTime.now();
	}
	/*
	 * 获取数据之后的统计
	 */
	public void afterGetResult() {
		getResultEndTime=LocalDateTime.now();
		long seconds=DateTools.betweenTwoTime(getResultStartTime, getResultEndTime, ChronoUnit.SECONDS);
		logger.info("任务"+templateName+"获取数据一共耗时"+seconds+"秒");
	}
	java.time.LocalDateTime fetchEndTime=null;
	java.time.LocalDateTime fetchStartTime=null;
	/*
	 * 循环结果集，在循环直接做标记
	 * 
	 */
	public void beforFetchResult() {
		fetchStartTime=LocalDateTime.now();
	}
	/*
	 * 循环结果集之后，统计循环情况
	 */
	public void afterFetchResult() {
		fetchEndTime=LocalDateTime.now();
		long seconds=DateTools.betweenTwoTime(fetchStartTime, fetchEndTime, ChronoUnit.SECONDS);
		long count_per_seconds=rowCountAll/seconds;
		logger.info("任务"+templateName+"循环数据一共耗时"+seconds+"秒,每秒处理记录数"+count_per_seconds);
	}

}
