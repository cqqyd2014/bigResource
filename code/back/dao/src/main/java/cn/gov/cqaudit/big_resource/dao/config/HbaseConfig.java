package cn.gov.cqaudit.big_resource.dao.config;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource(value = { "classpath:hbase.properties" })
public class HbaseConfig {
	
	@Autowired
	ManualConfig manualConfig;

	public HbaseConfig() {
		super();
		// TODO Auto-generated constructor stub
		System.out.println("初始化HbaseDAO");
	}

	/*
	 * @Bean(name="hbaseTemplate") public HbaseTemplate
	 * hbaseTemplate(@Value("${hbase.zookeeper.quorum}") String quorum,
	 * 
	 * @Value("${hbase.zookeeper.port}") String port,
	 * 
	 * @Value("${fs.defaultFS}") String fs,
	 * 
	 * @Value("${hbase.zookeeper.znode}") String znode) {
	 * System.out.println("开始初始化template"); HbaseTemplate hbaseTemplate = new
	 * HbaseTemplate(); org.apache.hadoop.conf.Configuration conf =
	 * HBaseConfiguration.create(); conf.set("hbase.zookeeper.quorum", quorum);
	 * conf.set("hbase.zookeeper.port", port); conf.set("hbase.zookeeper.znode",
	 * znode); conf.set("fs.defaultFS", znode);
	 * hbaseTemplate.setConfiguration(conf); hbaseTemplate.setAutoFlush(true);
	 * System.out.println("初始化template结束"); return hbaseTemplate; }
	 */
	@Bean(name = "hConfig")
	public org.apache.hadoop.conf.Configuration hConfig(@Value("${hbase.zookeeper.quorum}") String quorum,
			@Value("${hbase.zookeeper.port}") String port, @Value("${hbase.rootdir}") String fs,
			@Value("${hbase.zookeeper.znode}") String znode) {
		org.apache.hadoop.conf.Configuration HbaseConfig = HBaseConfiguration.create();
		HbaseConfig.set("hbase.zookeeper.quorum", quorum);
		HbaseConfig.set("hbase.zookeeper.port", port);
		HbaseConfig.set("hbase.zookeeper.znode", znode);
		HbaseConfig.set("hbase.rootdir", fs);
		return HbaseConfig;
	}

	@Bean(name = "hConn")
	public Connection conn(@Value("${hbase.batch_pool}") String batch_pool,org.apache.hadoop.conf.Configuration hConfig) {
		// org.apache.hadoop.conf.Configuration HbaseConfig =
		// HBaseConfiguration.create();
		System.out.println("初始化connection");
		Connection connection = null;
		try {
			ExecutorService pool = Executors.newFixedThreadPool(Integer.valueOf(batch_pool));
			connection = ConnectionFactory.createConnection(hConfig,pool);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("创建HBase数据库连接出错");
			e.printStackTrace();
		}
		System.out.println("初始化connection成功");
		return connection;
	}
	@Bean(name = "mConfig")
	public ManualConfig mConfig(@Value("${hbase.put_batch_buffer_size}") String put_batch_buffer_size,
			@Value("${hbase.scan_batch_do_work}") String scan_batch_do_work) {
		manualConfig.setPut_batch_buffer_size(Integer.valueOf(put_batch_buffer_size));
		manualConfig.setScan_batch_do_work(Integer.valueOf(scan_batch_do_work));
		return manualConfig;
	}

}
