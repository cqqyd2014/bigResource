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

import cn.gov.cqaudit.big_resource.dao.abs.NodePersonAbs;




import cn.gov.cqaudit.big_resource.entity.NodePerson;

@Repository
public class HbaseNodePersonImpl extends NodePersonAbs {

	private String tableName = "node";

	public static byte[] CF_INFO = Bytes.toBytes("cf1");
	private byte[] name = Bytes.toBytes("name");
	private byte[] sex = Bytes.toBytes("sex");
	private byte[] mobile_phone = Bytes.toBytes("mobile_phone");
	private byte[] year_in_come = Bytes.toBytes("year_in_come");






	@Override
	public boolean create(Connection conn, NodePerson nodePerson) {
		// TODO Auto-generated method stub
		// writing to 'MyTable'
				return create(conn,tableName,nodePerson);
	}



	@Override
	public NodePerson getByRowKey(Connection conn, String rowKey) {
		return mapRow(getByRowKey(conn, tableName, rowKey), 0);
	}

	@Override
	public List<NodePerson> getAll(Connection conn) {
		// TODO Auto-generated method stub
		Scan scan = new Scan();

		return getByScan(conn, scan);
	}

	@Override
	public List<NodePerson> getStartAndEnd(Connection conn, String start_row, String stop_row) {
		// TODO Auto-generated method stub
		Scan scan = new Scan();
		scan.withStartRow(Bytes.toBytes(start_row));
		scan.withStopRow(Bytes.toBytes(stop_row));

		return getByScan(conn, scan);
	}

	@Override
	public List<NodePerson> getByScan(Connection conn, Scan scan) {
		// TODO Auto-generated method stub
		return getByScan(conn, tableName, scan);
	}

	@Override
	public NodePerson mapRow(Result result, int rowNum) {
		// TODO Auto-generated method stub
		return new NodePerson(Bytes.toString(result.getRow())
				, Bytes.toString(result.getValue(CF_INFO, name))
				, Bytes.toBoolean(result.getValue(CF_INFO, sex))
				, Bytes.toString(result.getValue(CF_INFO, mobile_phone)),
				Bytes.toFloat(result.getValue(CF_INFO, year_in_come)));
	}



	@Override
	public void deleteList(Connection conn, List<Delete> rowKeys) {
		// TODO Auto-generated method stub
		deleteList(conn,tableName,rowKeys);
		
	}



	@Override
	public boolean isValidIdNoCreate(Connection conn, NodePerson nodePerson) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean putAutoBatch(Connection conn, List<Put> putList) {
		// TODO Auto-generated method stub
		return putAutoBatch(conn,tableName,putList);
	}



	@Override
	public boolean putManualBatch(Connection conn, List<Put> putList, int bufferSize) {
		// TODO Auto-generated method stub
		return putManualBatch(conn,putList,bufferSize);
	}



	@Override
	public Put mapPut(NodePerson object, int rowNum) {
		// TODO Auto-generated method stub
		Put put = new Put(Bytes.toBytes(object.getId()));
        put.addColumn(CF_INFO, name, Bytes.toBytes(object.getName()));
        put.addColumn(CF_INFO, mobile_phone, Bytes.toBytes(object.getMobile_phone()));
        put.addColumn(CF_INFO, sex, Bytes.toBytes(object.isSex()));
        put.addColumn(CF_INFO, year_in_come, Bytes.toBytes(object.getYear_in_come()));
		return put;
	}

}
