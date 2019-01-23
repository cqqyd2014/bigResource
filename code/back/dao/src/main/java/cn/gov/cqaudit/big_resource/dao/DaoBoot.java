package cn.gov.cqaudit.big_resource.dao;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "cn.gov.cqaudit.**" })
public class DaoBoot implements CommandLineRunner {



	@Override
	public void run(String... args) {

	}

	/*
	 * public static void main(String[] args) { SpringApplication.run(DaoBoot.class,
	 * args); }
	 */
}
