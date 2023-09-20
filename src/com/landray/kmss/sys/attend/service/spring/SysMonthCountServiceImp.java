package com.landray.kmss.sys.attend.service.spring;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.service.ISysMonthCountService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

public class SysMonthCountServiceImp  extends BaseServiceImp
implements ISysMonthCountService{

	@Override
	public IExtendForm initFormSetting(IExtendForm iextendform, RequestContext requestcontext) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBaseModel initModelSetting(RequestContext requestcontext) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(SysQuartzJobContext jobContext) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stat(Date date, Map<String, JSONObject> monthDataMap) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stat(SysOrgElement orgElement, Date date, Map<String, JSONObject> monthDataMap) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stat(SysOrgElement orgElement, Date date, Boolean isStatMonth, Map<String, JSONObject> monthDataMap)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stat(List<String> orgIdList, List<Date> dateList) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stat(Date date, Boolean isStatMonth, List<String> orgList, Map<String, JSONObject> monthDataMap)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletStat(String fdCategoryId, Date date, List<String> orgList) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restat(SysQuartzJobContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add() {
		// TODO Auto-generated method stub
		
	}

}
