package cn.gov.cqaudit.big_resource.dao.common;

import java.util.List;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.BufferedMutatorParams;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;

public class HbaseDaoCommon {
	/*
	 * HBase的put操作，批量大小手动指定
	 * @param conn
	 * @param tablename
	 * @param putList
	 * @param bufferSize 单位为MB
	 * @return
	 */
	public boolean putManualBatch(Connection conn,String tablename, List<Put> putList,long bufferSize)
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

}
