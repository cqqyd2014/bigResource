package cn.gov.cqaudit.big_resource.import_hbase_module;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import cn.gov.cqaudit.big_resource.import_hbase_module.BootApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={BootApplication.class})// 指定启动类
public class TestApp {

     @Test
     public void testSomeLibraryMethod() {

       System.out.println("3434");

    }
}
