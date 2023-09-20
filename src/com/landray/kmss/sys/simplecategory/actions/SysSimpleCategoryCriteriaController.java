package com.landray.kmss.sys.simplecategory.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.web.KmssMediaTypes;
import com.landray.kmss.web.RestResponse;

import net.sf.json.JSONArray;

/**
 * 简单分类门户数据源数据接口
 * 
 * @author chao
 *
 */
@Controller
@RequestMapping(value = "/data/sys-simplecategory/sysSimpleCategoryCriteria")
public class SysSimpleCategoryCriteriaController {

	private SysSimpleCategoryCriteriaAction action;

	public SysSimpleCategoryCriteriaAction getAction() {
		if (action == null) {
			action = (SysSimpleCategoryCriteriaAction) SpringBeanUtil
					.getBean("sysSimpleCategoryCriteriaAction");
		}
		return action;
	}

	/**
	 * 简单分类概览--面板
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/index", produces = {
			KmssMediaTypes.APPLICATION_JSON_UTF8 })
	@ResponseBody
	public RestResponse<?> index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestContext requestCtx = new RequestContext(request, true);
		JSONArray array = null;
		try {
			array = getAction().getFlatData(requestCtx);
		} catch (Exception e) {
			e.printStackTrace();
			// 错误处理
			return RestResponse.error(RestResponse.ERROR_CODE_500,
					e.getMessage());
		}
		return RestResponse.ok(array);
	}

	/**
	 * 简单分类概览--层级
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/index2", produces = {
			KmssMediaTypes.APPLICATION_JSON_UTF8 })
	@ResponseBody
	public RestResponse<?> index2(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RequestContext requestCtx = new RequestContext(request, true);
		JSONArray array = null;
		try {
			array = getAction().getLevelData(requestCtx);
		} catch (Exception e) {
			e.printStackTrace();
			// 错误处理
			return RestResponse.error(RestResponse.ERROR_CODE_500,
					e.getMessage());
		}
		return RestResponse.ok(array);
	}
}
