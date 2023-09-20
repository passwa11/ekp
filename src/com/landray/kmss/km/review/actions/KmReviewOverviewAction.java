package com.landray.kmss.km.review.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.review.model.KmReviewTemplate;
import com.landray.kmss.km.review.service.IKmReviewOverviewService;
import com.landray.kmss.km.review.service.IKmReviewTemplateService;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.category.interfaces.ConfigUtil;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.service.ISysCategoryConfigService;
import com.landray.kmss.sys.category.service.ISysCategoryMainService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
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


public class KmReviewOverviewAction extends ExtendAction

{
	protected IKmReviewOverviewService kmReviewOverviewService;
	
	protected IKmReviewTemplateService kmReviewTemplateService;

	@Override
	protected IKmReviewOverviewService getServiceImp(HttpServletRequest request) {
		if (kmReviewOverviewService == null) {
            kmReviewOverviewService = (IKmReviewOverviewService) getBean("kmReviewOverviewService");
        }
		return kmReviewOverviewService;
	}
	public IKmReviewTemplateService getKmReviewTemplateService() {
		if (kmReviewTemplateService == null) {
            kmReviewTemplateService = (IKmReviewTemplateService) getBean("kmReviewTemplateService");
        }
		return kmReviewTemplateService;
	}
	
	private ISysCategoryMainService sysCategoryMainService;

	public ISysCategoryMainService getCategoryMainService() {
		if (sysCategoryMainService == null) {
			sysCategoryMainService = (ISysCategoryMainService) SpringBeanUtil
					.getBean("sysCategoryMainService");
		}
		return sysCategoryMainService;
	}
	
	private ISysCategoryConfigService sysCategoryConfigService;

	public ISysCategoryConfigService getCategoryConfigService() {
		if (sysCategoryConfigService == null) {
			sysCategoryConfigService = (ISysCategoryConfigService) SpringBeanUtil
					.getBean("sysCategoryConfigService");
		}
		return sysCategoryConfigService;
	}

	public ActionForward preview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String arrayString = getServiceImp(request)
					.getReviewPre();
			if (StringUtil.isNull(arrayString)) {
				arrayString = getServiceImp(request)
						.updateReview();
			}
			JSONArray array = new JSONArray();
			array = JSONArray.fromObject(arrayString);
			/*
			 * if (SysLangUtil.isLangEnabled()) { }
			 */
			String fdKey = "com.landray.kmss.km.review.model.KmReviewTemplate";
			// 若分类权限过滤开关开启则过滤权限
			String fdValue = (String) getCategoryConfigService()
					.findByModuleKey(fdKey);
			if ("1".equals(fdValue)) {
				updateTextByAuth(array, null, null, 0);
			} else {
				updateText(array);
			}

			request.setAttribute("lui-source", array);

		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-index", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}
	
	private void updateText(JSONArray array) throws Exception {
		if (array != null) {
			for (int i = 0; i < array.size(); i++) {
				JSONObject o = (JSONObject) array.get(i);
				String id = o.getString("id");
				String nodeType = o.getString("nodeType");
				if ("TEMPLATE".equals(nodeType)) {
					KmReviewTemplate temp = (KmReviewTemplate) getKmReviewTemplateService()
							.findByPrimaryKey(id, null, true);
					if (temp != null) {
						o.put("text", temp.getFdName());
					} else {
						array.remove(i);
					}
				} else if ("CATEGORY".equals(nodeType)) {
					SysCategoryMain category = (SysCategoryMain) getCategoryMainService()
							.findByPrimaryKey(id, null, true);
					if (category != null) {
						o.put("text", category.getFdName());
					}
					if (o.containsKey("children")) {
						try {
							updateText(o.getJSONArray("children"));
						} catch (net.sf.json.JSONException e) {

						}
					}
				}
			}
		}
	}

