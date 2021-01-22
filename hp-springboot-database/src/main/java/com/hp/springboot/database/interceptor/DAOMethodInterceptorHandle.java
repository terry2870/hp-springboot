package com.hp.springboot.database.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.hp.springboot.database.bean.DAOInterfaceInfoBean;
import com.hp.springboot.database.bean.DAOInterfaceInfoBean.DBDelayInfo;
import com.hp.springboot.threadprofile.profile.ThreadProfile;

/**
 * 
 * 描述：执行数据库操作之前拦截请求，记录当前线程信息
 * 之所以用抽象类，是因为可以扩展选择持久层框架。可以选择mybatis或jdbcTemplate，又或者hibernate
 * 作者：黄平
 * 时间：2018年4月11日
 */
public abstract class DAOMethodInterceptorHandle implements MethodInterceptor {

	private static Logger log = LoggerFactory.getLogger(DAOMethodInterceptorHandle.class);
	
	/**
	 * 存放当前执行线程的一些信息
	 */
	private static ThreadLocal<DAOInterfaceInfoBean> routeKey = new ThreadLocal<>();
	
	/**
	 * 最大数据库查询时间（超过这个时间，就会打印一个告警日志）
	 */
	@Value("${hp.springboot.database.maxDbDelayTime:150}")
	private long MAX_DB_DELAY_TIME;
	
	/**
	 * 获取dao操作的对象，方法等
	 * @param invocation
	 * @return
	 */
	public abstract DAOInterfaceInfoBean getDAOInterfaceInfoBean(MethodInvocation invocation);
	
	/**
	 * 获取当前线程的数据源路由的key
	 */
	public static DAOInterfaceInfoBean getRouteDAOInfo() {
		return routeKey.get();
	}
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		//获取dao的操作方法，参数等信息，并设置到线程变量里
		this.setRouteDAOInfo(getDAOInterfaceInfoBean(invocation));
		
		//设置进入查询，记录线程执行时长
		entry();
		Object obj = null;
		try {
			//执行实际方法
			obj = invocation.proceed();
			return obj;
		} catch (Exception e) {
			throw  e;
		} finally {
			//退出查询
			exit();
			
			//避免内存溢出，释放当前线程的数据
			this.removeRouteDAOInfo();
		}
	}
	
	/**
	 * 进入查询
	 */
	private void entry() {
		DAOInterfaceInfoBean bean = getRouteDAOInfo();
		//加入到我们的线程调用堆栈里面，可以统计线程调用时间
		ThreadProfile.enter(bean.getMapperNamespace(), bean.getStatementId());
		DBDelayInfo delay = bean.new DBDelayInfo();
		delay.setBeginTime(System.currentTimeMillis());
		bean.setDelay(delay);
	}
	
	/**
	 * 结束查询
	 */
	private void exit() {
		DAOInterfaceInfoBean bean = getRouteDAOInfo();
		DBDelayInfo delay = bean.getDelay();
		delay.setEndTime(System.currentTimeMillis());
		ThreadProfile.exit();
		//输出查询数据库的时间
		if (delay.getEndTime() - delay.getBeginTime() >= MAX_DB_DELAY_TIME) {
			log.warn("execute db expire time. {}", delay);
		}
		
	}
	
	/**
	 * 绑定当前线程数据源路由的key 使用完成后必须调用removeRouteKey()方法删除
	 */
	private void setRouteDAOInfo(DAOInterfaceInfoBean key) {
		routeKey.set(key);
	}

	/**
	 * 删除与当前线程绑定的数据源路由的key
	 */
	private void removeRouteDAOInfo() {
		routeKey.remove();
	}
}
