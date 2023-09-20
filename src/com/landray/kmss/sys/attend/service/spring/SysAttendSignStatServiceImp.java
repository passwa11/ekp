package com.landray.kmss.sys.attend.service.spring;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendOrgService;
import com.landray.kmss.sys.attend.service.ISysAttendSignStatService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;

/**
 * 人员签到统计表业务接口实现
 * 
 * @author
 * @version 1.0 2017-07-27
 */
public class SysAttendSignStatServiceImp extends BaseServiceImp
		implements ISysAttendSignStatService {
	private ISysAttendCategoryService sysAttendCategoryService;
	private ISysOrgCoreService sysOrgCoreService;
	private ISysAttendOrgService sysAttendOrgService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	public void setSysAttendCategoryService(
			ISysAttendCategoryService sysAttendCategoryService) {
		this.sysAttendCategoryService = sysAttendCategoryService;
	}

	public void
			setSysAttendOrgService(ISysAttendOrgService sysAttendOrgService) {
		this.sysAttendOrgService = sysAttendOrgService;
	}



}
