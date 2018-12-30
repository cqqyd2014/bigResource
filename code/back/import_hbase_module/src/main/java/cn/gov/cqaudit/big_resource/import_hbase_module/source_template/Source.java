package cn.gov.cqaudit.big_resource.import_hbase_module.source_template;

public class Source {

	SourceTypeEnum type;
	String cvsFileName;
	String jdbcDriver;
	String jdbcUrl;
	public SourceTypeEnum getType() {
		return type;
	}
	public void setType(SourceTypeEnum type) {
		this.type = type;
	}


	public String getCvsFileName() {
		return cvsFileName;
	}
	public void setCvsFileName(String cvsFileName) {
		this.cvsFileName = cvsFileName;
	}
	public String getJdbcDriver() {
		return jdbcDriver;
	}
	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}
	public String getJdbcUrl() {
		return jdbcUrl;
	}
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
	public String getJdbcUserName() {
		return jdbcUserName;
	}
	public void setJdbcUserName(String jdbcUserName) {
		this.jdbcUserName = jdbcUserName;
	}
	public String getJdbcPassWord() {
		return jdbcPassWord;
	}
	public void setJdbcPassWord(String jdbcPassWord) {
		this.jdbcPassWord = jdbcPassWord;
	}

	String jdbcUserName;
	String jdbcPassWord;
}
