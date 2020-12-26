/**
 * 
 */
package com.hp.springboot.database.datasource.pool.impl;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.hp.springboot.database.bean.DatabaseConfigProperties.DatabaseConfig;
import com.hp.springboot.database.datasource.db.AbstDatabase;
import com.hp.springboot.database.datasource.pool.AbstConnectionPoolFactory;

/**
 * @author huangping
 * Jul 16, 2020
 */
public class DruidConnectionPoolFactoryImpl implements AbstConnectionPoolFactory {

	@Override
	public DataSource getDatasource(DatabaseConfig databaseConfig, AbstDatabase database, String ipPort) {
		DruidDataSource ds = new DruidDataSource();
		ds.setUrl(database.getConnectionUrl(ipPort, databaseConfig.getDatabaseName(), databaseConfig.getConnectionParam()));
		ds.setUsername(databaseConfig.getUsername());
		ds.setPassword(databaseConfig.getPassword());
        
        //下面都是可选的配置
        ds.setInitialSize(databaseConfig.getInitialSize());  //初始连接数，默认0
        ds.setMaxActive(databaseConfig.getMaxTotal());  //最大连接数，默认8
        ds.setMinIdle(databaseConfig.getMinIdle());  //最小闲置数
        ds.setMaxWait(databaseConfig.getMaxWaitMillis());  //获取连接的最大等待时间，单位毫秒
		ds.setTimeBetweenEvictionRunsMillis(databaseConfig.getTimeBetweenEvictionRunsMillis());
		ds.setTestWhileIdle(databaseConfig.isTestWhileIdle());
		ds.setValidationQuery(databaseConfig.getValidationQuery());
		ds.setPoolPreparedStatements(databaseConfig.isPoolPreparedStatements());
		ds.setMaxOpenPreparedStatements(databaseConfig.getMaxPoolPreparedStatementPerConnectionSize());
		try {
			ds.setFilters("stat");
		} catch (SQLException e) {
		}
		return ds;
	}

}
