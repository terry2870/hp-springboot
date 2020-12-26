/**
 * 
 */
package com.hp.springboot.database.bean;

import java.lang.reflect.Method;

import com.hp.springboot.common.bean.AbstractBean;
import com.hp.springboot.common.util.JacksonUtil;

/**
 * 当前执行的dao的类属性
 * @author huangping
 * 2018年4月11日
 */
public class DAOInterfaceInfoBean extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4162275762456111854L;
	
	/**
	 * mapper的接口类型
	 */
	private Class<?> className;
	
	/**
	 * 父接口的class
	 */
	private Class<?> parentClassName;
	
	/**
	 * 执行的方法
	 */
	private Method method;
	
	/**
	 * dao的类名称
	 */
	private String mapperNamespace;
	
	/**
	 * 参数
	 */
	private Object[] parameters;
	
	/**
	 * 执行的方法
	 */
	private String statementId;
	
	/**
	 * 调用数据库信息
	 */
	private DBDelayInfo delay;
	
	public class DBDelayInfo {

		private long beginTime;
		private long endTime ;
		
		public DBDelayInfo() {}
		
		public DBDelayInfo(long beginTime, long endTime) {
			this.beginTime = beginTime;
			this.endTime = endTime;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("operator db with, delay=").append(endTime - beginTime)
			.append(", dao=").append(mapperNamespace).append(".").append(statementId)
			.append(", arguments=").append(JacksonUtil.toJSONString(parameters));
			return sb.toString();
		}

		public long getBeginTime() {
			return beginTime;
		}

		public void setBeginTime(long beginTime) {
			this.beginTime = beginTime;
		}

		public long getEndTime() {
			return endTime;
		}

		public void setEndTime(long endTime) {
			this.endTime = endTime;
		}
	}

	public String getMapperNamespace() {
		return mapperNamespace;
	}

	public void setMapperNamespace(String mapperNamespace) {
		this.mapperNamespace = mapperNamespace;
	}

	public String getStatementId() {
		return statementId;
	}

	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public DBDelayInfo getDelay() {
		return delay;
	}

	public void setDelay(DBDelayInfo delay) {
		this.delay = delay;
	}

	public Class<?> getClassName() {
		return className;
	}

	public void setClassName(Class<?> className) {
		this.className = className;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Class<?> getParentClassName() {
		return parentClassName;
	}

	public void setParentClassName(Class<?> parentClassName) {
		this.parentClassName = parentClassName;
	}

}
