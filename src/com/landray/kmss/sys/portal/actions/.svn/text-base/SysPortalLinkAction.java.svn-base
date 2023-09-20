package com.landray.kmss.sys.portal.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.portal.forms.SysPortalLinkForm;
import com.landray.kmss.sys.portal.model.SysPortalLink;
import com.landray.kmss.sys.portal.model.SysPortalLinkDetail;
import com.landray.kmss.sys.portal.service.ISysPortalLinkService;
import com.landray.kmss.sys.portal.util.SysPortalConfig;
import com.landray.kmss.sys.ui.util.SysUiConstant;
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
 * 快捷方式 Action
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class SysPortalLinkAction extends ExtendAction {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	protected ISysPortalLinkService sysPortalLinkService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysPortalLinkService == null) {
            sysPortalLinkService = (ISysPortalLinkService) getBean("sysPortalLinkService");
        }
		return sysPortalLinkService;
	}

	public ActionForward portlet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String fdId = request.getParameter("fdId");
			String fdType = request.getParameter("fdType");
			JSONArray array = new JSONArray();
			
			if (StringUtil.isNotNull(fdId)) {
				SysPortalLink link = (SysPortalLink) getServiceImp(request).findByPrimaryKey(fdId,SysPortalLink.class,true);
				if(link != null) {
					List<SysPortalLinkDetail> links = link.getFdLinks();
					if ("1".equals(fdType)) {
						// 常用连接
						for (int i = 0; i < links.size(); i++) {
							JSONObject json = new JSONObject();
							json.put("text", links.get(i).getFdName());
							json.put("target", links.get(i).getFdTarget());
							if (StringUtil.isNotNull(links.get(i).getFdSysLink())) {
								String xid = links.get(i).getFdSysLink();
								if (xid.indexOf(SysUiConstant.SEPARATOR) > 0) {
									String server = xid.substring(0, xid.indexOf(SysUiConstant.SEPARATOR));
									json.put("href", SysPortalConfig.getServerUrl(server) + links.get(i).getFdUrl());
								} else {
									json.put("href", links.get(i).getFdUrl());
								}
							} else {
								json.put("href", links.get(i).getFdUrl());
							}
							array.add(json);
						}
					} else if ("2".equals(fdType)) {
						// 快捷方式
						for (int i = 0; i < links.size(); i++) {
							JSONObject json = new JSONObject();
							json.put("text", links.get(i).getFdName());
							json.put("target", links.get(i).getFdTarget());
							if (StringUtil.isNotNull(links.get(i).getFdSysLink())) {
								String xid = links.get(i).getFdSysLink();
								if (xid.indexOf(SysUiConstant.SEPARATOR) > 0) {
									String server = xid.substring(0, xid.indexOf(SysUiConstant.SEPARATOR));
									json.put("href", SysPortalConfig.getServerUrl(server) + links.get(i).getFdUrl());
								} else {
									json.put("href", links.get(i).getFdUrl());
								}
							} else {
								json.put("href", links.get(i).getFdUrl());
							}
							//如果icon为空则是自定义的图片
							if(StringUtil.isNull(links.get(i).getFdIcon())){
								json.put("img", links.get(i).getFdImg());
							}else {
								json.put("icon", links.get(i).getFdIcon());
							}
							array.add(json);
						}
					}
				}
			} else {
				if ("1".equals(fdType)) {
					HQLInfo info = new HQLInfo();
					info.setWhereBlock(" fdType = :fdType ");
					info.setParameter("fdType", "1");
					Object obj = getServiceImp(request).findFirstOne(info);
					if (obj != null) {
						SysPortalLink link = (SysPortalLink) obj;
						List<SysPortalLinkDetail> links = link.getFdLinks();
						for (int i = 0; i < links.size(); i++) {
							JSONObject json = new JSONObject();
							json.put("text", links.get(i).getFdName());
							json.put("target", links.get(i).getFdTarget());
							if(StringUtil.isNotNull(links.get(i).getFdSysLink())){
								String xid = links.get(i).getFdSysLink();
								if(xid.indexOf(SysUiConstant.SEPARATOR)>0){
									String server = xid.substring(0,xid.indexOf(SysUiConstant.SEPARATOR));
									json.put("href", SysPortalConfig.getServerUrl(server)+links.get(i).getFdUrl());
								}else{
									json.put("href", links.get(i).getFdUrl());
								}
							}else{
								json.put("href", links.get(i).getFdUrl());
							}
							array.add(json);
						}
					}
				} else if ("2".equals(fdType)) {
					HQLInfo info = new HQLInfo();
					info.setWhereBlock(" fdType = 2 ");
					Object obj = getServiceImp(request).findList(info);
					if (obj != null) {
						SysPortalLink link = (SysPortalLink)obj;
						List<SysPortalLinkDetail> links = link.getFdLinks();
						for (int i = 0; i < links.size(); i++) {
							JSONObject json = new JSONObject();
							json.put("text", links.get(i).getFdName());
							json.put("target", links.get(i).getFdTarget());
							if(StringUtil.isNotNull(links.get(i).getFdSysLink())){
								String xid = links.get(i).getFdSysLink();
								if(xid.indexOf(SysUiConstant.SEPARATOR)>0){
									String server = xid.substring(0,xid.indexOf(SysUiConstant.SEPARATOR));
									json.put("href", SysPortalConfig.getServerUrl(server)+links.get(i).getFdUrl());
								}else{
									json.put("href", links.get(i).getFdUrl());
								}
							}else{
								json.put("href", links.get(i).getFdUrl());
							}
							json.put("icon", links.get(i).getFdIcon());
							json.put("img", links.get(i).getFdImg());
							array.add(json);
						}
					}
				}
			}
			request.setAttribute("lui-source", array);
			return getActionForward("lui-source", mapping, form, request, response);
		} catch (Exception e) {
			KmssMessages messages = new KmssMessages();
			messages.addError(e);
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("lui-failure", mapping, form, request, response);
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
			String docSubject = request.getParameter("q.docSubject");
			HQLInfo hqlInfo = new HQLInfo();
			if(StringUtil.isNotNull(docSubject)){
				String where = "sysPortalLink.fdName like:docSubject";
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
		String type = request.getParameter("fdType");
		if (StringUtil.isNotNull(type)) {
			where += " and sysPortalLink.fdType = :fdType ";
			hqlInfo.setParameter("fdType", type);
		}

		hqlInfo.setWhereBlock(where);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysPortalLink.class);
		if (StringUtil.isNotNull(request.getParameter("config"))) {
		  hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_EDITOR);
		}
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysPortalLinkForm xform = (SysPortalLinkForm) super.createNewForm(
				mapping, form, request, response);
		if (request.getParameter("fdType") != null) {
			xform.setFdType(request.getParameter("fdType"));
		}
		xform.setDocCreatorId(UserUtil.getUser().getFdId());
		xform.setDocCreatorName(UserUtil.getUser().getFdName());
		xform.setDocCreateTime(DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, request.getLocale()));
		xform.setFdType(request.getParameter("fdType"));
		return xform;
	}

