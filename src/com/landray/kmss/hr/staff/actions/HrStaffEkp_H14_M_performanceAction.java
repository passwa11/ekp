package com.landray.kmss.hr.staff.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.autonomy.utilities.DateUtils;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.model.Ekp_H14_M;
import com.landray.kmss.hr.staff.model.Ekp_H14_M_detailed;
import com.landray.kmss.hr.staff.model.Ekp_H14_M_performance;
import com.landray.kmss.hr.staff.model.HrStaffAccumulationFund;
import com.landray.kmss.hr.staff.model.HrStaffEmolumentWelfare;
import com.landray.kmss.hr.staff.model.HrStaffPerformanceSearch;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffEkp_H14_InternService;
import com.landray.kmss.hr.staff.service.IHrStaffEkp_H14_M_performanceService;
import com.landray.kmss.hr.staff.service.IHrStaffPerformanceSearchService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.service.IHrStaffSecurityService;
import com.landray.kmss.hr.staff.util.HrStaffAuthorityUtil;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
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
public class HrStaffEkp_H14_M_performanceAction extends HrStaffImportAction {
	private IHrStaffEkp_H14_M_performanceService hrStaffEkp_H14_M_performanceService;
	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	@Override
	protected IHrStaffEkp_H14_M_performanceService getServiceImp(
			HttpServletRequest request) {
		if (hrStaffEkp_H14_M_performanceService == null) {
			hrStaffEkp_H14_M_performanceService = (IHrStaffEkp_H14_M_performanceService) getBean(
					"hrStaffEkp_H14_M_performanceService");
		}

		return hrStaffEkp_H14_M_performanceService;
	}

	protected IHrStaffPersonInfoService getHrStaffPersonInfoService() {
		if (hrStaffPersonInfoService == null) {
			hrStaffPersonInfoService = (IHrStaffPersonInfoService) getBean(
					"hrStaffPersonInfoService");
		}
		return hrStaffPersonInfoService;
	}

