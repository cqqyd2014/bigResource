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
	*
	*随机生成生份证号
	* @param province_ids 省份id，其中11为北京市，12为天津市，13为河北省，14为陕西省，15为内蒙古，21为辽宁，22为吉林，23为黑龙江，31为上海，32为江苏，33为浙江，34为安徽，35为福建，36为江西，37为山东，41为河南，42为湖北，43为湖南，44为广东，45为广西，46为海南，50为重庆，51为四川，52为贵州，53为云南，54为西藏，61为陕西，62为甘肃，63为青海，64为宁夏，65新疆
   * @return Nothing.
   * @exception IOException On input error.
	*/
	public String getChineseIDCard(java.util.ArrayList<String> province_ids) {

		

		cn.gov.cqaudit.tools.Province_city pc=new cn.gov.cqaudit.tools.Province_city();
		java.util.ArrayList<String> dist_codes=pc.getDisCodesByProviceIds(province_ids);
		
		
		return "a";
	}





}
