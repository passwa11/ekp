package com.landray.kmss.km.imeeting.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.imeeting.forms.KmImeetingVoteForm;
import com.landray.kmss.km.imeeting.service.IKmImeetingVoteService;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;

public class KmImeetingVoteAction extends ExtendAction {

	private IKmImeetingVoteService kmImeetingVoteService;
	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmImeetingVoteService == null) {
			kmImeetingVoteService = (IKmImeetingVoteService) getBean(
					"kmImeetingVoteService");
		}
		return kmImeetingVoteService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String whereBlock = hqlInfo.getWhereBlock();
		String fdTemporaryId = request.getParameter("fdTemporaryId");
		if (StringUtil.isNotNull(fdTemporaryId)) {
			whereBlock = StringUtil.linkString(whereBlock, "and",
					"kmImeetingVote.fdTemporaryId = :fdTemporaryId");
			hqlInfo.setParameter("fdTemporaryId", fdTemporaryId);
		}
		hqlInfo.setWhereBlock(whereBlock);
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmImeetingVoteForm kmImeetingVoteForm = (KmImeetingVoteForm) super.createNewForm(
				mapping,
				form, request, response);
		String fdTemporaryId = request.getParameter("fdTemporaryId");
		if (StringUtil.isNotNull(fdTemporaryId)) {
			kmImeetingVoteForm.setFdTemporaryId(fdTemporaryId);
		}
		return kmImeetingVoteForm;
	}

}
