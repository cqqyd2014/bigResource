package cn.gov.cqaudit.big_resource.hive_module.jdbc;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.dao.support.DataAccessUtils;



import cn.gov.cqaudit.big_resource.import_hbase_module.import_template.*;


import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

public class HiveTemplate implements InitializingBean, HiveOperations, ResourceLoaderAware {

	private HiveClientFactory hiveClientFactory;
	private ResourceLoader resourceLoader;
	private static final Log log = LogFactory.getLog(HiveTemplate.class);
	
	/*
	 * 根据targetTemplate创建从hive到hbase的表
	 * 默认表名是相同的
	 * CREATE TABLE hbase_table_1(key int, value1 string, value2 int, value3 int) 
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES (
"hbase.columns.mapping" = ":key,a:b,a:c,d:e"
);
	 * 
	 */
	public void createHiveTableIntegrationHBase(TargetTemplate targetTemplate) {
		String tableName=targetTemplate.getTableName();
		
		String sql="create table "+tableName+"("+targetTemplate.getRowKey()+" string";
		java.util.List<TargetTemplateDetail> details=targetTemplate.getDetails();
		for (TargetTemplateDetail detail:details) {
			sql=sql+","+detail.getColumn()+" ";
			switch (detail.getType()){
				case STRING:
					sql=sql+"string";
					break;
				case DATE:
					sql=sql+"date";
					break;
				case DATETIME:
					sql=sql+"timestamp";
					break;
				case NUMBER:
					sql=sql+"double";
			}
		}
		sql=sql+") STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'" + 
				"WITH SERDEPROPERTIES (\"hbase.columns.mapping\" = \":key";
		for (TargetTemplateDetail detail:details) {
			sql=sql+",cf1:"+detail.getColumn();
		}
		sql=sql+"\");";
		
		query(sql);
	}
	/*
	 * 外部表关联到hbase
	 */
	public void createHiveOuterTableIntegrationHBase(TargetTemplate targetTemplate) {
		String tableName=targetTemplate.getTableName();
		
		String sql="create EXTERNAL table "+tableName+"("+targetTemplate.getRowKey()+" string";
		java.util.List<TargetTemplateDetail> details=targetTemplate.getDetails();
		for (TargetTemplateDetail detail:details) {
			sql=sql+","+detail.getColumn()+" ";
			switch (detail.getType()){
				case STRING:
					sql=sql+"string";
					break;
				case DATE:
					sql=sql+"date";
					break;
				case DATETIME:
					sql=sql+"timestamp";
					break;
				case NUMBER:
					sql=sql+"double";
			}
		}
		sql=sql+") STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'" + 
				"WITH SERDEPROPERTIES (\"hbase.columns.mapping\" = \":key";
		for (TargetTemplateDetail detail:details) {
			sql=sql+",cf1:"+detail.getColumn();
		}
		sql=sql+"\");";
		
		query(sql);
	}

	/*
	 * 删除表
	 */
	
	public void deleteTable(String tableName) {
		
		update("DROP TABLE "+tableName);
	}
	/*
	 * 检查表是否存在
	 * 
	 */
	public boolean tableExists(String tableName) {
		return query("SHOW TABLES \'"+tableName+"\'").size()>0;
	}

	/**
	 * Constructs a new <code>HiveClient</code> instance.
	 * Expects {@link #setHiveClientFactory(HiveClientFactory)} to be called before using it.
	 */
	public HiveTemplate() {
	}

