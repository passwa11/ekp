package com.landray.kmss.sys.zone.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;

import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.zone.service.ISysZonePersonAttenFanService;

import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.zone.model.SysZonePersonAttenFan;
import com.landray.kmss.sys.zone.forms.SysZonePersonAttenFanForm;
import com.landray.kmss.sys.organization.forms.SysOrgElementForm;

import com.landray.kmss.common.service.ICoreOuterService;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.simplecategory.interfaces.SimpleCategoryUtil;


import com.landray.kmss.common.forms.IExtendForm;
 

import com.landray.kmss.sys.zone.model.SysZonePersonAttenFan;
import com.landray.kmss.sys.organization.model.SysOrgElement;

 
/**
 * 关注/粉丝信息 Action
 * 
 * @author XuJieYang
 * @version 1.0 2014-08-28
 */
public class SysZonePersonAttenFanAction extends ExtendAction {
	protected ISysZonePersonAttenFanService sysZonePersonAttenFanService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if(sysZonePersonAttenFanService == null){
			sysZonePersonAttenFanService = (ISysZonePersonAttenFanService)getBean("sysZonePersonAttenFanService");
		}
		return sysZonePersonAttenFanService;
	}

	@Override
    protected void changeFindPageHQLInfo(HttpServletRequest request,
                                         HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);		

		CriteriaValue cv = new CriteriaValue(request);
		
		String where = hqlInfo.getWhereBlock();
		if(StringUtil.isNull(where)){
			where = " 1=1 ";
		}
		
		CriteriaUtil.buildHql(cv, hqlInfo, SysZonePersonAttenFan.class);
	}
}

