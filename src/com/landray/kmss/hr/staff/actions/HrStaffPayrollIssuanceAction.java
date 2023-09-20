package com.landray.kmss.hr.staff.actions;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.staff.forms.HrStaffPayrollIssuanceForm;
import com.landray.kmss.hr.staff.model.HrStaffPayrollIssuance;
import com.landray.kmss.hr.staff.model.HrStaffSalaryInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPayrollIssuanceService;
import com.landray.kmss.hr.staff.service.IHrStaffSalaryInfoService;
import com.landray.kmss.hr.staff.util.HrStaffAuthorityUtil;
import com.landray.kmss.hr.staff.util.HrStaffImportUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class HrStaffPayrollIssuanceAction extends ExtendAction {
	private IHrStaffPayrollIssuanceService hrStaffPayrollIssuanceService;
	private ISysOrgPersonService sysOrgPersonService;	
	
	@Override
	protected IHrStaffPayrollIssuanceService getServiceImp(
			HttpServletRequest request) {
		if (hrStaffPayrollIssuanceService == null) {
			hrStaffPayrollIssuanceService = (IHrStaffPayrollIssuanceService) getBean("hrStaffPayrollIssuanceService");
		}
		return hrStaffPayrollIssuanceService;
	}
	
	protected ISysOrgPersonService getSysOrgPersonService (
			HttpServletRequest request) {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}
	
	private static Map<String, String> canDownMap = new HashMap<String, String>();

	public ActionForward downloadAjax(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = UserUtil.getUser().getFdId();
		if (!canDownMap.containsKey(userId)) {
			canDownMap.put(userId, "true");
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(canDownMap.get(userId));
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	/**
	 * 获取下载的模板文件名称
	 * 
	 * @return
	 */

	public ActionForward downloadTemplet(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String userId = UserUtil.getUser().getFdId();
		try {
			canDownMap.put(userId, "false");
			// 模板名称
			String templetName = getTempletName();
			// 构建模板文件
			String fdExportDeptId = request.getParameter("fdExportDeptId");
			HSSFWorkbook workbook = getServiceImp(request)
					.buildTempletWorkBook(fdExportDeptId);

			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment;fileName="
					+ HrStaffImportUtil.encodeFileName(request, templetName));
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			// 添加日志信息
			if (UserOperHelper.allowLogOper("downloadTemplet",
					getServiceImp(request).getModelName())) {
				UserOperHelper.setEventType(ResourceUtil
						.getString("hr-staff:hrStaff.import.button.download"));
				UserOperContentHelper.putFind("", templetName,
						getServiceImp(request).getModelName());
			}
			return null;
		} catch (IOException e) {
			messages.addError(e);
		} finally {
			canDownMap.remove(userId);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}
	
	public String getTempletName() {
		return ResourceUtil
				.getString("hr-staff:hrStaffPayrollIssuance.templetName");
	}


	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		HrStaffPayrollIssuanceForm hrStaffPayrollIssuanceForm = (HrStaffPayrollIssuanceForm) form;
		// 创建者
		hrStaffPayrollIssuanceForm.setFdCreatorId(UserUtil.getUser().getFdId());
		hrStaffPayrollIssuanceForm.setFdCreatorName(UserUtil.getUser()
				.getFdName());
		hrStaffPayrollIssuanceForm.setFdCreateTime(DateUtil
				.convertDateToString(new Date(), DateUtil.TYPE_DATETIME,
						request.getLocale()));
		KmssMessages messages = new KmssMessages();
		
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).add(
					(IExtendForm) hrStaffPayrollIssuanceForm,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}
		
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 打开工资阅读页面。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回view页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward viewSalary(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdId = request.getParameter("fdId");
		try {
			IHrStaffSalaryInfoService hrStaffSalaryInfoService = (IHrStaffSalaryInfoService)SpringBeanUtil.getBean("hrStaffSalaryInfoService");
			HrStaffSalaryInfo hrStaffSalaryInfo = (HrStaffSalaryInfo) hrStaffSalaryInfoService.findByPrimaryKey(fdId);
			if(hrStaffSalaryInfo!=null && !UserUtil.getKMSSUser().getPerson().getFdId().equals(hrStaffSalaryInfo.getFdPersonId())){
				return getActionForward("e403", mapping, form, request, response);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			if(MobileUtil.PC != MobileUtil.getClientType(request)) {
                return getActionForward("viewSalaryMobile", mapping, form, request, response);
            } else {
                return getActionForward("viewSalaryPc", mapping, form, request, response);
            }
		}
	}
	
	public ActionForward salaryLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject salaryContent = null;
		String fdId = request.getParameter("fdId");
		HrStaffPayrollIssuanceForm hrStaffPayrollIssuanceForm = (HrStaffPayrollIssuanceForm) form;
		try {
			Boolean islogin = getSysOrgPersonService(request).validatePassword(UserUtil.getKMSSUser().getPerson().getFdId(), hrStaffPayrollIssuanceForm.getFdPassword());
			
			if(islogin){
				salaryContent= getServiceImp(request).getSalaryContent(fdId);
				salaryContent.put("isLogin", true);
			}else{
				salaryContent = new JSONObject();
				salaryContent.put("isLogin", false);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(salaryContent.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	public ActionForward setSalaryTodoDone(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdId = request.getParameter("fdId");
		JSONObject setTodoDone  = new JSONObject();
		try {
			setTodoDone = getServiceImp(request).updateSalaryTodoDone(fdId);
		} catch (Exception e) {
			messages.addError(e);
			setTodoDone.put("isSet", false);
			setTodoDone.put("errorCode", 2);
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(setTodoDone.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, HrStaffPayrollIssuance.class);
		String hqlWhere = hqlInfo.getWhereBlock();
		StringBuffer whereBlock = null;
		if (StringUtil.isNotNull(hqlWhere)) {
			whereBlock = new StringBuffer(hqlWhere);
		}
		if(null == whereBlock){
			whereBlock = new StringBuffer("1 = 1");
		}

		if (!UserUtil.checkRole("ROLE_HRSTAFF_PAYMENT")) {
			// 如果没有工资条发放角色，需要判断机构授权
			whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffPayrollIssuance", hqlInfo);
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
	}
	
	public ActionForward success(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return mapping.findForward("success");

	}
	
}
