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
	 * 静态方法初始化，从china_provice_city.csv提取省市，区县信息，这个文件是来自国标，涵盖2016年7月以前的地区
	 */
	 final static java.util.HashMap<String,java.util.HashMap<String,String>> province_map=new java.util.HashMap<String,java.util.HashMap<String,String>>();
	static {
		try {

			String fileName = GenerateString.class.getResource("/china_province_city.csv").getFile();

			Reader in = new FileReader(fileName);
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			for (CSVRecord record : records) {
				String ext_id = record.get("ext_id");

				String name = record.get("name");
				int ext_id_len=ext_id.length();
				switch(ext_id_len){
					case 2:

						java.util.HashMap<String,String> empty_province=new java.util.HashMap<String,String>();
						province_map.put(ext_id,empty_province);

						break;
					case 12:

						java.util.HashMap<String,String> province_exist=(java.util.HashMap<String,String>)province_map.get(ext_id.substring(0,2));
						province_exist.put(ext_id.substring(0,6),name);
						break;
					default:
						break;
				}




			}
		} catch (java.io.FileNotFoundException ffe) {
			System.out.println("不能打开省市CSV文件" + ffe.toString());
		} catch (java.io.IOException ioe) {
			System.out.println("IO错误" + ioe.toString());
		}

	}

	/**
	*
	*随机生成生份证号
	* @param province_ids 省份id，其中11为北京市，12为天津市，13为河北省，14为陕西省，15为内蒙古，21为辽宁，22为吉林，23为黑龙江，31为上海，32为江苏，33为浙江，34为安徽，35为福建，36为江西，37为山东，41为河南，42为湖北，43为湖南，44为广东，45为广西，46为海南，50为重庆，51为四川，52为贵州，53为云南，54为西藏，61为陕西，62为甘肃，63为青海，64为宁夏，65新疆
   * @return Nothing.
   * @exception IOException On input error.
	*/
	public static String getChineseIDCard(java.util.ArrayList<String> province_ids) {
		java.util.ArrayList<String> dist_codes=new java.util.ArrayList<String>();
		if (province_ids==null){

		}
		else{

		}
		return "a";
	}

	/**
	*得到这个省份的所有地区编码
	* @param province_id 省份id
	* @return  地区编码列表.
	* @exception IOException On input error.
	*/
	private static java.util.ArrayList<String> getDistCodeNByProvinceId(String province_id){
		java.util.ArrayList<String> dists_list=new java.util.ArrayList<String>();
		java.util.HashMap<String,String> dists_map=(java.util.HashMap<String,String>)province_map.get(province_id);
		for(java.util.Map.Entry<String, String> entry: dists_map.entrySet())
        {
					dists_list.add(entry.getKey());

        }
		return dists_list;


	}




}
