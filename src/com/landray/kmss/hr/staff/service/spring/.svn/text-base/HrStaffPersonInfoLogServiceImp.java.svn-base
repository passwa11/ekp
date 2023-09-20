package com.landray.kmss.hr.staff.service.spring;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoLog;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoLogService;
import com.landray.kmss.sys.log.util.UserAgentUtil;

/**
 * 员工信息日志
 * 
 * @author 潘永辉 2017-1-7
 * 
 */
public class HrStaffPersonInfoLogServiceImp extends BaseServiceImp implements
		IHrStaffPersonInfoLogService {

	@Override
	public HrStaffPersonInfoLog buildPersonInfoLog(String fdParaMethod,
			String fdDetails) throws Exception {
		HttpServletRequest request = Plugin.currentRequest();
		HrStaffPersonInfoLog log = new HrStaffPersonInfoLog();
		log.setFdParaMethod(fdParaMethod); // 操作方法
		log.setFdDetails(fdDetails); // 操作记录内容
		if (request != null) {
			log.setFdIp(request.getRemoteAddr()); // IP
		}
		log.setFdBrowser(UserAgentUtil.getBrowser()); // 浏览器
		log.setFdEquipment(UserAgentUtil.getOperatingSystem()); // 设备
		return log;
	}

	@Override
    public void savePersonInfoLog(String fdParaMethod, String fdDetails)
			throws Exception {
		super.add(buildPersonInfoLog(fdParaMethod, fdDetails));
	}

}
