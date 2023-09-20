package com.landray.kmss.km.imeeting.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.imeeting.service.IKmImeetingAgendaService;
import com.landray.kmss.util.UserUtil;

/**
 * 会议议程 Action
 */
public class KmImeetingAgendaAction extends ExtendAction {
	protected IKmImeetingAgendaService kmImeetingAgendaService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmImeetingAgendaService == null) {
            kmImeetingAgendaService = (IKmImeetingAgendaService) getBean("kmImeetingAgendaService");
        }
		return kmImeetingAgendaService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		hqlInfo.setWhereBlock("kmImeetingAgenda.docRespons.fdId=:responsId");
		hqlInfo.setParameter("responsId", UserUtil.getUser().getFdId());
	}

}
