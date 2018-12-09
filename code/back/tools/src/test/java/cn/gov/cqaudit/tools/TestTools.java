package cn.gov.cqaudit.tools;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class TestTools {
	  @Test
	  void testa() {

		  
		  cn.gov.cqaudit.tools.Province_city pc=new cn.gov.cqaudit.tools.Province_city();
		  System.out.println(pc.getDisCodesByProviceIds(null).toString());
		  assertEquals(1, 1);
	 }
}
