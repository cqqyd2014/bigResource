package cn.gov.cqaudit.big_reource.etl_tools.shell;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsoleApp implements CommandLineRunner{
  public static void main(String[] args) throws Exception{
      SpringApplication.run(ConsoleApp.class,args);
  }


  @Override
  public void run(String... args) throws Exception{

  }

}
