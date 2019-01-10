package cn.gov.cqaudit.big_resource.dao.common;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.BufferedMutatorParams;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;

public class HbaseDaoCommon {
	public BufferedMutator mutator = null;
	/*
	 * HBase的put操作，批量大小手动指定
	 * @param conn
	 * @param putList
	 * @param bufferSize 单位为MB
	 * @return
	 */
	public boolean putManualBatch(Connection conn,List<Put> putList) throws IOException
	{
	    
	    
	   
	    	
	    	
	         mutator.mutate(putList); // 数据量达到5M时会自动提交一次
	         //mutator.flush(); // 手动提交一次
	         return true;
	   
	    
	}

}
