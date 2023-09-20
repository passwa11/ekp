package com.landray.kmss.sys.attend.actions;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.attend.model.SysAttendStatMonth;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendOrgService;
import com.landray.kmss.sys.attend.service.ISysAttendStatMonthService;
import com.landray.kmss.sys.attend.service.ISysAttendStatService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

 
/**
 * 月统计表 Action
 * 
 * @author 
 * @version 1.0 2017-08-17
 */
public class SysAttendStatMonthAction extends ExtendAction {
	protected ISysAttendStatMonthService sysAttendStatMonthService;
	protected ISysAttendStatService sysAttendStatService;
	private ISysAttendOrgService sysAttendOrgService;
	private ISysAttendCategoryService sysAttendCategoryService;
	protected ISysAttendStatService
			getStatServiceImp(HttpServletRequest request) {
		if (sysAttendStatService == null) {
			sysAttendStatService = (ISysAttendStatService) getBean(
					"sysAttendStatService");
		}
		return sysAttendStatService;
	}
	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if(sysAttendStatMonthService == null){
			sysAttendStatMonthService = (ISysAttendStatMonthService)getBean("sysAttendStatMonthService");
		}
		return sysAttendStatMonthService;
	}

	public ISysAttendOrgService getSysAttendOrgService() {
		if (sysAttendOrgService == null) {
			sysAttendOrgService = (ISysAttendOrgService) getBean(
					"sysAttendOrgService");
		}
		return sysAttendOrgService;
	}

	public ISysAttendCategoryService getSysAttendCategoryService() {
		if (sysAttendCategoryService == null) {
			sysAttendCategoryService = (ISysAttendCategoryService) getBean(
					"sysAttendCategoryService");
		}
		return sysAttendCategoryService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);		
		CriteriaValue cv = new CriteriaValue(request);
		StringBuffer sb = new StringBuffer("1=1 ");

		String fdMonth = request.getParameter("fdMonth");
		fdMonth = StringUtil.isNull(fdMonth) ? String.valueOf(System.currentTimeMillis())
				: fdMonth;
		Date date = new Date(Long.valueOf(fdMonth));
		Date fdStartTime = AttendUtil.getMonth(date, 0);
		Date fdEndTime = AttendUtil.getMonth(date, 1);

		sb.append(
				" and sysAttendStatMonth.fdMonth>=:fdStartTime and sysAttendStatMonth.fdMonth <:fdEndTime ");
		hqlInfo.setParameter("fdStartTime", fdStartTime);
		hqlInfo.setParameter("fdEndTime", fdEndTime);

		// 根据人员权限过滤
		if (!getStatServiceImp(null).isStatAllReader()) {
			// 部门列表
			List deptIds = getSysAttendOrgService()
					.findDeptsByLeader(UserUtil.getUser());
			// 人员列表
			List personIds = getSysAttendOrgService()
					.findPersonsByLeader(UserUtil.getUser());
			// 考勤组负责人
			List cateIds = getStatServiceImp(null).findCategoryIds();
			// 考勤组可阅读者和可编辑者
			cateIds.addAll(getSysAttendCategoryService()
					.findCateIdsByAuthId(UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds(), 1));
			if (deptIds != null && !deptIds.isEmpty() || !cateIds.isEmpty()
					|| !personIds.isEmpty()) {
				StringBuffer tmp = new StringBuffer(" (");
				for (int i = 0; i < deptIds.size(); i++) {
					String deptId = "deptId" + i;
					String prefix = " ";
					if (i > 0) {
						prefix = " or ";
					}
					tmp.append(
							prefix + "sysAttendStatMonth.docCreator.fdHierarchyId like :"
									+ deptId);
					hqlInfo.setParameter(deptId, "%" + deptIds.get(i) + "%");
				}
				if (!cateIds.isEmpty()) {
					// 获取考勤组列表中的所有有效人员
					List<String> orgIds = this.getSysAttendCategoryService().getAttendPersonIds(cateIds,fdStartTime,true);
					if (!orgIds.isEmpty()) {
						personIds.addAll(orgIds);
					}
				}
				if (!personIds.isEmpty()) {
					String prefix = " ";
					if (tmp.toString().indexOf("sysAttendStatMonth") > -1) {
						prefix = " or ";
					}
					tmp.append(prefix + HQLUtil.buildLogicIN(
							"sysAttendStatMonth.docCreator.fdId", personIds));
				}
				tmp.append(")");
				sb.append(" and " + tmp.toString());
			}
		}

		// 按部门查询
		String fdDeptId = request.getParameter("fdDeptId");
		if (StringUtil.isNotNull(fdDeptId)) {
			sb.append(
					" and sysAttendStatMonth.docCreator.fdHierarchyId like :fdDeptId");
			hqlInfo.setParameter("fdDeptId", "%" + fdDeptId + "%");
		}
		String fdType = request.getParameter("fdType");
		if (StringUtil.isNotNull(fdType)) {
			setFdTypeHql(sb, hqlInfo, fdType);
		}
		hqlInfo.setWhereBlock(sb.toString());
		CriteriaUtil.buildHql(cv, hqlInfo, SysAttendStatMonth.class);
		hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
		request.setAttribute("fdType", fdType);
	}

	private void setFdTypeHql(StringBuffer sb, HQLInfo hqlInfo, String fdType) {
		if ("0".equals(fdType)) {
			sb.append(" and sysAttendStatMonth.fdMissed=:fdMissed");
			hqlInfo.setParameter("fdMissed", true);
		} else if ("1".equals(fdType)) {
			sb.append(" and sysAttendStatMonth.fdStatus=:fdStatus");
			hqlInfo.setParameter("fdStatus", true);
		} else if ("2".equals(fdType)) {
			sb.append(" and sysAttendStatMonth.fdLate=:fdLate");
			hqlInfo.setParameter("fdLate", true);
		} else if ("3".equals(fdType)) {
			sb.append(" and sysAttendStatMonth.fdLeft=:fdLeft");
			hqlInfo.setParameter("fdLeft", true);
		} else if ("4".equals(fdType)) {
			sb.append(" and sysAttendStatMonth.fdOutside=:fdOutside");
			hqlInfo.setParameter("fdOutside", true);
		} else if ("5".equals(fdType)) {
			sb.append(" and sysAttendStatMonth.fdAbsent=:fdAbsent");
			hqlInfo.setParameter("fdAbsent", true);
		} else if ("6".equals(fdType)) {
			sb.append(" and sysAttendStatMonth.fdTripDays>0");
		} else if ("7".equals(fdType)) {
			sb.append(
					" and (sysAttendStatMonth.fdOffDays>0 or sysAttendStatMonth.fdOffTime>0 or sysAttendStatMonth.fdOffTimeHour>0)");
		} else if ("8".equals(fdType)) {
			sb.append(" and sysAttendStatMonth.fdOverTime>0");
		} else if ("9".equals(fdType)) {
			sb.append(" and sysAttendStatMonth.fdOutgoingTime>0");
		}

	}
}

