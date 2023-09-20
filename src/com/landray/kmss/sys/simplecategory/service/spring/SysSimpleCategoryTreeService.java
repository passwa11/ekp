package com.landray.kmss.sys.simplecategory.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysAuthConstant.AreaIsolation;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.authorization.util.SysAuthAreaHelper;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.portal.cloud.util.CloudPortalUtil;
import com.landray.kmss.sys.portal.cloud.util.PortletConstants;
import com.landray.kmss.sys.simplecategory.interfaces.SimpleCategoryUtil;
import com.landray.kmss.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.query.Query;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简单类别模块菜单树
 * 
 * 创建日期 2009-07-23
 * 
 * @author 吴兵
 */
@SuppressWarnings("unchecked")
public class SysSimpleCategoryTreeService implements IXMLDataBean {
	private static final int SHOW_ALL = 0;
	private static final int SHOW_EDIT = 1;
	private static final int SHOW_READ = 2;

	/**
	 * 获取辅类别信息和获取辅类别的模板信息 categoryId 父辅类别ID，不传则获取根辅类别的ID authType
	 * 对节点的验证权限,0显示所有(00可以选中所有,01只能选中有维护权限的,02只能选中有使用权限的),1 只显示有维护权限的 2
	 * modelName 简单分类的域模型名 必须 filterUrl 简单分类过滤URL前缀 必须 extendPara 扩展参数
	 */
	@Override
    public List<Map<String, Object>> getDataList(RequestContext xmlContext)
			throws Exception {
		String categoryId = xmlContext.getParameter("categoryId");
		String modelName = xmlContext.getParameter("modelName");
		String authType = xmlContext.getParameter("authType");
		String pAdmin = xmlContext.getParameter("pAdmin");
		String extendService = xmlContext.getParameter("extendService");
		String extProps = xmlContext.getParameter("extProps"); 
		boolean categoryIsNull = StringUtil.isNotNull(xmlContext
				.getParameter("categoryIsNull"));

		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		int showType = getShowType(xmlContext, dict);

		IBaseService service = (IBaseService) SpringBeanUtil.getBean(dict
				.getServiceBean());
		String tableName = ModelUtil.getModelTableName(modelName);
		List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
		List<Object[]> categoriesList = findAll(service, categoryId, modelName,
				tableName, xmlContext);
		// 记录操作日志
		if (UserOperHelper.allowLogOper("sysSimpleCategoryTreeService", null)) {
			UserOperHelper.setModelNameAndModelDesc(modelName,
					ResourceUtil.getString(dict.getMessageKey()));
			for (Object[] categories : categoriesList) {
				UserOperContentHelper.putFind(categories[1].toString(),
						categories[0].toString(),modelName);
			}
			UserOperHelper.setOperSuccess(true);
		}
		List categoryEditList = null;
		List categoryReadList = null;
		switch (showType) {
		case SHOW_READ:
			categoryReadList = findReaderIds(service, categoryId, modelName,
					tableName);
			categoryEditList = findEditorIds(service, categoryId, modelName,
					tableName);
			break;
		case SHOW_EDIT:
			categoryEditList = findEditorIds(service, categoryId, modelName,
					tableName);
			break;
		}
		String baseBeanName = "sysSimpleCategoryTreeService";
		if (StringUtil.isNotNull(extendService)) {
			// 增加简单分类下展开该分类下的文档节点
			baseBeanName += ";" + extendService + "&extendService="
					+ URLEncoder.encode(extendService, "UTF-8");
		}
		if(StringUtil.isNotNull(extProps)) {
			baseBeanName += "&extProps=" + extProps;
		}
		
		ArrayList<String> rtnIds = "1".equals( xmlContext.getParameter("isLoadChildren")) ?
				new ArrayList<String>() : null;
		
		for (int i = 0; i < categoriesList.size(); i++) {
			Object[] info = (Object[]) categoriesList.get(i);
			String id = (String) info[1];
			boolean showValue = true;
			String beanName = baseBeanName;
			if (showType != SHOW_ALL) {
				showValue = "1".equals(pAdmin) || categoryEditList.contains(id);
				if (showValue) {
					beanName += "&pAdmin=1";
				} else {
					if (ISysAuthConstant.IS_AREA_ENABLED && info.length > 2
							&& info[2] != null) {
						String areaHierarchyId = (String) info[2];
						showValue = SimpleCategoryUtil.isAreaAdmin(dict
								.getModelName(), areaHierarchyId);
					} else {
						showValue = SimpleCategoryUtil.isAdmin(dict
								.getModelName());
					}
				}
			} else if ("1".equals(pAdmin)) {
				beanName += "&pAdmin=1";
			}
			if (showType == SHOW_READ && !showValue) {
				showValue = categoryReadList.contains(id);
			}

			HashMap<String, Object> node = new HashMap<String, Object>();
			node.put("text", info[0]);
			beanName += "&authType=" + authType + "&modelName=" + modelName
					+ "&categoryId=" + id;

			if (!showValue) {
				node.put("isShowCheckBox", "0");
				node.put("href", "");
			}
			// 用在elearning中的处理
			if (categoryIsNull) {
				node.put("href", "");
			}
			beanName = beanName + "&s_seq=" + IDGenerator.generateID();
			if (xmlContext.isCloud()) {
				beanName = PortletConstants.TREE_DATA_SOURCE_URL + "?s_bean="
						+ beanName;
				// cloud那边叫url
				node.put("url", CloudPortalUtil.addAppNameInUri(beanName));
			} else {
				node.put("beanName", beanName);
			}
			node.put("value", id);
			node.put("nodeType", "CATEGORY");
			
			rtnList.add(node);
			if(rtnIds != null) {
				rtnIds.add(id);
			}
		}
		if(!ArrayUtil.isEmpty(rtnIds)) {
			this.loadChildren(rtnIds, rtnList, xmlContext,
					showType, service, modelName, tableName);
		}

		return rtnList;
	}
	
