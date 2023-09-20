package com.landray.kmss.hr.staff.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.model.HrStaffEmolumentWelfare;
import com.landray.kmss.hr.staff.service.IHrStaffEmolumentWelfareService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.util.HrStaffAuthorityUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
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
public class HrStaffEmolumentWelfareAction extends HrStaffImportAction {
	private IHrStaffEmolumentWelfareService hrStaffEmolumentWelfareService;
	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	@Override
	protected IHrStaffEmolumentWelfareService getServiceImp(
			HttpServletRequest request) {
		if (hrStaffEmolumentWelfareService == null) {
			hrStaffEmolumentWelfareService = (IHrStaffEmolumentWelfareService) getBean("hrStaffEmolumentWelfareService");
		}
		return hrStaffEmolumentWelfareService;
	}

	protected IHrStaffPersonInfoService getHrStaffPersonInfoService() {
		if (hrStaffPersonInfoService == null) {
            hrStaffPersonInfoService = (IHrStaffPersonInfoService) getBean("hrStaffPersonInfoService");
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
					.append(" and (hrStaffEmolumentWelfare.fdPersonInfo.fdName like :fdKey or hrStaffEmolumentWelfare.fdPersonInfo.fdOrgPerson.fdName like :fdKey");
			whereBlock
					.append(" or hrStaffEmolumentWelfare.fdPersonInfo.fdOrgPerson.fdLoginName like :fdKey");
			whereBlock
					.append(" or hrStaffEmolumentWelfare.fdPayrollAccount like :fdKey");
			whereBlock
					.append(" or hrStaffEmolumentWelfare.fdSurplusAccount like :fdKey");
			whereBlock
					.append(" or hrStaffEmolumentWelfare.fdSocialSecurityNumber like :fdKey)");
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
					.append(" and (hrStaffEmolumentWelfare.fdPersonInfo.fdStatus in (:fdStatus)");
			if (isNull) {
				whereBlock
						.append(" or hrStaffEmolumentWelfare.fdPersonInfo.fdStatus is null");
			}
			whereBlock.append(")");
			hqlInfo.setParameter("fdStatus", fdStatus);
		}
		// 部门
		if (StringUtil.isNotNull(_fdDept)) {
			whereBlock
					.append(" and (hrStaffEmolumentWelfare.fdPersonInfo.fdOrgParent.fdId = :fdDept or hrStaffEmolumentWelfare.fdPersonInfo.fdOrgPerson.hbmParent.fdId = :fdDept)");
			hqlInfo.setParameter("fdDept", _fdDept);
		}
		whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffEmolumentWelfare", hqlInfo);
		hqlInfo.setWhereBlock(whereBlock.toString());
	}

	@Override
	public String getTempletName() {
		return ResourceUtil
				.getString("hr-staff:hrStaffEmolumentWelfare.templetName");
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
			HrStaffEmolumentWelfare emolumentWelfare = getHrStaffEmolumentWelfare(personInfoId);
			UserOperHelper.logFind(emolumentWelfare);// 添加日志信息
			if (emolumentWelfare != null) {
				request.setAttribute("hrStaffEmolumentWelfare",
						emolumentWelfare);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
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
	private HrStaffEmolumentWelfare getHrStaffEmolumentWelfare(
			String personInfoId) throws Exception {
		HrStaffEmolumentWelfare model = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdPersonInfo.fdId = :personInfoId");
		hqlInfo.setParameter("personInfoId", personInfoId);
		List<HrStaffEmolumentWelfare> list = getServiceImp(null).findPage(
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
			HrStaffEmolumentWelfare emolumentWelfare = getHrStaffEmolumentWelfare(fdPersonInfoId);
			if (emolumentWelfare == null) {
				isOk = true;
			} else {
				obj.put("message", ResourceUtil
						.getString("hr-staff:hrStaffEmolumentWelfare.exist"));
			}
		} else {
			obj
					.put(
							"message",
							ResourceUtil
									.getString("hr-staff:hrStaffEmolumentWelfare.fdPersonInfo.nofind"));
		}

		obj.put("isOk", isOk);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(obj);
		response.getWriter().flush();
		response.getWriter().close();
	}

}
