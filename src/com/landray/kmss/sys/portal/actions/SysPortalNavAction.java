package com.landray.kmss.sys.portal.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.portal.forms.SysPortalNavForm;
import com.landray.kmss.sys.portal.model.SysPortalMainPage;
import com.landray.kmss.sys.portal.model.SysPortalNav;
import com.landray.kmss.sys.portal.service.ISysPortalMainService;
import com.landray.kmss.sys.portal.service.ISysPortalNavService;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 系统导航 Action
 */
public class SysPortalNavAction extends ExtendAction {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	protected ISysPortalNavService sysPortalNavService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysPortalNavService == null) {
            sysPortalNavService = (ISysPortalNavService) getBean("sysPortalNavService");
        }
		return sysPortalNavService;
	}

	public ActionForward portlet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fdId = request.getParameter("fdId");
		JSONArray array = new JSONArray();
		if (StringUtil.isNotNull(fdId)) {
			SysPortalNav nav = (SysPortalNav) getServiceImp(request).findByPrimaryKey(fdId);
			if (nav != null) {
				array = JSONArray.fromObject(nav.getFdContent());
			}
		}
		KMSSUser kmssUser = UserUtil.getKMSSUser();
		String key = kmssUser.getLocale().getLanguage() + "-" + kmssUser.getLocale().getCountry();
		
		array = getLangJson(array ,key);
		
		//在这里统一处理系统导航的多语言转换
		request.setAttribute("lui-source", array);
		return getActionForward("lui-source", mapping, form, request, response);
	}
	
	//递归获得当前语言的系统导航text
	private JSONArray getLangJson(JSONArray array , String key){
		
		for(int i=0;i<array.size();i++){
			JSONObject obj = (JSONObject)array.get(i);
			if(obj.containsKey(key)){
				if(StringUtil.isNotNull(obj.getString(key))){
					obj.put("text", obj.getString(key));
				}
			}
			
			if(obj.containsKey("children")){
				JSONArray childArray = obj.getJSONArray("children");
				getLangJson(childArray,key);
			}
		}
		
		return array;
	}
	
	public ActionForward select(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String docSubject = request.getParameter("q.docSubject");
			HQLInfo hqlInfo = new HQLInfo();
			if(StringUtil.isNotNull(docSubject)){
				String where = "sysPortalNav.fdName like:docSubject";
				hqlInfo.setWhereBlock(where);
				hqlInfo.setParameter("docSubject", "%" + docSubject.trim() + "%");
			}
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("select", mapping, form, request, response);
		}
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		String where = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(where)) {
			where = " 1=1 ";
		}
		
		String fdAnonymous = request.getParameter("fdAnonymous");
		if (StringUtil.isNotNull(fdAnonymous)) {
			where += " AND sysPortalNav.fdAnonymous = :fdAnonymous ";
			hqlInfo.setParameter("fdAnonymous", "1".equals(fdAnonymous));
		}
		
		hqlInfo.setWhereBlock(where);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysPortalNav.class);
		if (StringUtil.isNotNull(request.getParameter("config"))) {
		  hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_EDITOR);
		}
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysPortalNavForm xform = (SysPortalNavForm) super.createNewForm(
				mapping, form, request, response);
		xform.setDocCreatorId(UserUtil.getUser().getFdId());
		xform.setDocCreatorName(UserUtil.getUser().getFdName());
		xform.setDocCreateTime(DateUtil.convertDateToString(new Date(),DateUtil.TYPE_DATETIME, request.getLocale()));
		String fdPageId = request.getParameter("fdPageId");
		if(StringUtil.isNotNull(fdPageId)){
			xform.setFdPageId(fdPageId);
		}
		/*
		 * 页面编辑处理子菜单维护时，
		 * 子菜单的属性就随页面属性，不能调整
		 * @author 吴进 by 20191128
		 */
		if ("1".equals(request.getParameter("switchAnonymous"))) {
			xform.setFdAnonymous(Boolean.TRUE);
		}
		return xform;
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward af = super.update(mapping, form, request, response);
		if (af.getName().endsWith("success")) {
			KmssReturnPage returnPage = KmssReturnPage.getInstance(request);
			List<List<String>> buttons = returnPage.getButtons();
			for (int i = 0; i < buttons.size(); i++) {
				List<String> ele = buttons.get(i);
				if (ele.contains("button.back")) {
					buttons.remove(i);
					break;
				}
			}
			returnPage.addButton("button.back",
					"sysPortalNav.do?method=edit&fdId="
							+ ((SysPortalNavForm) form).getFdId(),
					false);
		}
		return af;
	}
	
	
	/**
	 * 根据页面ID获取系统导航菜单树信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPortletNavByPageId(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fdPageId = request.getParameter("fdPageId");
		JSONObject resultObj = new JSONObject();
		if(StringUtil.isNotNull(fdPageId)){
			HQLInfo hqlInfo = new HQLInfo();
			
			StringBuffer whereBlockSb = new StringBuffer();
			whereBlockSb.append("sysPortalNav.fdPageId=:fdPageId ");
			
			hqlInfo.setWhereBlock(whereBlockSb.toString());
			hqlInfo.setParameter("fdPageId", fdPageId);
			
			Object obj = getServiceImp(request).findFirstOne(hqlInfo);
			SysPortalNav navModel = null;
			if(obj!=null){
				navModel = (SysPortalNav)obj;
				resultObj.put("fdId", navModel.getFdId());
				resultObj.put("fdName", navModel.getFdName());
				resultObj.put("fdContent", navModel.getFdContent());
			}
		}
		request.setAttribute("lui-source", resultObj);
		return getActionForward("lui-source", mapping, form, request, response);
	}
	
	
	/**
	 * 根据门户与页面中间表的fdId获取系统导航菜单树信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPortletNavByPortalPageId(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String portalPageId = request.getParameter("portalPageId");
		JSONObject resultObj = new JSONObject();
		if(StringUtil.isNotNull(portalPageId)){
			ISysPortalMainService service = (ISysPortalMainService) SpringBeanUtil.getBean("sysPortalMainService");
			SysPortalMainPage portalMainPage = service.getPortalPageById(portalPageId);
			
			HQLInfo hqlInfo = new HQLInfo();
			StringBuffer whereBlockSb = new StringBuffer();
			whereBlockSb.append("sysPortalNav.fdPageId=:fdPageId ");
			
			hqlInfo.setWhereBlock(whereBlockSb.toString());
			hqlInfo.setParameter("fdPageId", portalMainPage.getSysPortalPage().getFdId());
			
			Object obj = getServiceImp(request).findFirstOne(hqlInfo);
			SysPortalNav navModel = null;
			if(obj!=null){
				navModel = (SysPortalNav)obj;
				resultObj.put("fdId", navModel.getFdId());
				resultObj.put("fdName", navModel.getFdName());
				resultObj.put("fdContent", navModel.getFdContent());
			}
		}
		request.setAttribute("lui-source", resultObj);
		return getActionForward("lui-source", mapping, form, request, response);
	}
	
	/**
	 * 根据门户与页面中间表的fdId数组获取系统导航菜单树信息列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPortletNavByPortalPageIdArray(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONArray resultArray = new JSONArray();
		String portalPageIds = request.getParameter("portalPageIds");
		if(StringUtil.isNotNull(portalPageIds)){
			String[] portalPageIdArray = portalPageIds.split(",");
			ISysPortalMainService service = (ISysPortalMainService) SpringBeanUtil.getBean("sysPortalMainService");
			for(int i=0;i<portalPageIdArray.length;i++){
				JSONObject jsonObj = new JSONObject();
				String portalPageId = portalPageIdArray[i];
				SysPortalMainPage portalMainPage = service.getPortalPageById(portalPageId);
				
				HQLInfo hqlInfo = new HQLInfo();
				StringBuffer whereBlockSb = new StringBuffer();
				whereBlockSb.append("sysPortalNav.fdPageId=:fdPageId ");
				
				hqlInfo.setWhereBlock(whereBlockSb.toString());
				hqlInfo.setParameter("fdPageId", portalMainPage.getSysPortalPage().getFdId());
				
				Object obj = getServiceImp(request).findFirstOne(hqlInfo);
				SysPortalNav navModel = null;
				if(obj!=null){
					navModel = (SysPortalNav)obj;
					jsonObj.put("portalPageId", portalPageId);
					jsonObj.put("fdId", navModel.getFdId());
					jsonObj.put("fdName", navModel.getFdName());
					jsonObj.put("fdContent", navModel.getFdContent());
					resultArray.add(jsonObj);
				}
			}

		}
		request.setAttribute("lui-source", resultArray);
		return getActionForward("lui-source", mapping, form, request, response);
	}
	

	/**************** 匿名门户 Start @author 吴进 by 20191125 ******************************************/
	
	/**
	 * 页面编辑处理子菜单维护时，
	 * 子菜单的属性就随页面属性，不能调整
	 * @author 吴进 by 20191128
	 */
	@Override
	public ActionForward add(ActionMapping mapping, ActionForm form,
							 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 锁定switch控件
		if ("0".equals(request.getParameter("switchAnonymous")) || "1".equals(request.getParameter("switchAnonymous"))) {
			request.setAttribute("showType", "show");
		} else {
			request.setAttribute("showType", "edit");
		}
		return super.add(mapping, form, request, response);
	}
	
	
	
