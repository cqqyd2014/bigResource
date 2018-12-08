package cn.gov.cqaudit.generate_data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;
public class TestGenerate{

  @Test public void testGenerateData() {


     assertEquals("a", GenerateString.getChineseIDCard(null));
 }
 @BeforeAll
    static void initAll() {
    }

    @BeforeEach
    void init() {
    }

}
