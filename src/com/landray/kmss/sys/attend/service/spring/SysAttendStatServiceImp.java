package com.landray.kmss.sys.attend.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendOrgService;
import com.landray.kmss.sys.attend.service.ISysAttendStatMonthService;
import com.landray.kmss.sys.attend.service.ISysAttendStatService;
import com.landray.kmss.sys.attend.util.AttendPersonUtil;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 人员统计表业务接口实现
 * 
 * @author
 * @version 1.0 2017-07-27
 */
public class SysAttendStatServiceImp extends BaseServiceImp
		implements ISysAttendStatService, SysOrgConstant {
	private ISysAttendCategoryService sysAttendCategoryService;
	private ISysOrgCoreService sysOrgCoreService;
	private ISysAttendStatMonthService sysAttendStatMonthService;
	private ISysAttendOrgService sysAttendOrgService;
	private ISysOrgElementService sysOrgElementService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	public void setSysAttendCategoryService(
			ISysAttendCategoryService sysAttendCategoryService) {
		this.sysAttendCategoryService = sysAttendCategoryService;
	}

	public void setSysAttendStatMonthService(
			ISysAttendStatMonthService sysAttendStatMonthService) {
		this.sysAttendStatMonthService = sysAttendStatMonthService;
	}

	public void
			setSysAttendOrgService(ISysAttendOrgService sysAttendOrgService) {
		this.sysAttendOrgService = sysAttendOrgService;
	}

	public void
			setSysOrgElementService(
					ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	@Override
	public JSONObject sumAttendCount(Date dateTime, String statType,
			String fdDeptId, HttpServletRequest request) throws Exception {
		String fdType = request.getParameter("fdType");
		JSONObject result = new JSONObject();
		if ("2".equals(statType)) {
			// 移动端月统计
			result = sysAttendStatMonthService.sumAttendCount(dateTime,
					fdDeptId);
			return result;
		}

		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer sb = new StringBuffer();
		Date fdStartTime = AttendUtil.getDate(dateTime, 0);
		Date fdEndTime = AttendUtil.getDate(dateTime, 1);

		sb.append("sysAttendStat.fdDate>=:fdStartTime and sysAttendStat.fdDate <:fdEndTime ");
		hqlInfo.setParameter("fdStartTime", fdStartTime);
		hqlInfo.setParameter("fdEndTime", fdEndTime);

		List deptIds = null;
		List cateIds = null;
		List personIds = null;
		// 根据人员权限过滤
		if (!isStatAllReader()) {
			// 部门列表
			deptIds = sysAttendOrgService.findDeptsByLeader(UserUtil.getUser());
			// 人员列表
			personIds = sysAttendOrgService.findPersonsByLeader(UserUtil.getUser());
			// 考勤组负责人
			cateIds = findCategoryIds();
			// 考勤组可阅读者和可编辑者
			cateIds.addAll(sysAttendCategoryService.findCateIdsByAuthId(UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds(), 1));
			if (deptIds != null && !deptIds.isEmpty() || !cateIds.isEmpty()
					|| !personIds.isEmpty()) {
				StringBuffer tmp = new StringBuffer();
				for (int i = 0; i < deptIds.size(); i++) {
					String deptId = "deptId" + i;
					String prefix = " ";
					if (i > 0) {
						prefix = " or ";
					}
					tmp.append(
							prefix + "sysAttendStat.docCreator.fdHierarchyId like :"
									+ deptId);
					hqlInfo.setParameter(deptId, "%" + deptIds.get(i) + "%");
				}
				if (!cateIds.isEmpty()) {
					// 获取考勤组列表中的所有有效人员
					List<String> orgIds = this.sysAttendCategoryService.getAttendPersonIds(cateIds,fdStartTime,true);
					if (!orgIds.isEmpty()) {
						personIds.addAll(orgIds);
					}
				}
				if (!personIds.isEmpty()) {
					String prefix = " ";
					if (tmp.toString().indexOf("sysAttendStat") > -1) {
						prefix = " or ";
					}
					tmp.append(prefix + HQLUtil.buildLogicIN(
							"sysAttendStat.docCreator.fdId", personIds));
				}
				if (StringUtil.isNotNull(tmp.toString())) {
					tmp.insert(0, " (").append(")");
					sb.append(" and " + tmp.toString());
				}
			}
		}
		// 按部门查询
		if (StringUtil.isNotNull(fdDeptId)) {
			sb.append(
					" and sysAttendStat.docCreator.fdHierarchyId like :fdDeptId");
			hqlInfo.setParameter("fdDeptId", "%" + fdDeptId + "%");
		}
		HQLInfo hqlInfo1 = (HQLInfo) hqlInfo.clone();
		List totalList = new ArrayList();
		List signList = new ArrayList();
		List lateList = new ArrayList();
		List leftList = new ArrayList();
		List outsideList = new ArrayList();
		List missedList = new ArrayList();
		List absentList = new ArrayList();
		List statusList = new ArrayList();
		List tripList = new ArrayList();
		List offList = new ArrayList();
		List overTimeList = new ArrayList();
		List outgoingCountList = new ArrayList();

		if ("totalCount".equals(fdType)) {
			// 应打卡人数
			statTotalCount(totalList, hqlInfo1, sb, dateTime, deptIds, personIds,
					cateIds, fdDeptId);
			// 已打卡人数(非旷工人数)
			hqlInfo1 = (HQLInfo) hqlInfo.clone();
			hqlInfo1.setSelectBlock("count(sysAttendStat.fdId)");
			hqlInfo1.setWhereBlock(
					sb.toString()
							+ " and sysAttendStat.fdAbsent !=:fdAbsent and (sysAttendStat.fdIsNoRecord is null or sysAttendStat.fdIsNoRecord=:fdIsNoRecord)");
			hqlInfo1.setParameter("fdIsNoRecord", false);
			hqlInfo1.setParameter("fdAbsent", true);
			hqlInfo1.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
			signList = this.findValue(hqlInfo1);
		} else {
			// 迟到人数
			hqlInfo1 = (HQLInfo) hqlInfo.clone();
			hqlInfo1.setSelectBlock("count(sysAttendStat.fdId)");
			hqlInfo1.setWhereBlock(
					sb.toString() + " and sysAttendStat.fdLate =:fdLate");
			hqlInfo1.setParameter("fdLate", true);
			hqlInfo1.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
			lateList = this.findValue(hqlInfo1);
			// 早退人数
			hqlInfo1 = (HQLInfo) hqlInfo.clone();
			hqlInfo1.setSelectBlock("count(sysAttendStat.fdId)");
			hqlInfo1.setWhereBlock(
					sb.toString() + " and sysAttendStat.fdLeft =:fdLeft");
			hqlInfo1.setParameter("fdLeft", true);
			hqlInfo1.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
			leftList = this.findValue(hqlInfo1);
			// 外勤人数
			hqlInfo1 = (HQLInfo) hqlInfo.clone();
			hqlInfo1.setSelectBlock("count(sysAttendStat.fdId)");
			hqlInfo1.setWhereBlock(
					sb.toString() + " and sysAttendStat.fdOutside =:fdOutside");
			hqlInfo1.setParameter("fdOutside", true);
			hqlInfo1.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
			outsideList = this.findValue(hqlInfo1);
			// 缺卡人数
			hqlInfo1 = (HQLInfo) hqlInfo.clone();
			hqlInfo1.setSelectBlock("count(sysAttendStat.fdId)");
			hqlInfo1.setWhereBlock(
					sb.toString() + " and sysAttendStat.fdMissed =:fdMissed");
			hqlInfo1.setParameter("fdMissed", true);
			hqlInfo1.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
			missedList = this.findValue(hqlInfo1);
			// 旷工人数
			hqlInfo1 = (HQLInfo) hqlInfo.clone();
			hqlInfo1.setSelectBlock("count(sysAttendStat.fdId)");
			hqlInfo1.setWhereBlock(
					sb.toString() + " and sysAttendStat.fdAbsent =:fdAbsent");
			hqlInfo1.setParameter("fdAbsent", true);
			hqlInfo1.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
			absentList = this.findValue(hqlInfo1);
			// 正常人数
			hqlInfo1 = (HQLInfo) hqlInfo.clone();
			hqlInfo1.setSelectBlock("count(sysAttendStat.fdId)");
			hqlInfo1.setWhereBlock(
					sb.toString() + " and sysAttendStat.fdStatus =:fdStatus");
			hqlInfo1.setParameter("fdStatus", true);
			hqlInfo1.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
			statusList = this.findValue(hqlInfo1);
			// 出差人数
			hqlInfo1 = (HQLInfo) hqlInfo.clone();
			hqlInfo1.setSelectBlock("count(sysAttendStat.fdId)");
			hqlInfo1.setWhereBlock(
					sb.toString() + " and sysAttendStat.fdTripDays > 0");
			hqlInfo1.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
			tripList = this.findValue(hqlInfo1);
			// 请假人数
			hqlInfo1 = (HQLInfo) hqlInfo.clone();
			hqlInfo1.setSelectBlock("count(sysAttendStat.fdId)");
			hqlInfo1.setWhereBlock(
					sb.toString()
							+ " and (sysAttendStat.fdOffDays > 0 or sysAttendStat.fdOffTime > 0 or sysAttendStat.fdOffTimeHour > 0)");
			hqlInfo1.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
			offList = this.findValue(hqlInfo1);
			// 加班人数
			hqlInfo1 = (HQLInfo) hqlInfo.clone();
			hqlInfo1.setSelectBlock("count(sysAttendStat.fdId)");
			hqlInfo1.setWhereBlock(
					sb.toString()
							+ " and sysAttendStat.fdOverTime>0");
			hqlInfo1.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
			overTimeList = this.findValue(hqlInfo1);
			// 外出人数
			hqlInfo1 = (HQLInfo) hqlInfo.clone();
			hqlInfo1.setSelectBlock("count(sysAttendStat.fdId)");
			hqlInfo1.setWhereBlock(
					sb.toString()
							+ " and sysAttendStat.fdOutgoingTime>0");
			hqlInfo1.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
			outgoingCountList = this.findValue(hqlInfo1);
		}

		Number totalCount = (Number) (totalList.size() > 0 ? totalList.get(0)
				: 0);
		Number signCount = (Number) (signList.size() > 0 ? signList.get(0) : 0);
		Number lateCount = (Number) (lateList.size() > 0 ? lateList.get(0) : 0);
		Number leftCount = (Number) (leftList.size() > 0 ? leftList.get(0) : 0);
		Number outsideCount = (Number) (outsideList.size() > 0
				? outsideList.get(0) : 0);
		Number missedCount = (Number) (missedList.size() > 0 ? missedList.get(0)
				: 0);
		Number absentCount = (Number) (absentList.size() > 0 ? absentList.get(0)
				: 0);
		Number statusCount = (Number) (statusList.size() > 0 ? statusList.get(0)
				: 0);
		Number tripCount = (Number) (tripList.size() > 0 ? tripList.get(0) : 0);
		Number offCount = (Number) (offList.size() > 0 ? offList.get(0) : 0);
		Number overTimeCount = (Number) (overTimeList.size() > 0
				? overTimeList.get(0) : 0);
		Number fdOutgoingCount = (Number) (outgoingCountList.size() > 0
				? outgoingCountList.get(0) : 0);

		result.accumulate("totalCount", totalCount);
		result.accumulate("signCount", signCount);
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
		// 添加日志信息
		if (UserOperHelper.allowLogOper("sumAttendCount", getModelName())) {
			UserOperContentHelper.putFind("totalCount",
					String.valueOf(totalCount),null);
			UserOperContentHelper.putFind("signCount",
					String.valueOf(signCount),null);
			UserOperContentHelper.putFind("fdLateCount",
					String.valueOf(lateCount),null);
			UserOperContentHelper.putFind("fdLeftCount",
					String.valueOf(leftCount),null);
			UserOperContentHelper.putFind("fdOutsideCount",
					String.valueOf(outsideCount),null);
			UserOperContentHelper.putFind("fdMissedCount",
					String.valueOf(missedCount),null);
			UserOperContentHelper.putFind("fdAbsentCount",
					String.valueOf(absentCount),null);
			UserOperContentHelper.putFind("fdStatusCount",
					String.valueOf(statusCount),null);
			UserOperContentHelper.putFind("fdTripCount",
					String.valueOf(tripCount),null);
			UserOperContentHelper.putFind("fdOffCount",
					String.valueOf(offCount),null);
			UserOperContentHelper.putFind("fdOverTimeCount",
					String.valueOf(overTimeCount),null);
			UserOperContentHelper.putFind("fdOutgoingCount",
					String.valueOf(fdOutgoingCount),null);
		}
		return result;
	}

	@Override
	public List findCategoryIds() throws Exception {
		List<String> list = sysAttendCategoryService
				.findCategorysByLeader(UserUtil.getUser(), 1);
		return list;
	}

	@Override
	public boolean isStatAllReader() throws Exception {
		if (UserUtil.getKMSSUser().isAdmin()) {
			return true;
		}
		if (ArrayUtil.isListIntersect(UserUtil.getKMSSUser()
				.getUserAuthInfo().getAuthRoleAliases(),
				Arrays.asList(new String[] { "ROLE_SYSATTEND_STAT_READER" }))) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isStatReader() throws Exception {
		try {
			if (isStatAllReader()) {
				return true;
			}

			// 部门列表
			List deptIds = sysAttendOrgService
					.findOrgIdsByLeader(UserUtil.getUser());
			if (deptIds != null && !deptIds.isEmpty()) {
				return true;
			}
			// 考勤组负责人
			List cateIds = findCategoryIds();
			if (cateIds != null && !cateIds.isEmpty()) {
				return true;
			}
			// 考勤组可阅读者和可编辑者
			List<String> cateAuthIds = sysAttendCategoryService
					.findCateIdsByAuthId(
							UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds(), 1);
			if (cateAuthIds != null && !cateAuthIds.isEmpty()) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public boolean isSignStatReader() throws Exception {
		try {
			// 签到负责人
			List cateIds = sysAttendCategoryService
					.findCategorysByLeader(UserUtil.getUser(), 2);
			if (cateIds != null && !cateIds.isEmpty()) {
				return true;
			}
			// 签到组可阅读者和可编辑者
			List<String> cateAuthIds = sysAttendCategoryService
					.findCateIdsByAuthId(
							UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds(), 2);
			if (cateAuthIds != null && !cateAuthIds.isEmpty()) {
				return true;
			}
			// 允许签到组内人员查看
			List<SysAttendCategory> list = sysAttendCategoryService.findList(
					"sysAttendCategory.fdIsAllowView=true and sysAttendCategory.fdType=2",
					null);
			for (SysAttendCategory category : list) {
				if (UserUtil.checkUserModels(category.getFdTargets())) {
					return true;
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public boolean isStatDeptLeader() throws Exception {
		try {
			if (isStatAllReader()) {
				return true;
			}
			// 部门列表
			List deptIds = sysAttendOrgService
					.findOrgIdsByLeader(UserUtil.getUser());
			if (deptIds != null && !deptIds.isEmpty()) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public boolean isStatCateReader() throws Exception {
		try {
			if (isStatAllReader()) {
				return true;
			}
			// 考勤组负责人
			List cateIds = findCategoryIds();
			if (cateIds != null && !cateIds.isEmpty()) {
				return true;
			}
			// 考勤组可阅读者和可编辑者
			List<String> cateAuthIds = sysAttendCategoryService
					.findCateIdsByAuthId(
							UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds(), 1);
			if (cateAuthIds != null && !cateAuthIds.isEmpty()) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	// TODO:排班制时如何统计??
	private void statTotalCount(List totalList, HQLInfo hqlInfo1,
			StringBuffer sb, Date dateTime, List deptIds, List personIds,
			List cateIds, String fdDeptId)
			throws Exception {
		if (dateTime.getTime() < DateUtil.getDate(0).getTime()) {// 今天前
			hqlInfo1.setSelectBlock("count(sysAttendStat.fdId)");
			hqlInfo1.setWhereBlock(sb.toString());
			List tmpList = this.findValue(hqlInfo1);
			totalList.addAll(tmpList);
		} else {// 今天或今天后
			Set<String> setIds = new HashSet<String>();

			if (!isStatAllReader()) {// 非管理员
				if (deptIds != null && !deptIds.isEmpty()) {
					List ids = AttendPersonUtil.expandToPersonIds(deptIds);
					setIds.addAll(ids);
				}
				if (personIds != null && !personIds.isEmpty()) {
					setIds.addAll(personIds);
				}
				if (cateIds != null && !cateIds.isEmpty()) {
					List ids = sysAttendCategoryService.getAttendPersonIds(cateIds,dateTime,true);
					setIds.addAll(ids);
				}
			} else {
				// 管理员
				List<String> cateIdList = sysAttendCategoryService.findValue(
						"sysAttendCategory.fdId",
						"sysAttendCategory.fdStatus=1 and sysAttendCategory.fdType=1",
						null);
				List ids = sysAttendCategoryService.getAttendPersonIds(cateIdList,dateTime,true);
				setIds.addAll(ids);
			}
			
			// 当前有权限访问的人员
			String[] orgIds = new String[] {};
			orgIds = setIds.toArray(orgIds);
			List<SysOrgElement> orgList = sysOrgCoreService
					.findByPrimaryKeys(orgIds);
			
			// 当前有效的考勤组
			for (SysOrgElement ele : orgList) {
				String categId = sysAttendCategoryService.getAttendCategory(ele,
						AttendUtil.getDate(dateTime, 0));
				if (StringUtil.isNull(categId)) {
					setIds.remove(ele.getFdId());
				}
			}

			// 按部门查询
			if (StringUtil.isNotNull(fdDeptId)) {
				setIds.clear();
				for (SysOrgElement ele : orgList) {
					if (ele.getFdHierarchyId().indexOf(fdDeptId) > 0) {
						setIds.add(ele.getFdId());
					}
				}
			}

			totalList.add(setIds.size());

			if (totalList.isEmpty()) {
				totalList.add(0);
			}
		}
	}

	@Override
	public List addressList(RequestContext xmlContext) throws Exception {
		// 有权限访问的组织架构
		List authOrgIds = sysAttendOrgService
				.findOrgIdsByLeader(UserUtil.getUser());
		List cateIds = findCategoryIds();
		cateIds.addAll(sysAttendCategoryService
				.findCateIdsByAuthId(UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds(), 1));
		if (cateIds != null && !cateIds.isEmpty()) {
			for (int i = 0; i < cateIds.size(); i++) {
				SysAttendCategory sysAttendCategory = (SysAttendCategory) sysAttendCategoryService
						.findByPrimaryKey((String) cateIds.get(i));
				if (sysAttendCategory.getFdStatus() == 1) {
					for (SysOrgElement ele : sysAttendCategory.getFdTargets()) {
						authOrgIds.add(ele.getFdId());
					}
				}
			}
		}

		String whereBlock;
		HQLInfo hqlInfo = new HQLInfo();
		whereBlock = HQLUtil.buildLogicIN("sysOrgElement.fdId ", authOrgIds);

		// 父部门
		String parentId = xmlContext.getParameter("parentId");
		if (StringUtil.isNotNull(parentId)) {
			whereBlock = "sysOrgElement.hbmParent.fdId=:parentId";
			hqlInfo.setParameter("parentId", parentId);
		}
		// 组织类型
		int orgType = ORG_TYPE_POSTORPERSON | ORG_TYPE_ORGORDEPT;
		// 构建组织架构类型where语句
		whereBlock = SysOrgHQLUtil
				.buildWhereBlock(
						orgType & (ORG_TYPE_ALLORG | ORG_FLAG_AVAILABLEALL
								| ORG_FLAG_BUSINESSALL),
						whereBlock, "sysOrgElement");
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy(
				"sysOrgElement.fdOrgType desc,sysOrgElement.fdNamePinYin,sysOrgElement.fdOrder");
		hqlInfo.setAuthCheckType("DIALOG_READER");
		// 查询结果
		List<SysOrgElement> elemList = sysOrgElementService.findList(hqlInfo);
		List personList = new ArrayList();
		for (SysOrgElement org : elemList) {
			personList.add(formatElement(org, false));
		}
		return personList;
	}

	protected Map formatElement(SysOrgElement orgElem, boolean needDetail) {
		Map tmpMap = new HashMap();
		tmpMap.put("fdId", orgElem.getFdId());
		tmpMap.put("label", orgElem.getFdName());
		tmpMap.put("type", orgElem.getFdOrgType());
		tmpMap.put("order", orgElem.getFdOrder());
		tmpMap.put("pinyin", orgElem.getFdNamePinYin());
		if (orgElem.getFdOrgType().equals(ORG_TYPE_PERSON)
				|| orgElem.getFdOrgType().equals(ORG_TYPE_POST)) {
			tmpMap.put("parentNames", StringUtil.isNotNull(orgElem
					.getFdParentsName("_")) ? orgElem.getFdParentsName("_")
							: "");
		}
		if (needDetail) {
			if (orgElem.getFdParent() != null) {
				tmpMap.put("parentId", orgElem.getFdParent().getFdId());
			}
		}
		if (orgElem.getFdOrgType() == ORG_TYPE_PERSON) {
			tmpMap.put("icon", PersonInfoServiceGetter
					.getPersonHeadimageUrl(orgElem.getFdId()));
		}
		return tmpMap;
	}

}
