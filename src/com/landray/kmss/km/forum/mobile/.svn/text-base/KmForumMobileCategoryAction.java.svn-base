package com.landray.kmss.km.forum.mobile;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysAuthConstant.AreaIsolation;
import com.landray.kmss.km.forum.model.KmForumCategory;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.authorization.util.SysAuthAreaHelper;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.simplecategory.interfaces.SimpleCategoryUtil;
import com.landray.kmss.sys.simplecategory.mobile.MobileSimpleCategoryAction;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.CacheMode;
import org.hibernate.query.Query;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class KmForumMobileCategoryAction extends MobileSimpleCategoryAction {
	@Override
	public ActionForward cateList(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray array = new JSONArray();
			String modelName = request.getParameter("modelName");
			String parentId = request.getParameter("categoryId");
			String authType = request.getParameter("authType");
			if (StringUtil.isNotNull(modelName)) {
				HQLInfo hqlInfo = new HQLInfo();
				SysDictModel dict = SysDataDict.getInstance().getModel(
						modelName);
				IBaseService service = (IBaseService) SpringBeanUtil
						.getBean(dict.getServiceBean());
				String tableName = ModelUtil.getModelTableName(modelName);
				initHQLInfo(hqlInfo, tableName, modelName);
				buildWhereHQLInfo(hqlInfo, modelName, tableName, authType,
						parentId);
				List<Object[]> list = service.findList(hqlInfo);
				if (list.size() > 0) {
					List<String> cateList = null;
					List<String> hierarchyReaderList = null;
					if (hierarchyReaderList == null){
						hierarchyReaderList = findHierarchyReaderIds(service, modelName,
								tableName);
					}
					if (!UserUtil.getKMSSUser().isAdmin()) {
						cateList =  findReaderIds(service, parentId, modelName,
									tableName);
//						cateList = getCategoryIdsWithChild(service, modelName,
//								tableName, authType);
					}
					for (Object[] cate : list) {
						Boolean __pAdmin = UserUtil.getKMSSUser().isAdmin();
						Boolean disabled = false;
						if(!__pAdmin){
							if (cateList != null && !cateList.contains(cate[1])) {
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
						KmForumCategory kmForumCategory = (KmForumCategory) service.findByPrimaryKey(cate[1].toString());
						JSONObject row = new JSONObject();
						row.put("value", cate[1]);
						row.put("text", kmForumCategory.getFdName());
						String nodeType = "";
						if(cate[3] != null&&!disabled){
							// 查询是否有子，如果有子，需要设置为类别
							HQLInfo hql = new HQLInfo();
							hql.setSelectBlock("count(" + tableName + ".fdId)");
							hql.setJoinBlock("left join " + tableName + ".hbmParent hbmParent");
							hql.setWhereBlock("hbmParent.fdId=:cateId");
							hql.setParameter("cateId", cate[1]);
							List bankuai = service.findList(hql);
							if (Integer.valueOf(bankuai.get(0).toString()) > 0) {
								nodeType = "CATEGORY";
							} else {
								nodeType = "NOCATEGORY";
							}
						}
						row.put("nodeType",nodeType);
						array.element(row);
					}

				}
			}
			request.setAttribute("lui-source", array.toString());
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}
	
	protected List findHierarchyReaderIds(IBaseService service,
			String modelName, String tableName) throws Exception {
		// 通过HQL查询有权限的层级ID
		String hql = "select distinct " + tableName + ".fdHierarchyId from "
				+ modelName + " " + tableName + " left join " + tableName
				+ ".authAllEditors editors";
		hql += " left join " + tableName + ".authAllReaders readers";
		hql += " where (editors.fdId in (:orgIds)";
		List orgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			// 如果是外部组织，只能查看有权限的模板
			if (SysOrgEcoUtil.isExternal()) {
				hql += " or readers.fdId in (:orgIds))";
			} else {
				hql += " or readers.fdId in (:orgIds) or readers.fdId is null)";
				orgIds.add(UserUtil.getEveryoneUser().getFdId());
			}
		} else {
			hql += " or " + tableName + ".authReaderFlag = :authReaderFlag or readers.fdId in (:orgIds))";
		}
		AreaIsolation isolationType = SysAuthAreaUtils
				.getAreaIsolation(ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		Query query = SysAuthAreaHelper.queryWithArea(service, hql, modelName,
				tableName, isolationType);
		query.setCacheable(true);
		query.setCacheMode(CacheMode.NORMAL);
		query.setCacheRegion("km-forum");
		if (!SysOrgEcoUtil.IS_ENABLED_ECO) {
			query.setParameter("authReaderFlag", Boolean.TRUE);
		}
		query.setParameterList("orgIds", orgIds);
		// 查找所有父分类的id
		List<String> cateIds = hierarchyId2Fdid(query.list());
		return cateIds;
	}
	
	protected List<String> hierarchyId2Fdid(List hierarchyIds) {
		List<String> results = new ArrayList<String>();
		for (Object hierarchyId : hierarchyIds) {
			if(StringUtil.isNull((String) hierarchyId)){
				continue;
			}
			String[] ids = ((String) hierarchyId)
					.split(BaseTreeConstant.HIERARCHY_ID_SPLIT);
			for (int i = 1; i < ids.length - 1; i++) {
				if (!results.contains(ids[i])) {
					results.add(ids[i]);
				}
			}
		}
		return results;
	}
	

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
				.append(tableName).append(".fdHierarchyId, ").append("hbmParent.fdId");
		String joinBlock = hqlInfo.getJoinBlock();
		if (StringUtil.isNull(joinBlock) || !joinBlock.contains("hbmParent")) {
			hqlInfo.setJoinBlock(
					StringUtil.linkString(joinBlock, " ", " left join " + tableName + ".hbmParent hbmParent"));
		}
		if (SysAuthAreaUtils.isAreaEnabled(modelName)) {
			selectBlock.append(", ").append(tableName).append(".").append(
					ISysAuthConstant.AREA_FIELD_NAME);
		}
		hqlInfo.setSelectBlock(selectBlock.toString());
		hqlInfo.setOrderBy(tableName + ".fdOrder, " + tableName + ".fdId");
	}

	protected void buildWhereHQLInfo(HQLInfo hqlInfo, String modelName,
			String tableName, String authType, String parentId)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		if (StringUtil.isNull(parentId)) {
			sb.append("hbmParent is null");
		} else {
			sb.append("hbmParent.fdId=:categoryId");
			hqlInfo.setParameter("categoryId", parentId);
		}
		String joinBlock = hqlInfo.getJoinBlock();
		if (StringUtil.isNull(joinBlock) || !joinBlock.contains("hbmParent")) {
			hqlInfo.setJoinBlock(
					StringUtil.linkString(joinBlock, " ", " left join " + tableName + ".hbmParent hbmParent"));
		}
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		hqlInfo.setWhereBlock(sb.toString());
	}

	private boolean isGetAll(String modelName, String authType)
			throws Exception {
		boolean isAll = false;
		if (UserUtil.getKMSSUser().isAdmin()
				|| SimpleCategoryUtil.isAdmin(modelName)) {
			isAll = true;
		} else {
			if("00".equals(authType)) {
				isAll = true;
			}
		}
		return isAll;
	}

	protected List<String> getCategoryIdsWithChild(IBaseService service,
			String modelName, String tableName, String authType)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("distinct hbmParent.fdId");
		hqlInfo
				.setJoinBlock("left join " + tableName + ".authAllReaders authAllReaders left join " + tableName
						+ ".hbmParent hbmParent ");

		StringBuffer sb = new StringBuffer();
		sb.append("hbmParent.fdId is not null");
		if (!isGetAll(modelName, authType)) {
			sb
					.append(" and (("
							+ tableName
							+ ".authReaderFlag =:authReaderFlag) or (authAllReaders.fdId in(:orgs)))");
			hqlInfo.setParameter("authReaderFlag", true);
			hqlInfo.setParameter("orgs", UserUtil.getKMSSUser()
					.getUserAuthInfo().getAuthOrgIds());
		}
		hqlInfo.setWhereBlock(sb.toString());
		return service.findValue(hqlInfo);
	}

	protected List findReaderIds(IBaseService service, String categoryId,
			String modelName, String tableName) throws Exception {
		String hql = "select " + tableName + ".fdId from " + modelName + " "
				+ tableName + " left join " + tableName
				+ ".authAllReaders readers left join " + tableName
				+ ".authReaders visits left join " + tableName + ".hbmParent hbmParent where ";
		if (StringUtil.isNull(categoryId)) {
			hql += "hbmParent is null and";
		} else if (!"__all".equals(categoryId)) {
			hql += "hbmParent.fdId=:categoryId and";
		}
		hql += " ((" + tableName + ".authReaderFlag = :authReaderFlag and ((" + tableName
				+ ".authVisitFlag is null or " + tableName
				+ ".authVisitFlag = :authVisitFlag) or visits.fdId in (:_orgIds))) or readers.fdId in (:orgIds))";
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
		query.setParameter("authReaderFlag", Boolean.TRUE);
		query.setParameter("authVisitFlag", Boolean.TRUE);
		query.setParameterList("_orgIds",
				UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
		query.setParameterList("orgIds", UserUtil.getKMSSUser()
				.getUserAuthInfo().getAuthOrgIds());
		return query.list();
	}
	
}
