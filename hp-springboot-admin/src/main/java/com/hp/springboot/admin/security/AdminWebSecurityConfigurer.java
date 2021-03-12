package com.hp.springboot.admin.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hp.springboot.admin.constant.AdminConstants;
import com.hp.springboot.admin.interceptor.UrlAuthenticationInterceptor;
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
		// 静态资源
		String[] ignoreArray = commonWebMvcConfigurer.getMergeStaticPatternArray();
		web.ignoring().antMatchers(ignoreArray);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 验证码过滤器
		http.addFilterBefore(new ValidateCodeFilter(), UsernamePasswordAuthenticationFilter.class);
		
		// 第一层免过滤列表
		List<String> noFilterList = new ArrayList<>();
		noFilterList.add(AdminConstants.ACCESS_DENIED_URL);
		noFilterList.add(AdminConstants.VERIFY_CODE_URL);
		noFilterList.addAll(commonWebMvcConfigurer.getMergeFirstNoFilterList());
		
		// 静态资源
		//noFilterList.addAll(commonWebMvcConfigurer.getMergeStaticPatternList());
		
		http.formLogin()
		.usernameParameter("username").passwordParameter("password")
		.loginPage(AdminConstants.LOGIN_PAGE_URL)
		.loginProcessingUrl(AdminConstants.LOGIN_PROCESSING_URL)
		//.defaultSuccessUrl(AdminConstants.INDEX_URL)
		.successHandler(adminAuthenticationSuccessHandler())// 登录成功处理
		.failureHandler(adminAuthenticationFailureHandler())// 登录失败的处理
		.permitAll()
		.and()
		.logout()
		.logoutUrl(AdminConstants.LOGOUT_URL)
		.logoutSuccessUrl(AdminConstants.LOGIN_PAGE_URL)
		.permitAll()// 登出处理
		.and().authorizeRequests()
		
		// 第一层免过滤列表
		.antMatchers(noFilterList.toArray(new String[noFilterList.size()])).permitAll()
		
		//.anyRequest().authenticated()// 其他都要权限才能访问
		.anyRequest().access("@UrlAuthenticationInterceptor.hasPermission(request, authentication)")// 其他都需要权限控制
		.and().exceptionHandling()
		.accessDeniedHandler(new AdminAccessDeniedHandler())// 登录用户访问无权限的资源
		.authenticationEntryPoint(new AdminAuthenticationEntryPoint())// 匿名用户访问无权限的资源
		.and().csrf().disable()// 禁用csrf
		
		.sessionManagement()
		.invalidSessionUrl(AdminConstants.LOGIN_PAGE_URL)
		.maximumSessions(1)
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
	 * d
	 * @Title: adminAuthenticationFailureHandler
	 * @Description: 登录异常处理
	 * @return
	 */
	@Bean
	public AdminAuthenticationFailureHandler adminAuthenticationFailureHandler() {
		return new AdminAuthenticationFailureHandler();
	}
	
	/**
	 * @Title: adminAuthenticationSuccessHandler
	 * @Description: 登录成功后的处理
	 * @return
	 */
	@Bean
	public AdminAuthenticationSuccessHandler adminAuthenticationSuccessHandler() {
		return new AdminAuthenticationSuccessHandler();
	}
	
	/**
	 * @Title: urlAuthenticationInterceptor
	 * @Description: 查询权限拦截器
	 * @return
	 */
	@Bean("UrlAuthenticationInterceptor")
	public UrlAuthenticationInterceptor urlAuthenticationInterceptor() {
		return new UrlAuthenticationInterceptor();
	}
}
