package cn.gov.cqaudit.big_resource.hbase_module.jdbc;

import org.apache.hadoop.hbase.client.Result;

public interface RowMapper<T> {

	T mapRow(Result result, int rowNum) throws Exception;
}
