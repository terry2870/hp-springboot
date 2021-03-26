package com.hp.springboot.queue.model;

import java.util.concurrent.BlockingQueue;

import com.hp.springboot.common.bean.AbstractBean;

/**
 * 描述：队列的对象
 * 作者：黄平
 * 时间：2021年3月23日
 */
public class QueueExecutorBO extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7257129042392330250L;

	/**
	 * 队列名称
	 */
	private String queueName;
	
	/**
	 * 队列最大容量
	 */
	private int maxSize;
	
	/**
	 * 队列
	 */
	private BlockingQueue<Object> queue;
	
	/**
	 * 消费者数量
	 */
	private int consumerSize;
	
	public QueueResponseBO toQueueResponseBO() {
		QueueResponseBO resp = new QueueResponseBO();
		resp.setConsumerSize(consumerSize);
		resp.setCurrentSize(queue.size());
		resp.setMaxSize(maxSize);
		resp.setQueueName(queueName);
		return resp;
	}

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

	public BlockingQueue<Object> getQueue() {
		return queue;
	}

	public void setQueue(BlockingQueue<Object> queue) {
		this.queue = queue;
	}

	public int getConsumerSize() {
		return consumerSize;
	}

	public void setConsumerSize(int consumerSize) {
		this.consumerSize = consumerSize;
	}
}
