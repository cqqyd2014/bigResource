package cn.gov.cqaudit.big_resource.hive_module;



import java.io.IOException;

import org.apache.hadoop.hbase.client.Connection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.hadoop.hive.HiveClientFactory;




@RunWith(SpringRunner.class)
@SpringBootTest(classes=HiveModuleBoot.class)
public class TestApp {

	
	@Autowired
	private HiveClientFactory hiveClientFactory;
	 
     @Test
     public void testSome() {
    	
		
		

    }
}
