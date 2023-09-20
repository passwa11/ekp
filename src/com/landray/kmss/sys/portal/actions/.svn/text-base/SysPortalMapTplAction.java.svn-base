package com.landray.kmss.sys.portal.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.portal.forms.SysPortalMapTplForm;
import com.landray.kmss.sys.portal.model.SysPortalMapTpl;
import com.landray.kmss.sys.portal.model.SysPortalNav;
import com.landray.kmss.sys.portal.service.ISysPortalMapTplService;
import com.landray.kmss.sys.portal.service.ISysPortalNavService;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.query.NativeQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

public class SysPortalMapTplAction extends ExtendAction {

    private ISysPortalMapTplService sysPortalMapTplService;
	private ISysPortalNavService sysPortalNavService;
    @Override
	public ISysPortalMapTplService getServiceImp(HttpServletRequest request) {
        if (sysPortalMapTplService == null) {
            sysPortalMapTplService = (ISysPortalMapTplService) getBean("sysPortalMapTplService");
        }
        return sysPortalMapTplService;
    }

	public IBaseService getNavServiceImp(HttpServletRequest request) {
		if (sysPortalNavService == null) {
			sysPortalNavService = (ISysPortalNavService) getBean(
					"sysPortalNavService");
		}
		return sysPortalNavService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		String where = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(where)) {
			where = " 1=1 ";
		}
		hqlInfo.setWhereBlock(where);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysPortalMapTpl.class);
	    if (StringUtil.isNotNull(request.getParameter("config"))) {
		  hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_EDITOR);
		}
	}

    @Override
	public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysPortalMapTplForm sysPortalMapTplForm = (SysPortalMapTplForm) super.createNewForm(mapping, form, request, response);
        ((ISysPortalMapTplService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        sysPortalMapTplForm.setFdIsCustom(String.valueOf(true));
        return sysPortalMapTplForm;
    }

	/**
	 * 打开新增页面。<br>
	 * 该操作的大部分代码有具体业务逻辑由runAddAction实现，这里仅做错误以及页面跳转的处理。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回edit页面，否则返回failure页面
	 * @throws Exception
	 */
	@Override
	public ActionForward add(ActionMapping mapping, ActionForm form,
							 HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		boolean bol = new File(PluginConfigLocationsUtil.getKmssConfigPath()
				+ "/kms/kmaps").exists();
		if (bol) {
			response.setHeader("content-type", "text/html;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(ResourceUtil
					.getString("sysPortalMapTpl.response.msg", "sys-portal"));
			return null;
		} else {
			return super.add(mapping, form, request, response);
		}

	}

	public ActionForward portlet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String fdId = request.getParameter("fdId");
			JSONArray arrayData = new JSONArray();
			
			if (StringUtil.isNotNull(fdId)) {

				SysPortalMapTpl tpl = (SysPortalMapTpl) getServiceImp(request)
						.findByPrimaryKey(fdId, SysPortalMapTpl.class, true);
				//modify by wangjf 20210624 加入判空操作
				if (tpl != null) {
					// 自定义数据
					if (tpl.getFdIsCustom() != null && tpl.getFdIsCustom()) {
						arrayData.add(
								getServiceImp(request).getCustomDatasByTpl(tpl));
					} else {
						// 引用系统导航
						arrayData.add(getServiceImp(request).getDatasByTpl(tpl));
					}

					JSONArray inlet = getInletList(request, fdId);
					arrayData.add(inlet);
				}
			}
			
			request.setAttribute("lui-source", arrayData);
			return getActionForward("lui-source", mapping, form, request,
					response);
		} catch (Exception e) {
			KmssMessages messages = new KmssMessages();
			messages.addError(e);
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("lui-failure", mapping, form, request,
					response);
		}
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
			HQLInfo hqlInfo = new HQLInfo();
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
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("select", mapping, form, request, response);
		}
	}

	public ActionForward getSysNavlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {

			/*
			 * String sysNavIds = request.getParameter("sysNavIds"); String[]
			 * sourceStr = sysNavIds.split(";");
			 */
			/*
			 * List list = getNavServiceImp(request).getBaseDao()
			 * .findByPrimaryKeys(sourceStr);
			 */
			String fdId = request.getParameter("fdId");
			HQLInfo info = new HQLInfo();
			info.setSelectBlock(
					"fdPortalNav.fdId,fdPortalNav.fdName");
			info.setJoinBlock(
					"left join sysPortalMapTpl.fdPortalNav fdPortalNav");
			info.setWhereBlock("sysPortalMapTpl.fdId=:fdId");
			info.setParameter("fdId", fdId);
			List list = getServiceImp(request).findList(info);
			JSONArray array = new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				Object[] listArr = (Object[]) list.get(i);
				JSONObject jsonObject = new JSONObject();
				jsonObject.accumulate("fdName", listArr[1]);
				jsonObject.accumulate("fdId", listArr[0]);
				array.add(jsonObject);
			}
			request.setAttribute("lui-source", array);
			return getActionForward("lui-source", mapping, form, request,
					response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("select", mapping, form, request, response);
		}
	}

	public ActionForward getInletInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		KmssMessages messages = new KmssMessages();
		try {
			String fdId = request.getParameter("fdId");

			request.setAttribute("lui-source", getInletList(request, fdId));
			return getActionForward("lui-source", mapping, form, request,
					response);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("select", mapping, form, request, response);
		}
	}

	private JSONArray getInletList(HttpServletRequest request, String fdId)
			throws Exception {
		String sql = "select fd_name,fd_url from sys_portal_map_inlet where fd_tpl_id =?";
		NativeQuery query = getServiceImp(request).getBaseDao()
				.getHibernateSession().createNativeQuery(sql);
		query.setString(0, fdId);
		List list = query.list();
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			Object[] arr = (Object[]) list.get(i);
			JSONObject jsonobject = new JSONObject();
			jsonobject.accumulate("text", arr[0]);
			jsonobject.accumulate("href", arr[1]);
			array.add(jsonobject);
		}


		return array;
	}

	public ActionForward preView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		request.getRequestDispatcher("");
		return null;
	}

	public ActionForward getSysNavInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray array = new JSONArray();
			String fdIds = request.getParameter("fdIds");
			String[] str = null;
			List nav = null;
			if (!"".equals(fdIds)) {
				str = fdIds.split(";");
				nav = getNavServiceImp(request)
						.getBaseDao()
						.findByPrimaryKeys(str);
				for (int i = 0; i < nav.size(); i++) {
					SysPortalNav list = (SysPortalNav) nav.get(i);
					JSONObject jsonobj = new JSONObject();
					jsonobj.accumulate("name", list.getFdName());
					
					KMSSUser kmssUser = UserUtil.getKMSSUser();
					String key = kmssUser.getLocale().getLanguage() + "-" + kmssUser.getLocale().getCountry();
					
					JSONArray arr = JSONArray.fromObject(list.getFdContent());
					
					arr = getLangJson(arr,key);
					
					jsonobj.accumulate("content", arr);
					
					array.add(jsonobj);
				}
			}
			request.setAttribute("lui-source", array);
			return getActionForward("lui-source", mapping, form, request,
					response);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("select", mapping, form, request, response);
		}
	}
	
	//递归获得当前语言的系统导航text
	private net.sf.json.JSONArray getLangJson(net.sf.json.JSONArray array , String key){
		
		for(int i=0;i<array.size();i++){
			JSONObject obj = (JSONObject)array.get(i);
			if(obj.containsKey(key)){
				if(StringUtil.isNotNull(obj.getString(key))){
					obj.put("text", obj.getString(key));
				}
			}
			
			if(obj.containsKey("children")){
				net.sf.json.JSONArray childArray = obj.getJSONArray("children");
				getLangJson(childArray,key);
			}
		}
		
		return array;
	}
}
