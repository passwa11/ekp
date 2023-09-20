package com.landray.kmss.hr.staff.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.model.HrStaffAccumulationFund;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffAccumulationFundService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.service.IHrStaffSecurityService;
import com.landray.kmss.hr.staff.util.HrStaffAuthorityUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
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
public class HrStaffAccumulationFundAction extends HrStaffImportAction {
	private IHrStaffAccumulationFundService hrStaffAccumulationFundService;
	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	@Override
	protected IHrStaffAccumulationFundService getServiceImp(
			HttpServletRequest request) {
		if (hrStaffAccumulationFundService == null) {
			hrStaffAccumulationFundService = (IHrStaffAccumulationFundService) getBean(
					"hrStaffAccumulationFundService");
		}
		System.out.println(hrStaffAccumulationFundService.toString());

		return hrStaffAccumulationFundService;
	}

	protected IHrStaffPersonInfoService getHrStaffPersonInfoService() {
		if (hrStaffPersonInfoService == null) {
			hrStaffPersonInfoService = (IHrStaffPersonInfoService) getBean(
					"hrStaffPersonInfoService");
		}
		return hrStaffPersonInfoService;
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
		 .append(" and (hrStaffAccumulationFund.fdPersonInfo.fdName like :fdKey or hrStaffAccumulationFund.fdPersonInfo.fdOrgPerson.fdName like :fdKey");
		 whereBlock
		 .append(" or hrStaffAccumulationFund.fdPersonInfo.fdOrgPerson.fdLoginName like :fdKey");
		 whereBlock
		 .append(" or hrStaffAccumulationFund.fdIdCard like :fdKey");
		 whereBlock
		 .append(" or hrStaffAccumulationFund.fdProvidentFundInsuranceCompany like :fdKey");
		 whereBlock
		 .append(" or hrStaffAccumulationFund.fdIndividualProvidentFundAccount like :fdKey");
		 whereBlock
		 .append(" or hrStaffAccumulationFund.fdPlaceOfInsurancePayment like :fdKey");
		whereBlock
		 .append(" or hrStaffAccumulationFund.fdRemark like :fdKey)");
		 hqlInfo.setParameter("fdKey", "%" + _fdKey + "%");
	 }
	 String fdEnterPriseStartTime = request.getParameter("fdEnterPriseStartTime");
		String fdEnterPriseEndTime = request.getParameter("fdEnterPriseEndTime");		
		if (StringUtil.isNotNull(fdEnterPriseStartTime) && StringUtil.isNotNull(fdEnterPriseEndTime)) {
			whereBlock.append(" and hrStaffAccumulationFund.fdDeliveryDate  BETWEEN  :fdEnterPriseStartTime and :fdEnterPriseEndTime ");
			Date startDate = new Date();
			Date endDate = new Date();
			startDate = DateUtil.convertStringToDate(fdEnterPriseStartTime,
					DateUtil.TYPE_DATE, null);
			Calendar c = Calendar.getInstance();
			c.setTime(startDate);
			c.add(Calendar.DATE, -1);
			c.add(Calendar.HOUR, 23);
			c.add(Calendar.MINUTE, 59);
			c.add(Calendar.SECOND, 59);
			startDate = c.getTime();
			endDate = DateUtil.convertStringToDate(fdEnterPriseEndTime,
					DateUtil.TYPE_DATE, null);
			c.setTime(endDate);
			c.add(Calendar.HOUR, 23);
			c.add(Calendar.MINUTE, 59);
			c.add(Calendar.SECOND, 59);
			endDate = c.getTime();
			hqlInfo.setParameter("fdEnterPriseStartTime", startDate);
			hqlInfo.setParameter("fdEnterPriseEndTime", endDate);
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
	 .append(" and (hrStaffAccumulationFund.fdPersonInfo.fdStatus in  (:fdStatus)");
	 if (isNull) {
	 whereBlock
	 .append(" or hrStaffAccumulationFund.fdPersonInfo.fdStatus is null");
	 }
	 whereBlock.append(")");
	 hqlInfo.setParameter("fdStatus", fdStatus);
	 }
	 // 部门
	 if (StringUtil.isNotNull(_fdDept)) {
	 whereBlock
	 .append(" and (hrStaffAccumulationFund.fdPersonInfo.fdOrgParent.fdId = :fdDept or hrStaffAccumulationFund.fdPersonInfo.fdOrgPerson.hbmParent.fdId = :fdDept)");
	 hqlInfo.setParameter("fdDept", _fdDept);
	 }
	 whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock,
	 "hrStaffAccumulationFund", hqlInfo);
	 hqlInfo.setWhereBlock(whereBlock.toString());

		CriteriaUtil.buildHql(cv, hqlInfo, HrStaffAccumulationFund.class);
	 }

	@Override
	public String getTempletName() {
		return ResourceUtil
				.getString("hr-staff:hrStaffAccumulationFund.templetName");
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
			HrStaffAccumulationFund accumulationFund = getHrStaffAccumulationFund(
					personInfoId);
			UserOperHelper.logFind(accumulationFund);// 添加日志信息
			if (accumulationFund != null) {
				request.setAttribute("hrStaffAccumulationFund",
						accumulationFund);
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
	private HrStaffAccumulationFund getHrStaffAccumulationFund(
			String personInfoId) throws Exception {
		HrStaffAccumulationFund model = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdPersonInfo.fdId = :personInfoId");
		hqlInfo.setParameter("personInfoId", personInfoId);
		List<HrStaffAccumulationFund> list = getServiceImp(null).findPage(
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
			HrStaffAccumulationFund accumulationFund = getHrStaffAccumulationFund(
					fdPersonInfoId);
			if (accumulationFund == null) {
				isOk = true;
			} else {
				obj.put("message", ResourceUtil
						.getString("hr-staff:hrStaffAccumulationFund.exist"));
			}
		} else {
			obj
					.put(
							"message",
							ResourceUtil
									.getString(
											"hr-staff:hrStaffAccumulationFund.fdPersonInfo.nofind"));
		}

		obj.put("isOk", isOk);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(obj);
		response.getWriter().flush();
		response.getWriter().close();
	}

}
