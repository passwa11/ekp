package com.landray.kmss.hr.staff.service.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.tag.constant.Constant;
import com.landray.kmss.sys.tag.model.SysTagTags;
import com.landray.kmss.sys.tag.service.ISysTagTagsService;
import com.landray.kmss.util.StringUtil;

public class TagByCategoryDatabean implements IXMLDataBean {

	private ISysTagTagsService sysTagTagsService;

	public void setSysTagTagsService(ISysTagTagsService sysTagTagsService) {
		this.sysTagTagsService = sysTagTagsService;
	}

	@Override
    public List<Map<String, Object>> getDataList(RequestContext requestInfo)
			throws Exception {
		List rtnList = new ArrayList();
		String type = requestInfo.getParameter("type");
		if ("getTag".equals(type)) {
			rtnList = getList(requestInfo);
		} else if ("search".equals(type)) {
			rtnList = getSearchResult(requestInfo);
		}
		return rtnList;
	}

	public List<Map<String, Object>> getList(RequestContext requestInfo)
			throws Exception {
		String fdCategoryId = requestInfo.getParameter("fdCategoryId");
		HQLInfo hqlInfo = new HQLInfo();
		if (!StringUtil.isNull(fdCategoryId)) {
			hqlInfo
					.setWhereBlock("sysTagTags.fdCategory.fdId = :fdCategoryId and sysTagTags.fdStatus = "
							+ Constant.SYS_TAG_STATUS_INTEGER_TRUE
							+ " and sysTagTags.fdIsPrivate = "
							+ Constant.SYS_TAG_STRING_PERSON);
			hqlInfo.setParameter("fdCategoryId", fdCategoryId);
		} else {
			hqlInfo
					.setWhereBlock("sysTagTags.fdCategory.fdId is null and sysTagTags.fdStatus = "
							+ Constant.SYS_TAG_STATUS_INTEGER_TRUE
							+ " and sysTagTags.fdIsPrivate = "
							+ Constant.SYS_TAG_STRING_PERSON);
		}
		hqlInfo.setOrderBy("sysTagTags.docCreateTime asc");
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		List tagTagsList = sysTagTagsService.findList(hqlInfo);
		Map<String, Object> dataMap = null;
		for (Iterator<SysTagTags> itera = tagTagsList.iterator(); itera
				.hasNext();) {
			SysTagTags sysTagTags = (SysTagTags) itera.next();
			dataMap = new HashMap<String, Object>();
			dataMap.put("name", sysTagTags.getFdName());
			dataMap.put("id", sysTagTags.getFdName());
			dataMap.put("text", sysTagTags.getFdName());
			dataMap.put("value", sysTagTags.getFdId());

			resultList.add(dataMap);
		}
		return resultList;
	}

	private List getSearchResult(RequestContext xmlContext) throws Exception {
		List rtnList = new ArrayList();
		String key = xmlContext.getRequest().getParameter("key");
		String whereBlock = "";
		HQLInfo hqlInfo = new HQLInfo();
		if (StringUtil.isNotNull(key)) {
			whereBlock = " sysTagTags.fdStatus = "
					+ Constant.SYS_TAG_STATUS_INTEGER_TRUE
					+ " and sysTagTags.fdName like :key and sysTagTags.fdIsPrivate = "
					+ Constant.SYS_TAG_STRING_PERSON + ")";
			hqlInfo.setParameter("key", "%" + key + "%");
		}
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setSelectBlock("sysTagTags.fdId, sysTagTags.fdName");
		List list = sysTagTagsService.findValue(hqlInfo);
		if (!list.isEmpty() && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] info = (Object[]) list.get(i);
				rtnList.add(info);
			}
		}
		return rtnList;
	}

}
