package com.hp.springboot.database.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ImportResource;

import com.hp.springboot.database.bean.DatabaseConfigProperties;
import com.hp.springboot.database.datasource.DynamicDatasource;
import com.hp.springboot.database.interceptor.ForceMasterInterceptor;
import com.hp.springboot.database.interceptor.UseDatabaseInterceptor;


/**
 * 描述：
 * 作者：黄平
 * 时间：2020-12-26
 */
@Configuration
@EnableConfigurationProperties(DatabaseConfigProperties.class)
@ImportResource(locations = {"classpath*:META-INF/spring/hp-springboot-database.xml"})
public class DatabaseAutoConfiguration {

	@Autowired
	private DatabaseConfigProperties databaseConfigProperties;
	
	/**
	* @Title: dynamicDatasource  
	* @Description: 生成动态数据源
	* @return
	 */
	@Bean("dynamicDatasource")
	@DependsOn("SpringContextUtil")
	public DynamicDatasource dynamicDatasource() {
		DynamicDatasource dynamicDatasource = new DynamicDatasource(databaseConfigProperties);
		return dynamicDatasource;
	}
	
	/**
	* @Title: forceMasterInterceptor  
	* @Description: 设置强制走主库
	* @return
	 */
	@Bean
	public ForceMasterInterceptor forceMasterInterceptor() {
		return new ForceMasterInterceptor();
	}
	
	/**
	* @Title: useDatabaseInterceptor  
	* @Description: 强制走哪个数据库
	* @return
	 */
	@Bean
	public UseDatabaseInterceptor useDatabaseInterceptor() {
		return new UseDatabaseInterceptor();
	}
}
