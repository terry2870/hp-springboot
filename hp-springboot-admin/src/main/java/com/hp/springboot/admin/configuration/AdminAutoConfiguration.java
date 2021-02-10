package com.hp.springboot.admin.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.hp.springboot.admin.interceptor.AdminExceptionHandler;

/**
 * 描述：admin工程的自启动类
 * 作者：黄平
 * 时间：2021年1月11日
 */
@Configuration
@MapperScan("com.hp.springboot.admin.dal")
@ComponentScan({"com.hp.springboot.admin.controller", "com.hp.springboot.admin.service"})
@ImportResource(locations = {"classpath*:META-INF/spring/hp-springboot-admin.xml"})
public class AdminAutoConfiguration {

	
	/**
	 * @Title: exceptionHandler
	 * @Description: 开启异常拦截器
	 * @return
	 */
	@Bean("adminExceptionHandler")
	public AdminExceptionHandler exceptionHandler() {
		return new AdminExceptionHandler();
	}

}
