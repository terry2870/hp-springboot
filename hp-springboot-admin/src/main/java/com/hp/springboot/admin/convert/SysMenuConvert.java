package com.hp.springboot.admin.convert;

import org.springframework.beans.BeanUtils;

import com.hp.springboot.admin.dal.model.SysMenu;
import com.hp.springboot.admin.model.request.SysMenuRequestBO;
import com.hp.springboot.admin.model.response.SysMenuResponseBO;
import com.hp.springboot.common.enums.StatusEnum;
import com.hp.springboot.common.util.DateUtil;

/**
 * 对象转换类
 * @author huangping
 * 2018-08-06
 */

public class SysMenuConvert {

	/**
	 * bo request --> dal
	 * @param bo
	 * @return
	 */
	public static SysMenu boRequest2Dal(SysMenuRequestBO bo) {
		if (bo == null) {
			return null;
		}
		SysMenu dal = new SysMenu();
		BeanUtils.copyProperties(bo, dal);
		return dal;
	}

	/**
	 * dal --> bo response
	 * @param dal
	 * @return
	 */
	public static SysMenuResponseBO dal2BOResponse(SysMenu dal) {
		if (dal == null) {
			return null;
		}
		SysMenuResponseBO bo = new SysMenuResponseBO();
		BeanUtils.copyProperties(dal, bo);
		bo.setStatusStr(StatusEnum.getTextByValue(dal.getStatus()));
		bo.setCreateTimeStr(DateUtil.intToString(dal.getCreateTime()));
		return bo;
	}
}
