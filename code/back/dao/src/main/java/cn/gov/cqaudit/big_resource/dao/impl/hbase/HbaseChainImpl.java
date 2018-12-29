package cn.gov.cqaudit.big_resource.dao.impl.hbase;

import java.text.ParseException;
import java.util.List;

import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import cn.gov.cqaudit.big_resource.dao.common.abs.HbaseTableOperationAbs;
import cn.gov.cqaudit.big_resource.entity.Chain;

import cn.gov.cqaudit.tools.DateTools;

public class HbaseChainImpl extends HbaseTableOperationAbs<Chain>{
	private String tableName = "chain";
	

	public static byte[] CF_INFO = Bytes.toBytes("cf1");
	private byte[] start_node_id = Bytes.toBytes("start_node_id");
	private byte[] end_node_id = Bytes.toBytes("end_node_id");
	private byte[] date_time = Bytes.toBytes("date_time");
	private byte[] type = Bytes.toBytes("type");
	private byte[] amount = Bytes.toBytes("amount");
	private byte[] seq=Bytes.toBytes("seq");
	private byte[] start_end_seq_md5=Bytes.toBytes("start_end_seq_md5");
	

	@Override
	public void deleteList(Connection conn, List<Delete> rowKeys) {
		// TODO Auto-generated method stub
		deleteList(conn,tableName,rowKeys);
	}

	@Override
	public boolean create(Connection conn, Chain node) {
		// TODO Auto-generated method stub
		return create(conn,tableName,node);
	}

	@Override
	public Chain getByRowKey(Connection conn, String rowKey) {
		// TODO Auto-generated method stub
		return mapRow(getByRowKey(conn, tableName, rowKey), 0);
	}

	@Override
	public List<Chain> getAll(Connection conn) {
		// TODO Auto-generated method stub
		Scan scan = new Scan();
		return getByScan(conn, scan);
	}

	@Override
	public List<Chain> getStartAndEnd(Connection conn, String start_row, String stop_row) {
		// TODO Auto-generated method stub
		Scan scan = new Scan();
		scan.withStartRow(Bytes.toBytes(start_row));
		scan.withStopRow(Bytes.toBytes(stop_row));

		return getByScan(conn, scan);
	}

	@Override
	public List<Chain> getByScan(Connection conn, Scan scan) {
		// TODO Auto-generated method stub
		return getByScan(conn, tableName, scan);
	}

	@Override
	public Chain mapRow(Result result, int rowNum) {
		// TODO Auto-generated method stub
		try {
			return new Chain(Bytes.toString(result.getRow())
					, Bytes.toString(result.getValue(CF_INFO, seq))
					, Bytes.toString(result.getValue(CF_INFO, start_node_id))
					, Bytes.toString(result.getValue(CF_INFO, end_node_id))
					, DateTools.convertDate(Bytes.toString(result.getValue(CF_INFO, date_time)),"YYYY-MM-dd HH:mi:ss.SSS")
					, Bytes.toString(result.getValue(CF_INFO, type))
					, Bytes.toFloat(result.getValue(CF_INFO, amount)));
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
		return putManualBatch(conn,tableName,putList,bufferSize);
	}

	@Override
	public Put mapPut(Chain object, int rowNum) {
		Put put = new Put(Bytes.toBytes(object.getStart_end_seq_md5()));
		put.addColumn(CF_INFO, seq, Bytes.toBytes(object.getSeq()));
		put.addColumn(CF_INFO, start_node_id, Bytes.toBytes(object.getStart_node_id()));
		put.addColumn(CF_INFO, end_node_id, Bytes.toBytes(object.getEnd_node_id()));
		put.addColumn(CF_INFO, date_time, Bytes.toBytes(DateTools.convertString(object.getDate_time(),"YYYY-MM-dd HH:mi:ss.SSS")));
		put.addColumn(CF_INFO, type, Bytes.toBytes(object.getType()));
		put.addColumn(CF_INFO, amount, Bytes.toBytes(object.getAmount()));
		return put;
	}

}
