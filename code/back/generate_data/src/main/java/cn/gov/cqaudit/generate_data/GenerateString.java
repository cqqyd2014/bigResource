/**
* Copyright Chongqing Audit Bureau.
* All right reserved.
* FileName: GenerateString.java
* @author 王利
* @Date 2018-12-07
* @version 1.00
*/
package cn.gov.cqaudit.generate_data;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.lang.Iterable;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
*
*/
public class GenerateString {

	/**
	 * 静态方法初始化，从china_provice_city.csv提取省市，区县信息
	 */
	static {
/*		try {*/
			
			String fileName = GenerateString.class.getResource("/").getFile();
			System.out.println(fileName);
			/*Reader in = new FileReader(fileName);
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			for (CSVRecord record : records) {
				String id = record.get("ID");
				String customerNo = record.get("CustomerNo");
				String name = record.get("Name");
			}
		} catch (java.io.FileNotFoundException ffe) {
			System.out.println("不能打开省市CSV文件" + ffe.toString());
		} catch (java.io.IOException ioe) {
			System.out.println("IO错误" + ioe.toString());
		}*/

	}

	/**
	*
	*
	*/
	public static String getChineseIDCard() {
		System.out.println("test");
		return "a";
	}
}
