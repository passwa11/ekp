package com.landray.kmss.sys.organization.service.spring;

import java.util.Date;
import java.util.List;

import org.hibernate.type.StandardBasicTypes;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgRetrievePasswordLog;
import com.landray.kmss.sys.organization.service.ISysOrgRetrievePasswordLogService;

public class SysOrgRetrievePasswordLogServiceImp extends BaseServiceImp implements ISysOrgRetrievePasswordLogService{

	@Override
	public List<SysOrgRetrievePasswordLog> findRetrievePasswordLogs(
			String personId, Date availableTime) throws Exception {
		// TODO 自动生成的方法存根
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysOrgRetrievePasswordLog.fdPerson.fdId=:personId and sysOrgRetrievePasswordLog.fdCreateTime>:availableTime");
		hqlInfo.setOrderBy("sysOrgRetrievePasswordLog.fdCreateTime desc");
		hqlInfo.setParameter("personId", personId);
		hqlInfo.setParameter("availableTime", availableTime, StandardBasicTypes.TIMESTAMP);
		List list = getBaseDao().findList(hqlInfo);
		return list;
	}


}
