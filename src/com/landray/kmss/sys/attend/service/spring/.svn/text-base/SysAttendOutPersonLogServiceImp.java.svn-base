package com.landray.kmss.sys.attend.service.spring;

import java.util.Date;
import java.util.List;

import org.hibernate.type.StandardBasicTypes;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attend.model.SysAttendOutPersonLog;
import com.landray.kmss.sys.attend.service.ISysAttendOutPersonLogService;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-08-23
 */
public class SysAttendOutPersonLogServiceImp extends BaseServiceImp
		implements ISysAttendOutPersonLogService {

	@Override
	public List<SysAttendOutPersonLog> findOutPersonLogs(String phoneNum,
			Date availableTime) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"sysAttendOutPersonLog.fdUserPhoneNum=:phoneNum and sysAttendOutPersonLog.fdCreateTime>:availableTime");
		hqlInfo.setOrderBy("sysAttendOutPersonLog.fdCreateTime desc");
		hqlInfo.setParameter("phoneNum", phoneNum);
		hqlInfo.setParameter("availableTime", availableTime,
				StandardBasicTypes.TIMESTAMP);
		List list = getBaseDao().findList(hqlInfo);
		return list;
	}


}
