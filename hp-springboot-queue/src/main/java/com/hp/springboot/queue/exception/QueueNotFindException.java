package com.hp.springboot.queue.exception;

/**
 * 描述：未找到queue异常
 * 作者：黄平
 * 时间：2021年3月22日
 */
public class QueueNotFindException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7653437755158747281L;

	
	public QueueNotFindException(String queueName) {
		super("the queue is not exists. with queueName=" + queueName);
	}
}
