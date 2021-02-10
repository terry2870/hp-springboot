package com.hp.springboot.common.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.springboot.common.bean.Response;
import com.hp.springboot.common.constant.ContentTypeConstant;

/**
 * 描述：处理http相应数据
 * 作者：黄平
 * 时间：2021年2月8日
 */
public class HttpResponseUtil {

	private static Logger log = LoggerFactory.getLogger(HttpResponseUtil.class);
	
	/**
	 * @Title: responseJSON
	 * @Description: 响应json数据
	 * @param resp
	 * @param request
	 * @param response
	 */
	public static void responseJSON(Response<Object> resp, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType(ContentTypeConstant.APPLICATION_JSON_UTF8);
		try (PrintWriter out = response.getWriter()) {
			out.write(resp.toString());
			out.flush();
		} catch (IOException e) {
			log.error("responseJSON error. with resp={}", resp, e);
		}
	}
	
	/**
	 * @Title: showMessageAndRedirectToUrl
	 * @Description: 显示信息，并且跳转到指定页面
	 * @param request
	 * @param response
	 * @param showMessage
	 * @param redirectUrl
	 */
	public static void showMessageAndRedirectToUrl(HttpServletRequest request, HttpServletResponse response
			, String showMessage, String redirectUrl) {
		response.setContentType(ContentTypeConstant.TEXT_HTML_UTF8);
		try (PrintWriter out = response.getWriter()) {
			out.write("<script>alert('"+ showMessage +"');location.href='"+ redirectUrl +"'</script>");
			out.flush();
		} catch (IOException e) {
			log.error("showMessageAndRedirectToUrl error. with showMessage={}", showMessage, e);
		}
	}
	
}
