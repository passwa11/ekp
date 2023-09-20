package com.landray.kmss.hr.staff.actions;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.model.HrStaffAccumulationFund;
import com.landray.kmss.hr.staff.model.HrStaffEmolumentWelfare;
import com.landray.kmss.hr.staff.model.HrStaffPerformanceContractImport;
import com.landray.kmss.hr.staff.service.IHrStaffImportService;
import com.landray.kmss.hr.staff.service.IHrStaffPerformanceContractImportService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.service.IHrStaffSecurityService;
import com.landray.kmss.hr.staff.util.HrStaffAuthorityUtil;
import com.landray.kmss.hr.staff.util.HrStaffImportUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

/**
 * 薪酬福利
 * 
 * @author 潘永辉 2017-1-14
 * 
 */
public class HrStaffPerformanceContractImportAction extends HrStaffImportAction {
	private IHrStaffPerformanceContractImportService hrStaffPerformanceContractImportService;
	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	@Override
	protected IHrStaffPerformanceContractImportService getServiceImp(
			HttpServletRequest request) {
		if (hrStaffPerformanceContractImportService == null) {
			hrStaffPerformanceContractImportService = (IHrStaffPerformanceContractImportService) getBean(
					"hrStaffPerformanceContractImportService");
		}

		return hrStaffPerformanceContractImportService;
	}

	protected IHrStaffPersonInfoService getHrStaffPersonInfoService() {
		if (hrStaffPersonInfoService == null) {
			hrStaffPersonInfoService = (IHrStaffPersonInfoService) getBean(
					"hrStaffPersonInfoService");
		}
		return hrStaffPersonInfoService;
	}
	@Override
	public ActionForward downloadTemplet(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			// 模板名称
			String templetName = getTempletName();
			// 构建模板文件
			HSSFWorkbook workbook = ((IHrStaffImportService) getServiceImp(request))
					.buildTempletWorkBook();

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

	 @Override
	 protected void changeFindPageHQLInfo(HttpServletRequest request,
	 HQLInfo hqlInfo) throws Exception {
	 super.changeFindPageHQLInfo(request, hqlInfo);
	 CriteriaValue cv = new CriteriaValue(request);
	
	 // 以下筛选属性需要手工定义筛选范围
	 String _fdKey = cv.poll("_fdKey");
	 String _fdDept = cv.poll("_fdDept");
	
	 String hqlWhere = hqlInfo.getWhereBlock();
	 StringBuffer whereBlock = null;
	 if (StringUtil.isNotNull(hqlWhere)) {
	 whereBlock = new StringBuffer(hqlWhere);
	 } else {
	 whereBlock = new StringBuffer("1 = 1");
	 }
	
	 // 姓名、登录名、工资账号和公积金账号
	 if (StringUtil.isNotNull(_fdKey)) {
		 whereBlock
		 .append(" and (hrStaffPerformanceContractImport.fdPersonInfo.fdName like :fdKey or hrStaffPerformanceContractImport.fdPersonInfo.fdOrgPerson.fdName like :fdKey");
		 whereBlock
		 .append(" or hrStaffPerformanceContractImport.fdPersonInfo.fdOrgPerson.fdLoginName like :fdKey");
		 whereBlock
		 .append(" or hrStaffPerformanceContractImport.fdEvaluationIndex like :fdKey");
		 whereBlock
		 .append(" or hrStaffPerformanceContractImport.fdEvaluationDimension like :fdKey");
		 whereBlock
		 .append(" or hrStaffPerformanceContractImport.fdTargetValue like :fdKey");
		 whereBlock
		 .append(" or hrStaffPerformanceContractImport.fdThirdLevelDepartment like :fdKey");
		whereBlock
		 .append(" or hrStaffPerformanceContractImport.fdSecondLevelDepartment like :fdKey");
		whereBlock
		 .append(" or hrStaffPerformanceContractImport.fdFirstLevelDepartment like :fdKey)");
		 hqlInfo.setParameter("fdKey", "%" + _fdKey + "%");
	 }
	 // 员工状态
	 String[] _fdStatus = cv.polls("_fdStatus");
	 if (_fdStatus != null && _fdStatus.length > 0) {
	 List<String> fdStatus = new ArrayList<String>();
	 boolean isNull = false;
	 for (String _fdStatu : _fdStatus) {
	 if ("official".equals(_fdStatu)) {
	 isNull = true;
	 }
	 fdStatus.add(_fdStatu);
	 }
	 whereBlock
	 .append(" and (hrStaffPerformanceContractImport.fdPersonInfo.fdStatus in  (:fdStatus)");
	 if (isNull) {
	 whereBlock
	 .append(" or hrStaffPerformanceContractImport.fdPersonInfo.fdStatus is null");
	 }
	 whereBlock.append(")");
	 hqlInfo.setParameter("fdStatus", fdStatus);
	 }
	 // 部门
	 if (StringUtil.isNotNull(_fdDept)) {
	 whereBlock
	 .append(" and (hrStaffPerformanceContractImport.fdPersonInfo.fdOrgParent.fdId = :fdDept or hrStaffPerformanceContractImport.fdPersonInfo.fdOrgPerson.hbmParent.fdId = :fdDept)");
	 hqlInfo.setParameter("fdDept", _fdDept);
	 }
	 whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock,
	 "hrStaffPerformanceContractImport", hqlInfo);
	 hqlInfo.setWhereBlock(whereBlock.toString());
		CriteriaUtil.buildHql(cv, hqlInfo, HrStaffPerformanceContractImport.class);
	 }

