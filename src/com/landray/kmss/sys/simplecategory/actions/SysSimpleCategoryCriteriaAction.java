package com.landray.kmss.sys.simplecategory.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.IBaseTreeModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysAuthConstant.AreaIsolation;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.authorization.util.SysAuthAreaHelper;
import com.landray.kmss.sys.category.interfaces.ConfigUtil;
import com.landray.kmss.sys.category.model.ISysCategoryBaseModel;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.sys.portal.cloud.dto.IconDataVO;
import com.landray.kmss.sys.simplecategory.interfaces.SimpleCategoryUtil;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;
import com.landray.kmss.sys.simplecategory.util.SysSimpleCategoryUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.query.Query;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 简单分类筛选数据源
 * 
 * @author
 */
@SuppressWarnings("unchecked")
public class SysSimpleCategoryCriteriaAction extends ExtendAction {

	private static final int SHOW_ALL = 0;
	private static final int SHOW_EDIT = 1;
	private static final int SHOW_READ = 2;

	public ActionForward criteria(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TimeCounter.logCurrentTime("Action-main", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String parentId = request.getParameter("parentId");
			String modelName = request.getParameter("modelName");
			Boolean pAdmin = "true".equals(request.getParameter("pAdmin"));

			boolean loadAll = "true".equalsIgnoreCase(request.getParameter("_all"));

			SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
			IBaseService service = (IBaseService) SpringBeanUtil.getBean(dict.getServiceBean());
			String tableName = ModelUtil.getModelTableName(modelName);
			List<?> categoriesList = findAll(service, parentId, modelName, tableName, request);
			JSONArray array = new JSONArray();

			List<String> readList = null;
			List<String> editList = null;
			List<String> hierarchyReaderList = null;
			if (!pAdmin && ConfigUtil.auth(modelName)) {
				hierarchyReaderList = findHierarchyReaderIds(service, modelName, tableName);
				switch (getShowType(modelName, request)) {
				case SHOW_READ:
					readList = findReaderIds(service, parentId, modelName, tableName);
					editList = findEditorIds(service, parentId, modelName, tableName);
					break;
				case SHOW_EDIT:
					editList = findEditorIds(service, parentId, modelName, tableName);
					break;
				}
			}
			for (int i = 0; i < categoriesList.size(); i++) {
				Object[] info = (Object[]) categoriesList.get(i);
				if (hasAuth(readList, hierarchyReaderList, info[1])) {
                    continue;
                }
				JSONObject row = fillToJson(info[0], info[1], info[3]);
				row.element("pAdmin", pAdmin || editList != null && editList.contains(info[1]));
				array.add(row);
			}

			if (loadAll) {
				JSONObject result = new JSONObject();
				if (StringUtil.isNotNull(parentId)) {
					Object[] curr = findOne(service, tableName, parentId, request);
					result.put("current", fillToJson(curr[0], curr[1], curr[4]));
					if (curr[2] != null) {
						List<Object[]> parents = findAllParent(service, tableName, parentId,
								curr[2].toString().split(BaseTreeConstant.HIERARCHY_ID_SPLIT), request);
						JSONArray psJson = new JSONArray();
						for (Object[] parentCate : parents) {
							if (hasAuth(readList, hierarchyReaderList, parentCate[1])) {
                                continue;
                            }
							psJson.add(fillToJson(parentCate[0], parentCate[1], parentCate[2]));
						}
						result.put("parents", psJson);
					}
				}
				result.put("datas", array);
				request.setAttribute("lui-source", result);
			} else {
				request.setAttribute("lui-source", array);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-main", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}

	public ActionForward navigation(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String parentId = request.getParameter("parentId");
			String modelName = request.getParameter("modelName");
			IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
			JSONArray array = new JSONArray();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setModelName(modelName);
			hqlInfo.setWhereBlock("fdId = :fdId");
			hqlInfo.setParameter("fdId", parentId);
			IBaseTreeModel model = (IBaseTreeModel)baseDao.findFirstOne(hqlInfo);
			if (model != null) {

				JSONObject json = new JSONObject();
				json.put("text", PropertyUtils.getProperty(model, "fdName").toString());
				json.put("value", model.getFdId());
				json.put("children", new JSONArray());
				array.add(0, json);
				model = model.getFdParent();
			}
			request.setAttribute("lui-source", array);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-main", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		return null;
	}

	protected JSONObject fillToJson(Object text, Object value, String desc) {
		JSONObject row = new JSONObject();
		row.put("value", value);
		row.put("desc", null != desc ? desc : "");
		row.put("text", text.toString());
		return row;
	}

	protected List<Object[]> findAllParent(IBaseService service, String tableName, String categoryId,
			String[] parentIds, HttpServletRequest request) throws Exception {
		return findAllParent(service, tableName, categoryId, parentIds, request, request.getParameter("modelName"));
	}

	protected List<Object[]> findAllParent(IBaseService service, String tableName, String categoryId,
			String[] parentIds, HttpServletRequest request, String modelName) throws Exception {
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
		String langFieldName = SysLangUtil.getLangFieldName(modelName, "fdName");
		String selectBlock_lang = "";

		if (StringUtil.isNotNull(langFieldName)) {
			selectBlock_lang = ", " + tableName + "." + langFieldName;
		}
		HQLInfo hqlInfo = new HQLInfo();
		if (hasDescript(modelName)) {
			hqlInfo.setSelectBlock(
					tableName + ".fdName, " + tableName + ".fdId, " + tableName + ".fdDesc" + selectBlock_lang);
		} else {
			hqlInfo.setSelectBlock(tableName + ".fdName, " + tableName + ".fdId, ''" + selectBlock_lang);
		}
		hqlInfo.setWhereBlock(tableName + ".fdId in (:ids)");
		hqlInfo.setOrderBy(tableName + ".fdHierarchyId asc");
		hqlInfo.setParameter("ids", ids);
		this.buildValue(request, hqlInfo, tableName);
		List<Object[]> list = (List<Object[]>) service.findValue(hqlInfo);
		if (StringUtil.isNotNull(langFieldName)) {
			replaceFdName(list, 0, 3);
		}
		return list;
	}

	protected Object[] findOne(IBaseService service, String tableName, String categoryId, HttpServletRequest request)
			throws Exception {
		return findOne(service, tableName, categoryId, request, request.getParameter("modelName"));
	}

	protected Object[] findOne(IBaseService service, String tableName, String categoryId, HttpServletRequest request,
			String modelName) throws Exception {
		String langFieldName = SysLangUtil.getLangFieldName(modelName, "fdName");
		String selectBlock_lang = "";

		if (StringUtil.isNotNull(langFieldName)) {
			selectBlock_lang = ", " + tableName + "." + langFieldName;
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(service.getModelName());
		hqlInfo.setJoinBlock(" left join " + tableName + ".hbmParent hbmParent");
		if (hasDescript(modelName)) {
			hqlInfo.setSelectBlock(tableName + ".fdName, " + tableName + ".fdId, " + tableName + ".fdHierarchyId, "
					 + "hbmParent.fdId, " + tableName + ".fdDesc" + selectBlock_lang);
		} else {
			hqlInfo.setSelectBlock(tableName + ".fdName, " + tableName + ".fdId, " + tableName + ".fdHierarchyId, "
					 + "hbmParent.fdId, ''" + selectBlock_lang);
		}
		hqlInfo.setWhereBlock(tableName + ".fdId=:categoryId");
		hqlInfo.setParameter("categoryId", categoryId);
		// this.buildValue(request, hqlInfo, tableName);
		List list = service.findValue(hqlInfo);
		if (list != null && !list.isEmpty()) {
			Object[] o = (Object[]) list.get(0);
			if (StringUtil.isNotNull(langFieldName)) {
				String text = (String) o[5];
				if (StringUtil.isNotNull(text)) {
					o[0] = text;
				}
			}
			return o;
		}
		return null;
	}

	protected List<?> findAllByParent(IBaseService service, String categoryId, String modelName, String tableName,
									  HttpServletRequest request) throws Exception {
		String langFieldName = SysLangUtil.getLangFieldName(modelName, "fdName");
		String selectBlock_lang = "";
		String fdHierarchyId = null;
		if (StringUtil.isNotNull(categoryId)) {
			// 如果有父分类，需要查询该分类下所有子分类
			List<Object> list = service.findValue(tableName + ".fdHierarchyId", tableName + ".fdId = '" + categoryId + "'", null);
			if (CollectionUtils.isNotEmpty(list)) {
				fdHierarchyId = (String) list.get(0);
			}
		}

		if (StringUtil.isNotNull(langFieldName)) {
			selectBlock_lang = ", " + tableName + "." + langFieldName;
		}
		HQLInfo hqlInfo = new HQLInfo();

		setAreaIsolation(hqlInfo);
		StringBuilder selectBlock = null;
		if (hasDescript(modelName)) {
			selectBlock = new StringBuilder().append(tableName).append(".fdName, ").append(tableName).append(".fdId, ")
					.append(tableName).append(".fdHierarchyId, ").append(tableName).append(".fdDesc, hbmParent.fdId");
		} else {
			selectBlock = new StringBuilder().append(tableName).append(".fdName, ").append(tableName).append(".fdId, ")
					.append(tableName).append(".fdHierarchyId, '', hbmParent.fdId");
		}
		if (StringUtil.isNotNull(selectBlock_lang)) {
			selectBlock.append(selectBlock_lang);
		}
		hqlInfo.setSelectBlock(selectBlock.toString());
		hqlInfo.setJoinBlock(" left join " + tableName + ".hbmParent hbmParent");
		hqlInfo.setOrderBy(tableName + ".fdOrder, " + tableName + ".fdId");
		if (StringUtil.isNotNull(fdHierarchyId)) {
			// 查询该父分类下的所有子分类，但是不包含父分类自己
			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", tableName + ".fdHierarchyId like :fdHierarchyId and " + tableName + ".fdId != :categoryId"));
			hqlInfo.setParameter("fdHierarchyId", fdHierarchyId + "%");
			hqlInfo.setParameter("categoryId", categoryId);
		}
		this.buildValue(request, hqlInfo, tableName);
		List<Object[]> list = (List<Object[]>) service.findValue(hqlInfo);
		if (StringUtil.isNotNull(langFieldName)) {
			replaceFdName(list, 0, 5);
		}
		return list;
	}

	protected List<?> findAll(IBaseService service, String categoryId, String modelName, String tableName,
			HttpServletRequest request) throws Exception {
		String langFieldName = SysLangUtil.getLangFieldName(modelName, "fdName");
		String selectBlock_lang = "";

		if (StringUtil.isNotNull(langFieldName)) {
			selectBlock_lang = ", " + tableName + "." + langFieldName;
		}
		HQLInfo hqlInfo = new HQLInfo();

		setAreaIsolation(hqlInfo);
		StringBuilder selectBlock = null;
		if (hasDescript(modelName)) {
			selectBlock = new StringBuilder().append(tableName).append(".fdName, ").append(tableName).append(".fdId, ")
					.append(tableName).append(".fdHierarchyId, ").append(tableName).append(".fdDesc");
		} else {
			selectBlock = new StringBuilder().append(tableName).append(".fdName, ").append(tableName).append(".fdId, ")
					.append(tableName).append(".fdHierarchyId, ''");
		}
		if (StringUtil.isNotNull(selectBlock_lang)) {
			selectBlock.append(selectBlock_lang);
		}
		hqlInfo.setSelectBlock(selectBlock.toString());
		hqlInfo.setJoinBlock(" left join " + tableName + ".hbmParent hbmParent");
		if (StringUtil.isNull(categoryId)) {
			hqlInfo.setWhereBlock("hbmParent is null");
		} else {
			hqlInfo.setWhereBlock("hbmParent.fdId=:categoryId");
			hqlInfo.setParameter("categoryId", categoryId);
		}
		hqlInfo.setOrderBy(tableName + ".fdOrder, " + tableName + ".fdId");
		this.buildValue(request, hqlInfo, tableName);
		List<Object[]> list = (List<Object[]>) service.findValue(hqlInfo);
		if (StringUtil.isNotNull(langFieldName)) {
			replaceFdName(list, 0, 4);
		}
		return list;
	}

	protected List loadCategoriesByParentId(JSONArray array, IBaseService service, String categoryId, String modelName,
			String tableName, Boolean autoFetch, RequestContext request)
			throws Exception {
		return loadCategoriesByParentId(array, service, categoryId, modelName, tableName, autoFetch, request, null,
				null, null);
	}

	/**
	 * 根据当前分类ID获取所有子分类及子分类的子分类...
	 * @param array
	 * @param service
	 * @param categoryId
	 * @param modelName
	 * @param tableName
	 * @param autoFetch
	 * @param request
	 * @param readList
	 * @param hierarchyReaderList
	 * @param allEditorIds
	 * @return
	 * @throws Exception
	 */
	protected List loadNewCategoriesByParentId(JSONArray array, IBaseService service, String categoryId, String modelName,
											String tableName, Boolean autoFetch, RequestContext request,
											List<String> readList,
											List<String> hierarchyReaderList, List<String> allEditorIds) throws Exception {
		long start = System.currentTimeMillis();
		List<Object[]> list = null;
		if (StringUtil.isNotNull(categoryId)) {
			list = (List<Object[]>) findAllByParent(service, categoryId, modelName, tableName, request.getRequest());
		} else {
			list = (List<Object[]>) findAll(service, modelName, tableName, request.getRequest());
		}
		if (logger.isDebugEnabled()) {
			logger.debug("查询分类数据耗时：{}毫秒，数量：{}", (System.currentTimeMillis() - start), list.size());
			start = System.currentTimeMillis();
		}
		// 权限过滤
		List<Object[]> myList = new ArrayList<>();
		for (Object[] cate : list) {
			if (hasAuth(readList, hierarchyReaderList, allEditorIds, cate[1], cate[2])) {
				continue;
			}
			myList.add(cate);
		}
		String url = request.getParameter("url");
		boolean isCloud = request.isCloud();
		// 构建数据
		List<String> ids = new ArrayList<String>();
		array.addAll(SysSimpleCategoryUtil.buildCategoryTree(myList, url, autoFetch, isCloud));
		for (int i = 0; i < array.size(); i++) {
			ids.add(array.getJSONObject(i).getString("value"));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("处理分类数据耗时：{}毫秒，处理后数量：{}", (System.currentTimeMillis() - start), ids.size());
		}
		return ids;
	}

	protected List loadCategoriesByParentId(JSONArray array, IBaseService service, String categoryId, String modelName,
			String tableName, Boolean autoFetch, RequestContext request,
			List<String> readList,
			List<String> hierarchyReaderList, List<String> allEditorIds) throws Exception {
		List<Object[]> list = (List<Object[]>) findAll(service, categoryId,
				modelName, tableName, request.getRequest());
		List ids = new ArrayList<String>();
		for (Object[] cate : list) {
			if (hasAuth(readList, hierarchyReaderList, allEditorIds, cate[1], cate[2])) {
				continue;
			}
			JSONObject row = new JSONObject();
			row.put("text", cate[0].toString());
			row.put("value", cate[1]);
			row.put("autoFetch", autoFetch);
			if (request.isCloud()) {
				String url = request.getParameter("url");
				if (StringUtil.isNotNull(url)) {
					row.put("href", url
							+ "#j_path=%2FdocCategory&docCategory=" + cate[1]);
				}
				row.put("target", "_blank");
				IconDataVO icon = new IconDataVO();
				icon.setName("category");
				row.put("icon", icon);
			}
			row.put("children", new JSONArray());
			array.add(row);
			ids.add(cate[1]);
		}
		return ids;
	}

	public ActionForward currentCate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-currentCate", true, getClass());
		KmssMessages messages = new KmssMessages();
		String currId = request.getParameter("currId");
		String modelName = request.getParameter("modelName");
		JSONArray array = new JSONArray();
		if (StringUtil.isNotNull(currId)) {
			SysDictModel cateModel = SysDataDict.getInstance().getModel(modelName);
			IBaseService service = (IBaseService) getBean(cateModel.getServiceBean());
			String tableName = ModelUtil.getModelTableName(modelName);
			Object[] cate = findOne(service, tableName, currId, request);
			JSONObject json = new JSONObject();
			if (cate != null) {
				json.put("text", cate[0].toString());
				json.put("value", cate[1]);
				json.put("hid", cate[2]);
			}
			array.element(json);

			request.setAttribute("lui-source", array);
		}
		TimeCounter.logCurrentTime("Action-currentCate", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}

	private Boolean hasAuth(List<String> readList, List<String> hierarchyReaderList, List<String> allEditorIds,
			Object id, Object hierarchyId) {
		return hasAuth(readList, hierarchyReaderList, id) && allEditorIds != null
				&& !ArrayUtil.isArrayIntersect(allEditorIds.toArray(),
						hierarchyId.toString().split(BaseTreeConstant.HIERARCHY_ID_SPLIT));
	}

	private Boolean hasAuth(List<String> readList, List<String> hierarchyReaderList, Object id) {
		return readList != null && !readList.contains(id) && hierarchyReaderList != null
				&& !hierarchyReaderList.contains(id);
	}

	public final static int LEVEL2 = 2;
	public final static int LEVEL3 = 3;
	public final static int LEVEL4 = 4;

	/**
	 * 分类面板数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	JSONArray getFlatData(RequestContext request) throws Exception {
		String parentId = request.getParameter("parentId");
		String modelName = request.getParameter("modelName");
		String currId = request.getParameter("currId");
		String expandStr = request.getParameter("expand");

		// 一次请求多少级数据
		//String level = request.getParameter("level");
//		int lev = LEVEL3;
//		if (StringUtil.isNotNull(level)) {
//			lev = Integer.valueOf(level);
//		}

		boolean expand = StringUtil.isNotNull(parentId)
				&& "true".equals(expandStr);
		String __currId = request.getParameter("__currId");
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		IBaseService service = (IBaseService) SpringBeanUtil
				.getBean(dict.getServiceBean());
		String tableName = ModelUtil.getModelTableName(modelName);
		if (StringUtil.isNull(parentId) && StringUtil.isNotNull(currId)) {
			parentId = currId;
		}
		if (StringUtil.isNull(parentId) && StringUtil.isNotNull(__currId)) {
			Object[] category = findOne(service, tableName, __currId,
					request.getRequest());
			parentId = (String) category[3];
		}
		List<String> readList = null;
		List<String> hierarchyReaderList = null;
		List<String> allEditorIds = null;
		if (ConfigUtil.auth(modelName)) {
			hierarchyReaderList = findHierarchyReaderIds(service, modelName,
					tableName);
			switch (getShowType(modelName, request.getRequest())) {
				case SHOW_READ:
					readList = findReaderIds(service, "__all", modelName,
							tableName);
					break;
			}
			allEditorIds = findAllEditorIds(service, modelName, tableName);
		}
		JSONArray array = new JSONArray();
		//第一次进入 获取该模块的所有第一级分类ID
//		List parentIds = loadCategoriesByParentId(array, service, parentId,
//				modelName, tableName, Boolean.TRUE,
//				request, readList, hierarchyReaderList, allEditorIds);
		if (StringUtil.isNull(__currId) || expand) {
			// #160296 改造此段逻辑不限制分类层级 通过递归获取子分类构造JSON
			loadNewCategoriesByParentId(array, service, parentId, modelName, tableName, Boolean.TRUE, request, readList,
						hierarchyReaderList, allEditorIds);
//			if (lev >= LEVEL2) {
//				List __parentIds = loadCategorysByParentIds(array, parentIds,
//						modelName, tableName, request,
//						Boolean.TRUE, service, LEVEL2, readList,
//						hierarchyReaderList, allEditorIds);
//				if (lev >= LEVEL3) {
//					List ___parentIds = loadCategorysByParentIds(array,
//							__parentIds, modelName, tableName, request,
//							Boolean.TRUE, service, LEVEL3, readList,
//							hierarchyReaderList, allEditorIds);
//					if (lev >= LEVEL4) {
//						loadCategorysByParentIds(array, ___parentIds, modelName,
//								tableName, request, Boolean.TRUE,
//								service, LEVEL4, readList, hierarchyReaderList,
//								allEditorIds);
//					}
//				}
//			}
		}else{
			List parentIds = loadCategoriesByParentId(array, service, parentId,
					modelName, tableName, Boolean.TRUE,
					request, readList, hierarchyReaderList, allEditorIds);
		}
		return array;
	}

	// -------- 分类检索 ------------
	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TimeCounter.logCurrentTime("Action-index", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			RequestContext requestCtx = new RequestContext(request);
			JSONArray array = getFlatData(requestCtx);
			request.setAttribute("lui-source", array);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}

		TimeCounter.logCurrentTime("Action-index", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}

	protected List<String> loadCategorysByParentIds(JSONArray array, List<String> parentIds, String modelName,
			String tableName, RequestContext request, Boolean autoFetch,
			IBaseService service, int level,
			List<String> readList, List<String> hierarchyReaderList, List<String> allEditorIds) throws Exception {
		List<Object[]> list = (List<Object[]>) findAllByParentIds(service,
				parentIds, modelName, tableName, request.getRequest());
		List ids = new ArrayList<String>();
		for (Object[] cate : list) {
			if (hasAuth(readList, hierarchyReaderList, allEditorIds, cate[1], cate[3])) {
                continue;
            }
			JSONObject row = new JSONObject();
			row.put("text", cate[0].toString());
			row.put("value", cate[1]);
			row.put("autoFetch", autoFetch);
			if (request.isCloud()) {
				String url = request.getParameter("url");
				if (StringUtil.isNotNull(url)) {
					row.put("href", url
							+ "#j_path=%2FdocCategory&docCategory=" + cate[1]);
				}
				row.put("target", "_blank");
				IconDataVO icon = new IconDataVO();
				icon.setName("category");
				row.put("icon", icon);
			}
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
						JSONArray ___children = (JSONArray) ___cate.get("children");
						if (___cate.getString("value").equals(cate[2])) {
							___children.element(row);
							break;
						}
					}
				}
				break;

			case LEVEL4:
				Iterator<JSONObject> ___it = array.iterator();
				while (___it.hasNext()) {
					JSONObject __cate = ___it.next();
					JSONArray __children = (JSONArray) __cate.get("children");
					Iterator<JSONObject> ____it = __children.iterator();
					while (____it.hasNext()) {
						JSONObject ___cate = ____it.next();
						JSONArray ___children = (JSONArray) ___cate.get("children");
						Iterator<JSONObject> _____it = ___children.iterator();
						while (_____it.hasNext()) {
							JSONObject ____cate = _____it.next();
							JSONArray ____children = (JSONArray) ____cate.get("children");
							if (____cate.getString("value").equals(cate[2])) {
								____children.element(row);
								break;
							}
						}
					}
				}
				break;
			}
			ids.add(cate[1]);
		}

		return ids;
	}

	/**
	 * 分类层级数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	JSONArray getLevelData(RequestContext request) throws Exception {
		String parentId = request.getParameter("parentId");
		String modelName = request.getParameter("modelName");
		String currId = request.getParameter("currId");
		String expandStr = request.getParameter("expand");
		boolean expand = StringUtil.isNotNull(parentId)
				&& "true".equals(expandStr);

		String __currId = request.getParameter("__currId");
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		IBaseService service = (IBaseService) SpringBeanUtil
				.getBean(dict.getServiceBean());
		String tableName = ModelUtil.getModelTableName(modelName);

		JSONArray array = new JSONArray();

		if (StringUtil.isNull(parentId) && StringUtil.isNotNull(currId)) {
			parentId = currId;
		}

		if (StringUtil.isNull(parentId) && StringUtil.isNotNull(__currId)) {
			Object[] category = findOne(service, tableName, __currId,
					request.getRequest());
			parentId = (String) category[3];
		}

		loadCategoriesByParentId(array, service, parentId, modelName,
				tableName, Boolean.TRUE, request);

		if (expand) {
			for (Iterator<?> it = array.iterator(); it.hasNext();) {
				JSONObject cate = (JSONObject) it.next();
				String cateId = cate.getString("value");
				JSONArray children = new JSONArray();
				loadCategoriesByParentId(children, service, cateId,
						modelName, tableName, Boolean.FALSE, request);
				cate.put("children", children);
			}
		}
		return array;
	}

	public ActionForward index2(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TimeCounter.logCurrentTime("Action-index", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			RequestContext requestCtx = new RequestContext(request);
			JSONArray array = getLevelData(requestCtx);
			request.setAttribute("lui-source", array);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}

		TimeCounter.logCurrentTime("Action-index", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}

	protected List<?> findAllByParentIds(IBaseService service, List<String> parentIds, String modelName,
			String tableName, HttpServletRequest request) throws Exception {
		String langFieldName = SysLangUtil.getLangFieldName(modelName, "fdName");
		String selectBlock_lang = "";

		if (StringUtil.isNotNull(langFieldName)) {
			selectBlock_lang = ", " + tableName + "." + langFieldName;
		}
		HQLInfo hqlInfo = new HQLInfo();

		setAreaIsolation(hqlInfo);
		StringBuilder selectBlock = new StringBuilder().append(tableName).append(".fdName, ").append(tableName)
				.append(".fdId, ").append("hbmParent.fdId, ").append(tableName)
				.append(".fdHierarchyId");
		if (StringUtil.isNotNull(langFieldName)) {
			selectBlock.append(selectBlock_lang);
		}
		hqlInfo.setSelectBlock(selectBlock.toString());
		hqlInfo.setJoinBlock(" left join " + tableName + ".hbmParent hbmParent");
		if (parentIds.size() == 0) {
			hqlInfo.setWhereBlock("hbmParent is null");
		} else {
			hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("hbmParent", parentIds));
		}
		hqlInfo.setOrderBy(tableName + ".fdOrder, " + tableName + ".fdId");
		this.buildValue(request, hqlInfo, tableName);
		List<Object[]> list = (List<Object[]>) service.findValue(hqlInfo);
		if (StringUtil.isNotNull(langFieldName)) {
			replaceFdName(list, 0, 4);
		}
		return list;
	}

	// -------- 路径导航 ------------

	public ActionForward path(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TimeCounter.logCurrentTime("Action-path", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			RequestContext requestCtx = new RequestContext(request);
			String currId = request.getParameter("currId");
			String modelName = request.getParameter("modelName");
			Class model = ClassUtils.forName(modelName);
			if (model.newInstance() instanceof ISysCategoryBaseModel) {
				if (StringUtil.isNotNull(currId)) {
					loadSiblingCategories(requestCtx);
				} else {
					loadHierarchyCategories(request);
				}
			} else {
				request.setAttribute("lui-source", new JSONArray());
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}

		TimeCounter.logCurrentTime("Action-path", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}

	protected void loadSiblingCategories(RequestContext request)
			throws Exception {
		String modelName = request.getParameter("modelName");
		String currId = request.getParameter("currId");

		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		IBaseService service = (IBaseService) SpringBeanUtil.getBean(dict.getServiceBean());
		String tableName = ModelUtil.getModelTableName(modelName);

		JSONArray array = new JSONArray();
		Object[] category = findOne(service, tableName, currId,
				request.getRequest());
		String parentId = (String) category[3];

		List<String> readList = null;
		List<String> hierarchyReaderList = null;
		List<String> allEditorIds = null;

		if (ConfigUtil.auth(modelName)) {
			hierarchyReaderList = findHierarchyReaderIds(service, modelName, tableName);
			switch (getShowType(modelName, request.getRequest())) {
			case SHOW_READ:
				readList = findReaderIds(service, "__all", modelName, tableName);
				break;
			}
			allEditorIds = findAllEditorIds(service, modelName, tableName);
		}
		//如果carrId不为空使用新的接口
		if(StringUtil.isNotNull(currId)){
			loadNewCategoriesByParentId(array, service, parentId, modelName, tableName, Boolean.FALSE, request, readList,
					hierarchyReaderList, allEditorIds);
		}else {
			loadCategoriesByParentId(array, service, parentId, modelName, tableName, Boolean.FALSE, request, readList,
					hierarchyReaderList, allEditorIds);
		}
		//在面包屑中本分类也要显示
		//	for (Iterator<?> it = array.iterator(); it.hasNext();) {
		//		JSONObject row = (JSONObject) it.next();
		//		if (currId.equals(row.getString("value"))) {
		//			it.remove();
		//			break;
		//		}
		//	}

		request.setAttribute("lui-source", array);
	}

	protected void loadHierarchyCategories(HttpServletRequest request) throws Exception {
		String modelName = request.getParameter("modelName");
		String currId = request.getParameter("categoryId");
		JSONArray array = new JSONArray();
		if (StringUtil.isNotNull(currId)) {
			SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
			IBaseService service = (IBaseService) SpringBeanUtil.getBean(dict.getServiceBean());
			String tableName = ModelUtil.getModelTableName(modelName);

			Object[] category = findOne(service, tableName, currId, request);
			JSONObject currObj = new JSONObject();
			if(category != null){
				currObj.put("text", category[0].toString());
				currObj.put("value", category[1]);
				String[] categoryHierarchyIds = category[2].toString().split(BaseTreeConstant.HIERARCHY_ID_SPLIT);

				List<Object[]> parents = findAllParent(service, tableName, currId, categoryHierarchyIds, request);
				for (Object[] parent : parents) {
					JSONObject pObj = new JSONObject();
					pObj.put("text", parent[0].toString());
					pObj.put("value", parent[1]);
					array.add(pObj);
				}
			}

			if (!currObj.isEmpty()) {
                array.add(currObj);
            }
		}
		request.setAttribute("lui-source", array);
	}

	protected void loadSelectCategories(HttpServletRequest request) throws Exception {
		JSONArray array = new JSONArray();
		JSONArray _array = new JSONArray();
		String modelName = request.getParameter("modelName");
		String parentId = request.getParameter("parentId");
		String searchText = request.getParameter("searchText");
		String qSearch = request.getParameter("qSearch");
		String pAdmin = request.getParameter("pAdmin");
		HQLInfo hqlInfo = new HQLInfo();
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		IBaseService service = (IBaseService) SpringBeanUtil.getBean(dict.getServiceBean());
		String tableName = ModelUtil.getModelTableName(modelName);
		initHQLInfo(hqlInfo, tableName, modelName);
		List<String> hierarchyReaderList = null;
		List<String> readList = null;
		List<String> editList = null;
		List __allEditorIds = null;
		int showType = getShowType(modelName, request);
		Boolean isQsearch = "true".equals(qSearch);
		if (isQsearch) {
			// 快速搜素不需要判断层级
			hierarchyReaderList = Collections.emptyList();
			switch (showType) {
			case SHOW_READ:
				readList = findReaderIds(service, "__all", modelName, tableName);
				editList = findEditorIds(service, "__all", modelName, tableName);
				break;
			case SHOW_EDIT:
				editList = findEditorIds(service, "__all", modelName, tableName);
				break;
			}
		} else {
			buildParentHQLInfo(hqlInfo, tableName, parentId);
		}
		if (StringUtil.isNotNull(searchText)) {
			buildSearchHQLInfo(hqlInfo, tableName, searchText, modelName);
		}
		buildValue(request, hqlInfo, tableName);
		setAreaIsolation(hqlInfo);

		List<Object[]> list = service.findValue(hqlInfo);
		if (readList == null) {
            switch (showType) {
            case SHOW_READ:
                readList = findReaderIds(service, parentId, modelName, tableName);
                editList = findEditorIds(service, parentId, modelName, tableName);
                break;
            case SHOW_EDIT:
                editList = findEditorIds(service, parentId, modelName, tableName);
                break;
            }
        }
		// 如果是外部组织，访问权限应该是Readers+Editors
		if (SysOrgEcoUtil.IS_ENABLED_ECO && SysOrgEcoUtil.isExternal()) {
			ArrayUtil.concatTwoList(editList, readList);
		}
		if (hierarchyReaderList == null && showType != SHOW_ALL) {
			hierarchyReaderList = findHierarchyReaderIdsByParent(service, modelName,
					tableName, parentId);
		}
		if (list.size() > 0) {
			if (isQsearch) {
                __allEditorIds = findAllEditorIds(service, modelName, tableName);
            }
			for (Object[] cate : list) {
				Boolean __pAdmin = false;
				if (isQsearch) {
					if (ArrayUtil.isArrayIntersect(cate[2].toString().split(BaseTreeConstant.HIERARCHY_ID_SPLIT),
							__allEditorIds.toArray())) {
                        __pAdmin = true;
                    }
				}
				Boolean disabled = false;
				if (readList != null && !readList.contains(cate[1]) && !__pAdmin) {
					if (hierarchyReaderList.contains(cate[1])) {
                        disabled = true;
                    } else {
                        continue;
                    }
				}
				String ___pAdmin = "";
				if (showType == SHOW_ALL || "1".equals(pAdmin) || editList.contains(cate[1])) {
                    ___pAdmin = "1";
                }

				String langFieldName = SysLangUtil.getLangFieldName(modelName, "fdName");
				if (StringUtil.isNotNull(langFieldName)) {
					int pos = 4;
					if (SysAuthAreaUtils.isAreaEnabled(modelName)) {
						pos++;
					}
					String text = (String) cate[pos];
					if (StringUtil.isNotNull(text)) {
						cate[0] = text;
					}
				}

				JSONObject row = fillToJson(cate[0], cate[1], (String) cate[3], disabled, ___pAdmin);
				_array.add(row);
			}
			if (_array.size() > 0) {
                array.add(_array);
            }
		}
		request.setAttribute("lui-source", array);
	}

	protected void loadSelectCategoryiesById(HttpServletRequest request) throws Exception {
		String modelName = request.getParameter("modelName");
		String currId = request.getParameter("currId");
		// String pAdmin = request.getParameter("pAdmin");
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		IBaseService service = (IBaseService) SpringBeanUtil.getBean(dict.getServiceBean());
		String tableName = ModelUtil.getModelTableName(modelName);
		Object[] curr = findOne(service, tableName, currId, request);
		JSONArray array = new JSONArray();
		JSONArray _array = new JSONArray();
		// 一级简单分类
		List<Object[]> list = (List<Object[]>) findAll(service, null, modelName, tableName, request);
		String[] ids = null;
		if (curr != null && curr[2] != null && StringUtil.isNotNull(curr[2].toString())) {
			ids = curr[2].toString().substring(1).split(BaseTreeConstant.HIERARCHY_ID_SPLIT);
		}
		int showType = getShowType(modelName, request);
		List<String> readList = null;
		List<String> editList = null;
		switch (showType) {
		case SHOW_READ:
			readList = findReaderIds(service, null, modelName, tableName);
			editList = findEditorIds(service, null, modelName, tableName);
			break;
		case SHOW_EDIT:
			editList = findEditorIds(service, null, modelName, tableName);
			break;
		}
		List<String> hierarchyAuthList = findHierarchyReaderIds(service, modelName, tableName);
		listToJson(list, _array, ids, readList, hierarchyAuthList, editList, showType);
		array.add(_array);

		if (ids != null) {
			for (int j = 0; j < ids.length; j++) {
				JSONArray __array = new JSONArray();
				List<Object[]> _list = (List<Object[]>) findAll(service, ids[j], modelName, tableName, request);
				String pAdmin = null;
				if (j <= array.size() - 1) {
					JSONArray __obj = array.getJSONArray(j);
					for (Object ___obj : __obj) {
						JSONObject ____obj = JSONObject.fromObject(___obj);
						if (____obj.getString("value").equals(ids[j])) {
							pAdmin = ____obj.getString("pAdmin");
							break;
						}
					}
					if ("1".equals(pAdmin)) {
                        showType = SHOW_ALL;
                    }
				}
				List<String> _readList = null;
				List<String> _editList = null;
				switch (showType) {
				case SHOW_READ:
					_readList = findReaderIds(service, ids[j], modelName, tableName);
					_editList = findEditorIds(service, ids[j], modelName, tableName);
					break;
				case SHOW_EDIT:
					_editList = findEditorIds(service, ids[j], modelName, tableName);
					break;
				}
				listToJson(_list, __array, ids, j, currId, _readList, hierarchyAuthList, _editList, showType);
				if (__array.size() > 0) {
                    array.add(__array);
                }
			}
		}
		request.setAttribute("lui-source", array);
	}

	protected void buildSearchHQLInfo(HQLInfo hqlInfo, String tableName, String searchText, String modelName) {
		if (StringUtil.isNotNull(searchText)) {
			String langFieldName = SysLangUtil.getLangFieldName(modelName, "fdName");
			String whereBlock_lang = "";

			if (StringUtil.isNotNull(langFieldName)) {
				whereBlock_lang = " or " + tableName + "." + langFieldName + " like :searchText";
			}
			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and (",
					tableName + ".fdName like :searchText" + whereBlock_lang + ")"));
			hqlInfo.setParameter("searchText", "%" + searchText + "%");
		}
	}


	protected void buildSearchHQLInfo(HQLInfo hqlInfo, String tableName, String searchText) {
		if (StringUtil.isNotNull(searchText)) {

			hqlInfo.setWhereBlock(
					StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", tableName + ".fdName like :searchText"));
			hqlInfo.setParameter("searchText", "%" + searchText + "%");
		}
	}

	protected void buildParentHQLInfo(HQLInfo hqlInfo, String tableName, String categoryId) {
		String joinBlock = hqlInfo.getJoinBlock();
		if (StringUtil.isNull(joinBlock) || joinBlock.indexOf("hbmParent") < 0) {
			hqlInfo.setJoinBlock(StringUtil.linkString(joinBlock, " ", "left join " + tableName + ".hbmParent hbmParent"));
		}
		if (StringUtil.isNull(categoryId)) {
			hqlInfo.setWhereBlock(
					StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "hbmParent is null"));
		} else {
			hqlInfo.setWhereBlock(
					StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "hbmParent.fdId=:categoryId"));
			hqlInfo.setParameter("categoryId", categoryId);
		}
	}

	protected void initHQLInfo(HQLInfo hqlInfo, String tableName, String modelName) {
		setAreaIsolation(hqlInfo);
		StringBuilder selectBlock = null;
		if (hasDescript(modelName)) {
			selectBlock = new StringBuilder().append(tableName).append(".fdName, ").append(tableName).append(".fdId, ")
					.append(tableName).append(".fdHierarchyId, ").append(tableName).append(".fdDesc");
		} else {
			selectBlock = new StringBuilder().append(tableName).append(".fdName, ").append(tableName).append(".fdId, ")
					.append(tableName).append(".fdHierarchyId, ''");
		}

		if (SysAuthAreaUtils.isAreaEnabled(modelName)) {
			selectBlock.append(", ").append(tableName).append(".").append(ISysAuthConstant.AREA_FIELD_NAME);
		}

		String langFieldName = SysLangUtil.getLangFieldName(modelName, "fdName");
		String selectBlock_lang = "";

		if (StringUtil.isNotNull(langFieldName)) {
			selectBlock_lang = ", " + tableName + "." + langFieldName;
			selectBlock.append(selectBlock_lang);
		}

		hqlInfo.setSelectBlock(selectBlock.toString());
		hqlInfo.setOrderBy(tableName + ".fdOrder, " + tableName + ".fdId");
		hqlInfo.setWhereBlock("1=1");
	}

	protected void buildValue(HttpServletRequest request, HQLInfo hqlInfo, String tableName) {
		String prefix = "qq.";
		Enumeration enume = request.getParameterNames();
		String whereBlock = hqlInfo.getWhereBlock();
		while (enume.hasMoreElements()) {
			String name = (String) enume.nextElement();
			if (name != null && name.trim().startsWith(prefix)) {
				String value = request.getParameter(name);
				if (StringUtil.isNotNull(value)) {
					name = name.trim().substring(prefix.length());
					String[] ___val = value.split("[;；,，]");

					String ___block = "";
					for (int i = 0; i < ___val.length; i++) {
						String param = "kmss_ext_props_" + HQLUtil.getFieldIndex();
						___block = StringUtil.linkString(___block, " or ", tableName + "." + name + " =:" + param);
						hqlInfo.setParameter(param, ___val[i]);
					}
					whereBlock = StringUtil.linkString(whereBlock, " and ", "(" + ___block + ")");
				}
			}
		}
		hqlInfo.setWhereBlock(whereBlock);
	}

	protected void loadParentCategoriesById(HttpServletRequest request) throws Exception {
		JSONArray array = new JSONArray();
		String modelName = request.getParameter("modelName");
		String currId = request.getParameter("currId");
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		IBaseService service = (IBaseService) SpringBeanUtil.getBean(dict.getServiceBean());
		String tableName = ModelUtil.getModelTableName(modelName);
		Object[] curr = findOne(service, tableName, currId, request);
		List<Object[]> list = findAllParent(service, tableName, currId,
				curr[2].toString().split(BaseTreeConstant.HIERARCHY_ID_SPLIT), request);
		// 分类可阅读者
		int showType = getShowType(modelName, request);
		// Boolean isAdmin = SimpleCategoryUtil.isAdmin(modelName);
		List<String> authList = null;
		if (showType == SHOW_READ) {
            authList = findReaderIds(service, "__all", modelName, tableName);
        }
		// 层级可阅读者
		List<String> hierarchyAuthList = findHierarchyReaderIds(service, modelName, tableName);
		if (list.size() > 0) {
			for (int i = list.size() - 1; i >= 0; i--) {
				Object[] cate = list.get(i);
				Boolean disabled = false;
				if (authList != null && !authList.contains(cate[1])) {
					if (hierarchyAuthList.contains(cate[1])) {
                        disabled = true;
                    } else {
                        continue;
                    }
				}
				JSONObject row = fillToJson(cate[0], cate[1], (String) cate[2], disabled);
				array.add(row);
			}
		}
		request.setAttribute("lui-source", array);
	}

	public ActionForward select(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-select", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String type = request.getParameter("type");
			if (StringUtil.isNotNull(type)) {
				if ("01".equals(type)) // 快速搜索
                {
                    this.loadParentCategoriesById(request);
                }
				if ("02".equals(type)) // 初始单选分类
                {
                    this.loadSelectCategoryiesById(request);
                }
				if ("03".equals(type)) // 下级分类
                {
                    this.loadSelectCategories(request);
                }
				if ("04".equals(type)) // 初始多选分类
                {
                    this.loadSelectCategoriesByIds(request);
                }
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-select", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}

	private void loadSelectCategoriesByIds(HttpServletRequest request) throws Exception {
		String modelName = request.getParameter("modelName");
		String currId = request.getParameter("currId");
		String pAdmin = request.getParameter("pAdmin");
		JSONArray array = new JSONArray();
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		IBaseService service = (IBaseService) SpringBeanUtil.getBean(dict.getServiceBean());
		String tableName = ModelUtil.getModelTableName(modelName);
		List<Object[]> _list = (List<Object[]>) findAll(service, null, modelName, tableName, request);
		int showType = getShowType(modelName, request);
		List<String> authList = null;
		List<String> hierarchyReaderList = null;
		// Boolean isAdmin = SimpleCategoryUtil.isAdmin(modelName);
		if (showType == SHOW_READ) {
            authList = findReaderIds(service, null, modelName, tableName);
        }
		if (hierarchyReaderList == null) {
			hierarchyReaderList = findHierarchyReaderIds(service, modelName, tableName);
		}
		JSONArray _array = new JSONArray();
		for (Object[] obj : _list) {
			Boolean disabled = false;
			if (authList != null && !authList.contains(obj[1])) {
				if (hierarchyReaderList.contains(obj[1])) {
                    disabled = true;
                } else {
                    continue;
                }
			}
			String ___pAdmin = "";
			if (showType == SHOW_ALL || "1".equals(pAdmin)) {
                ___pAdmin = "1";
            }
			_array.add(fillToJson(obj[0], obj[1], (String) obj[3], disabled, ___pAdmin));
		}
		array.add(_array);
		// 默认选中
		if (StringUtil.isNotNull(currId)) {
			String[] currIds = currId.split(";");
			List<Object[]> __list = (List<Object[]>) findAll(service, tableName, currIds, modelName);
			JSONArray __array = new JSONArray();
			for (Object[] obj : __list) {
				__array.add(fillToJson(obj[0], obj[1], (String) obj[3], obj[2]));
			}
			array.add(__array);
		}
		request.setAttribute("lui-source", array);
	}

	protected JSONObject fillToJson(Object text, Object value, Object hierarchyId) {
		return fillToJson(text, value, null, hierarchyId);
	}

	protected JSONObject fillToJson(Object text, Object value, String desc, Object hierarchyId) {
		JSONObject row = this.fillToJson(text, value, desc);
		row.put("hierarchyId", hierarchyId);
		return row;
	}

	protected List<?> findAll(IBaseService service, String modelName, String tableName, HttpServletRequest request)
			throws Exception {
		return findAllByParent(service, null, modelName, tableName, request);
	}


	protected List<Object[]> findAll(IBaseService service, String tableName, String[] ids, String modelName)
			throws Exception {
		if (ids == null || ids.length == 0) {
			return Collections.emptyList();
		}
		List<String> list = new ArrayList<String>();
		for (String pid : ids) {
			list.add(pid);
		}
		if (list.isEmpty()) {
			return Collections.emptyList();
		}

		String langFieldName = SysLangUtil.getLangFieldName(modelName, "fdName");
		String selectBlock_lang = "";
		if (StringUtil.isNotNull(langFieldName)) {
			selectBlock_lang = ", " + tableName + "." + langFieldName;
		}
		HQLInfo hqlInfo = new HQLInfo();
		if (hasDescript(modelName)) {
			hqlInfo.setSelectBlock(tableName + ".fdName, " + tableName + ".fdId, " + tableName + ".fdHierarchyId, "
					+ tableName + ".fdDesc" + selectBlock_lang);
		} else {
			hqlInfo.setSelectBlock(tableName + ".fdName, " + tableName + ".fdId, " + tableName + ".fdHierarchyId, ''"
					+ selectBlock_lang);
		}

		hqlInfo.setWhereBlock(HQLUtil.buildLogicIN(tableName + ".fdId", list));
		hqlInfo.setOrderBy(tableName + ".fdHierarchyId asc");
		List<Object[]> result = (List<Object[]>) service.findValue(hqlInfo);
		if (StringUtil.isNotNull(langFieldName)) {
			replaceFdName(result, 0, 4);
		}
		return result;
	}

	protected List<Object[]> findAll(IBaseService service, String tableName, String[] ids) throws Exception {
		return findAll(service, tableName, ids, null);
	}

	private void listToJson(List<Object[]> list, JSONArray array, String ids[], List<String> readList,
			List<String> hierarchyAuthList, List<String> editList, int showType) throws Exception {
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
			pObj.put("desc", null != obj[3] ? obj[3] : "");
			if (ids != null && StringUtil.isNotNull(ids[0])) {
				pObj.put("selected", ids[0].equals(obj[1]) ? Boolean.TRUE : Boolean.FALSE);
			} else {
				pObj.put("selected", Boolean.FALSE);
			}
			pObj.put("nodeType", disabled);
			String ___pAdmin = "";
			if (showType == SHOW_ALL || editList.contains(obj[1])) {
                ___pAdmin = "1";
            }
			pObj.put("pAdmin", ___pAdmin);
			array.add(pObj);
		}
	}

	private void listToJson(List<Object[]> list, JSONArray array, String ids[], int index, String currId,
			List<String> readList, List<String> hierarchyAuthList, List<String> editList, int showType)
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
			pObj.put("desc", null != obj[3] ? obj[3] : "");
			pObj.put("selected", index + 1 < ids.length ? ids[index + 1].equals(obj[1]) : currId.equals(obj[1]));
			pObj.put("nodeType", disabled);
			String ___pAdmin = "";
			if (showType == SHOW_ALL || editList.contains(obj[1])) {
                ___pAdmin = "1";
            }
			pObj.put("pAdmin", ___pAdmin);
			array.add(pObj);
		}
	}

	protected List findReaderIds(IBaseService service, String categoryId, String modelName, String tableName)
			throws Exception {
		String hql = "select " + tableName + ".fdId from " + modelName + " " + tableName + " left join " + tableName
				+ ".authAllReaders readers left join " + tableName + ".hbmParent hbmParent where ";

		if (StringUtil.isNull(categoryId)) {
			hql += "hbmParent is null and";
		} else if (!"__all".equals(categoryId)) {
			hql += "hbmParent.fdId=:categoryId and";
		}
		List orgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			// 如果是外部组织，只能查看有权限的模板
			if (SysOrgEcoUtil.isExternal()) {
				hql += " readers.fdId in (:orgIds)";
			} else {
				orgIds.add(UserUtil.getEveryoneUser().getFdId());
				hql += " ((" + tableName + ".authReaderFlag= :authReaderFlag and readers.fdId is null) or readers.fdId in (:orgIds))";
			}
		} else {
			orgIds.add(UserUtil.getEveryoneUser().getFdId());
			hql += " (" + tableName + ".authReaderFlag= :authReaderFlag or readers.fdId in (:orgIds))";
		}
		AreaIsolation isolationType = SysAuthAreaUtils.getAreaIsolation(ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		Query query = SysAuthAreaHelper.queryWithArea(service, hql, modelName, tableName, isolationType);
		if (StringUtil.isNotNull(categoryId) && !"__all".equals(categoryId)) {
			query.setParameter("categoryId", categoryId);
		}
		if(!(SysOrgEcoUtil.IS_ENABLED_ECO && SysOrgEcoUtil.isExternal())){
			query.setParameter("authReaderFlag", Boolean.TRUE);
		}
		query.setParameterList("orgIds", orgIds);
		return query.list();
	}

	protected List findEditorIds(IBaseService service, String categoryId, String modelName, String tableName)
			throws Exception {
		String hql = "select " + tableName + ".fdId from " + modelName + " " + tableName + " inner join " + tableName
				+ ".authAllEditors editors left join " + tableName + ".hbmParent hbmParent where ";
		if (StringUtil.isNull(categoryId)) {
			hql += "hbmParent is null and ";
		} else if (!"__all".equals(categoryId)) {
			hql += "hbmParent.fdId=:categoryId and ";
		}
		hql += " editors.fdId in (:orgIds)";
		AreaIsolation isolationType = SysAuthAreaUtils.getAreaIsolation(ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		Query query = SysAuthAreaHelper.queryWithArea(service, hql, modelName, tableName, isolationType);
		if (StringUtil.isNotNull(categoryId) && !"__all".equals(categoryId)) {
			query.setParameter("categoryId", categoryId);
		}
		query.setParameterList("orgIds", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
		return query.list();
	}

	protected List findHierarchyReaderIds(IBaseService service, String fdParentId, String modelName, String tableName)
			throws Exception {
		return findHierarchyReaderIds(service, modelName, tableName);
	}

	protected List findHierarchyReaderIds(IBaseService service, String modelName, String tableName) throws Exception {
		return findHierarchyReaderIdsByParent(service,modelName,tableName,null);
	}

	protected List findHierarchyReaderIdsByParent(IBaseService service, String modelName, String tableName, String parentId) throws Exception {
		// 通过HQL查询有权限的层级ID
		String hql = "select distinct " + tableName + ".fdHierarchyId from " + modelName + " " + tableName
				+ " left join " + tableName + ".authAllEditors editors";
		hql += " left join " + tableName + ".authAllReaders readers";

		hql += " where ";
		SysSimpleCategoryAuthTmpModel fdParentCate = null;
		if (StringUtil.isNotNull(parentId)) {
			fdParentCate = (SysSimpleCategoryAuthTmpModel)service.findByPrimaryKey(parentId);
			if (fdParentCate !=null) {
				hql += (" ( " + tableName + ".fdHierarchyId like :fdParentHid )");
			}
		}
		if (fdParentCate !=null) {
			hql += " and (editors.fdId in (:orgIds)";
		} else {
			hql += " (editors.fdId in (:orgIds)";
		}
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
			hql += " or " + tableName + ".authReaderFlag= :authReaderFlag or readers.fdId in (:orgIds))";
		}
		AreaIsolation isolationType = SysAuthAreaUtils.getAreaIsolation(ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		Query query = SysAuthAreaHelper.queryWithArea(service, hql, modelName, tableName, isolationType);
		if (!SysOrgEcoUtil.IS_ENABLED_ECO) {
			query.setParameter("authReaderFlag", Boolean.TRUE);
		}

		if (fdParentCate !=null) {
			query.setParameter("fdParentHid", fdParentCate.getFdHierarchyId() + "%");
		}

		query.setParameterList("orgIds", orgIds);
		// 查找所有父分类的id
		List<String> cateIds = hierarchyId2Fdid(query.list());
		return cateIds;
	}

	private List findAllEditorIds(IBaseService service, String modelName, String tableName) throws Exception {
		// 通过HQL查询有权限的层级ID
		String hql = "select distinct " + tableName + ".fdId from " + modelName + " " + tableName + " left join "
				+ tableName + ".authAllEditors editors";
		hql += " where editors.fdId in (:orgIds)";
		AreaIsolation isolationType = SysAuthAreaUtils.getAreaIsolation(ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		Query query = SysAuthAreaHelper.queryWithArea(service, hql, modelName, tableName, isolationType);
		query.setParameterList("orgIds", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
		return query.list();
	}

	protected List<String> hierarchyId2Fdid(List hierarchyIds) {
		List<String> results = new ArrayList<String>();
		for (Object hierarchyId : hierarchyIds) {
			if (StringUtil.isNull((String) hierarchyId)) {
				continue;
			}
			String[] ids = ((String) hierarchyId).split(BaseTreeConstant.HIERARCHY_ID_SPLIT);
			for (int i = 1; i < ids.length - 1; i++) {
				if (!results.contains(ids[i])) {
					results.add(ids[i]);
				}
			}
		}
		return results;
	}

	protected JSONObject fillToJson(Object text, Object value, Boolean disabled, String pAdmin) {
		return fillToJson(text, value, null, disabled, pAdmin);
	}

	protected JSONObject fillToJson(Object text, Object value, String desc, Boolean disabled, String pAdmin) {
		JSONObject row = this.fillToJson(text, value, desc);
		row.put("nodeType", disabled);
		row.put("pAdmin", pAdmin);
		return row;
	}

	protected int getShowType(String modelName, HttpServletRequest request) {

		if (SimpleCategoryUtil.isAdmin(modelName)) {
			return SHOW_ALL;
		}
		// 父分类可维护者
		if ("1".equals(request.getParameter("pAdmin"))) {
			return SHOW_ALL;
		}
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		if (dict.getPropertyMap().get("authAllEditors") == null
				|| dict.getPropertyMap().get("authAllReaders") == null) {
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

	// 判断模块是否有描述字段
	private boolean hasDescript(String modelName) {
		boolean flag = false;
		if (StringUtil.isNull(modelName)) {
            return flag;
        }

		try {
			String fdDesc = HibernateUtil.getColumnName(ClassUtils.forName(modelName), "fdDesc");
			if (StringUtil.isNotNull(fdDesc)) {
				flag = true;
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return flag;
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

	/**
	 * 设置集团分级权限
	 * 
	 * @param hqlInfo
	 */
	private void setAreaIsolation(HQLInfo hqlInfo) {
		if (ISysAuthConstant.IS_AREA_ENABLED && ISysAuthConstant.IS_ISOLATION_ENABLED) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaCheck, SysAuthConstant.AreaCheck.YES);

			// 先读取area-isolation.xml配置，如果未有配置，则按原逻辑进行
			AreaIsolation isolationType = SysAuthAreaUtils
					.getIsolationFromSceneConfig(ISysAuthConstant.ISOLATION_SCENE_SIMPLE_CATEGORY);
			if (isolationType != null) {
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaIsolation, isolationType);
			} else {
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaIsolation, SysAuthConstant.AreaIsolation.BRANCH);
			}
		}

	}
	
	
	
	/**
	 * 获取子分类id，用于分类选择框的过滤
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAllChildrenIds(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		TimeCounter.logCurrentTime("Action-getAllChildrenIds", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String parentId = request.getParameter("fdId");
			String modelName = request.getParameter("modelName");
			List<String> list = null;
			
			if(StringUtil.isNotNull(parentId) && StringUtil.isNotNull(modelName)) {
				SysDictModel dict = SysDataDict.getInstance().getModel(modelName);

				IBaseService service = (IBaseService) SpringBeanUtil.getBean(dict.getServiceBean());
				String tableName = ModelUtil.getModelTableName(modelName);

				HQLInfo hqlInfo = new HQLInfo();

				hqlInfo.setSelectBlock(tableName + ".fdId");
				hqlInfo.setWhereBlock(tableName + ".fdHierarchyId like:fdHierarchyId");
				hqlInfo.setParameter("fdHierarchyId", "%" + parentId + "%");
				list = service.findValue(hqlInfo);
			} 
			
			request.setAttribute("lui-source", list != null ? JSONArray.fromObject(list) : "[]");

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-getAllChildrenIds", false, getClass());

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}
}
