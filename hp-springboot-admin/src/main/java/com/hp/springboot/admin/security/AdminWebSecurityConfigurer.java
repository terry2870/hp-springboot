package com.hp.springboot.admin.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启Security注解的功能，如果你项目中不用Security的注解（hasRole，hasAuthority等），则可以不加该注解
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
		
		// 设置系统的静态资源。静态资源不会走权限框架
		web.ignoring().antMatchers(ignoreArray);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 验证码过滤器
		http.addFilterBefore(new ValidateCodeFilter(), UsernamePasswordAuthenticationFilter.class);
		
		// 第一层免过滤列表
		// 就是所有人都可以访问的地址。区别于静态资源
		List<String> noFilterList = new ArrayList<>();
		noFilterList.add(AdminConstants.ACCESS_DENIED_URL);
		noFilterList.add(AdminConstants.VERIFY_CODE_URL);
		noFilterList.addAll(commonWebMvcConfigurer.getMergeFirstNoFilterList());
				
		http.formLogin()// 登录页面使用form提交的方式
		.usernameParameter("username").passwordParameter("password")// 设置登录页面用户名和密码的input对应name值（其实默认值就是username,password，所以这里可以不用设置）
		.loginPage(AdminConstants.LOGIN_PAGE_URL)// 设置登录页面的地址
		.loginProcessingUrl(AdminConstants.LOGIN_PROCESSING_URL)// 登录页面输入用户名密码后提交的地址
		.successHandler(adminAuthenticationSuccessHandler())// 登录成功处理
		.failureHandler(adminAuthenticationFailureHandler())// 登录失败的处理
		.permitAll()// 以上url全部放行，不需要校验权限
		.and()
		
		// 注销相关配置
		.logout()
		.logoutUrl(AdminConstants.LOGOUT_URL)// 注销地址
		.logoutSuccessUrl(AdminConstants.LOGIN_PAGE_URL)// 注销成功后跳转地址（这里就是跳转到登录页面）
		.permitAll()// 以上地址全部放行
		.and().authorizeRequests()
		
		// 第一层免过滤列表
		// 不需要登录，就可以直接访问的地址
		.antMatchers(noFilterList.toArray(new String[noFilterList.size()])).permitAll() // 全部放行
		
		// 其他都需要权限控制
		// 这里使用.anyRequest().access方法，把权限验证交给指定的一个方法去处理。
		// 这里 hasPermission接受两个参数request和authentication
		.anyRequest().access("@UrlAuthenticationInterceptor.hasPermission(request, authentication)")
		/*
		.anyRequest().authenticated().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {

			@Override
			public <O extends FilterSecurityInterceptor> O postProcess(O object) {
				
				// 权限查询器
				// 设置你有哪些权限
				object.setSecurityMetadataSource(null);
				
				// 权限决策器
				// 判断你有没有权限访问当前的url
				object.setAccessDecisionManager(null);
				return object;
			}

		})*/
		
		// 异常处理
		.and().exceptionHandling()
		.accessDeniedHandler(new AdminAccessDeniedHandler())// 登录用户访问无权限的资源
		.authenticationEntryPoint(new AdminAuthenticationEntryPoint())// 匿名用户访问无权限的资源
		.and().csrf().disable()// 禁用csrf
		
		// session管理
		.sessionManagement()
		.invalidSessionUrl(AdminConstants.LOGIN_PAGE_URL)// session失效后，跳转的地址
		.maximumSessions(1)// 同一个账号最大允许同时在线数
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
