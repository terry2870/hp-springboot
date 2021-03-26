package com.hp.springboot.queue.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hp.springboot.queue.QueueFactory;
import com.hp.springboot.queue.model.QueueExecutorBO;
import com.hp.springboot.queue.model.QueueResponseBO;

/**
 * 描述：展示当前队列的使用情况
 * 作者：黄平
 * 时间：2021年3月22日
 */
@RestController
public class QueueController {

	/**
	 * @Title: queueList
	 * @Description: 队列监控
	 * @param queueName
	 * @return
	 */
	@RequestMapping("/queueList")
	public ModelAndView queueList(String queueName) {
		// 通过工厂类，获取所有的队列
		Map<String, QueueExecutorBO> queueMap = QueueFactory.getInstance().getQueueMap();
		
		List<QueueResponseBO> queueList = new ArrayList<>();
		
		if (StringUtils.isNotBlank(queueName)) {
			// 如果传入queueName，则只查询这个queue的数据
			QueueExecutorBO queue = queueMap.get(queueName);
			if (queue != null) {
				queueList.add(queue.toQueueResponseBO());
			}
		} else {
			// 返回全部的queue
			// 遍历map，放入queueList中
			for (Entry<String, QueueExecutorBO> entry : queueMap.entrySet()) {
				queueList.add(entry.getValue().toQueueResponseBO());
			}
		}
		
		// 按照key排序
		Collections.sort(queueList, new Comparator<QueueResponseBO>() {
			@Override
			public int compare(QueueResponseBO o1, QueueResponseBO o2) {
				return o1.getQueueName().compareTo(o2.getQueueName());
			}
		});
		Map<String, Object> map = new HashMap<>();
		map.put("queueList", queueList);
		return new ModelAndView("queueList", map);
	}
}
