package com.hp.springboot.scheduler.enums;

/**
 * 描述：任务状态
 * 作者：黄平
 * 时间：2021年3月26日
 */
public enum JobStatusEnum {

	WAITING(1, "待执行"),
	RUNNING(2, "执行中"),
	;
	
	private int value;
	private String text;
	
	private JobStatusEnum(int value, String text) {
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
