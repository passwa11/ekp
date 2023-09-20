package com.landray.kmss.eop.basedata.actions;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictListProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.dict.SysDictModelProperty;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 基础数据选择框统一数据接口类
 */
public class EopBasedataDialogAciton extends ExtendAction {
	/**
	 * 数据查询主方法
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward select(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-select", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			// 弹框类型拓展预留字段type
			String type = request.getParameter("type");
			this.loadSelectCategories(request);

		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("EopBasedataDialogAciton-select", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}

	protected void loadSelectCategories(HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		JSONArray columns = new JSONArray();
		JSONArray datas = new JSONArray();
		JSONObject page = new JSONObject();
		String displayField = request.getParameter("displayField");
		String qSearch = request.getParameter("q._keyword");
		String s_pageno = request.getParameter("pageno");
		String order = request.getParameter("order");
		String where = request.getParameter("where");
		if (StringUtil.isNull(s_pageno)) {
			s_pageno = "1";
		}
		String s_rowsize = request.getParameter("rowsize");
		int pageno = 0;
		int rowsize = SysConfigParameters.getRowSize();
		if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
			pageno = Integer.parseInt(s_pageno);
		}
		if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
			rowsize = Integer.parseInt(s_rowsize);
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
		hqlInfo.setPageNo(pageno);
		hqlInfo.setRowSize(rowsize);

		String modelName = request.getParameter("modelName");
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		IBaseService service = (IBaseService) SpringBeanUtil.getBean(dict.getServiceBean());
		String tableName = ModelUtil.getModelTableName(modelName);

		List<String> displayFieldList = null;
		if (StringUtil.isNotNull(displayField)) {
			String[] split = displayField.split("[,，]");
			displayFieldList = Arrays.asList(split);
		}

		List<SysDictCommonProperty> propertyList = dict.getPropertyList();
		JSONObject property = new JSONObject();
		// fdId默认为必查且隐藏属性
		property.put("property", "fdId");
		property.put("type", "String");
		if (null != displayFieldList && displayFieldList.contains("fdId")) {
			property.put("title", ResourceUtil.getString(dict.getPropertyMap().get("fdId").getMessageKey()));
		}
		columns.add(property);
		for (SysDictCommonProperty sysDictCommonProperty : propertyList) {
			Map<String, SysDictCommonProperty> propertyMap = dict.getPropertyMap();
			String type = sysDictCommonProperty.getType();
			String rowName = sysDictCommonProperty.getName();
			String column = sysDictCommonProperty.getColumn();
			if (StringUtil.isNull(column)) {
				continue;
			}
			SysDictCommonProperty sysDictCommonPropertyDetail = propertyMap.get(rowName);
			// 跳过集合类型和非显示的属性
			if (!isDisplay(displayFieldList, sysDictCommonProperty)
					|| sysDictCommonPropertyDetail instanceof SysDictListProperty) {
				continue;
			}
			// 如果是model属性且不是非空字段，跳过（防止内连接影响查询数据）
			if (sysDictCommonPropertyDetail instanceof SysDictModelProperty
					&& !sysDictCommonPropertyDetail.isNotNull()) {
				continue;
			}
			int index = -1;
			// 如果指定了展示字段，则根据指定字段的顺序进行展示
			if (null != displayFieldList) {
				index = displayFieldList.indexOf(rowName);
			}
			property = new JSONObject();
			// 如果该属性是model则查询其fdId和fdName
			if (sysDictCommonPropertyDetail instanceof SysDictModelProperty) {
				property = new JSONObject();
				property.put("property", sysDictCommonProperty.getName() + ".fdId");
				property.put("type", type);
				addCloums(columns, property, index);
				property = new JSONObject();
				property.put("title", ResourceUtil.getString(sysDictCommonProperty.getMessageKey()));
				property.put("property", sysDictCommonProperty.getName() + ".fdName");
				property.put("type", type);
				addCloums(columns, property, index);
			} else {
				property.put("title", ResourceUtil.getString(sysDictCommonProperty.getMessageKey()));
				property.put("property", sysDictCommonProperty.getName());
				property.put("type", type);
				addCloums(columns, property, index);
			}
		}
		initHQLInfo(qSearch, columns, hqlInfo, tableName, modelName, order, where);
		Page pageData = service.findPage(hqlInfo);
		page.put("currentPage", pageData.getPageno());
		page.put("pageSize", pageData.getRowsize());
		page.put("totalSize", pageData.getTotalrows());
		List<Object[]> dataList = pageData.getList();
		if (!dataList.isEmpty()) {
			dataProcessing(columns, datas, dataList);
			result.put("datas", datas);
			result.put("columns", columns);
			result.put("page", page);
		}

		request.setAttribute("lui-source", result);
	}

	/**
	 * 根据指定顺序添加字段
	 * 
	 * @param columns
	 * @param property
	 * @param index
	 */
	private void addCloums(JSONArray columns, JSONObject property, int index) {
		if (index != -1) {
			columns.add(index, property);
		} else {
			columns.add(property);
		}
	}

