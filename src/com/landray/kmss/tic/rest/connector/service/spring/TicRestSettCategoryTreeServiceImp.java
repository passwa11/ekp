package com.landray.kmss.tic.rest.connector.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.rest.connector.service.ITicRestSettCategoryService;
import com.landray.kmss.util.StringUtil;

public class TicRestSettCategoryTreeServiceImp implements IXMLDataBean {
	private ITicRestSettCategoryService ticRestSettCategoryService;

	@Override
    public List<Map<String, String>> getDataList(RequestContext requestInfo) throws Exception {
		String parentId = requestInfo.getParameter("parentId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("ticRestSettCategory.fdName, ticRestSettCategory.fdId");
		if (StringUtil.isNull(parentId)) {
			hqlInfo.setWhereBlock("ticRestSettCategory.hbmParent is null");
		} else {
			hqlInfo.setWhereBlock("ticRestSettCategory.hbmParent.fdId=:parentId");
			hqlInfo.setParameter("parentId", parentId);
		}
		List<?> result = ticRestSettCategoryService.findList(hqlInfo);
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

	public ITicRestSettCategoryService getTicRestSettCategoryService() {
		return ticRestSettCategoryService;
	}

	public void setTicRestSettCategoryService(ITicRestSettCategoryService ticRestSettCategoryService) {
		this.ticRestSettCategoryService = ticRestSettCategoryService;
	}

}
