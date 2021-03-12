package com.hp.springboot.admin.convert;

import org.springframework.beans.BeanUtils;

import com.hp.springboot.admin.dal.model.SysRole;
import com.hp.springboot.admin.model.request.SysRoleRequestBO;
import com.hp.springboot.admin.model.response.SysRoleResponseBO;
import com.hp.springboot.common.enums.StatusEnum;
import com.hp.springboot.common.util.DateUtil;

/**
 * 对象转换类
 * @author huangping
 * 2018-08-06
 */

public class SysRoleConvert {

	/**
	 * bo request --> dal
	 * @param bo
	 * @return
	 */
	public static SysRole boRequest2Dal(SysRoleRequestBO bo) {
		if (bo == null) {
			return null;
		}
		SysRole dal = new SysRole();
		BeanUtils.copyProperties(bo, dal);
		return dal;
	}

	/**
	 * dal --> bo response
	 * @param dal
	 * @return
	 */
	public static SysRoleResponseBO dal2BOResponse(SysRole dal) {
		if (dal == null) {
			return null;
		}
		SysRoleResponseBO bo = new SysRoleResponseBO();
		BeanUtils.copyProperties(dal, bo);
		bo.setStatusStr(StatusEnum.getTextByValue(dal.getStatus()));
		bo.setCreateTimeStr(DateUtil.intToString(dal.getCreateTime()));
		return bo;
	}
}
