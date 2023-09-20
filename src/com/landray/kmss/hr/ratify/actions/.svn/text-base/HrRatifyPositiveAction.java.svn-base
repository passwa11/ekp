package com.landray.kmss.hr.ratify.actions;

import com.google.common.collect.Lists;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.ratify.forms.HrRatifyPositiveForm;
import com.landray.kmss.hr.ratify.model.HrRatifyPositive;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.service.IHrRatifyPositiveService;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.print.interfaces.ISysPrintLogCoreService;
import com.landray.kmss.sys.print.interfaces.ISysPrintMainCoreService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.HtmlToMht;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.excel.ExcelOutput;
import com.landray.kmss.util.excel.ExcelOutputImp;
import com.landray.kmss.util.excel.WorkBook;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class HrRatifyPositiveAction extends HrRatifyMainAction {

    private IHrRatifyPositiveService hrRatifyPositiveService;
    
    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (hrRatifyPositiveService == null) {
            hrRatifyPositiveService = (IHrRatifyPositiveService) getBean("hrRatifyPositiveService");
        }
        return hrRatifyPositiveService;
    }

	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	protected IHrStaffPersonInfoService getHrStaffPersonInfoServiceImp() {
		if (hrStaffPersonInfoService == null) {
			hrStaffPersonInfoService = (IHrStaffPersonInfoService) getBean("hrStaffPersonInfoService");
		}
		return hrStaffPersonInfoService;
	}

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, HrRatifyPositive.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.hr.ratify.util.HrRatifyUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.hr.ratify.model.HrRatifyPositive.class);
        com.landray.kmss.hr.ratify.util.HrRatifyUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		((IHrRatifyPositiveService) getServiceImp(request)).initFormSetting(
				(IExtendForm) form, new RequestContext(request));
        HrRatifyPositiveForm hrRatifyPositiveForm = (HrRatifyPositiveForm) super.createNewForm(mapping, form, request, response);
		hrRatifyPositiveForm.setDocTemplateName(
				getTemplatePath(hrRatifyPositiveForm.getDocTemplateId()));

		String fdStaffId = request.getParameter("fdStaffId");
		HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) getHrStaffPersonInfoServiceImp()
				.findByPrimaryKey(fdStaffId);
		if (null != hrStaffPersonInfo) {
			hrRatifyPositiveForm.setFdPositiveStaffId(hrStaffPersonInfo.getFdId());
			hrRatifyPositiveForm.setFdPositiveStaffName(hrStaffPersonInfo.getFdName());
			if(null != hrStaffPersonInfo.getFdOrgParent()){
				hrRatifyPositiveForm.setFdPositiveDeptId(hrStaffPersonInfo.getFdOrgParent().getFdId());
				hrRatifyPositiveForm.setFdPositiveDeptName(hrStaffPersonInfo.getFdOrgParent().getFdName());
			}
			if(null != hrStaffPersonInfo.getFdEntryTime()){
				hrRatifyPositiveForm.setFdPositiveEnterDate(DateUtil.convertDateToString(hrStaffPersonInfo.getFdEntryTime(), DateUtil.PATTERN_DATE));
			}
			hrRatifyPositiveForm.setFdPositiveTrialPeriod(hrStaffPersonInfo.getFdTrialOperationPeriod());
			hrRatifyPositiveForm.setFdPositivePeriodDate(DateUtil
					.convertDateToString(
							hrStaffPersonInfo.getFdTrialExpirationTime(),
							DateUtil.PATTERN_DATE));
		}
        return hrRatifyPositiveForm;
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
			HrRatifyPositiveForm ratifyForm = (HrRatifyPositiveForm) form;
			HrRatifyTemplate template = (HrRatifyTemplate) getHrRatifyTemplateService()
					.findByPrimaryKey(ratifyForm.getDocTemplateId());
			boolean enable = getSysPrintMainCoreService()
					.isEnablePrintTemplate(template, null, request);

			HrRatifyPositive main = (HrRatifyPositive) getServiceImp(
					null)
							.convertFormToModel(ratifyForm, null,
									new RequestContext(request));
			getSysPrintMainCoreService().initPrintData(main, ratifyForm,
					request);
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

	/**
	 * <p>办理转正</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward transactionPositive(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-transactionPositive", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdId = request.getParameter("fdId");
			HrStaffPersonInfo personInfo = (HrStaffPersonInfo) getHrStaffPersonInfoServiceImp().findByPrimaryKey(fdId);
			request.setAttribute("hrStaffPersonInfo", personInfo);

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-transactionPositive", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("transactionPositive", mapping, form, request, response);
		}
	}

	public ActionForward saveTransactionPositive(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String personId = request.getParameter("personId");
			String fdActualPositiveTime = request.getParameter("fdActualPositiveTime");
			String fdPositiveRemark = request.getParameter("fdPositiveRemark");
			if(null != personId && StringUtil.isNotNull(fdActualPositiveTime)){
				((IHrRatifyPositiveService) getServiceImp(request)).saveTransactionPositive(personId,
						DateUtil.convertStringToDate(fdActualPositiveTime), fdPositiveRemark);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * <p>导出</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward export(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			HQLInfo hqlInfo = new HQLInfo();
			String whereBlock = "hrStaffPersonInfo.fdStatus = 'trial'";
			String ids = request.getParameter("ids");
			if (StringUtil.isNotNull(ids)) {
				String[] idArr = ids.split(";");
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						HQLUtil.buildLogicIN("hrStaffPersonInfo.fdId",
								ArrayUtil.convertArrayToList(idArr)));
			}
			hqlInfo.setWhereBlock(whereBlock);
			List<HrStaffPersonInfo> personInfos = getHrStaffPersonInfoServiceImp().findList(hqlInfo);
			WorkBook wb = ((IHrRatifyPositiveService) getServiceImp(request)).export(personInfos, request);
			ExcelOutput output = new ExcelOutputImp();
			output.output(wb, response);

		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("failure", mapping, form, request, response);
		}
		return null;
	}

	/**
	 * <p>批量调整</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward batchAdjust(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String fdId = request.getParameter("ids");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("fdId", Arrays.asList(fdId.split(","))));
			List<HrStaffPersonInfo> personInfos = getHrStaffPersonInfoServiceImp().findList(hqlInfo);
			request.setAttribute("list", personInfos);

		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("batchAdjust", mapping, form, request, response);
		}
	}

	/**
	 * <p>批量调整转正日期or试用日期</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateBatchAdjust(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String fdIds = request.getParameter("fdId");
			String type = request.getParameter("type"); //1、调整转正日期，2、调整试用日期
			if ("1".equals(type)) {
				String fdPositiveTime = request.getParameter("fdPositiveTime");
				((IHrRatifyPositiveService) getServiceImp(request)).updatePositiveDate(fdIds,
						DateUtil.convertStringToDate(fdPositiveTime));
			} else {
				String fdTrialOperationPeriod = request.getParameter("fdTrialOperationPeriod");
				((IHrRatifyPositiveService) getServiceImp(request)).updatePositiveTrialPeriod(fdIds,
						fdTrialOperationPeriod);
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}
	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String orgId =((HrRatifyPositiveForm)form).getFdPositiveStaffId();
		Boolean hasAccount = hashAccount(orgId);
		if(hasAccount){
			KmssMessages messages = new KmssMessages();
			messages.addMsg(new KmssMessage("hr-ratify:hrRatifyPositive.failure.needAccount"));
			
			KmssReturnPage.getInstance(request)
			.addMessages(messages)
			.addButton(KmssReturnPage.BUTTON_RETURN)
			.addButton(KmssReturnPage.BUTTON_CLOSE)
			.save(request);
			
			return getActionForward("failure", mapping, form, request, response);
		}
		//转正人员必须是试用、实习、试用延期的人员
		HrStaffPersonInfo personInfo = (HrStaffPersonInfo) getHrStaffPersonInfoServiceImp().findByPrimaryKey(orgId);
		if(personInfo !=null){
			List<String> positiveStatus  = Lists.newArrayList( "trial","trialDelay","practice");
			if(!positiveStatus.contains(personInfo.getFdStatus())){
				KmssMessages messages = new KmssMessages();
				messages.addMsg(new KmssMessage("hr-ratify:hrRatifyPositive.failure.needStatus"));

				KmssReturnPage.getInstance(request)
						.addMessages(messages)
						.addButton(KmssReturnPage.BUTTON_RETURN)
						.addButton(KmssReturnPage.BUTTON_CLOSE)
						.save(request);

				return getActionForward("failure", mapping, form, request, response);
			}
		}
		return  super.save(mapping, form, request, response);
	}

	private Boolean hashAccount(String sysPersonId) throws Exception{
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName("com.landray.kmss.sys.organization.model.SysOrgPerson");
		hqlInfo.setJoinBlock(", com.landray.kmss.hr.staff.model.HrStaffPersonInfo info");
		hqlInfo.setWhereBlock("sysOrgPerson.fdId = :id");
		hqlInfo.setParameter("id", sysPersonId);
		List data = ((ISysOrgPersonService)getBean("sysOrgPersonService")).findList(hqlInfo);
		return data.isEmpty();
	}
}
