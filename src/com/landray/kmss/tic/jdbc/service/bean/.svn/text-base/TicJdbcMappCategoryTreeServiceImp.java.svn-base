package com.landray.kmss.tic.jdbc.service.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.jdbc.service.ITicJdbcMappCategoryService;
import com.landray.kmss.util.StringUtil;

public class TicJdbcMappCategoryTreeServiceImp implements IXMLDataBean {
	private ITicJdbcMappCategoryService ticJdbcMappCategoryService;

	public ITicJdbcMappCategoryService getTicJdbcMappCategoryService() {
		return ticJdbcMappCategoryService;
	}

	public void setTicJdbcMappCategoryService(
			ITicJdbcMappCategoryService ticJdbcMappCategoryService) {
		this.ticJdbcMappCategoryService = ticJdbcMappCategoryService;
	}


	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {

		String parentId = requestInfo.getParameter("parentId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
				.setSelectBlock("ticJdbcMappCategory.fdName, ticJdbcMappCategory.fdId");
		if (StringUtil.isNull(parentId)) {
			hqlInfo.setWhereBlock("ticJdbcMappCategory.hbmParent is null");
		} else {
			hqlInfo
					.setWhereBlock("ticJdbcMappCategory.hbmParent.fdId=:parentId");
			hqlInfo.setParameter("parentId", parentId);
		}
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
}
