package com.landray.kmss.sys.attend.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;

import com.landray.kmss.sys.attend.service.ISysAttendCategoryWorktimeService;

import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
 
/**
 * 考勤班次 Action
 * 
 * @author 
 * @version 1.0 2017-05-24
 */
public class SysAttendCategoryWorktimeAction extends ExtendAction {
	protected ISysAttendCategoryWorktimeService sysAttendCategoryWorktimeService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if(sysAttendCategoryWorktimeService == null){
			sysAttendCategoryWorktimeService = (ISysAttendCategoryWorktimeService)getBean("sysAttendCategoryWorktimeService");
		}
		return sysAttendCategoryWorktimeService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);		
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysAttendCategoryWorktime.class);
	}
}

