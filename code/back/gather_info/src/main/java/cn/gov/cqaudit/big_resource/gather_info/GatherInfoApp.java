package cn.gov.cqaudit.big_resource.gather_info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={
        "cn.gov.cqaudit.big_resource.gather_info.**"
})
public class GatherInfoApp {

	public static void main(String[] args) {
		SpringApplication.run(GatherInfoApp.class, args);
	}

}
