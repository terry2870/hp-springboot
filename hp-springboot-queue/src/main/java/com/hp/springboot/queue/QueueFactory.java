package com.hp.springboot.queue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.springboot.queue.exception.QueueNotFindException;
import com.hp.springboot.queue.model.QueueExecutorBO;

/**
 * 描述：队列的工厂类。保存一些全局变量
 * 作者：黄平
 * 时间：2017年3月30日
 */
public class QueueFactory {
	
	private static Logger log = LoggerFactory.getLogger(QueueFactory.class);
		
	//存放所有的队列
	private Map<String, QueueExecutorBO> queueMap = new ConcurrentHashMap<>();

	// 饿汉模式，保证单例
	private static QueueFactory instance = new QueueFactory();
	
	/**
	 * 为了单例，私有化构造方法
	 */
	private QueueFactory() {}
	
	/**
	 * 获取实例
	 * @return
	 */
	public static QueueFactory getInstance() {
		return instance;
	}
	
	/**
	 * @Title: getQueue
	 * @Description: 获取队列
	 * @param queueName
	 * @return
	 */
	public QueueExecutorBO getQueue(String queueName) {
		//获取该队列
		QueueExecutorBO queue = queueMap.get(queueName);
		if (queue == null) {
			log.error("the queue is not find. please create this queue. with queueName={}", queueName);
			throw new QueueNotFindException(queueName);
		}
		return queue;
	}

	public Map<String, QueueExecutorBO> getQueueMap() {
		return queueMap;
	}

}
