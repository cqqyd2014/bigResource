package cn.gov.cqaudit.big_resource.dao;

import javax.annotation.Resource;

import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import cn.gov.cqaudit.big_resource.dao.BootApplication;
import cn.gov.cqaudit.big_resource.dao.common.abs.HbaseTableOperationAbs;

import cn.gov.cqaudit.big_resource.dao.config.ManualConfig;

import cn.gov.cqaudit.big_resource.entity.Node;
import cn.gov.cqaudit.big_resource.entity.NodePerson;
import cn.gov.cqaudit.tools.ListTools;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApp {
	@Resource(name="hbaseNodePersonImpl")        //实现类1中 @Service注解中标定的名称
    private HbaseTableOperationAbs<NodePerson> nodePersonImpl;
	@Autowired
	private Connection hConn;
	@Autowired
	private ManualConfig mConfig;

     @Test
     public void testSome() {
		/*
		 * try { NodePerson node=new
		 * NodePerson("510304198012211031","王利",true,"1234",100);
		 * 
		 * 
		 * nodePersonImpl.putObjectManaulBatch(hConn, ListTools.oneToList(node),
		 * mConfig.getPut_batch_buffer_size()) ;
		 * 
		 * NodePerson
		 * node_resule=nodePersonImpl.getByRowKey(hConn,"510304198012211031");
		 * System.out.println(node_resule.getId()+node_resule.getMobile_phone()); }
		 * catch(Exception e) { System.out.println(e.toString()); }
		 */
    	 
    	 //hbaseTemplate.put("node", node.getId(), "cf1", "name", Bytes.toBytes(node.getName()));



    }
}
