package cn.gov.cqaudit.big_resource.hive_module.jdbc;



public interface HiveClientCallback<T> {

	/**
	 * Gets called by {@link HiveTemplate#execute(HiveClientCallback)} with an active {@link HiveClient}. Does not need to
	 * care about activating or closing the {@link HiveClient}, or handling exceptions.
	 * 
	 * Due to the big number of exceptions thrown by {@link HiveClient} (in particular
	 * {@link org.apache.hadoop.hive.metastore.api.ThriftHiveMetastore.Client}) which do not share any common base class,
	 * the callback signature uses a generic declaration.
	 * For user specific error, consider runtime exceptions which are not translated.
	 * 
	 * @param hiveClient active hive connection
	 * @return action result
	 * @throws Exception if thrown by {@link HiveClient}
	 */
	T doInHive(HiveClient hiveClient) throws Exception;
}