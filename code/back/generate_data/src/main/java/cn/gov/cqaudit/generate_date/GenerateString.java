/**
* Copyright Chongqing Audit Bureau.
* All right reserved.
* FileName: GenerateString.java
* @author 鐜嬪埄
* @Date    2018骞?湀6鏃�
* @version 1.00
*/
package cn.gov.cqaudit.generate_date;

import java.io.FileReader;
import java.io.Reader;
import java.util.Iterable;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
/**
*鐢熸垚瀛楃涓茬被鏁版嵁
*/
public Class GenerateString{

/**
*闈欐�佸潡锛岀敤鏉ュ垵濮嬪寲璇诲叆china_provice_city.csv鏁版嵁
*/
static{
  Reader in = new FileReader("path/to/file.csv");
Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
for (CSVRecord record : records) {
    String id = record.get("ID");
    String customerNo = record.get("CustomerNo");
    String name = record.get("Name");
}

}

/**
*鐢熸垚韬唤璇佸瓧绗︿覆
*0-2涓虹渷浠藉湴鍖猴紝3-5涓哄競鍘垮湴鍖猴紝6-13涓烘棩鏈燂紝14-16浣嶉殢鏈烘暟瀛楋紝17涓烘牎楠屼綅
*/
public static String getChineseIDCard(){
  return "a";
}
}
