package cn.gov.cqaudit.big_resource.gather_info.web.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableJpaRepositories(basePackages = { "cn.gov.cqaudit.big_resource.gather_info.repository" })
@ComponentScan(basePackages = { "cn.gov.cqaudit.big_resource.gather_info" })
@EnableTransactionManagement
public class PersistenceJPAConfig {
	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;
	@Value("${spring.datasource.url}	")
	private String url;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;

	/*	*//**
			 * } dozer 配置 用于动态解析实体bean
			 * 
			 * @return
			 *//*
				 * @Bean(name = "org.dozer.Mapper") public DozerBeanMapper dozer() {
				 * List<String> mappingFiles = new ArrayList<>();
				 * mappingFiles.add("dozer/dozer-mappings-user.xml"); DozerBeanMapper dozerBean
				 * = new DozerBeanMapper(); dozerBean.setMappingFiles(mappingFiles); return
				 * dozerBean; }
				 */
	// 数据源 spring 下的自带jdbc的链接数据源
	@Bean
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	// 获取sessionFactory
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DriverManagerDataSource dataSource,
			JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean localSession = new LocalContainerEntityManagerFactoryBean();
		localSession.setDataSource(dataSource);
		localSession.setJpaVendorAdapter(jpaVendorAdapter);
		localSession.setPackagesToScan("cn.gov.cqaudit.big_resource.gather_info.entity");
		Properties properties = new Properties();
		properties.setProperty("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.format", "true");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		localSession.setJpaProperties(properties);
		return localSession;
	}

	// jpa规范
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(true);
		adapter.setDatabase(Database.POSTGRESQL);
		adapter.setGenerateDdl(false);
		adapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
		return adapter;
	}

	// 事务
	@Bean
	public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryBean.getObject());
		return transactionManager;
	}

	@Bean
	public BeanPostProcessor paPostProcessor() {
		return new PersistenceAnnotationBeanPostProcessor();
	}

	@Bean
	public BeanPostProcessor persistenceTranslation() {
		return new PersistenceAnnotationBeanPostProcessor();
	}
}
