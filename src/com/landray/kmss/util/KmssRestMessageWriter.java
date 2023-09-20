package com.landray.kmss.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.landray.kmss.sys.profile.model.PasswordSecurityConfig;
import com.landray.kmss.web.RestResponse;
import com.landray.kmss.web.RestResponseConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

/**
 * 用于在jsperror.jsp上输出RestResponse的返回体，
 * 前后端分离使用
 *
 * @Author 严明镜
 * @create 2020年12月02日
 */
public class KmssRestMessageWriter {

	private static final ObjectMapper mapper = new ObjectMapper();

	public static void printRestResponse(HttpServletRequest request, JspWriter out, Throwable e) {
		try {
			out.clear();
			String errorCode = RestResponseConstant.ERROR_CODE_BAD_CREDENTIALS;
			String errorMsg = ResourceUtil.getString(request.getSession(), errorCode);
			//安全策略中不禁用详细报错信息
			if (!"true".equals(PasswordSecurityConfig.newInstance().getKmssErrorStackDisabled())) {
				out.write(mapper.writeValueAsString(RestResponse.error(errorCode, ExceptionUtil.getExceptionString(e))));
			} else {
				out.write(mapper.writeValueAsString(RestResponse.error(errorCode, errorMsg)));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
