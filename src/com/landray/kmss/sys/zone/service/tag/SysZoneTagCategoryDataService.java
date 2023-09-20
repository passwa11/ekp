package com.landray.kmss.sys.zone.service.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.tag.model.SysTagCategory;
import com.landray.kmss.sys.tag.service.ISysTagCategoryService;

public class SysZoneTagCategoryDataService implements IXMLDataBean {
	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		String type = requestInfo.getParameter("type");
		List rtnList = new ArrayList();
		if ("1".equals(type)) {
			HQLInfo hqlInfo = new HQLInfo();
			String orderBy = "sysTagCategory.fdOrder";
			hqlInfo.setOrderBy(orderBy);
			// hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_READER);
			List sysTagCategorys = sysTagCategoryService.findList(hqlInfo);
			Map node = new HashMap();
			node.put("text", "未分类");
			node.put("value", null);
			node.put("isAutoFetch", "0");
			rtnList.add(node);
			for (int i = 0; i < sysTagCategorys.size(); i++) {
				SysTagCategory sysTagCategory = (SysTagCategory) sysTagCategorys
						.get(i);
				node = new HashMap();
				node.put("text", sysTagCategory.getFdName());
				node.put("value", sysTagCategory.getFdId());
				node.put("isAutoFetch", "0");
				rtnList.add(node);
			}
		} else {
			HQLInfo hqlInfo = new HQLInfo();
			String orderBy = "sysTagCategory.fdOrder";
			hqlInfo.setOrderBy(orderBy);
			hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_READER);
			List sysTagCategorys = sysTagCategoryService.findList(hqlInfo);
			for (int i = 0; i < sysTagCategorys.size(); i++) {
				SysTagCategory sysTagCategory = (SysTagCategory) sysTagCategorys
						.get(i);
				Map node = new HashMap();
				node.put("name", sysTagCategory.getFdName());
				node.put("id", sysTagCategory.getFdId());
				node.put("isAutoFetch", "0");
				rtnList.add(node);
			}
		}
		return rtnList;
	}

	private ISysTagCategoryService sysTagCategoryService;

	public void setSysTagCategoryService(
			ISysTagCategoryService sysTagCategoryService) {
		this.sysTagCategoryService = sysTagCategoryService;
	}
}
