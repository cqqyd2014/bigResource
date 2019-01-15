package cn.gov.cqaudit.big_resource.hive_module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;





@SpringBootApplication(scanBasePackages= {"cn.gov.cqaudit.**"})

public class HiveModuleBoot implements CommandLineRunner {



    public static void main(String[] args) {
        SpringApplication.run(HiveModuleBoot.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
