package com.landray.kmss.sys.news.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.news.model.SysNewsOutSign;
import com.landray.kmss.sys.news.service.ISysNewsOutSignService;
import com.landray.kmss.sys.news.service.spring.SysNewsYqqSignServiceImp;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;


public class SysNewsOutSignAction extends ExtendAction{
	
	private ISysNewsOutSignService sysNewsOutSignService;
	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysNewsOutSignService == null) {
			sysNewsOutSignService = (ISysNewsOutSignService) getBean(
					"sysNewsOutSignService");
		}
		return sysNewsOutSignService;
	}

	public ActionForward queryStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String signId = request.getParameter("signId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"sysNewsOutSign.fdMainId=:fdMainId and sysNewsOutSign.fdStatus in (:fdStatusList) and sysNewsOutSign.docCreator.fdId = :docCreatorId");
		hqlInfo.setParameter("fdMainId", signId);
		hqlInfo.setParameter("docCreatorId", UserUtil.getUser().getFdId());
		List<String> fdStatusList = new ArrayList<String>();
		
		fdStatusList.add(SysNewsYqqSignServiceImp.status_code_received);
		fdStatusList.add(SysNewsYqqSignServiceImp.status_code_callback);
		hqlInfo.setParameter("fdStatusList", fdStatusList);
		List<SysNewsOutSign> signList = ((ISysNewsOutSignService) getServiceImp(
				request)).findList(hqlInfo);
		response.setCharacterEncoding("utf-8");
		if (signList != null && signList.size() > 0) {
			SysNewsOutSign kmImissiveOutSign = signList.get(0);
			response.getWriter().write(kmImissiveOutSign.getFdUrl());
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
				"sysNewsOutSign.fdMainId=:fdMainId and sysNewsOutSign.fdStatus in (:fdStatusList)");
		hqlInfo.setParameter("fdMainId", signId);
		List<String> fdStatusList = new ArrayList<String>();
		
		fdStatusList.add(SysNewsYqqSignServiceImp.status_code_init);
		fdStatusList.add(SysNewsYqqSignServiceImp.status_code_received);
		hqlInfo.setParameter("fdStatusList", fdStatusList);
		List<SysNewsOutSign> signList = ((ISysNewsOutSignService) getServiceImp(
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
				"sysNewsOutSign.fdMainId=:fdMainId and sysNewsOutSign.docCreator.fdId = :docCreatorId");
		hqlInfo.setParameter("fdMainId", signId);
		hqlInfo.setParameter("docCreatorId", UserUtil.getUser().getFdId());
		List<SysNewsOutSign> signList = ((ISysNewsOutSignService) getServiceImp(
				request)).findList(hqlInfo);
		response.setCharacterEncoding("utf-8");
		if (signList != null && signList.size() > 0) {
			for (SysNewsOutSign sysNewsOutSign : signList) {
				if ("yqq".equals(sysNewsOutSign.getFdType())
						&& !SysNewsYqqSignServiceImp.status_code_callback
								.equals(sysNewsOutSign.getFdStatus())) {
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
