package cn.gov.cqaudit.tools;

import cn.gov.cqaudit.tools.Province_city;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
	 @Autowired
	 Province_city province_city;
	 @Test
    public void testOne(){
			province_city.initDisstCodesByProviceIds(null);
        System.out.println(province_city.getRandomDistCode());
    }
}
