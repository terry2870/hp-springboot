package com.hp.springboot.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hp.springboot.admin.convert.BaseConvert;
import com.hp.springboot.admin.convert.SysRoleConvert;
import com.hp.springboot.admin.dal.ISysRoleDAO;
import com.hp.springboot.admin.dal.model.SysRole;
import com.hp.springboot.admin.model.request.SysRoleRequestBO;
import com.hp.springboot.admin.model.response.SysRoleResponseBO;
import com.hp.springboot.admin.service.ISysRoleMenuService;
import com.hp.springboot.admin.service.ISysRoleService;
import com.hp.springboot.admin.service.ISysUserRoleService;
import com.hp.springboot.common.bean.PageResponse;
import com.hp.springboot.common.exception.CommonException;
import com.hp.springboot.common.util.DateUtil;
import com.hp.springboot.common.util.NumberUtil;
import com.hp.springboot.database.annotation.UseDatabase;
import com.hp.springboot.database.bean.PageModel;
import com.hp.springboot.database.bean.PageRequest;
import com.hp.springboot.database.bean.SQLBuilders;
import com.hp.springboot.database.bean.SQLWhere;

/**
 * 系统角色表业务操作接口实现
 * @author huangping
 * 2018-08-06
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {

	private static Logger log = LoggerFactory.getLogger(SysRoleServiceImpl.class);

	@Autowired
	private ISysRoleDAO sysRoleDAO;
	@Autowired
	private ISysUserRoleService sysUserRoleService;
	@Autowired
	private ISysRoleMenuService sysRoleMenuService;

	@Override
	public void saveSysRole(SysRoleRequestBO request) {
		log.info("saveSysRole with request={}", request);
		SysRole dal = SysRoleConvert.boRequest2Dal(request);
		
		//检查参数
		saveSysRoleCheck(request);
		
		if (request.getId() == null || request.getId().intValue() == 0) {
			//新增
			BaseConvert.convertModel(dal);
			sysRoleDAO.insertSelective(dal);
		} else {
			//修改
			dal.setUpdateTime(DateUtil.getCurrentTimeSeconds());
			sysRoleDAO.updateByPrimaryKeySelective(dal);
		}
	}

	@Override
	public PageResponse<SysRoleResponseBO> querySysRolePageList(SysRoleRequestBO request, PageRequest pageRequest) {
		log.info("querySysRolePageList with request={}", request);
		PageModel page = pageRequest.toPageModel();

		List<SQLWhere> whereList = SQLWhere.builder()
				.eq("id", request.getId())
				.like("role_name", request.getRoleName())
				.build();
		//查询总数
		int total = sysRoleDAO.selectCount(whereList);
		if (total == 0) {
			log.warn("querySysRolePageList error. with total=0. with request={}", request);
			return null;
		}
		PageResponse<SysRoleResponseBO> resp = new PageResponse<>();
		resp.setCurrentPage(pageRequest.getPage());
		resp.setPageSize(pageRequest.getLimit());
		resp.setTotal(total);

		SQLBuilders builders = SQLBuilders.create()
				.withWhere(whereList)
				.withPage(page)
				;
		//查询列表
		List<SysRole> list = sysRoleDAO.selectList(builders);
		if (CollectionUtils.isEmpty(list)) {
			log.warn("querySysRolePageList error. with list is empty. with request={}", request);
			return resp;
		}

		List<SysRoleResponseBO> respList = new ArrayList<>();
		for (SysRole a : list) {
			respList.add(SysRoleConvert.dal2BOResponse(a));
		}
		log.info("querySysRolePageList success. with request={}", request);
		return new PageResponse<>(total, respList, page.getCurrentPage(), page.getPageSize());
	}

	@Override
	@UseDatabase("test")
	@Transactional
	public void deleteSysRole(Integer id) {
		log.info("deleteSysRole with id={}", id);
		
		// 检查角色是否有用户绑定
		List<Integer> userIdList = sysUserRoleService.selectUserIdByRoleId(id);
		if (CollectionUtils.isNotEmpty(userIdList)) {
			log.warn("deleteSysRole error. with role has user bind. with roleId={}", id);
			throw new CommonException(500, "该角色有用户关联，请先解除用户关联，再删除角色。");
		}
		
		// 先删除与菜单关系
		sysRoleMenuService.deleteByRoleId(id);
		
		// 再删除角色
		sysRoleDAO.deleteByPrimaryKey(id);
	}

	@Override
	public SysRoleResponseBO querySysRoleById(Integer id) {
		log.info("querySysRoleById with id={}", id);
		SysRole dal = sysRoleDAO.selectByPrimaryKey(id);
		if (dal == null) {
			log.warn("querySysRoleById error. with result is null. with id={}", id);
			return null;
		}
		return SysRoleConvert.dal2BOResponse(dal);
	}
	
	/**
	 * @Title: saveSysRoleCheck
	 * @Description: 保存前的检查参数
	 * 检查名称是否重复
	 * @param request
	 */
	private void saveSysRoleCheck(SysRoleRequestBO request) {
		//根据角色名称查询
		SysRole role = sysRoleDAO.selectOne(SQLBuilders.create()
				.withWhere(SQLWhere.builder()
						.eq("role_name", request.getRoleName())
						.build()
						)
				);
		if (role == null) {
			return;
		}
		
		if (NumberUtil.isNullOrZero(request.getId())) {
			//新增
			log.warn("saveSysRole error. roleName is exists. with request={}", request);
			throw new CommonException(500, "角色名已经存在");
		} else {
			//修改
			if (!role.getId().equals(request.getId())) {
				log.warn("saveSysRole error. roleName is exists. with request={}", request);
				throw new CommonException(500, "角色名已经存在");
			}
		}
	}
}
