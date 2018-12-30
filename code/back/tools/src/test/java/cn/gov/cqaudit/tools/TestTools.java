package cn.gov.cqaudit.tools;


import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.*;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={ToolsApplication.class})// 指定启动类
public class TestTools {
/*
	@Autowired
	Province_city province_city
	  @Test
	  void testa() {



		  System.out.println(province_city.getDisCodesByProviceIds(null).toString());
		  //assertEquals(1, 1);
	 }


	 */
	
	 @Test
    public void testOne(){

        java.util.Date d3=new java.util.Date();
        for (int i=0;i<10000000;i++) {
			StringTools.checkDate2("1980-12-31");
		}
		java.util.Date d4=new java.util.Date();
		System.out.println(d4.getTime()-d3.getTime());
        
        
    }
}
