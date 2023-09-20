package com.landray.kmss.hr.staff.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.model.HrStaffAccumulationFund;
import com.landray.kmss.hr.staff.model.HrStaffSecurity;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.service.IHrStaffSecurityService;
import com.landray.kmss.hr.staff.util.HrStaffAuthorityUtil;
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
public class HrStaffSecurityAction extends HrStaffImportAction {
	private IHrStaffSecurityService hrStaffSecurityService;
	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	@Override
	protected IHrStaffSecurityService getServiceImp(
			HttpServletRequest request) {
		if (hrStaffSecurityService == null) {
			hrStaffSecurityService = (IHrStaffSecurityService) getBean(
					"hrStaffSecurityService");
		}
		System.out.println(hrStaffSecurityService.toString());

		return hrStaffSecurityService;
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
	 .append(" and (hrStaffSecurity.fdPersonInfo.fdName like :fdKey or hrStaffSecurity.fdPersonInfo.fdOrgPerson.fdName like :fdKey");
	 whereBlock
	 .append(" or hrStaffSecurity.fdPersonInfo.fdOrgPerson.fdLoginName like :fdKey");
	 whereBlock
	 .append(" or hrStaffSecurity.fdIdCard like :fdKey");
	 whereBlock
	 .append(" or hrStaffSecurity.fdPlaceOfInsurancePayment like :fdKey");
	 whereBlock
	 .append(" or hrStaffSecurity.fdRemark like :fdKey)");
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
	 .append(" and (hrStaffSecurity.fdPersonInfo.fdStatus in (:fdStatus)");
	 if (isNull) {
	 whereBlock
	 .append(" or hrStaffSecurity.fdPersonInfo.fdStatus is null");
	 }
	 whereBlock.append(")");
	 hqlInfo.setParameter("fdStatus", fdStatus);
	 }
	 // 部门
	 if (StringUtil.isNotNull(_fdDept)) {
	 whereBlock
	 .append(" and (hrStaffSecurity.fdPersonInfo.fdOrgParent.fdId = :fdDept or hrStaffSecurity.fdPersonInfo.fdOrgPerson.hbmParent.fdId =:fdDept)");
	 hqlInfo.setParameter("fdDept", _fdDept);
	 }
	 whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock,
	 "hrStaffSecurity", hqlInfo);
	 hqlInfo.setWhereBlock(whereBlock.toString());

		CriteriaUtil.buildHql(cv, hqlInfo, HrStaffSecurity.class);
	 }

	@Override
	public String getTempletName() {
		return ResourceUtil
				.getString("hr-staff:hrStaffSecurity.templetName");
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
			HrStaffSecurity security = getHrStaffSecurity(
					personInfoId);
			UserOperHelper.logFind(security);// 添加日志信息
			if (security != null) {
				request.setAttribute("hrStaffSecurity",
						security);
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
	private HrStaffSecurity getHrStaffSecurity(
			String personInfoId) throws Exception {
		HrStaffSecurity model = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdPersonInfo.fdId = :personInfoId");
		hqlInfo.setParameter("personInfoId", personInfoId);
		List<HrStaffSecurity> list = getServiceImp(null).findPage(
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
			HrStaffSecurity security = getHrStaffSecurity(
					fdPersonInfoId);
			if (security == null) {
				isOk = true;
			} else {
				obj.put("message", ResourceUtil
						.getString("hr-staff:hrStaffSecurity.exist"));
			}
		} else {
			obj
					.put(
							"message",
							ResourceUtil
									.getString(
											"hr-staff:hrStaffSecurity.fdPersonInfo.nofind"));
		}

		obj.put("isOk", isOk);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(obj);
		response.getWriter().flush();
		response.getWriter().close();
	}

}
