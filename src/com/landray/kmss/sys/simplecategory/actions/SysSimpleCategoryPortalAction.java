package com.landray.kmss.sys.simplecategory.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.query.Query;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysAuthConstant.AreaIsolation;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.authorization.util.SysAuthAreaHelper;
import com.landray.kmss.sys.category.interfaces.ConfigUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.simplecategory.interfaces.SimpleCategoryUtil;
import com.landray.kmss.util.ArrayUtil;
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

/***
 * 分类导航抽象类
 */

@SuppressWarnings("unchecked")
public abstract class SysSimpleCategoryPortalAction extends ExtendAction {

	private static final int SHOW_ALL = 0;
	private static final int SHOW_READ = 2;

	protected int getShowType(String modelName, HttpServletRequest request) {
		if (SimpleCategoryUtil.isAdmin(modelName)) {
            return SHOW_ALL;
        }
		return SHOW_READ;
	}

	public ActionForward portal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TimeCounter.logCurrentTime("Action-portal", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {

			String parentId = request.getParameter("parentId");
			String modelName = request.getParameter("modelName");
			if (StringUtil.isNull(modelName)) {
                modelName = getServiceImp(request).getModelName();
            }
			String currId = request.getParameter("currId");
			String __currId = request.getParameter("__currId");
			SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
			IBaseService service = (IBaseService) SpringBeanUtil.getBean(dict
					.getServiceBean());
			String tableName = ModelUtil.getModelTableName(modelName);

			JSONArray array = new JSONArray();

			if (StringUtil.isNull(parentId) && StringUtil.isNotNull(currId)) {
				parentId = currId;
			}

			if (StringUtil.isNull(parentId) && StringUtil.isNotNull(__currId)) {
				Object[] category = findOne(service, tableName, __currId,
						request);
				parentId = (String) category[3];
			}

			List<String> readList = null;
			List<String> hierarchyReaderList = null;
			List<String> allEditorIds = null;

			if ( ConfigUtil.auth(modelName)) {
				hierarchyReaderList = findHierarchyReaderIds(service,
						modelName, tableName);
				switch (getShowType(modelName, request)) {
				case SHOW_READ:
					readList = findReaderIds(service, "__all", modelName,
							tableName);
					break;
				}
				allEditorIds = findAllEditorIds(service, modelName, tableName);
			}

			List parentIds = loadCategoriesByParentId(array, service, parentId,
					modelName, tableName, Boolean.TRUE, request, readList,
					hierarchyReaderList, allEditorIds);
			List __parentIds = loadCategorysByParentIds(array, parentIds,
					modelName, tableName, request, Boolean.TRUE, service,
					LEVEL2, readList, hierarchyReaderList, allEditorIds);
			loadCategorysByParentIds(array, __parentIds, modelName, tableName,
					request, Boolean.TRUE, service, LEVEL3, readList,
					hierarchyReaderList, allEditorIds);
			request.setAttribute("lui-source", array);

		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}

		TimeCounter.logCurrentTime("Action-portal", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	protected List findReaderIds(IBaseService service, String categoryId,
			String modelName, String tableName) throws Exception {
		String hql = "select " + tableName + ".fdId from " + modelName + " "
				+ tableName + " left join " + tableName
				+ ".authAllReaders readers where ";
		if (StringUtil.isNull(categoryId)) {
			hql += tableName + ".hbmParent is null and";
		} else if (!"__all".equals(categoryId)) {
			hql += tableName + ".hbmParent.fdId=:categoryId and";
		}
		List orgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			// 如果是外部组织，只能查看有权限的模板
			if (SysOrgEcoUtil.isExternal()) {
				hql += " (readers.fdId in (:orgIds))";
			} else {
				orgIds.add(UserUtil.getEveryoneUser().getFdId());
				hql += " (readers.fdId is null or readers.fdId in (:orgIds))";
			}
		} else {
			hql += " (" + tableName + ".authReaderFlag= :authReaderFlag or readers.fdId in (:orgIds))";
		}
		AreaIsolation isolationType = SysAuthAreaUtils
				.getAreaIsolation(ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		Query query = SysAuthAreaHelper.queryWithArea(service, hql, modelName,
				tableName, isolationType);
		if (StringUtil.isNotNull(categoryId) && !"__all".equals(categoryId)) {
			query.setParameter("categoryId", categoryId);
		}
		if (!SysOrgEcoUtil.IS_ENABLED_ECO) {
			query.setParameter("authReaderFlag", Boolean.TRUE);
		}
		query.setParameterList("orgIds", orgIds);
		return query.list();
	}

	private List findAllEditorIds(IBaseService service, String modelName,
			String tableName) throws Exception {
		// 通过HQL查询有权限的层级ID
		String hql = "select distinct " + tableName + ".fdId from " + modelName
				+ " " + tableName + " left join " + tableName
				+ ".authAllEditors editors";
		hql += " where editors.fdId in (:orgIds)";
		AreaIsolation isolationType = SysAuthAreaUtils
				.getAreaIsolation(ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		Query query = SysAuthAreaHelper.queryWithArea(service, hql, modelName,
				tableName, isolationType);
		query.setParameterList("orgIds", UserUtil.getKMSSUser()
				.getUserAuthInfo().getAuthOrgIds());
		return query.list();
	}

	protected List findHierarchyReaderIds(IBaseService service,
			String modelName, String tableName) throws Exception {
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
				orgIds.add(UserUtil.getEveryoneUser().getFdId());
				hql += " or readers.fdId is null or readers.fdId in (:orgIds))";
			}
		} else {
			hql += " or " + tableName + ".authReaderFlag= :authReaderFlag or readers.fdId in (:orgIds))";
		}
		AreaIsolation isolationType = SysAuthAreaUtils
				.getAreaIsolation(ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		Query query = SysAuthAreaHelper.queryWithArea(service, hql, modelName,
				tableName, isolationType);
		if (!SysOrgEcoUtil.IS_ENABLED_ECO) {
			query.setParameter("authReaderFlag", Boolean.TRUE);
		}
		query.setParameterList("orgIds", orgIds);
		List<String> cateIds = hierarchyId2Fdid(query.list());
		return cateIds;
	}

	protected List<String> hierarchyId2Fdid(List hierarchyIds) {
		List<String> results = new ArrayList<String>();
		for (Object hierarchyId : hierarchyIds) {
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

	public final static int LEVEL2 = 2;
	public final static int LEVEL3 = 3;

	protected List<String> loadCategorysByParentIds(JSONArray array,
			List<String> parentIds, String modelName, String tableName,
			HttpServletRequest request, Boolean autoFetch,
			IBaseService service, int level, List<String> readList,
			List<String> hierarchyReaderList, List<String> allEditorIds)
			throws Exception {
		List<Object[]> list = (List<Object[]>) findAllByParentIds(service,
				parentIds, modelName, tableName, request);
		List ids = new ArrayList<String>();
		for (Object[] cate : list) {
			if (hasAuth(readList, hierarchyReaderList, allEditorIds, cate[1],
					cate[3])) {
                continue;
            }
			JSONObject row = new JSONObject();
			row.put("text", cate[0]);
			row.put("value", cate[1]);
			row.put("autoFetch", autoFetch);
			row.put("href", formatHref(getHref()));
			row.put("target", getTarget());
			row.put("children", new JSONArray());
			switch (level) {
			case LEVEL2:
				Iterator<JSONObject> it = array.iterator();
				while (it.hasNext()) {
					JSONObject _cate = it.next();
					JSONArray __children = (JSONArray) _cate.get("children");
					if (_cate.getString("value").equals(cate[2])) {
						__children.element(row);
						break;
					}
				}
				break;
			case LEVEL3:
				Iterator<JSONObject> __it = array.iterator();
				while (__it.hasNext()) {
					JSONObject __cate = __it.next();
					JSONArray __children = (JSONArray) __cate.get("children");
					Iterator<JSONObject> ___it = __children.iterator();
					while (___it.hasNext()) {
						JSONObject ___cate = ___it.next();
						JSONArray ___children = (JSONArray) ___cate
								.get("children");
						if (___cate.getString("value").equals(cate[2])) {
							___children.element(row);
							break;
						}
					}
				}
				break;
			}
			ids.add(cate[1]);
		}
		return ids;
	}

	protected String formatHref(String __href) {
		return __href + "#j_path=%2FdocCategory&docCategory=!{value}";
	}

	private Boolean hasAuth(List<String> readList,
			List<String> hierarchyReaderList, List<String> allEditorIds,
			Object id, Object hierarchyId) {
		return readList != null
				&& !readList.contains(id)
				&& hierarchyReaderList != null
				&& !hierarchyReaderList.contains(id)
				&& allEditorIds != null
				&& !ArrayUtil.isArrayIntersect(
						allEditorIds.toArray(),
						hierarchyId.toString().split(
								BaseTreeConstant.HIERARCHY_ID_SPLIT));
	}

	protected List loadCategoriesByParentId(JSONArray array,
			IBaseService service, String categoryId, String modelName,
			String tableName, Boolean autoFetch, HttpServletRequest request,
			List<String> readList, List<String> hierarchyReaderList,
			List<String> allEditorIds) throws Exception {
		List<Object[]> list = (List<Object[]>) findAll(service, categoryId,
				modelName, tableName, request);
		List ids = new ArrayList<String>();
		for (Object[] cate : list) {
			if (hasAuth(readList, hierarchyReaderList, allEditorIds, cate[1],
					cate[2])) {
                continue;
            }
			JSONObject row = new JSONObject();
			row.put("text", cate[0]);
			row.put("value", cate[1]);
			row.put("autoFetch", autoFetch);
			row.put("href", formatHref(getHref()));
			row.put("target", getTarget());
			row.put("children", new JSONArray());
			array.add(row);
			ids.add(cate[1]);
		}
		return ids;
	}

	protected Object[] findOne(IBaseService service, String tableName,
			String categoryId, HttpServletRequest request) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock(tableName + ".fdName, " + tableName + ".fdId, "
				+ tableName + ".fdHierarchyId, " + tableName
				+ ".hbmParent.fdId");
		hqlInfo.setWhereBlock(tableName + ".fdId=:categoryId");
		hqlInfo.setParameter("categoryId", categoryId);
		this.buildValue(request, hqlInfo, tableName);
		return (Object[]) service.findValue(hqlInfo).get(0);
	}

	protected void buildValue(HttpServletRequest request, HQLInfo hqlInfo,
			String tableName) {
	}

	protected List<?> findAllByParentIds(IBaseService service,
			List<String> parentIds, String modelName, String tableName,
			HttpServletRequest request) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		
		String langFieldName = SysLangUtil.getLangFieldName(modelName, "fdName");
		String selectBlock_lang = "";
		if (StringUtil.isNotNull(langFieldName)) {
			selectBlock_lang = ", " + tableName + "." + langFieldName;
		}
		if (ISysAuthConstant.IS_AREA_ENABLED
				&& ISysAuthConstant.IS_ISOLATION_ENABLED) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaCheck,
					SysAuthConstant.AreaCheck.YES);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaIsolation,
					SysAuthConstant.AreaIsolation.BRANCH);
		}
		StringBuilder selectBlock = new StringBuilder().append(tableName)
				.append(".fdName, ").append(tableName).append(".fdId, ")
				.append(tableName).append(".hbmParent.fdId, ")
				.append(tableName).append(".fdHierarchyId");
		
