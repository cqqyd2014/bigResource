package cn.gov.cqaudit.big_resource.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.hadoop.fs.FsShell;

@SpringBootApplication
public class EntityBoot  implements CommandLineRunner {

	/*
	 * @Autowired private FsShell shell;
	 */

	@Override
	public void run(String... args) {

	}

	public static void main(String[] args) {
		SpringApplication.run(EntityBoot.class, args);
	}
}
