/**
 * 
 */
package com.hp.springboot.database.enums;

import java.util.ArrayList;
import java.util.Collection;

import com.hp.springboot.common.bean.ValueTextBean;
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
	public static Collection<ValueTextBean> toJson() {
		Collection<ValueTextBean> list = new ArrayList<>();
		for (DatabaseTypeEnum e : values()) {
			list.add(new ValueTextBean(e.getValue().toString(), e.toString()));
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
