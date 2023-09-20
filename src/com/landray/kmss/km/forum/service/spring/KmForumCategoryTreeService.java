package com.landray.kmss.km.forum.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.km.forum.service.IKmForumCategoryService;
import com.landray.kmss.util.StringUtil;

/**
 * 论坛模块菜单树
 * 
 * 创建日期 2006-Aug-31
 * 
 * @author 吴兵
 */

public class KmForumCategoryTreeService implements IXMLDataBean {
	private IKmForumCategoryService kmForumCategoryService;

	@Override
    public List getDataList(RequestContext xmlContext) throws Exception {
		List rtnList = new ArrayList();
		String whereBlock = null;
		HQLInfo hqlInfo = new HQLInfo();
		
		String categoryId = xmlContext.getRequest().getParameter("categoryId");
		if (StringUtil.isNotNull(categoryId)) {
			whereBlock = "kmForumCategory.hbmParent.fdId = :categoryId";
			hqlInfo.setParameter("categoryId", categoryId);
		} else {
			whereBlock = "kmForumCategory.hbmParent is null ";
		}
		hqlInfo.setSelectBlock("kmForumCategory.fdName,kmForumCategory.fdId");
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy("kmForumCategory.fdOrder asc");
		List kmForumCategorys = kmForumCategoryService.findValue(hqlInfo);
		for (int i = 0; i < kmForumCategorys.size(); i++) {
			Object[] info = (Object[]) kmForumCategorys.get(i);
			HashMap node = new HashMap();
			node.put("text", info[0].toString());
			node.put("value", info[1]);
			if (StringUtil.isNotNull(categoryId)) {
				node.put("isShowCheckBox", true);
			} else {
				node.put("isShowCheckBox", false);
			}
			node.put("beanName", "kmForumCategoryTreeService&categoryId="
					+ info[1]);
			node.put("nodeType", "CATEGORY");
			rtnList.add(node);
		}
		return rtnList;
	}

	public void setKmForumCategoryService(
			IKmForumCategoryService kmForumCategoryService) {
		this.kmForumCategoryService = kmForumCategoryService;
	}
}
