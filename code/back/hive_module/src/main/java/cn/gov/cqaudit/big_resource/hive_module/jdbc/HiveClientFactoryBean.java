package cn.gov.cqaudit.big_resource.hive_module.jdbc;

import java.sql.Connection;
import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;


import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.util.Assert;


	public class HiveClientFactoryBean implements FactoryBean<HiveClientFactory>, InitializingBean, DisposableBean {

		private Collection<HiveScript> scripts;

		private DataSource hiveDataSource;

		private SingleConnectionDataSource factoryDataSource;

		@Override
		public void afterPropertiesSet() throws Exception {
			Assert.notNull(hiveDataSource, "HiveDataSource must be set");
			Connection con = DataSourceUtils.getConnection(hiveDataSource);
			factoryDataSource = new SingleConnectionDataSource(con, true);
		}

		@Override
		public void destroy() throws Exception {
			factoryDataSource.destroy();
		}

		private class DefaultHiveClientFactory implements HiveClientFactory {
			@Override
			public HiveClient getHiveClient() throws BeansException {
				try {
					return createHiveClient();
				} catch (Exception ex) {
					throw new BeanCreationException("Cannot create HiveClient instance", ex);
				}
			}
		}

		public HiveClientFactory getObject() {
			return new DefaultHiveClientFactory();
		}

		public Class<?> getObjectType() {
			return HiveClientFactory.class;
		}

		public boolean isSingleton() {
			return true;
		}

		protected HiveClient createHiveClient() {
			if (factoryDataSource == null) {
				throw new IllegalStateException("HiveDataSource must be set before requesting a HiveClient");
			}
			return new HiveClient(factoryDataSource);
		}

		public int getPhase() {
			return Integer.MIN_VALUE;
		}


		/**
		 * Sets the DataSource.
		 * 
		 * @param dataSource The DataSource.
		 */
		public void setHiveDataSource(DataSource dataSource) {
			this.hiveDataSource = dataSource;
		}

		/**
		 * Sets the scripts to execute once the client connects.
		 * 
		 * @param scripts The scripts to set.
		 */
		public void setScripts(Collection<HiveScript> scripts) {
			this.scripts = scripts;
		}
	}

