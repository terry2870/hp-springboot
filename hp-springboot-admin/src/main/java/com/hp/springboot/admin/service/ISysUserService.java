package com.hp.springboot.admin.service;

import com.hp.springboot.admin.model.request.SysUserRequestBO;
import com.hp.springboot.admin.model.response.SysUserResponseBO;
import com.hp.springboot.common.bean.PageResponse;
import com.hp.springboot.database.bean.PageRequest;

/**
 * 描述：用户的service
 * 作者：黄平
 * 时间：2021年1月12日
 */
public interface ISysUserService {

	/**
	 * @Title: saveSysUser
	 * @Description: 保存系统用户表
	 * @param request
	 */
	void saveSysUser(SysUserRequestBO request);

	/**
	 * @Title: querySysUserPageList
	 * @Description: 查询系统用户表列表
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	PageResponse<SysUserResponseBO> querySysUserPageList(SysUserRequestBO request, PageRequest pageRequest);

	/**
	 * @Title: deleteSysUser
	 * @Description: 删除系统用户表
	 * @param id
	 */
	void deleteSysUser(Integer id);

	/**
	 * @Title: querySysUserById
	 * @Description: 根据id，查询系统用户表
	 * @param id
	 * @return
	 */
	SysUserResponseBO querySysUserById(Integer id);
}
