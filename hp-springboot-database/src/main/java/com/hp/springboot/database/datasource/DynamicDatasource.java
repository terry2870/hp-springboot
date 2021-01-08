/**
 * 
 */
package com.hp.springboot.database.datasource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.hp.springboot.database.bean.DAOInterfaceInfoBean;
import com.hp.springboot.database.bean.DatabaseConfigProperties;
import com.hp.springboot.database.bean.DatabaseConfigProperties.DatabaseConfig;
import com.hp.springboot.database.bean.DynamicDatasourceBean;
import com.hp.springboot.database.datasource.pool.AbstConnectionPoolFactory;
import com.hp.springboot.database.enums.ConnectionPoolFactoryEnum;
import com.hp.springboot.database.exception.DataSourceNotFoundException;
import com.hp.springboot.database.exception.DynamicDataSourceRouteException;
import com.hp.springboot.database.interceptor.DAOMethodInterceptorHandle;
import com.hp.springboot.database.interceptor.ForceMasterInterceptor;
import com.hp.springboot.database.interceptor.UseDatabaseInterceptor;

/**
 * 描述：动态路由选择数据源
 * 作者：黄平
 * 时间：2018年4月1日
 */
public class DynamicDatasource extends AbstractRoutingDataSource {

	
	private static Logger log = LoggerFactory.getLogger(DynamicDatasource.class);
	
	/**
	 * 存放所有的dao对应的数据源的key
	 * key=dao名称，value=databaseName
	 * 多数据源时，根据database.yml中的配置，先找有没有该dao指定的数据源，如果有，则使用指定的数据源，如果找不到，则使用第一个（也就是主数据源）数据源
	 */
	private static Map<String, String> databaseNameMap = new HashMap<>();
	
	/**
	 * 存放所有的数据源主从的个数
	 * master_databaseName,10
	 * slave_databaseName,20
	 */
	private static Map<String, Integer> databaseIPCountMap = new HashMap<>();
	
	/**
	 * 默认的数据源名称
	 */
	private static String DEFAULT_DATABASE_NAME = "";
	
	/**
	 * master数据源名称的前缀
	 */
	private static final String MASTER_DS_KEY_PREX = "master_";
	
	/**
	 * slave数据源名称的前缀
	 */
	private static final String SLAVE_DS_KEY_PREX = "slave_";
	
	/**
	 * 匹配查询语句
	 */
	private static Pattern select = Pattern.compile("^select.*");
	
	/**
	 * 匹配更新语句
	 */
	private static Pattern update = Pattern.compile("^update.*");
	
	/**
	 * 匹配插入语句
	 */
	private static Pattern insert = Pattern.compile("^insert.*");
	
	/**
	 * 匹配删除语句
	 */
	private static Pattern delete = Pattern.compile("^delete.*");
	
	/**
	 * 数据库配置信息
	 */
	private DatabaseConfigProperties databaseConfigProperties;
	
	public DynamicDatasource() {}
	
	public DynamicDatasource(DatabaseConfigProperties databaseConfigProperties) {
		this.databaseConfigProperties = databaseConfigProperties;
	}
	
