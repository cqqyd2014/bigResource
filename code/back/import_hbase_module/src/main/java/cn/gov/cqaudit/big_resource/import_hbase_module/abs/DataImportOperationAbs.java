package cn.gov.cqaudit.big_resource.import_hbase_module.abs;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.BufferedMutatorParams;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;

import org.apache.hadoop.hbase.util.Bytes;
import org.json.JSONArray;
import org.json.JSONObject;

import cn.gov.cqaudit.big_resource.dao.common.HbaseDaoCommon;

import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.TargetTemplate;
import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.TargetTemplateDetail;
import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.TargetTemplateTypeEnum;

import cn.gov.cqaudit.big_resource.import_hbase_module.source_template.SourceTemplate;


public abstract class DataImportOperationAbs<T, S> extends HbaseDaoCommon {
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
	public void processOneRow(S record, Connection hconn) throws Exception {

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
			putManualBatch(hconn, Arrays.asList(puts));
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
			System.out.println(templateName+"已经导入数据" + batchCount * batchDoCount);
			//System.gc();
		}
	}

	// 处理批量之后剩下的零星
	public void afterProcessOneRow(Connection hconn) throws IOException {

		// tablePuts(puts);
		putManualBatch(hconn, getFragmentaryPuts());
		// puts.clear();
		cleanArray();
		System.out.println(templateName+"结束导入，一共导入数据" + (batchCount * batchDoCount + puts.length));
	}

	// 初始化环境
	public void init(Connection hConn, String sourceFileName, String templateFileName, int batchCount)
			throws IOException {

		System.out.println("读取导入表模板");
		readTargetTemplateFromFile( templateFileName);
		afterReadTargetTemplate(hConn);
		System.out.println("读取数据源");
		readSourceTemplate(sourceFileName);
		this.batchCount = batchCount;

		tName = TableName.valueOf(targetTemplate.getTableName());
		System.out.println("连接操作表" + tName);

		// puts=new java.util.ArrayList<Put>();
		puts = new Put[batchCount];
		System.out.println("初始化成功");
	}

	// 初始化环境
	public void init(Connection hConn, SourceTemplate sourceTemplate, TargetTemplate targetTemplate, int batchCount,String templateName) throws IOException {
		this.targetTemplate = targetTemplate;
		this.sourceTemplate = sourceTemplate;
		this.batchCount = batchCount;
		puts = new Put[batchCount];
		this.templateName=templateName;
		afterReadTargetTemplate(hConn);
		System.out.println("初始化成功");

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
	public abstract long do_import_hbase_batch(Connection hconn) throws Exception;

	// 读取数据源参数
	public void readSourceTemplateFromFile(String sourceFieName) throws IOException {

		
		String sourceString = FileUtils.readFileToString(new File(sourceFieName), "UTF-8");
		readSourceTemplate(sourceString);

	}
	public abstract void readSourceTemplate(String sourceString);

	public abstract void readTartgetTemplate(String inputString);
	// 读取表的模板
	public void readTargetTemplateFromFile(String fileName) throws IOException {

		
		

		// 读取文件
		String inputString = FileUtils.readFileToString(new File(fileName), "UTF-8");
		readTartgetTemplate(inputString);

		System.out.println("导入的数据表为：" + targetTemplate.getTableName());

		

	}

	/*
	 * 读取导入模板之后，初始化Hbase批处理
	 * 
	 */
	private void afterReadTargetTemplate(Connection hConn) throws IOException {
		TableName tName = TableName.valueOf(targetTemplate.getTableName());
		BufferedMutatorParams params = new BufferedMutatorParams(tName);
		params.writeBufferSize(bufferSize * 1024 * 1024); // 可以自己设定阈值 5M 达到5M则提交一次

		mutator = hConn.getBufferedMutator(params);

	}

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

}