	/**
	 * 处理查询后的数据列
	 * 
	 * @param columns
	 * @param datas
	 * @param dataList
	 */
	private void dataProcessing(JSONArray columns, JSONArray datas, List<Object[]> dataList) {
		JSONArray data;
		// 遍历行数据
		for (int j = 0; j < dataList.size(); j++) {
			data = new JSONArray();
			Object[] colData = dataList.get(j);
			// 遍历列数据
			for (int i = 0; i < colData.length; i++) {
				JSONObject colJson = new JSONObject();
				// 对日期格式数据进行转换
				if ("DateTime".equals(((JSONObject) columns.get(i)).getString("type"))) {
					colJson.put("col", ((JSONObject) columns.get(i)).getString("property"));
					if (null != colData[i]) {
						colJson.put("value", DateUtil.convertDateToString((Date) colData[i], null));
					}
				} else {
					colJson.put("col", ((JSONObject) columns.get(i)).getString("property"));
					colJson.put("value", colData[i]);
				}
				data.add(colJson);
			}
			datas.add(data);
		}
	}

	/**
	 * 判断是否展示该字段
	 * 
	 * @param displayFieldList
	 * @param sysDictCommonProperty
	 * @return
	 */
	private boolean isDisplay(List<String> displayFieldList, SysDictCommonProperty sysDictCommonProperty) {
		if (!"fdId".equals(sysDictCommonProperty.getName())
				&& (null == displayFieldList || displayFieldList.contains(sysDictCommonProperty.getName()))) {
			return true;
		}
		return false;
	}

	/**
	 * 根据fdName搜索数据
	 * 
	 * @param hqlInfo
	 * @param tableName
	 * @param searchText
	 */
	protected void buildSearchHQLInfo(HQLInfo hqlInfo, String tableName, String searchText) {
		if (StringUtil.isNotNull(searchText)) {

			hqlInfo.setWhereBlock(
					StringUtil.linkString(hqlInfo.getWhereBlock(), " and (", tableName + ".fdName like :searchText)"));
			hqlInfo.setParameter("searchText", "%" + searchText + "%");
		}
	}

	/**
	 * 初始化查询hql
	 * 
	 * @param columns   要查询的列
	 * @param hqlInfo
	 * @param tableName
	 * @param modelName
	 */
	protected void initHQLInfo(String qSearch, JSONArray columns, HQLInfo hqlInfo, String tableName, String modelName,
			String order, String tagWhere) {
		StringBuilder selectBlock = new StringBuilder();
		String where = "";
		for (Object column : columns) {
			JSONObject columJson = JSONObject.fromObject(column);
			String rowName = columJson.getString("property");
			selectBlock = selectBlock.append(tableName).append(".").append(rowName).append(",");
			// 如果没有指定排序字段，且有创建时间字段，则根据创建时间降序排序
			if (StringUtil.isNull(order) && "docCreateTime".equals(rowName)) {
				hqlInfo.setOrderBy(tableName + ".docCreateTime desc ");
			}
			if (StringUtil.isNotNull(qSearch) && "fdName".equals(rowName)) {
				if (StringUtil.isNull(where)) {
					where = "fdName like  '%" + qSearch + "%' ";
				} else {
					where += " or fdName like  '%" + qSearch + "%' ";
				}
			}
			if (StringUtil.isNotNull(qSearch) && "docSubject".equals(rowName)) {
				if (StringUtil.isNull(where)) {
					where = "docSubject like  '%" + qSearch + "%' ";
				} else {
					where += " or docSubject like  '%" + qSearch + "%' ";
				}
			}
		}
		if (StringUtil.isNotNull(order)) {
			hqlInfo.setOrderBy(order);
		}
		if (StringUtil.isNotNull(where) && StringUtil.isNotNull(tagWhere)) {
			where += " and " + tagWhere;
		} else if (StringUtil.isNotNull(tagWhere)) {
			where = tagWhere;
		}
		if (StringUtil.isNotNull(where)) {
			hqlInfo.setWhereBlock("(" + where + ")");
		}
		selectBlock.replace(selectBlock.lastIndexOf(","), selectBlock.length(), "");
		hqlInfo.setSelectBlock(selectBlock.toString());
		hqlInfo.setModelName(modelName);
	}

	/**
	 * 获取指定model的service
	 * 
	 * @param request
	 * @return
	 */
	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		return null;
	}
}
