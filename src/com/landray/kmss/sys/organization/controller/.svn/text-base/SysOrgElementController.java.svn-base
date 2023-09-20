package com.landray.kmss.sys.organization.controller;

import com.landray.kmss.common.rest.controller.BaseController;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.dto.HttpResponseWriterWrapper;
import com.landray.kmss.sys.organization.actions.SysOrgElementOutAction;
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
 * 组织架构对外后端接口
 *
 * @Author 严明镜
 * @create 2020/10/23 15:15
 */
@Controller
@RequestMapping(value = "/data/sys-organization/sysOrgElement", method = RequestMethod.POST)
public class SysOrgElementController extends BaseController {

	SysOrgElementOutAction action = new SysOrgElementOutAction();

	/**
	 * 获取组织架构名称
	 */
	@ResponseBody
	@RequestMapping("getDeptName")
	public RestResponse<?> getDeptName(@RequestBody Map<String, Object> vo, HttpServletRequest request, HttpServletResponse response) {
		HttpRequestParameterWrapper reqWrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
		HttpResponseWriterWrapper respWrapper = ControllerHelper.buildResponseWriterWrapper(response);
		action.getDeptName(new ActionMapping(new ActionConfig()), null, reqWrapper, respWrapper);
		return result(reqWrapper, ControllerHelper.standardizeResult(respWrapper.getWriteContent()));
	}

}