	/**
	 * Constructs a new <code>HiveTemplate</code> instance.
	 *
	 * @param hiveClientFactory HiveClient factory
	 */
	public HiveTemplate(HiveClientFactory hiveClientFactory) {
		this.hiveClientFactory = hiveClientFactory;
		afterPropertiesSet();
	}

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(hiveClientFactory, "non-null hive client factory required");
	}

	/**
	 * Executes the action specified by the given callback object within an active connection.
	 * 
	 * @param action callback object that specifies the Hive action
	 * @return the action result object
	 * @throws DataAccessException exception
	 */
	@Override
	public <T> T execute(HiveClientCallback<T> action) throws DataAccessException {
		Assert.notNull(action, "a valid callback is required");
		HiveClient hiveClient = createHiveClient();
		try {
			return action.doInHive(hiveClient);
		} catch (Exception ex) {
			throw convertHiveAccessException(ex);
		} finally {
			try {
				hiveClient.shutdown();
			} catch (Exception ex) {
				throw new InvalidDataAccessResourceUsageException("Error while closing client connection", ex);
			}
		}
	}

	/**
	 * Converts the given Hive exception to an appropriate exception from the <tt>org.springframework.dao</tt> hierarchy.
	 * 
	 * @param ex hive exception
	 * @return a corresponding DataAccessException
	 */
	protected DataAccessException convertHiveAccessException(Exception ex) {
		return HiveUtils.convert(ex);
	}

	/**
	 * Executes the given HiveQL that results in a list of objects.
	 * The script is interpreted as a URL or if that fails, as a HiveQL statement.
	 *  
	 * @param query HiveQL
	 * @return list of values returned by the query
	 * @throws DataAccessException exception
	 */
	@Override
	public List<String> query(String query) throws DataAccessException {
		
		return query(query, null);
	}

	/**
	 * Executes the given HiveQL using the list of arguments, expecting a list of objects.
	 * The script is interpreted as a URL or if that fails, as a HiveQL statement.
	 * 
	 * @param query HiveQL
	 * @param arguments query arguments
	 * @return list of values returned by the query
	 * @throws DataAccessException exception
	 */
	@Override
	public List<String> query(String query, Map<?, ?> arguments) throws DataAccessException {
		Assert.hasText(query, "a script is required");

		Resource res = null;

		if (ResourceUtils.isUrl(query)) {
			if (resourceLoader != null) {
				res = resourceLoader.getResource(query);
			}
		}
		else {
			res = new ByteArrayResource(query.getBytes());
		}

		return executeScript(new HiveScript(res, arguments));
	}

	/**
	 * Executes the given HiveQL that results in a single object.
	 * The script is interpreted as a URL or if that fails, as a HiveQL statement.
	 * 
	 * @param query HiveQL
	 * @return query result
	 * @throws DataAccessException exception
	 */
	@Override
	public String queryForString(String query) throws DataAccessException {
		return queryForString(query, null);
	}

	/**
	 * Executes the given HiveQL using the list of arguments, that results in a single object.
	 * The script is interpreted as a URL or if that fails, as a HiveQL statement.
	 * 
	 * @param query HiveQL
	 * @param arguments query arguments
	 * @return query result
	 * @throws DataAccessException exception
	 */
	@Override
	public String queryForString(String query, Map<?, ?> arguments) throws DataAccessException {
		return DataAccessUtils.singleResult(query(query, arguments));
	}

	/**
	 * Executes the given HiveQL that results in a single int value.
	 * The script is interpreted as a URL or if that fails, as a HiveQL statement.
	 * 
	 * @param query HiveQL
	 * @return query int result
	 * @throws DataAccessException exception
	 */
	@Override
	public Integer queryForInt(String query) throws DataAccessException {
		return queryForInt(query, null);
	}

	/**
	 * Executes the given HiveQL using the list of arguments, that results in a single int value.
	 * The script is interpreted as a URL or if that fails, as a HiveQL statement.
	 * 
	 * @param query HiveQL
	 * @param arguments query arguments
	 * @return query int result
	 * @throws DataAccessException exception
	 */
	@Override
	public Integer queryForInt(String query, Map<?, ?> arguments) throws DataAccessException {
		String result = queryForString(query, arguments);
		if (result != null) {
			try {
				return Integer.valueOf(result);
			} catch (NumberFormatException ex) {
				throw new TypeMismatchDataAccessException("Invalid int result found [" + result + "]", ex);
			}
		}
		return null;
	}

	/**
	 * Executes the given HiveQL that results in a single long value.
	 * The script is interpreted as a URL or if that fails, as a HiveQL statement.
	 * 
	 * @param query HiveQL
	 * @return query long result
	 * @throws DataAccessException exception
	 */
	@Override
	public Long queryForLong(String query) throws DataAccessException {
		return queryForLong(query, null);
	}

	/**
	 * Executes the given HiveQL using the list of arguments, that results in a single long value.
	 * The script is interpreted as a URL or if that fails, as a HiveQL statement.
	 * 
	 * @param query HiveQL
	 * @param arguments query arguments
	 * @return query long result
	 * @throws DataAccessException exception
	 */
	@Override
	public Long queryForLong(String query, Map<?, ?> arguments) throws DataAccessException {
		String result = queryForString(query, arguments);
		if (result != null) {
			try {
				return Long.valueOf(result);
			} catch (NumberFormatException ex) {
				throw new TypeMismatchDataAccessException("Invalid long result found [" + result + "]", ex);
			}
		}
		return null;
	}


	/**
	 * Executes a Hive script.
	 * 
	 * @param script script resource and arguments
	 * @return script result
	 * @throws DataAccessException exception
	 */
	@Override
	public List<String> executeScript(HiveScript script) throws DataAccessException {
		return executeScript(Collections.singleton(script));
	}

	/**
	 * Executes multiple Hive scripts.
	 * 
	 * @param scripts scripts resources and arguments
	 * @return scripts results
	 * @throws DataAccessException exception
	 */
	@Override
	public List<String> executeScript(final Iterable<HiveScript> scripts) throws DataAccessException {
		return execute(new HiveClientCallback<List<String>>() {
			@Override
			public List<String> doInHive(HiveClient hiveClient) throws Exception {
				return HiveUtils.run(hiveClient, scripts);
			}
		});
	}

	protected HiveClient createHiveClient() {
		return hiveClientFactory.getHiveClient();
	}

	/**
	 * Sets the {@link HiveClient} factory.
	 * 
	 * @param hiveClientFactory hive client factory to set
	 */
	public void setHiveClientFactory(HiveClientFactory hiveClientFactory) {
		this.hiveClientFactory = hiveClientFactory;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@Override
	public void update(String update) throws DataAccessException {
		// TODO Auto-generated method stub
		HiveClient hiveClient=createHiveClient();
		try {
			hiveClient.update(update);
		}
	finally {
		try {
			hiveClient.shutdown();
		} catch (Exception ex) {
			throw new InvalidDataAccessResourceUsageException("Error while closing client connection", ex);
		}
	}
	}
}