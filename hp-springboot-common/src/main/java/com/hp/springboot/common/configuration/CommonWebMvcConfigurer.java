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

	/**
	 * 默认静态资源
	 */
	private static final List<String> DEFAULT_STATIC_PATTERN = Lists.newArrayList("/layui/**", "/html/**", "/js/**", "/css/**", "/images/**"
			, "/image/**", "/favicon.ico");
	
	/**
	 * 配置的静态资源文件
	 */
	@Value("#{'${hp.springboot.common.static.path.patterns:}'.split(',')}")
	private List<String> staticPatternList;
	
	/**
	 * 合并后的静态资源
	 */
	private volatile String[] mergeStaticPatternArray;
	
	/**
	 * 默认的第一免过滤
	 */
	private static final List<String> DEFAULT_FIRST_NO_FILTER_LIST = Lists.newArrayList("/actuator", "/health", "/refeshCheckCode", "/error");
	
	/**
	 * 一级免过滤列表（不管有没有session都可以访问）
	 */
	@Value("#{'${hp.springboot.common.first-no-filter-urls:}'.split(',')}")
	private List<String> firstNoFilterList;
	
	/**
	 * 合并后的第一免过滤列表
	 */
	private volatile String[] mergeFirstNoFilterArray;
	
	/**
	 * 拦截器的beanname
	 */
	@Value("#{'${hp.springboot.common.interceptor.beannames:}'.split(',')}")
	private List<String> interceptorBeanNameList;
	
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
		HandlerInterceptor interceptor = null;
		if (CollectionUtils.isEmpty(interceptorBeanNameList) || interceptorBeanNameList.size() == 0 || StringUtils.isEmpty(interceptorBeanNameList.get(0))) {
			// 没有配置拦截器
			return;
		}
		
		// 获取静态资源
		String[] staticArray = getMergeStaticPatternArray();
		
		// 循环遍历，加入拦截器 
		for (String beanName : interceptorBeanNameList) {
			interceptor = SpringContextUtil.getBean(beanName, HandlerInterceptor.class);
			registry.addInterceptor(interceptor).addPathPatterns(interceptorPathPatterns).excludePathPatterns(staticArray);
		}
	}

	/**
	 * @Title: getMergeStaticPatternList
	 * @Description: 静态资源组合
	 * @return
	 */
	public synchronized String[] getMergeStaticPatternArray() {
		if (mergeStaticPatternArray != null) {
			return mergeStaticPatternArray;
		}
		
		// 首先放入默认静态资源
		List<String> list = Lists.newArrayList(DEFAULT_STATIC_PATTERN);
		
		// 判断是否有配置文件里的静态资源
		if (CollectionUtils.isNotEmpty(staticPatternList) && !(staticPatternList.size() == 1 && StringUtils.isEmpty(staticPatternList.get(0)))) {
			list.addAll(staticPatternList);
		}
		mergeStaticPatternArray = list.toArray(new String[list.size()]);
		return mergeStaticPatternArray;
	}
	
	/**
	 * d
	 * @Title: getMergeStaticPatternList
	 * @Description: 静态资源组合(返回list)
	 * @return
	 */
	public List<String> getMergeStaticPatternList() {
		String[] arr = getMergeStaticPatternArray();
		return Lists.newArrayList(arr);
	}
	
	/**
	 * @Title: getMergeFirstNoFilterArray
	 * @Description: 合并第一级免过滤列表
	 * @return
	 */
	public synchronized String[] getMergeFirstNoFilterArray() {
		if (mergeFirstNoFilterArray != null) {
			return mergeFirstNoFilterArray;
		}
		
		// 首先放入默认地址
		List<String> list = Lists.newArrayList(DEFAULT_FIRST_NO_FILTER_LIST);
		
		// 判断是否有配置文件里的第一免过滤列表
		if (CollectionUtils.isNotEmpty(firstNoFilterList) && !(firstNoFilterList.size() == 1 && StringUtils.isEmpty(firstNoFilterList.get(0)))) {
			list.addAll(firstNoFilterList);
		}
		mergeFirstNoFilterArray = list.toArray(new String[list.size()]);
		return mergeFirstNoFilterArray;
	}
	
	/**
	 * @Title: getMergeFirstNoFilterList
	 * @Description: 合并第一级免过滤列表()返回list
	 * @return
	 */
	public List<String> getMergeFirstNoFilterList() {
		String[] arr = getMergeFirstNoFilterArray();
		return Lists.newArrayList(arr);
	}
}
