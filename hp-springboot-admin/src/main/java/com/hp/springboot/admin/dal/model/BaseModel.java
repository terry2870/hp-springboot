package com.hp.springboot.admin.dal.model;

import com.hp.springboot.common.bean.AbstractBean;

/**
 * 描述：基础model
 * 作者：黄平
 * 时间：2021年1月27日
 */
public class BaseModel extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3311559086250144610L;

	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 创建者id
	 */
	private Integer createUserId;
	
	/**
	 * 创建时间
	 */
	private Integer createTime;
	
	/**
	 * 更新时间
	 */
	private Integer updateTime;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}
}
