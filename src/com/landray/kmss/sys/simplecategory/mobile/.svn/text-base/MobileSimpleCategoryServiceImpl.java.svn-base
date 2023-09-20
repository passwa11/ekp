package com.landray.kmss.sys.simplecategory.mobile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.simplecategory.service.spring.SysSimpleCategoryTreeService;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class MobileSimpleCategoryServiceImpl implements IMobileSimpleCategoryService {

	@Override
	public List<Map<String, Object>> searchList(RequestContext xmlContext) throws Exception {

		// List<Map<String, Object>> rtnList = new ArrayList<Map<String,
		// Object>>();
		// String modelName = xmlContext.getParameter("modelName");
		// String keyword = xmlContext.getParameter("keyword");
		// SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		//
		// IBaseService service = (IBaseService)
		// SpringBeanUtil.getBean(dict.getServiceBean());
		// String tableName = ModelUtil.getModelTableName(modelName);
		// List categoriesList = findAll(service, keyword, modelName, tableName,
		// xmlContext);
		// for (int i = 0; i < categoriesList.size(); i++) {
		// Object[] info = (Object[]) categoriesList.get(i);
		// String id = (String) info[1];
		// HashMap<String, Object> node = new HashMap<String, Object>();
		// node.put("text", info[0]);
		// node.put("isShowCheckBox", "0");
		// node.put("href", "");
		// node.put("value", id);
		// node.put("nodeType", "CATEGORY");
		//
		// rtnList.add(node);
		// }
		// return rtnList;
		SysSimpleCategoryTreeService sysSimpleCategoryTreeService = (SysSimpleCategoryTreeService) SpringBeanUtil
				.getBean("sysSimpleCategoryTreeService");

		return sysSimpleCategoryTreeService.getDataList(xmlContext);
	}


	private List findAll(IBaseService service, String keyword, String modelName, String tableName,
			RequestContext xmlContext) throws Exception {
		List<Object[]> list = new ArrayList();
		if (StringUtil.isNull(keyword)) {
			return list;
		}
		HQLInfo hqlInfo = new HQLInfo();
		// 过滤场所数据
		if (ISysAuthConstant.IS_AREA_ENABLED && ISysAuthConstant.IS_ISOLATION_ENABLED) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaCheck, SysAuthConstant.AreaCheck.YES);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaIsolation, ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		}

		StringBuilder selectBlock = new StringBuilder().append(tableName).append(".fdName, ").append(tableName)
				.append(".fdId");

		if (SysAuthAreaUtils.isAreaEnabled(modelName)) {
			selectBlock.append(", ").append(tableName).append(".").append(ISysAuthConstant.AREA_FIELD_NAME).append(".")
					.append("fdHierarchyId");
		}

		String langFieldName = SysLangUtil.getLangFieldName(modelName, "fdName");
		String selectBlock_lang = "";

		if (StringUtil.isNotNull(langFieldName)) {
			selectBlock_lang = ", " + tableName + "." + langFieldName;
		}
		selectBlock.append(selectBlock_lang);

		hqlInfo.setSelectBlock(selectBlock.toString());

		hqlInfo.setWhereBlock(tableName + ".fdName like:searchText");

		hqlInfo.setParameter("searchText", "%" + keyword + "%");

		hqlInfo.setOrderBy(tableName + ".fdOrder, " + tableName + ".fdId");
		String extProps = xmlContext.getParameter("extProps");
		if (StringUtil.isNotNull(extProps) && !"undefined".equals(extProps)) {
			buildHqlByTemplateType(extProps, hqlInfo, tableName);
		}
		list = service.findValue(hqlInfo);
		if (list != null && StringUtil.isNotNull(langFieldName)) {
			for (Object[] o : list) {
				int pos = 2;
				if (SysAuthAreaUtils.isAreaEnabled(modelName)) {
					pos++;
				}
				String text = (String) o[pos];
				if (StringUtil.isNotNull(text)) {
					o[0] = text;
				}
			}
		}
		return list;
	}

	/**
	 * 根据模板类型构建类别查询hql对象
	 * 
	 * @param fdTemplateType
	 * @param hqlInfo
	 */
	public void buildHqlByTemplateType(String extProps, HQLInfo hqlInfo, String tableName) {
		String[] params = extProps.split(";");
		String __whereBlock = "";
		for (String s : params) {
			String[] val = s.split(":");
			if (val == null || val.length < 2) {
				continue;
			}
			String param = "kmss_ext_props_" + HQLUtil.getFieldIndex();
			__whereBlock = StringUtil.linkString(__whereBlock, " or ", tableName + "." + val[0] + "=:" + param);
			hqlInfo.setParameter(param, val[1]);
		}
		hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "(" + __whereBlock + ")"));
	}
}
