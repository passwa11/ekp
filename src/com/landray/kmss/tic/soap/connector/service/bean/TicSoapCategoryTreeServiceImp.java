package com.landray.kmss.tic.soap.connector.service.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.soap.connector.model.TicSoapCategory;
import com.landray.kmss.tic.soap.connector.service.ITicSoapCategoryService;
import com.landray.kmss.util.StringUtil;

/**
 * 获取分类树形结构
 * @author zhangtian
 * date :2012-8-6 上午08:04:01
 */
public class TicSoapCategoryTreeServiceImp  implements IXMLDataBean {

	private ITicSoapCategoryService ticSoapCategoryService;
	
	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		String parentId = requestInfo.getParameter("parentId");
		String flag = requestInfo.getParameter("flag");
		HQLInfo hqlInfo = new HQLInfo();

		if (StringUtil.isNull(parentId)) {
			hqlInfo.setWhereBlock("ticSoapCategory.hbmParent is null");

		} else {
			hqlInfo.setWhereBlock("ticSoapCategory.hbmParent.fdId=:parentId");
			hqlInfo.setParameter("parentId", parentId);
		}
		if (StringUtil.isNotNull(hqlInfo.getWhereBlock())) {
			List result = ticSoapCategoryService.findList(hqlInfo);

			List rtnValue = new ArrayList();
			for (int i = 0; i < result.size(); i++) {
				TicSoapCategory ticSoapCategory = (TicSoapCategory) result.get(i);
				Map node = new HashMap();
				node.put("text", ticSoapCategory.getFdName());
				node.put("value", ticSoapCategory.getFdId());
				rtnValue.add(node);
			}
			return rtnValue;
		} else {
			return null;
		}
	}

	public ITicSoapCategoryService getTicSoapCategoryService() {
		return ticSoapCategoryService;
	}

	public void setTicSoapCategoryService(
			ITicSoapCategoryService ticSoapCategoryService) {
		this.ticSoapCategoryService = ticSoapCategoryService;
	}

	
	
	
}
