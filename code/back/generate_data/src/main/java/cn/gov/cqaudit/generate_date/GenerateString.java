/**
* Copyright Chongqing Audit Bureau.
* All right reserved.
* FileName: GenerateString.java
* @author 王利
* @Date    2018�?��6旄1�7
* @version 1.00
*/
package cn.gov.cqaudit.generate_date;

import java.io.FileReader;
import java.io.Reader;
import java.util.Iterable;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
/**
*生成字符串类数据
*/
public Class GenerateString{

/**
*静�1�7�块，用来初始化读入china_provice_city.csv数据
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
*生成身份证字符串
*0-2为省份地区，3-5为市县地区，6-13为日期，14-16位随机数字，17为校验位
*/
public static String getChineseIDCard(){
  return "a";
}
}
