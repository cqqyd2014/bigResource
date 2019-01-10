package cn.gov.cqaudit.big_resource.entity;

import java.time.LocalDateTime;

import cn.gov.cqaudit.tools.DateTools;
import cn.gov.cqaudit.tools.StringTools;

public class Chain {
	
	/**
	 * 用于维护关系的基础类，已关系的startid、datetime(非md5),endid、seq各自的md5为rowKey
	 * 
	 * 
	 */
	String start_end_seq_md5;
	String seq;//交易的原始流水号，应该为唯一序号
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}

	public Chain(String start_end_seq_md5, String seq, String start_node_id, String end_node_id, LocalDateTime date_time,
			String type, float amount) {
		super();
		this.start_end_seq_md5 = start_end_seq_md5;
		this.seq = seq;
		this.start_node_id = start_node_id;
		this.end_node_id = end_node_id;
		this.date_time = date_time;
		this.type = type;
		this.amount = amount;
	}
	public Chain(String seq, String start_node_id, String end_node_id, LocalDateTime date_time,
			String type, float amount) {
		super();
		this.start_end_seq_md5 = StringTools.MD5EncodeUTF8(start_node_id)
				+DateTools.formatLocalDateTime(date_time,"yyyy-MM-dd HH:mm:ss.SSS")
				+StringTools.MD5EncodeUTF8(end_node_id)+StringTools.MD5EncodeUTF8(seq);
		this.seq = seq;
		this.start_node_id = start_node_id;
		this.end_node_id = end_node_id;
		this.date_time = date_time;
		this.type = type;
		this.amount = amount;
	}
	public String getStart_end_seq_md5() {
		return start_end_seq_md5;
	}
	public void setStart_end_seq_md5(String start_end_seq_md5) {
		this.start_end_seq_md5 = start_end_seq_md5;
	}

	String start_node_id;
	String end_node_id;
	LocalDateTime date_time;
	public LocalDateTime getDate_time() {
		return date_time;
	}
	public void setDate_time(LocalDateTime date_time) {
		this.date_time = date_time;
	}
	String type;
	float amount;
	public String getStart_node_id() {
		return start_node_id;
	}

	public void setStart_node_id(String start_node_id) {
		this.start_node_id = start_node_id;
	}
	public String getEnd_node_id() {
		return end_node_id;
	}
	public void setEnd_node_id(String end_node_id) {
		this.end_node_id = end_node_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}

}
