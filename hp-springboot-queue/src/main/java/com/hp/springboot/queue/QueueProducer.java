package com.hp.springboot.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.springboot.queue.model.QueueExecutorBO;

/**
 * 描述：生产者
 * 作者：黄平
 * 时间：2017年3月30日
 */
public class QueueProducer {

	
	private static Logger log = LoggerFactory.getLogger(QueueProducer.class);
	
	/**
	 * @Title: send
	 * @Description: 发送消息
	 * @param queueName
	 * @param message
	 */
	public void send(String queueName, Object message) {
		QueueExecutorBO queue = QueueFactory.getInstance().getQueue(queueName);
		try {
			//如果队列有空间，则正常放入；如果队列满了，就阻塞在这里
			queue.getQueue().put(message);
		} catch (InterruptedException e) {
			log.error("put message into queue error. with queueName={}", queueName, e);
		}
	}
	
}
