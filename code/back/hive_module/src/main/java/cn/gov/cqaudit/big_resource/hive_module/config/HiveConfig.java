package cn.gov.cqaudit.big_resource.hive_module.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import cn.gov.cqaudit.big_resource.hive_module.jdbc.*;
import cn.gov.cqaudit.big_resource.hive_module.jdbc.datasource.*;






@Configuration
@PropertySource(value = { "classpath:hive.properties" })
public class HiveConfig {

	public HiveConfig() {
		super();
		// TODO Auto-generated constructor stub
		System.out.println("初始化Hive Config");
	}
	/*
	@Bean(name = "hive_datasource")
	 
	 public DataSource dataSource(@Value("${hive.url}") String hive_url){
			 DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
			 
			 dataSourceBuilder.driverClassName("org.apache.hive.jdbc.HiveDriver");
			 
			 dataSourceBuilder.url(hive_url);
			 
			 return dataSourceBuilder.build();
	 }
	 @Bean(name = "hiveJdbcTemplate")
	 
	 public JdbcTemplate jdbcTemplate(@Qualifier("hive_datasource") DataSource dataSource){
			 return new JdbcTemplate(dataSource);
	 }
*/
	
	@Bean(name = "hiveDataSource")
	public org.springframework.jdbc.datasource.SimpleDriverDataSource getDataSource(@Value("${hive.url}") String hive_url) {
		org.springframework.jdbc.datasource.SimpleDriverDataSource dataSource = new org.springframework.jdbc.datasource.SimpleDriverDataSource(new org.apache.hive.jdbc.HiveDriver(),hive_url);

		dataSource.setDriver(new org.apache.hive.jdbc.HiveDriver());
		dataSource.setUrl(hive_url);

		return dataSource;
	}
	@Bean(name = "hiveClientFactoryBean")
	public HiveClientFactoryBean getHiveClientFactoryBean(org.springframework.jdbc.datasource.SimpleDriverDataSource hiveDataSource) {
		HiveClientFactoryBean hiveClientFactory = new HiveClientFactoryBean();
		hiveClientFactory.setHiveDataSource(hiveDataSource);
		
		return hiveClientFactory;
	}
	@Bean(name = "hiveClientFactory")
	public HiveClientFactory getHiveClientFactory(HiveClientFactoryBean hiveClientFactoryBean) {
		
		return hiveClientFactoryBean.getObject();
	}


	@Bean(name = "hiveRunner")
	public HiveRunner getHiveRunner(HiveClientFactory hiveClientFactory) {
		HiveRunner hiveRunner = new HiveRunner();
		hiveRunner.setHiveClientFactory(hiveClientFactory);

		return hiveRunner;
	}
	@Bean(name="hiveTemplate")
	public HiveTemplate getHiveTemplate(HiveClientFactory hiveClientFactory) {
		HiveTemplate hiveTemplate=new HiveTemplate();
		hiveTemplate.setHiveClientFactory(hiveClientFactory);
		System.out.println("Template初始化完成");
		return hiveTemplate;
	}
	

}
