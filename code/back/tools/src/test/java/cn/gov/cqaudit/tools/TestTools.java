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

       String a="1";
       java.util.ArrayList<String> list=new java.util.ArrayList<String>();
       list.add(a);
       System.out.println(list.get(0));
       a=null;
       System.out.println(list.get(0));
        
    }
}
