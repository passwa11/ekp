package com.landray.kmss.sys.mportal.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.mportal.model.SysMportalModuleCate;
import com.landray.kmss.sys.mportal.service.ISysMportalModuleCateService;

public class SysMportalModuleCateTreeService implements IXMLDataBean {

	private ISysMportalModuleCateService sysMportalModuleCateService;

	public void setSysMportalModuleCateService(
			ISysMportalModuleCateService sysMportalModuleCateService) {
		this.sysMportalModuleCateService = sysMportalModuleCateService;
	}

	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		List rtnList = new ArrayList();
		String fdId = requestInfo.getParameter("fdId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setOrderBy("sysMportalModuleCate.fdOrder asc");
		List<SysMportalModuleCate> cateList = sysMportalModuleCateService
				.findList(hqlInfo);
		for (SysMportalModuleCate sysMportalModuleCate : cateList) {
				Map node = new HashMap();
			node.put("text", sysMportalModuleCate.getFdName());
			node.put("value", sysMportalModuleCate.getFdId());
				node.put("nodeType", "CATEGORY");
				node.put("isAutoFetch", "0");
				rtnList.add(node);
			}
		return rtnList;
	}

}
