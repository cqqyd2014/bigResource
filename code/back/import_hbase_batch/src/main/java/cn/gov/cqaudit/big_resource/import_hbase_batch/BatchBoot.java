package cn.gov.cqaudit.big_resource.import_hbase_batch;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.hadoop.hbase.client.Connection;
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

@SpringBootApplication(scanBasePackages = { "cn.gov.cqaudit.**" })
public class BatchBoot implements CommandLineRunner {

	@Autowired
	BatchLoader batchLoader;
	@Autowired
	private HbaseTemplate hbaseTemplate;
	public static void main(String[] args) {
		SpringApplication.run(BatchBoot.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		BatchTemplate batchTemplate=batchLoader.load(args[0]);
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
    		System.out.println("子线程" + detail.getTemplate_name() + "开始运行");
    		SourceTemplateTypeEnum type = detail.getSourceTemplate().getType();
    		long result = 0l;
    		switch (type) {
    		case CSV:
    			cSVImporter.init(detail.getSourceTemplate(), detail.getTargetTemplate(), batchCount,
    					detail.getTemplate_name());
    			result = cSVImporter.do_import_hbase_batch(hbaseTemplate);
    			break;
    		case Database:
    			//System.out.println(detail.getTargetTemplate());
    			//System.out.println(detail.getSourceTemplate());
    			//System.out.println("------");
    			dbImporter.init( detail.getSourceTemplate(), detail.getTargetTemplate(), batchCount,
    					detail.getTemplate_name());
    			result = dbImporter.do_import_hbase_batch(hbaseTemplate);
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
