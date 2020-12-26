/**
 * 
 */
package com.hp.springboot.database.datasource.db.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.hp.springboot.database.datasource.db.AbstDatabase;

/**
 * @author huangping
 * 2018年4月2日
 */
public class MysqlDatabaseImpl implements AbstDatabase {

	/**
	 * mysql默认驱动
	 */
	private static final String DEFAULT_MYSQL_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
	
	@Override
	public String getConnectionUrl(String ipPort, String databaseName, String... params) {
		String url = "jdbc:mysql://"+ ipPort +"/"+ databaseName;
		if (ArrayUtils.isNotEmpty(params) && StringUtils.isNotEmpty(params[0])) {
			url += "?" + params[0];
		}
		return url;
	}

	@Override
	public String getDriverClassName(String driverClassName) {
		return StringUtils.isEmpty(driverClassName) ? DEFAULT_MYSQL_DRIVER_CLASS_NAME : driverClassName;
	}

	@Override
	public String dbTypeToJavaType(String jdbcType) {
		String dataType = jdbcType.toLowerCase();
		if (dataType.indexOf("int") >= 0 || dataType.equalsIgnoreCase("bit")) {
			return "Integer";
		} else if (dataType.indexOf("long") >= 0) {
			return "Long";
		} else if (dataType.equalsIgnoreCase("double")) {
			return "Double";
		} else if (dataType.equalsIgnoreCase("float")) {
			return "Float";
		} else {
			return "String";
		}
	}

	@Override
	public String getCheckSql() {
		return "select 1";
	}

}
