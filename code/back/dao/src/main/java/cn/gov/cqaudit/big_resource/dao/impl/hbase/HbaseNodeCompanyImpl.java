package cn.gov.cqaudit.big_resource.dao.impl.hbase;

import java.text.ParseException;
import java.util.List;

import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import cn.gov.cqaudit.big_resource.dao.abs.NodeCompanyAbs;

import cn.gov.cqaudit.big_resource.entity.NodeCompany;
import cn.gov.cqaudit.tools.DateTools;

public class HbaseNodeCompanyImpl extends NodeCompanyAbs{
	private String tableName = "node";

	public static byte[] CF_INFO = Bytes.toBytes("cf1");
	private byte[] name = Bytes.toBytes("name");
	private byte[] type = Bytes.toBytes("type");
	private byte[] address = Bytes.toBytes("address");
	private byte[] law_man = Bytes.toBytes("law_man");
	private byte[] capital = Bytes.toBytes("capital");
	private byte[] start_date = Bytes.toBytes("start_date");
	private byte[] end_date = Bytes.toBytes("end_date");
	private byte[] forever = Bytes.toBytes("forever");
	private byte[] business_scope = Bytes.toBytes("business_scope");
	private byte[] register = Bytes.toBytes("register");
	

	@Override
	public void deleteList(Connection conn, List<Delete> rowKeys) {
		// TODO Auto-generated method stub
		deleteList(conn,tableName,rowKeys);
		
	}

	@Override
	public boolean create(Connection conn, NodeCompany node) {
		// TODO Auto-generated method stub
		return create(conn,tableName,node);
		
	}

	@Override
	public NodeCompany getByRowKey(Connection conn, String rowKey) {
		// TODO Auto-generated method stub
		return mapRow(getByRowKey(conn, tableName, rowKey), 0);
	}

	@Override
	public List<NodeCompany> getAll(Connection conn) {
		// TODO Auto-generated method stub
		Scan scan = new Scan();

		return getByScan(conn, scan);
	}

	@Override
	public List<NodeCompany> getStartAndEnd(Connection conn, String start_row, String stop_row) {
		// TODO Auto-generated method stub
		Scan scan = new Scan();
		scan.withStartRow(Bytes.toBytes(start_row));
		scan.withStopRow(Bytes.toBytes(stop_row));

		return getByScan(conn, scan);
	}

	@Override
	public List<NodeCompany> getByScan(Connection conn, Scan scan) {
		// TODO Auto-generated method stub
		return getByScan(conn, tableName, scan);
	}

	@Override
	public NodeCompany mapRow(Result result, int rowNum) {
		// TODO Auto-generated method stub
		try {
			return new NodeCompany(Bytes.toString(result.getRow())
					, Bytes.toString(result.getValue(CF_INFO, name))
					, Bytes.toString(result.getValue(CF_INFO, type))
					, Bytes.toString(result.getValue(CF_INFO, address))
					, Bytes.toString(result.getValue(CF_INFO, law_man))
					, Bytes.toFloat(result.getValue(CF_INFO, capital))
					, DateTools.convertDate(Bytes.toString(result.getValue(CF_INFO, start_date)),"yyyy-MM-dd")
					, DateTools.convertDate(Bytes.toString(result.getValue(CF_INFO, end_date)),"yyyy-MM-dd")
					, Bytes.toBoolean(result.getValue(CF_INFO, forever))
					, Bytes.toString(result.getValue(CF_INFO, business_scope))
					, Bytes.toString(result.getValue(CF_INFO, register)));
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
	public Put mapPut(NodeCompany object, int rowNum) {
		// TODO Auto-generated method stub
		Put put = new Put(Bytes.toBytes(object.getId()));
		put.addColumn(CF_INFO, name, Bytes.toBytes(object.getName()));
		put.addColumn(CF_INFO, type, Bytes.toBytes(object.getType()));
		put.addColumn(CF_INFO, address, Bytes.toBytes(object.getAddress()));
		put.addColumn(CF_INFO, law_man, Bytes.toBytes(object.getLaw_man()));
		put.addColumn(CF_INFO, address, Bytes.toBytes(object.getAddress()));
		put.addColumn(CF_INFO, capital, Bytes.toBytes(object.getCapital()));
		put.addColumn(CF_INFO, start_date, Bytes.toBytes(DateTools.convertString(object.getStart_date(),"yyyy-mm-dd")));
		put.addColumn(CF_INFO, end_date, Bytes.toBytes(DateTools.convertString(object.getEnd_date(),"yyyy-mm-dd")));
		put.addColumn(CF_INFO, forever, Bytes.toBytes(object.isForever()));
		put.addColumn(CF_INFO, business_scope, Bytes.toBytes(object.getBusiness_scope()));
		put.addColumn(CF_INFO, register, Bytes.toBytes(object.getRegister()));
		
		
		
		return put;
	}

}
