package com.hp.springboot.threadprofile.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.hp.springboot.threadprofile.profile.ThreadProfile;

/**
 * 描述：方法调用堆栈统计
 * 作者：黄平
 * 时间：2020-12-25
 */
public class MethodProfileInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// 被代理的方法的，类名、方法名、参数
		String className = invocation.getMethod().getDeclaringClass().getName();
		
		// 方法名称
		String method = invocation.getMethod().getName();
				
		// 全局性能统计，并记录堆栈，函数调用开始
		ThreadProfile.enter(className, method);

		// 返回结果
		Object result = null ;
		try {
			// 执行被代理（拦截）的调用
			result = invocation.proceed();
		} catch (Exception e) {
			throw  e;
		} finally {
			// 全局性能统计，并记录堆栈，函数调用结束
			ThreadProfile.exit();
		}
		return result;
	}

}
