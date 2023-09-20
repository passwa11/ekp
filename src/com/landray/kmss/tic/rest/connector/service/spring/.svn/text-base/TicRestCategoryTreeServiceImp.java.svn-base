package com.landray.kmss.tic.rest.connector.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.rest.connector.model.TicRestCategory;
import com.landray.kmss.tic.rest.connector.service.ITicRestCategoryService;
import com.landray.kmss.util.StringUtil;

/**
 * 获取分类树形结构
 */
public class TicRestCategoryTreeServiceImp  implements IXMLDataBean {

	private ITicRestCategoryService ticRestCategoryService;

	public ITicRestCategoryService getTicRestCategoryService() {
		return ticRestCategoryService;
	}

	public void setTicRestCategoryService(
			ITicRestCategoryService ticRestCategoryService) {
		this.ticRestCategoryService = ticRestCategoryService;
	}
	
	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		String parentId = requestInfo.getParameter("parentId");
		String flag = requestInfo.getParameter("flag");
		HQLInfo hqlInfo = new HQLInfo();

		if (StringUtil.isNull(parentId)) {
			hqlInfo.setWhereBlock("ticRestCategory.hbmParent is null");

		} else {
			hqlInfo.setWhereBlock("ticRestCategory.hbmParent.fdId=:parentId");
			hqlInfo.setParameter("parentId", parentId);
		}
		if (StringUtil.isNotNull(hqlInfo.getWhereBlock())) {
			List result = ticRestCategoryService.findList(hqlInfo);

			List rtnValue = new ArrayList();
			for (int i = 0; i < result.size(); i++) {
				TicRestCategory ticRestCategory = (TicRestCategory) result
						.get(i);
				Map node = new HashMap();
				node.put("text", ticRestCategory.getFdName());
				node.put("value", ticRestCategory.getFdId());
				rtnValue.add(node);
			}
			return rtnValue;
		} else {
			return null;
		}
	}
	
}
