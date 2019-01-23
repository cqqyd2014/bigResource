package cn.gov.cqaudit.big_resource.import_hbase_module.source_template;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class SourceTemplate {
	
	String jdbcUserName;
	String jdbcPassWord;
	public SourceTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}
	String sql;

	public SourceTemplate(String jdbcUserName, String jdbcPassWord, String sql, SourceTemplateTypeEnum type,
			String cvsFileName, String jdbcDriver, String jdbcUrl) {
		super();
		this.jdbcUserName = jdbcUserName;
		this.jdbcPassWord = jdbcPassWord;
		this.sql = sql;
		this.type = type;
		this.cvsFileName = cvsFileName;
		this.jdbcDriver = jdbcDriver;
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
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	SourceTemplateTypeEnum type;
	String cvsFileName;
	public SourceTemplateTypeEnum getType() {
		return type;
	}
	public void setType(SourceTemplateTypeEnum type) {
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
	String jdbcDriver;
	String jdbcUrl;
}
