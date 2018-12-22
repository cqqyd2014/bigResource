package cn.gov.cqaudit.big_resource.entity;

import java.io.Serializable;

import lombok.Data;



public class Node implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String	id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	String	name;




}
