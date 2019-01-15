package cn.gov.cqaudit.big_resource.hive_module.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.hadoop.hive.HiveClientFactory;
import org.springframework.data.hadoop.hive.HiveClientFactoryBean;
import org.springframework.data.hadoop.hive.HiveRunner;

@Configuration
@PropertySource(value = { "classpath:hive.properties" })
public class HiveConfig {

	public HiveConfig() {
		super();
		// TODO Auto-generated constructor stub
		System.out.println("初始化Hive Config");
	}
	@Bean(name = "hiveDataSource")
	public org.springframework.jdbc.datasource.SimpleDriverDataSource getDataSource(@Value("${hive.url}") String hive_url) {
		org.springframework.jdbc.datasource.SimpleDriverDataSource dataSource = new org.springframework.jdbc.datasource.SimpleDriverDataSource(new org.apache.hive.jdbc.HiveDriver(),hive_url);

		dataSource.setDriver(new org.apache.hive.jdbc.HiveDriver());
		dataSource.setUrl(hive_url);

		
		return dataSource;
	}
	@Bean(name = "hiveClientFactory")
	public HiveClientFactory getHiveClientFactory(org.springframework.jdbc.datasource.SimpleDriverDataSource hiveDataSource) {
		HiveClientFactoryBean hiveClientFactory = new HiveClientFactoryBean();
		hiveClientFactory.setHiveDataSource(hiveDataSource);

		return hiveClientFactory.getObject();
	}
	

	@Bean(name = "hiveRunner")
	public HiveRunner getHiveRunner(HiveClientFactory hiveClientFactory) {
		HiveRunner hiveRunner = new HiveRunner();
		hiveRunner.setHiveClientFactory(hiveClientFactory); 

		return hiveRunner;
	}
	

}
