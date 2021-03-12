package com.hp.springboot.admin.service;

import com.hp.springboot.admin.model.request.SysUserRequestBO;
import com.hp.springboot.admin.model.response.SysUserResponseBO;
import com.hp.springboot.common.bean.PageResponse;
import com.hp.springboot.common.enums.StatusEnum;
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
	 * @Title: changeUserStatus
	 * @Description: 修改用户状态
	 * @param id
	 * @param status
	 */
	void changeUserStatus(Integer id, StatusEnum status);

	/**
	 * @Title: querySysUserById
	 * @Description: 根据id，查询系统用户表
	 * @param id
	 * @return
	 */
	SysUserResponseBO querySysUserById(Integer id);
	
	/**
	 * @Title: modifyPwd
	 * @Description: 修改密码
	 * @param userId
	 * @param oldPwd
	 * @param newPwd
	 */
	void modifyPwd(Integer userId, String oldPwd, String newPwd);
	
	/**
	 * @Title: updateLastLoginTime
	 * @Description: 更新最新登录时间
	 * @param userId
	 */
	void updateLastLoginTime(Integer userId);
}
