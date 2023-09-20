package com.landray.kmss.tic.soap.connector.service.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.soap.connector.service.ITicSoapSettCategoryService;
import com.landray.kmss.util.StringUtil;

public class TicSoapSettCategoryTreeServiceImp  implements IXMLDataBean{
	private ITicSoapSettCategoryService ticSoapSettCategoryService;
	
	@Override
    public List<Map<String, String>> getDataList(RequestContext requestInfo) throws Exception {
		String parentId = requestInfo.getParameter("parentId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("ticSoapSettCategory.fdName, ticSoapSettCategory.fdId");
		if (StringUtil.isNull(parentId)) {
			hqlInfo.setWhereBlock("ticSoapSettCategory.hbmParent is null");
		} else {
			hqlInfo
					.setWhereBlock("ticSoapSettCategory.hbmParent.fdId=:parentId");
			hqlInfo.setParameter("parentId", parentId);
		}
		List<?> result = ticSoapSettCategoryService.findList(hqlInfo);
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
	public ITicSoapSettCategoryService getTicSoapSettCategoryService() {
		return ticSoapSettCategoryService;
	}
	public void setTicSoapSettCategoryService(
			ITicSoapSettCategoryService ticSoapSettCategoryService) {
		this.ticSoapSettCategoryService = ticSoapSettCategoryService;
	}
	
	
	
}
