package cn.gov.cqaudit.big_resource.dao.config;

import org.springframework.stereotype.Component;

@Component
public class ManualConfig {
	int put_batch_buffer_size;
	int	scan_batch_do_work;

	public int getScan_batch_do_work() {
		return scan_batch_do_work;
	}

	public void setScan_batch_do_work(int scan_batch_do_work) {
		this.scan_batch_do_work = scan_batch_do_work;
	}

	public int getPut_batch_buffer_size() {
		return put_batch_buffer_size;
	}

	public void setPut_batch_buffer_size(int put_batch_buffer_size) {
		this.put_batch_buffer_size = put_batch_buffer_size;
	}

}
