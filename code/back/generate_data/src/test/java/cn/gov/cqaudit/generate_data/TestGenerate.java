package cn.gov.cqaudit.generate_data;

import org.junit.Test;
import static org.junit.Assert.*;
public class TestGenerate{

  @Test public void testGenerateData() {


     assertEquals("a", GenerateString.getChineseIDCard());
 }

}
