package cn.gov.cqaudit.big_resource.import_hbase_module.impl;

import java.sql.Types;
import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.ImportDataTypeEnum;

public class JdbcDataCompareHbaseData {
	public static boolean compare(String jdbcType,ImportDataTypeEnum hbaseType) {
		System.out.println("输入的分析类型分别为"+jdbcType+"与"+hbaseType);
		switch (hbaseType) {
		case	DATE:
			switch (jdbcType) {
			case "java.sql.Date":
				return true;
				
			case "java.sql.Timestamp":
				return true;
				
			default:
				return false;
			}

		case	DATETIME:
			switch (jdbcType) {
			
			case "java.sql.Date":
				return true;
				
			case "java.sql.Timestamp":
				return true;
				
			default:
				return false;
			}
		case	STRING:
			switch (jdbcType) {
			
			case "java.lang.String":
				return true;
				
			
				
			default:
				return false;
			}
		case	NUMBER:
			switch (jdbcType) {
			
			case "java.lang.Integer":
				return true;
			case "java.lang.Float":
				return true;
			case "java.lang.Double":
				return true;
			case "java.math.BigDecimal":
				return true;
				
			default:
				return false;
			}
		default:
			return false;
			
		}
		
	}

}
