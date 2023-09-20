package com.landray.kmss.km.comminfo.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.km.comminfo.service.IKmComminfoCategoryService;
import com.landray.kmss.util.StringUtil;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * 
 * @author 徐乃瑞 案例类别树加载时调用的借口实现
 * 
 */
public class KmComminfoCategoryTreeServiceImpl implements IXMLDataBean {
	private IKmComminfoCategoryService kmComminfoCategoryService;

	public void setKmComminfoCategoryService(
			IKmComminfoCategoryService kmComminfoCategoryService) {
		this.kmComminfoCategoryService = kmComminfoCategoryService;
	}

	@Override
    public List<Map<String, String>> getDataList(RequestContext requestInfo)
			throws Exception {
		// 需要转移的分类，用于转移时剔除当前已经选择的分类，多个ID用;分隔
		String cateIds = requestInfo.getParameter("cateIds");
		List<Object> cateIdList = null;
		if(StringUtil.isNotNull(cateIds)){
			cateIdList = Arrays.asList(cateIds.split(";"));
		}
		
		String parentId = requestInfo.getParameter("parentId");
		// 用于portlet的标志
		String portlet = requestInfo.getRequest().getParameter("portlet");
		if (!StringUtil.isNull(parentId)) {
			return null;
		}
		List<?> result = this.kmComminfoCategoryService.findValue(
				"kmComminfoCategory.fdName, kmComminfoCategory.fdId", null,
				"kmComminfoCategory.fdOrder, kmComminfoCategory.fdName asc");
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < result.size(); i++) {
			Object[] obj = (Object[]) result.get(i);
			
			// 跳过当前选中的分类
			if (cateIdList != null && cateIdList.contains(obj[1])) {
				continue;
			}
			
			Map<String, String> node = new HashMap<String, String>();
			node.put("text", obj[0].toString());
			node.put("value", obj[1].toString());
			node.put("id", obj[1].toString());
			node.put("name", obj[0].toString());
			if (StringUtil.isNotNull(portlet)) {
				node.put("nodeType", "CATEGORY");
			}
			rtnList.add(node);
		}
		return rtnList;
	}

}
