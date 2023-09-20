package com.landray.kmss.tic.jdbc.service.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.jdbc.service.ITicJdbcMappCategoryService;
import com.landray.kmss.tic.jdbc.service.ITicJdbcMappManageService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class TicJdbcMappManageBeanService implements IXMLDataBean {

@Override
public List getDataList(RequestContext requestInfo) throws Exception {
	List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>(1);
	String type = requestInfo.getParameter("type");

	if ("cate".equals(type)) {
		rtnList = executeCate(requestInfo);
	} else if ("func".equals(type)) {
		rtnList = executeFunc(requestInfo);
	} else if ("search".equals(type)) {
		rtnList = executeSearch(requestInfo);
	}
	return rtnList;
}

public List<Map<String, String>> executeCate(RequestContext requestInfo)throws Exception {
		String parentId = requestInfo.getParameter("parentId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("ticJdbcMappCategory.fdName, ticJdbcMappCategory.fdId");
		if (StringUtil.isNull(parentId)) {
			hqlInfo.setWhereBlock("ticJdbcMappCategory.hbmParent is null");
		} else {
			hqlInfo.setWhereBlock("ticJdbcMappCategory.hbmParent.fdId=:parentId");
			hqlInfo.setParameter("parentId", parentId);
		}
		ITicJdbcMappCategoryService ticJdbcMappCategoryService=(ITicJdbcMappCategoryService)SpringBeanUtil.getBean("ticJdbcMappCategoryService");
		List<?> result = ticJdbcMappCategoryService.findList(hqlInfo);
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < result.size(); i++) {
			Object[] obj = (Object[]) result.get(i);
			Map<String, String> node = new HashMap<String, String>();
			node.put("text", obj[0].toString());
			node.put("value", obj[1].toString());
			rtnList.add(node);
		}
		return rtnList;
}

public List<Map<String, String>> executeFunc(RequestContext requestInfo)throws Exception {
	    String selecteId = requestInfo.getParameter("selecteId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("ticJdbcMappManage.docSubject, ticJdbcMappManage.fdId");
		if (StringUtil.isNotNull(selecteId)) {
			hqlInfo.setWhereBlock("ticJdbcMappManage.docCategory.fdId=:selecteId and ticJdbcMappManage.fdIsEnabled=true");
			hqlInfo.setParameter("selecteId", selecteId);
		}
		ITicJdbcMappManageService ticJdbcMappManageService=(ITicJdbcMappManageService)SpringBeanUtil.getBean("ticJdbcMappManageService");
		List<?> result = ticJdbcMappManageService.findList(hqlInfo);
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < result.size(); i++) {
			Object[] obj = (Object[]) result.get(i);
			Map<String, String> node = new HashMap<String, String>();
			//这里的key必须取id,name
			node.put("name", obj[0].toString());
			node.put("id", obj[1].toString());
			rtnList.add(node);
		}
		return rtnList;
}

public List<Map<String, String>> executeSearch(RequestContext requestInfo)
		throws Exception {

    String selecteId = requestInfo.getParameter("selecteId");
    String keyWord= requestInfo.getParameter("keyword");
    
	HQLInfo hqlInfo = new HQLInfo();
	hqlInfo.setSelectBlock("ticJdbcMappManage.docSubject, ticJdbcMappManage.fdId");
	if (StringUtil.isNotNull(selecteId)) {
		hqlInfo.setWhereBlock("ticJdbcMappManage.docCategory.fdId=:selecteId");
		hqlInfo.setParameter("selecteId", selecteId);
	}
	
	String hqlInformation=hqlInfo.getWhereBlock();
	if(StringUtil.isNotNull(keyWord)){
		hqlInformation=StringUtil.linkString(hqlInformation, " and ", "ticJdbcMappManage.docSubject like :keyWord ");
		hqlInfo.setParameter("keyWord", "%"+keyWord+"%");
	}
	hqlInfo.setWhereBlock(hqlInformation);
	
	ITicJdbcMappManageService ticJdbcMappManageService=(ITicJdbcMappManageService)SpringBeanUtil.getBean("ticJdbcMappManageService");
	List<?> result = ticJdbcMappManageService.findList(hqlInfo);
	List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
	for (int i = 0; i < result.size(); i++) {
		Object[] obj = (Object[]) result.get(i);
		Map<String, String> node = new HashMap<String, String>();
		node.put("name", obj[0].toString());
		node.put("id", obj[1].toString());
		rtnList.add(node);
	}
	return rtnList;

}

}
