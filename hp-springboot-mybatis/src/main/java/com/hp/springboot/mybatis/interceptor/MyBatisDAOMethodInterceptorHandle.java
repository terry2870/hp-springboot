/**
 * 
 */
package com.hp.springboot.mybatis.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.ClassUtils;

import com.hp.springboot.database.bean.DAOInterfaceInfoBean;
import com.hp.springboot.database.interceptor.DAOMethodInterceptorHandle;

/**
 * @author huangping
 * Jul 14, 2020
 */
public class MyBatisDAOMethodInterceptorHandle extends DAOMethodInterceptorHandle {

	@Override
	public DAOInterfaceInfoBean getDAOInterfaceInfoBean(MethodInvocation invocation) {
		DAOInterfaceInfoBean bean = new DAOInterfaceInfoBean();
		
		// 获取当前类的信息（由于我们使用的mybatis，这里获取到的是spring的代理类信息）
		Class<?> clazz = invocation.getThis().getClass();
		
		// 这里获取的才是我们定义的dao接口对象
		Class<?>[] targetInterfaces = ClassUtils.getAllInterfacesForClass(clazz, clazz.getClassLoader());
		
		// 获取该类的父类（该操作暂时没用使用到）
		Class<?>[] parentClass = targetInterfaces[0].getInterfaces();
		if (ArrayUtils.isNotEmpty(parentClass)) {
			bean.setParentClassName(parentClass[0]);
		}
		
		// 设置类名信息
		bean.setClassName(targetInterfaces[0]);
		
		// 设置方法的类信息
		bean.setMapperNamespace(targetInterfaces[0].getName());
		
		// 设置方法名
		bean.setStatementId(invocation.getMethod().getName());
		
		// 设置方法参数
		bean.setParameters(invocation.getArguments());
		return bean;
	}
}
