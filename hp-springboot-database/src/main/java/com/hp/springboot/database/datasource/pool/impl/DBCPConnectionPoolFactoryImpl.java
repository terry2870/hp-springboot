/**
 * 
 */
package com.hp.springboot.database.datasource.pool.impl;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.hp.springboot.database.bean.DatabaseConfigProperties.DatabaseConfig;
import com.hp.springboot.database.datasource.db.AbstDatabase;
import com.hp.springboot.database.datasource.pool.AbstConnectionPoolFactory;

/**
 * @author huangping
 * 2018年4月2日
 */
public class DBCPConnectionPoolFactoryImpl implements AbstConnectionPoolFactory {
	
	/**
	 * 获取基础信息
	 */
	@Override
	public DataSource getDatasource(DatabaseConfig databaseConfig, AbstDatabase database, String ipPort) {
		BasicDataSource ds = new BasicDataSource();
		ds.setUrl(database.getConnectionUrl(ipPort, databaseConfig.getDatabaseName(), databaseConfig.getConnectionParam()));
		ds.setDriverClassName(database.getDriverClassName(databaseConfig.getDriverClassName()));
		ds.setUsername(databaseConfig.getUsername());
		ds.setPassword(databaseConfig.getPassword());
		ds.setMaxTotal(databaseConfig.getMaxTotal());
		ds.setMaxIdle(databaseConfig.getMaxIdle());
		ds.setMinIdle(databaseConfig.getMinIdle());
		ds.setInitialSize(databaseConfig.getInitialSize());
		ds.setMaxWaitMillis(databaseConfig.getMaxWaitMillis());
		ds.setTimeBetweenEvictionRunsMillis(databaseConfig.getTimeBetweenEvictionRunsMillis());
		ds.setTestWhileIdle(databaseConfig.isTestWhileIdle());
		ds.setNumTestsPerEvictionRun(databaseConfig.getNumTestsPerEvictionRun());
		ds.setValidationQuery(databaseConfig.getValidationQuery());
		ds.setPoolPreparedStatements(databaseConfig.isPoolPreparedStatements());
		ds.setMaxOpenPreparedStatements(databaseConfig.getMaxPoolPreparedStatementPerConnectionSize());
		return ds;
	}
	

}
