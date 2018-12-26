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
	public Node(String id, String name) {
		super();
		this.id = id;
		this.name = name;
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
