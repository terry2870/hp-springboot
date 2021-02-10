package com.hp.springboot.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hp.springboot.admin.constant.AdminConstants;
import com.hp.springboot.admin.convert.BaseConvert;
import com.hp.springboot.admin.convert.SysUserConvert;
import com.hp.springboot.admin.dal.ISysUserDAO;
import com.hp.springboot.admin.dal.model.SysUser;
import com.hp.springboot.admin.model.request.SysUserRequestBO;
import com.hp.springboot.admin.model.response.SysUserResponseBO;
import com.hp.springboot.admin.service.ISysUserRoleService;
import com.hp.springboot.admin.service.ISysUserService;
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
 * 描述：用户的service实现
 * 作者：黄平
 * 时间：2021年1月12日
 */
@Service
public class SysUserServiceImpl implements ISysUserService {
	
	@Autowired
	private ISysUserDAO sysUserDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ISysUserRoleService sysUserRoleService;

	private static Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);
	
	@Override
	public void saveSysUser(SysUserRequestBO request) {
		log.info("saveSysUser with request={}", request);
		SysUser dal = SysUserConvert.boRequest2Dal(request);
		
		//检查唯一性
		saveSysUserCheck(request);
		
		if (NumberUtil.isEmpty(request.getId())) {
			//新增
			BaseConvert.convertModel(dal);
			
			//新增时，设置初始密码
			dal.setLoginPwd(passwordEncoder.encode(AdminConstants.NORMAL_USER_DEFAULT_PASSWORD));
			sysUserDAO.insertSelective(dal);
		} else {
			//修改
			dal.setUpdateTime(DateUtil.getCurrentTimeSeconds());
			
			//修改的时候，这里不修改密码
			dal.setLoginPwd(null);
			sysUserDAO.updateByPrimaryKeySelective(dal);
		}
		
		//保存该用户的角色
		sysUserRoleService.insertUserRole(dal.getId(), request.getRoleIdList());
		log.info("saveSysUser success with request={}", request);
	}

	@Override
	public PageResponse<SysUserResponseBO> querySysUserPageList(SysUserRequestBO request, PageRequest pageRequest) {
		log.info("querySysUserPageList with request={}, pageRequest={}", request, pageRequest);
		PageModel page = pageRequest.toPageModel();

		List<SQLWhere> whereList = SQLWhere.builder()
				.eq("id", request.getId())
				.like("login_name", request.getLoginName())
				.like("nick_name", request.getNickName())
				.build()
				;
		//查询总数
		int total = sysUserDAO.selectCount(whereList);
		if (total == 0) {
			log.warn("querySysUserPageList error. with total=0. with request={}", request);
			return null;
		}
		PageResponse<SysUserResponseBO> resp = new PageResponse<>();
		resp.setCurrentPage(pageRequest.getPage());
		resp.setPageSize(pageRequest.getLimit());
		resp.setTotal(total);

		SQLBuilders builders = SQLBuilders.create()
				.withWhere(whereList)
				.withPage(page)
				;
		//查询列表
		List<SysUser> list = sysUserDAO.selectList(builders);
		if (CollectionUtils.isEmpty(list)) {
			log.warn("querySysUserPageList error. with list is empty. with request={}", request);
			return resp;
		}

		List<SysUserResponseBO> respList = new ArrayList<>();
		for (SysUser a : list) {
			respList.add(SysUserConvert.dal2BOResponse(a));
		}
		log.info("querySysUserPageList success. with request={}", request);
		return new PageResponse<>(total, respList, page.getCurrentPage(), page.getPageSize());
	}

	@Override
	public void deleteSysUser(Integer id) {
		log.info("deleteSysUser with id={}", id);
		
		SysUser user = new SysUser();
		user.setId(id);
		user.setStatus(StatusEnum.INVALID.getValue());
		sysUserDAO.updateByPrimaryKeySelective(user);
		
		log.info("deleteSysUser success with id={}", id);
	}

	@Override
	public SysUserResponseBO querySysUserById(Integer id) {
		log.info("querySysUserById with id={}", id);
		SysUser dal = sysUserDAO.selectByPrimaryKey(id);
		if (dal == null) {
			log.warn("querySysUserById error. with result is null. with id={}", id);
			return null;
		}
		return SysUserConvert.dal2BOResponse(dal);
	}

	/**
	 * @Title: saveSysUserCheck
	 * @Description: 保存前的检查
	 * @param request
	 */
	private void saveSysUserCheck(SysUserRequestBO request) {
		//检查登录名
		SQLBuilders builders = SQLBuilders.create()
				.withWhere(SQLWhere.builder()
						.eq("login_name", request.getLoginName())
						.build()
						)
				;
		
		SysUser user = sysUserDAO.selectOne(builders);
		if (user != null) {
			if (NumberUtil.isEmpty(request.getId())) {
				//新增
				log.warn("saveSysUser error. loginName is exists. with request={}", request);
				throw new CommonException(500, "登录名已经存在");
			} else {
				//修改
				if (!user.getId().equals(request.getId())) {
					log.warn("saveSysUser error. loginName is exists. with request={}", request);
					throw new CommonException(500, "登录名已经存在");
				}
			}
		}
		
		//检查用户名
		builders = SQLBuilders.create()
				.withWhere(SQLWhere.builder()
						.eq("nick_name", request.getNickName())
						.build()
						)
				;
		user = sysUserDAO.selectOne(builders);
		if (user != null) {
			if (NumberUtil.isEmpty(request.getId())) {
				//新增
				log.warn("saveUser error. userName is exists. with request={}", request);
				throw new CommonException(500, "用户名已经存在");
			} else {
				//修改
				if (!user.getId().equals(request.getId())) {
					log.warn("saveUser error. userName is exists. with request={}", request);
					throw new CommonException(500, "用户名已经存在");
				}
			}
		}
	}
}
