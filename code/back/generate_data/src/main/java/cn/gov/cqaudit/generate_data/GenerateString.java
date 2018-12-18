/**opyright Chongqing Audit Bureau.
* All right reserved.
* FileName: GenerateString.java
* @author
* @Date 2018-12-07
* @version 1.00
*/
package cn.gov.cqaudit.generate_data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import cn.gov.cqaudit.tools.Province_city;

/**
*
*/
@ComponentScan(basePackages = "cn.gov.cqaudit.tools")
@Component("generateString")
public class GenerateString {

	
	@Autowired
	Province_city province_city;

	/**
	*
	*生成身份证号
	* @param province_ids 省份编号列表，如果为空，返回全国身份证
   * @return Nothing.
   * @exception IOException On input error.
	*/
	public java.util.ArrayList<String> getChineseIDCard(java.util.ArrayList<String> province_ids,int start_year,int end_year,int num) {
		java.util.ArrayList<String> result=new java.util.ArrayList<String>();
		
		//根据省市得到地区编码
		province_city.initDisstCodesByProviceIds(province_ids);
		
		for (int i=0;i<num;i++) {
			String id_str="";
			String dis_code=province_city.getRandomDistCode();
			id_str=id_str+dis_code;
		}
		
		
		
		
		return result;
	}





}

