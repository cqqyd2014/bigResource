package cn.gov.cqaudit.big_resource.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import cn.gov.cqaudit.big_resource.import_hbase_module.BootApplication;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={BootApplication.class})// 指定启动类
public class TestApp {

     @Test
     public void testSomeLibraryMethod() {

       AbstractApplicationContext context = new ClassPathXmlApplicationContext(
   				"/application-context.xml", TestApp.class);

   		context.registerShutdownHook();

   		UserUtils userUtils = context.getBean(UserUtils.class);
   		userUtils.initialize();
   		userUtils.addUsers();

   		UserRepository userRepository = context.getBean(UserRepository.class);
   		List<User> users = userRepository.findAll();
   		System.out.println("Number of users = " + users.size());
   		System.out.println(users);

    }
}
