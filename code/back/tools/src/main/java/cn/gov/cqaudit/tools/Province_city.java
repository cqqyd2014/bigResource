package cn.gov.cqaudit.tools;


import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;


@Component("province_city")
@Scope("prototype")
public class Province_city {

	/**
	 * 根据本次查询要求统计的身份证前6位
	 */
	java.util.ArrayList<String> dists_list = new java.util.ArrayList<String>();
	int dists_count=0;
	java.util.ArrayList<String> province_ids;


	/**
	 * 静态方法初始化，从china_provice_city.csv提取省市，区县信息，这个文件是来自国标，涵盖2016年7月以前的地区
	 */
	 static java.util.HashMap<String, java.util.HashMap<String, String>> province_map = new java.util.HashMap<String, java.util.HashMap<String, String>>();

static {
		try {
			//File file=ResourceUtils.getFile("classpath:china_province_city.csv");
			InputStream is = Province_city.class.getClassLoader().getResourceAsStream("china_province_city.csv");

			Reader in = new InputStreamReader(is);
			//Reader in= new java.io.FileReader(file);
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

			for (CSVRecord record : records) {
				String ext_id = record.get("ext_id");
				//System.out.println(ext_id);
				String name = record.get("name");
				int ext_id_len = ext_id.length();
				switch (ext_id_len) {
				case 2:

					java.util.HashMap<String, String> empty_province = new java.util.HashMap<String, String>();
					province_map.put(ext_id, empty_province);

					break;
				case 12:

					java.util.HashMap<String, String> province_exist = (java.util.HashMap<String, String>) province_map
							.get(ext_id.substring(0, 2));
					province_exist.put(ext_id.substring(0, 6), name);
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
	catch (Exception ioe) {
		System.out.println("一般错误" + ioe.toString());
	}

		System.out.println("ProvinceCity初始化成功");
	}

	/**
	 * 得到这个省份的所有地区编码
	 *
	 * @param province_id 省份id
	 * @return  地区编码列表.
	 * @exception IOException On input error.
	 */
	private java.util.ArrayList<String> getDistCodesNByProvinceId(String province_id) {

		java.util.ArrayList<String> dists_list = new java.util.ArrayList<String>();
		java.util.HashMap<String, String> dists_map = (java.util.HashMap<String, String>) province_map.get(province_id);
		if (dists_map==null) {
			return dists_list;
		}
		for (java.util.Map.Entry<String, String> entry : dists_map.entrySet()) {
			dists_list.add(entry.getKey());

		}
		//readyForRamdomFecth=true;
		return dists_list;

	}
	/**
	 * 得到省份列表的所有地区编码存入dists_list，同时统计列表个数存入dists_count
	 *
	 * @param province_ids 省份列表
	 * @return  地区编码列表.
	 * @exception IOException On input error.
	 */
	public  java.util.ArrayList<String> initDisstCodesByProviceIds(java.util.ArrayList<String> province_ids) {

		if (province_ids==null||province_ids.size()==0) {
			province_ids=ArrayListTools.intsToStringList(11, 60);

		}
		for (int i = 0; i < province_ids.size(); i++) {
			dists_list.addAll(getDistCodesNByProvinceId(province_ids.get(i))) ;
		}
		dists_count=dists_list.size();

		return dists_list;
	}

	/**
	 * 随机获得一个用于身份证的地区编码
	 *

	 * @return  地区编码列表.
	 * @exception IOException On input error.
	 */
	public String getRandomDistCode() {


		Random randSeed = new Random();
		int flag=randSeed.nextInt(dists_count) ;
		return dists_list.get(flag);

	}
}
