package cn.gov.cqaudit.big_resource.hive_module;



import java.io.IOException;

import javax.annotation.Resource;

import org.apache.hadoop.hbase.client.Connection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.jdbc.core.JdbcTemplate;




@RunWith(SpringRunner.class)
@SpringBootTest(classes=HiveModuleBoot.class)
public class TestApp {

	/*
	@Resource(name="hiveJdbcTemplate")        //实现类1中 @Service注解中标定的名称
    private JdbcTemplate hiveJdbcTemplate;
	 */
	
	@Autowired
	HiveClientFactory hiveClientFactory;
     @Test
     public void testSome() {
    	 
    	
    	
		
		

    }
}
