package com.landray.kmss.hr.staff.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.staff.model.HrStaffAttendanceManageDetailed;
import com.landray.kmss.hr.staff.service.IHrStaffAttendanceManageDetailedService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 请假明细
 * 
 * @author 潘永辉 2017-1-11
 * 
 */
public class HrStaffAttendanceManageDetailedAction extends
		HrStaffAttendanceManageBaseAction {
	private IHrStaffAttendanceManageDetailedService hrStaffAttendanceManageDetailedService;

	@Override
	protected IHrStaffAttendanceManageDetailedService getServiceImp(
			HttpServletRequest request) {
		if (hrStaffAttendanceManageDetailedService == null) {
			hrStaffAttendanceManageDetailedService = (IHrStaffAttendanceManageDetailedService) getBean("hrStaffAttendanceManageDetailedService");
		}
		return hrStaffAttendanceManageDetailedService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		// 解析其它筛选属性
		CriteriaUtil.buildHql(cv, hqlInfo,
				HrStaffAttendanceManageDetailed.class);
		String personInfoId = request.getParameter("personInfoId");
		String fdType = request.getParameter("fdType");
		StringBuffer whereBlock = new StringBuffer();
		String _whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNotNull(_whereBlock)) {
			whereBlock.append(_whereBlock);
		} else {
			whereBlock.append("1 = 1");
		}
		if (StringUtil.isNotNull(personInfoId)) {
			whereBlock.append(
					" and hrStaffAttendanceManageDetailed.fdPersonInfo.fdId = :personInfoId");
			hqlInfo.setParameter("personInfoId", personInfoId);
		}
		if (StringUtil.isNotNull(fdType)) {
			if (HrStaffAttendanceManageDetailed.TYPE_LEAVE.toString().equals(fdType)) { // 请假
				whereBlock.append(" and (hrStaffAttendanceManageDetailed.fdType is null or hrStaffAttendanceManageDetailed.fdType = 1)");
			} else if (HrStaffAttendanceManageDetailed.TYPE_OVERTIME.toString().equals(fdType)) { // 加班
				whereBlock.append(" and hrStaffAttendanceManageDetailed.fdType = 2");
			}
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
	}

	@Override
	public String getTempletName() {
		return ResourceUtil
				.getString("hr-staff:hrStaffAttendanceManageDetailed.templetName");
	}

}
