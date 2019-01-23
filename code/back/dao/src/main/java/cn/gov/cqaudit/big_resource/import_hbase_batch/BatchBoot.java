package cn.gov.cqaudit.big_resource.import_hbase_batch;

import java.io.File;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.io.FileUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cn.gov.cqaudit.big_resource.hbase_module.jdbc.HbaseTemplate;
import cn.gov.cqaudit.big_resource.import_hbase_batch.template.BatchTemplate;
import cn.gov.cqaudit.big_resource.import_hbase_batch.template.BatchTemplateDetail;

import cn.gov.cqaudit.big_resource.import_hbase_module.impl.CSVImporter;
import cn.gov.cqaudit.big_resource.import_hbase_module.impl.DbImporter;
import cn.gov.cqaudit.big_resource.import_hbase_module.source_template.SourceTemplateTypeEnum;
import cn.gov.cqaudit.big_resource.import_hbase_module.template_loader.TargetLoader;
import cn.gov.cqaudit.big_resource.hive_module.jdbc.*;


@SpringBootApplication(scanBasePackages = { "cn.gov.cqaudit.**" })
public class BatchBoot implements CommandLineRunner {
	
	@Autowired
	BatchLoader batchLoader;
	@Autowired
	private HbaseTemplate hbaseTemplate;
	@Autowired
	HiveTemplate hiveTemplate;
	
	protected static final Logger logger = LoggerFactory.getLogger(BatchBoot.class);
	
	public static void main(String[] args) {
		SpringApplication.run(BatchBoot.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/* if (args.length==1) { */
			String inputString = FileUtils.readFileToString(new File("/u01/software/bigResource/code/back/dao/src/main/resources/source.json"), "UTF-8");
			doBatch(inputString);
		
		/* } */
	
	}
	public void doBatch(String inputString) throws Exception {
		BatchTemplate batchTemplate=batchLoader.loadString(inputString);
		//检查批量模板，如果一次批量中有相同的表，所有表必须为overwrite=false.
		java.util.HashMap<String, Boolean> check_map=new java.util.HashMap<String, Boolean>();
		//logger.info("开开始始检检测测模模板板");
		for(BatchTemplateDetail detail:batchTemplate.getDetails()) {
			//Log.info("当前"+detail.getTargetTemplate().toString());
			String tableName=detail.getTargetTemplate().getTableName();
			//logger.info("执执行行"+tableName);
			boolean current_flag=detail.getTargetTemplate().isOverWrite();
			if(check_map.get(tableName)==null) {
				//记录中还没出现该表
				check_map.put(tableName, detail.getTargetTemplate().isOverWrite());
				//Log.info("记记录录中中没没有有表表"+tableName);
			}
			else {
				//如果有记录，那么这个表多次出现，之前的记录和当前的记录的overwirte都必须为false
				boolean before_flag=check_map.get(tableName);
				//Log.info("记记录录中中有有表表"+tableName+"上次的的结结果果是是"+before_flag+"当当前前的的结结果果是是"+current_flag);
				if (before_flag||current_flag) {
					throw new Exception(tableName+"表，多次出现在在批量导入中，但是OVERWRITE为TRUE");
				}
			}
		}
		//logger.info("导导入入模模板板检检测测完完成成");
		int batchCount=batchTemplate.getBatchCount();
		java.util.ArrayList<BatchTemplateDetail> details=batchTemplate.getDetails();
		ExecutorService executor = Executors.newCachedThreadPool();
		for (BatchTemplateDetail detail:details) {
			
			Task task = new Task(detail,batchCount);
	        Future<Long> result = executor.submit(task);
		}
        executor.shutdown();
	}
	

	//内部类
    private class Task implements Callable<Long> {



    	@Override
    	public Long call() throws Exception {
    		CSVImporter cSVImporter=new CSVImporter();
    		DbImporter dbImporter=new DbImporter();
    		logger.info("任务" + detail.getTemplate_name() + "开始运行");
    		SourceTemplateTypeEnum type = detail.getSourceTemplate().getType();
    		long result = 0l;
    		switch (type) {
    		case CSV:
    			cSVImporter.init(detail.getSourceTemplate(), detail.getTargetTemplate(), batchCount,
    					detail.getTemplate_name());
    			result = cSVImporter.do_import_hbase_batch(hbaseTemplate,hiveTemplate);
    			break;
    		case Database:

    			dbImporter.init( detail.getSourceTemplate(), detail.getTargetTemplate(), batchCount,
    					detail.getTemplate_name());
    			result = dbImporter.do_import_hbase_batch(hbaseTemplate,hiveTemplate);
    			break;
    		default:
    			result = 0l;
    			break;
    		}
    		return result;

    	}

    	public Task(BatchTemplateDetail detail, int batchCount) {
    		super();
    		this.detail = detail;
    		this.batchCount = batchCount;
    	}

    	BatchTemplateDetail detail;

    	int batchCount;

    	public BatchTemplateDetail getDetail() {
    		return detail;
    	}

    	public void setDetail(BatchTemplateDetail detail) {
    		this.detail = detail;
    	}

    }
}
