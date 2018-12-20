package cn.gov.cqaudit.big_resource.dao.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gov.cqaudit.big_resource.dao.inter.NodeInter;
import cn.gov.cqaudit.big_resource.entity.Node;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

@Repository
public class NodeImpl implements NodeInter {

	@Autowired
	private HbaseTemplate hbaseTemplate;

	@Override
	public void add(Node node) {
		// TODO Auto-generated method stub
		return hbaseTemplate.execute(tableName, new TableCallback<User>() {
			public User doInTable(Table table) throws Throwable {
				User user = new User(userName, email, password);
				Put p = new Put(Bytes.toBytes(user.getName()));
				p.add(CF_INFO, qUser, Bytes.toBytes(user.getName()));
				p.add(CF_INFO, qEmail, Bytes.toBytes(user.getEmail()));
				p.add(CF_INFO, qPassword, Bytes.toBytes(user.getPassword()));
				table.put(p);
				return user;
				
			}
		});

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
