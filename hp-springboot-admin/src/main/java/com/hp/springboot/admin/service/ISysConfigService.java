package com.hp.springboot.admin.service;

import com.hp.springboot.admin.model.request.SysConfigRequestBO;
import com.hp.springboot.admin.model.response.SysConfigResponseBO;
import com.hp.springboot.common.bean.PageResponse;
import com.hp.springboot.common.enums.StatusEnum;
import com.hp.springboot.database.bean.PageRequest;

/**
 * 描述：系统配置的接口
 * 作者：黄平
 * 时间：2021年2月24日
 */
public interface ISysConfigService {

	/**
	 * @Title: saveSysConfig
	 * @Description: 保存系统配置
	 * @param request
	 */
	void saveSysConfig(SysConfigRequestBO request);

	/**
	 * @Title: querySysConfigPageList
	 * @Description: 查询系统配置表列表
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	PageResponse<SysConfigResponseBO> querySysConfigPageList(SysConfigRequestBO request, PageRequest pageRequest);

	/**
	 * @Title: deleteSysConfig
	 * @Description: 删除系统配置表
	 * @param id
	 */
	void deleteSysConfig(Integer id);

	/**
	 * @Title: querySysConfigById
	 * @Description: 根据id，查询系统配置表
	 * @param id
	 * @return
	 */
	SysConfigResponseBO querySysConfigById(Integer id);
	
	/**
	 * @Title: changeSysConfigStatus
	 * @Description: 修改配置状态
	 * @param id
	 * @param status
	 */
	void changeSysConfigStatus(Integer id, StatusEnum status);
}
