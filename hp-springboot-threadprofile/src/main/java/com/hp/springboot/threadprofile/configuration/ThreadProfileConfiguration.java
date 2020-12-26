package com.hp.springboot.threadprofile.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hp.springboot.threadprofile.interceptor.ThreadProfileInterceptor;

/**
 * 描述：线程调用堆栈打印的启动类
 * 作者：黄平
 * 时间：2020-12-25
 */
@Configuration
@ImportResource(locations = {"classpath*:META-INF/spring/hp-springboot-threadprofile.xml"})
public class ThreadProfileConfiguration implements WebMvcConfigurer {

	@Value("${hp.springboot.service.profile.threshold:200}")
	private int threshold;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 添加到mvc拦截器
		ThreadProfileInterceptor threadProfileInterceptor = new ThreadProfileInterceptor();
		threadProfileInterceptor.setThreshold(this.threshold);
		registry.addInterceptor(threadProfileInterceptor);
	}
}
