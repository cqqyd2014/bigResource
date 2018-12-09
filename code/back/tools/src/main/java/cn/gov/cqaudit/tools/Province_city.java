package cn.gov.cqaudit.tools;


import java.io.FileReader;
import java.io.Reader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Province_city {
	
	/**
	 * 根据本次查询要求统计的身份证前6位
	 */
	java.util.ArrayList<String> dists_list = new java.util.ArrayList<String>();
	int dists_count;

	/**
	 * 静态方法初始化，从china_provice_city.csv提取省市，区县信息，这个文件是来自国标，涵盖2016年7月以前的地区
	 */
	final static java.util.HashMap<String, java.util.HashMap<String, String>> province_map = new java.util.HashMap<String, java.util.HashMap<String, String>>();
	static {
		try {
			
			String fileName = Province_city.class.getResource("/china_province_city.csv").getFile();
			
			Reader in = new FileReader(fileName);
			
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
			for (CSVRecord record : records) {
				String ext_id = record.get("ext_id");

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

	}

	/**
	 * 得到这个省份的所有地区编码
	 * 
	 * @param province_id 省份id
	 * @return  地区编码列表.
	 * @exception IOException On input error.
	 */
	public java.util.ArrayList<String> getDistCodesNByProvinceId(String province_id) {

		java.util.ArrayList<String> dists_list = new java.util.ArrayList<String>();
		java.util.HashMap<String, String> dists_map = (java.util.HashMap<String, String>) province_map.get(province_id);
		if (dists_map==null) {
			return dists_list;
		}
		for (java.util.Map.Entry<String, String> entry : dists_map.entrySet()) {
			dists_list.add(entry.getKey());

		}
		return dists_list;

	}

	public java.util.ArrayList<String> getDisCodesByProviceIds(java.util.ArrayList<String> province_ids) {
		
		if (province_ids==null||province_ids.size()==0) {
			province_ids=ArrayListTools.intsToStringList(11, 60);
			
		}
		for (int i = 0; i < province_ids.size(); i++) {
			dists_list.addAll(getDistCodesNByProvinceId(province_ids.get(i))) ;
		}
		dists_count=dists_list.size();
		return dists_list;
	}
}
