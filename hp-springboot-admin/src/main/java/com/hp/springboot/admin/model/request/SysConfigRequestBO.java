package com.hp.springboot.admin.model.request;

import com.hp.springboot.common.bean.BaseRequestBO;

/**
 * 描述：
 * 作者：黄平
 * 时间：2021年2月24日
 */
public class SysConfigRequestBO extends BaseRequestBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3787101709336139995L;

	private Integer id;
	
	/**
	 * 配置名称
	 */
	private String configName;
	
	/**
	 * 配置描述
	 */
	private String configDesc;
	
	/**
	 * 配置的key
	 */
	private String configKey;
	
	/**
	 * 配置的值
	 */
	private String configValue;
	
	/**
	 * 配置数据类型
	 */
	private Integer configValueType;
	
	/**
	 * 配置数据类型的详细信息
	 */
	private String configValueTypeDesc;
	
	/**
	 * 状态
	 */
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigDesc() {
		return configDesc;
	}

	public void setConfigDesc(String configDesc) {
		this.configDesc = configDesc;
	}

	public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public Integer getConfigValueType() {
		return configValueType;
	}

	public void setConfigValueType(Integer configValueType) {
		this.configValueType = configValueType;
	}

	public String getConfigValueTypeDesc() {
		return configValueTypeDesc;
	}

	public void setConfigValueTypeDesc(String configValueTypeDesc) {
		this.configValueTypeDesc = configValueTypeDesc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
