package com.landray.kmss.hr.ratify.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.ratify.model.HrRatifyOutSign;
import com.landray.kmss.hr.ratify.service.IHrRatifyOutSignService;
import com.landray.kmss.hr.ratify.service.spring.HrRatifyYqqSignServiceImp;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class HrRatifyOutSignAction extends ExtendAction {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HrRatifyOutSignAction.class);

	private IHrRatifyOutSignService hrRatifyOutSignService;
	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (hrRatifyOutSignService == null) {
			hrRatifyOutSignService = (IHrRatifyOutSignService) getBean(
					"hrRatifyOutSignService");
		}
		return hrRatifyOutSignService;
	}

	public ActionForward queryStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String signId = request.getParameter("signId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"hrRatifyOutSign.fdMainId=:fdMainId and hrRatifyOutSign.fdStatus in (:fdStatusList) and hrRatifyOutSign.docCreator.fdId = :docCreatorId");
		hqlInfo.setParameter("fdMainId", signId);
		hqlInfo.setParameter("docCreatorId", UserUtil.getUser().getFdId());
		List<String> fdStatusList = new ArrayList<String>();
		
		fdStatusList.add(HrRatifyYqqSignServiceImp.status_code_received);
		fdStatusList.add(HrRatifyYqqSignServiceImp.status_code_callback);
		hqlInfo.setParameter("fdStatusList", fdStatusList);
		List<HrRatifyOutSign> signList = ((IHrRatifyOutSignService) getServiceImp(
				request)).findList(hqlInfo);
		response.setCharacterEncoding("utf-8");
		if (signList != null && signList.size() > 0) {
			HrRatifyOutSign hrRatifyOutSign = signList.get(0);
			response.getWriter().write(hrRatifyOutSign.getFdUrl());
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
				"hrRatifyOutSign.fdMainId=:fdMainId and hrRatifyOutSign.fdStatus in (:fdStatusList)");
		hqlInfo.setParameter("fdMainId", signId);
		List<String> fdStatusList = new ArrayList<String>();
		
		fdStatusList.add(HrRatifyYqqSignServiceImp.status_code_init);
		fdStatusList.add(HrRatifyYqqSignServiceImp.status_code_received);
		hqlInfo.setParameter("fdStatusList", fdStatusList);
		List<HrRatifyOutSign> signList = ((IHrRatifyOutSignService) getServiceImp(
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
				"hrRatifyOutSign.fdMainId=:fdMainId and hrRatifyOutSign.docCreator.fdId = :docCreatorId");
		hqlInfo.setParameter("fdMainId", signId);
		hqlInfo.setParameter("docCreatorId", UserUtil.getUser().getFdId());
		List<HrRatifyOutSign> signList = ((IHrRatifyOutSignService) getServiceImp(
				request)).findList(hqlInfo);
		logger.info("hroutsign-signList:" + signList.size());
		response.setCharacterEncoding("utf-8");
		if (signList != null && signList.size() > 0) {
			for (HrRatifyOutSign hrRatifyOutSign : signList) {
				if ("yqq".equals(hrRatifyOutSign.getFdType())
						&& !HrRatifyYqqSignServiceImp.status_code_finish
								.equals(hrRatifyOutSign.getFdStatus())) {
					logger.info("hroutsign-status:"
							+ hrRatifyOutSign.getFdStatus());
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
