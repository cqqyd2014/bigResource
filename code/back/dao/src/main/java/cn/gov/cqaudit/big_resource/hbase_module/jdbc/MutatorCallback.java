package cn.gov.cqaudit.big_resource.hbase_module.jdbc;

import org.apache.hadoop.hbase.client.BufferedMutator;

public interface MutatorCallback<T> {
	T doInTable(BufferedMutator mutator) throws Throwable;
}
