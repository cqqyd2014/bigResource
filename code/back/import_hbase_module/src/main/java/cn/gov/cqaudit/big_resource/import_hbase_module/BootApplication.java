package cn.gov.cqaudit.big_resource.import_hbase_module;

import org.apache.hadoop.fs.FileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.hadoop.fs.FsShell;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@SpringBootApplication
public class BootApplication implements CommandLineRunner {

	@Autowired
	private FsShell shell;

	@Override
	public void run(String... args) {
		for (FileStatus s : shell.lsr("/hbase")) {
			System.out.println("> " + s.getPath());
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
	}
}
