package cn.gov.cqaudit.big_resource.import_hbase_module.abs;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.hbase.client.Put;
import org.json.JSONArray;
import org.json.JSONObject;

import cn.gov.cqaudit.big_resource.import_hbase_module.template.ImportDataTypeEnum;
import cn.gov.cqaudit.big_resource.import_hbase_module.template.Importer;
import cn.gov.cqaudit.big_resource.import_hbase_module.template.ImporterRow;



public abstract class DataImportOperationAbs<T> {
	
	public abstract void init();

	public abstract <T> Iterable<T> getList() throws IOException;

	/**
	 *
	 * 将原始的一条记录转换为对象，之后的对象再转put
	 * @param result
	 * @param rowNum
	 * @return
	 */

	public abstract Put mapPut(java.util.ArrayList<String> oneLine,int rowNum);

	/**
	 * 批量导入
	 * @param list
	 * @param batchCount 一次批量处理的数量
	 * @return
	 */
	public abstract int do_import_hbase_batch(Iterable<T> list)throws IOException;
	

	public Importer readTemplate(String fileName) {
		Importer importer;
		try {
			importer=new Importer();
			JSONObject templateObject = null;

	        //读取文件
	        String inputString = FileUtils.readFileToString(new File(fileName), "UTF-8");
	        //将读取的数据转换为JSONObject
	        JSONObject jsonObject = new JSONObject(inputString);

	        if (jsonObject != null) {
	            //取出按钮权限的数据
	        	templateObject = jsonObject.getJSONObject("import_template");
	        }

	        importer.setTableName(templateObject.getString("tableName"));
	        importer.setRowKey(templateObject.getString("rowKey"));
	        java.util.List<ImporterRow> rows=new java.util.ArrayList<ImporterRow>();
	        JSONArray rows_array=templateObject.getJSONArray("rows");

	        for (int i=0;i<rows_array.length();i++) {
	        	JSONObject row=rows_array.getJSONObject(i);
	        	String colName=row.getString("colName");
	        	
	        	ImporterRow i_row=null;
	        	
	        		i_row=new ImporterRow(colName);
	        	    
	        	rows.add(i_row);

	        }
	        importer.setRows(rows);


	        System.out.println(importer.getTableName());


	        return importer;
		}
		catch(Exception e) {
			System.out.print(e.toString());
			return null;
		}




	}

}
