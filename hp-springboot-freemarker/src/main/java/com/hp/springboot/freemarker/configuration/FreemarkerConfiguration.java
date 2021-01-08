package com.hp.springboot.freemarker.configuration;

import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * 描述：freemarker自动加载类
 * 作者：黄平
 * 时间：2020-12-23
 */
@Configuration
public class FreemarkerConfiguration {

	
	/**
	* @Title: getFreeMarkerConfigurer  
	* @Description: 生成freemarker的bean
	* @return
	 */
	@Bean("local_freeMarkerConfigurer")
	public FreeMarkerConfigurer getFreeMarkerConfigurer() {
		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
		freeMarkerConfigurer.setTemplateLoaderPath("classpath:/templates");
		freeMarkerConfigurer.setPreferFileSystemAccess(false);
		freeMarkerConfigurer.setDefaultEncoding(StandardCharsets.UTF_8.toString());
		freeMarkerConfigurer.setConfigLocation(new ClassPathResource("freemarker.properties"));
		return freeMarkerConfigurer;
	}
}