	private Integer updateTextByAuth(JSONArray array, List<String> authIds,
			List<String> categoryEditorIds, Integer authIndex)
			throws Exception {
		String modelName = "com.landray.kmss.km.review.model.KmReviewTemplate";
		if(authIds == null){
			authIds = ___getCategoryAuthIds(modelName, null);
		}
		if(categoryEditorIds == null){
			if (ConfigUtil.auth(modelName)) {
				categoryEditorIds = findCategoryAllEditorIds("sysCategoryMain");
			}
		}
		Integer tempAuthIndex = authIndex;
		if (tempAuthIndex != 0) {
			tempAuthIndex = 0;
		}
		if (array != null) {
			for (int i = 0; i < array.size(); i++) {
				JSONObject o = (JSONObject) array.get(i);
				String id = o.getString("id");
				String nodeType = o.getString("nodeType");
				
				if ("TEMPLATE".equals(nodeType)) {
					KmReviewTemplate temp = (KmReviewTemplate) getKmReviewTemplateService()
							.findByPrimaryKey(id, null, true);
					
					if (temp != null) {
							if (!hasAuth(authIds, categoryEditorIds,id,temp.getDocCategory().getFdId())){
								o.put("text", temp.getFdName());
							tempAuthIndex = tempAuthIndex + 1;
							}else{
								array.remove(i);
							if (i >= 0) {
								i = i - 1;
							}
							}
					} else {
						array.remove(i);
						if (i >= 0) {
							i = i - 1;
						}
					}
				} else if ("CATEGORY".equals(nodeType)) {
					SysCategoryMain category = (SysCategoryMain) getCategoryMainService()
							.findByPrimaryKey(id, null, true);
					List<String> categoryAuthIds = ___getCategoryAuthIds(
							modelName,
							id);
					Integer index = 0;
					if (category != null) {

						if (!hasAuth(categoryAuthIds, categoryEditorIds, id,
								category.getFdHierarchyId())) {
							o.put("text",
									category.getFdName());
							index = 1;
							tempAuthIndex = tempAuthIndex + 1;
						}
						// 若没有子分类直接去掉
						if (index == 0 && !o.containsKey("children")) {

							array.remove(i);
							if (i >= 0) {
								i = i - 1;
							}
						}
					} else {
						array.remove(i);
						if (i >= 0) {
							i = i - 1;
						}
					}
					// 若有子分类时进行回调判断其子分类或者模板是否有权限
					if (o.containsKey("children")) {
						try {
							if (index == 0) {
								Integer tempIndex = updateTextByAuth(
										o.getJSONArray("children"), authIds,
										categoryEditorIds, 0);
								if (tempIndex < 1) {
									array.remove(i);
									if (i >= 0) {
										i = i - 1;
									}
								}
							} else {
								Integer tempIndex = updateTextByAuth(
										o.getJSONArray("children"), authIds,
										categoryEditorIds, 0);
							}

						} catch (net.sf.json.JSONException e) {

						}
					}
				}
			}
		}
		return tempAuthIndex;
	}
	

	private Boolean hasAuth(List<String> __authList,
			List<String> categoryEditorIds, Object id,Object hierarchyId) {
		return !__authList.contains(id)  && ((categoryEditorIds != null && !ArrayUtil.isArrayIntersect(
            categoryEditorIds.toArray(), hierarchyId.toString()
                .split(BaseTreeConstant.HIERARCHY_ID_SPLIT))) || categoryEditorIds == null);
	}
	
