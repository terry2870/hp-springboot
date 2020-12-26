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

/**
 * @author huangping
 * 2018年4月1日 下午1:26:55
 */
public class DynamicDatasource extends AbstractRoutingDataSource {

	
	private static Logger log = LoggerFactory.getLogger(DynamicDatasource.class);
	
	//存放所有的dao对应的数据源的key
	// key=dao名称，value=databaseName
	private static Map<String, String> databaseNameMap = new HashMap<>();
	
	/**
	 * 存放所有的数据源主从的个数
	 * master_databaseName,10
	 * slave_databaseName,20
	 */
	private static Map<String, Integer> databaseIPCountMap = new HashMap<>();
	
	//默认的数据源名称
	private static String DEFAULT_DATABASE_NAME = "";
	
	private static final String MASTER_DS_KEY_PREX = "master_";
	private static final String SLAVE_DS_KEY_PREX = "slave_";
	
	private static Pattern select = Pattern.compile("^select.*");
	private static Pattern update = Pattern.compile("^update.*");
	private static Pattern insert = Pattern.compile("^insert.*");
	private static Pattern delete = Pattern.compile("^delete.*");
		
	private DatabaseConfigProperties databaseConfigProperties;
	
	public DynamicDatasource() {}
	
	public DynamicDatasource(DatabaseConfigProperties databaseConfigProperties) {
		this.databaseConfigProperties = databaseConfigProperties;
	}
	
	public void init() {
		//设置targetDataSources 值
		if (databaseConfigProperties == null || CollectionUtils.isEmpty(databaseConfigProperties.getDatabaseConfigList())) {
			log.error("set DynamicDatasource error. with databaseConfigProperties is null.");
			throw new DynamicDataSourceRouteException("DynamicDatasource route error. with databaseConfigProperties is null");
		}
		try {
			Map<Object, Object> targetDataSources = new HashMap<>();
			
			//使用哪种类型的连接池
			AbstConnectionPoolFactory connectionPool = ConnectionPoolFactoryEnum.getConnectionPoolFactory(databaseConfigProperties.getPoolName());
			DynamicDatasourceBean dynamicDatasourceBean = null;
			String databaseName = null;
			for (DatabaseConfig databaseConfig : databaseConfigProperties.getDatabaseConfigList()) {
				if (databaseConfig.getServers() == null || CollectionUtils.isEmpty(databaseConfig.getServers().getMaster())) {
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
					DEFAULT_DATABASE_NAME = databaseName;
				}
				
				//处理dao
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
		//根据用户
		DAOInterfaceInfoBean daoInfo = DAOMethodInterceptorHandle.getRouteDAOInfo();
		if (daoInfo == null) {
			//如果没有获取到拦截信息，则取主数据库
			log.warn("determineCurrentLookupKey error. with daoInfo is empty.");
			//return null;
			//new一个对象出来
			daoInfo = new DAOInterfaceInfoBean();
		}
		
		//按照dao的className，从数据源中获取数据源
		String mapperNamespace = daoInfo.getMapperNamespace();
		String databaseName = databaseNameMap.get(mapperNamespace);
		if (StringUtils.isEmpty(databaseName)) {
			//如果没有，则使用默认数据源
			databaseName = DEFAULT_DATABASE_NAME;
		}
		
		String result = getDatasourceByKey(databaseName, getForceMaster(daoInfo));
		log.debug("-------select route datasource with statementId={} and result is {}", (daoInfo.getMapperNamespace() + "." + daoInfo.getStatementId()), result);
		return result;
	}
	
	/**
	 * 获取是否  forceMaster
	 * @param daoInfo
	 * @return
	 */
	private boolean getForceMaster(DAOInterfaceInfoBean daoInfo) {
		if (ForceMasterInterceptor.getForceMaster()) {
			//有设置forceMaster
			return true;
		}
		
		return getForceMasterFromAnnotation(daoInfo);
	}
	
	/**
	 * 根据方法名，判断走主从
	 */
	private boolean getForceMasterFromAnnotation(DAOInterfaceInfoBean daoInfo) {
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
			//这个时候，随机主从
			int i = RandomUtils.nextInt(0, 2);
			fromMaster = BooleanUtils.toBoolean(i);
		} else if (update.matcher(statementId).matches() || insert.matcher(statementId).matches() || delete.matcher(statementId).matches()) {
			//使用master数据源
			fromMaster = true;
		} else {
			//如果statemenetId不符合规范，则告警，并且使用master数据源
			log.warn("statement id {}.{} is invalid, should be start with select*/insert*/update*/delete*. ", daoInfo.getMapperNamespace(), daoInfo.getStatementId());
			fromMaster = true;
		}
		return fromMaster;
	}
	
	/**
	 * 随机获取路由
	 * @param databaseName
	 * @param fromMaster
	 * @return
	 */
	private String getDatasourceByKey(String databaseName, boolean fromMaster) {
		String datasourceKey = null;
		Integer num = null;
		if (fromMaster) {
			datasourceKey = buildMasterDatasourceKey(databaseName, -1);
			num = databaseIPCountMap.get(datasourceKey);
			if (num == null) {
				//没找到，直接抛出异常
				log.error("datasource not found with databaseName= {}", databaseName);
				throw new DataSourceNotFoundException(databaseName);
			}
		} else {
			datasourceKey = buildSlaveDatasourceKey(databaseName, -1);
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
	 * 获取主数据源的key
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