	@Override
	public void afterPropertiesSet() {
		//设置targetDataSources 值
		if (databaseConfigProperties == null || CollectionUtils.isEmpty(databaseConfigProperties.getDatabaseConfigList())) {
			// 没有数据库配置信息，直接抛异常，启动失败
			log.error("set DynamicDatasource error. with databaseConfigProperties is null.");
			throw new DynamicDataSourceRouteException("DynamicDatasource route error. with databaseConfigProperties is null");
		}
		try {
			Map<Object, Object> targetDataSources = new HashMap<>();
			
			// 使用哪种类型的连接池（可以dbcp，Druid等等）
			AbstConnectionPoolFactory connectionPool = ConnectionPoolFactoryEnum.getConnectionPoolFactory(databaseConfigProperties.getPoolName());
			DynamicDatasourceBean dynamicDatasourceBean = null;
			String databaseName = null;
			
			// 循环遍历数据库配置信息
			for (DatabaseConfig databaseConfig : databaseConfigProperties.getDatabaseConfigList()) {
				if (databaseConfig.getServers() == null || CollectionUtils.isEmpty(databaseConfig.getServers().getMaster())) {
					// 没有配置数据库ip信息或没有配置主库，直接抛异常，启动失败
					log.error("init database error. with masterUrls is empty.");
					throw new DynamicDataSourceRouteException("masterUrls is empty. with databaseConfig is: " + databaseConfig);
				}
				
				databaseName = databaseConfig.getDatabaseName();
				dynamicDatasourceBean = connectionPool.getDynamicDatasource(databaseConfig);
				
				if (dynamicDatasourceBean == null || CollectionUtils.isEmpty(dynamicDatasourceBean.getMasterDatasource())) {
					log.error("init database error. with masterUrls is empty.");
					throw new DynamicDataSourceRouteException("masterUrls is empty. with databaseConfig is: " + databaseConfig);
				}
				
				//设置master
				for (int i = 0; i < dynamicDatasourceBean.getMasterDatasource().size(); i++) {
					// 设置到自动路由的map中
					targetDataSources.put(buildMasterDatasourceKey(databaseName, i), dynamicDatasourceBean.getMasterDatasource().get(i));
				}
				//设置master有几个数据源
				databaseIPCountMap.put(buildMasterDatasourceKey(databaseName, -1), dynamicDatasourceBean.getMasterDatasource().size());
				
				//设置slave
				if (CollectionUtils.isNotEmpty(dynamicDatasourceBean.getSlaveDatasource())) {
					for (int i = 0; i < dynamicDatasourceBean.getSlaveDatasource().size(); i++) {
						targetDataSources.put(buildSlaveDatasourceKey(databaseName, i), dynamicDatasourceBean.getSlaveDatasource().get(i));
					}
					//设置slave有几个数据源
					databaseIPCountMap.put(buildSlaveDatasourceKey(databaseName, -1), dynamicDatasourceBean.getSlaveDatasource().size());
				}
				
				//默认数据源
				if (StringUtils.isEmpty(DEFAULT_DATABASE_NAME)) {
					// databases.yml的节点 databaseConfigList 下的第一个数据源就是主数据源
					DEFAULT_DATABASE_NAME = databaseName;
				}
				
				//处理dao（这里就是多数据源自动路由使用）
				dealDAOS(databaseConfig.getDaos(), databaseName);
			}
			
			super.setTargetDataSources(targetDataSources);
			super.afterPropertiesSet();
		} catch (Exception e) {
			log.error("deal DynamicDatasource error.", e);
		}
	}

	@Override
	protected Object determineCurrentLookupKey() {
		
		// 查询是否有UseDatabase注解设置数据库
		String useDatabase = UseDatabaseInterceptor.getDatabaseName();
		if (StringUtils.isNotEmpty(useDatabase)) {
			// 有设置了强制数据库
			// 而且强制走这边的master库
			return getDatasourceByKey(useDatabase, true);
		} else {
			// 没有设置，则需要根据方法名去自动路由
			// 获取当前线程的信息
			DAOInterfaceInfoBean daoInfo = DAOMethodInterceptorHandle.getRouteDAOInfo();
			if (daoInfo == null) {
				//如果没有获取到拦截信息，则取主数据库
				log.warn("determineCurrentLookupKey error. with daoInfo is empty.");
				//return null;
				// 由于上面没有设置defaultTargetDataSource，所以这里需要new一个对象出来空对象，下面会自动在主库中随机选择一个
				daoInfo = new DAOInterfaceInfoBean();
			}
			
			//按照dao的className，从数据源中获取数据源
			String mapperNamespace = daoInfo.getMapperNamespace();
			String databaseName = databaseNameMap.get(mapperNamespace);
			
			if (StringUtils.isEmpty(databaseName)) {
				//如果没有，则使用默认数据源
				databaseName = DEFAULT_DATABASE_NAME;
			}
			
			// 根据数据源的key前缀，获取真实的数据源的key
			// 这里考虑了在代码里面的注解（ForceMaster）
			String result = getDatasourceByKey(databaseName, getForceMaster(daoInfo));
			log.debug("-------select route datasource with statementId={} and result is {}", (daoInfo.getMapperNamespace() + "." + daoInfo.getStatementId()), result);
			return result;
		}
	}
	