//	/**
//	 * 匿名新建
//	 * 
//	 * 打开新增页面。<br>
//	 * 该操作的大部分代码有具体业务逻辑由runAddAction实现，这里仅做错误以及页面跳转的处理。<br>
//	 * 该操作一般以HTTP的GET方式触发。
//	 * 
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return 执行成功，返回edit页面，否则返回failure页面
//	 * @throws Exception
//	 */
//	public ActionForward addAnonymous(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		TimeCounter.logCurrentTime("Action-add", true, getClass());
//		KmssMessages messages = new KmssMessages();
//		try {
//			ActionForm newForm = createNewForm(mapping, form, request, response);
//			if (newForm != form)
//				request.setAttribute(getFormName(newForm, request), newForm);
//		} catch (Exception e) {
//			messages.addError(e);
//			logger.error(e.getMessage(), e);
//		}
//
//		TimeCounter.logCurrentTime("Action-add", false, getClass());
//		if (messages.hasError()) {
//			KmssReturnPage.getInstance(request).addMessages(messages)
//					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
//			return getActionForward("failure", mapping, form, request, response);
//		} else {
//			return getActionForward("editAnonymous", mapping, form, request, response);
//		}
//	}
//	
//	/**
//	 * 匿名更新
//	 * 
//	 * 打开编辑页面。<br>
//	 * URL中必须包含fdId参数，该参数为记录id。<br>
//	 * 该操作一般以HTTP的GET方式触发。
//	 * 
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return 执行成功，返回edit页面，否则返回failure页面
//	 * @throws Exception
//	 */
//	public ActionForward editAnonymous(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		TimeCounter.logCurrentTime("Action-edit", true, getClass());
//		KmssMessages messages = new KmssMessages();
//		try {
//			loadActionForm(mapping, form, request, response);
//		} catch (Exception e) {
//			messages.addError(e);
//			logger.error(e.getMessage(), e);
//		}
//
//		TimeCounter.logCurrentTime("Action-edit", false, getClass());
//		if (messages.hasError()) {
//			KmssReturnPage.getInstance(request).addMessages(messages)
//					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
//			return getActionForward("failure", mapping, form, request, response);
//		} else {
//			return getActionForward("editAnonymous", mapping, form, request, response);
//		}
//	}
//	
//	/**************** 匿名门户 End @author 吴进 by 20191125 ******************************************/
}
