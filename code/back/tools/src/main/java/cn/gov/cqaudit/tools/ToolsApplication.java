package cn.gov.cqaudit.tools;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class ToolsApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ToolsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(DateTools.convertDate("2018-12-21", "yyyy-mm-dd"));
    }
}

