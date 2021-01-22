package com.hp.springboot.admin.configuration;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import com.google.common.collect.Lists;
import com.hp.springboot.admin.constant.AdminConstants;
import com.hp.springboot.admin.security.AdminAccessDecisionManager;
import com.hp.springboot.admin.security.AdminFilterInvocationSecurityMetadataSource;
import com.hp.springboot.admin.security.AdminUserDetailsService;
import com.hp.springboot.admin.security.ValidateCodeFilter;
import com.hp.springboot.admin.security.handler.AdminAccessDeniedHandler;
import com.hp.springboot.admin.security.handler.AdminAuthenticationEntryPoint;
import com.hp.springboot.admin.security.handler.AdminAuthenticationFailureHandler;
import com.hp.springboot.admin.security.handler.AdminAuthenticationSuccessHandler;
import com.hp.springboot.common.configuration.CommonWebMvcConfigurer;

/**
 * 描述：security全局配置
 * 作者：黄平
 * 时间：2021年1月11日
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //全局
public class AdminWebSecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CommonWebMvcConfigurer commonWebMvcConfigurer;
	
	// 一级免过滤列表（不管有没有session都可以访问）
	@Value("#{'${hp.springboot.admin.first-no-filter-urls:}'.split(',')}")
	private List<String> firstNoFilterList;
	
	// 默认的第一免过滤
	private static final List<String> defaultFirstNoFilterList = Lists.newArrayList("/", "/actuator", "/health", AdminConstants.LOGIN_PAGE_URL, AdminConstants.LOGOUT_URL, AdminConstants.LOGOUT_URL, "/refeshCheckCode");

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 设置超级管理员
		auth.inMemoryAuthentication()
			.withUser(AdminConstants.ADMIN_USER)
		;
		
		// 其余账号通过数据库查询验证
		auth.userDetailsService(adminUserDetailsService()).passwordEncoder(passwordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// 所有的无需登录就可访问的地址都在这里配置
		web.ignoring().antMatchers(commonWebMvcConfigurer.getStaticPatternList().toArray(new String[] {}))
		.and()
		.ignoring().antMatchers(defaultFirstNoFilterList.toArray(new String[defaultFirstNoFilterList.size()]))
		;
		if (CollectionUtils.isNotEmpty(firstNoFilterList)) {
			web.ignoring().antMatchers(firstNoFilterList.toArray(new String[firstNoFilterList.size()]));
		}
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 验证码过滤器
		http.addFilterBefore(new ValidateCodeFilter(), UsernamePasswordAuthenticationFilter.class);
		http.authorizeRequests()
		.requestMatchers(CorsUtils :: isPreFlightRequest).permitAll()// 支持跨域
		.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
			// 进入这个拦截器的，都是需要登录才能访问的地址
			@Override
			public <O extends FilterSecurityInterceptor> O postProcess(O object) {
				// 添加权限过滤器
				object.setSecurityMetadataSource(adminFilterInvocationSecurityMetadataSource());
				
				// 添加权限决策器
				object.setAccessDecisionManager(adminAccessDecisionManager());
				return object;
			}
		})
		.and().authorizeRequests()
		//.antMatchers("/test", "/test111").permitAll()
		.anyRequest().authenticated()//其他路径需要授权访问
		.and().exceptionHandling()
		.accessDeniedHandler(new AdminAccessDeniedHandler())// 登录用户访问无权限的资源
		.authenticationEntryPoint(new AdminAuthenticationEntryPoint())// 匿名用户访问无权限的资源
		
		.and()
		.formLogin()
		.usernameParameter("username").passwordParameter("password")
		.loginPage(AdminConstants.LOGIN_PAGE_URL)
		.loginProcessingUrl(AdminConstants.LOGIN_PROCESSING_URL)
		//.defaultSuccessUrl(AdminConstants.INDEX_URL)
		.successHandler(new AdminAuthenticationSuccessHandler())// 登录成功处理
		.failureHandler(new AdminAuthenticationFailureHandler())// 登录失败的处理
		.permitAll()
		.and()
		.logout()
		.logoutUrl(AdminConstants.LOGOUT_URL)
		.permitAll()// 登出处理
		.and().csrf().disable();// 禁用csrf		
		;
	}
	
	/**
	 * @Title: passwordEncoder
	 * @Description: 加密方式
	 * @return
	 */
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	/**
	 * @Title: adminUserDetailsService
	 * @Description: 用户信息
	 * @return
	 */
	@Bean
	public UserDetailsService adminUserDetailsService() {
		return new AdminUserDetailsService();
	}
	
	/**
	 * @Title: adminFilterInvocationSecurityMetadataSource
	 * @Description: 权限过滤器
	 * @return
	 */
	@Bean
	public FilterInvocationSecurityMetadataSource adminFilterInvocationSecurityMetadataSource() {
		return new AdminFilterInvocationSecurityMetadataSource();
	}
	
	/**
	 * @Title: adminAccessDecisionManager
	 * @Description: 权限决策管理器
	 * @return
	 */
	@Bean
	public AccessDecisionManager adminAccessDecisionManager() {
		return new AdminAccessDecisionManager();
	}
}
