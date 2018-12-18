package cn.gov.cqaudit.generate_data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cn.gov.cqaudit.tools.Province_city;



@SpringBootApplication(scanBasePackages= {"cn.gov.cqaudit.tools"})

public class BootApplication implements CommandLineRunner {
	@Autowired
    private Province_city province_city;

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //System.out.println("a");
    }
}

