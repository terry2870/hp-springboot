package com.hp.springboot.database.bean;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.hp.springboot.common.factory.YmlPropertySourceFactory;
import com.hp.springboot.database.enums.ConnectionPoolFactoryEnum;
import com.hp.springboot.database.enums.DatabaseTypeEnum;

/**
 * 描述：数据库配置文件类
 * 作者：黄平
 * 时间：2020-12-26
 */
@Configuration
@ConfigurationProperties(prefix = "hp.springboot.database")
@PropertySource(value = "classpath:databases.yml", factory = YmlPropertySourceFactory.class)
public class DatabaseConfigProperties {

	/**
	 * 需要拦截的操作数据列的dao的包
	 */
	private String expression;
	
	private List<DatabaseConfig> databaseConfigList;
	
	/**
	 * 连接池类型
	 */
	private String poolName = ConnectionPoolFactoryEnum.DBCP.name();
	
	public static class DatabaseConfig {
		
		/**
		 * 数据库类型
		 */
		private String databaseType = DatabaseTypeEnum.MYSQL.name();
		
		/**
		 * 数据库驱动
		 */
		private String driverClassName;
		
		/**
		   * 数据库名称
		 */
		private String databaseName;
		
		/**
		 * 主备库配置
		 */
		private Servers servers;
		
		/**
		 * 数据库用户名
		 */
		private String username;
		
		/**
		 * 数据库密码
		 */
		private String password;
		
		/**
		 * 多数据源情况下，关联的dao列表
		 */
		private List<String> daos;
		
		/**
		 * 连接字符串
		 */
		private String connectionParam = "useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC&useInformationSchema=true";
		
		/**
		 * 最小空闲数
		 */
		private int minIdle = 0;
		
		/**
		 * 最大连接数
		 */
		private int maxTotal = 8;
		
		/**
		 * 最大空闲数
		 */
		private int maxIdle = 8;
		
		/**
		 * 初始化连接数
		 */
		private int initialSize = 0;
		
		/**
		 * 最大等待时间
		 */
		private long maxWaitMillis = 5000;
		
		/**
		 * 
		 */
		private long timeBetweenEvictionRunsMillis = -1L;
		
		/**
		 * 是否检查连接有效性
		 */
		private boolean testWhileIdle = true;
		
		/**
		 * 检查的sql
		 */
		private String validationQuery = "select 1";
		
		/**
		 * 设定在进行后台对象清理时，每次检查几个链接
		 */
		private int numTestsPerEvictionRun = 3;
		
		/**
		 * 
		 */
		private boolean poolPreparedStatements = false;
		
		/**
		 * Statement缓存大小
		 */
		private int maxPoolPreparedStatementPerConnectionSize;

		public String getDatabaseType() {
			return databaseType;
		}

		public void setDatabaseType(String databaseType) {
			this.databaseType = databaseType;
		}

		public String getDriverClassName() {
			return driverClassName;
		}

		public void setDriverClassName(String driverClassName) {
			this.driverClassName = driverClassName;
		}

		public String getDatabaseName() {
			return databaseName;
		}

		public void setDatabaseName(String databaseName) {
			this.databaseName = databaseName;
		}

		public Servers getServers() {
			return servers;
		}

		public void setServers(Servers servers) {
			this.servers = servers;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public List<String> getDaos() {
			return daos;
		}

		public void setDaos(List<String> daos) {
			this.daos = daos;
		}

		public String getConnectionParam() {
			return connectionParam;
		}

		public void setConnectionParam(String connectionParam) {
			this.connectionParam = connectionParam;
		}

		public int getMinIdle() {
			return minIdle;
		}

		public void setMinIdle(int minIdle) {
			this.minIdle = minIdle;
		}

		public int getMaxTotal() {
			return maxTotal;
		}

		public void setMaxTotal(int maxTotal) {
			this.maxTotal = maxTotal;
		}

		public int getMaxIdle() {
			return maxIdle;
		}

		public void setMaxIdle(int maxIdle) {
			this.maxIdle = maxIdle;
		}

		public int getInitialSize() {
			return initialSize;
		}

		public void setInitialSize(int initialSize) {
			this.initialSize = initialSize;
		}

		public long getMaxWaitMillis() {
			return maxWaitMillis;
		}

		public void setMaxWaitMillis(long maxWaitMillis) {
			this.maxWaitMillis = maxWaitMillis;
		}

		public long getTimeBetweenEvictionRunsMillis() {
			return timeBetweenEvictionRunsMillis;
		}

		public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
			this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
		}

		public boolean isTestWhileIdle() {
			return testWhileIdle;
		}

		public void setTestWhileIdle(boolean testWhileIdle) {
			this.testWhileIdle = testWhileIdle;
		}

		public int getNumTestsPerEvictionRun() {
			return numTestsPerEvictionRun;
		}

		public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
			this.numTestsPerEvictionRun = numTestsPerEvictionRun;
		}

		public boolean isPoolPreparedStatements() {
			return poolPreparedStatements;
		}

		public void setPoolPreparedStatements(boolean poolPreparedStatements) {
			this.poolPreparedStatements = poolPreparedStatements;
		}

		public int getMaxPoolPreparedStatementPerConnectionSize() {
			return maxPoolPreparedStatementPerConnectionSize;
		}

		public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
			this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
		}

		public String getValidationQuery() {
			return validationQuery;
		}

		public void setValidationQuery(String validationQuery) {
			this.validationQuery = validationQuery;
		}
	}
	
	public static class Servers {
		
		/**
		 * 主库地址
		 */
		private List<String> master;
		
		/**
		 * 从库地址
		 */
		private List<String> slave;

		public List<String> getMaster() {
			return master;
		}

		public void setMaster(List<String> master) {
			this.master = master;
		}

		public List<String> getSlave() {
			return slave;
		}

		public void setSlave(List<String> slave) {
			this.slave = slave;
		}
	}

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public List<DatabaseConfig> getDatabaseConfigList() {
		return databaseConfigList;
	}

	public void setDatabaseConfigList(List<DatabaseConfig> databaseConfigList) {
		this.databaseConfigList = databaseConfigList;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
}
