package cn.gov.cqaudit.big_resource.hbase_module.jdbc;

import java.nio.charset.Charset;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
public abstract class HbaseAccessor implements InitializingBean {

	private String encoding;
	private Charset charset = HbaseUtils.getCharset(encoding);

	private Connection tableConn;
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	private Admin	admin;
	//private Configuration configuration;

	public Connection getTableConn() {
		return tableConn;
	}

	public void setTableConn(Connection tableConn) {
		this.tableConn = tableConn;
	}

	@Override
	public void afterPropertiesSet() {
		//Assert.notNull(configuration, " a valid configuration is required");
		// detect charset
		charset = HbaseUtils.getCharset(encoding);
	}

	/**
	 * Sets the table factory.
	 *
	 * @param tableFactory The tableFactory to set.
	 */


	/**
	 * Sets the encoding.
	 *
	 * @param encoding The encoding to set.
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * Sets the configuration.
	 *
	 * @param configuration The configuration to set.
	 */
	/*
	 * public void setConfiguration(Configuration configuration) {
	 * this.configuration = configuration; }
	 */

	public Charset getCharset() {
		return charset;
	}



	/*
	 * public Configuration getConfiguration() { return configuration; }
	 */
}