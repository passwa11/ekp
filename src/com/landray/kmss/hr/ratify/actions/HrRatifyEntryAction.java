package com.landray.kmss.hr.ratify.actions;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.ratify.forms.HrRatifyCertifiForm;
import com.landray.kmss.hr.ratify.forms.HrRatifyEduExpForm;
import com.landray.kmss.hr.ratify.forms.HrRatifyEntryForm;
import com.landray.kmss.hr.ratify.forms.HrRatifyHistoryForm;
import com.landray.kmss.hr.ratify.forms.HrRatifyRewPuniForm;
import com.landray.kmss.hr.ratify.model.HrRatifyEntry;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.service.IHrRatifyEntryService;
import com.landray.kmss.hr.ratify.service.IHrRatifyTemplateService;
import com.landray.kmss.hr.staff.forms.HrStaffCertifiForm;
import com.landray.kmss.hr.staff.model.HrStaffCertifi;
import com.landray.kmss.hr.staff.model.HrStaffEduExp;
import com.landray.kmss.hr.staff.model.HrStaffEntry;
import com.landray.kmss.hr.staff.model.HrStaffHistory;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoSettingNew;
import com.landray.kmss.hr.staff.model.HrStaffRewPuni;
import com.landray.kmss.hr.staff.service.IHrStaffEntryService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.print.interfaces.ISysPrintLogCoreService;
import com.landray.kmss.sys.print.interfaces.ISysPrintMainCoreService;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HtmlToMht;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HrRatifyEntryAction extends HrRatifyMainAction {

	private IHrStaffEntryService hrStaffEntryService;

	public IHrStaffEntryService getHrStaffEntryService() {
		if (hrStaffEntryService == null) {
			hrStaffEntryService = (IHrStaffEntryService) getBean(
					"hrStaffEntryService");
		}
		return hrStaffEntryService;
	}

	private IHrRatifyEntryService hrRatifyEntryService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (hrRatifyEntryService == null) {
            hrRatifyEntryService = (IHrRatifyEntryService) getBean("hrRatifyEntryService");
        }
        return hrRatifyEntryService;
    }

	private IHrRatifyTemplateService hrRatifyTemplateService;

	@Override
    public IHrRatifyTemplateService getHrRatifyTemplateService() {
		if (hrRatifyTemplateService == null) {
			hrRatifyTemplateService = (IHrRatifyTemplateService) getBean(
					"hrRatifyTemplateService");
		}
		return hrRatifyTemplateService;
	}

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, HrRatifyEntry.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.hr.ratify.util.HrRatifyUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.hr.ratify.model.HrRatifyEntry.class);
        com.landray.kmss.hr.ratify.util.HrRatifyUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HrRatifyEntryForm cloneForm = (HrRatifyEntryForm)BeanUtils.cloneBean(form);
        HrRatifyEntryForm hrRatifyEntryForm = (HrRatifyEntryForm) super.createNewForm(mapping, form, request, response);
		hrRatifyEntryForm
				.setFdRecruitEntryName(hrRatifyEntryForm.getFdEntryName());
		hrRatifyEntryForm.setDocTemplateName(
				getTemplatePath(hrRatifyEntryForm.getDocTemplateId()));
		
		hrRatifyEntryForm.setDocSubject(cloneForm.getDocSubject());
		hrRatifyEntryForm.setFdPassword(cloneForm.getFdPassword());
		hrRatifyEntryForm.setFdEntryDate(cloneForm.getFdEntryDate());
		hrRatifyEntryForm.setFdEntryTrialPeriod(cloneForm.getFdEntryTrialPeriod());
		hrRatifyEntryForm.setFdStaffNo(cloneForm.getFdStaffNo());
		hrRatifyEntryForm.setFdLoginName(cloneForm.getFdLoginName());
        return hrRatifyEntryForm;
    }

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm(mapping, form, request, response);
		HrRatifyEntryForm hrRatifyEntryForm = (HrRatifyEntryForm) form;
		String fdEntryId = request.getParameter("fdEntryId");
		if (StringUtil.isNotNull(fdEntryId)) {
			HrStaffEntry staffEntry = (HrStaffEntry) getHrStaffEntryService()
					.findByPrimaryKey(fdEntryId, HrStaffEntry.class, true);
			hrRatifyEntryForm.setFdRecruitEntryId(fdEntryId);
			hrRatifyEntryForm.setFdEntryName(staffEntry.getFdName());
			hrRatifyEntryForm
					.setFdNameUsedBefore(staffEntry.getFdNameUsedBefore());
			hrRatifyEntryForm.setFdSex(staffEntry.getFdSex());
			hrRatifyEntryForm.setFdDateOfBirth(
					DateUtil.convertDateToString(staffEntry.getFdDateOfBirth(),
							DateUtil.TYPE_DATE, request.getLocale()));
			hrRatifyEntryForm.setFdNativePlace(staffEntry.getFdNativePlace());
			hrRatifyEntryForm.setFdStaffNo(staffEntry.getFdStaffNo());
			HrStaffPersonInfoSettingNew fdMaritalStatus = staffEntry
					.getFdMaritalStatus();
			if (fdMaritalStatus != null) {
				hrRatifyEntryForm
						.setFdMaritalStatusId(fdMaritalStatus.getFdId());
				hrRatifyEntryForm
						.setFdMaritalStatusName(fdMaritalStatus.getFdName());
			}
			HrStaffPersonInfoSettingNew fdNation = staffEntry.getFdNation();
			if (fdNation != null) {
				hrRatifyEntryForm.setFdNationId(fdNation.getFdId());
				hrRatifyEntryForm.setFdNationName(fdNation.getFdName());
			}
			HrStaffPersonInfoSettingNew fdPoliticalLandscape = staffEntry
					.getFdPoliticalLandscape();
			if (fdPoliticalLandscape != null) {
				hrRatifyEntryForm.setFdPoliticalLandscapeId(
						fdPoliticalLandscape.getFdId());
				hrRatifyEntryForm.setFdPoliticalLandscapeName(
						fdPoliticalLandscape.getFdName());
			}
			HrStaffPersonInfoSettingNew fdHealth = staffEntry.getFdHealth();
			if (fdHealth != null) {
				hrRatifyEntryForm.setFdHealthId(fdHealth.getFdId());
				hrRatifyEntryForm.setFdHealthName(fdHealth.getFdName());
			}
			hrRatifyEntryForm.setFdLivingPlace(staffEntry.getFdLivingPlace());
			hrRatifyEntryForm.setFdIdCard(staffEntry.getFdIdCard());
			HrStaffPersonInfoSettingNew fdHighestDegree = staffEntry
					.getFdHighestDegree();
			if (fdHighestDegree != null) {
				hrRatifyEntryForm
						.setFdHighestDegreeId(fdHighestDegree.getFdId());
				hrRatifyEntryForm
						.setFdHighestDegreeName(fdHighestDegree.getFdName());
			}
			HrStaffPersonInfoSettingNew fdHighestEducation = staffEntry
					.getFdHighestEducation();
			if (fdHighestEducation != null) {
				hrRatifyEntryForm
						.setFdHighestEducationId(fdHighestEducation.getFdId());
				hrRatifyEntryForm.setFdHighestEducationName(
						fdHighestEducation.getFdName());
			}
			hrRatifyEntryForm.setFdProfession(staffEntry.getFdProfession());
			hrRatifyEntryForm.setFdWorkTime(
					DateUtil.convertDateToString(staffEntry.getFdWorkTime(),
							DateUtil.TYPE_DATE, request.getLocale()));
			hrRatifyEntryForm.setFdDateOfGroup(
					DateUtil.convertDateToString(staffEntry.getFdDateOfGroup(),
							DateUtil.TYPE_DATE, request.getLocale()));
			hrRatifyEntryForm.setFdDateOfParty(
					DateUtil.convertDateToString(staffEntry.getFdDateOfParty(),
							DateUtil.TYPE_DATE, request.getLocale()));
			Integer fdStature = staffEntry.getFdStature();
			if (fdStature != null) {
				hrRatifyEntryForm.setFdStature(String.valueOf(fdStature));
			}
			Integer fdWeight = staffEntry.getFdWeight();
			if (fdWeight != null) {
				hrRatifyEntryForm.setFdWeight(String.valueOf(fdWeight));
			}
			hrRatifyEntryForm.setFdHomeplace(staffEntry.getFdHomeplace());
			hrRatifyEntryForm.setFdAccountProperties(
					staffEntry.getFdAccountProperties());
			hrRatifyEntryForm.setFdRegisteredResidence(
					staffEntry.getFdRegisteredResidence());
			hrRatifyEntryForm.setFdResidencePoliceStation(
					staffEntry.getFdResidencePoliceStation());
			hrRatifyEntryForm.setFdEmail(staffEntry.getFdEmail());
			hrRatifyEntryForm.setFdMobileNo(staffEntry.getFdMobileNo());
			hrRatifyEntryForm
					.setFdEmergencyContact(staffEntry.getFdEmergencyContact());
			hrRatifyEntryForm.setFdEmergencyContactPhone(
					staffEntry.getFdEmergencyContactPhone());
			hrRatifyEntryForm.setFdOtherContact(staffEntry.getFdOtherContact());
			hrRatifyEntryForm.setFdEntryDate(DateUtil.convertDateToString(
					staffEntry.getFdPlanEntryTime(), DateUtil.TYPE_DATE,
					request.getLocale()));
			SysOrgElement fdPlanEntryDept = staffEntry.getFdPlanEntryDept();
			if (fdPlanEntryDept != null) {
				hrRatifyEntryForm.setFdEntryDeptId(fdPlanEntryDept.getFdId());
				hrRatifyEntryForm
						.setFdEntryDeptName(fdPlanEntryDept.getFdName());
			}
			AutoArrayList fdHistory = new AutoArrayList(
					HrRatifyHistoryForm.class);
			List<HrStaffHistory> sFdHistotry = staffEntry.getFdHistory();
			for (int i = 0; i < sFdHistotry.size(); i++) {
				HrRatifyHistoryForm history = new HrRatifyHistoryForm();
				HrStaffHistory sh = sFdHistotry.get(i);
				history.setFdDesc(sh.getFdDesc());
				history.setFdEndDate(
						DateUtil.convertDateToString(sh.getFdEndDate(),
								DateUtil.TYPE_DATE, request.getLocale()));
				history.setFdLeaveReason(sh.getFdLeaveReason());
				history.setFdName(sh.getFdName());
				history.setFdPost(sh.getFdPost());
				history.setFdStartDate(
						DateUtil.convertDateToString(sh.getFdStartDate(),
								DateUtil.TYPE_DATE, request.getLocale()));
				history.setDocMainId(hrRatifyEntryForm.getFdId());
				fdHistory.set(i, history);
			}
			hrRatifyEntryForm.setFdHistory_Form(fdHistory);
			AutoArrayList fdEducations = new AutoArrayList(
					HrRatifyEduExpForm.class);
			List<HrStaffEduExp> sFdEducations = staffEntry.getFdEducations();
			for (int i = 0; i < sFdEducations.size(); i++) {
				HrRatifyEduExpForm eduExp = new HrRatifyEduExpForm();
				HrStaffEduExp se = sFdEducations.get(i);
				HrStaffPersonInfoSettingNew fdAcadeRecord = se
						.getFdAcadeRecord();
				if (fdAcadeRecord != null) {
					eduExp.setFdAcadeRecordId(fdAcadeRecord.getFdId());
					eduExp.setFdAcadeRecordName(fdAcadeRecord.getFdName());
				}
				eduExp.setFdEntranceDate(
						DateUtil.convertDateToString(se.getFdEntranceDate(),
								DateUtil.TYPE_DATE, request.getLocale()));
				eduExp.setFdGraduationDate(
						DateUtil.convertDateToString(se.getFdGraduationDate(),
								DateUtil.TYPE_DATE, request.getLocale()));
				eduExp.setFdMajor(se.getFdMajor());
				eduExp.setFdName(se.getFdName());
				eduExp.setFdRemark(se.getFdRemark());
				eduExp.setDocMainId(hrRatifyEntryForm.getFdId());
				fdEducations.set(i, eduExp);
			}
			hrRatifyEntryForm.setFdEducations_Form(fdEducations);
			AutoArrayList fdCertificate = new AutoArrayList(
					HrStaffCertifiForm.class);
			List<HrStaffCertifi> sFdCertificate = staffEntry.getFdCertificate();
			for (int i = 0; i < sFdCertificate.size(); i++) {
				HrRatifyCertifiForm cert = new HrRatifyCertifiForm();
				HrStaffCertifi sc = sFdCertificate.get(i);
				cert.setFdInvalidDate(
						DateUtil.convertDateToString(sc.getFdInvalidDate(),
								DateUtil.TYPE_DATE, request.getLocale()));
				cert.setFdIssueDate(
						DateUtil.convertDateToString(sc.getFdIssueDate(),
								DateUtil.TYPE_DATE, request.getLocale()));
				cert.setFdIssuingUnit(sc.getFdIssuingUnit());
				cert.setFdName(sc.getFdName());
				cert.setFdRemark(sc.getFdRemark());
				cert.setDocMainId(hrRatifyEntryForm.getFdId());
				fdCertificate.set(i, cert);
			}
			hrRatifyEntryForm.setFdCertificate_Form(fdCertificate);
			AutoArrayList fdRewardsPunishments = new AutoArrayList(
					HrRatifyRewPuniForm.class);
			List<HrStaffRewPuni> sFdRewardsPunishments = staffEntry
					.getFdRewardsPunishments();
			for (int i = 0; i < sFdRewardsPunishments.size(); i++) {
				HrRatifyRewPuniForm rewPuni = new HrRatifyRewPuniForm();
				HrStaffRewPuni sp = sFdRewardsPunishments.get(i);
				rewPuni.setFdDate(DateUtil.convertDateToString(sp.getFdDate(),
						DateUtil.TYPE_DATE, request.getLocale()));
				rewPuni.setFdName(sp.getFdName());
				rewPuni.setFdRemark(sp.getFdRemark());
				rewPuni.setDocMainId(hrRatifyEntryForm.getFdId());
				fdRewardsPunishments.set(i, rewPuni);
			}
			hrRatifyEntryForm
					.setFdRewardsPunishments_Form(fdRewardsPunishments);
		}
		hrRatifyEntryForm
				.setFdRecruitEntryName(hrRatifyEntryForm.getFdEntryName());
	}

	/**
	 * 打印
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
    public ActionForward print(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-print", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			HtmlToMht.setLocaleWhenExport(request);
			loadActionForm(mapping, form, request, response);
			// 引入打印机制
			HrRatifyEntryForm ratifyForm = (HrRatifyEntryForm) form;
			HrRatifyTemplate template = (HrRatifyTemplate) getHrRatifyTemplateService()
					.findByPrimaryKey(ratifyForm.getDocTemplateId());
			boolean enable = getSysPrintMainCoreService()
					.isEnablePrintTemplate(template, null, request);

			HrRatifyEntry main = (HrRatifyEntry) getServiceImp(request).findByPrimaryKey(ratifyForm.getFdId());
			try {
				getSysPrintMainCoreService().initPrintData(main, ratifyForm, request);
			} catch (Exception e) {
			}
			if (enable) {
				request.setAttribute("isShowSwitchBtn", "true");
			}
			// 打印日志
			getSysPrintLogCoreService().addPrintLog(main,
					new RequestContext(request));
			String printPageType = request.getParameter("_ptype");
			if (enable && !"old".equals(printPageType)) {
				return mapping.findForward("sysprint");
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-print", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("print", mapping, form, request, response);
		}
	}

	protected ISysPrintMainCoreService sysPrintMainCoreService;

	@Override
    public ISysPrintMainCoreService getSysPrintMainCoreService() {
		if (sysPrintMainCoreService == null) {
			sysPrintMainCoreService = (ISysPrintMainCoreService) getBean(
					"sysPrintMainCoreService");
		}
		return sysPrintMainCoreService;
	}

	protected ISysPrintLogCoreService sysPrintLogCoreService;

	@Override
    public ISysPrintLogCoreService getSysPrintLogCoreService() {
		if (sysPrintLogCoreService == null) {
			sysPrintLogCoreService = (ISysPrintLogCoreService) getBean(
					"sysPrintLogCoreService");
		}
		return sysPrintLogCoreService;
	}

	public ActionForward checkAddOrg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fdEntryId = request.getParameter("fdEntryId");
		HrRatifyEntry entry = (HrRatifyEntry) getServiceImp(request)
				.findByPrimaryKey(fdEntryId);
		Boolean fdHasWrite = entry.getFdHasWrite();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().print(fdHasWrite);
		return null;
	}
}
