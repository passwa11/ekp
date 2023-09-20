package com.landray.kmss.km.review.service.spring;

import java.util.HashMap;
import java.util.Map;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.sys.lbpmservice.support.service.LbpmSummaryApplovalHandler;

/**
 * 流程管理模块
 * 
 * @author suyb
 *
 */
public class KmReviewSummaryHandler implements LbpmSummaryApplovalHandler {

	@Override
	public Map<String, Object> handler(IBaseModel model) {
		Map<String, Object> elems = new HashMap<String, Object>();

		KmReviewMain kmReviewMain = (KmReviewMain) model;
		elems.put("fdId", model.getFdId());
		elems.put("docSubject", kmReviewMain.getDocSubject());
		elems.put("docCreator", kmReviewMain.getDocCreator());
		elems.put("docCreateTime", kmReviewMain.getDocCreateTime());
		//模板还是得获取，在表单处理多语言的时候需要用到
		elems.put("fdTemplateId", kmReviewMain.getFdTemplate().getFdId());
		//elems.put("fdTemplateName", kmReviewMain.getFdTemplate().getFdName());
		elems.put("extendFilePath", kmReviewMain.getExtendFilePath());

		return elems;
	}
}
