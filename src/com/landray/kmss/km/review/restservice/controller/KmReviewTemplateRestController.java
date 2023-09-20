package com.landray.kmss.km.review.restservice.controller;

import com.landray.kmss.km.review.restservice.dto.KmReviewBaseRestListDTO;
import com.landray.kmss.km.review.restservice.dto.KmReviewTemplateRestModelDTO;
import com.landray.kmss.km.review.restservice.dto.PaddingRequestContext;
import com.landray.kmss.km.review.restservice.service.IKmReviewTemplateRestService;
import com.landray.kmss.web.annotation.RestApi;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/km-review/template", method = RequestMethod.POST)
@RestApi(docUrl = "/km/review/restservice/kmReviewTemplateRestHelp.jsp", name = "kmReviewTemplateRestController", resourceKey = "km-review:kmReviewTemplate.restservice")
public class KmReviewTemplateRestController {

	private IKmReviewTemplateRestService kmReviewTemplateRestService;

	public void setKmReviewTemplateRestService(IKmReviewTemplateRestService kmReviewTemplateRestService) {
		this.kmReviewTemplateRestService = kmReviewTemplateRestService;
	}

	/**
	 * 审批模板数据列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public KmReviewBaseRestListDTO list(@RequestBody PaddingRequestContext ctx) throws Exception {
		return kmReviewTemplateRestService.list(ctx);
	}

	/**
	 * 审批模板数据详情
	 * @param fdId 模板ID
	 */
	@ResponseBody
	@RequestMapping("/get")
	public KmReviewTemplateRestModelDTO get(@RequestParam("fdId") String fdId) throws Exception {
		return kmReviewTemplateRestService.get(fdId);
	}

}
