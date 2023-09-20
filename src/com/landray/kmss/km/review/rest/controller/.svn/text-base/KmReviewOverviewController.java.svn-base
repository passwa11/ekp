package com.landray.kmss.km.review.rest.controller;

import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.rest.controller.BaseController;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.km.review.actions.KmReviewOverviewAction;
import com.landray.kmss.web.RestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 流程概览
 *
 * @Author 严明镜
 * @create 2020年12月15日
 */
@Controller
@RequestMapping("/data/km-review/kmReviewOverview")
public class KmReviewOverviewController extends BaseController {

	private final KmReviewOverviewAction action = new KmReviewOverviewAction();

	/**
	 * 流程概览页面
	 */
	@ResponseBody
	@RequestMapping("preview")
	public RestResponse<?> preview(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpRequestParameterWrapper reqWrapper = ControllerHelper.buildRequestParameterWrapper(request);
		action.preview(getDefMapping(), null, reqWrapper, response);
		return result(reqWrapper, ControllerHelper.standardizeResult(reqWrapper.getAttribute("lui-source")));
	}

}
