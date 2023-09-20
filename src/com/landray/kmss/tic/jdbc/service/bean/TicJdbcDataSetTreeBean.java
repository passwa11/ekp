/**
 * 
 */
package com.landray.kmss.tic.jdbc.service.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.tic.jdbc.model.TicJdbcDataSet;
import com.landray.kmss.tic.jdbc.service.ITicJdbcDataSetCategoryService;
import com.landray.kmss.tic.jdbc.service.ITicJdbcDataSetService;
import com.landray.kmss.util.StringUtil;

/**
 * @author qiujh
 * @version 1.0 2014-4-29
 */
public class TicJdbcDataSetTreeBean implements IXMLDataBean {
	private ITicJdbcDataSetCategoryService ticJdbcDataSetCategoryService;
	private ITicJdbcDataSetService ticJdbcDataSetService;

	public void setTicJdbcDataSetCategoryService(
			ITicJdbcDataSetCategoryService ticJdbcDataSetCategoryService) {
		this.ticJdbcDataSetCategoryService = ticJdbcDataSetCategoryService;
	}
	
	public void setTicJdbcDataSetService(
			ITicJdbcDataSetService ticJdbcDataSetService) {
		this.ticJdbcDataSetService = ticJdbcDataSetService;
	}

	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		String type = requestInfo.getParameter("type");
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		try {
			if ("cate".equals(type)) {
				getCate(requestInfo, rtnList);
			} else if ("func".equals(type)) {
				getFunc(requestInfo, rtnList);
			} else if ("search".equals(type)) {
				getSearch(requestInfo, rtnList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return rtnList;
		}
		return rtnList;
	}
	
	public List<Map<String, String>> getCate(RequestContext requestInfo, 
			List<Map<String, String>> rtnList) throws Exception {
		String parentId = requestInfo.getParameter("selectId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("ticJdbcDataSetCategory.fdName, ticJdbcDataSetCategory.fdId");
		if (StringUtil.isNull(parentId)) {
			hqlInfo.setWhereBlock("ticJdbcDataSetCategory.hbmParent is null");
		} else {
			hqlInfo.setWhereBlock("ticJdbcDataSetCategory.hbmParent.fdId=:parentId");
			hqlInfo.setParameter("parentId", parentId);
		}
		List<?> result = ticJdbcDataSetCategoryService.findList(hqlInfo);
		for (int i = 0; i < result.size(); i++) {
			Object[] obj = (Object[]) result.get(i);
			Map<String, String> node = new HashMap<String, String>();
			node.put("text", obj[0].toString());
			node.put("value", obj[1].toString());
			rtnList.add(node);
		}
		return rtnList;
	}
	
	public List<Map<String, String>> getFunc(RequestContext requestInfo, 
			List<Map<String, String>> rtnList) throws Exception {
		String parentId = requestInfo.getParameter("selectId");
		HQLInfo hqlInfo = new HQLInfo();
		if (StringUtil.isNull(parentId)) {
			hqlInfo.setWhereBlock("ticJdbcDataSet.docCategory is null");
		} else {
			hqlInfo.setWhereBlock("ticJdbcDataSet.docCategory.fdId=:parentId");
			hqlInfo.setParameter("parentId", parentId);
		}
		List<TicJdbcDataSet> dataSetList = ticJdbcDataSetService.findList(hqlInfo);
		for (TicJdbcDataSet ticJdbcDataSet : dataSetList) {
			Map<String, String> node = new HashMap<String, String>();
			node.put("name", ticJdbcDataSet.getFdName());
			node.put("id", ticJdbcDataSet.getFdId());
			node.put("dataSource", ticJdbcDataSet.getFdDataSource());
			node.put("dataSetSql", ticJdbcDataSet.getFdSqlExpression());
			node.put("fdData", ticJdbcDataSet.getFdData());
			rtnList.add(node);
		}
		return rtnList;
	}
	
	public List<Map<String, String>> getSearch(RequestContext requestInfo, 
			List<Map<String, String>> rtnList) throws Exception {
		return null;
	}

}
