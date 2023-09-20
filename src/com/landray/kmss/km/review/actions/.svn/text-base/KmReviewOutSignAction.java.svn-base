package com.landray.kmss.km.review.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.review.model.KmReviewOutSign;
import com.landray.kmss.km.review.service.IKmReviewOutSignService;
import com.landray.kmss.km.review.service.spring.KmReviewYqqSignServiceImp;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class KmReviewOutSignAction extends ExtendAction{
	
	private IKmReviewOutSignService kmReviewOutSignService;
	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmReviewOutSignService == null) {
			kmReviewOutSignService = (IKmReviewOutSignService) getBean(
					"kmReviewOutSignService");
		}
		return kmReviewOutSignService;
	}

	public ActionForward queryStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String signId = request.getParameter("signId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"kmReviewOutSign.fdMainId=:fdMainId and kmReviewOutSign.fdStatus in (:fdStatusList) and kmReviewOutSign.docCreator.fdId = :docCreatorId");
		hqlInfo.setParameter("fdMainId", signId);
		hqlInfo.setParameter("docCreatorId", UserUtil.getUser().getFdId());
		List<String> fdStatusList = new ArrayList<String>();
		
		fdStatusList.add(KmReviewYqqSignServiceImp.status_code_received);
		fdStatusList.add(KmReviewYqqSignServiceImp.status_code_callback);
		hqlInfo.setParameter("fdStatusList", fdStatusList);
		KmReviewOutSign kmReviewOutSign =(KmReviewOutSign) ((IKmReviewOutSignService) getServiceImp(
				request)).findFirstOne(hqlInfo);
		response.setCharacterEncoding("utf-8");
		if (null != kmReviewOutSign) {
			response.getWriter().write(kmReviewOutSign.getFdUrl());
		} else {
			response.getWriter().write("false");
		}
		return null;
	}

	public ActionForward validateOnce(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String signId = request.getParameter("signId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"kmReviewOutSign.fdMainId=:fdMainId and kmReviewOutSign.fdStatus in (:fdStatusList)");
		hqlInfo.setParameter("fdMainId", signId);
		List<String> fdStatusList = new ArrayList<String>();
		
		fdStatusList.add(KmReviewYqqSignServiceImp.status_code_init);
		fdStatusList.add(KmReviewYqqSignServiceImp.status_code_received);
		hqlInfo.setParameter("fdStatusList", fdStatusList);
		KmReviewOutSign kmReviewOutSign = (KmReviewOutSign) ((IKmReviewOutSignService) getServiceImp(
				request)).findFirstOne(hqlInfo);
		if (kmReviewOutSign != null ) {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("true");
		}
		return null;
	}
	
	public ActionForward queryFinish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Boolean result = Boolean.TRUE;
		String signId = request.getParameter("signId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"kmReviewOutSign.fdMainId=:fdMainId  and kmReviewOutSign.docCreator.fdId = :docCreatorId");
		hqlInfo.setParameter("fdMainId", signId);
		hqlInfo.setParameter("docCreatorId", UserUtil.getUser().getFdId());
		List<KmReviewOutSign> signList = ((IKmReviewOutSignService) getServiceImp(
				request)).findList(hqlInfo);
		response.setCharacterEncoding("utf-8");
		if (signList != null && signList.size() > 0) {
			for (KmReviewOutSign kmReviewOutSign : signList) {
				if ("yqq".equals(kmReviewOutSign.getFdType())
						&& !KmReviewYqqSignServiceImp.status_code_callback
								.equals(kmReviewOutSign.getFdStatus())) {
					result = Boolean.FALSE;
				}
			}
		} else {
			result = Boolean.FALSE;
		}
		response.getWriter().write(result.toString());
		return null;
	}


}

