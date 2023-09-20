package com.landray.kmss.sys.simplecategory.controller;

import com.landray.kmss.common.rest.controller.BaseController;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.sys.simplecategory.actions.CateChgAction;
import com.landray.kmss.sys.simplecategory.forms.CateChgForm;
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

/**
 * 分类转移后端接口
 *
 * @Author 严明镜
 * @create 2020/10/21 18:00
 */
@Controller
@RequestMapping(value = "/data/sys-simplecategory/cateChg", method = RequestMethod.POST)
public class CateChgController extends BaseController {

	private final CateChgAction action = new CateChgAction();

	/**
	 * 分类转移保存
	 */
	@ResponseBody
	@RequestMapping("cateChgUpdate")
	public RestResponse<?> cateChgUpdate(@RequestBody CateChgForm vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
		action.cateChgUpdate(new ActionMapping(new ActionConfig()), vo, wrapper, response);
		return result(wrapper);
	}

}
