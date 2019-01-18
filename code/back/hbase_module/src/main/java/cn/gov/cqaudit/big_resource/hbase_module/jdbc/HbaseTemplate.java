package cn.gov.cqaudit.big_resource.hbase_module.jdbc;


import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.BufferedMutatorParams;
import org.apache.hadoop.hbase.client.Connection;

public class HbaseTemplate extends HbaseAccessor implements HbaseOperations {

	//private boolean autoFlush = true;
	private long put_batch_buffer_size;

	public long getPut_batch_buffer_size() {
		return put_batch_buffer_size;
	}

	public void setPut_batch_buffer_size(long put_batch_buffer_size) {
		this.put_batch_buffer_size = put_batch_buffer_size;
	}

	public HbaseTemplate() {
	}

	public HbaseTemplate(Connection configuration) {
		setTableConn(configuration);
		afterPropertiesSet();
	}
	//关闭hbase链接
	@PreDestroy
	private void closeConnection() {
		if (getTableConn()!=null) {
			try {
				getTableConn().close();
				System.out.println("已经关闭HBase链接");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public <T> T execute(String tableName, TableCallback<T> action) {
		Assert.notNull(action, "Callback object must not be null");
		Assert.notNull(tableName, "No table specified");

		Table table = getTable(tableName);

		try {
			//boolean previousFlushSetting = applyFlushSetting(table);
			T result = action.doInTable(table);
			//flushIfNecessary(table, previousFlushSetting);
			return result;
		} catch (Throwable th) {
			if (th instanceof Error) {
				throw ((Error) th);
			}
			if (th instanceof RuntimeException) {
				throw ((RuntimeException) th);
			}
			throw convertHbaseAccessException((Exception) th);
		} finally {
			releaseTable(tableName, table);
		}
	}

	private Table getTable(String tableName) {
		return HbaseUtils.getHTable(tableName,  getCharset(), getTableConn());
	}

	private void releaseTable(String tableName, Table table) {
		HbaseUtils.releaseTable(tableName, table, getTableConn());
	}
	
	private void releaseMutator(String tableName,BufferedMutator mutator) {
		HbaseUtils.releaseMutator(tableName, mutator, getTableConn());
	}

	/*
	 * @SuppressWarnings("deprecation") private boolean applyFlushSetting(Table
	 * table) { boolean autoFlush = table.isAutoFlush(); if (table instanceof
	 * HTable) { ((HTable) table).setAutoFlush(this.autoFlush); } return autoFlush;
	 * }
	 * 
	 * @SuppressWarnings("deprecation") private void restoreFlushSettings(Table
	 * table, boolean oldFlush) { if (table instanceof HTable) { if
	 * (table.isAutoFlush() != oldFlush) { ((HTable) table).setAutoFlush(oldFlush);
	 * } } }
	 */

	/*
	 * private void flushIfNecessary(Table table, boolean oldFlush) throws
	 * IOException { // TODO: check whether we can consider or not a table scope
	 * table.flushCommits(); restoreFlushSettings(table, oldFlush); }
	 */

	public DataAccessException convertHbaseAccessException(Exception ex) {
		return HbaseUtils.convertHbaseException(ex);
	}

	@Override
	public <T> T find(String tableName, String family, final ResultsExtractor<T> action) {
		Scan scan = new Scan();
		scan.addFamily(family.getBytes(getCharset()));
		return find(tableName, scan, action);
	}

	@Override
	public <T> T find(String tableName, String family, String qualifier, final ResultsExtractor<T> action) {
		Scan scan = new Scan();
		scan.addColumn(family.getBytes(getCharset()), qualifier.getBytes(getCharset()));
		return find(tableName, scan, action);
	}

	@Override
	public <T> T find(String tableName, final Scan scan, final ResultsExtractor<T> action) {
		return execute(tableName, new TableCallback<T>() {
			@Override
			public T doInTable(Table htable) throws Throwable {
				ResultScanner scanner = htable.getScanner(scan);
				try {
					return action.extractData(scanner);
				} finally {
					scanner.close();
				}
			}
		});
	}

	@Override
	public <T> List<T> find(String tableName, String family, final RowMapper<T> action) {
		Scan scan = new Scan();
		scan.addFamily(family.getBytes(getCharset()));
		return find(tableName, scan, action);
	}

	@Override
	public <T> List<T> find(String tableName, String family, String qualifier, final RowMapper<T> action) {
		Scan scan = new Scan();
		scan.addColumn(family.getBytes(getCharset()), qualifier.getBytes(getCharset()));
		return find(tableName, scan, action);
	}

	@Override
	public <T> List<T> find(String tableName, final Scan scan, final RowMapper<T> action) {
		return find(tableName, scan, new RowMapperResultsExtractor<T>(action));
	}

	@Override
	public <T> T get(String tableName, String rowName, final RowMapper<T> mapper) {
		return get(tableName, rowName, null, null, mapper);
	}

	@Override
	public <T> T get(String tableName, String rowName, String familyName, final RowMapper<T> mapper) {
		return get(tableName, rowName, familyName, null, mapper);
	}

	@Override
	public <T> T get(String tableName, final String rowName, final String familyName, final String qualifier, final RowMapper<T> mapper) {
		return execute(tableName, new TableCallback<T>() {
			@Override
			public T doInTable(Table htable) throws Throwable {
				Get get = new Get(rowName.getBytes(getCharset()));
				if (familyName != null) {
					byte[] family = familyName.getBytes(getCharset());

					if (qualifier != null) {
						get.addColumn(family, qualifier.getBytes(getCharset()));
					}
					else {
						get.addFamily(family);
					}
				}
				Result result = htable.get(get);
				return mapper.mapRow(result, 0);
			}
		});
	}

	@Override
	public void put(String tableName, final String rowName, final String familyName, final String qualifier, final byte[] value) {
		Assert.hasLength(rowName);
		Assert.hasLength(familyName);
		Assert.hasLength(qualifier);
		Assert.notNull(value);
		execute(tableName, new TableCallback<Object>() {
			@Override
			public Object doInTable(Table htable) throws Throwable {
				Put put = new Put(rowName.getBytes(getCharset())).addColumn(familyName.getBytes(getCharset()), qualifier.getBytes(getCharset()), value);
				htable.put(put);
				return null;
			}
		});
	}

	@Override
	public void delete(String tableName, final String rowName, final String familyName) {
		delete(tableName, rowName, familyName, null);
	}
	
	@Override
	public void delete(String tableName, final String rowName, final String familyName, final String qualifier) {
		Assert.hasLength(rowName);
		Assert.hasLength(familyName);
		execute(tableName, new TableCallback<Object>() {
			@Override
			public Object doInTable(Table htable) throws Throwable {
				Delete delete = new Delete(rowName.getBytes(getCharset()));
				byte[] family = familyName.getBytes(getCharset());

				if (qualifier != null) {
					delete.addColumn(family, qualifier.getBytes(getCharset()));
				}
				else {
					delete.addFamily(family);
				}
				
				htable.delete(delete);
				return null;
			}
		});
	}

	@Override
	public <T> T execute_batch(String tableName, MutatorCallback<T> action) {
		// TODO Auto-generated method stub
		Assert.notNull(action, "Callback object must not be null");
		Assert.notNull(tableName, "No table specified");

		//Table table = getTable(tableName);
		TableName tName = TableName.valueOf(tableName);
		BufferedMutatorParams params = new BufferedMutatorParams(tName);
		params.writeBufferSize(put_batch_buffer_size * 1024 * 1024); // 可以自己设定阈值 5M 达到5M则提交一次

		BufferedMutator mutator=null;
		
		try {
			mutator= getTableConn().getBufferedMutator(params);
			//boolean previousFlushSetting = applyFlushSetting(table);
			T result = action.doInTable(mutator);
			//flushIfNecessary(table, previousFlushSetting);
			return result;
		} catch (Throwable th) {
			if (th instanceof Error) {
				throw ((Error) th);
			}
			if (th instanceof RuntimeException) {
				throw ((RuntimeException) th);
			}
			throw convertHbaseAccessException((Exception) th);
		} finally {
			releaseMutator(tableName, mutator);
		}
	}

	@Override
	public void put_batch(String tableName,List<Put> puts) {
		// TODO Auto-generated method stub
		execute_batch(tableName, new MutatorCallback<Object>() {
			@Override
			public Object doInTable(BufferedMutator mutator) throws Throwable {
				
				mutator.mutate(puts);
				return null;
			}
		});
	}



	/**
	 * Sets the auto flush.
	 *
	 * @param autoFlush The autoFlush to set.
	 */
	/*
	 * public void setAutoFlush(boolean autoFlush) { this.autoFlush = autoFlush; }
	 */
}