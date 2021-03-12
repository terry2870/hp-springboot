package com.hp.springboot.admin.convert;

import org.springframework.beans.BeanUtils;

import com.hp.springboot.admin.dal.model.SysConfig;
import com.hp.springboot.admin.model.request.SysConfigRequestBO;
import com.hp.springboot.admin.model.response.SysConfigResponseBO;
import com.hp.springboot.common.enums.StatusEnum;
import com.hp.springboot.common.util.DateUtil;

/**
 * 描述：
 * 作者：黄平
 * 时间：2021年2月24日
 */
public class SysConfigConvert {

	/**
	 * bo request --> dal
	 * @param bo
	 * @return
	 */
	public static SysConfig boRequest2Dal(SysConfigRequestBO bo) {
		if (bo == null) {
			return null;
		}
		SysConfig dal = new SysConfig();
		BeanUtils.copyProperties(bo, dal);
		return dal;
	}

	/**
	 * dal --> bo response
	 * @param dal
	 * @return
	 */
	public static SysConfigResponseBO dal2BOResponse(SysConfig dal) {
		if (dal == null) {
			return null;
		}
		SysConfigResponseBO bo = new SysConfigResponseBO();
		BeanUtils.copyProperties(dal, bo);
		bo.setCreateTimeStr(DateUtil.intToString(dal.getCreateTime()));
		bo.setStatusStr(StatusEnum.getTextByValue(dal.getStatus()));
		return bo;
	}
}
