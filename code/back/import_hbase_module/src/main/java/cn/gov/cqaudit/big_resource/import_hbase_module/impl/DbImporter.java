package cn.gov.cqaudit.big_resource.import_hbase_module.impl;


import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.hadoop.hbase.client.Put;
import org.springframework.stereotype.Component;

import cn.gov.cqaudit.big_resource.import_hbase_module.abs.DataImportOperationAbs;

@Component("dbImporter")
public class DbImporter extends DataImportOperationAbs<ResultSet>{

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ResultSet getResultset() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Put mapPut(ArrayList<String> oneLine, int rowNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int do_import_hbase_batch(ResultSet resultset) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

}