	protected List<Object[]> findCategoriesByParentId(String parentId,
			String modelName) throws Exception {
		String langFieldName = SysLangUtil.getLangFieldName(
				"com.landray.kmss.sys.category.model.SysCategoryMain",
				"fdName");
		String selectBlock_lang = "";
		if (StringUtil.isNotNull(langFieldName)) {
			selectBlock_lang = ", sysCategoryMain."
					+ langFieldName;
		}
		HQLInfo info = new HQLInfo();
		info.setSelectBlock(
				"sysCategoryMain.fdName, sysCategoryMain.fdId, sysCategoryMain.fdHierarchyId, sysCategoryMain.fdDesc"
						+ selectBlock_lang);
		if (StringUtil.isNotNull(parentId)) {
			info.setWhereBlock("sysCategoryMain.hbmParent.fdId = :id");
			info.setParameter("id", parentId);
		} else {
			info.setWhereBlock("sysCategoryMain.hbmParent is null");
		}
		info.setWhereBlock(info.getWhereBlock()
				+ " and sysCategoryMain.fdModelName = :modelName");
		info.setParameter("modelName", modelName);
		info.setOrderBy("sysCategoryMain.fdOrder, sysCategoryMain.fdId");
		List<Object[]> result = getCategoryMainService().findValue(info);
		if (StringUtil.isNotNull(langFieldName)) {
			for (Object[] o : result) {
				if (StringUtil.isNotNull((String) o[4])) {
					o[0] = (String) o[4];
				}
			}
		}
		return result;
	}
	
	
	protected List<String> findCategoryAllEditorIds(String tableName)
			throws Exception {
		IBaseService baseService = (IBaseService) getBean("sysCategoryMainService");
		String whereBlock = tableName + ".authAllEditors.fdId in (:authOrgs)";
		String selectBlock = tableName + ".fdId";
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock(selectBlock);
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setParameter("authOrgs", UserUtil.getKMSSUser()
				.getUserAuthInfo().getAuthOrgIds());
		return baseService.findValue(hqlInfo);
	}
	
	protected List<String> ___getCategoryAuthIds(String modelName,
			String categoryId) throws Exception {
		// 计算有阅读权限的模板Id，分类Id及其分类的上级Id
		HQLInfo hqlInfo = new HQLInfo();
		SysDictModel templateModel = SysDataDict.getInstance().getModel(
				modelName);
		IBaseService baseService = (IBaseService) getBean(templateModel
				.getServiceBean());
		if (ConfigUtil.auth(modelName)) {
			if (hqlInfo.getCheckParam(SysAuthConstant.CheckType.AllCheck) == null) {
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
						SysAuthConstant.AllCheck.DEFAULT);
			}
		}
		
		if (ISysAuthConstant.IS_AREA_ENABLED
				&& ISysAuthConstant.IS_ISOLATION_ENABLED) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaCheck,
					SysAuthConstant.AreaCheck.YES);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaIsolation,
					ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		}

		String modelTableName = ModelUtil.getModelTableName(modelName);
		hqlInfo.setSelectBlock(modelTableName + ".fdId, " + modelTableName
				+ ".docCategory.fdHierarchyId," + modelTableName
				+ ".docCategory.fdName");

		if (StringUtil.isNotNull(categoryId)) {
			hqlInfo.setWhereBlock(modelTableName
					+ ".docCategory.fdId = :categoryId");
			hqlInfo.setParameter("categoryId", categoryId);
		}

		List<?> results = baseService.findValue(hqlInfo);
		if (results.isEmpty()) {
			return new ArrayList<String>();
		}
		List<String> authIds = new ArrayList<String>();
		for (int i = 0; i < results.size(); i++) {

			Object[] obj = (Object[]) results.get(i);
			String id = obj[0].toString();
			if (StringUtil.isNotNull(id) && !authIds.contains(id)) {
				authIds.add(id);
			}
			String hid = obj[1].toString();
			convertHierarchyId2Ids(hid, authIds);
		}
		return authIds;
	}
	
	private void convertHierarchyId2Ids(String hierarchyId, List<String> authIds) {
		if (StringUtil.isNotNull(hierarchyId)) {
			String[] ids = hierarchyId
					.split(BaseTreeConstant.HIERARCHY_ID_SPLIT);
			for (int j = 0; j < ids.length; j++) {
				if (StringUtil.isNotNull(ids[j]) && !authIds.contains(ids[j])) {
					authIds.add(ids[j]);
				}
			}
		}
	}


}
