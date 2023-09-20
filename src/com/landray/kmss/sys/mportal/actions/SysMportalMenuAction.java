package com.landray.kmss.sys.mportal.actions;

import java.net.URLDecoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.mportal.forms.SysMportalMenuForm;
import com.landray.kmss.sys.mportal.model.SysMportalMenu;
import com.landray.kmss.sys.mportal.plugin.MportalLinkUtil;
import com.landray.kmss.sys.mportal.service.ISysMportalMenuService;
import com.landray.kmss.sys.mportal.xml.SysMportalMlink;
import com.landray.kmss.sys.person.actions.PageQuery;
import com.landray.kmss.sys.person.util.SelectPredicate;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

/**
 * 快捷配置 Action
 * 
 * @author
 * @version 1.0 2015-10-08
 */
public class SysMportalMenuAction extends ExtendAction {
	protected ISysMportalMenuService SysMportalMenuService;

	@Override
	protected ISysMportalMenuService getServiceImp(
			HttpServletRequest request) {
		if (SysMportalMenuService == null) {
            SysMportalMenuService = (ISysMportalMenuService) getBean("sysMportalMenuService");
        }
		return SysMportalMenuService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.createNewForm(mapping, form, request, response);
		SysMportalMenuForm menuForm = (SysMportalMenuForm) form;
		KMSSUser user = UserUtil.getKMSSUser();
		menuForm.setDocCreateTime(
				DateUtil.convertDateToString(new Date(), ResourceUtil.getString(
						"date.format.datetime", request.getLocale())));
		menuForm.setDocCreatorId(user.getUserId());
		menuForm.setDocCreatorName(user.getUserName());
		return form;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysMportalMenu.class);

		// 列表页过滤可编辑者
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck,
				SysAuthConstant.AuthCheck.SYS_EDITOR);
	}

	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = super.list(mapping, form, request, response);
		String contentType = request.getParameter("contentType");
		if (!"failure".equals(forward.getName()) && "json".equals(contentType)) {
			return getActionForward("data", mapping, form, request, response);
		}
		return forward;
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward select(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-select", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			
			String type = request.getParameter("q.type");
			List list = MportalLinkUtil.getLinkListByType(type);
			Collections.sort(list, new Comparator<SysMportalMlink>() {
				@Override
				public int compare(SysMportalMlink k1, SysMportalMlink k2) {
					if (k1.getMsgKey().compareTo(k2.getMsgKey()) > 0) {
						return -1;
					} else {
						return 1;
					}
				}
			});
			String key = request.getParameter("q.key");
			if(StringUtil.isNotNull(key)) {
				String searchText = URLDecoder.decode(key, "utf-8");
				list = new SelectPredicate(searchText).from(list);
			}
			PageQuery.findPage(request, list);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-select", false, getClass());

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return mapping.findForward("failure");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("select");
		}
	}
	
	
	/**
	 * 选择图标
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward icon(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-icon", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			request.setAttribute("clazs", getServiceImp(request).toClass());
			request.setAttribute("imgClass", getServiceImp(request).imgClass());
			request.setAttribute("iconClass", getServiceImp(request).iconClass());
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-icon", false, getClass());

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return mapping.findForward("failure");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("icon");
		}
	}

	/**
	 * 菜单选项数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward items(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-items", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			request.setAttribute("lui-source", getServiceImp(request)
					.toItemData(request));
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-items", false, getClass());

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return mapping.findForward("lui-failure");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("lui-source");
		}
	}

	/**
	 * 推送菜单到门户主页
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward enable(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-enable", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {

			if (!request.getMethod().equals("POST"))
				throw new UnexpectedRequestException();
			String id = request.getParameter("List_Selected");
			if (StringUtil.isNotNull(id)) {
				getServiceImp(request).updateEnable(id);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-enable", false, getClass());
		if (messages.hasError())
			return getActionForward("failure", mapping, form, request, response);
		else
			return getActionForward("success", mapping, form, request, response);
	}*/

	/**
	 * 选择菜单数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward menus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-menus", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			request.setAttribute("queryPage", this.getServiceImp(request)
					.findPage(this.buidPage(request)));
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-menus", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return mapping.findForward("failure");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("menus");
		}
	}

	private HQLInfo buidPage(HttpServletRequest request) {
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
		return hqlInfo;
	}
	
	public ActionForward isExist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-menus", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONObject obj = new JSONObject();
			String fdId = request.getParameter("fdId");
			if(StringUtil.isNotNull(fdId)) {
				obj.put("isExist", this.getServiceImp(request).getBaseDao()
						.isExist("com.landray.kmss.sys.mportal.model.SysMportalMenu", fdId));
			}
			request.setAttribute("lui-source", obj);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-menus", false, getClass());
		if (messages.hasError()) {
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}
}
