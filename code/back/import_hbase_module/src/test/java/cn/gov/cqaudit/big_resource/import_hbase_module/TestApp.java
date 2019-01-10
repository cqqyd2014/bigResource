package cn.gov.cqaudit.big_resource.import_hbase_module;



import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cn.gov.cqaudit.big_resource.import_hbase_module.ImportHbaseModuleBoot;
import cn.gov.cqaudit.big_resource.import_hbase_module.impl.CSVImporter;



@RunWith(SpringRunner.class)
@SpringBootTest(classes=ImportHbaseModuleBoot.class)
public class TestApp {

	
	  @Autowired 
	  CSVImporter cSVImporter;
	 
     @Test
     public void testSome() {
    	 System.out.println("dd");
		
		 try {
			cSVImporter.readTargetTemplateFromFile("D:/1.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

    }
}
