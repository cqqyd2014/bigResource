package cn.gov.cqaudit.big_resource.hive_create_table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;





@SpringBootApplication(scanBasePackages= {"cn.gov.cqaudit.tools"})

public class HiveCreateTableBoot implements CommandLineRunner {



    public static void main(String[] args) {
        SpringApplication.run(HiveCreateTableBoot.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
