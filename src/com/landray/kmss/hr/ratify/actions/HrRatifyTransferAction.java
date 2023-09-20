package com.landray.kmss.hr.ratify.actions;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.ratify.forms.HrRatifyTransferForm;
import com.landray.kmss.hr.ratify.model.HrRatifySalary;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.model.HrRatifyTransfer;
import com.landray.kmss.hr.ratify.service.IHrRatifySalaryService;
import com.landray.kmss.hr.ratify.service.IHrRatifyTransferService;
import com.landray.kmss.hr.staff.forms.HrStaffTrackRecordForm;
import com.landray.kmss.hr.staff.model.HrStaffEmolumentWelfareDetalied;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffEmolumentWelfareDetaliedService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.print.interfaces.ISysPrintLogCoreService;
import com.landray.kmss.sys.print.interfaces.ISysPrintMainCoreService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HtmlToMht;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.excel.ExcelOutput;
import com.landray.kmss.util.excel.ExcelOutputImp;
import com.landray.kmss.util.excel.WorkBook;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HrRatifyTransferAction extends HrRatifyMainAction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HrRatifyTransferAction.class);

    private IHrRatifyTransferService hrRatifyTransferService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (hrRatifyTransferService == null) {
            hrRatifyTransferService = (IHrRatifyTransferService) getBean("hrRatifyTransferService");
        }
        return hrRatifyTransferService;
    }

	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	public IHrStaffPersonInfoService getHrStaffPersonInfoService() {
		if (hrStaffPersonInfoService == null) {
			hrStaffPersonInfoService = (IHrStaffPersonInfoService) getBean("hrStaffPersonInfoService");
		}
		return hrStaffPersonInfoService;
	}

	private IHrStaffEmolumentWelfareDetaliedService hrStaffEmolumentWelfareDetaliedService;

	public IHrStaffEmolumentWelfareDetaliedService getHrStaffEmolumentWelfareDetaliedService() {
		if (hrStaffEmolumentWelfareDetaliedService == null) {
			hrStaffEmolumentWelfareDetaliedService = (IHrStaffEmolumentWelfareDetaliedService) getBean(
					"hrStaffEmolumentWelfareDetaliedService");
		}
		return hrStaffEmolumentWelfareDetaliedService;
	}

	private IHrRatifySalaryService hrRatifySalaryService;

	public IHrRatifySalaryService getHrRatifySalaryServiceImp() {
		if (hrRatifySalaryService == null) {
			hrRatifySalaryService = (IHrRatifySalaryService) getBean("hrRatifySalaryService");
		}
		return hrRatifySalaryService;
	}

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, HrRatifyTransfer.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.hr.ratify.util.HrRatifyUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.hr.ratify.model.HrRatifyTransfer.class);
        com.landray.kmss.hr.ratify.util.HrRatifyUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		SysOrgElement orgElement = (SysOrgElement) getSysOrgCoreService().findByPrimaryKey(userId);
		((IHrRatifyTransferService) getServiceImp(request)).initFormSetting(
				(IExtendForm) form, new RequestContext(request));
		HrRatifyTransferForm hrRatifyTransferForm = (HrRatifyTransferForm) super.createNewForm(
				mapping, form, request, response);
		hrRatifyTransferForm.setDocTemplateName(
				getTemplatePath(hrRatifyTransferForm.getDocTemplateId()));
		if (null != orgElement) {
			hrRatifyTransferForm.setFdTransferStaffId(orgElement.getFdId());
			hrRatifyTransferForm.setFdTransferStaffName(orgElement.getFdName());
			if (null != orgElement.getFdParent()) {
				hrRatifyTransferForm.setFdTransferLeaveDeptId(orgElement.getFdParent().getFdId());
				hrRatifyTransferForm.setFdTransferLeaveDeptName(orgElement.getFdParent().getFdName());
			}
		}
        return hrRatifyTransferForm;
    }

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.loadActionForm(mapping, form, request, response);
		HrRatifyTransferForm hrRatifyTransferForm = (HrRatifyTransferForm) form;
		String id = hrRatifyTransferForm.getFdTransferStaffId();

		// 需要查询出调离人员的岗位信息post
		HrStaffPersonInfo personInfo = (HrStaffPersonInfo) getHrStaffPersonInfoService(
				request).updateGetPersonInfo(id);
		JSONArray posts = new JSONArray();
		List<SysOrgPost> fdOrgPosts = personInfo.getFdOrgPosts();
		if(!CollectionUtils.isEmpty(fdOrgPosts)){
			for (SysOrgPost p : fdOrgPosts) {
				JSONObject pJson = new JSONObject();
				pJson.put("id", p.getFdId());
				pJson.put("name", p.getFdName());
				posts.add(pJson);
			}
		}
		request.setAttribute("posts",posts);
		String leavePostId = hrRatifyTransferForm.getFdTransferLeavePostIds();
		request.setAttribute("leavePostId",leavePostId);
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
			HrRatifyTransferForm ratifyForm = (HrRatifyTransferForm) form;
			HrRatifyTemplate template = (HrRatifyTemplate) getHrRatifyTemplateService()
					.findByPrimaryKey(ratifyForm.getDocTemplateId());
			boolean enable = getSysPrintMainCoreService()
					.isEnablePrintTemplate(template, null, request);

			HrRatifyTransfer main = (HrRatifyTransfer) getServiceImp(
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
	 * <p>员工关系-人事调动列表</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward transferManageList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
			}
			if (isReserve) {
				orderby += " desc";
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setModelName("com.landray.kmss.hr.ratify.model.HrRatifyMain");
			Page page = ((IHrRatifyTransferService) getServiceImp(request)).getTransferManagePage(hqlInfo, request);
			UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("transferManageList", mapping, form, request, response);
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
			WorkBook wb = ((IHrRatifyTransferService) getServiceImp(request)).export(hqlInfo, request);
			ExcelOutput output = new ExcelOutputImp();
			response.setCharacterEncoding("UTF-8");
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
	 * <p>下载导入模板</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward downloadTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			WorkBook wb = ((IHrRatifyTransferService) getServiceImp(request)).buildTemplateWorkbook(request);
			ExcelOutput output = new ExcelOutputImp();
			output.output(wb, response);
			// 添加日志信息
			if (UserOperHelper.allowLogOper("downloadTemplate", getServiceImp(request).getModelName())) {
				UserOperHelper
						.setEventType(ResourceUtil.getString("hr-ratify:hrRatify.concern.transfer.template.download"));
			}
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
	 * <p>批量导入员工异动信息</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward importTransfer(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject importResult = new JSONObject();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			importResult.put("otherErrors", new JSONArray());
			HrRatifyTransferForm mainForm = (HrRatifyTransferForm) form;
			FormFile file = mainForm.getFile();
			importResult = ((IHrRatifyTransferService) getServiceImp(request)).addImportData(file.getInputStream(),
					request.getLocale());
		} catch (Exception e) {
			e.printStackTrace();
			importResult.put("hasError", 1);
			importResult.put("importMsg", ResourceUtil.getString("hr-ratify:hrRatify.concern.transfer.import.fail"));
			importResult.getJSONArray("otherErrors").add(e.getMessage());
		}
		String result = replaceCharacter(importResult.toString());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("<script>parent.callback(" + result + ");</script>");
		return null;
	}

	private String replaceCharacter(String oriString) throws Exception {
		Pattern p = Pattern.compile("\\\\n|\\\\r|\\\\r\\\\n");
		Matcher m = p.matcher(oriString);
		String result = m.replaceAll("<br>"); // 将内容中的换行符替换，避免前台JSON解析出错
		p = Pattern.compile("'");
		m = p.matcher(result);
		result = m.replaceAll("‘"); // 将内容中的'替换成‘
		return result;
	}

	/**
	 * <p>新增调动调薪</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward addTransferPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();

		try {
			String fdId = request.getParameter("fdId");
			
			boolean hrRatifyTransfer = selectTransferProcess(request, fdId);
			boolean hrRatifySalary = selectSalaryProcess(fdId);
			if(hrRatifyTransfer||hrRatifySalary){
				throw new KmssException(new KmssMessage("hr-ratify:hrRatifyTransfer.msg.notAllow"));
			}
			
			HrStaffPersonInfo personInfo = (HrStaffPersonInfo) getHrStaffPersonInfoService().findByOrgPersonId(fdId);
			HrStaffTrackRecordForm recordForm = new HrStaffTrackRecordForm();
			if (null != personInfo.getFdOrgPerson()) {
				recordForm.setFdOrgPersonId(personInfo.getFdOrgPerson().getFdId());
				recordForm.setFdOrgPersonName(personInfo.getFdOrgPerson().getFdName());
			}
			recordForm.setFdPersonInfoId(personInfo.getFdId());
			recordForm.setFdPersonInfoName(personInfo.getFdName());
			recordForm.setFdTransDate(DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME));
			Double salary = getHrStaffEmolumentWelfareDetaliedService().getSalaryByStaffId(personInfo);
			request.setAttribute("currSalary", salary);
			request.setAttribute(getFormName(recordForm, request), recordForm);
			request.setAttribute("personInfo", personInfo);
		} catch (Exception e) {
			messages.addError(e);
			logger.error("调薪报错：", e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("addTransferPage", mapping, form, request, response);
		}
	}

	/**
	 * <p>删除调薪</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public ActionForward deleteSalaryPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String ids = request.getParameter("personId");
			List<HrStaffPersonInfo> list = getHrStaffPersonInfoService().findByPrimaryKeys(ids.split(";"));
			request.setAttribute("list", list);
			request.setAttribute("fdId", request.getParameter("fdId"));

		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("salaryCancel", mapping, form, request, response);
		}
	}

	/**
	 * <p>删除调动</p>
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteTrackPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String ids = request.getParameter("personId");
			List<HrStaffPersonInfo> list = getHrStaffPersonInfoService().findByPrimaryKeys(ids.split(";"));
			request.setAttribute("list", list);
			request.setAttribute("fdId", request.getParameter("fdId"));

		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("trackCancel", mapping, form, request, response);
		}
	}

	/**
	 * 查询当前选择人员的调岗流程（找生效时间最晚的）
	 */
	private boolean selectTransferProcess(HttpServletRequest request, String fdId) throws Exception{
		HQLInfo hqlInfo = new HQLInfo();
    	String whereBlock = " hrRatifyTransfer.fdTransferStaff.fdId =:fdId ";
    	hqlInfo.setParameter("fdId",fdId);
    	hqlInfo.setWhereBlock(whereBlock);
    	hqlInfo.setOrderBy("hrRatifyTransfer.fdTransferDate desc ");
    	List<HrRatifyTransfer> list = getServiceImp(request).findList(hqlInfo);
    	if(!ArrayUtil.isEmpty(list)){	//存在一个或一个以上的调岗流程（可能多次调岗）
    		Date current = new Date();
    		Date fdTransferDate = list.get(0).getFdTransferDate();	//取最近一次的调岗流程做判断
    		boolean flag = current.after(fdTransferDate);			//当前时间在生效时间之后说明没有生效，说明此流程可以制约此次手动添加动作
    		return flag;
    	}else{
    		return false;
    	}
	}
	
	/**
	 * 查询当前选择人员的调薪流程（找生效时间最晚的）
	 */
	private boolean selectSalaryProcess(String fdId) throws Exception{
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = " hrRatifySalary.fdSalaryStaff.fdId =:fdId ";
		hqlInfo.setParameter("fdId",fdId);
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy("hrRatifySalary.fdSalaryDate desc ");
		List<HrRatifySalary> list = getHrRatifySalaryServiceImp().findList(hqlInfo);
		if(!ArrayUtil.isEmpty(list)){	//存在一个或一个以上的调薪流程（可能多次调薪）
			Date current = new Date();
			Date fdSalaryDate = list.get(0).getFdSalaryDate();	//取最近一次的调薪流程做判断
			boolean flag = current.after(fdSalaryDate);			//当前时间在生效时间之后说明没有生效，说明此流程可以制约此次手动添加动作
			return flag;
		}else{
			return false;
		}
	}
	
	public ActionForward editSalary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String fdId = request.getParameter("fdId");
			String orgPersonId = request.getParameter("orgId");
			boolean hrRatifyTransfer = selectTransferProcess(request, fdId);
			boolean hrRatifySalary = selectSalaryProcess(fdId);
			if(hrRatifyTransfer||hrRatifySalary){
				throw new KmssException(new KmssMessage("hr-ratify:hrRatifyTransfer.msg.notAllow"));
			}
			
			HrStaffEmolumentWelfareDetalied reward = (HrStaffEmolumentWelfareDetalied)getHrStaffEmolumentWelfareDetaliedService().findByPrimaryKey(fdId);
			Double fdAfterEmolument = reward.getFdAfterEmolument();
			Double fdBeforeEmolument = reward.getFdBeforeEmolument();
			Date fdEffectTime = reward.getFdEffectTime();
			
			HrStaffTrackRecordForm recordForm = new HrStaffTrackRecordForm();
			recordForm.setFdId(fdId);
			recordForm.setFdTransSalary(fdAfterEmolument);
			recordForm.setFdBeforSalary(fdBeforeEmolument);
			recordForm.setFdTransDate(DateUtil.convertDateToString(fdEffectTime, "yyyy-MM-dd"));
			
			HrStaffPersonInfo personInfo = (HrStaffPersonInfo) getHrStaffPersonInfoService().findByOrgPersonId(orgPersonId);
			
			if (null != personInfo.getFdOrgPerson()) {
				recordForm.setFdOrgPersonId(personInfo.getFdOrgPerson().getFdId());
				recordForm.setFdOrgPersonName(personInfo.getFdOrgPerson().getFdName());
			}
			
			recordForm.setFdPersonInfoId(personInfo.getFdId());
			recordForm.setFdPersonInfoName(personInfo.getFdName());
			Double salary = getHrStaffEmolumentWelfareDetaliedService().getSalaryByStaffId(personInfo);
			request.setAttribute("currSalary", salary);
			request.setAttribute(getFormName(recordForm, request), recordForm);
			request.setAttribute("personInfo", personInfo);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("editSalary", mapping, form, request, response);
		}
	}


}
