package com.landray.kmss.sys.attend.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attend.model.SysAttendStatMonth;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendOrgService;
import com.landray.kmss.sys.attend.service.ISysAttendStatMonthService;
import com.landray.kmss.sys.attend.service.ISysAttendStatService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.constant.SysAuthConstant;

import net.sf.json.JSONObject;
/**
 * 月统计表业务接口实现
 * 
 * @author 
 * @version 1.0 2017-08-17
 */
public class SysAttendStatMonthServiceImp extends BaseServiceImp
		implements ISysAttendStatMonthService {


	@Override
	public JSONObject sumAttendCount(Date dateTime, String fdDeptId)
			throws Exception {
		JSONObject result = new JSONObject();
		ISysAttendStatService sysAttendStatService = (ISysAttendStatService) SpringBeanUtil
				.getBean("sysAttendStatService");
		ISysAttendOrgService sysAttendOrgService = (ISysAttendOrgService) SpringBeanUtil
				.getBean("sysAttendOrgService");
		ISysAttendCategoryService sysAttendCategoryService = (ISysAttendCategoryService) SpringBeanUtil
				.getBean("sysAttendCategoryService");
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer sb = new StringBuffer();
		Date fdStartTime = AttendUtil.getMonth(dateTime, 0);
		Date fdEndTime = AttendUtil.getMonth(dateTime, 1);

		sb.append(
				"sysAttendStatMonth.fdMonth>=:fdStartTime and sysAttendStatMonth.fdMonth <:fdEndTime ");
		hqlInfo.setParameter("fdStartTime", fdStartTime);
		hqlInfo.setParameter("fdEndTime", fdEndTime);

		List deptIds = null;
		List cateIds = null;
		// 根据人员权限过滤
		if (!sysAttendStatService.isStatAllReader()) {
			// 部门列表
			deptIds = sysAttendOrgService.findDeptsByLeader(UserUtil.getUser());
			// 人员列表
			List personIds = sysAttendOrgService
					.findPersonsByLeader(UserUtil.getUser());
			// 考勤组负责人
			cateIds = sysAttendStatService.findCategoryIds();
			// 考勤组可阅读者和可编辑者
			cateIds.addAll(sysAttendCategoryService
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
					List<String> orgIds = sysAttendCategoryService.getAttendPersonIds(cateIds,fdStartTime,true);
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
		if (StringUtil.isNotNull(fdDeptId)) {
			sb.append(
					" and sysAttendStatMonth.docCreator.fdHierarchyId like :fdDeptId");
			hqlInfo.setParameter("fdDeptId", "%" + fdDeptId + "%");
		}

		HQLInfo hqlInfo1 = (HQLInfo) hqlInfo.clone();

		// 迟到人数
		hqlInfo1 = (HQLInfo) hqlInfo.clone();
		hqlInfo1.setSelectBlock("count(sysAttendStatMonth.fdId)");
		hqlInfo1.setWhereBlock(
				sb.toString() + " and sysAttendStatMonth.fdLate =:fdLate");
		hqlInfo1.setParameter("fdLate", true);
		hqlInfo1.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.DEFAULT);
		List lateList = this.findValue(hqlInfo1);
		// 早退人数
		hqlInfo1 = (HQLInfo) hqlInfo.clone();
		hqlInfo1.setSelectBlock("count(sysAttendStatMonth.fdId)");
		hqlInfo1.setWhereBlock(
				sb.toString() + " and sysAttendStatMonth.fdLeft =:fdLeft");
		hqlInfo1.setParameter("fdLeft", true);
		hqlInfo1.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.DEFAULT);
		List leftList = this.findValue(hqlInfo1);
		// 外勤人数
		hqlInfo1 = (HQLInfo) hqlInfo.clone();
		hqlInfo1.setSelectBlock("count(sysAttendStatMonth.fdId)");
		hqlInfo1.setWhereBlock(
				sb.toString()
						+ " and sysAttendStatMonth.fdOutside =:fdOutside");
		hqlInfo1.setParameter("fdOutside", true);
		hqlInfo1.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.DEFAULT);
		List outsideList = this.findValue(hqlInfo1);
		// 缺卡人数
		hqlInfo1 = (HQLInfo) hqlInfo.clone();
		hqlInfo1.setSelectBlock("count(sysAttendStatMonth.fdId)");
		hqlInfo1.setWhereBlock(
				sb.toString() + " and sysAttendStatMonth.fdMissed =:fdMissed");
		hqlInfo1.setParameter("fdMissed", true);
		hqlInfo1.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.DEFAULT);
		List missedList = this.findValue(hqlInfo1);
		// 旷工人数
		hqlInfo1 = (HQLInfo) hqlInfo.clone();
		hqlInfo1.setSelectBlock("count(sysAttendStatMonth.fdId)");
		hqlInfo1.setWhereBlock(
				sb.toString() + " and sysAttendStatMonth.fdAbsent =:fdAbsent");
		hqlInfo1.setParameter("fdAbsent", true);
		hqlInfo1.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.DEFAULT);
		List absentList = this.findValue(hqlInfo1);
		// 正常人数
		hqlInfo1 = (HQLInfo) hqlInfo.clone();
		hqlInfo1.setSelectBlock("count(sysAttendStatMonth.fdId)");
		hqlInfo1.setWhereBlock(
				sb.toString() + " and sysAttendStatMonth.fdStatus =:fdStatus");
		hqlInfo1.setParameter("fdStatus", true);
		hqlInfo1.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.DEFAULT);
		List statusList = this.findValue(hqlInfo1);

		// 出差人数
		hqlInfo1 = (HQLInfo) hqlInfo.clone();
		hqlInfo1.setSelectBlock("count(sysAttendStatMonth.fdId) ");
		hqlInfo1.setWhereBlock(
				sb.toString() + " and sysAttendStatMonth.fdTripDays >0");
		hqlInfo1.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.DEFAULT);
		List tripList = this.findValue(hqlInfo1);
		// 请假人数
		hqlInfo1 = (HQLInfo) hqlInfo.clone();
		hqlInfo1.setSelectBlock("count(sysAttendStatMonth.fdId)");
		hqlInfo1.setWhereBlock(
				sb.toString()
						+ " and (sysAttendStatMonth.fdOffDays>0 or sysAttendStatMonth.fdOffTime>0 or sysAttendStatMonth.fdOffTimeHour>0)");
		hqlInfo1.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.DEFAULT);
		List offList = this.findValue(hqlInfo1);
		// 加班人数
		hqlInfo1 = (HQLInfo) hqlInfo.clone();
		hqlInfo1.setSelectBlock("count(sysAttendStatMonth.fdId)");
		hqlInfo1.setWhereBlock(
				sb.toString() + " and sysAttendStatMonth.fdOverTime>0");
		hqlInfo1.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.DEFAULT);
		List overTimeList = this.findValue(hqlInfo1);
		// 外出人数
		hqlInfo1 = (HQLInfo) hqlInfo.clone();
		hqlInfo1.setSelectBlock("count(sysAttendStatMonth.fdId)");
		hqlInfo1.setWhereBlock(
				sb.toString() + " and sysAttendStatMonth.fdOutgoingTime >0");
		hqlInfo1.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.DEFAULT);
		List outgoingCountList = this.findValue(hqlInfo1);

		Number lateCount = (Number) lateList.get(0);
		Number leftCount = (Number) leftList.get(0);
		Number outsideCount = (Number) outsideList.get(0);
		Number missedCount = (Number) missedList.get(0);
		Number absentCount = (Number) absentList.get(0);
		Number statusCount = (Number) statusList.get(0);
		Number tripCount = (Number) tripList.get(0);
		Number offCount = (Number) offList.get(0);
		Number overTimeCount = (Number) overTimeList.get(0);
		Number fdOutgoingCount = (Number) outgoingCountList.get(0);

		result.accumulate("fdLateCount", lateCount);
		result.accumulate("fdLeftCount", leftCount);
		result.accumulate("fdOutsideCount", outsideCount);
		result.accumulate("fdMissedCount", missedCount);
		result.accumulate("fdAbsentCount", absentCount);
		result.accumulate("fdStatusCount", statusCount);
		result.accumulate("fdTripCount", tripCount);
		result.accumulate("fdOffCount", offCount);
		result.accumulate("fdOverTimeCount", overTimeCount);
		result.accumulate("fdOutgoingCount", fdOutgoingCount);
		return result;
	}

	@Override
	public JSONObject statLeaderMonth(Date dateTime, String leaderId,
			String deptId)
			throws Exception {
		JSONObject result = new JSONObject();
		if (dateTime == null || StringUtil.isNull(deptId)
				|| StringUtil.isNull(leaderId)) {
			return result;
		}
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer sb = new StringBuffer();
		Date fdStartTime = AttendUtil.getMonth(dateTime, 0);
		Date fdEndTime = AttendUtil.getMonth(dateTime, 1);

		sb.append(
				"sysAttendStatMonth.fdMonth>=:fdStartTime and sysAttendStatMonth.fdMonth <:fdEndTime ");
		hqlInfo.setParameter("fdStartTime", fdStartTime);
		hqlInfo.setParameter("fdEndTime", fdEndTime);

		sb.append(
				" and sysAttendStatMonth.docCreator.fdHierarchyId like :deptId");
		hqlInfo.setParameter("deptId", "%" + deptId + "%");

		HQLInfo hqlInfo1 = (HQLInfo) hqlInfo.clone();

		// 正常人数
		hqlInfo1 = (HQLInfo) hqlInfo.clone();
		hqlInfo1.setSelectBlock("count(sysAttendStatMonth.fdId)");
		hqlInfo1.setWhereBlock(
				sb.toString()
						+ " and sysAttendStatMonth.fdStatus =:fdStatus");
		hqlInfo1.setParameter("fdStatus", true);
		List statusList = this.findValue(hqlInfo1);
		// 早退人数
		hqlInfo1 = (HQLInfo) hqlInfo.clone();
		hqlInfo1.setSelectBlock("count(sysAttendStatMonth.fdId)");
		hqlInfo1.setWhereBlock(
				sb.toString() + " and sysAttendStatMonth.fdLeft =:fdLeft");
		hqlInfo1.setParameter("fdLeft", true);
		List leftList = this.findValue(hqlInfo1);
		// 迟到人数
		hqlInfo1 = (HQLInfo) hqlInfo.clone();
		hqlInfo1.setSelectBlock("count(sysAttendStatMonth.fdId)");
		hqlInfo1.setWhereBlock(
				sb.toString() + " and sysAttendStatMonth.fdLate =:fdLate");
		hqlInfo1.setParameter("fdLate", true);
		List lateList = this.findValue(hqlInfo1);
		// 缺卡人数
		hqlInfo1 = (HQLInfo) hqlInfo.clone();
		hqlInfo1.setSelectBlock("count(sysAttendStatMonth.fdId)");
		hqlInfo1.setWhereBlock(
				sb.toString()
						+ " and sysAttendStatMonth.fdMissed =:fdMissed");
		hqlInfo1.setParameter("fdMissed", true);
		List missedList = this.findValue(hqlInfo1);
		// 旷工人数
		hqlInfo1 = (HQLInfo) hqlInfo.clone();
		hqlInfo1.setSelectBlock("count(sysAttendStatMonth.fdId)");
		hqlInfo1.setWhereBlock(
				sb.toString()
						+ " and sysAttendStatMonth.fdAbsent =:fdAbsent");
		hqlInfo1.setParameter("fdAbsent", true);
		List absentList = this.findValue(hqlInfo1);
		// 所属考勤组
		hqlInfo1 = (HQLInfo) hqlInfo.clone();
		hqlInfo1.setSelectBlock(null);
		hqlInfo1.setWhereBlock(
				sb.toString()
						+ " and sysAttendStatMonth.docCreator.fdId =:me");
		hqlInfo1.setParameter("me", leaderId);
		List list = this.findValue(hqlInfo1);

		Number statusCount = (Number) statusList.get(0);
		Number leftCount = (Number) leftList.get(0);
		Number lateCount = (Number) lateList.get(0);
		Number missedCount = (Number) missedList.get(0);
		Number absentCount = (Number) absentList.get(0);

		result.accumulate("fdStatusCount", statusCount);
		result.accumulate("fdLeftCount", leftCount);
		result.accumulate("fdLateCount", lateCount);
		result.accumulate("fdMissedCount", missedCount);
		result.accumulate("fdAbsentCount", absentCount);
		if (list != null && !list.isEmpty()) {
			result.accumulate("fdCategoryId",
					((SysAttendStatMonth) list.get(0)).getFdCategoryId());
		} else {
			ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
					.getBean("sysOrgCoreService");
			ISysAttendCategoryService sysAttendCategoryService = (ISysAttendCategoryService) SpringBeanUtil
					.getBean("sysAttendCategoryService");
			SysOrgElement orgEle=sysOrgCoreService.findByPrimaryKey(leaderId);
			String fdCategoryId=sysAttendCategoryService.getAttendCategory(orgEle);
			if(StringUtil.isNotNull(fdCategoryId)) {
				result.accumulate("fdCategoryId", fdCategoryId);
			}else {
				result.accumulate("fdCategoryId", null);
			}
		}

		return result;
	}

	@Override
	public SysAttendStatMonth getDataBypersonMonth(Date dateTime, String docCreatorId) throws Exception {
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setWhereBlock("sysAttendStatMonth.fdMonth>=:fdStartTime and sysAttendStatMonth.fdMonth <:fdEndTime  and sysAttendStatMonth.docCreator.fdId=:docCreatorId");
		Date fdStartTime = AttendUtil.getMonth(dateTime, 0);
		Date fdEndTime = AttendUtil.getMonth(dateTime, 1);
		hqlInfo.setParameter("fdStartTime", fdStartTime);
		hqlInfo.setParameter("fdEndTime", fdEndTime);
		
        hqlInfo.setParameter("docCreatorId",docCreatorId);
		List<SysAttendStatMonth> list=this.findList(hqlInfo);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
