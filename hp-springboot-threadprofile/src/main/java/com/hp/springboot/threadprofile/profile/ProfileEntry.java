package com.hp.springboot.threadprofile.profile;

/**
 * 描述：线程进入堆栈的记录对象
 * 作者：黄平
 * 时间：2020-12-25
 */
public class ProfileEntry {


	/**
	   * 类的全限定名
	 */
	private String className;
	
	/**
	 * 方法名称
	 */
	private String method;
	
	/**
	   * 进入时间
	 */
	private long enterTime;
	
	/**
	   * 退出时间
	 */
	private long exitTime;
	
	/**
	   * 层级
	 */
	private int level;

	public ProfileEntry(String className, String method) {
		this.className = className;
		this.method = method;
		this.enterTime = System.currentTimeMillis();
		this.exitTime = this.enterTime;
		this.level = 0;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public long getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(long enterTime) {
		this.enterTime = enterTime;
	}

	public long getExitTime() {
		return exitTime;
	}

	public void setExitTime(long exitTime) {
		this.exitTime = exitTime;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("+---[").append(this.exitTime - this.enterTime).append("ms] - ").append(className)
				.append(".").append(method).append(" - [enter:").append(this.enterTime).append(",exit:")
				.append(this.exitTime).append("]");
		return sb.toString();
	}
}
