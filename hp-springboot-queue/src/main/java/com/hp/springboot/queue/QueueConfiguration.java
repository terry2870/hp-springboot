package com.hp.springboot.queue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：队列的自动加载配置
 * 作者：黄平
 * 时间：2021年3月22日
 */
@Configuration
@ComponentScan("com.hp.springboot.queue.controller")
public class QueueConfiguration {

	
	@Bean
	public QueueProducer queueProducer() {
		return new QueueProducer();
	}

}
