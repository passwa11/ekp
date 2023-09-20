package com.landray.kmss.sys.mportal.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.util.IDGenerator;

/*
 * 获取ID
 * */
public class SysMportalGenerateId implements IXMLDataBean{
	
	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		List rtnList = new ArrayList();
		Map map = new HashMap();
		String fdId = IDGenerator.generateID();
		map.put("fdId", fdId);
		rtnList.add(map);
		return rtnList;
	}
}
