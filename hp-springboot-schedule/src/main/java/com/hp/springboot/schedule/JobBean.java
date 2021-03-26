package com.hp.springboot.schedule;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;

import com.hp.springboot.common.util.DateUtil;
import com.hp.springboot.schedule.enums.SchedulerTypeEnum;

/**
 * 
 * 描述：调度任务的对象
 * 作者：黄平
 * 时间：2016年6月18日
 */
public class JobBean implements Serializable {

	private static final long serialVersionUID = -156296402817061433L;

	/**
	 * 任务名称（必须唯一）
	 */
	private String jobName;

	/**
	 * 任务调度方式
	 */
	private SchedulerTypeEnum scheduleType;
	
	/**
	 * trigger任务的触发器
	 */
	private Trigger trigger;
	
	/**
	 * 开始时间
	 */
	private Date startTime;
	
	/**
	 * 固定周期（毫秒）
	 */
	private long period;
	
	/**
	 * 固定延迟（毫秒）
	 */
	private long delay;
	
	/**
	 * 任务线程
	 */
	private Runnable task;
	
	/**
	 * 定时任务返回对象
	 */
	private ScheduledFuture<?> scheduledFuture;

	private JobBean() {}
	
	/**
	 * @Title: createOnceJob
	 * @Description: 创建只执行一次的任务对象
	 * @param jobName
	 * @param startTime
	 * @param task
	 * @return
	 */
	public static JobBean createOnceJob(String jobName, Date startTime, Runnable task) {
		JobBean job = new JobBean();
		job.setScheduleType(SchedulerTypeEnum.ONCE);
		job.setJobName(jobName);
		job.setStartTime(startTime);
		job.setTask(task);
		return job;
	}
	
	/**
	 * @Title: createOnceJob
	 * @Description: 创建只执行一次的任务对象
	 * @param jobName
	 * @param startTime
	 * @param task
	 * @return
	 */
	public static JobBean createOnceJob(String jobName, long startTime, Runnable task) {
		Date stime = DateUtil.longToDate(startTime);
		return createOnceJob(jobName, stime, task);
	}
	
	/**
	 * @Title: createCronJob
	 * @Description: 创建触发器任务对象
	 * @param jobName
	 * @param trigger
	 * @param task
	 * @return
	 */
	public static JobBean createCronJob(String jobName, Trigger trigger, Runnable task) {
		JobBean job = new JobBean();
		job.setScheduleType(SchedulerTypeEnum.CRON_TRIGGER);
		job.setJobName(jobName);
		job.setTask(task);
		job.setTrigger(trigger);
		return job;
	}
	
	/**
	 * @Title: createCronJob
	 * @Description: 创建触发器任务对象
	 * @param jobName
	 * @param cronExpression
	 * @param task
	 * @return
	 */
	public static JobBean createCronJob(String jobName, String cronExpression, Runnable task) {
		Trigger trigger = new CronTrigger(cronExpression);
		return createCronJob(jobName, trigger, task);
	}
	
	/**
	 * @Title: createFixedRateJob
	 * @Description: 创建固定周期任务对象
	 * @param jobName
	 * @param startTime
	 * @param period
	 * @param task
	 * @return
	 */
	public static JobBean createFixedRateJob(String jobName, Date startTime, long period, Runnable task) {
		JobBean job = new JobBean();
		job.setScheduleType(SchedulerTypeEnum.FIXED_RATE);
		job.setJobName(jobName);
		job.setTask(task);
		job.setStartTime(startTime);
		job.setPeriod(period);
		return job;
	}
	
	/**
	 * @Title: createFixedRateJob
	 * @Description: 创建固定周期任务对象
	 * @param jobName
	 * @param startTime
	 * @param period
	 * @param task
	 * @return
	 */
	public static JobBean createFixedRateJob(String jobName, long startTime, long period, Runnable task) {
		Date stime = startTime == 0 ? null : DateUtil.longToDate(startTime);
		return createFixedRateJob(jobName, stime, period, task);
	}
	
	/**
	 * @Title: createFixedRateJob
	 * @Description: 创建固定周期任务对象
	 * @param jobName
	 * @param period
	 * @param task
	 * @return
	 */
	public static JobBean createFixedRateJob(String jobName, long period, Runnable task) {
		return createFixedRateJob(jobName, 0, period, task);
	}
	
	/**
	 * @Title: createFixedDelayJob
	 * @Description: 创建固定延迟时间的任务对象
	 * @param jobName
	 * @param startTime
	 * @param delay
	 * @param task
	 * @return
	 */
	public static JobBean createFixedDelayJob(String jobName, Date startTime, long delay, Runnable task) {
		JobBean job = new JobBean();
		job.setScheduleType(SchedulerTypeEnum.FIXED_DELAY);
		job.setJobName(jobName);
		job.setTask(task);
		job.setStartTime(startTime);
		job.setDelay(delay);
		return job;
	}
	
	/**
	 * @Title: createFixedDelayJob
	 * @Description: 创建固定延迟时间的任务对象
	 * @param jobName
	 * @param startTime
	 * @param delay
	 * @param task
	 * @return
	 */
	public static JobBean createFixedDelayJob(String jobName, long startTime, long delay, Runnable task) {
		Date stime = startTime == 0 ? null : DateUtil.longToDate(startTime);
		return createFixedDelayJob(jobName, stime, delay, task);
	}
	
	/**
	 * @Title: createFixedDelayJob
	 * @Description: 创建固定延迟时间的任务对象
	 * @param jobName
	 * @param delay
	 * @param task
	 * @return
	 */
	public static JobBean createFixedDelayJob(String jobName, long delay, Runnable task) {
		return createFixedDelayJob(jobName, 0, delay, task);
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public SchedulerTypeEnum getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(SchedulerTypeEnum scheduleType) {
		this.scheduleType = scheduleType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public ScheduledFuture<?> getScheduledFuture() {
		return scheduledFuture;
	}

	public void setScheduledFuture(ScheduledFuture<?> scheduledFuture) {
		this.scheduledFuture = scheduledFuture;
	}

	public Runnable getTask() {
		return task;
	}

	public void setTask(Runnable task) {
		this.task = task;
	}

	public Trigger getTrigger() {
		return trigger;
	}

	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}

	@Override
	public String toString() {
		return "JobBean [jobName=" + jobName + ", scheduleType=" + scheduleType + ", trigger=" + trigger
				+ ", startTime=" + startTime + ", period=" + period + ", delay=" + delay + ", task=" + task + "]";
	}

}
