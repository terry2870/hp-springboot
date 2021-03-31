package com.hp.springboot.scheduler.enums;

/**
 * 描述：任务调度方式
 * 作者：黄平
 * 时间：2021年3月26日
 */
public enum SchedulerTypeEnum {

	ONCE(1, "单次执行"),
	CRON_TRIGGER(2, "cron表达式"),
	FIXED_RATE(3, "固定周期"),
	FIXED_DELAY(4, "固定延迟")
	;
	
	private int value;
	private String text;
	
	private SchedulerTypeEnum(int value, String text) {
		this.value = value;
		this.text = text;
	}

	public int getValue() {
		return value;
	}

	public String getText() {
		return text;
	}
}