	private int getShowType(RequestContext request, SysDictModel dict) {
		if (UserUtil.getKMSSUser().isAdmin()) {
			return SHOW_ALL;
		}
		if (dict.getPropertyMap().get("authAllEditors") == null
				|| dict.getPropertyMap().get("authAllReaders") == null) {
			return SHOW_ALL;
		}
		if ("1".equals(request.getParameter("pAdmin"))) {
			return SHOW_ALL;
		}
		String authType = request.getParameter("authType");
		if (StringUtil.isNull(authType)) {
			return SHOW_ALL;
		}
		if (authType.endsWith("1")) {
			return SHOW_EDIT;
		}
		if (authType.endsWith("2")) {
			return SHOW_READ;
		}
		return SHOW_ALL;
	}

	private List<Object[]> findAll(IBaseService service, String categoryId,
			String modelName, String tableName, RequestContext xmlContext)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		// 过滤场所数据
		if (ISysAuthConstant.IS_AREA_ENABLED
				&& ISysAuthConstant.IS_ISOLATION_ENABLED) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaCheck,
					SysAuthConstant.AreaCheck.YES);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaIsolation,
					ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		}

		StringBuilder selectBlock = new StringBuilder().append(tableName)
				.append(".fdName, ").append(tableName).append(".fdId");

		if (SysAuthAreaUtils.isAreaEnabled(modelName)) {
			selectBlock.append(", ").append(tableName).append(".").append(
					ISysAuthConstant.AREA_FIELD_NAME).append(".").append(
					"fdHierarchyId");
		}

		String langFieldName = SysLangUtil.getLangFieldName(modelName,
				"fdName");
		String selectBlock_lang = "";

		if (StringUtil.isNotNull(langFieldName)) {
			selectBlock_lang = ", " + tableName + "."
					+ langFieldName;
		}
		selectBlock.append(selectBlock_lang);

		hqlInfo.setSelectBlock(selectBlock.toString());
		String keyword = xmlContext.getParameter("keyword");
		if (StringUtil.isNotNull(keyword)) {
			hqlInfo.setWhereBlock(tableName + ".fdName like:searchText");

			hqlInfo.setParameter("searchText", "%" + keyword + "%");
		} else if (StringUtil.isNull(categoryId)) {
			hqlInfo.setWhereBlock(tableName + ".hbmParent is null");
		} else {
			hqlInfo.setWhereBlock(tableName + ".hbmParent.fdId=:categoryId");
			hqlInfo.setParameter("categoryId", categoryId);
		}
		// 移动端收藏夹特殊处理
		//if (StringUtil.isNull(categoryId)) {
			if (StringUtil.isNotNull(modelName) && "com.landray.kmss.sys.bookmark.model.SysBookmarkCategory".equals(
					modelName)) {
				String whereBlock = hqlInfo.getWhereBlock();
				if (StringUtil.isNotNull(whereBlock)) {
					// hqlInfo.setWhereBlock(whereBlock
					// + " and fdCategoryType = 2 and docCreator.fdId = :user");
					//有父级ID的情况下，也得进行类型的过滤，收藏夹分为1和2两种类型，1类型全部可见，2类型创建者可见
					String hql = whereBlock + " and (fdCategoryType = 1 or (fdCategoryType = 2 and docCreator.fdId = :user)) ";
					if(StringUtil.isNull(categoryId) && StringUtil.isNull(keyword)) {//加载第一级并且是非搜索的情况下
						hql = whereBlock + " and (fdCategoryType = 1 or (fdCategoryType = 2 and docCreator.fdId = :user) and "
								+ tableName + ".hbmParent is null)";
					}
					hqlInfo.setParameter("user", UserUtil.getUser().getFdId());
					hqlInfo.setWhereBlock(hql);
				}
			}
		//}

		hqlInfo.setOrderBy(tableName + ".fdOrder, " + tableName + ".fdId");
		String extProps = xmlContext.getParameter("extProps");
		if (StringUtil.isNotNull(extProps) && !"undefined".equals(extProps)) {
			buildHqlByTemplateType(extProps, hqlInfo, tableName);
		}
		
		List<Object[]> list = service.findValue(hqlInfo);
		
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
		
		//#108424 数据存在重复，需要进行过滤
		List<Object[]> resultList = new ArrayList<Object[]>();
		List<String> objectIds = new ArrayList<String>();
		if(list != null)
		{
			for (Object[] o : list) 
			{
				String objectId = (String) o[1];
				if(!objectIds.contains(objectId))
				{
					objectIds.add(objectId);
					resultList.add(o);
				}
			}
		}
		
		return resultList;
	}

	/**
	 * 根据模板类型构建类别查询hql对象
	 *
	 * @param extProps
	 * @param hqlInfo
	 * @param tableName
	 */
	public void buildHqlByTemplateType(String extProps, HQLInfo hqlInfo,
			String tableName) { 
		String[] params = extProps.split(";");
		String __whereBlock = "";
		for (String s : params) {
			String[] val = s.split(":");
			if(val == null || val.length < 2) {
				continue;
			} 
			String param = "kmss_ext_props_" + HQLUtil.getFieldIndex();
			__whereBlock = StringUtil.linkString(__whereBlock, " or ",
					tableName + "." + val[0] + "=:" + param);
			hqlInfo.setParameter(param, val[1]); 
		}
		if (StringUtil.isNotNull(__whereBlock)) {
			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(),
					" and ", "(" + __whereBlock + ")"));
		}
	}

	private List findEditorIds(IBaseService service, String categoryId,
			String modelName, String tableName) throws Exception {
		String hql = "select " + tableName + ".fdId from " + modelName + " " + tableName + " inner join " + tableName
				+ ".authAllEditors editors where ";
		if (StringUtil.isNull(categoryId)) {
			hql += tableName + ".hbmParent is null";
		} else {
			hql += tableName + ".hbmParent.fdId=:categoryId";
		}
		hql += " and editors.fdId in (:orgIds)";
		AreaIsolation isolationType = SysAuthAreaUtils.getAreaIsolation(ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		Query query = SysAuthAreaHelper.queryWithArea(service, hql, modelName, tableName, isolationType);
		if (StringUtil.isNotNull(categoryId)) {
			query.setParameter("categoryId", categoryId);
		}
		query.setParameterList("orgIds", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
		return query.list();
	}

	private List findReaderIds(IBaseService service, String categoryId, String modelName, String tableName)
			throws Exception {
		AreaIsolation isolationType = SysAuthAreaUtils
				.getAreaIsolation(ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		List orgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		Map<String, Object> params = new HashMap<>();

		String hql = "select " + tableName + ".fdId from " + modelName + " " + tableName + " left join " + tableName
				+ ".authAllReaders readers where ";
		if (StringUtil.isNull(categoryId)) {
			hql += tableName + ".hbmParent is null";
		} else {
			hql += tableName + ".hbmParent.fdId=:categoryId";
		}
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			// 如果是外部组织，只能查看有权限的模板
			if (SysOrgEcoUtil.isExternal()) {
				hql += " and readers.fdId in (:orgIds)";
			} else {
				orgIds.add(UserUtil.getEveryoneUser().getFdId());
				hql += " and (readers.fdId is null or readers.fdId in (:orgIds))";
				// 判断是否有"所有人不可使用"属性
				SysDictModel dictModel = SysDataDict.getInstance().getModel(modelName);
				if (dictModel != null) {
					SysDictCommonProperty property = dictModel.getPropertyMap().get("authNotReaderFlag");
					if (property != null) {
						// 排除所有人不可使用
						hql += " and " + tableName + ".authNotReaderFlag = :authNotReaderFlag";
						params.put("authNotReaderFlag", Boolean.FALSE);
					}
				}
			}
		} else {
			hql += " and (" + tableName + ".authReaderFlag = :authReaderFlag or readers.fdId in (:orgIds))";
			params.put("authReaderFlag", Boolean.TRUE);
		}

		Query query = SysAuthAreaHelper.queryWithArea(service, hql, modelName, tableName, isolationType);
		if (StringUtil.isNotNull(categoryId)) {
			query.setParameter("categoryId", categoryId);
		}
		for(String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		query.setParameterList("orgIds", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
		List<String> rtnList = query.list();

		// 针对外部人员的搜索条件
		if (SysOrgEcoUtil.IS_ENABLED_ECO && SysOrgEcoUtil.isExternal()) {
			List<String> ids = new ArrayList<String>();
			if (StringUtil.isNotNull(categoryId)) {
				hql = "select fdHierarchyId from " + modelName + " where fdId = :id";
				query = service.getBaseDao().getHibernateSession().createQuery(hql);
				query.setParameter("id", categoryId);
				List<String> list = query.list();
				if (CollectionUtils.isNotEmpty(list)) {
					String[] split = list.get(0).split(BaseTreeConstant.HIERARCHY_ID_SPLIT);
					for (String temp : split) {
						if (StringUtil.isNotNull(temp)) {
							ids.add(temp);
						}
					}
				}
			}

			// 查询“维护者”权限的分类
			hql = "select " + tableName + ".fdHierarchyId from " + modelName + " " + tableName + " left join "
					+ tableName + ".authAllEditors editors where editors.fdId in (:orgIds) and ";
			if (CollectionUtils.isNotEmpty(ids)) {
				hql += tableName + ".fdId in (:ids)";
			} else {
				hql += tableName + ".hbmParent is null";
			}
			query = SysAuthAreaHelper.queryWithArea(service, hql, modelName, tableName, isolationType);
			query.setParameterList("orgIds", orgIds);
			if (CollectionUtils.isNotEmpty(ids)) {
				query.setParameterList("ids", ids);
			}
			List<String> pids = query.list();
			// 根据有”维护者“权限的父分类，查询所有有权限或everyone的子分类
			if (CollectionUtils.isNotEmpty(pids)) {
				hql = "select " + tableName + ".fdId from " + modelName + " " + tableName + " left join " + tableName
						+ ".authAllReaders readers where readers.fdId in (:orgIds)";
				params = new HashMap<String, Object>();
				if (CollectionUtils.isNotEmpty(pids)) {
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < pids.size(); i++) {
						String pid = pids.get(i);
						if (sb.length() > 0) {
							sb.append(" or ");
						}
						sb.append(tableName).append(".fdHierarchyId like :pid_").append(i);
						params.put("pid_" + i, pid + "%");
					}
					hql += " and (" + sb.toString() + ")";
				}
				List<String> authIds = new ArrayList<String>(orgIds);
				authIds.add(UserUtil.getEveryoneUser().getFdId());
				query = SysAuthAreaHelper.queryWithArea(service, hql, modelName, tableName, isolationType);
				query.setParameterList("orgIds", authIds);
				for (String key : params.keySet()) {
					query.setParameter(key, params.get(key));
				}
				List temp = query.list();
				ArrayUtil.concatTwoList(temp, rtnList);
			}
		}
		return rtnList;
	}

	
	/*********获取子分类****/
	private List findAll(IBaseService service, 
			List<String> parentIds,String modelName,String tableName,
			RequestContext request) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		// 过滤场所数据
		if (ISysAuthConstant.IS_AREA_ENABLED
				&& ISysAuthConstant.IS_ISOLATION_ENABLED) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaCheck,
					SysAuthConstant.AreaCheck.YES);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaIsolation,
					ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		}

		StringBuilder selectBlock = new StringBuilder().append(tableName).append(".fdId, ")
				.append(tableName).append(" .hbmParent.fdId");

		if (SysAuthAreaUtils.isAreaEnabled(modelName)) {
			selectBlock.append(", ").append(tableName).append(".").append(
					ISysAuthConstant.AREA_FIELD_NAME).append(".").append(
					"fdHierarchyId");
		}

		hqlInfo.setSelectBlock(selectBlock.toString());
		if (ArrayUtil.isEmpty(parentIds)) { 
			hqlInfo.setWhereBlock(tableName + ".hbmParent is null");
		} else {
			hqlInfo.setWhereBlock(tableName + ".hbmParent.fdId in(:categoryIds)");
			hqlInfo.setParameter("categoryIds", parentIds);
		}
		hqlInfo.setOrderBy(tableName + ".fdOrder, " + tableName + ".fdId");
		String extProps = request.getParameter("extProps");
		if (StringUtil.isNotNull(extProps) && !"undefined".equals(extProps)) {
			buildHqlByTemplateType(extProps, hqlInfo, tableName);
		}
		return service.findValue(hqlInfo);
		
//		HashMap<String, List<String[]>> rtnMap = new HashMap<String, List<String[]>>();
//		for(Object obj : list) {
//			Object[] info = (Object[])obj;
//			String id = (String)info[0];
//			String parentId = (String)info[1];
//			List<String> tmpChildren = rtnMap.get(parentId);
//			if(tmpChildren == null) {
//				tmpChildren = new ArrayList<String>();
//				rtnMap.put(parentId, tmpChildren);
//			}
//			tmpChildren.add(id);
//		}
//		
//		return rtnMap;
	}
	
	
	private List findEditorIds(IBaseService service, List<String> parentIds,
			String modelName, String tableName) throws Exception {
		String hql = "select " + tableName + ".fdId from " + modelName + " "
				+ tableName + " inner join " + tableName
				+ ".authAllEditors editors where ";
		if (ArrayUtil.isEmpty(parentIds)) {
			hql += tableName + ".hbmParent is null";
		} else {
			hql += tableName + ".hbmParent.fdId in(:categoryIds)";
		}
		hql += " and editors.fdId in (:orgIds)";
		AreaIsolation isolationType = SysAuthAreaUtils
				.getAreaIsolation(ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		Query query = SysAuthAreaHelper.queryWithArea(service, hql, modelName,
				tableName, isolationType);
		if (!ArrayUtil.isEmpty(parentIds)) {
			query.setParameterList("categoryIds", parentIds);
		}
		query.setParameterList("orgIds", UserUtil.getKMSSUser()
				.getUserAuthInfo().getAuthOrgIds());
		return query.list();
	}

	private List findReaderIds(IBaseService service, List<String> parentIds,
			String modelName, String tableName) throws Exception {
		List orgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		AreaIsolation isolationType = SysAuthAreaUtils.getAreaIsolation(ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		// 针对外部人员的搜索条件
		if (SysOrgEcoUtil.IS_ENABLED_ECO && SysOrgEcoUtil.isExternal()) {
			List<String> ids = new ArrayList<String>();
			if (CollectionUtils.isNotEmpty(parentIds)) {
				String hql = "select fdHierarchyId from " + modelName + " where fdId in (:ids)";
				Query query = service.getBaseDao().getHibernateSession().createQuery(hql);
				query.setParameterList("ids", parentIds);
				List<String> list = query.list();
				if (CollectionUtils.isNotEmpty(list)) {
					String[] split = list.get(0).split(BaseTreeConstant.HIERARCHY_ID_SPLIT);
					for (String temp : split) {
						if (StringUtil.isNotNull(temp)) {
							ids.add(temp);
						}
					}
				}
			}

			// 查询“维护者”权限的分类
			String hql = "select " + tableName + ".fdHierarchyId from " + modelName + " " + tableName + " left join "
					+ tableName + ".authAllReaders readers" + " left join " + tableName
					+ ".authAllEditors editors where (editors.fdId in (:orgIds) or readers.fdId in (:orgIds)) and ";
			if (CollectionUtils.isNotEmpty(ids)) {
				hql += tableName + ".fdId in (:ids)";
			} else {
				hql += tableName + ".hbmParent is null";
			}
			Query query = SysAuthAreaHelper.queryWithArea(service, hql, modelName, tableName, isolationType);
			query.setParameterList("orgIds", orgIds);
			if (CollectionUtils.isNotEmpty(ids)) {
				query.setParameterList("ids", ids);
			}
			List<String> pids = query.list();

			// 根据有”维护者“权限的父分类，查询所有有权限或everyone的子分类
			hql = "select " + tableName + ".fdId from " + modelName + " " + tableName + " left join " + tableName
					+ ".authAllReaders readers where readers.fdId in (:orgIds)";
			Map<String, String> params = new HashMap<String, String>();
			if (CollectionUtils.isNotEmpty(pids)) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < pids.size(); i++) {
					String pid = pids.get(i);
					if (sb.length() > 0) {
						sb.append(" or ");
					}
					sb.append(tableName).append(".fdHierarchyId like :pid_").append(i);
					params.put("pid_" + i, pid + "%");
				}
				hql += " and (" + sb.toString() + ")";
			}
			List<String> authIds = new ArrayList<String>(orgIds);
			authIds.add(UserUtil.getEveryoneUser().getFdId());
			query = SysAuthAreaHelper.queryWithArea(service, hql, modelName, tableName, isolationType);
			query.setParameterList("orgIds", authIds);
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
			return query.list();
		} else {
			String hql = "select " + tableName + ".fdId from " + modelName + " " + tableName + " left join " + tableName
					+ ".authAllReaders readers where ";
			if (ArrayUtil.isEmpty(parentIds)) {
				hql += tableName + ".hbmParent is null";
			} else {
				hql += tableName + ".hbmParent.fdId in (:categoryIds)";
			}
			if (SysOrgEcoUtil.IS_ENABLED_ECO) {
				// 如果是外部组织，只能查看有权限的模板
				if (SysOrgEcoUtil.isExternal()) {
					hql += " and readers.fdId in (:orgIds)";
				} else {
					orgIds.add(UserUtil.getEveryoneUser().getFdId());
					hql += " and (readers.fdId is null or readers.fdId in (:orgIds))";
				}
			} else {
				hql += " and (" + tableName + ".authReaderFlag="+ SysOrgHQLUtil.toBooleanValueString(true)+" or readers.fdId in (:orgIds))";
			}

			Query query = SysAuthAreaHelper.queryWithArea(service, hql, modelName, tableName, isolationType);
			if (!ArrayUtil.isEmpty(parentIds)) {
				query.setParameterList("categoryIds", parentIds);
			}
			query.setParameterList("orgIds", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
			return query.list();
		}
	}
	
	
	
	private void loadChildren(List<String> parentIds, List<Map<String, Object>> parentList, 
			RequestContext request,
			int showType, IBaseService service, String modelName, String tableName) throws Exception {
		List categories = findAll(service, parentIds, modelName, tableName, request);
		List categoryEditList = null;
		List categoryReadList = null;
		switch (showType) {
		case SHOW_READ:
			categoryReadList = findReaderIds(service, parentIds, modelName,
					tableName);
			categoryEditList = findEditorIds(service, parentIds, modelName,
					tableName);
			break;
		case SHOW_EDIT:
			categoryEditList = findEditorIds(service, parentIds, modelName,
					tableName);
			break;
		}
		String pAdmin = request.getParameter("pAdmin");
		HashMap<String, Map> nodesMap = new HashMap<String, Map>();
		for(int i = 0; i < parentList.size(); i++) {
			Map m = parentList.get(i);
			nodesMap.put((String)m.get("value"), m);
		}
		for (int i = 0; i < categories.size(); i++) {
			Object[] info = (Object[])categories.get(i);
			String id = (String)info[0];
			boolean showValue = true;
			if (showType != SHOW_ALL) {
				showValue = "1".equals(pAdmin) || categoryEditList.contains(id);
				if (!showValue) {
					if (ISysAuthConstant.IS_AREA_ENABLED && info.length > 2
							&& info[2] != null) {
						String areaHierarchyId = (String) info[2];
						showValue = SimpleCategoryUtil.isAreaAdmin(modelName, areaHierarchyId);
					} else {
						showValue = SimpleCategoryUtil.isAdmin(modelName);
					}
				}
			} 
			if (showType == SHOW_READ && !showValue) {
				showValue = categoryReadList.contains(id);
			}

			HashMap<String, Object> node = new HashMap<String, Object>();
			if (!showValue) {
				node.put("isShowCheckBox", "0");
			}
			node.put("value", id);
			node.put("nodeType", "CATEGORY");
			
			String parentId = (String)info[1];
			Map parentMap = nodesMap.get(parentId);
			if(parentMap != null) {
				Object children = parentMap.get("child");
				if(children == null) {
					children = new ArrayList<Map>();
					parentMap.put("child", children);
				}
				((List)children).add(node);
			}
		}
	}
	
	
	/**获取子分类结束**/
}
