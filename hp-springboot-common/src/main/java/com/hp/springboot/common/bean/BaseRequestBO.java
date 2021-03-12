/**
 * 
 */
package com.hp.springboot.common.bean;

/**
 * @author huangping
 * 2016年9月4日 上午12:24:26
 */
public class BaseRequestBO extends AbstractBean {

	private static final long serialVersionUID = 7863585169540166691L;

	
	private String queryStartDate;
	private String queryEndDate;
	
	private Integer status;
	
	public String getQueryStartDate() {
		return queryStartDate;
	}
	public void setQueryStartDate(String queryStartDate) {
		this.queryStartDate = queryStartDate;
	}
	public String getQueryEndDate() {
		return queryEndDate;
	}
	public void setQueryEndDate(String queryEndDate) {
		this.queryEndDate = queryEndDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
