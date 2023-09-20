package com.landray.kmss.km.forum.actions;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysAuthConstant.AreaIsolation;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.authorization.util.SysAuthAreaHelper;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.simplecategory.actions.SysSimpleCategoryCriteriaAction;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.CacheMode;
import org.hibernate.query.Query;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 论坛筛选数据源
 * 
 * @author tanyouhao
 * @date 2014-6-4
 * 
 */
public class ForumCategoryCriteriaAction extends
		SysSimpleCategoryCriteriaAction {
	private static final int SHOW_ALL = 0;
	private static final int SHOW_EDIT = 1;
	private static final int SHOW_READ = 2;

	@Override
	protected void loadSelectCategories(HttpServletRequest request)
			throws Exception {
		JSONArray array = new JSONArray();
		JSONArray _array = new JSONArray();
		String modelName = request.getParameter("modelName");
		String parentId = request.getParameter("parentId");
		String searchText = request.getParameter("searchText");
		String qSearch = request.getParameter("qSearch");
		HQLInfo hqlInfo = new HQLInfo();
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		IBaseService service = (IBaseService) SpringBeanUtil.getBean(dict
				.getServiceBean());
		String tableName = ModelUtil.getModelTableName(modelName);
		initHQLInfo(hqlInfo, tableName, modelName);
		Boolean isQsearch = "true".equals(qSearch);
		List<String> readList = null;
		List<String> hierarchyReaderList = null;
		if (isQsearch) {
			// 快速搜素不需要判断层级
			hierarchyReaderList = Collections.emptyList();
			readList = findReaderIds(service, "__all", modelName, tableName);
		} else {
			buildParentHQLInfo(hqlInfo, tableName, parentId);
		}
		if (StringUtil.isNotNull(searchText)) {
			buildSearchHQLInfo(hqlInfo, tableName, searchText);
		}
		//if(!UserUtil.getKMSSUser().isAdmin()){
			//buildPosterHql(hqlInfo, tableName);
		//}
	//	buildValue(request, hqlInfo, tableName);
		
		if (readList == null){
			readList = findReaderIds(service, parentId, modelName,
					tableName);
		}
		if (hierarchyReaderList == null){
			hierarchyReaderList = findHierarchyReaderIds(service, modelName,
					tableName);
		}
		List<Object[]> list = service.findList(hqlInfo);
		if (list.size() > 0) {
			String langFieldName = SysLangUtil.getLangFieldName(modelName,
					"fdName");
			if (StringUtil.isNotNull(langFieldName)) {
				for (Object[] cate : list) {
					String value = (String) cate[4];
					if (StringUtil.isNotNull(value)) {
						cate[0] = value;
					}
				}
			}

			for (Object[] cate : list) {
				Boolean __pAdmin = UserUtil.getKMSSUser().isAdmin();
				Boolean disabled = false;
				if(!__pAdmin){
					if (readList != null && !readList.contains(cate[1])) {
						if (hierarchyReaderList.contains(cate[1])) {
                            disabled = true;
                        } else {
                            continue;
                        }
					}else if (cate[3] == null && !hierarchyReaderList.contains(cate[1])) {
						continue;
					}
				}
				if (cate[3] == null) {
					disabled = true;
				}
				String ___pAdmin = "";
				
				JSONObject row = fillToJson(cate[0], cate[1], null,disabled,
						___pAdmin);
				_array.add(row);
			}
			if (_array.size() > 0) {
                array.add(_array);
            }
		}
		
		request.setAttribute("lui-source", array);
	}

	@Override
	protected void initHQLInfo(HQLInfo hqlInfo, String tableName,
							   String modelName) {
		if (ISysAuthConstant.IS_AREA_ENABLED
				&& ISysAuthConstant.IS_ISOLATION_ENABLED) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaCheck,
					SysAuthConstant.AreaCheck.YES);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaIsolation,
					SysAuthConstant.AreaIsolation.BRANCH);
		}
		StringBuilder selectBlock = new StringBuilder().append(tableName)
				.append(".fdName, ").append(tableName).append(".fdId, ")
				.append(tableName).append(".fdHierarchyId,").append("hbmParent.fdId");
		hqlInfo.setJoinBlock(" left join kmForumCategory.hbmParent hbmParent");
		String langFieldName = SysLangUtil.getLangFieldName(modelName,
				"fdName");
		String selectBlock_lang = "";
		if (StringUtil.isNotNull(langFieldName)) {
			selectBlock_lang = ", " + tableName + "."
					+ langFieldName;
			selectBlock.append(selectBlock_lang);
		}

		if (SysAuthAreaUtils.isAreaEnabled(modelName)) {
			selectBlock.append(", ").append(tableName).append(".").append(
					ISysAuthConstant.AREA_FIELD_NAME);
		}
		hqlInfo.setSelectBlock(selectBlock.toString());
		hqlInfo.setOrderBy(tableName + ".fdOrder, " + tableName
				+ ".fdId");
		hqlInfo.setWhereBlock("1=1");
	}

	@Override
	protected void loadParentCategoriesById(HttpServletRequest request)
			throws Exception {
		JSONArray array = new JSONArray();
		String modelName = request.getParameter("modelName");
		String currId = request.getParameter("currId");
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		IBaseService service = (IBaseService) SpringBeanUtil.getBean(dict
				.getServiceBean());
		String tableName = ModelUtil.getModelTableName(modelName);
		Object[] curr = findOne(service, tableName, currId, request, modelName);
		List<Object[]> list = findAllParent(service, tableName, currId, curr[2]
				.toString().split("x"), request);
	/*	List<String> list = getCategoryIdsWithOutChild(service,tableName);*/
		if (list.size() > 0) {
			for (int i = list.size() - 1; i >= 0; i--) {
				Object[] cate = list.get(i);
				Boolean disabled = false;
				if (cate[3] == null) {
					disabled = true;
				}
				JSONObject row = fillToJson(cate[0], cate[1], disabled);
				array.add(row);
			}
		}
		request.setAttribute("lui-source", array);
	}
	
	private void buildPosterHql(HQLInfo hqlInfo,String tableName){
		String whereBlock = hqlInfo.getWhereBlock();
		hqlInfo.setJoinBlock("left join kmForumCategory.authAllReaders authAllReaders");
		KMSSUser user = UserUtil.getKMSSUser();
		List authOrgIds = user.getUserAuthInfo().getAuthOrgIds();
        whereBlock +=" and (("+tableName+".authReaderFlag =:authReaderFlag) or "+"(authAllReaders.fdId in(:orgs)))";
        hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
        hqlInfo.setWhereBlock(whereBlock);
        hqlInfo.setParameter("authReaderFlag", true);
        hqlInfo.setParameter("orgs", authOrgIds);
	}
	
	@Override
	protected List<Object[]> findAllParent(IBaseService service,
										   String tableName, String categoryId, String[] parentIds,
										   HttpServletRequest request) throws Exception {
		if (parentIds == null || parentIds.length == 0) {
			return Collections.emptyList();
		}
		List<String> ids = new ArrayList<String>();
		for (String pid : parentIds) {
			if (categoryId.equals(pid) || StringUtil.isNull(pid)) {
				continue;
			}
			ids.add(pid);
		}
		if (ids.isEmpty()) {
			return Collections.emptyList();
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setJoinBlock(" left join " + tableName + ".hbmParent hbmParent");
		hqlInfo.setSelectBlock(tableName + ".fdName, " + tableName + ".fdId," + tableName + ".fdHierarchyId," + "hbmParent.fdId");
		hqlInfo.setWhereBlock(tableName + ".fdId in (:ids)");
		hqlInfo.setOrderBy(tableName + ".fdHierarchyId asc");
		hqlInfo.setParameter("ids", ids);
		this.buildValue(request, hqlInfo, tableName);
		return service.findValue(hqlInfo);
	}
	
	/**
	 * 返回有下级板块并且下级板块有新建权限的分类
	 * 
	 * @param service
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	protected List<String> getCategoryIdsWithOutChild(IBaseService service,
			String tableName) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("distinct hbmParent.fdId");
		hqlInfo.setJoinBlock("left join " + tableName + ".authAllReaders authAllReaders left join " + tableName
				+ ".hbmParent hbmParent");
		String hql = "hbmParent.fdId is not null";
		KMSSUser user = UserUtil.getKMSSUser();
		List authOrgIds = user.getUserAuthInfo().getAuthOrgIds();
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			// 开启生态组织，需要进行内外隔离
			if (SysOrgEcoUtil.isExternal()) {
				// 外部人员，需要指定可阅读者
				hqlInfo.setWhereBlock(hql + " and authAllReaders.fdId in(:orgs)");
			} else {
				// 内部人员需要增加Everyone
				authOrgIds.add(UserUtil.getEveryoneUser().getFdId());
				hqlInfo.setWhereBlock(hql + " and (authAllReaders.fdId is null or authAllReaders.fdId in(:orgs))");
			}
		} else {
			hqlInfo.setWhereBlock(hql + " and ((" + tableName + ".authReaderFlag =:authReaderFlag) or "
					+ "(authAllReaders.fdId in(:orgs)))");
			hqlInfo.setParameter("authReaderFlag", true);
		}
	    hqlInfo.setParameter("orgs", authOrgIds);
		return service.findValue(hqlInfo);
	}
	
	/**
	 * 常用分类选择的时候显示类别（返回有下级板块并且下级板块有新建权限的分类）
	 * 
	 * @param service
	 * @param tableName
	 * @param categoryId 如果常用分类是第一级则不过滤，直接显示
	 * @return
	 * @throws Exception
	 */
	protected List<Object[]> getCategoryIdsWithOutChildByOften(IBaseService service,
			String tableName,String categoryId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("distinct hbmParent.fdName, hbmParent.fdId");
		hqlInfo.setJoinBlock("left join " + tableName + ".authAllReaders authAllReaders left join " + tableName + ".hbmParent hbmParent");
		KMSSUser user = UserUtil.getKMSSUser();
		List authOrgIds = user.getUserAuthInfo().getAuthOrgIds();
		String whereBlock = "(hbmParent.fdId is not null";
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			// 开启生态组织，需要进行内外隔离
			if (SysOrgEcoUtil.isExternal()) {
				// 外部人员，需要指定可阅读者
				whereBlock += " and authAllReaders.fdId in(:orgs))";
			} else {
				// 内部人员需要增加Everyone
				authOrgIds.add(UserUtil.getEveryoneUser().getFdId());
				whereBlock += " and (authAllReaders.fdId is null or authAllReaders.fdId in(:orgs)))";
			}
		} else {
			whereBlock += " and ((" + tableName + ".authReaderFlag =:authReaderFlag) or authAllReaders.fdId in(:orgs)))";
			hqlInfo.setParameter("authReaderFlag", true);
		}
		
		if(StringUtil.isNotNull(categoryId)){
			whereBlock += " or hbmParent.fdId=:fdId";
			hqlInfo.setParameter("fdId", categoryId);
		}
		hqlInfo.setWhereBlock(whereBlock);
	    hqlInfo.setParameter("orgs", authOrgIds);
		return service.findValue(hqlInfo);
	}
	

	
	private void listToJson(List<Object[]> list, JSONArray array, String[] ids,
							List<String> readList, List<String> hierarchyAuthList,
							List<String> editList, int showType) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			Boolean disabled = Boolean.TRUE;
			Object[] obj = list.get(i);
			JSONObject pObj = new JSONObject();
			pObj.put("text", obj[0].toString());
			pObj.put("value", obj[1]);
			pObj.put("selected", ids[0].equals(obj[1]) ? Boolean.TRUE
					: Boolean.FALSE);
			pObj.put("nodeType", disabled);
			String ___pAdmin = "";
			if (showType == SHOW_ALL || editList.contains(obj[1])) {
                ___pAdmin = "1";
            }
			pObj.put("pAdmin", ___pAdmin);
			array.add(pObj);
		}
	}

	private void listToJson(List<Object[]> list, JSONArray array, String[] ids,
							int index, String currId, List<String> readList,
							List<String> hierarchyAuthList, List<String> editList, int showType)
			throws Exception {
		for (int i = 0; i < list.size(); i++) {
			Boolean disabled = Boolean.FALSE;
			Object[] obj = list.get(i);
			if (readList != null && !readList.contains(obj[1])) {
				if (hierarchyAuthList.contains(obj[1])) {
                    disabled = Boolean.TRUE;
                } else {
                    continue;
                }
			}
			JSONObject pObj = new JSONObject();
			pObj.put("text", obj[0].toString());
			pObj.put("value", obj[1]);
			pObj.put("selected", index + 1 < ids.length ? ids[index + 1]
					.equals(obj[1]) : currId.equals(obj[1]));
			pObj.put("nodeType", disabled);
			String ___pAdmin = "";
			if (showType == SHOW_ALL || editList.contains(obj[1])) {
                ___pAdmin = "1";
            }
			pObj.put("pAdmin", ___pAdmin);
			array.add(pObj);
		}
	}

	@Override
	protected List findReaderIds(IBaseService service, String categoryId,
								 String modelName, String tableName)
			throws Exception {
		String hql = "select " + tableName + ".fdId from " + modelName + " "
				+ tableName + " left join " + tableName
				+ ".authAllReaders readers left join " + tableName
				+ ".authReaders visits left join " + tableName + ".hbmParent hbmParent where ";
		if (StringUtil.isNull(categoryId)) {
			hql += "hbmParent is null and";
		} else if (!"__all".equals(categoryId)) {
			hql += "hbmParent.fdId=:categoryId and";
		}
		List orgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			// 开启生态组织，需要进行内外隔离
			if (SysOrgEcoUtil.isExternal()) {
				// 外部人员，需要指定可阅读者
				hql += " (visits.fdId in (:orgIds) or readers.fdId in (:orgIds))";
			} else {
				// 内部人员需要增加Everyone
				orgIds.add(UserUtil.getEveryoneUser().getFdId());
				hql += " (visits.fdId is null or visits.fdId in (:orgIds) or readers.fdId is null or readers.fdId in (:orgIds))";
			}
		} else {
			hql += " ((" + tableName + ".authReaderFlag = :authReaderFlag and ((" + tableName + ".authVisitFlag is null or " + tableName
					+ ".authVisitFlag = :authVisitFlag) or visits.fdId in (:orgIds))) or readers.fdId in (:orgIds))";
		}
		AreaIsolation isolationType = SysAuthAreaUtils
				.getAreaIsolation(ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		Query query = SysAuthAreaHelper.queryWithArea(service, hql, modelName,
				tableName, isolationType);
		query.setCacheable(true);
		query.setCacheMode(CacheMode.NORMAL);
		query.setCacheRegion("km-forum");
		if (StringUtil.isNotNull(categoryId) && !"__all".equals(categoryId)) {
			query.setParameter("categoryId", categoryId);
		}
		if (!SysOrgEcoUtil.IS_ENABLED_ECO) {
			query.setParameter("authReaderFlag", Boolean.TRUE);
			query.setParameter("authVisitFlag", Boolean.TRUE);
		}
		query.setParameterList("orgIds", orgIds);
		return query.list();
	}
	
}
