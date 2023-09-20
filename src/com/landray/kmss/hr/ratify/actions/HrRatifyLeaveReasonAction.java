package com.landray.kmss.hr.ratify.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.ratify.forms.HrRatifyLeaveReasonForm;
import com.landray.kmss.hr.ratify.model.HrRatifyLeaveReason;
import com.landray.kmss.hr.ratify.service.IHrRatifyLeaveReasonService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class HrRatifyLeaveReasonAction extends ExtendAction {

	private IHrRatifyLeaveReasonService hrRatifyLeaveReasonService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (hrRatifyLeaveReasonService == null) {
			hrRatifyLeaveReasonService = (IHrRatifyLeaveReasonService) getBean(
					"hrRatifyLeaveReasonService");
		}
		return hrRatifyLeaveReasonService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		// 新UED查询方式
		CriteriaValue cv = new CriteriaValue(request);
		String fdType = request.getParameter("fdType");
		if (StringUtil.isNotNull(fdType)) {
			hqlInfo.setWhereBlock("hrRatifyLeaveReason.fdType=:fdType");
			hqlInfo.setParameter("fdType", fdType);
		} else {
			hqlInfo.setWhereBlock(
					"hrRatifyLeaveReason.fdType is null or hrRatifyLeaveReason.fdType = ''");
		}
		CriteriaUtil.buildHql(cv, hqlInfo, HrRatifyLeaveReason.class);
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fdType = request.getParameter("fdType");
		if (StringUtil.isNotNull(fdType)) {
			HrRatifyLeaveReasonForm reasonForm = (HrRatifyLeaveReasonForm) form;
			reasonForm.setFdType(fdType);
		}
		return super.createNewForm(mapping, form, request, response);
	}

	public ActionForward criteria(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray array = new JSONArray();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"hrRatifyLeaveReason.fdType is null or hrRatifyLeaveReason.fdType = ''");
			List<HrRatifyLeaveReason> list = getServiceImp(request)
					.findList(hqlInfo);
			if (list != null && !list.isEmpty()) {
				for (HrRatifyLeaveReason reason : list) {
					JSONObject obj = new JSONObject();
					obj.put("text", reason.getFdName());
					obj.put("value", reason.getFdName());
					array.add(obj);
				}
			}
			request.setAttribute("lui-source", array);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}
}
