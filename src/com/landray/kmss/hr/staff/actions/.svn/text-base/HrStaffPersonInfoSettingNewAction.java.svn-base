package com.landray.kmss.hr.staff.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.staff.forms.HrStaffPersonInfoSettingNewForm;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoSettingNew;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoSettingNewService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class HrStaffPersonInfoSettingNewAction extends ExtendAction {

	private IHrStaffPersonInfoSettingNewService hrStaffPersonInfoSettingNewService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (hrStaffPersonInfoSettingNewService == null) {
			hrStaffPersonInfoSettingNewService = (IHrStaffPersonInfoSettingNewService) getBean(
					"hrStaffPersonInfoSetNewService");
		}
		return hrStaffPersonInfoSettingNewService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.createNewForm(mapping, form, request, response);
		String fdType = request.getParameter("i.fdType");
		request.setAttribute("fdType", fdType);
		HrStaffPersonInfoSettingNewForm hrStaffPersonInfoSettingNewForm = (HrStaffPersonInfoSettingNewForm) form;
		hrStaffPersonInfoSettingNewForm.setFdType(fdType);
		return hrStaffPersonInfoSettingNewForm;
	}

	public ActionForward initView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-initView", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdType = request.getParameter("i.fdType");
		request.setAttribute("fdType", fdType);
		TimeCounter.logCurrentTime("Action-initView", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("initView", mapping, form, request,
					response);
		}
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);

		String fdType = request.getParameter("i.fdType");
		hqlInfo.setWhereBlock("hrStaffPersonInfoSettingNew.fdType = :fdType");
		hqlInfo.setParameter("fdType", fdType);

		// 新UED查询方式
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, HrStaffPersonInfoSettingNew.class);
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fdType = request.getParameter("i.fdType");
		HrStaffPersonInfoSettingNewForm hrStaffPersonInfoSettingNewForm = (HrStaffPersonInfoSettingNewForm) form;
		hrStaffPersonInfoSettingNewForm.setFdType(fdType);
		return super.update(mapping, hrStaffPersonInfoSettingNewForm, request,
				response);
	}

	@Override
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fdType = request.getParameter("i.fdType");
		request.setAttribute("fdType", fdType);
		return super.view(mapping, form, request, response);
	}

	public ActionForward criteria(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray array = new JSONArray();
			HQLInfo hqlInfo = new HQLInfo();
			String fdType = request.getParameter("fdType");
			hqlInfo.setWhereBlock("hrStaffPersonInfoSettingNew.fdType=:fdType");
			hqlInfo.setParameter("fdType", fdType);
			List<HrStaffPersonInfoSettingNew> list = getServiceImp(request)
					.findList(hqlInfo);
			if (list != null && !list.isEmpty()) {
				for (HrStaffPersonInfoSettingNew setingNew : list) {
					JSONObject obj = new JSONObject();
					obj.put("text", setingNew.getFdName());
					obj.put("value", setingNew.getFdName());
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
