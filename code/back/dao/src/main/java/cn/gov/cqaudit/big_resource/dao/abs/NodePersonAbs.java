package cn.gov.cqaudit.big_resource.dao.abs;

import org.apache.hadoop.hbase.client.Connection;

import cn.gov.cqaudit.big_resource.dao.common.abs.HbaseTableOperationAbs;

import cn.gov.cqaudit.big_resource.entity.NodePerson;

public abstract class NodePersonAbs extends HbaseTableOperationAbs<NodePerson>{

	/**
	 * 验证身份证是否有效
	 * @param id
	 * 
	 */
	public boolean isValidIdNo(String id) {
		if (id==null) {
			return false;
		}
		return  cn.gov.cqaudit.tools.PersonIdCard.isValidIdNo(id);
	}
	public abstract boolean isValidIdNoCreate(Connection conn,NodePerson nodePerson);
}
