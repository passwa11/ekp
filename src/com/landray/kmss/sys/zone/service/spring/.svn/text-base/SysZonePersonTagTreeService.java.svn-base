package com.landray.kmss.sys.zone.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.tag.model.SysTagCategory;
import com.landray.kmss.sys.tag.service.ISysTagCategoryService;
import com.landray.kmss.sys.tag.service.ISysTagTagsService;
import com.landray.kmss.util.StringUtil;

public class SysZonePersonTagTreeService implements IXMLDataBean {
	private ISysTagCategoryService sysTagCategoryService;
	private ISysTagTagsService sysTagTagsService;

	public void setSysTagTagsService(ISysTagTagsService sysTagTagsService) {
		this.sysTagTagsService = sysTagTagsService;
	}

	public void setSysTagCategoryService(
			ISysTagCategoryService sysTagCategoryService) {
		this.sysTagCategoryService = sysTagCategoryService;
	}

	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		String fdCategoryId = requestInfo.getParameter("fdCategoryId");
		String oldTagIds = requestInfo.getParameter("oldTagIds");
		List rtnList = new ArrayList();
		HQLInfo hqlInfo = new HQLInfo();
		if (StringUtil.isNull(fdCategoryId)) {
			String orderBy = "sysTagCategory.fdOrder";
			hqlInfo.setOrderBy(orderBy);
			List sysTagCategorys = sysTagCategoryService.findList(hqlInfo);
			Map node = null;
			for (int i = 0; i < sysTagCategorys.size(); i++) {
				SysTagCategory sysTagCategory = (SysTagCategory) sysTagCategorys
						.get(i);
				node = new HashMap();
				node.put("text", sysTagCategory.getFdName());
				node.put("value", sysTagCategory.getFdId());
				node.put("isShowCheckBox", "false");
				// node.put("isExpanded", "true");
				rtnList.add(node);
			}
		} else {
			Map node = null;
			hqlInfo.setSelectBlock("fdId,fdName");
			hqlInfo.setWhereBlock("sysTagTags.fdIsPrivate=:fdIsPrivate and sysTagTags.fdStatus=:fdStatus and sysTagTags.fdCategory.fdId=:fdCategoryId");
			hqlInfo.setParameter("fdIsPrivate", 2);
			hqlInfo.setParameter("fdStatus", 1);
			hqlInfo.setParameter("fdCategoryId", fdCategoryId);
			List tagsList = sysTagTagsService.findList(hqlInfo);
			for (int i = 0; i < tagsList.size(); i++) {
				Object[] object = (java.lang.Object[]) tagsList.get(i);
				node = new HashMap();
				node.put("text", object[1]);
				node.put("value", object[0]);
				node.put("isAutoFetch", "0");
				node.put("isShowCheckBox", "true");
				if (oldTagIds.contains(object[0].toString())) {
					node.put("isChecked", "true");
				}
				rtnList.add(node);
			}

		}
		return rtnList;
	}
}
