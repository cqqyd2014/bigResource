package cn.gov.cqaudit.big_resource.dao.impl.hbase;


import java.util.List;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Delete;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;

import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;


import org.springframework.stereotype.Repository;

import cn.gov.cqaudit.big_resource.dao.common.abs.HbaseTableOperationAbs;

import cn.gov.cqaudit.big_resource.entity.Node;

@Repository
public class HbaseNodeImpl extends HbaseTableOperationAbs<Node> {

	private String tableName = "node";

	public static byte[] CF_INFO = Bytes.toBytes("cf1");
	private byte[] name = Bytes.toBytes("name");

	@Override
	public boolean create(Connection conn, Node node) {
		// writing to 'MyTable'
		return create(conn,tableName,node);

	}

	@Override
	public Node getByRowKey(Connection conn, String rowKey) {

		return mapRow(getByRowKey(conn, tableName, rowKey), 0);

	}

	@Override
	public List<Node> getAll(Connection conn) {
		Scan scan = new Scan();

		return getByScan(conn, scan);
	}

	@Override
	public List<Node> getStartAndEnd(Connection conn, String start_row, String stop_row) {

		Scan scan = new Scan();
		scan.withStartRow(Bytes.toBytes(start_row));
		scan.withStopRow(Bytes.toBytes(stop_row));

		return getByScan(conn, scan);

	}

	@Override
	public Node mapRow(Result result, int rowNum) {
		// TODO Auto-generated method stub
		return new Node(Bytes.toString(result.getRow()), Bytes.toString(result.getValue(CF_INFO, name)));
	}

	@Override
	public List<Node> getByScan(Connection conn, Scan scan) {

		return getByScan(conn, tableName, scan);
	}

	@Override
	public void deleteList(Connection conn, List<Delete> rowKeys) {
		deleteList(conn,tableName,rowKeys);

	}

	@Override
	public boolean putAutoBatch(Connection conn, List<Put> putList) {
		// TODO Auto-generated method stub
		return putAutoBatch(conn,tableName,putList);
	}

	@Override
	public boolean putManualBatch(Connection conn, List<Put> putList, int bufferSize) {
		// TODO Auto-generated method stub
		return putManualBatch(conn,tableName,putList,bufferSize);
	}

	@Override
	public Put mapPut(Node object, int rowNum) {
		// TODO Auto-generated method stub
		Put put = new Put(Bytes.toBytes(object.getId()));
		put.addColumn(CF_INFO, name, Bytes.toBytes(object.getName()));
		return put;
	}

}
