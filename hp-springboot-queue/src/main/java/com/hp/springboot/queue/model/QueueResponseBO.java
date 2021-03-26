package com.hp.springboot.queue.model;

import com.hp.springboot.common.bean.AbstractBean;

/**
 * 描述：队列监控响应的实体类
 * 作者：黄平
 * 时间：2021年3月23日
 */
public class QueueResponseBO extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8517091372489035970L;

	/**
	 * 队列名称
	 */
	private String queueName;
	
	/**
	 * 队列最大容量
	 */
	private int maxSize;
	
	/**
	 * 队列当前数据总数
	 */
	private int currentSize;
	
	/**
	 * 消费者数量
	 */
	private int consumerSize;
	
	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public int getCurrentSize() {
		return currentSize;
	}

	public void setCurrentSize(int currentSize) {
		this.currentSize = currentSize;
	}

	public int getConsumerSize() {
		return consumerSize;
	}

	public void setConsumerSize(int consumerSize) {
		this.consumerSize = consumerSize;
	}
}
