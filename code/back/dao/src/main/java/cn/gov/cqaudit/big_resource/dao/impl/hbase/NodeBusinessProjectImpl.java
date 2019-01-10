package cn.gov.cqaudit.big_resource.dao.impl.hbase;

import java.text.ParseException;
import java.util.List;

import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import cn.gov.cqaudit.big_resource.dao.abs.NodeBusinessProjectAbs;
import cn.gov.cqaudit.big_resource.entity.NodeBuinessProject;

import cn.gov.cqaudit.tools.DateTools;

public class NodeBusinessProjectImpl extends NodeBusinessProjectAbs{
	private String tableName = "node";

	public static byte[] CF_INFO = Bytes.toBytes("cf1");
	private byte[] name = Bytes.toBytes("name");
	private byte[] type = Bytes.toBytes("type");
	private byte[] seq = Bytes.toBytes("seq");
	private byte[] datetime = Bytes.toBytes("datetime");

	@Override
	public void deleteList(Connection conn, List<Delete> rowKeys) {
		// TODO Auto-generated method stub
		deleteList(conn,tableName,rowKeys);
		
	}

	@Override
	public boolean create(Connection conn, NodeBuinessProject node) {
		// TODO Auto-generated method stub
		return create(conn,tableName,node);
	}

	@Override
	public NodeBuinessProject getByRowKey(Connection conn, String rowKey) {
		// TODO Auto-generated method stub
		return mapRow(getByRowKey(conn, tableName, rowKey), 0);
	}

	@Override
	public List<NodeBuinessProject> getAll(Connection conn) {
		// TODO Auto-generated method stub
		Scan scan = new Scan();

		return getByScan(conn, scan);
	}

	@Override
	public List<NodeBuinessProject> getStartAndEnd(Connection conn, String start_row, String stop_row) {
		// TODO Auto-generated method stub
		Scan scan = new Scan();
		scan.withStartRow(Bytes.toBytes(start_row));
		scan.withStopRow(Bytes.toBytes(stop_row));

		return getByScan(conn, scan);
	}

	@Override
	public List<NodeBuinessProject> getByScan(Connection conn, Scan scan) {
		// TODO Auto-generated method stub
		return getByScan(conn, tableName, scan);
	}

	@Override
	public NodeBuinessProject mapRow(Result result, int rowNum) {
		// TODO Auto-generated method stub
		try {
			return new NodeBuinessProject(Bytes.toString(result.getRow())
					, Bytes.toString(result.getValue(CF_INFO, name))
					, Bytes.toString(result.getValue(CF_INFO, type))
					, Bytes.toString(result.getValue(CF_INFO, seq)),
					DateTools.convertLocalDateTime(Bytes.toString(result.getValue(CF_INFO, datetime)),"yyyy-MM-dd HH:mm:ss.SSS"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
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
	public Put mapPut(NodeBuinessProject object, int rowNum) {
		// TODO Auto-generated method stub
		Put put = new Put(Bytes.toBytes(object.getId()));
        put.addColumn(CF_INFO, name, Bytes.toBytes(object.getName()));
        put.addColumn(CF_INFO, type, Bytes.toBytes(object.getType()));
        put.addColumn(CF_INFO, seq, Bytes.toBytes(object.getSeq()));
        put.addColumn(CF_INFO, datetime, Bytes.toBytes(DateTools.formatLocalDateTime(object.getDatetime(),"yyyy-MM-dd HH:mm:ss.SSS")));
		return put;
	}

}
