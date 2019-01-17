package cn.gov.cqaudit.big_resource.hbase_module.jdbc;

import org.springframework.dao.UncategorizedDataAccessException;

@SuppressWarnings("serial")
public class HbaseSystemException extends UncategorizedDataAccessException {

	public HbaseSystemException(Exception cause) {
		super(cause.getMessage(), cause);
	}
}
