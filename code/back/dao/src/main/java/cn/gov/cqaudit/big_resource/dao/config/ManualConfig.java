package cn.gov.cqaudit.big_resource.dao.config;

import org.springframework.stereotype.Component;

@Component
public class ManualConfig {
	//put的缓存数量XMB
	int put_batch_buffer_size;
	//一次取回的列
	int	scan_batch;
	//一次取回的行
	int scan_cache;


	public int getScan_batch() {
		return scan_batch;
	}

	public void setScan_batch(int scan_batch) {
		this.scan_batch = scan_batch;
	}

	public int getScan_cache() {
		return scan_cache;
	}

	public void setScan_cache(int scan_cache) {
		this.scan_cache = scan_cache;
	}

	public int getPut_batch_buffer_size() {
		return put_batch_buffer_size;
	}

	public void setPut_batch_buffer_size(int put_batch_buffer_size) {
		this.put_batch_buffer_size = put_batch_buffer_size;
	}

}