	@Override
	public String getTempletName() {
		return ResourceUtil
				.getString("hr-staff:hrStaffPerformanceContractImport.templetName");
	}

	/**
	 * 根据员工信息获取信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getByPerson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String personInfoId = request.getParameter("personInfoId");
		String paramKey = request.getParameter("paramKey");
		try {
			HrStaffPerformanceContractImport performanceContractImport = getHrStaffPerformanceContractImport(
					personInfoId);
			UserOperHelper.logFind(performanceContractImport);// 添加日志信息
			if (performanceContractImport != null) {
				request.setAttribute("HrStaffPerformanceContractImport",
						performanceContractImport);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			if (StringUtil.isNotNull(paramKey)) {
				return getActionForward(paramKey, mapping, form, request,
						response);
			} else {
				return getActionForward("view", mapping, form, request,
						response);
			}
		}
	}

	/**
	 * 根据工员ID获取数据
	 * 
	 * @param personInfoId
	 * @return
	 * @throws Exception
	 */
	private HrStaffPerformanceContractImport getHrStaffPerformanceContractImport(
			String personInfoId) throws Exception {
		HrStaffPerformanceContractImport model = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdPersonInfo.fdId = :personInfoId");
		hqlInfo.setParameter("personInfoId", personInfoId);
		List<HrStaffPerformanceContractImport> list = getServiceImp(null).findPage(
				hqlInfo).getList();
		if (!list.isEmpty()) {
			model = list.get(0);
		}
		return model;
	}

	/**
	 * 检查用户信息是否正常
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void checkPerson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject obj = new JSONObject();
		boolean isOk = false;
		// 检查用户是否存在
		String fdPersonInfoId = request.getParameter("fdPersonInfoId");
		IBaseModel model = getHrStaffPersonInfoService().findByPrimaryKey(
				fdPersonInfoId, null, true);
		// 添加日志信息
		if (UserOperHelper.allowLogOper("checkPerson",
				getServiceImp(request).getModelName())) {
			UserOperContentHelper.putFind(model);
		}
		if (model != null) {
			// 如果用户已存在，再检查用户是否已经有薪酬福利信息（每个员工的薪酬福利信息只能有一条）
			HrStaffPerformanceContractImport performanceContractImport = getHrStaffPerformanceContractImport(
					fdPersonInfoId);
			if (performanceContractImport == null) {
				isOk = true;
			} else {
				obj.put("message", ResourceUtil
						.getString("hr-staff:hrStaffPerformanceContractImport.exist"));
			}
		} else {
			obj
					.put(
							"message",
							ResourceUtil
									.getString(
											"hr-staff:hrStaffPerformanceContractImport.fdPersonInfo.nofind"));
		}

		obj.put("isOk", isOk);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(obj);
		response.getWriter().flush();
		response.getWriter().close();
	}

}
