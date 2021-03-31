package com.hp.springboot.scheduler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;

import com.hp.springboot.scheduler.enums.SchedulerTypeEnum;

/**
 * 描述：调度任务
 * 作者：黄平
 * 时间：2021年3月26日
 */
public class SchedulerJob {

	private static Logger log = LoggerFactory.getLogger(SchedulerJob.class);
	
	/**
	 * 保存所有的定时任务
	 * 使用ConcurrentHashMap保证线程安全
	 */
	private static Map<String, JobBean> JOB_MAP = new ConcurrentHashMap<>();
	
	@Autowired
	private TaskScheduler taskScheduler;
	
	/**
	 * 添加或修改任务
	 * @param job
	 */
	public void addOrUpdateJob(JobBean job) {
		log.info("addOrUpdateJob with job={}", job);
		if (job == null) {
			log.warn("addOrUpdateJob error. job is null");
			return;
		}
		if (StringUtils.isEmpty(job.getJobName())) {
			log.warn("addOrUpdateJob error. jobName is empty with job={}", job);
			return;
		}
		JobBean oldJob = JOB_MAP.get(job.getJobName());
		if (oldJob != null) {
			// 任务存在，判断是否修改了属性
			if (jobEquals(oldJob, job)) {
				// 任务属性一样，没有改变，则直接退出
				return;
			}
			//任务修改了时间属性，则停止掉以前的任务
			cancel(oldJob, false);
		}
		
		// 开始新任务
		startJob(job);
	}
	
	/**
	 * @Title: cancel
	 * @Description: 关闭任务
	 * @param job
	 * @param force
	 * @return
	 */
	public boolean cancel(JobBean job, boolean force) {
		// 停止任务
		boolean result = job.getScheduledFuture().cancel(force);
		
		// 从map中删除
		JOB_MAP.remove(job.getJobName());
		return result;
	}
	
	/**
	 * @Title: cancel
	 * @Description: 关闭定时任务
	 * @param jobName
	 * @param force
	 * @return
	 */
	public boolean cancel(String jobName, boolean force) {
		JobBean o = JOB_MAP.get(jobName);
		if (o == null) {
			return true;
		}
		return cancel(o, force);
	}
	
	/**
	 * @Title: startJob
	 * @Description: 开始新任务
	 * @param job
	 * @return
	 */
	private ScheduledFuture<?> startJob(JobBean job) {
		ScheduledFuture<?> future = null;
		SchedulerTypeEnum schedulerType = job.getScheduleType();
		if (SchedulerTypeEnum.ONCE.equals(schedulerType)) {
			// 一次任务
			future = taskScheduler.schedule(job.getTask(), job.getStartTime());
		} else if (SchedulerTypeEnum.CRON_TRIGGER.equals(schedulerType)) {
			// cron 表达式
			future = taskScheduler.schedule(job.getTask(), job.getTrigger());
		} else if (SchedulerTypeEnum.FIXED_RATE.equals(schedulerType)) {
			// 固定周期
			if (job.getStartTime() == null) {
				future = taskScheduler.scheduleAtFixedRate(job.getTask(), job.getPeriod());
			} else {
				future = taskScheduler.scheduleAtFixedRate(job.getTask(), job.getStartTime(), job.getPeriod());
			}
		} else if (SchedulerTypeEnum.FIXED_DELAY.equals(schedulerType)) {
			// 固定延迟
			if (job.getStartTime() == null) {
				future = taskScheduler.scheduleWithFixedDelay(job.getTask(), job.getDelay());
			} else {
				future = taskScheduler.scheduleWithFixedDelay(job.getTask(), job.getStartTime(), job.getDelay());
			}
		} else {
			return null;
		}
		
		//任务存入缓存
		job.setScheduledFuture(future);
		JOB_MAP.put(job.getJobName(), job);
		return future;
	}
	
	/**
	 * @Title: jobEquals
	 * @Description: 检查两个任务是否一样
	 * @param oldJob
	 * @param newJob
	 * @return
	 */
	private boolean jobEquals(JobBean oldJob, JobBean newJob) {
		if (oldJob.getScheduleType() != newJob.getScheduleType()) {
			// 任务调度方式改变了，则直接返回
			return false;
		}
		
		// 按照任务调度方式来分别判断
		SchedulerTypeEnum schedulerType = oldJob.getScheduleType();
		if (SchedulerTypeEnum.ONCE.equals(schedulerType)) {
			// 一次性任务，判断开始时间是否变化
			if (oldJob.getStartTime() == null) {
				return newJob.getStartTime() == null;
			} else {
				return oldJob.getStartTime().equals(newJob.getStartTime());
			}
		} else if (SchedulerTypeEnum.CRON_TRIGGER.equals(schedulerType)) {
			// cron 表达式
			if (oldJob.getTrigger() == null) {
				return newJob.getTrigger() == null;
			} else {
				return oldJob.getTrigger().equals(newJob.getTrigger());
			}
		} else if (SchedulerTypeEnum.FIXED_RATE.equals(schedulerType)) {
			// 固定周期
			if (oldJob.getStartTime() == null) {
				return newJob.getStartTime() == null && oldJob.getPeriod() == newJob.getPeriod();
			} else {
				return oldJob.getStartTime().equals(newJob.getStartTime()) && oldJob.getPeriod() == newJob.getPeriod();
			}
		} else if (SchedulerTypeEnum.FIXED_DELAY.equals(schedulerType)) {
			// 固定延迟
			if (oldJob.getStartTime() == null) {
				return newJob.getStartTime() == null && oldJob.getDelay() == newJob.getDelay();
			} else {
				return oldJob.getStartTime().equals(newJob.getStartTime()) && oldJob.getDelay() == newJob.getDelay();
			}
		}
		return false;
	}
}
