package com.hp.springboot.queue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.springboot.queue.exception.QueueNameExistsException;
import com.hp.springboot.queue.model.QueueExecutorBO;

/**
 * 描述：队列的消费者，抽象类，子类继承
 * 作者：黄平
 * 时间：2017年3月30日
 */
public abstract class AbstractQueueConsumer {

	private static Logger log = LoggerFactory.getLogger(AbstractQueueConsumer.class);
	
	/**
	 * @Title: getQueueName
	 * @Description: 获取队列的名称
	 * @return
	 */
	public abstract String getQueueName();
	
	/**
	 * @Title: execute
	 * @Description: 具体处理方法
	 * @param message
	 */
	protected abstract void execute(Object message);
	
	/**
	 * @Title: getConsumerSize
	 * @Description: 获取消费者数量（默认一个）
	 * @return
	 */
	public int getConsumerSize() {
		return 1;
	}
	
	/**
	 * @Title: getQueueMaxSize
	 * @Description: 获取该队列的最大容量
	 * @return
	 */
	public int getQueueMaxSize() {
		return 5000;
	}
	
	/**
	 * @Title: getRejectedExecutionHandler
	 * @Description: 获取线程池拒绝策略
	 * 默认直接拒绝，抛出异常
	 * @return
	 */
	public RejectedExecutionHandler getRejectedExecutionHandler() {
		return new AbortPolicy();
	}
	
	/**
	 * @Title: onException
	 * @Description: 当处理失败时，执行
	 * @param message
	 * @param e
	 */
	public void onException(Object message, Exception e) {
		// do notiong
	}
	
	/**
	 * 消费
	 */
	@PostConstruct
	protected void init() {
		String queueName = getQueueName();
		log.info("init HPQueueConsumer start. with queueName={}", queueName);
		if (StringUtils.isEmpty(queueName)) {
			log.warn("init error. with queueName is empty.");
			return;
		}
		
		// 创建线程池处理消费者
		ThreadPoolExecutor exe = new ThreadPoolExecutor(getConsumerSize(), 
				getConsumerSize(), 
				0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(),
				getRejectedExecutionHandler());
		
		// 创建队列，然后返回初始化后的队列
		QueueExecutorBO queue = createQueue(queueName, getQueueMaxSize());
		
		// 循环，创建多线程执行任务
		for (int i = 0; i < getConsumerSize(); i++) {
			exe.execute(new Runnable() {
				
				@Override
				public void run() {
					while (true) {
						Object message = null;
						try {
							// 从队列里面获取一条消息，如果为空，则阻塞
							message = queue.getQueue().take();
							
							// 执行任务
							execute(message);
						} catch (Exception e) {
							log.error("execute message from {} error", queueName, e);
							onException(message, e);
						}
					}
				}
			});
		}
		log.info("init HPQueueConsumer success. with queueName={}", queueName);
	}
	
	/**
	 * @Title: initQueue
	 * @Description:  创建新的队里
	 * 返回初始化后的队列
	 * @param queueName
	 * @param queueSize
	 * @return
	 */
	private QueueExecutorBO createQueue(String queueName, int queueSize) {
		QueueFactory factory = QueueFactory.getInstance();
		QueueExecutorBO queue = factory.getQueueMap().get(queueName);
		if (queue != null) {
			// 创建时，如果队列已经存在，则抛异常
			throw new QueueNameExistsException(queueName);
		}
		
		// 新生成一个队列对象
		queue = new QueueExecutorBO();
		queue.setMaxSize(queueSize);
		queue.setQueue(new LinkedBlockingQueue<>(queueSize));
		queue.setQueueName(queueName);
		queue.setConsumerSize(getConsumerSize());
		
		// 放入工厂类
		factory.getQueueMap().put(queueName, queue);
		return queue;
	}
}
