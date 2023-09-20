package com.landray.kmss.sys.time.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.time.forms.SysTimeCommonTimeForm;
import com.landray.kmss.sys.time.model.SysTimeCommonTime;
import com.landray.kmss.sys.time.service.ISysTimeCommonTimeService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽
 */
public class SysTimeCommonTimeAction extends ExtendAction {
	protected ISysTimeCommonTimeService sysTimeCommonTimeService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysTimeCommonTimeService == null) {
			sysTimeCommonTimeService = (ISysTimeCommonTimeService) getBean(
					"sysTimeCommonTimeService");
		}
		return sysTimeCommonTimeService;
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		SysTimeCommonTimeForm sysTimeCommonTimeForm = (SysTimeCommonTimeForm) form;
		SysTimeCommonTime commonTime = (SysTimeCommonTime) getServiceImp(
				request)
						.findByPrimaryKey(sysTimeCommonTimeForm.getFdId());
		sysTimeCommonTimeForm
				.setSysTimeWorkList(commonTime.getSysTimeWorkList());
		sysTimeCommonTimeForm
				.setSysTimePatchList(commonTime.getSysTimePatchwork());
		if (!messages.hasError()) {
			try {
				if (!"POST".equals(request.getMethod())) {
					throw new UnexpectedRequestException();
				}

				getServiceImp(request).update(sysTimeCommonTimeForm,
						new RequestContext(request));
			} catch (Exception e) {
				messages.addError(e);
			}
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	public void commonTimeById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		JSONArray ja = ((ISysTimeCommonTimeService) getServiceImp(request))
				.getCommonTime(request.getParameter("fdId"));
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(ja.toString());
	}

	public void getAreaByCommonId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		JSONArray ja = ((ISysTimeCommonTimeService) getServiceImp(request))
				.getRelevantAreas(request.getParameter("fdId"));
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(ja.toString());
	}

	@Override
	public ActionForward data(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String fdName = request.getParameter("q.fdName");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
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
			String whereBlock = hqlInfo.getWhereBlock();
			if (StringUtil.isNull(whereBlock)) {
				whereBlock = "1=1";
			}
			if (StringUtil.isNotNull(fdName)) {
				whereBlock += " and fdName like:fdName";
				hqlInfo.setParameter("fdName", "%" + fdName + "%");
			}
			whereBlock += " and type!='2'";
			hqlInfo.setWhereBlock(whereBlock);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
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
			return getActionForward("data", mapping, form, request, response);
		}
	}
}

