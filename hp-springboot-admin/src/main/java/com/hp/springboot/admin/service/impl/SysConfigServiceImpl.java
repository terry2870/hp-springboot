package com.hp.springboot.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.springboot.admin.convert.BaseConvert;
import com.hp.springboot.admin.convert.SysConfigConvert;
import com.hp.springboot.admin.dal.ISysConfigDAO;
import com.hp.springboot.admin.dal.model.SysConfig;
import com.hp.springboot.admin.model.request.SysConfigRequestBO;
import com.hp.springboot.admin.model.response.SysConfigResponseBO;
import com.hp.springboot.admin.service.ISysConfigService;
import com.hp.springboot.common.bean.PageResponse;
import com.hp.springboot.common.enums.StatusEnum;
import com.hp.springboot.common.exception.CommonException;
import com.hp.springboot.common.util.DateUtil;
import com.hp.springboot.common.util.NumberUtil;
import com.hp.springboot.database.bean.PageModel;
import com.hp.springboot.database.bean.PageRequest;
import com.hp.springboot.database.bean.SQLBuilders;
import com.hp.springboot.database.bean.SQLWhere;

/**
 * 描述：系统配置实现类
 * 作者：黄平
 * 时间：2021年2月25日
 */
@Service
public class SysConfigServiceImpl implements ISysConfigService {

	private static Logger log = LoggerFactory.getLogger(SysConfigServiceImpl.class);
	
	@Autowired
	private ISysConfigDAO sysConfigDAO;
	
	@Override
	public void saveSysConfig(SysConfigRequestBO request) {
		log.info("saveSysConfig with request={}", request);
		SysConfig dal = SysConfigConvert.boRequest2Dal(request);
		
		//检查唯一性
		saveSysConfigCheck(request);
		
		if (NumberUtil.isNullOrZero(request.getId())) {
			//新增
			BaseConvert.convertModel(dal);
			sysConfigDAO.insertSelective(dal);
		} else {
			//修改
			dal.setUpdateTime(DateUtil.getCurrentTimeSeconds());
			
			sysConfigDAO.updateByPrimaryKeySelective(dal);
		}
	}

	@Override
	public PageResponse<SysConfigResponseBO> querySysConfigPageList(SysConfigRequestBO request,
			PageRequest pageRequest) {
		log.info("querySysConfigPageList with request={}, pageRequest={}", request, pageRequest);
		PageModel page = pageRequest.toPageModel();

		List<SQLWhere> whereList = SQLWhere.builder()
				.eq("config_key", request.getConfigKey())
				.like("config_name", request.getConfigName())
				.eq("status", request.getStatus())
				.build()
				;
		//查询总数
		int total = sysConfigDAO.selectCount(whereList);
		if (total == 0) {
			log.warn("querySysConfigPageList error. with total=0. with request={}", request);
			return null;
		}
		PageResponse<SysConfigResponseBO> resp = new PageResponse<>();
		resp.setCurrentPage(pageRequest.getPage());
		resp.setPageSize(pageRequest.getLimit());
		resp.setTotal(total);

		SQLBuilders builders = SQLBuilders.create()
				.withWhere(whereList)
				.withPage(page)
				;
		//查询列表
		List<SysConfig> list = sysConfigDAO.selectList(builders);
		if (CollectionUtils.isEmpty(list)) {
			log.warn("querySysConfigPageList error. with list is empty. with request={}", request);
			return resp;
		}

		List<SysConfigResponseBO> respList = new ArrayList<>(list.size());
		for (SysConfig a : list) {
			respList.add(SysConfigConvert.dal2BOResponse(a));
		}
		log.info("querySysConfigPageList success. with request={}", request);
		return new PageResponse<>(total, respList, page.getCurrentPage(), page.getPageSize());
	}

	@Override
	public void deleteSysConfig(Integer id) {
		log.info("deleteSysConfig with id={}", id);
		sysConfigDAO.deleteByPrimaryKey(id);
	}

	@Override
	public SysConfigResponseBO querySysConfigById(Integer id) {
		log.info("querySysConfigById with id={}", id);
		SysConfig dal = sysConfigDAO.selectByPrimaryKey(id);
		if (dal == null) {
			log.warn("querySysConfigById error. with result is null. with id={}", id);
			return null;
		}
		return SysConfigConvert.dal2BOResponse(dal);
	}
	
	@Override
	public void changeSysConfigStatus(Integer id, StatusEnum status) {
		log.info("changeSysConfigStatus with id={}, status={}", id, status);
		
		SysConfig dal = new SysConfig();
		dal.setStatus(status.getIntegerValue());
		dal.setId(id);
		dal.setUpdateTime(DateUtil.getCurrentTimeSeconds());
		sysConfigDAO.updateByPrimaryKeySelective(dal);
	}
	
	/**
	 * @Title: saveSysConfigCheck
	 * @Description: 检查参数
	 * @param request
	 */
	private void saveSysConfigCheck(SysConfigRequestBO request) {
		//检查config_key字段是否重复
		SysConfig config = sysConfigDAO.selectOne(SQLBuilders.create()
				.withWhere(SQLWhere.builder()
						.eq("config_key", request.getConfigKey())
						.build())
				);
		if (config == null) {
			return;
		}
		
		if (NumberUtil.isNullOrZero(request.getId())) {
			//新增
			log.warn("saveSysConfig error. with configKey is exists. with request={}", request);
			throw new CommonException(500, "配置的key已经存在");
		} else {
			//修改
			if (!config.getId().equals(request.getId())) {
				log.warn("saveSysConfig error. with configKey is exists. with request={}", request);
				throw new CommonException(500, "配置的key已经存在");
			}
		}
		if (StringUtils.equals(config.getConfigKey(), request.getConfigKey())) {
			
		}
	}
}
