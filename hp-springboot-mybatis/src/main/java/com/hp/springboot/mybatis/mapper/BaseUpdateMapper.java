/**
 * 
 */
package com.hp.springboot.mybatis.mapper;

import org.apache.ibatis.annotations.UpdateProvider;

import com.hp.springboot.database.dao.IBaseUpdateDAO;
import com.hp.springboot.mybatis.provider.BaseUpdateSQLProvider;

/**
 * 实现基本的更新操作
 * 继承该接口，可以自动得到基本的更新操作
 * @author huangping
 * 2018年5月29日
 */
public interface BaseUpdateMapper<MODEL, PK> extends IBaseUpdateDAO<MODEL, PK> {

	/**
	 * 根据主键更新数据
	 * @param target
	 * @return
	 */
	@UpdateProvider(type = BaseUpdateSQLProvider.class, method = "updateByPrimaryKey")
	public Integer updateByPrimaryKey(MODEL target);
	
	/**
	 * 根据条件，更新字段
	 * @param t
	 * @return
	 */
	@UpdateProvider(type = BaseUpdateSQLProvider.class, method = "updateByPrimaryKeySelective")
	public Integer updateByPrimaryKeySelective(MODEL target);
}
