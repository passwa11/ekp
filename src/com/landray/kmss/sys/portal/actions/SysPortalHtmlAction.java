package com.landray.kmss.sys.portal.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.portal.forms.SysPortalHtmlForm;
import com.landray.kmss.sys.portal.model.SysPortalHtml;
import com.landray.kmss.sys.portal.service.ISysPortalHtmlService;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 自定义页面 Action
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class SysPortalHtmlAction extends ExtendAction {
	protected ISysPortalHtmlService sysPortalHtmlService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysPortalHtmlService == null) {
            sysPortalHtmlService = (ISysPortalHtmlService) getBean("sysPortalHtmlService");
        }
		return sysPortalHtmlService;
	}

	public ActionForward html(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fdId = request.getParameter("fdId");
		if (StringUtil.isNotNull(fdId)) {
			SysPortalHtml html = (SysPortalHtml) getServiceImp(request).findByPrimaryKey(fdId,SysPortalHtml.class,true);
			if(html != null){
				request.setAttribute("lui-text", html.getFdContent());
			}else{
				request.setAttribute("lui-text", "该部件未配置内容");
			}

		} else {
			HQLInfo info = new HQLInfo();
			Object obj = getServiceImp(request).findFirstOne(info);
			if (obj != null) {
				SysPortalHtml html = (SysPortalHtml) obj;
				request.setAttribute("lui-text", html.getFdContent());
			} else {
				request.setAttribute("lui-text", "fdId参数为空");
			}
		}
		return getActionForward("lui-text", mapping, form, request, response);
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
				String where = "sysPortalHtml.fdName like:docSubject";
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
			where += " AND sysPortalHtml.fdAnonymous = :fdAnonymous ";
			hqlInfo.setParameter("fdAnonymous", "1".equals(fdAnonymous));
		}
		
		hqlInfo.setWhereBlock(where);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysPortalHtml.class);
	    if (StringUtil.isNotNull(request.getParameter("config"))) {
		  hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_EDITOR);
		}
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysPortalHtmlForm xform = (SysPortalHtmlForm) super.createNewForm(
				mapping, form, request, response);
		xform.setDocCreatorId(UserUtil.getUser().getFdId());
		xform.setDocCreatorName(UserUtil.getUser().getFdName());
		xform.setDocCreateTime(DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, request.getLocale()));
		return xform;
	}
}
