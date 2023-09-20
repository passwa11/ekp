package com.landray.kmss.sys.organization.controller;

import com.landray.kmss.common.rest.controller.BaseController;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.sys.organization.actions.SysOrgElementCriteriaAction;
import com.landray.kmss.web.RestResponse;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.config.ActionConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author 严明镜
 * @create 2020/10/22 13:59
 */
@Controller
@RequestMapping(value = "/data/sys-organization/sysOrgElementCriteria", method = RequestMethod.POST)
public class SysOrgElementCriteriaController extends BaseController {

	private final SysOrgElementCriteriaAction action = new SysOrgElementCriteriaAction();

	/**
	 * 筛选框组织架构数据
	 */
	@ResponseBody
	@RequestMapping("criteria")
	public RestResponse<?> criteria(@RequestBody Map<String, Object> vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
		action.criteria(new ActionMapping(new ActionConfig()), null, wrapper, response);
		return result(wrapper, wrapper.getAttribute("lui-source"));
	}

}
