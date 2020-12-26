package com.hp.springboot.threadprofile.profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 描述：线程进入后的对象
 * 作者：黄平
 * 时间：2020-12-25
 */
public class StackTrace {
	
	/**
	   * 线程调用栈列表
	 */
	private List<ProfileEntry> entryList = new ArrayList<ProfileEntry>(32);
	
	/**
	   * 线程调用栈对象
	 */
	private Stack<ProfileEntry> entryStack = new Stack<>();
	
	/**
	   * 请求的url
	 */
	private String message;
	
	/**
	   * 进入时间
	 */
	private long beginTime;
	
	/**
	   * 退出时间
	 */
	private long endTime;
	
	/**
	   * 当前调用堆栈层级
	 */
	private int currentStackLevel;
	
	public StackTrace(String message) {
		this.beginTime = System.currentTimeMillis();
		this.endTime = beginTime;
		this.message = message;
		this.currentStackLevel = 0;
	}
	
	/**
	   * 记录方法的进入
	 * @param entry
	 */
	public void enter(ProfileEntry entry) {
		entry.setLevel(++this.currentStackLevel);
		entryList.add(entry);
		entryStack.push(entry);
	}
	
	/**
	 * @Title: exit  
	 * @Description: 记录方法的返回
	 */
	public void exit() {
		this.currentStackLevel--;
		ProfileEntry entryPop = entryStack.pop();
		entryPop.setExitTime(System.currentTimeMillis());
	}
	
	/**
	* @Title: end  
	* @Description: 线程执行结束
	*/
	public void end() {
		this.endTime = System.currentTimeMillis();
	}
	
	/**
	* @Title: clear  
	* @Description: 线程结束，清理数据
	*/
	public void clear() {
		if (entryList != null) {
			entryList.clear();
		}
		if (entryStack != null) {
			entryStack.clear();
		}
	}
	
	/**
	 * @Title: duration  
	 * @Description: 线程执行时长
	 * @return
	 */
	public long duration() {
		return endTime - beginTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ProfileEntry> getEntryList() {
		return entryList;
	}

	public void setEntryList(List<ProfileEntry> entryList) {
		this.entryList = entryList;
	}
}
