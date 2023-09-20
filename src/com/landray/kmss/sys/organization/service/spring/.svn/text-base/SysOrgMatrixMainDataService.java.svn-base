package com.landray.kmss.sys.organization.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.organization.model.SysOrgMatrix;
import com.landray.kmss.sys.organization.model.SysOrgMatrixRelation;
import com.landray.kmss.sys.organization.service.ISysOrgMatrixService;
import com.landray.kmss.sys.xform.maindata.model.SysFormMainDataCustomList;
import com.landray.kmss.sys.xform.maindata.model.SysFormMainDataInsystem;
import com.landray.kmss.sys.xform.maindata.service.ISysFormMainDataCustomListService;
import com.landray.kmss.sys.xform.maindata.service.ISysFormMainDataInsystemService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 主数据服务类
 * 
 * @author 潘永辉 2019年6月19日
 *
 */
public class SysOrgMatrixMainDataService implements IXMLDataBean {

	// 系统内数据
	private ISysFormMainDataInsystemService sysFormMainDataInsystemService;

	// 自定义数据（列表）
	private ISysFormMainDataCustomListService sysFormMainDataCustomListService;
	
	// 矩阵组织
	private ISysOrgMatrixService sysOrgMatrixService;

	public void setSysFormMainDataInsystemService(
			ISysFormMainDataInsystemService sysFormMainDataInsystemService) {
		this.sysFormMainDataInsystemService = sysFormMainDataInsystemService;
	}

	public void setSysFormMainDataCustomListService(
			ISysFormMainDataCustomListService sysFormMainDataCustomListService) {
		this.sysFormMainDataCustomListService = sysFormMainDataCustomListService;
	}

	public void setSysOrgMatrixService(ISysOrgMatrixService sysOrgMatrixService) {
		this.sysOrgMatrixService = sysOrgMatrixService;
	}

