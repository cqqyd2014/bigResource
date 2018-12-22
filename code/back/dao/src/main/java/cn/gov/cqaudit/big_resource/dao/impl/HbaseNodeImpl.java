package cn.gov.cqaudit.big_resource.dao.impl;

import java.util.ArrayList;

import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

import org.springframework.stereotype.Repository;

import cn.gov.cqaudit.big_resource.dao.inter.NodeInter;
import cn.gov.cqaudit.big_resource.entity.Node;

@Repository
public class HbaseNodeImpl implements NodeInter {
/*	@Autowired
	private HbaseTemplate hbaseTemplate;*/
	@Override
	public void add(Node node) {
/*		// writing to 'MyTable'
		hbaseTemplate.put("node", node.getId(), "cf1", "name", Bytes.toBytes(node.getName()));
		
*/
		
	}

	@Override
	public Node getByKey(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Node> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