	 @SuppressWarnings("deprecation")
	@Override
	 protected void changeFindPageHQLInfo(HttpServletRequest request,
	 HQLInfo hqlInfo) throws Exception {
	 super.changeFindPageHQLInfo(request, hqlInfo);
	 CriteriaValue cv = new CriteriaValue(request);

		hqlInfo.setOrderBy("");
//
//    	hqlInfo.setJoinBlock(" left join com.landray.kmss.hr.staff.model.Ekp_H14_M_performance_detail1 ekp_H14_M_performance_detail1");
	 // 以下筛选属性需要手工定义筛选范围
//	 String _fdKey = cv.poll("_fdKey");
//	 String _fdDept = cv.poll("_fdDept");
	
	 String hqlWhere = hqlInfo.getWhereBlock();
	 StringBuffer whereBlock = null;
	 if (StringUtil.isNotNull(hqlWhere)) {
	 whereBlock = new StringBuffer(hqlWhere);
	 } else {
	 whereBlock = new StringBuffer("1 = 1");
	 }
	
	 // 姓名、登录名、工资账号和公积金账号
//	 if (StringUtil.isNotNull(_fdKey)) {
//		 whereBlock
//		 .append(" and (ekp_H14_M_detailed.ekp_H14_M.fdGangWeiMingChen.fdName like :fdKey");
//		 whereBlock
//		 .append(" or ekp_H14_M_detailed.ekp_H14_M.fdBeiKaoHeRenXingMing.fdName like :fdKey");
//		 hqlInfo.setParameter("fdKey", "%" + _fdKey + "%");
//		 whereBlock
//		 .append(" or ekp_H14_M_detailed.ekp_H14_M.fdKaoHeJieShuShiJian = :fdKey1");
//		 whereBlock
//		 .append(" or ekp_H14_M_detailed.ekp_H14_M.fdKaoHeKaiShiShiJian = :fdKey1");
//		 hqlInfo.setParameter("fdKey1", DateUtil.convertStringToDate(_fdKey));
//			whereBlock.append(")");
////		 whereBlock
////		 .append(" or ekp_H14_M.fdTargetValue like :fdKey");
//	 }

	 // 部门
//	 if (StringUtil.isNotNull(_fdDept)) {
////	 whereBlock
////	 .append(" and (ekp_H14_M.fdSuoShuFenBu.fdId = :fdDept )");
//
//		 whereBlock
//		 .append(" and (ekp_H14_M_detailed.ekp_H14_M.fdSanJiBuMen.fdId= :fdDept ");
//		whereBlock
//		 .append(" or ekp_H14_M_detailed.ekp_H14_M.fdErJiBuMen.fdId= :fdDept ");
//		whereBlock
//		 .append(" or ekp_H14_M_detailed.ekp_H14_M.fdYiJiBuMen.fdId= :fdDept )");
//		 hqlInfo.setParameter("fdDept", _fdDept);
//	 }
	 whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock,
	 "ekp_H14_M_performance", hqlInfo);
	 hqlInfo.setWhereBlock(whereBlock.toString());
		CriteriaUtil.buildHql(cv, hqlInfo, Ekp_H14_M_performance.class);
	 }

	@Override
	public String getTempletName() {
		return ResourceUtil
				.getString("hr-staff:Ekp_H14_M.templetName");
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
			HrStaffPerformanceSearch performanceContractImport = getHrStaffPerformanceSearch(
					personInfoId);
			UserOperHelper.logFind(performanceContractImport);// 添加日志信息
			if (performanceContractImport != null) {
				request.setAttribute("HrStaffPerformanceSearch",
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
	private HrStaffPerformanceSearch getHrStaffPerformanceSearch(
			String personInfoId) throws Exception {
		HrStaffPerformanceSearch model = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdPersonInfo.fdId = :personInfoId");
		hqlInfo.setParameter("personInfoId", personInfoId);
		List<HrStaffPerformanceSearch> list = getServiceImp(null).findPage(
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
			HrStaffPerformanceSearch performanceContractImport = getHrStaffPerformanceSearch(
					fdPersonInfoId);
			if (performanceContractImport == null) {
				isOk = true;
			} else {
				obj.put("message", ResourceUtil
						.getString("hr-staff:hrStaffPerformanceSearch.exist"));
			}
		} else {
			obj
					.put(
							"message",
							ResourceUtil
									.getString(
											"hr-staff:hrStaffPerformanceSearch.fdPersonInfo.nofind"));
		}

		obj.put("isOk", isOk);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(obj);
		response.getWriter().flush();
		response.getWriter().close();
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
	public ActionForward exportPerson(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = UserUtil.getUser().getFdId();
		try {
			canDownMap.put(userId, "false");
			HQLInfo hqlInfo = new HQLInfo();
			// 排序规则：层级长度，层级，排序号，名称
//			hqlInfo.setOrderBy(
//					"length(hrStaffPersonInfo.fdOrgParent.fdHierarchyId), hrStaffPersonInfo.fdOrgParent.fdHierarchyId, hrStaffPersonInfo.fdOrgParent.fdOrder, hrStaffPersonInfo.fdOrgPerson.fdOrder, hrStaffPersonInfo.fdOrgPerson."
//							+ SysLangUtil.getLangFieldName("fdName"));
//			String personStatus = request.getParameter("personStatus");
			StringBuffer whereBlock = new StringBuffer();
//			if (StringUtil.isNotNull(personStatus)
//					&& "quit".equals(personStatus)) {
//				whereBlock.append(
//						"hrStaffPersonInfo.fdStatus in ('dismissal','leave','retire')");
//			} else {
//				whereBlock.append(
//						"hrStaffPersonInfo.fdStatus in ('trial','official','temporary','trialDelay','practice')");
//			}
//			whereBlock.append(
//					"hrStaffPersonInfo.fdStatus in ('trial','official','temporary','trialDelay','practice','dismissal','leave','retire')");
			
			String fdDeptId = request.getParameter("fdDeptId");
			List<String> fdDeptIds = null;
			if (StringUtil.isNotNull(fdDeptId)) {
				//排除admin
				if (UserUtil.getKMSSUser().isAdmin()
						|| UserUtil.checkRole("ROLE_HRSTAFF_READALL")) {
//					SysOrgElement dept = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(fdDeptId);
//					whereBlock.append(" and hrStaffPersonInfo.fdHierarchyId like :fdDeptId");
//					hqlInfo.setParameter("fdDeptId", "%" + dept.getFdHierarchyId() + "%");
				}else {
					// 是否具有该部门权限
//					List<String> list = HrStaffAuthorityUtil.obtainOrgAuth();
					// 获取该部门的所有子部门
//					fdDeptIds = HrStaffAuthorityUtil.getchildDept(fdDeptId);
//					if (fdDeptIds.contains(fdDeptId)) {
						// 有权限
//						whereBlock = HrStaffAuthorityUtil.getLeavePerson(
//								whereBlock,
//								fdDeptIds, hqlInfo);
//					} else {
						// 没权限
//						whereBlock.append(" and 1=2");
//					}
				}
			}
			// 导出所有，仅导出所有管辖范围内的数据
			String warningType = (String) request.getAttribute("warningType");
			if (StringUtil.isNull(warningType) && StringUtil.isNull(fdDeptId)
					&& (!UserUtil.getKMSSUser().isAdmin()
							&& !UserUtil.checkRole("ROLE_HRSTAFF_READALL"))) {
				// 获取有权限的部门数据
//				List<String> orgIds = this.getAuthDeptIds();
//				whereBlock = HrStaffAuthorityUtil.getLeavePerson(whereBlock,
//						orgIds, hqlInfo);
			}
			hqlInfo.setWhereBlock(whereBlock.toString());
			List<Ekp_H14_M_performance> rtnList = getServiceImp(request)
					.findList(hqlInfo);
			// 添加日志信息
			UserOperHelper.logFindAll(rtnList,
					getServiceImp(request).getModelName());
			this.getServiceImp(request).exportList(request, response, rtnList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			canDownMap.remove(userId);
		}
		return null;
	}
}
