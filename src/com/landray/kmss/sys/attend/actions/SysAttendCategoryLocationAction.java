package com.landray.kmss.sys.attend.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;

import com.landray.kmss.sys.attend.service.ISysAttendCategoryLocationService;

import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.attend.model.SysAttendCategoryLocation;
 
/**
 * 签到点 Action
 * 
 * @author 
 * @version 1.0 2017-05-24
 */
public class SysAttendCategoryLocationAction extends ExtendAction {
	protected ISysAttendCategoryLocationService sysAttendCategoryLocationService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if(sysAttendCategoryLocationService == null){
			sysAttendCategoryLocationService = (ISysAttendCategoryLocationService)getBean("sysAttendCategoryLocationService");
		}
		return sysAttendCategoryLocationService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);		
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysAttendCategoryLocation.class);
	}
}

