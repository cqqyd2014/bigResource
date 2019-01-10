package cn.gov.cqaudit.big_resource.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import cn.gov.cqaudit.tools.DateTools;
import cn.gov.cqaudit.tools.StringTools;

public class NodeBuinessProject extends Node{

	/**
	 * 用于业务和项目管理，比如电影公司的电影等，这个类的rowKey首先是业务分类++datetime+seq的md5
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NodeBuinessProject(String id, String name) {
		super(id, name);
		// TODO Auto-generated constructor stub
	}
	String type;//业务类型，比如农商行贷款，重钢采购铁矿石，招标公司招标项目等
	String seq;//项目编号，销售合同编号，贷款合同号等
	LocalDateTime datetime;//项目开始时间

	public String getType() {
		return type;
	}
	public NodeBuinessProject(String id, String name, String type, String seq, LocalDateTime datetime) {
		super(id, name);
		this.type = type;
		this.seq = seq;
		this.datetime = datetime;
	}
	public NodeBuinessProject(String name, String type, String seq, LocalDateTime datetime) {
		super(StringTools.MD5EncodeUTF8(type)
				+DateTools.formatLocalDateTime(datetime,"yyyy-MM-dd HH:mm:ss.SSS")
				+StringTools.MD5EncodeUTF8(seq), name);
		this.type = type;
		this.seq = seq;
		this.datetime = datetime;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public LocalDateTime getDatetime() {
		return datetime;
	}
	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}
	
	
	

}