	/**
	* @Title: getForceMaster  
	* @Description: 获取是否  forceMaster
	* @param daoInfo
	* @return
	 */
	private boolean getForceMaster(DAOInterfaceInfoBean daoInfo) {
		if (ForceMasterInterceptor.getForceMaster()) {
			//有设置forceMaster
			return true;
		}
		
		return getMasterOrSlave(daoInfo);
	}
	
	/**
	* @Title: getMasterOrSlave  
	* @Description: 根据方法名，判断走读库还是写库
	* 这里约定我们的dao里面的方法命名
	* 查询方法：selectXXX
	* 新增方法：insertXXX
	* 更新方法：insertXXX
	* 删除方法：deleteXXX
	* 如果不符合这个规范，则默认路由到master库
	* @param daoInfo
	* @return
	 */
	private boolean getMasterOrSlave(DAOInterfaceInfoBean daoInfo) {
		//根据方法名称去判断
		boolean fromMaster = false;
		//获取用户执行的sql方法名
		String statementId = daoInfo.getStatementId();
		if (StringUtils.isEmpty(statementId)) {
			//没有获取到方法，走master
			return true;
		}
		statementId = statementId.toLowerCase();
		if (select.matcher(statementId).matches()) {
			// 如果是查询语句，这里，随机取主从
			int i = RandomUtils.nextInt(0, 2);
			fromMaster = BooleanUtils.toBoolean(i);
		} else if (update.matcher(statementId).matches() || insert.matcher(statementId).matches() || delete.matcher(statementId).matches()) {
			// 更新，插入，删除使用master数据源
			fromMaster = true;
		} else {
			//如果statemenetId不符合规范，则告警，并且使用master数据源
			log.warn("statement id {}.{} is invalid, should be start with select*/insert*/update*/delete*. ", daoInfo.getMapperNamespace(), daoInfo.getStatementId());
			fromMaster = true;
		}
		return fromMaster;
	}
	
	/**
	* @Title: getDatasourceByKey  
	* @Description: 随机获取路由
	* @param databaseName
	* @param fromMaster
	* @return
	 */
	private String getDatasourceByKey(String databaseName, boolean fromMaster) {
		String datasourceKey = null;
		Integer num = null;
		if (fromMaster) {
			datasourceKey = buildMasterDatasourceKey(databaseName, -1);
			// 获取该数据库的个数
			num = databaseIPCountMap.get(datasourceKey);
			if (num == null) {
				//没找到，直接抛出异常
				log.error("datasource not found with databaseName= {}", databaseName);
				throw new DataSourceNotFoundException(databaseName);
			}
		} else {
			datasourceKey = buildSlaveDatasourceKey(databaseName, -1);
			
			// 获取该数据库的个数
			num = databaseIPCountMap.get(datasourceKey);
			if (num == null) {
				//没有配置从库，则路由到主库
				return getDatasourceByKey(databaseName, true);
			}
		}
		
		int random = 0;
		if (num == 1) {
			//如果就只有一个数据源，则就选择它
			random = 0;
		} else {
			//随机获取一个数据源
			random = RandomUtils.nextInt(0, num);
		}
		return fromMaster ? buildMasterDatasourceKey(databaseName, random) : buildSlaveDatasourceKey(databaseName, random);
	}
	
	/**
	* @Title: dealDAOS  
	* @Description: dao处理
	* @param daoList
	* @param databaseName
	 */
	private void dealDAOS(List<String> daoList, String databaseName) {
		if (CollectionUtils.isEmpty(daoList)) {
			return;
		}
		for (String dao : daoList) {
			databaseNameMap.put(dao, databaseName);
		}
	}

	/**
	* @Title: buildMasterDatasourceKey  
	* @Description: 获取主数据源的key
	* @param databaseName
	* @param index
	* @return
	 */
	private String buildMasterDatasourceKey(String databaseName, int index) {
		StringBuilder sb = new StringBuilder(MASTER_DS_KEY_PREX).append(databaseName);
		if (index >= 0) {
			sb.append("_").append(index);
		}
		return sb.toString();
	}
	
	/**
	 * 获取从数据源的key
	 * @param databaseName
	 * @param index
	 * @return
	 */
	private String buildSlaveDatasourceKey(String databaseName, int index) {
		StringBuilder sb = new StringBuilder(SLAVE_DS_KEY_PREX).append(databaseName);
		if (index >= 0) {
			sb.append("_").append(index);
		}
		return sb.toString();
	}

}
