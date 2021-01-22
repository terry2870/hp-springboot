package com.hp.springboot.admin.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hp.springboot.admin.constant.AdminConstants;
import com.hp.springboot.admin.convert.SysUserConvert;
import com.hp.springboot.admin.dao.ISysUserDAO;
import com.hp.springboot.admin.dao.model.SysUser;
import com.hp.springboot.admin.model.response.SysUserResponseBO;
import com.hp.springboot.admin.service.ISysUserRoleService;
import com.hp.springboot.database.bean.SQLBuilders;
import com.hp.springboot.database.bean.SQLWhere;

/**
 * 描述：Security需要的操作用户的接口实现
 * 执行登录,构建Authentication对象必须的信息
 * 如果用户不存在，则抛出UsernameNotFoundException异常
 * 作者：黄平
 * 时间：2021年1月12日
 */
public class AdminUserDetailsService implements UserDetailsService  {

	private static Logger log = LoggerFactory.getLogger(AdminUserDetailsService.class);
	
	@Autowired
	private ISysUserDAO sysUserDAO;
	@Autowired
	private ISysUserRoleService sysUserRoleService;
	
	/**
	 * 执行登录,构建Authentication对象必须的信息,
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("loadUserByUsername with username={}", username);
		
		//根据登录名，查询用户
		SysUser user = sysUserDAO.selectOne(SQLBuilders.create()
				.withWhere(SQLWhere.builder()
						.eq("login_name", username)
						.build()
						)
				);
		if (user == null) {
			log.warn("loadUserByUsername with user is not exists. with username={}", username);
			throw new UsernameNotFoundException("用户不存在");
		}
		
		SysUserResponseBO resp = SysUserConvert.dal2BOResponse(user);
		
		// 查询该用户角色
		resp.setAuthorities(getUserAuthorities(user.getId()));
		return resp;
	}
	
	/**
	 * @Title: getUserAuthorities
	 * @Description: 获取该用户所拥有的角色
	 * @param userId
	 * @return
	 */
	private List<GrantedAuthority> getUserAuthorities(Integer userId) {
		// 从缓存中获取
		Collection<Integer> roleIdList = sysUserRoleService.queryAllUserRole().get(userId);
		if (CollectionUtils.isEmpty(roleIdList)) {
			return null;
		}
		
		List<GrantedAuthority> authList = new ArrayList<>(roleIdList.size());
		for (Integer roleId : roleIdList) {
			authList.add(new SimpleGrantedAuthority(AdminConstants.ROLE_PREFIX + roleId.toString()));
		}
		
		return authList;
	}

}
