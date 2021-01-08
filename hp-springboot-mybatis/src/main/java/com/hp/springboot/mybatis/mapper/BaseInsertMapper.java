/**
 * 
 */
package com.hp.springboot.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;

import com.hp.springboot.database.dao.IBaseInsertDAO;
import com.hp.springboot.mybatis.constant.SQLProviderConstant;
import com.hp.springboot.mybatis.provider.BaseInsertSQLProvider;

/**
 * 实现基本的新增操作
 * 继承该接口，可以自动得到基本的新增操作
 * @author huangping
 * 2018年5月29日
 */
public interface BaseInsertMapper<MODEL, PK> extends IBaseInsertDAO<MODEL, PK> {

	/**
	 * 新增数据
	 * 插入成功后，参数对象中的主键自动赋值成功
	 * @param target
	 * @return
	 */
	@InsertProvider(type = BaseInsertSQLProvider.class, method = "insert")
	@Options(useGeneratedKeys = true, keyProperty = SQLProviderConstant.KEY_PROPERTY_ID)
	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = SQLProviderConstant.KEY_PROPERTY_ID, before = false, resultType = Integer.class)
	public Integer insert(MODEL target);
	
	/**
	 * 根据条件，新增字段
	 * 插入成功后，参数对象中的主键自动赋值成功
	 * @param target
	 * @return
	 */
	@InsertProvider(type = BaseInsertSQLProvider.class, method = "insertSelective")
	@Options(useGeneratedKeys = true, keyProperty = SQLProviderConstant.KEY_PROPERTY_ID)
	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = SQLProviderConstant.KEY_PROPERTY_ID, before = false, resultType = Integer.class)
	public Integer insertSelective(MODEL target);
	
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	@InsertProvider(type = BaseInsertSQLProvider.class, method = "insertBatch")
	public Integer insertBatch(List<MODEL> list);
}
