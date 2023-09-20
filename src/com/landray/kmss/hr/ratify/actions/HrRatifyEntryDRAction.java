package com.landray.kmss.hr.ratify.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.ratify.forms.HrRatifyEntryDRForm;
import com.landray.kmss.hr.ratify.model.HrRatifyEntry;
import com.landray.kmss.hr.ratify.service.IHrRatifyEntryService;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class HrRatifyEntryDRAction extends ExtendAction {

	private IHrRatifyEntryService hrRatifyEntryService;

	private ISysOrgElementService sysOrgElementService;

	@Override
	protected IHrRatifyEntryService getServiceImp(HttpServletRequest request) {
		if (hrRatifyEntryService == null) {
			hrRatifyEntryService = (IHrRatifyEntryService) getBean(
					"hrRatifyEntryService");
		}
		return hrRatifyEntryService;
	}

	public ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) getBean(
					"sysOrgElementService");
		}
		return sysOrgElementService;
	}

	public ActionForward addOrgPerson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-addOrgPerson", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			HrRatifyEntryDRForm drForm = (HrRatifyEntryDRForm) form;
			drForm.reset(mapping, request);
			String fdEntryId = request.getParameter("fdEntryId");
			HrRatifyEntry entry = (HrRatifyEntry) getServiceImp(request)
					.findByPrimaryKey(fdEntryId);
			Boolean fdHasWrite = entry.getFdHasWrite();
			drForm.setFdHasWrite(String.valueOf(fdHasWrite));
			drForm.setFdEntryId(fdEntryId);
			drForm.setFdEntryDate(
					DateUtil.convertDateToString(entry.getFdEntryDate(),
							DateUtil.TYPE_DATE, request.getLocale()));
			SysOrgElement fdEntryDept = entry.getFdEntryDept();
			if (fdEntryDept != null) {
				drForm.setFdEntryDeptId(fdEntryDept.getFdId());
				drForm.setFdEntryDeptName(fdEntryDept.getFdName());
			}
			List<SysOrgPost> fdEntryPosts = entry.getFdEntryPosts();
			if (fdEntryDept != null && !fdEntryPosts.isEmpty()) {
				String[] arr = ArrayUtil.joinProperty(fdEntryPosts,
						"fdId:fdName", ";");
				drForm.setFdEntryPostIds(arr[0]);
				drForm.setFdEntryPostNames(arr[1]);
			}
			drForm.setFdLoginName(entry.getFdLoginName());
			drForm.setFdPassword(entry.getFdPassword());
			drForm.setFdNo(entry.getFdStaffNo());
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-addOrgPerson", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("addOrgPerson", mapping, form, request,
					response);
		}
	}

	public ActionForward saveOrgPerson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-saveOrgPerson", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			HrRatifyEntryDRForm drForm = (HrRatifyEntryDRForm) form;
			String fdEntryId = drForm.getFdEntryId();
			HrRatifyEntry entry = (HrRatifyEntry) getServiceImp(request)
					.findByPrimaryKey(fdEntryId);
			Boolean fdHasWrite = entry.getFdHasWrite();
			if (fdHasWrite == null) {
				entry.setFdEntryDate(
						DateUtil.convertStringToDate(drForm.getFdEntryDate(),
								DateUtil.TYPE_DATE, request.getLocale()));
				SysOrgElement fdEntryDept = (SysOrgElement) getSysOrgElementService()
						.findByPrimaryKey(drForm.getFdEntryDeptId());
				entry.setFdEntryDept(fdEntryDept);
				List<SysOrgPost> fdEntryPosts = (List<SysOrgPost>) getSysOrgElementService()
						.findByPrimaryKeys(
								drForm.getFdEntryPostIds().split(";"));
				entry.setFdEntryPosts(fdEntryPosts);
				entry.setFdLoginName(drForm.getFdLoginName());
				entry.setFdPassword(drForm.getFdPassword());
				entry.setFdNo(drForm.getFdNo());
				getServiceImp(request).addOrgPerson(entry);
			}
			if (fdHasWrite != null && fdHasWrite.booleanValue()) {
				throw new KmssRuntimeException(new KmssMessage(
						"hr-ratify:hrRatifyEntry.fdHasWrite.tip"));
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}

		TimeCounter.logCurrentTime("Action-saveOrgPerson", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			KmssReturnPage rtnPage = KmssReturnPage.getInstance(request);
			rtnPage.addScriptCode("top.returnValue=true;");
			rtnPage.addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			//移动端跳转
			String mobile = request.getParameter("mobile");
			if ("mobile".equals(mobile)) {
				return getActionForward("addOrgPerson", mapping, form, request, response);
			}
			//pc端跳转
			return getActionForward("success" , mapping, form, request, response);
		}
	}

}
