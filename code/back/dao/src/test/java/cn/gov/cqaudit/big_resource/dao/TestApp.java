package cn.gov.cqaudit.big_resource.dao;

import javax.annotation.Resource;

import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import cn.gov.cqaudit.big_resource.dao.BootApplication;
import cn.gov.cqaudit.big_resource.dao.inter.NodeInter;
import cn.gov.cqaudit.big_resource.entity.Node;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApp {
	/*@Resource(name="hbaseNodeImpl")        //实现类1中 @Service注解中标定的名称
    private NodeInter nodeImpl;*/
	/*@Autowired
	private HbaseTemplate hbaseTemplate;*/
     @Test
     public void testSome() {
    	 ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext-hbase.xml");
    	 BeanFactory factory = (BeanFactory) ctx;
    	 //获取bean的实例
    	 org.springframework.data.hadoop.hbase.HbaseTemplate hbaseTemplate=(org.springframework.data.hadoop.hbase.HbaseTemplate) factory.getBean("hbaseTemplate");

    	 Node node=new Node();
    	 node.setId("510304198012211031");
    	 node.setName("王利");
    	 hbaseTemplate.put("node", node.getId(), "cf1", "name", Bytes.toBytes(node.getName()));

   		

    }
}
