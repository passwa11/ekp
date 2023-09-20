package com.landray.kmss.third.pda.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.third.pda.model.PdaModuleCate;
import com.landray.kmss.third.pda.service.IPdaModuleCateService;

public class PdaModuleCateTreeService implements IXMLDataBean {

	private IPdaModuleCateService pdaModuleCateService;

	public void setPdaModuleCateService(IPdaModuleCateService pdaModuleCateService) {
		this.pdaModuleCateService = pdaModuleCateService;
	}

	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		List rtnList = new ArrayList();
		String fdId = requestInfo.getParameter("fdId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setOrderBy("pdaModuleCate.fdOrder asc");
		List<PdaModuleCate> cateList = pdaModuleCateService.findList(hqlInfo);
		for (PdaModuleCate pdaModuleCate : cateList) {
				Map node = new HashMap();
				node.put("text", pdaModuleCate.getFdName());
				node.put("value", pdaModuleCate.getFdId());
				node.put("nodeType", "CATEGORY");
				node.put("isAutoFetch", "0");
				rtnList.add(node);
			}
		return rtnList;
	}

}
