package com.landray.kmss.km.review.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class KmReviewMainStatAction extends BaseAction {
	protected IKmReviewMainService kmReviewMainService;

	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmReviewMainService == null) {
            kmReviewMainService = (IKmReviewMainService) getBean("kmReviewMainService");
        }
		return kmReviewMainService;
	}

	public ActionForward showStat(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-showStat", true, getClass());
		JSONArray jsonArray = new JSONArray();
		JSONObject statInfo = new JSONObject();
		statInfo.put("text", ResourceUtil.getString("showstat.text", "km-review"));
		statInfo.put("count", getReviewCountByCreator(request.getParameter("userId")));
		jsonArray.add(statInfo);
		request.setAttribute("lui-source", jsonArray);
		TimeCounter.logCurrentTime("Action-showStat", false, getClass());
		return mapping.findForward("lui-source");
	}

	@SuppressWarnings("unchecked")
	private long getReviewCountByCreator(String userId) {
		long count = 0;
		HQLInfo hqlInfo = new HQLInfo();
		String creatorId = userId;
		if (StringUtil.isNull(creatorId)) {
			creatorId = UserUtil.getUser().getFdId();
		}
		try {
			String whereBlock = " kmReviewMain.docCreator.fdId=:createorId";
			hqlInfo.setParameter("createorId", creatorId);
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setGettingCount(true);
			List<Object> resultList = getServiceImp(null).findValue(hqlInfo);
			Object result = resultList.get(0);
			count = Long.parseLong(result != null ? result.toString() : "0");
		} catch (Exception e) {
			log.info("获取发起的流程数量出错", e);
		}
		return count;
	}

}
