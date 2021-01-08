package com.hp.springboot.mybatis.configuration;

import java.io.IOException;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.hp.springboot.database.datasource.DynamicDatasource;
import com.hp.springboot.database.interceptor.DAOMethodInterceptorHandle;
import com.hp.springboot.mybatis.interceptor.MyBatisDAOMethodInterceptorHandle;

/**
 * 描述：mybatis自动启动类
 * 作者：黄平
 * 时间：2020-12-28
 */
@Configuration
@ComponentScan("com.hp.springboot.mybatis.autocreate")
public class MyBatisAutoConfiguration {

	@Autowired
	private DynamicDatasource dynamicDatasource;
	
	@Bean("DAOMethodInterceptorHandle")
	public DAOMethodInterceptorHandle createDAOMethodInterceptorHandle() {
		return new MyBatisDAOMethodInterceptorHandle();
	}
	
	/**
	* @Title: getSqlSessionFactoryBean  
	* @Description: 生成mybatis的sessionFactory  
	* @return
	 * @throws IOException 
	 */
	@Bean
	public SqlSessionFactoryBean getSqlSessionFactoryBean() throws IOException {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dynamicDatasource);
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("META-INF/mybatis/*.xml"));
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("META-INF/spring/mybatis-config.xml"));
		return sqlSessionFactoryBean;
	}
}
