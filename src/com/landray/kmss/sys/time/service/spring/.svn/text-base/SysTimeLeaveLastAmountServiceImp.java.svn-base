package com.landray.kmss.sys.time.service.spring;

import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.time.model.SysTimeLeaveLastAmount;
import com.landray.kmss.sys.time.service.ISysTimeLeaveLastAmountService;

public class SysTimeLeaveLastAmountServiceImp extends BaseServiceImp
		implements ISysTimeLeaveLastAmountService {

	@Override
	public List findUserLeaveList(String fdPersonId, String fdLeaveId)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer sb = new StringBuffer();
		sb.append(
				"sysTimeLeaveLastAmount.fdPerson.fdId=:fdPersonId and sysTimeLeaveLastAmount.fdLeaveId=:fdLeaveId");
		sb.append(" and sysTimeLeaveLastAmount.fdTotalDay>0 ");
		hqlInfo.setWhereBlock(sb.toString());
		hqlInfo.setParameter("fdPersonId", fdPersonId);
		hqlInfo.setParameter("fdLeaveId", fdLeaveId);
		List<SysTimeLeaveLastAmount> lastAmountList = findList(hqlInfo);
		return lastAmountList;
	}


}
