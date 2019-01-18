package cn.gov.cqaudit.big_resource.hbase_module.config;


import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sql.DataSource;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import cn.gov.cqaudit.big_resource.hbase_module.jdbc.HbaseTemplate;








@Configuration
@PropertySource(value = { "classpath:hbase.properties" })
public class HBaseConfig {

	public HBaseConfig() {
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

	@Bean(name = "hadoopConfiguration")
	public org.apache.hadoop.conf.Configuration hadoopConfiguration(@Value("${hbase.zookeeper.quorum}") String quorum,
			@Value("${hbase.zookeeper.port}") String port, @Value("${hbase.rootdir}") String fs,
			@Value("${hbase.zookeeper.znode}") String znode) {
		org.apache.hadoop.conf.Configuration HbaseConfig = HBaseConfiguration.create();
		HbaseConfig.set("hbase.zookeeper.quorum", quorum);
		HbaseConfig.set("hbase.zookeeper.port", port);
		HbaseConfig.set("hbase.zookeeper.znode", znode);
		HbaseConfig.set("hbase.rootdir", fs);
		return HbaseConfig;
	}
	

	@Bean(name="hbaseTemplate")
	public HbaseTemplate template(@Value("${hbase.put_batch_buffer_size}") String put_batch_buffer_size,
			@Value("${hbase.scan_cache}") String scan_cache,
			@Value("${hbase.scan_batch}") String scan_batch,Connection hbaseConnection) {
		HbaseTemplate hbaseTemplate=new HbaseTemplate();
		hbaseTemplate.setTableConn(hbaseConnection);
		hbaseTemplate.setPut_batch_buffer_size(Long.parseLong(put_batch_buffer_size));
		return hbaseTemplate;
	}
	

	@Bean(name = "hbaseConnection")
	public Connection conn(@Value("${hbase.batch_pool}") String batch_pool,org.apache.hadoop.conf.Configuration hadoopConfiguration) {
		// org.apache.hadoop.conf.Configuration HbaseConfig =
		// HBaseConfiguration.create();
		System.out.println("初始化connection");
		Connection connection = null;
		try {
			ExecutorService pool = Executors.newFixedThreadPool(Integer.valueOf(batch_pool));
			connection = ConnectionFactory.createConnection(hadoopConfiguration,pool);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("创建HBase数据库连接出错");
			e.printStackTrace();
		}
		System.out.println("初始化connection成功");

		return connection;
	}
	


}
