package com.landray.kmss.km.review.restservice.controller;

import com.landray.kmss.km.review.restservice.dto.KmReviewBaseRestListDTO;
import com.landray.kmss.km.review.restservice.dto.KmReviewInstanceListRequestContext;
import com.landray.kmss.km.review.restservice.dto.KmReviewInstanceRestModelDTO;
import com.landray.kmss.km.review.restservice.service.IKmReviewInstanceRestService;
import com.landray.kmss.web.annotation.RestApi;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/km-review/instance", method = RequestMethod.POST)
@RestApi(docUrl = "/km/review/restservice/kmReviewInstanceRestHelp.jsp", name = "kmReviewInstanceRestController", resourceKey = "km-review:kmReviewInstance.restservice")
public class KmReviewInstanceRestController {

	private IKmReviewInstanceRestService kmReviewInstanceRestService;

	public void setKmReviewInstanceRestService(IKmReviewInstanceRestService kmReviewInstanceRestService) {
		this.kmReviewInstanceRestService = kmReviewInstanceRestService;
	}

	/**
	 * 审批模板数据列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public KmReviewBaseRestListDTO list(@RequestBody KmReviewInstanceListRequestContext ctx) throws Exception {
		return kmReviewInstanceRestService.list(ctx);
	}

	/**
	 * 审批模板数据详情
	 * @param fdId 模板ID
	 */
	@ResponseBody
	@RequestMapping("/get")
	public KmReviewInstanceRestModelDTO get(@RequestParam("fdId") String fdId) throws Exception {
		return kmReviewInstanceRestService.get(fdId);
	}

}
