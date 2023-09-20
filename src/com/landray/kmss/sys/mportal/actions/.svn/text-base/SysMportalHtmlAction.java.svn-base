package com.landray.kmss.sys.mportal.actions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.mportal.forms.SysMportalHtmlForm;
import com.landray.kmss.sys.mportal.model.SysMportalHtml;
import com.landray.kmss.sys.mportal.service.ISysMportalHtmlService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;

/**
 * 自定义页面 Action
 * 
 */
public class SysMportalHtmlAction extends ExtendAction {
	protected ISysMportalHtmlService SysMportalHtmlService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (SysMportalHtmlService == null) {
            SysMportalHtmlService = (ISysMportalHtmlService)
                getBean("sysMportalHtmlService");
        }
		return SysMportalHtmlService;
	}

	public ActionForward html(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fdId = request.getParameter("htmlId");
		if (StringUtil.isNotNull(fdId)) {
			SysMportalHtml html = 
				(SysMportalHtml) getServiceImp(request).findByPrimaryKey(fdId);
			request.setAttribute("lui-text", html.getFdContent());
		}else{
			request.setAttribute("lui-text", "fdId参数为空");
		}
		return getActionForward("lui-text", mapping, form, request, response);
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysMportalHtml.class);
		
		//列表页过滤可编辑者
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, 
							SysAuthConstant.AuthCheck.SYS_EDITOR);
	}

	public ActionForward select(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-select", true, getClass());
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
			super.changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-select", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("data", mapping, form, request, response);
		}
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysMportalHtmlForm xform = (SysMportalHtmlForm) super.createNewForm(
				mapping, form, request, response);
		xform.setDocCreatorId(UserUtil.getUser().getFdId());
		xform.setDocCreatorName(UserUtil.getUser().getFdName());
		xform.setDocCreateTime(DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, request.getLocale()));
		return xform;
	}
}
