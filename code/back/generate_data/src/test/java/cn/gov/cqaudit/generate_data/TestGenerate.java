package cn.gov.cqaudit.generate_data;

import cn.gov.cqaudit.tools.Province_city;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.context.annotation.ComponentScan;


@RunWith(SpringRunner.class)
@SpringBootTest

public class TestGenerate{



    @Autowired
 	 Province_city province_city;
 	 @Test
     public void testOne(){
 			province_city.initDisstCodesByProviceIds(null);
         System.out.println(province_city.getRandomDistCode());
     }




}
