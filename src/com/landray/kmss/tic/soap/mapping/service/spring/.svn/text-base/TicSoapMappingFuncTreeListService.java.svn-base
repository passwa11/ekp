package com.landray.kmss.tic.soap.mapping.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.soap.connector.model.TicSoapCategory;
import com.landray.kmss.tic.soap.connector.model.TicSoapMain;
import com.landray.kmss.tic.soap.connector.service.ITicSoapCategoryService;
import com.landray.kmss.tic.soap.connector.service.ITicSoapMainService;
import com.landray.kmss.util.StringUtil;

public class TicSoapMappingFuncTreeListService implements IXMLDataBean {

	private ITicSoapCategoryService ticSoapCategoryService;

	private ITicSoapMainService ticSoapMainService;

	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		// TODO 自动生成的方法存根
		String type = requestInfo.getParameter("type");
		String selectId = requestInfo.getParameter("selectId");
		String keyword = requestInfo.getParameter("keyword");
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>(1);
		// 分类查找
		if ("cate".equals(type)) {
			HQLInfo hqlInfo = new HQLInfo();
			if (StringUtil.isNull(selectId)) {
				hqlInfo.setWhereBlock("ticSoapCategory.hbmParent is null");
				hqlInfo.setOrderBy("ticSoapCategory.fdOrder");
			} else {
				hqlInfo.setWhereBlock(" ticSoapCategory.hbmParent.fdId =:fdId ");
				hqlInfo.setParameter("fdId", selectId);
				hqlInfo.setOrderBy("ticSoapCategory.fdOrder");
			}
			List<TicSoapCategory> dbList = ticSoapCategoryService
					.findList(hqlInfo);
			for (TicSoapCategory ticSoapCategory : dbList) {
				Map<String, String> h_map = new HashMap<String, String>();
				h_map.put("text", ticSoapCategory.getFdName());
				h_map.put("value", ticSoapCategory.getFdId());
				rtnList.add(h_map);
			}
		} else if ("func".equals(type)) {
			// 函数查找
			HQLInfo hqlInfo = new HQLInfo();
			if (StringUtil.isNull(selectId)) {
				hqlInfo.setWhereBlock("ticSoapMain.wsEnable = 1");
			} else {
				hqlInfo.setWhereBlock("ticSoapMain.wsEnable = 1 and ticSoapMain.docIsNewVersion =:docIsNewVersion and "
								+ " ticSoapMain.docCategory.fdId in "
								+ " (select ticSoapCategory.fdId from com.landray.kmss.tic.soap.connector.model.TicSoapCategory ticSoapCategory where ticSoapCategory.fdHierarchyId like :selectId ) ");
				hqlInfo.setParameter("selectId", "%" + selectId + "%");
				hqlInfo.setParameter("docIsNewVersion", true);
			}
			List<TicSoapMain> dbList = ticSoapMainService.findList(hqlInfo);
			for (TicSoapMain ticSoapMain : dbList) {
				Map<String, String> h_map = new HashMap<String, String>();
				h_map.put("name", ticSoapMain.getFdName());
				h_map.put("id", ticSoapMain.getFdId());
				rtnList.add(h_map);
			}
		} else if ("search".equals(type) && StringUtil.isNotNull(keyword)) {
			// 搜索
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("ticSoapMain.docSubject like :keyword and ticSoapMain.docIsNewVersion =:docIsNewVersion");
			hqlInfo.setParameter("keyword", "%" + keyword + "%");
			hqlInfo.setParameter("docIsNewVersion", true);
			List<TicSoapMain> dbList = ticSoapMainService.findList(hqlInfo);
			for (TicSoapMain ticSoapMain : dbList) {
				Map<String, String> h_map = new HashMap<String, String>();
				h_map.put("name", ticSoapMain.getFdName());
				h_map.put("id", ticSoapMain.getFdId());
				rtnList.add(h_map);
			}
		}
		return rtnList;
	}

	public ITicSoapCategoryService getticSoapCategoryService() {
		return ticSoapCategoryService;
	}

	public void setticSoapCategoryService(
			ITicSoapCategoryService ticSoapCategoryService) {
		this.ticSoapCategoryService = ticSoapCategoryService;
	}

	public ITicSoapMainService getticSoapMainService() {
		return ticSoapMainService;
	}

	public void setticSoapMainService(
			ITicSoapMainService ticSoapMainService) {
		this.ticSoapMainService = ticSoapMainService;
	}

}
