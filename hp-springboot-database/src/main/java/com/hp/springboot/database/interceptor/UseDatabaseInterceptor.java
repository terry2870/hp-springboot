package com.hp.springboot.database.interceptor;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.hp.springboot.database.annotation.UseDatabase;
import com.hp.springboot.database.exception.DatabaseNotSetException;

/**
 * 描述：强制使用数据
 * 作者：黄平
 * 时间：2021-1-7
 */
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UseDatabaseInterceptor {

	private static Logger log = LoggerFactory.getLogger(UseDatabaseInterceptor.class);
	
	private static final ThreadLocal<String> USE_DATABASE = new InheritableThreadLocal<>();
	
	/**
	* @Title: around  
	* @Description: 设置强制走的数据库
	* @param joinPoint
	* @return
	* @throws Throwable
	 */
	@Around("@annotation(com.hp.springboot.database.annotation.UseDatabase)")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		log.debug("start before");
		
		//方法签名
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		
		Method method = methodSignature.getMethod();
		
		// 获取UseDatabase注解
		UseDatabase useDatabase = method.getAnnotation(UseDatabase.class);
		
		// 设置的数据库名称
		String databaseName = useDatabase.value();
		
		if (StringUtils.isEmpty(databaseName)) {
			// 没有指定数据库名称，报错
			log.error("UseDatabase error. with databaseName is empty");
			throw new DatabaseNotSetException();
		}
		
		// 设置到线程变量中
		USE_DATABASE.set(databaseName);
		Object obj = null;
		try {
			obj = joinPoint.proceed();
			return obj;
		} catch (Exception e) {
			throw e;
		} finally {
			log.debug("start after");
			USE_DATABASE.remove();
		}
	}
	
	/**
	* @Title: getForceMaster  
	* @Description: 获取设置的哪个数据库
	* @return
	 */
	public static String getDatabaseName() {
		return USE_DATABASE.get();
	}
}
