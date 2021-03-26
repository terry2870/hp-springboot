package com.hp.springboot.queue.exception;

/**
 * 描述：queue名称重复异常
 * 作者：黄平
 * 时间：2021年3月22日
 */
public class QueueNameExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7653437755158747281L;

	
	public QueueNameExistsException(String queueName) {
		super("the queue is exists. with queueName=" + queueName);
	}
}
