package com.landray.kmss.sys.organization.controller;

import com.landray.kmss.common.rest.controller.BaseController;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.dto.HttpResponseWriterWrapper;
import com.landray.kmss.sys.organization.actions.SysOrganizationRecentContactAction;
import com.landray.kmss.web.RestResponse;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.config.ActionConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 最近使用的组织架构后端接口
 * @Author 严明镜
 * @create 2020/10/23 15:15
 */
@Controller
@RequestMapping(value = "/data/sys-organization/sysOrganizationRecentContact", method = RequestMethod.POST)
public class SysOrganizationRecentContactController extends BaseController {

	SysOrganizationRecentContactAction action = new SysOrganizationRecentContactAction();

	/**
	 * 清空最近使用的组织架构
	 */
	@ResponseBody
	@RequestMapping("delContacts")
	public RestResponse<?> delContacts(@RequestBody Map<String, Object> vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpRequestParameterWrapper reqWrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
		HttpResponseWriterWrapper respWrapper = ControllerHelper.buildResponseWriterWrapper(response);
		action.delContacts(new ActionMapping(new ActionConfig()), null, reqWrapper, respWrapper);
		Object result = respWrapper.getWriteContent();
		//action请求成功的返回值
		if ("true".equals(result)) {
			return result(reqWrapper);
		} else {
			return result(reqWrapper, null, String.valueOf(result));
		}
	}

	/**
	 * 添加最近使用的组织架构
	 */
	@ResponseBody
	@RequestMapping("addContacts")
	public RestResponse<?> addContacts(@RequestBody Map<String, Object> vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpRequestParameterWrapper reqWrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
		HttpResponseWriterWrapper respWrapper = ControllerHelper.buildResponseWriterWrapper(response);
		action.addContacts(new ActionMapping(new ActionConfig()), null, reqWrapper, respWrapper);
		Object result = respWrapper.getWriteContent();
		//action请求成功的返回值
		if ("true".equals(result)) {
			return result(reqWrapper);
		} else {
			return result(reqWrapper, null, String.valueOf(result));
		}
	}

}
