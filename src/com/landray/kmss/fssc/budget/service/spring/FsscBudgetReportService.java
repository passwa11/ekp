package com.landray.kmss.fssc.budget.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.fssc.budget.service.IFsscBudgetDataService;
import com.landray.kmss.util.StringUtil;

public class FsscBudgetReportService implements IXMLDataBean{
	
	protected IFsscBudgetDataService fsscBudgetDataService;
	
	public void setFsscBudgetDataService(IFsscBudgetDataService fsscBudgetDataService) {
		this.fsscBudgetDataService = fsscBudgetDataService;
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String fdType=requestInfo.getParameter("fdType");
		List rtnList=new ArrayList<>();
		HQLInfo hqlInfo = new HQLInfo();
		if("getYear".equals(fdType)){
			hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy("fsscBudgetData.fdYear");
			hqlInfo.setSelectBlock("distinct fsscBudgetData.fdYear ");
			List<String> years = fsscBudgetDataService.findValue(hqlInfo);
			if (years.size() == 0) {
				Map<String, String> nodeMap = new HashMap<String, String>();
				nodeMap.put("value", "");
				nodeMap.put("text", "");
				rtnList.add(nodeMap);
				return rtnList;
			}
			for (int i = 0; i < years.size(); i++) {
				String valueL =  years.get(i);
				if(StringUtil.isNotNull(valueL)){
					Map<String, String> nodeMap = new HashMap<String, String>();
					nodeMap.put("value", valueL.substring(1, 5));
					nodeMap.put("text", valueL.toString().substring(1, 5));
					rtnList.add(nodeMap);
				}
			}
		}
		return rtnList;
	}

}
