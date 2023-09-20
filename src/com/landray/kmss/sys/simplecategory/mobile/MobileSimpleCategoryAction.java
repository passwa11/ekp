package com.landray.kmss.sys.simplecategory.mobile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.query.Query;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysAuthConstant.AreaIsolation;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.authorization.util.SysAuthAreaHelper;
import com.landray.kmss.sys.category.interfaces.ConfigUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MobileSimpleCategoryAction extends BaseAction {

	private IMobileSimpleCategoryService mobileSimpleCategoryService;

	private IMobileSimpleCategoryService getMobileSimpleCategoryService() {
		if (mobileSimpleCategoryService == null) {
            mobileSimpleCategoryService = (IMobileSimpleCategoryService) SpringBeanUtil
                    .getBean("mobileSimpleCategoryService");
        }
		return mobileSimpleCategoryService;
	}

	private IXMLDataBean sysSimpleCategoryTreeService = null;

	private IXMLDataBean getSimpleCategoryTreeService() {
		if (sysSimpleCategoryTreeService == null) {
            sysSimpleCategoryTreeService = (IXMLDataBean) SpringBeanUtil
                    .getBean("sysSimpleCategoryTreeService");
        }
		return sysSimpleCategoryTreeService;
	}

	public JSON formatData(List<Map<String, Object>> dataList) {
		return JSONArray.fromObject(dataList);
	}

	/**
	 * 新建和类别筛选时使用， 新建时，需要机型权限校验 导航时，根据全局分类权限配置决定是否需要权限校验
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cateList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String authCateIds = request.getParameter("authCateIds");
			String authType = request.getParameter("authType");
			String modelName = request.getParameter("modelName");
			RequestContext req = new RequestContext();
			// 通过语句new RequestContext(request)构建的rq对象，不能修改parameter参数，故改用现用模式
			HashMap paraMap = new HashMap(request.getParameterMap());
			boolean readAll = "00".equals(authType);
			if ("03".equals(authType)) {// 权限由配置决定时的处理
				if (ConfigUtil.auth(modelName)) {
					paraMap.put("authType", new String[] { "02" });
				} else {
					paraMap.put("authType", new String[] { "00" });
					readAll = true;
				}
			}
			req.setParameterMap(paraMap);
			//移动端需要，获取子分类做显示箭头用
			req.setParameter("isLoadChildren", "1");
			List<Map<String, Object>> cates = (List<Map<String, Object>>) getSimpleCategoryTreeService()
					.getDataList(req);
			if (!readAll) {
				List<String> authIds = new ArrayList<String>();
				if (StringUtil.isNotNull(authCateIds)) {
					authIds = ArrayUtil.convertArrayToList(authCateIds
							.split(";"));
				}
				List<Map<String, Object>> tmpCates = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < cates.size(); i++) {
					Map<String, Object> tmpMap = (Map<String, Object>) cates
							.get(i);
					Boolean isShow = true;
					if ("0".equals(tmpMap.get("isShowCheckBox"))) {// 非可用分类信息，需要在authIds中包含或者存在可用的下级分类
						if ((!authIds.isEmpty() && authIds.contains(tmpMap.get("value")))
								|| findAuthByCateId((String) tmpMap.get("value"), modelName).size() > 0) {
							tmpCates.add(tmpMap);
						} else {
							isShow = false;
						}
						continue;
					} else {//可用分类信息
						tmpCates.add(tmpMap);
					}
					if(isShow) {
						List children = (List)tmpMap.get("child");
						if(!ArrayUtil.isEmpty(children)) {
							Iterator itr = children.iterator();
							while(itr.hasNext()) {
								Map cmap = (Map)itr.next();
								if ("0".equals(cmap.get("isShowCheckBox"))) { 
									if (!(!authIds.isEmpty()
											&& authIds.contains(cmap.get("value")))) {
										itr.remove();
									}
								}
							}
						}
					}
				}
				authIds.clear();
				authIds = null;
				cates = tmpCates;
			}
			request.setAttribute("lui-source", formatData(cates).toString());
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

	/* 查找可用的下级分类 */
	public List findAuthByCateId(String cateId, String modelName) throws Exception {
		List cateList = new ArrayList();
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		IBaseService service = (IBaseService) SpringBeanUtil.getBean(dict.getServiceBean());
		String tableName = ModelUtil.getModelTableName(modelName);

		String hql = "select " + tableName + ".fdId from " + modelName + " " + tableName + " left join " + tableName
				+ ".authAllReaders readers where ";
		hql += tableName + ".hbmParent.fdId=:categoryId";
		List orgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		if (SysOrgEcoUtil.IS_ENABLED_ECO) {
			// 如果是外部组织，只能查看有权限的模板
			if (SysOrgEcoUtil.isExternal()) {
				hql += " and readers.fdId in (:orgIds)";
			} else {
				orgIds.add(UserUtil.getEveryoneUser().getFdId());
				hql += " and (readers.fdId is null or readers.fdId in (:orgIds))";
			}
		} else {
			hql += " and (" + tableName + ".authReaderFlag= :authReaderFlag or readers.fdId in (:orgIds))";
		}
		AreaIsolation isolationType = SysAuthAreaUtils.getAreaIsolation(ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
		Query query = SysAuthAreaHelper.queryWithArea(service, hql, modelName, tableName, isolationType);
		if (!SysOrgEcoUtil.IS_ENABLED_ECO) {
			query.setParameter("authReaderFlag", Boolean.TRUE);
		}
		query.setParameter("categoryId", cateId);
		query.setParameterList("orgIds", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
		cateList = query.list();
		return cateList;
	}

	public ActionForward searchList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String authCateIds = request.getParameter("authCateIds");
			String authType = request.getParameter("authType");
			String modelName = request.getParameter("modelName");
			RequestContext req = new RequestContext();
			// 通过语句new RequestContext(request)构建的rq对象，不能修改parameter参数，故改用现用模式
			HashMap paraMap = new HashMap(request.getParameterMap());
			boolean readAll = "00".equals(authType);
			if ("03".equals(authType)) {// 权限由配置决定时的处理
				if (ConfigUtil.auth(modelName)) {
					paraMap.put("authType", new String[] { "02" });
				} else {
					paraMap.put("authType", new String[] { "00" });
					readAll = true;
				}
			}
			req.setParameterMap(paraMap);
			// 移动端需要，获取子分类做显示箭头用
			req.setParameter("isLoadChildren", "1");
			List<Map<String, Object>> cates = (List<Map<String, Object>>) getMobileSimpleCategoryService()
					.searchList(req);
			if (!readAll) {
				List<String> authIds = new ArrayList<String>();
				if (StringUtil.isNotNull(authCateIds)) {
					authIds = ArrayUtil.convertArrayToList(authCateIds.split(";"));
				}
				List<Map<String, Object>> tmpCates = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < cates.size(); i++) {
					Map<String, Object> tmpMap = (Map<String, Object>) cates.get(i);
					Boolean isShow = true;
					if ("0".equals(tmpMap.get("isShowCheckBox"))) {// 非可用分类信息，需要在authIds中包含或者存在可用的下级分类
						if ((!authIds.isEmpty() && authIds.contains(tmpMap.get("value")))
								|| findAuthByCateId((String) tmpMap.get("value"), modelName).size() > 0) {
							System.out.println(findAuthByCateId((String) tmpMap.get("value"), modelName));
							tmpCates.add(tmpMap);
						} else {
							isShow = false;
						}
						continue;
					} else {// 可用分类信息
						tmpCates.add(tmpMap);
					}
					if (isShow) {
						List children = (List) tmpMap.get("child");
						if (!ArrayUtil.isEmpty(children)) {
							Iterator itr = children.iterator();
							while (itr.hasNext()) {
								Map cmap = (Map) itr.next();
								if ("0".equals(cmap.get("isShowCheckBox"))) {
									if (!(!authIds.isEmpty() && authIds.contains(cmap.get("value")))) {
										itr.remove();
									}
								}
							}
						}
					}
				}
				authIds.clear();
				authIds = null;
				cates = tmpCates;
			}
			request.setAttribute("lui-source", formatData(cates).toString());
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


	public ActionForward detailList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String cateId = request.getParameter("cateId");
			String modelName = request.getParameter("modelName");
			List rtnList = new ArrayList();
			if (StringUtil.isNotNull(cateId) && StringUtil.isNotNull(modelName)) {
				SysDictModel dict = SysDataDict.getInstance().getModel(
						modelName);
				IBaseService service = (IBaseService) SpringBeanUtil
						.getBean(dict.getServiceBean());
				String tableName = ModelUtil.getModelTableName(modelName);
				
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setModelName(modelName);
				hqlInfo.setWhereBlock(HQLUtil.buildLogicIN(tableName + ".fdId",
						ArrayUtil.convertArrayToList(cateId.split(";"))));
				List cateList = service.findValue(hqlInfo);
				
				for(Object object : cateList)
				{
					Map tmpMap = new HashMap();
					tmpMap.put("fdId", PropertyUtils.getProperty(object, "fdId"));
					tmpMap.put("label", PropertyUtils.getProperty(object, "fdName"));
					
					if (PropertyUtils.isReadable(object, "fdParent")
							&& PropertyUtils.getProperty(object, "fdParent") != null) {
						Object parentInfos = PropertyUtils.getProperty(object, "fdParent");
						tmpMap.put("parentId", PropertyUtils.getProperty(parentInfos, "fdId"));
					}
					tmpMap.put("type", "CATEGORY");
					rtnList.add(tmpMap);
				}

			}
			request.setAttribute("lui-source", JSONArray.fromObject(rtnList)
					.toString());
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

	public ActionForward pathList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String cateId = request.getParameter("cateId");
			String modelName = request.getParameter("modelName");
			List rtnList = new ArrayList();
			if (StringUtil.isNotNull(cateId)) {
				SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
				IBaseService service = (IBaseService) SpringBeanUtil.getBean(dict.getServiceBean());
				String tableName = ModelUtil.getModelTableName(modelName);
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setModelName(modelName);
				hqlInfo.setWhereBlock(
						HQLUtil.buildLogicIN(tableName + ".fdId", ArrayUtil.convertArrayToList(cateId.split(";"))));
				List cateList = service.findValue(hqlInfo);

				if (cateList.size() > 0) {
					Object infos = (Object) cateList.get(0);
					Map tmpMap = new HashMap();
					tmpMap.put("fdId", PropertyUtils.getProperty(infos, "fdId"));
					tmpMap.put("label", PropertyUtils.getProperty(infos, "fdName"));
					rtnList.add(tmpMap);

					while (PropertyUtils.getProperty(infos, "fdParent") != null) {
						infos = PropertyUtils.getProperty(infos, "fdParent");
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("fdId", PropertyUtils.getProperty(infos, "fdId"));
						map.put("label", PropertyUtils.getProperty(infos, "fdName"));
						rtnList.add(map);
					}
				}
			}
			Collections.reverse(rtnList);
			request.setAttribute("lui-source", JSONArray.fromObject(rtnList).toString());
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}
	
	
	/**
	 * 权限数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward authData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();

		String authType = request.getParameter("authType");
		String modelName = request.getParameter("modelName");

		try {
			Boolean needRight = false;
			if ("02".equals(authType)) {
				needRight = true;
			} else if ("03".equals(authType) && ConfigUtil.auth(modelName)) {
				needRight = true;
			}
			JSONObject json = new JSONObject();
			if (!needRight) {
				json.element("authIds", "");
			} else {
				List<String> authIds = new ArrayList<>();
				IXMLDataBean sysSimpleCategoryAuthList = (IXMLDataBean) SpringBeanUtil
						.getBean("sysSimpleCategoryAuthList");
				List<Map<String, String>> authList = sysSimpleCategoryAuthList.getDataList(new RequestContext(request));
				if (authList != null && !authList.isEmpty()) {
					for (int i = 0; i < authList.size(); i++) {
						Map<String, String> tmpMap = (Map<String, String>) authList.get(i);
						authIds.add(tmpMap.get("v"));
					}
				}

				if (authIds != null && !authIds.isEmpty()) {
					json.element("authIds", StringUtil.join(authIds, ";"));
				} else {
					json.element("authIds", "");
				}
			}
			request.setAttribute("lui-source", json);
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

}
