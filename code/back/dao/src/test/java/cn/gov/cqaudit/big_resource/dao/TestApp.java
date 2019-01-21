package cn.gov.cqaudit.big_resource.dao;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.util.Bytes;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import cn.gov.cqaudit.big_resource.dao.common.abs.HbaseTableOperationAbs;

import cn.gov.cqaudit.big_resource.dao.config.ManualConfig;
import cn.gov.cqaudit.big_resource.import_hbase_batch.*;
import cn.gov.cqaudit.big_resource.import_hbase_batch.template.BatchTemplate;
import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.TargetTemplate;
import cn.gov.cqaudit.big_resource.entity.Node;
import cn.gov.cqaudit.big_resource.entity.NodePerson;
import cn.gov.cqaudit.big_resource.hbase_module.jdbc.HbaseTemplate;
import cn.gov.cqaudit.tools.ListTools;
import cn.gov.cqaudit.big_resource.hive_module.jdbc.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=DaoBoot.class)
public class TestApp {
	
	@Autowired
	private HiveTemplate hiveTemplate;
	@Autowired
	private HbaseTemplate hbaseTemplate;
	@Autowired
	BatchLoader batchLoader;
	
     @Test
     public void testSome() {
		
    	 try {
			BatchTemplate batchTemplate= batchLoader.load("d:/source.json");
			TargetTemplate targetTemplate=batchTemplate.getDetails().get(1).getTargetTemplate();
			hbaseTemplate.createTableWithRegions(targetTemplate.getTableName(), "cf1", "0", "9", 10);
			hiveTemplate.createHiveOuterTableIntegrationHBase(targetTemplate);
			
			
			
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	



    }
}
