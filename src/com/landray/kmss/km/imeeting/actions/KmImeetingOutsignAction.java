package com.landray.kmss.km.imeeting.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.imeeting.model.KmImeetingOutsign;
import com.landray.kmss.km.imeeting.service.IKmImeetingOutsignService;
import com.landray.kmss.km.imeeting.service.spring.KmImeetingYqqSignServiceImp;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class KmImeetingOutsignAction extends ExtendAction {

	private IKmImeetingOutsignService kmImeetingOutsignService;
	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if(kmImeetingOutsignService == null){
			kmImeetingOutsignService = (IKmImeetingOutsignService) getBean(
					"kmImeetingOutsignService");
		}
		return kmImeetingOutsignService;
	}

	public ActionForward queryStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String signId = request.getParameter("signId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"kmImeetingOutsign.fdMainid=:fdMainid and kmImeetingOutsign.fdStatus in (:fdStatusList) and kmImeetingOutsign.docCreator is null");
		hqlInfo.setParameter("fdMainid", signId);
		// hqlInfo.setParameter("docCreatorId", UserUtil.getUser().getFdId());
		List<String> fdStatusList = new ArrayList<String>();
		fdStatusList.add(KmImeetingYqqSignServiceImp.status_code_received);
		fdStatusList.add(KmImeetingYqqSignServiceImp.status_code_finish);
		hqlInfo.setParameter("fdStatusList", fdStatusList);
		List<KmImeetingOutsign> signList = ((IKmImeetingOutsignService) getServiceImp(
				request)).findList(hqlInfo);
		if (signList != null && signList.size() > 0) {
			KmImeetingOutsign kmImeetingOutsign = signList.get(0);
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(kmImeetingOutsign.getFdUrl());
		}
		return null;
	}

	public ActionForward validateOnce(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String signId = request.getParameter("signId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"kmImeetingOutsign.fdMainid=:fdMainid and kmImeetingOutsign.docCreator is null");
		hqlInfo.setParameter("fdMainid", signId);
		List<KmImeetingOutsign> signList = ((IKmImeetingOutsignService) getServiceImp(
				request)).findList(hqlInfo);
		if (signList != null && signList.size() > 0) {
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
				"kmImeetingOutsign.fdMainid=:fdMainid and kmImeetingOutsign.docCreator is null");
		hqlInfo.setParameter("fdMainid", signId);
		List<KmImeetingOutsign> signList = ((IKmImeetingOutsignService) getServiceImp(
				request)).findList(hqlInfo);
		response.setCharacterEncoding("utf-8");
		if (signList != null && signList.size() > 0) {
			for (KmImeetingOutsign kmImeetingOutSign : signList) {
				if ("yqq".equals(kmImeetingOutSign.getFdType())
						&& !KmImeetingYqqSignServiceImp.status_code_finish
								.equals(kmImeetingOutSign.getFdStatus())) {
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