	@Override
	public List<Map<String, Object>> getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
		String id = requestInfo.getParameter("id");
		// 自定义数据
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysFormMainDataCustomList.class.getName());
		hqlInfo.setWhereBlock("sysFormMainDataCustom.fdId = :customId");
		hqlInfo.setParameter("customId", id);
		List<SysFormMainDataCustomList> list = sysFormMainDataCustomListService.findValue(hqlInfo);
		if (list != null && !list.isEmpty()) {
			for (SysFormMainDataCustomList customList : list) {
				Map<String, Object> node = new HashMap<String, Object>();
				node.put("text", customList.getFdValueText());
				node.put("value", customList.getFdValue());
				node.put("isAutoFetch", 0);
				rtnList.add(node);
			}
		}
		return rtnList;
	}

	/**
	 * 获取系统主数据列表
	 * 
	 * @param requestInfo
	 * @return
	 * @throws Exception
	 */
	public Page findMainDataInsystem(RequestContext request) throws Exception {
		String s_pageno = request.getParameter("pageno");
		String s_rowsize = request.getParameter("rowsize");
		String matrixId = request.getParameter("fdId");
		String fieldName = request.getParameter("fieldName");

		int pageno = 0;
		int rowsize = SysConfigParameters.getRowSize();
		if (s_pageno != null && s_pageno.length() > 0
				&& Integer.parseInt(s_pageno) > 0) {
			pageno = Integer.parseInt(s_pageno);
		}
		if (s_rowsize != null && s_rowsize.length() > 0
				&& Integer.parseInt(s_rowsize) > 0) {
			rowsize = Integer.parseInt(s_rowsize);
		}
		
		SysFormMainDataInsystem mainData = getSysFormMainDataInsystem(matrixId, fieldName);
		
		// 标题列
		JSONArray subjects = getField(mainData.getFdModelName(), mainData.getFdSelectBlock(), false);
		request.setAttribute("subjects", subjects);
		
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setPageNo(pageno);
		hqlInfo.setRowSize(rowsize);
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_NONE);
		hqlInfo.setModelName(mainData.getFdModelName());
		// 获取数据字典
		Map<String, SysDictCommonProperty> propertyMap = SysDataDict
				.getInstance().getModel(mainData.getFdModelName())
				.getPropertyMap();
		// 搜索条件
		Map<String, String[]> parameterMap = request.getParameterMap();
		parameterMap.remove("fdId");
		HQLHelper.by(parameterMap).buildHQLInfo(hqlInfo, com.landray.kmss.util.ClassUtils.forName(mainData.getFdModelName()));
		Page page = sysFormMainDataInsystemService.getBaseDao().findPage(hqlInfo);
		List<IBaseModel> list = page.getList();
		if (list != null && !list.isEmpty()) {
			List<Object[]> content = new ArrayList<Object[]>();
			for (IBaseModel model : list) {
				Object[] objs = new Object[subjects.size()];
				for (int i = 0; i < subjects.size(); i++) {
					try {
						String propName = subjects.getJSONObject(i).getString("name");
						Object obj = PropertyUtils.getProperty(model, propName);
						// 日期按数据字典格式化
						if (obj instanceof Date) {
							SysDictCommonProperty commonProperty = propertyMap.get(propName);
							String type = commonProperty.getType();
							obj = DateUtil.convertDateToString((Date) obj,
									ResourceUtil.getString("date.format." + type.toLowerCase()));
						}
						objs[i] = obj;
					} catch (Exception e) {
						objs[i] = "";
					}
				}
				content.add(objs);
			}
			page.setList(content);
		}
		return page;
	}
	
	/**
	 * 根据矩阵和字段获取主数据配置
	 * 
	 * @param matrixId
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public SysFormMainDataInsystem getSysFormMainDataInsystem(String matrixId, String fieldName) throws Exception {
		// 获取矩阵
		String mainDataId = null;
		SysOrgMatrix matrix = (SysOrgMatrix) sysOrgMatrixService.findByPrimaryKey(matrixId, null, true);
		for (SysOrgMatrixRelation relation : matrix.getFdRelations()) {
			if (relation.getFdFieldName().equals(fieldName)) {
				mainDataId = relation.getFdType();
				break;
			}
		}
		if (StringUtil.isNull(mainDataId)) {
			throw new RuntimeException(ResourceUtil.getString("sysOrgMatrix.maindata.empty", "sys-organization"));
		}
		SysFormMainDataInsystem mainData = (SysFormMainDataInsystem) sysFormMainDataInsystemService.findByPrimaryKey(mainDataId, null, true);
		if (mainData == null) {
			throw new RuntimeException(ResourceUtil.getString("sysOrgMatrix.maindata.empty", "sys-organization"));
		}
		return mainData;
	}
	
	/**
	 * 获取主数据的搜索项
	 * 
	 * @param matrixId
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public JSONArray getCriteriaByMainData(String matrixId, String fieldName) throws Exception {
		SysFormMainDataInsystem mainData = getSysFormMainDataInsystem(matrixId, fieldName);
		// 获取主数据配置的搜索项
		String fdSearch = mainData.getFdSearch();
		return getField(mainData.getFdModelName(), fdSearch, true);
	}

	private JSONArray getField(String modelName, String str, boolean isCriteria) throws Exception {
		JSONArray array = new JSONArray();
		if (StringUtil.isNotNull(str)) {
			JSONArray searchs = JSONArray.fromObject(str);
			// 是否有标题列
			boolean hasSubject = false;
			// 是否有ID列
			boolean hasId = false;
			for (int i = 0; i < searchs.size(); i++) {
				JSONObject obj = new JSONObject();
				JSONObject search = searchs.getJSONObject(i);
				String field = search.getString("field");
				String fieldType = search.getString("fieldType");
				if ((search.containsKey("isIdProperty") && "true".equals(search.getString("isIdProperty")))
						|| "fdId".equals(field)) {
					hasId = true;
				}

				String type = "criterion.sys.string";
				if (search.containsKey("isSubjectProperty") && "true".equals(search.getString("isSubjectProperty"))) {
					hasSubject = true;
					type = "criterion.sys.docSubject";
					obj.put("isSubject", true);
				} else {
					if ("DateTime".equals(fieldType) || "Date".equals(fieldType)) {
						type = "criterion.sys.calendar";
					} else if ("Integer".equals(fieldType) || "Long".equals(fieldType)) {
						type = "criterion.sys.num";
					}
				}
				obj.put("type", type);
				if (search.containsKey("enumType")) {
					String enumType = search.getString("enumType");
					String enumShowType = search.getString("enumShowType");
					obj.put("enumType", enumType);
					obj.put("enumShowType", enumShowType);
				}
				if (search.containsKey("show")) {
					obj.put("show", search.getString("show"));
				}
				String[] tempField = getField(modelName, field, fieldType);
				if (tempField != null) {
					obj.put("name", tempField[0]);
					obj.put("label", tempField[1]);
					array.add(obj);
				}
			}

			// 以下逻辑只有显示时才会用到
			if (!isCriteria) {
				// 判断是否有标题列
				if (!hasSubject) {
					// 如果没有标题列，需要从数字字典中获取
					SysDictModel dictModel = SysDataDict.getInstance().getModel(modelName);
					if (dictModel != null) {
						String property = dictModel.getDisplayProperty();
						if (StringUtil.isNotNull(property)) {
							hasSubject = true;
							boolean isFind = false;
							for (int i = 0; i < array.size(); i++) {
								JSONObject obj = array.getJSONObject(i);
								if (property.equals(obj.getString("name"))) {
									obj.put("isSubject", true);
									isFind = true;
									break;
								}
							}
							if (!isFind) {
								// 如果主数据没有定义标题列，需要手动加上
								JSONObject obj = new JSONObject();
								obj.put("isSubject", true);
								obj.put("name", property);
								SysDictCommonProperty commonProperty = dictModel.getPropertyMap().get(property);
								obj.put("label", ResourceUtil.getString(commonProperty.getMessageKey()));
								array.add(obj);
							}
						}
					}
				}
				// 判断是否有ID列
				if (!hasId) {
					JSONObject obj = new JSONObject();
					obj.put("name", "fdId");
					array.add(obj);
				}
			}
		}
		return array;
	}
	
	private String[] getField(String modelName, String field, String fieldType) throws Exception {
		StringBuffer fieldLabel = new StringBuffer();
		if (field.contains("|")) {
			String[] tempFields = field.split("[|]");
			String[] tempFieldTypes = fieldType.split("[|]");
			for (int i = 0; i < tempFields.length - 1; i++) {
				if (i == 0) {
					fieldLabel.append(getMessageLabel(modelName, tempFields[0]));
				}
				fieldLabel.append(".").append(getMessageLabel(tempFieldTypes[i], tempFields[i + 1]));
			}
		} else {
			fieldLabel.append(getMessageLabel(modelName, field));
		}
		return new String[] { field.replaceAll("[|]", "."), fieldLabel.toString() };
	}

	private String getMessageLabel(String modelName, String propertyName) throws Exception {
		SysDictModel dictModel = SysDataDict.getInstance().getModel(modelName);
		String messageKey = dictModel.getPropertyMap().get(propertyName).getMessageKey();
		return ResourceUtil.getString(messageKey);
	}

}
