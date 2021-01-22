package com.hp.springboot.common.configuration;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.common.collect.Lists;
import com.hp.springboot.common.util.SpringContextUtil;

/**
 * 描述：通用的springMVC拦截器
 * 作者：黄平
 * 时间：2021-1-8
 */
@Configuration
@DependsOn("SpringContextUtil")
public class CommonWebMvcConfigurer implements WebMvcConfigurer {

	private static final List<String> DEFAULT_STATIC_PATTERN = Lists.newArrayList("/error", "/static/**", "/html/**", "/js/**", "/css/**", "/images/**"
			, "/image/**", "/favicon.ico", "*.html", "*.js", "*.css", "*.jpg", "*.png", "*.gif");
	
	/**
	 * 拦截器的beanname
	 */
	@Value("#{'${hp.springboot.common.interceptor.beannames:}'.split(',')}")
	private List<String> interceptorBeanNameList;
	
	/**
	 * 静态资源文件
	 */
	@Value("#{'${hp.springboot.common.static.path.patterns:}'.split(',')}")
	private List<String> staticPatternList;
	
	/**
	 * 默认的拦截url
	 */
	@Value("${hp.springboot.common.interceptor.path.patterns:/**}")
	private String interceptorPathPatterns;
	
	/**
	 * 首页
	 */
	@Value("${hp.springboot.common.interceptor.welcome.file:}")
	private String welcomeFile;
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		if (StringUtils.isEmpty(welcomeFile)) {
			return;
		}
		registry.addViewController("/").setViewName("forward:" + welcomeFile);
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if (CollectionUtils.isEmpty(staticPatternList) || (staticPatternList.size() == 1 && StringUtils.isEmpty(staticPatternList.get(0)))) {
			//如果没有配置静态资源，则使用默认
			staticPatternList = DEFAULT_STATIC_PATTERN;
		} else {
			//如果配置了，则加上默认的几个
			staticPatternList.addAll(DEFAULT_STATIC_PATTERN);
		}
		HandlerInterceptor interceptor = null;
		if (CollectionUtils.isEmpty(interceptorBeanNameList) || interceptorBeanNameList.size() == 0 || StringUtils.isEmpty(interceptorBeanNameList.get(0))) {
			return;
		}
		
		for (String beanName : interceptorBeanNameList) {
			interceptor = SpringContextUtil.getBean(beanName, HandlerInterceptor.class);
			registry.addInterceptor(interceptor).addPathPatterns(interceptorPathPatterns).excludePathPatterns(staticPatternList);
		}
	}

	public List<String> getStaticPatternList() {
		return staticPatternList;
	}
}
