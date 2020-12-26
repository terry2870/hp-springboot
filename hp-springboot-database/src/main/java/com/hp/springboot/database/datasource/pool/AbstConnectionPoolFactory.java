/**
 * 
 */
package com.hp.springboot.database.datasource.pool;
/**
 * @author huangping
 * 2018年4月2日
 */

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.springboot.database.bean.DatabaseConfigProperties.DatabaseConfig;
import com.hp.springboot.database.bean.DynamicDatasourceBean;
import com.hp.springboot.database.datasource.db.AbstDatabase;
import com.hp.springboot.database.enums.DatabaseTypeEnum;
import com.hp.springboot.database.exception.MasterUrlNotFoundException;

public interface AbstConnectionPoolFactory {
	
	static Logger log = LoggerFactory.getLogger(AbstConnectionPoolFactory.class);

	/**
	 * 获取连接池对象
	 * @param databaseConfig
	 * @return
	 */
	public default DynamicDatasourceBean getDynamicDatasource(DatabaseConfig databaseConfig) {
		if (databaseConfig == null || databaseConfig.getServers() == null || CollectionUtils.isEmpty(databaseConfig.getServers().getMaster())) {
			log.error("getDatasource error. with masterIpPort is empty");
			throw new MasterUrlNotFoundException();
		}
		
		List<String> masterList = databaseConfig.getServers().getMaster();
		List<String> slaveList = databaseConfig.getServers().getSlave();
		
		DynamicDatasourceBean result = new DynamicDatasourceBean();
		DataSource ds = null;
		AbstDatabase database = DatabaseTypeEnum.getDatabaseByDatabaseType(databaseConfig.getDatabaseType());
		
		List<DataSource> masterDatasource = new ArrayList<>(masterList.size());
		
		//处理master的数据源
		for (String url : masterList) {
			ds = getDatasource(databaseConfig, database, url);
			masterDatasource.add(ds);
		}
		result.setMasterDatasource(masterDatasource);
		
		//处理slave数据源
		if (CollectionUtils.isNotEmpty(slaveList)) {
			List<DataSource> slaveDatasource = new ArrayList<>(slaveList.size());
			for (String url : slaveList) {
				ds = getDatasource(databaseConfig, database, url);
				slaveDatasource.add(ds);
			}
			result.setSlaveDatasource(slaveDatasource);
		}
		return result;
	}
	
	/**
	 * 获取基础信息
	 * @param databaseConfig
	 * @param database
	 * @param ipPort
	 * @return
	 */
	public DataSource getDatasource(DatabaseConfig databaseConfig, AbstDatabase database, String ipPort);
}
