package cn.gov.cqaudit.big_resource.hive_module.jdbc;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import org.springframework.util.CollectionUtils;

public abstract class HiveExecutor implements InitializingBean {

	private HiveClientFactory hiveClientFactory;
	private HiveOperations hiveTemplate;
	private Collection<HiveScript> scripts;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (hiveClientFactory == null && hiveTemplate == null) {
			throw new IllegalArgumentException("a HiveClient factory or a HiveTemplate is required");
		}

		if (hiveTemplate == null) {
			hiveTemplate = new HiveTemplate(hiveClientFactory);
		}
	}

	protected List<String> executeHiveScripts() {
		if (CollectionUtils.isEmpty(scripts)) {
			return Collections.emptyList();
		}

		return hiveTemplate.executeScript(scripts);
	}

	/**
	 * Sets the scripts to be executed by this tasklet.
	 * 
	 * @param scripts The scripts to set.
	 */
	public void setScripts(Collection<HiveScript> scripts) {
		this.scripts = scripts;
	}

	/**
	 * Sets the hive client for this tasklet.
	 *  
	 * @param hiveClientFactory hiveFactory to set
	 */
	public void setHiveClientFactory(HiveClientFactory hiveClientFactory) {
		this.hiveClientFactory = hiveClientFactory;
	}

	/**
	 * Sets the hive template.
	 *
	 * @param hiveTemplate the new hive template
	 */
	public void setHiveTemplate(HiveOperations hiveTemplate) {
		this.hiveTemplate = hiveTemplate;
	}
}