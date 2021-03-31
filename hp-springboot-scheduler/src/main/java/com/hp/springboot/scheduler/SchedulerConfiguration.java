package com.hp.springboot.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 描述：定时任务的配置类
 * 作者：黄平
 * 时间：2021年3月30日
 */
@Configuration
public class SchedulerConfiguration {

	@Value("${hp.springboot.scheduler.pool-size:10}")
	private int poolSize;
	
	@Bean
	@ConditionalOnMissingBean(TaskScheduler.class)
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(poolSize);
		taskScheduler.setThreadNamePrefix("scheduler-");
		taskScheduler.initialize();
		return taskScheduler;
	}
	
	@Bean
	public SchedulerJob schedulerJob() {
		return new SchedulerJob();
	}
}
