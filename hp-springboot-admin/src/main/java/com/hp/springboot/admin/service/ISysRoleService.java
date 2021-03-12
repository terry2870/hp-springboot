package com.hp.springboot.admin.service;

import com.hp.springboot.admin.model.request.SysRoleRequestBO;
import com.hp.springboot.admin.model.response.SysRoleResponseBO;
import com.hp.springboot.common.bean.PageResponse;
import com.hp.springboot.database.bean.PageRequest;

/**
 * 系统角色表业务接口定义
 * @author huangping
 * 2018-08-06
 */
public interface ISysRoleService {

	/**
	 * 保存系统角色表
	 * @param request
	 */
	void saveSysRole(SysRoleRequestBO request);

	/**
	 * 查询系统角色表列表
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	PageResponse<SysRoleResponseBO> querySysRolePageList(SysRoleRequestBO request, PageRequest pageRequest);

	/**
	 * 删除系统角色表
	 * @param id
	 */
	void deleteSysRole(Integer id);

	/**
	 * 根据id，查询系统角色表
	 * @param id
	 * @return
	 */
	SysRoleResponseBO querySysRoleById(Integer id);

}
