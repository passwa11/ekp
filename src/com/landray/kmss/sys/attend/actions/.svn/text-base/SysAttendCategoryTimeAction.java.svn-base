package com.landray.kmss.sys.attend.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;

import com.landray.kmss.sys.attend.service.ISysAttendCategoryTimeService;

import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTime;
 
/**
 * 签到追加日期 Action
 * 
 * @author 
 * @version 1.0 2017-05-24
 */
public class SysAttendCategoryTimeAction extends ExtendAction {
	protected ISysAttendCategoryTimeService sysAttendCategoryTimeService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if(sysAttendCategoryTimeService == null){
			sysAttendCategoryTimeService = (ISysAttendCategoryTimeService)getBean("sysAttendCategoryTimeService");
		}
		return sysAttendCategoryTimeService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);		
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysAttendCategoryTime.class);
	}
}

