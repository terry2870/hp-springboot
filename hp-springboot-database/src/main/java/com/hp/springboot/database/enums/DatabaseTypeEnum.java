/**
 * 
 */
package com.hp.springboot.database.enums;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.hp.springboot.database.datasource.db.AbstDatabase;
import com.hp.springboot.database.datasource.db.impl.MysqlDatabaseImpl;

/**
 * @author huangping
 * 2018年4月2日
 */
public enum DatabaseTypeEnum {

	MYSQL(1),
	ORACLE(2);
	
	private Integer value;
	
	private DatabaseTypeEnum(Integer value) {
		this.value = value;
	}
	
	/**
	 * 返回json格式的数据
	 * @return 返回json格式的数据
	 */
	public static Collection<?> toJson() {
		Collection<Map<String, String>> list = new ArrayList<>();
		Map<String, String> map = null;
		for (DatabaseTypeEnum e : values()) {
			map = new HashMap<>();
			map.put("value", e.getValue().toString());
			map.put("text", e.toString());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 获取数据库连接url
	 * @param databaseType
	 * @return
	 */
	public static AbstDatabase getDatabaseByDatabaseType(String databaseType) {
		if (MYSQL.toString().equalsIgnoreCase(databaseType)) {
			return new MysqlDatabaseImpl();
		}
		return null;
	}

	public Integer getValue() {
		return value;
	}
}
