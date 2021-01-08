package com.hp.springboot.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hp.springboot.common.util.SpringContextUtil;

/**
 * 描述：
 * 作者：黄平
 * 时间：2020-12-30
 */
@Configuration
public class CommonAutoConfiguration {

	@Bean("SpringContextUtil")
	public SpringContextUtil getSpringContextUtil() {
		return new SpringContextUtil();
	}
}
