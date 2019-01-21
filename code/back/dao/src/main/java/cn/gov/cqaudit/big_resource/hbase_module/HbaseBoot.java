package cn.gov.cqaudit.big_resource.hbase_module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication(scanBasePackages= {"cn.gov.cqaudit.**"})

public class HbaseBoot implements CommandLineRunner {

	

    public static void main(String[] args) {
        SpringApplication.run(HbaseBoot.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    
    }
}
