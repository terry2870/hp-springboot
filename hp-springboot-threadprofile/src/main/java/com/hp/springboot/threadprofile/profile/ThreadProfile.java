package com.hp.springboot.threadprofile.profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述：记录线程调用堆栈，打印出调用链，记录调用时间
 * 作者：黄平
 * 时间：2020-12-24
 */
public class ThreadProfile {

	private static final String THREAD_PROFILE_LOGGER_NAME = "THREAD_PROFILE";
	
	/**
	   * 日志对象
	 */
	private static volatile Logger threadProfileLogger = null;
	
	/**
	   * 锁对象
	 */
	private static Object lock = new Object();
	
	/**
	   * 调用响应过长统计，默认情况下
	   * 超过200ms响应的请求会被输出到日志中
	 */
	public static int threadshold = 200;
	
	/**
	   * 调用堆栈信息记录，每次在start和stop时，都进行清理，避免出现引用仍然存在的问题
	 */
	private static final ThreadLocal<StackTrace> stackTrace = new ThreadLocal<StackTrace>();
	
	/**
	 * @Title: initLog  
	 * @Description: 初始化log对象
	 */
	private static void initLog() {
		if (threadProfileLogger != null) {
			return;
		}
		
		synchronized (lock) {
			if (threadProfileLogger != null) {
				return;
			}
			
			threadProfileLogger = LoggerFactory.getLogger(THREAD_PROFILE_LOGGER_NAME);
			
			//如果没有配置单独日志，则打印在公共日志文件里面
			if (threadProfileLogger == null) {
				threadProfileLogger = LoggerFactory.getLogger(ThreadProfile.class);
			}
		}
	}
	
	/**
	 * @Title: start  
	 * @Description: 开始统计当前线程的执行延时
	 * @param message
	 * @param threadshold
	 */
	public static void start(String message, int threadshold) {
		//设置超时时间
		ThreadProfile.threadshold = threadshold;

		//初始化日志对象
		initLog();
		
		//避免异常情况未清理，造成内存泄露
		stackTrace.remove();
		
		//创建当前线程的调用堆栈实例，用于记录关联路径的调用情况，关键路径通过全局的interceptor进行拦截
		stackTrace.set(new StackTrace(message));
	}
	
	/**
	   * 方法进入时记录线程的执行堆栈信息，拦截器调用
	 * @param className
	 * @param methodName
	 */
	public static void enter(String className, String methodName) {
		if (stackTrace.get() != null) {
			stackTrace.get().enter(new ProfileEntry(className, methodName));
		}
	}
	
	/**
	   * 方法return时记录线程的执行堆栈信息，拦截器调用
	 */
	public static void exit() {
		if (stackTrace.get() != null) {
			stackTrace.get().exit();
		}
	}
	
	/**
	 * @Title: stop  
	 * @Description: 结束统计，并打印结果, 整个调用的延时
	 */
	public static void stop() {
		StackTrace stack = stackTrace.get();
		if (stack == null) {
			return;
		}

		// 记录线程执行结束时间
		stack.end();

		// 打印调用堆栈
		dump();
		
		// 手动移除map
		stack.clear();

		// 清理threadlocal引用
		stackTrace.remove();
	}
	
	/**
	 * @Title: getDumpString  
	 * @Description: 获取线程调用堆栈
	 */
	private static void dump() {
		StackTrace stack = stackTrace.get();
		long delayTime = stack.duration();
		if (delayTime <= ThreadProfile.threadshold) {
			//耗时小于等于阈值
			return;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("Total Delay [").append(delayTime).append("ms] ").append(stack.getMessage()).append("\n");

		for (ProfileEntry entry : stack.getEntryList()) {
			if (entry == null) {
				continue;
			}

			for (int i = 0; i < entry.getLevel(); i++) {
				sb.append("    ");
			}
			sb.append(entry.toString()).append("\n");
		}

		// 大于200毫秒的，才需要warn
		threadProfileLogger.warn("Response time=[{}] exceed threadshold=[{}], requestUrl=[{}], stack info:\n[{}]\n\n\n",
				delayTime, ThreadProfile.threadshold, stack.getMessage(), sb.toString());
	}
}
