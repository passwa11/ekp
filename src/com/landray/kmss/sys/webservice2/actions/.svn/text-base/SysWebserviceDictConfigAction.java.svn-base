package com.landray.kmss.sys.webservice2.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;

import com.landray.kmss.sys.webservice2.service.ISysWebserviceRestConfigService;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceDictConfigService;

import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.webservice2.model.SysWebserviceDictConfig;
import com.landray.kmss.sys.webservice2.forms.SysWebserviceRestConfigForm;
import com.landray.kmss.sys.webservice2.forms.SysWebserviceDictConfigForm;

import com.landray.kmss.common.service.ICoreOuterService;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.simplecategory.interfaces.SimpleCategoryUtil;


import com.landray.kmss.common.forms.IExtendForm;
 

import com.landray.kmss.sys.webservice2.model.SysWebserviceDictConfig;
import com.landray.kmss.sys.webservice2.model.SysWebserviceRestConfig;

 
/**
 * 数据字典配置 Action
 * 
 * @author 
 * @version 1.0 2017-12-21
 */
public class SysWebserviceDictConfigAction extends ExtendAction {
	protected ISysWebserviceDictConfigService sysWebserviceDictConfigService;

	@Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
		if(sysWebserviceDictConfigService == null){
			sysWebserviceDictConfigService = (ISysWebserviceDictConfigService)getBean("sysWebserviceDictConfigService");
		}
		return sysWebserviceDictConfigService;
	}

	@Override
    protected void changeFindPageHQLInfo(HttpServletRequest request,
                                         HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);		
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysWebserviceDictConfig.class);
	}
}

