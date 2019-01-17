package cn.gov.cqaudit.big_resource.hbase_module.jdbc;

import org.apache.hadoop.hbase.client.Table;

public interface TableCallback<T> {

	/**
	 * Gets called by {@link HbaseTemplate} execute with an active Hbase table. Does need to care about activating or closing down the table.
	 * 
	 * @param table active Hbase table
	 * @return a result object, or null if none
	 * @throws Throwable thrown by the Hbase API
	 */
	T doInTable(Table table) throws Throwable;
}