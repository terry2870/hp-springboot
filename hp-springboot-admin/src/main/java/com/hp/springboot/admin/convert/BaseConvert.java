package com.hp.springboot.admin.convert;

import com.hp.springboot.admin.dal.model.BaseModel;
import com.hp.springboot.admin.util.SecuritySessionUtil;
import com.hp.springboot.common.util.DateUtil;

/**
 * 描述：基础的对象转换
 * 作者：黄平
 * 时间：2021年1月27日
 */
public class BaseConvert {

	/**
	 * @Title: convertModel
	 * @Description: 设置model的基础字段的初始值
	 * @param model
	 */
	public static void convertModel(BaseModel model) {
		int time = DateUtil.getCurrentTimeSeconds();
		model.setCreateTime(time);
		model.setUpdateTime(time);
		model.setCreateUserId(SecuritySessionUtil.getSessionData().getId());
	}
}
