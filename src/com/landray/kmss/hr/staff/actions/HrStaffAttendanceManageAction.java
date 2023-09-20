package com.landray.kmss.hr.staff.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.staff.model.HrStaffAttendanceManage;
import com.landray.kmss.hr.staff.service.IHrStaffAttendanceManageService;
import com.landray.kmss.hr.staff.util.HrStaffAuthorityUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 考勤管理
 * 
 * @author 潘永辉 2017-1-11
 * 
 */
public class HrStaffAttendanceManageAction extends
		HrStaffAttendanceManageBaseAction {
	private IHrStaffAttendanceManageService hrStaffAttendanceManageService;

	@Override
	protected IHrStaffAttendanceManageService getServiceImp(
			HttpServletRequest request) {
		if (hrStaffAttendanceManageService == null) {
			hrStaffAttendanceManageService = (IHrStaffAttendanceManageService) getBean("hrStaffAttendanceManageService");
		}
		return hrStaffAttendanceManageService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
			StringBuffer whereBlock = new StringBuffer();
			CriteriaValue cv = new CriteriaValue(request);
			// 解析其它筛选属性
			CriteriaUtil.buildHql(cv, hqlInfo, HrStaffAttendanceManage.class);
			String personInfoId = request.getParameter("personInfoId");
			String _whereBlock = hqlInfo.getWhereBlock();
			if (StringUtil.isNotNull(_whereBlock)) {
				whereBlock.append(_whereBlock);
			} else {
				whereBlock.append("1 = 1");
			}
			if (StringUtil.isNotNull(personInfoId)) {
				whereBlock
				.append(" and hrStaffAttendanceManage.fdPersonInfo.fdId = :personInfoId ");
				hqlInfo.setParameter("personInfoId", personInfoId);
			}
			whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffAttendanceManage", hqlInfo);
		hqlInfo.setWhereBlock(whereBlock.toString());
	}

	@Override
	public String getTempletName() {
		return ResourceUtil
				.getString("hr-staff:hrStaffAttendanceManage.templetName");
	}
	
	public ActionForward userHoliday(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<HrStaffAttendanceManage> manages = getServiceImp(request).findList(
				"hrStaffAttendanceManage.fdPersonInfo.fdId='"
						+ UserUtil.getUser().getFdId() + "'",
				"fdYear desc");
		// 添加日志信息
		UserOperHelper.logFindAll(manages,
				getServiceImp(request).getModelName());
		JSONArray source = new JSONArray();
		for (HrStaffAttendanceManage m : manages) {
			JSONObject resObj = new JSONObject();
			resObj.put("fdYear", m.getFdYear());// 年份
			resObj.put("fdExpirationDate",
					DateUtil.convertDateToString(m.getFdExpirationDate(),
							DateUtil.TYPE_DATE, request.getLocale()));// 失效日期
			resObj.put("fdDaysOfAnnualLeave", m.getDaysOfAnnualLeave());// 剩余年假天数
			resObj.put("fdDaysOfTakeWorking", m.getFdDaysOfTakeWorking());// 剩余调休天数
			resObj.put("fdDaysOfSickLeave", m.getFdDaysOfSickLeave());// 剩余带薪病假天数
			source.add(resObj);
		}
		request.setAttribute("lui-source", source);
		return getActionForward("lui-source", mapping, form, request, response);
	}

}
