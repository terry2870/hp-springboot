/**
 * 
 */
package com.hp.springboot.mybatis.autocreate;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hp.springboot.common.bean.Response;
import com.hp.springboot.mybatis.autocreate.helper.AutoCreateBean;

/**
 * @author huangping
 * 2018年9月19日
 */
@RestController
@RequestMapping("/AutoCreateRest")
public class AutoCreateRest {
	
	/**
	 * 自动生成
	 * @param bean
	 * @return
	 */
	@RequestMapping("/create")
	public Response<?> create(@RequestBody AutoCreateBean bean) {
		CreateFile.main(bean);
		return new Response<>();
	}
}
