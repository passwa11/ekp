package com.landray.kmss.km.imeeting.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainFeedbackService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.zone.service.ISysZoneDocCountGetter;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

public class KmImeetingDocCountGetter implements ISysZoneDocCountGetter{
	private IKmImeetingMainService kmImeetingMainService;
	

	public void setKmImeetingMainService(
			IKmImeetingMainService kmImeetingMainService) {
		this.kmImeetingMainService = kmImeetingMainService;
	}

	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	private List findMyfeedback(String fdPersonId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmImeetingMainFeedback.fdMeeting.fdId");
		hqlInfo.setWhereBlock(" kmImeetingMainFeedback.docCreator.fdId=:userId");
		hqlInfo.setParameter("userId", fdPersonId);
		List fs = ((IKmImeetingMainFeedbackService) SpringBeanUtil.getBean("kmImeetingMainFeedbackService"))
				.findList(hqlInfo);
		return fs;
	}

	@Override
	public int getDocNum(String fdPersonId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		// 去除重复
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		hqlInfo.setSelectBlock("kmImeetingMain.fdId");
		hqlInfo.setGettingCount(true);
		List l = findMyfeedback(fdPersonId);
		if (l.size() > 0) {
			hqlInfo.setJoinBlock(" left join kmImeetingMain.fdAttendPersons attendPersons");
			// 参加的所有会议（包括参与者中有我/TA，我/TA主持，我/TA纪要）
			String whereBlock = " kmImeetingMain.docStatus !='00' ";
			List authOrgIds = sysOrgCoreService
					.getOrgsUserAuthInfo(UserUtil.getUser(fdPersonId))
					.getAuthOrgIds();
			whereBlock += " and (kmImeetingMain.fdHost.fdId=:userid or  kmImeetingMain.fdSummaryInputPerson.fdId=:userid or "
					+ HQLUtil.buildLogicIN("kmImeetingMain.fdId", l)
					+ " or (" + HQLUtil.buildLogicIN(
							"attendPersons.fdId", authOrgIds)
					+ " and  kmImeetingMain.fdNeedFeedback = :fdNeedFeedback ))";
			whereBlock += " and kmImeetingMain.fdFinishDate<=:attendDate and (kmImeetingMain.fdRecurrenceStr is null or kmImeetingMain.fdRecurrenceStr='NO')";
			hqlInfo.setParameter("userid", fdPersonId);
			hqlInfo.setParameter("fdNeedFeedback", false);
			hqlInfo.setParameter("attendDate", new Date());
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
			hqlInfo.setWhereBlock(whereBlock);
		} else {
			hqlInfo.setWhereBlock("1=2");
		}
		List list = kmImeetingMainService.getBaseDao().findValue(hqlInfo);
		Object result = list.get(0);
		int count = (int) Long
				.parseLong(result != null ? result.toString() : "0");
		return count;
	}

}
