package com.landray.kmss.sys.filestore.service.spring;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.appconfig.model.SysAppConfig;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;

public class SysFileStoreAddModelNamesService implements IXMLDataBean {

	private ISysAppConfigService sysAppConfigService;

	public ISysAppConfigService getSysAppConfigService() {
		return sysAppConfigService;
	}

	public void setSysAppConfigService(ISysAppConfigService sysAppConfigService) {
		this.sysAppConfigService = sysAppConfigService;
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List<String> result=new ArrayList<String>();
		String appConfigKey = requestInfo.getParameter("appConfigKey");
		String modelNames = requestInfo.getParameter("modelNames");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdKey=:fdKey");
		hqlInfo.setParameter("fdKey", appConfigKey);
		SysAppConfig config=(SysAppConfig) getSysAppConfigService().findFirstOne(hqlInfo);
		config.setFdValue(modelNames);
		getSysAppConfigService().update(config);
		result.add(modelNames);
		return result;
	}

}
