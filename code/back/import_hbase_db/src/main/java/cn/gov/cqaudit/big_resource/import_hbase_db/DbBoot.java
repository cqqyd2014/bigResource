package cn.gov.cqaudit.big_resource.import_hbase_db;

import org.apache.hadoop.hbase.client.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cn.gov.cqaudit.big_resource.dao.config.ManualConfig;
import cn.gov.cqaudit.big_resource.import_hbase_module.impl.CSVImporter;
import cn.gov.cqaudit.big_resource.import_hbase_module.impl.DbImporter;

@SpringBootApplication(scanBasePackages = { "cn.gov.cqaudit.**" })
public class DbBoot implements CommandLineRunner {

	@Autowired
	DbImporter dbImporter;
	@Autowired
	private Connection hConn;
	@Autowired
	private ManualConfig mConfig;

	public static void main(String[] args) {
		SpringApplication.run(DbBoot.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/**
		 * 一共有三个参数，第一个是source_file，第二个是template_file,第三个是批量数据
		 *
		 */
		if (args.length != 3) {
			System.out.println("需要3个参数");
		}

		try {
			dbImporter.init(hConn,args[0],args[1],Integer.parseInt(args[2]));

			

			dbImporter.do_import_hbase_batch(hConn);
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}


	}
}
