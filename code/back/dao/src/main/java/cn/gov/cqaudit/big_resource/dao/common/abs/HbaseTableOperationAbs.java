package cn.gov.cqaudit.big_resource.dao.common.abs;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.BufferedMutatorParams;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;

import cn.gov.cqaudit.big_resource.entity.NodePerson;

public abstract class HbaseTableOperationAbs<T> {
	public void delete(Connection conn, String rowKey) {
		// TODO Auto-generated method stub
		
		List<Delete> list = new ArrayList<Delete>();
        Delete del = new Delete(rowKey.getBytes());
        list.add(del);
        deleteList(conn,list);
	        
	        
		
	}
	public boolean create(Connection conn,String tableName, T node) {
		Table table=null;
		 try {
		   // Use the table as needed, for a single operation and a single thread
			 table = conn.getTable(TableName.valueOf(tableName));
			 Put put = mapPut(node,0);
	         table.put(put);
	         return true;
		 } catch (Exception e) {
				System.out.println(this.getClass().getName()+e.toString());
				return false;
			}
	}
	
	public abstract void deleteList(Connection conn, List<Delete> rowKeys);
	
	public abstract boolean create(Connection conn,T node);

	public abstract T getByRowKey(Connection conn,String rowKey);
	public abstract java.util.List<T> getAll(Connection conn);
	public abstract java.util.List<T> getStartAndEnd(Connection conn,String start_row,String stop_row);
	public abstract java.util.List<T> getByScan(Connection conn,Scan scan);
	
	public Result getByRowKey(Connection conn,String tableName,String rowKey) {
		// TODO Auto-generated method stub
		Table table=null;
		Result rs=null;
		try {
			table = conn.getTable(TableName.valueOf(tableName));
	        Get get = new Get(rowKey.getBytes());
	        rs = table.get(get);
	        
	        
	         
		}catch (Exception e) {
			System.out.println(this.getClass().getName()+e.toString());
		}
		
		return rs;
		
	}
	public List<T> getByScan(Connection conn,String tableName, Scan scan) {
		// TODO Auto-generated method stub
		
		Table table=null;
		List<T> nodes=null;
		try {
			table = conn.getTable(TableName.valueOf(tableName));
			ResultScanner scanner = table.getScanner(scan);


			nodes=extractData(scanner);
			
			
		      scanner.close();
	        
	        
	         
		}catch (Exception e) {
			System.out.println(this.getClass().getName()+e.toString());
		}
		return nodes;
	}
	public List<T> extractData(ResultScanner results) throws Exception {
		List<T> rs = new ArrayList<T>();
		int rowNum = 0;
		for (Result result : results) {
			rs.add(mapRow(result,rowNum++));
		}
		return rs;
	}
	public abstract T mapRow(Result result,int rowNum);
	public void deleteList(Connection conn,String tableName, List<Delete> rowKeys) {
		// TODO Auto-generated method stub
		Table table = null;

		try {
			table = conn.getTable(TableName.valueOf(tableName));

			table.delete(rowKeys);

		} catch (Exception e) {
			System.out.println(this.getClass().getName() + e.toString());
		}

	}
	/**
	 * HBase的put操作，批量大小由数据库决定
	 * @param conn
	 * @param tablename
	 * @param putList
	 * @return
	 */
	public boolean putAutoBatch(Connection conn,String tablename, List<Put> putList)
	{
	     Table table = null;
	     try
	     {
	         TableName tName = TableName.valueOf(tablename);
	         table = conn.getTable(tName);
	         table.put(putList); // 数据量达到某个阈值时提交，不达到不提交
	         return true;
	     }
	     catch(Exception e)
	     { 
	         e.printStackTrace();
	         return false;
	     }
	     finally
	     {
	          try
	          {
	            if(table != null)table.close(); // 提交一次
	          }
	          catch(Exception e)
	          {
	              e.printStackTrace();
	          }
	     }
	     
	} 
	public abstract boolean putAutoBatch(Connection conn,List<Put> putList);
	public boolean putObjectAutoBatch(Connection conn,List<T> objectList) {
		java.util.List<Put> list=mapPuts(objectList);
		return putAutoBatch(conn,list);
	}
	
	/*
	 * HBase的put操作，批量大小手动指定
	 * @param conn
	 * @param tablename
	 * @param putList
	 * @param bufferSize 单位为MB
	 * @return
	 */
	public boolean putManualBatch(Connection conn,String tablename, List<Put> putList,int bufferSize)
	{
	    BufferedMutator mutator = null;
	    TableName tName = TableName.valueOf(tablename);
	    BufferedMutatorParams params = new BufferedMutatorParams(tName);
	    params.writeBufferSize(bufferSize*1024*1024); // 可以自己设定阈值 5M 达到5M则提交一次
	    try
	    {
	         mutator = conn.getBufferedMutator(params);
	         mutator.mutate(putList); // 数据量达到5M时会自动提交一次
	         mutator.flush(); // 手动提交一次
	         return true;
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        return false;
	    }
	    finally
	    {
	        try
	        {
	            if(mutator != null)mutator.close();  // 提交一次
	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	        }
	    }
	    
	}

	public abstract boolean putManualBatch(Connection conn,List<Put> putList,int bufferSize);
	public boolean putObjectManaulBatch(Connection conn,List<T> objectList,int bufferSize) {
		java.util.List<Put> list=mapPuts(objectList);
		return putManualBatch(conn,list,bufferSize);
	}

	/**
	 * 转换对象未hbase的put对象
	 * @param listObject
	 * @return
	 */
	public java.util.List<Put> mapPuts(List<T> listObject){
		List<Put> rs = new ArrayList<Put>();
		int rowNum = 0;
		for (T result : listObject) {
			rs.add(mapPut(result,rowNum++));
		}
		return rs;
	}
	public abstract Put mapPut(T object,int rowNum);
}
