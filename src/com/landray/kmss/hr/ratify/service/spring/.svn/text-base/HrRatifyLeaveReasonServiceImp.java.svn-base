package com.landray.kmss.hr.ratify.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.ICheckUniqueBean;
import com.landray.kmss.hr.ratify.service.IHrRatifyLeaveReasonService;
import com.landray.kmss.util.StringUtil;

public class HrRatifyLeaveReasonServiceImp extends BaseServiceImp
		implements IHrRatifyLeaveReasonService, ICheckUniqueBean {

	@Override
	public String checkUnique(RequestContext requestInfo) throws Exception {
		String fdName = requestInfo.getParameter("fdName");
		String fdId = requestInfo.getParameter("fdId");
		String result = "";
		List<String> lists = getByName(fdName, fdId);
		if ((lists != null) && (!lists.isEmpty()) && (lists.size() > 0)) {
			result += lists.get(0);
		}
		return result;
	}

	@Override
	public List<String> getByName(String fdName, String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdName");
		hqlInfo.setWhereBlock("hrRatifyLeaveReason.fdName = :fdName");
		if (StringUtil.isNotNull(fdId)) {
			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(),
					" and ", "hrRatifyLeaveReason.fdId !=:fdId"));
			hqlInfo.setParameter("fdId", fdId);
		}
		hqlInfo.setParameter("fdName", fdName);
		List<String> lists = findValue(hqlInfo);
		return lists;
	}

	@Override
	public boolean checkExist(String fdName) throws Exception {
		List<String> lists = getByName(fdName, null);
		return (lists != null) && (!lists.isEmpty()) && (lists.size() > 0);
	}

}
