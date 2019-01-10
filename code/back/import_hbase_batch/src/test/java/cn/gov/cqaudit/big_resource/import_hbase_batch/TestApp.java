package cn.gov.cqaudit.big_resource.import_hbase_batch;

import java.io.IOException;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=BatchBoot.class)
public class TestApp {

	
	  @Autowired private BatchLoader batchLoader;
	 
     @Test
     public void testSome() {

    	 try {
			batchLoader.load("D:/source.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("错误"+e.toString());
		} 

    }
}
