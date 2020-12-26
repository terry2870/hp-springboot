package com.hp.springboot.threadprofile.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import com.hp.springboot.threadprofile.profile.ThreadProfile;

/**
 * 描述：所有的工程都会加载这个 服务的interceptor，用来做慢的服务调用统计
 * 作者：黄平
 * 时间：2020-12-24
 */
public class ThreadProfileInterceptor implements HandlerInterceptor {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	// 多少毫秒会打印异常日志
	private int threshold = 200;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 开始统计线程调用堆栈
		ThreadProfile.start(request.getRequestURI(), this.threshold);
		
		// 进入方法
		ThreadProfile.enter(this.getClass().getName(), "preHandle");
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 结束调用
		ThreadProfile.exit();
		
		// 打印堆栈信息
		ThreadProfile.stop();
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
}
