package cn.gov.cqaudit.big_resource.import_hbase_db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cn.gov.cqaudit.big_resource.import_hbase_module.impl.CSVImporter;
import cn.gov.cqaudit.big_resource.import_hbase_module.impl.DbImporter;

@SpringBootApplication(scanBasePackages = { "cn.gov.cqaudit.**" })
public class DbBoot implements CommandLineRunner {

	@Autowired
	DbImporter dbImporter;

	public static void main(String[] args) {
		SpringApplication.run(DbBoot.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/**
		 * 一共有
		 *
		 */
		if (args.length != 3) {
			System.out.println("需要3个参数");
		}

		try {
			dbImporter.init(args[1], Integer.parseInt(args[0]));



			dbImporter.do_import_hbase_batch(cSVImporter.getList());
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}


	}
}
