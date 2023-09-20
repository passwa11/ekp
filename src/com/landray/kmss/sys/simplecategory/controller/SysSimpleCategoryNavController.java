package com.landray.kmss.sys.simplecategory.controller;

import com.landray.kmss.common.rest.controller.BaseController;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.sys.simplecategory.actions.SysSimpleCategoryCriteriaAction;
import com.landray.kmss.util.SpringBeanUtil;
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
 * 分类导航后端接口
 *
 * @Author 严明镜
 * @create 2020/10/21 18:00
 */
@Controller
@RequestMapping(value = "/data/sys-simplecategory/sysSimpleCategoryNav", method = RequestMethod.POST)
public class SysSimpleCategoryNavController extends BaseController {

	private SysSimpleCategoryCriteriaAction action;

	public SysSimpleCategoryCriteriaAction getAction() {
		if (action == null) {
			action = (SysSimpleCategoryCriteriaAction) SpringBeanUtil.getBean("sysSimpleCategoryCriteriaAction");
		}
		return action;
	}

	/**
	 * 分类框数据获取（我关注的分类设置/新建显示分类）
	 */
	@ResponseBody
	@RequestMapping("select")
	public RestResponse<?> select(@RequestBody Map<String, Object> reqMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper.buildRequestParameterWrapper(request, reqMap);
		getAction().select(new ActionMapping(new ActionConfig()), null, wrapper, response);
		return result(wrapper, ControllerHelper.standardizeResult(wrapper.getAttribute("lui-source")));
	}

	/**
	 * 分类导航
	 */
	@ResponseBody
	@RequestMapping("index")
	public RestResponse<?> index(@RequestBody Map<String, Object> reqMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper.buildRequestParameterWrapper(request, reqMap);
		getAction().index(new ActionMapping(new ActionConfig()), null, wrapper, response);
		return result(wrapper, request.getAttribute("lui-source"));
	}

	/**
	 * 分类导航-路径导航
	 */
	@ResponseBody
	@RequestMapping("path")
	public RestResponse<?> path(@RequestBody Map<String, Object> reqMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper.buildRequestParameterWrapper(request, reqMap);
		getAction().path(new ActionMapping(new ActionConfig()), null, wrapper, response);
		return result(wrapper, request.getAttribute("lui-source"));
	}

	/**
	 * 获取分类名称
	 */
	@ResponseBody
	@RequestMapping("currentCate")
	public RestResponse<?> currentCate(@RequestBody Map<String, Object> reqMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper.buildRequestParameterWrapper(request, reqMap);
		getAction().currentCate(new ActionMapping(new ActionConfig()), null, wrapper, response);
		return result(wrapper, request.getAttribute("lui-source"));
	}

}
