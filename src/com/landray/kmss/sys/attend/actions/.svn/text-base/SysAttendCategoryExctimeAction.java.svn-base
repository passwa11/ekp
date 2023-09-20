package com.landray.kmss.sys.attend.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryExctimeService;

import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.attend.model.SysAttendCategoryExctime;

 
/**
 * 签到例外日期 Action
 * 
 * @author 
 * @version 1.0 2017-05-24
 */
public class SysAttendCategoryExctimeAction extends ExtendAction {
	protected ISysAttendCategoryExctimeService sysAttendCategoryExctimeService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if(sysAttendCategoryExctimeService == null){
			sysAttendCategoryExctimeService = (ISysAttendCategoryExctimeService)getBean("sysAttendCategoryExctimeService");
		}
		return sysAttendCategoryExctimeService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);		
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysAttendCategoryExctime.class);
	}
}