//	/**************** 匿名 Start ****************************************************************/
//	/**
//	 * 因为兼容老数据，匿名字段为空需要特殊加0处理
//	 * @author 吴进 by 2019120
//	 * 
//	 * 打开列表页面。<br>
//	 * 该操作一般以HTTP的GET方式触发。
//	 * 
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return 执行成功，返回list页面，否则返回failure页面
//	 * @throws Exception
//	 */
//	public ActionForward getPortalLinkList(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		TimeCounter.logCurrentTime("Action-list", true, getClass());
//		KmssMessages messages = new KmssMessages();
//		try {
//			String s_pageno = request.getParameter("pageno");
//			String s_rowsize = request.getParameter("rowsize");
//			String orderby = request.getParameter("orderby");
//			String ordertype = request.getParameter("ordertype");
//			boolean isReserve = false;
//			if (ordertype != null && ordertype.equalsIgnoreCase("down")) {
//				isReserve = true;
//			}
//			int pageno = 0;
//			int rowsize = SysConfigParameters.getRowSize();
//			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
//				pageno = Integer.parseInt(s_pageno);
//			}
//			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
//				rowsize = Integer.parseInt(s_rowsize);
//			}
//
//			// 按多语言字段排序
//			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
//				Class<?> modelClass = ((IExtendForm) form).getModelClass();
//				if (modelClass != null) {
//					String langFieldName = SysLangUtil
//							.getLangFieldName(modelClass.getName(), orderby);
//					if (StringUtil.isNotNull(langFieldName)) {
//						orderby = langFieldName;
//					}
//				}
//			}
//			if (isReserve)
//				orderby += " desc";
//			HQLInfo hqlInfo = new HQLInfo();
//			hqlInfo.setOrderBy(orderby);
//			hqlInfo.setPageNo(pageno);
//			hqlInfo.setRowSize(rowsize);
//			changeFindPageHQLInfo(request, hqlInfo);
//			Page page = getServiceImp(request).findPage(hqlInfo);
//			if (page != null && page.getList() != null && !page.getList().isEmpty()) {
//				List list = page.getList();
//				for (int i = 0, n = list.size(); i < n; i++) {
//					Object object = list.get(i);
//					if (object instanceof SysPortalLink) {
//						SysPortalLink sysPortalLink = (SysPortalLink) object;
//						String anonymous = sysPortalLink.getFdAnonymous();
//						sysPortalLink.setFdAnonymous(StringUtil.isNotNull(anonymous) ? anonymous : "0");
//					}
//				}
//			}
//			UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
//			request.setAttribute("queryPage", page);
//		} catch (Exception e) {
//			messages.addError(e);
//		}
//
//		TimeCounter.logCurrentTime("Action-list", false, getClass());
//		if (messages.hasError()) {
//			KmssReturnPage.getInstance(request).addMessages(messages)
//					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
//			return getActionForward("failure", mapping, form, request, response);
//		} else {
//			return getActionForward("list", mapping, form, request, response);
//		}
//	}
//	
//	/**
//	 * 新建匿名页面
//	 * 
//	 * @author 吴进 by 20191115
//	 * 
//	 *         打开新增页面。<br>
//	 *         该操作的大部分代码有具体业务逻辑由runAddAction实现，这里仅做错误以及页面跳转的处理。<br>
//	 *         该操作一般以HTTP的GET方式触发。
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
//			ActionForm newForm = createNewForm(mapping, form, request,
//					response);
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
//			return getActionForward("failure", mapping, form, request,
//					response);
//		} else {
//			return getActionForward("editAnonymous", mapping, form, request,
//					response);
//		}
//	}
//
//	/**
//	 * 编辑匿名页面
//	 * 
//	 * @author 吴进 by 20191115
//	 * 
//	 *         打开编辑页面。<br>
//	 *         URL中必须包含fdId参数，该参数为记录id。<br>
//	 *         该操作一般以HTTP的GET方式触发。
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
//		}
//
//		TimeCounter.logCurrentTime("Action-edit", false, getClass());
//		if (messages.hasError()) {
//			KmssReturnPage.getInstance(request).addMessages(messages)
//					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
//			return getActionForward("failure", mapping, form, request,
//					response);
//		} else {
//			return getActionForward("editAnonymous", mapping, form, request,
//					response);
//		}
//	}
//	
//	/**************** 匿名 End ****************************************************************/
}