		if (StringUtil.isNotNull(selectBlock_lang)) {
			selectBlock.append(selectBlock_lang);
		}
		
		hqlInfo.setSelectBlock(selectBlock.toString());
		if (parentIds.size() == 0) {
			hqlInfo.setWhereBlock(tableName + ".hbmParent is null");
		} else {
			hqlInfo.setWhereBlock(tableName + ".hbmParent.fdId in (:parentIds)");
			hqlInfo.setParameter("parentIds", parentIds);
		}
		hqlInfo.setOrderBy(tableName + ".fdOrder, " + tableName + ".fdId");
		this.buildValue(request, hqlInfo, tableName);
		List<Object[]> list = (List<Object[]>) service.findValue(hqlInfo);
		if (StringUtil.isNotNull(langFieldName)) {
			replaceFdName(list, 0, 4);
		}
		return list;
	}
	
	private void replaceFdName(List<Object[]> list, int pos1, int pos2) {
		if (list != null) {
			for (Object[] o : list) {
				String text = (String) o[pos2];
				if (StringUtil.isNotNull(text)) {
					o[pos1] = text;
				}
			}
		}
	}

	protected List<?> findAll(IBaseService service, String categoryId,
			String modelName, String tableName, HttpServletRequest request)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		
		String langFieldName = SysLangUtil.getLangFieldName(modelName, "fdName");
		String selectBlock_lang = "";
		if (StringUtil.isNotNull(langFieldName)) {
			selectBlock_lang = ", " + tableName + "." + langFieldName;
		}
		if (ISysAuthConstant.IS_AREA_ENABLED
				&& ISysAuthConstant.IS_ISOLATION_ENABLED) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaCheck,
					SysAuthConstant.AreaCheck.YES);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaIsolation,
					SysAuthConstant.AreaIsolation.BRANCH);
		}
		StringBuilder selectBlock = new StringBuilder().append(tableName)
				.append(".fdName, ").append(tableName).append(".fdId, ")
				.append(tableName).append(".fdHierarchyId");
		;

		if (StringUtil.isNotNull(selectBlock_lang)) {
			selectBlock.append(selectBlock_lang);
		}
		hqlInfo.setSelectBlock(selectBlock.toString());
		if (StringUtil.isNull(categoryId)) {
			hqlInfo.setWhereBlock(tableName + ".hbmParent is null");
		} else {
			hqlInfo.setWhereBlock(tableName + ".hbmParent.fdId=:categoryId");
			hqlInfo.setParameter("categoryId", categoryId);
		}
		hqlInfo.setOrderBy(tableName + ".fdOrder, " + tableName + ".fdId");
		this.buildValue(request, hqlInfo, tableName);
		List<Object[]> list = (List<Object[]>) service.findValue(hqlInfo);
		if (StringUtil.isNotNull(langFieldName)) {
			replaceFdName(list, 0, 3);
		}
		return list;
	}

	public String getTarget() {
		return "_blank";
	}

	public abstract String getHref();

}
