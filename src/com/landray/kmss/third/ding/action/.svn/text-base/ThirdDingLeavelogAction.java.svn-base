package com.landray.kmss.third.ding.action;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.third.ding.forms.ThirdDingLeavelogForm;
import com.landray.kmss.third.ding.model.ThirdDingLeavelog;
import com.landray.kmss.third.ding.service.IThirdDingLeavelogService;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ThirdDingLeavelogAction extends ExtendAction {

    private IThirdDingLeavelogService thirdDingLeavelogService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingLeavelogService == null) {
            thirdDingLeavelogService = (IThirdDingLeavelogService) getBean("thirdDingLeavelogService");
        }
        return thirdDingLeavelogService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingLeavelog.class);
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		ThirdDingUtil.buildHqlInfoDate(hqlInfo, request,
				ThirdDingLeavelog.class);
		ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingLeavelogForm thirdDingLeavelogForm = (ThirdDingLeavelogForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingLeavelogService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingLeavelogForm;
    }

	/**
	 * 打开列表页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回list页面，否则返回failure页面
	 * @throws Exception
	 */
	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form,
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
			if (s_pageno != null && s_pageno.length() > 0
					&& Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil
							.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setWhereBlock(" fdTagName='请假'");
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
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
			return getActionForward("list", mapping, form, request, response);
		}
	}

	/**
	 * 查询列表JSON页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回data页面，否则返回failure页面
	 * @throws Exception
	 */
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
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0
					&& Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setWhereBlock(" fdTagName='请假'");
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());

			List<ThirdDingLeavelog> thirdDingLeavelogNew = putUserNameToThirdDingLeavelog(
					page.getList());
			page.setList(thirdDingLeavelogNew);
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
			return getActionForward("data", mapping, form, request, response);
		}
	}

	/**
	 * 请假失败同步
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resyncLeave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");
		try {
			String fdId = request.getParameter("fdId");
			boolean flag = thirdDingLeavelogService.updateLeaveSync(fdId);
			if (flag) {
				json.put("msg", "同步成功");
			} else {
				json.put("msg", "同步失败");
			}
		} catch (Exception e) {
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		return null;
	}

	/**
	 * 打开列表页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回list页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward bussList(ActionMapping mapping, ActionForm form,
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
			if (s_pageno != null && s_pageno.length() > 0
					&& Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil
							.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setWhereBlock(" fdTagName='外出'");
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
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
			return getActionForward("bussList", mapping, form, request,
					response);
		}
	}

	/**
	 * 查询列表JSON页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回data页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward bussData(ActionMapping mapping, ActionForm form,
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
			if (s_pageno != null && s_pageno.length() > 0
					&& Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setWhereBlock(" fdTagName='外出'");
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			List<ThirdDingLeavelog> thirdDingLeavelogNew = putUserNameToThirdDingLeavelog(
					page.getList());
			page.setList(thirdDingLeavelogNew);
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
			return getActionForward("bussData", mapping, form, request,
					response);
		}
	}

	/**
	 * 打开阅读页面。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回view页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward bussView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("bussView", mapping, form, request,
					response);
		}
	}

	/**
	 * 外出失败同步
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resyncBuss(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");
		try {
			String fdId = request.getParameter("fdId");
			boolean flag = thirdDingLeavelogService.updateBussSync(fdId);
			if (flag) {
				json.put("msg", "同步成功");
			} else {
				json.put("msg", "同步失败");
			}
		} catch (Exception e) {
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		return null;
	}

	/**
	 * 打开列表页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回list页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward tripList(ActionMapping mapping, ActionForm form,
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
			if (s_pageno != null && s_pageno.length() > 0
					&& Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil
							.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setWhereBlock(" fdTagName='出差'");
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
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
			return getActionForward("tripList", mapping, form, request,
					response);
		}
	}

	/**
	 * 查询列表JSON页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回data页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward tripData(ActionMapping mapping, ActionForm form,
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
			if (s_pageno != null && s_pageno.length() > 0
					&& Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setWhereBlock(" fdTagName='出差'");
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());

			// 把出差人放进列表中
			List<ThirdDingLeavelog> thirdDingLeavelogNew = putUserNameToThirdDingLeavelog(
					page.getList());
			page.setList(thirdDingLeavelogNew);
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
			return getActionForward("tripData", mapping, form, request,
					response);
		}
	}

	/**
	 * 打开列表页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回list页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward checkList(ActionMapping mapping, ActionForm form,
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
			if (s_pageno != null && s_pageno.length() > 0
					&& Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil
							.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setWhereBlock(" fdTagName='补卡'");
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
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
			return getActionForward("checkList", mapping, form, request,
					response);
		}
	}

	/**
	 * 查询列表JSON页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回data页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward checkData(ActionMapping mapping, ActionForm form,
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
			if (s_pageno != null && s_pageno.length() > 0
					&& Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setWhereBlock(" fdTagName='补卡'");
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);

			// 把补卡人放进列表中
			List<ThirdDingLeavelog> thirdDingLeavelogNew = putUserNameToThirdDingLeavelog(
					page.getList());
			page.setList(thirdDingLeavelogNew);

			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
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
			return getActionForward("checkData", mapping, form, request,
					response);
		}
	}

	/**
	 * 打开阅读页面。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回view页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward checkView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("checkView", mapping, form, request,
					response);
		}
	}

	/**
	 * 补卡失败同步
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resyncCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");
		try {
			String fdId = request.getParameter("fdId");
			boolean flag = thirdDingLeavelogService.updateCheckSync(fdId);
			if (flag) {
				json.put("msg", "同步成功");
			} else {
				json.put("msg", "同步失败");
			}
		} catch (Exception e) {
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		return null;
	}

	/**
	 * 打开列表页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回list页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward switchList(ActionMapping mapping, ActionForm form,
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
			if (s_pageno != null && s_pageno.length() > 0
					&& Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil
							.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setWhereBlock(" fdTagName='换班'");
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
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
			return getActionForward("switchList", mapping, form, request,
					response);
		}
	}

	/**
	 * 查询列表JSON页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回data页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward switchData(ActionMapping mapping, ActionForm form,
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
			if (s_pageno != null && s_pageno.length() > 0
					&& Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setWhereBlock(" fdTagName='换班'");
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			// 把换班人放进列表中
			List<ThirdDingLeavelog> thirdDingLeavelogNew = putUserNameToThirdDingLeavelog(
					page.getList());
			page.setList(thirdDingLeavelogNew);
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
			return getActionForward("switchData", mapping, form, request,
					response);
		}
	}

	/**
	 * 打开阅读页面。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回view页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward switchView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("switchView", mapping, form, request,
					response);
		}
	}

	/**
	 * 换班失败同步
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resyncSwitch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");
		try {
			String fdId = request.getParameter("fdId");
			boolean flag = thirdDingLeavelogService.updateSwitchSync(fdId);
			if (flag) {
				json.put("msg", "同步成功");
			} else {
				json.put("msg", "同步失败");
			}
		} catch (Exception e) {
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		return null;
	}

	/**
	 * 打开列表页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回list页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward overtimeList(ActionMapping mapping, ActionForm form,
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
			if (s_pageno != null && s_pageno.length() > 0
					&& Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil
							.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setWhereBlock(" fdTagName='加班'");
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
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
			return getActionForward("overtimeList", mapping, form, request,
					response);
		}
	}

	/**
	 * 
	 * 销假日志
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回list页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward cancelList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String cancelType = request.getParameter("cancelType");
			if (StringUtil.isNotNull(cancelType)) {
				request.setAttribute("cancelType", cancelType.trim());
			}

			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0
					&& Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil
							.getLangFieldName(modelClass.getName(), orderby);
					if (StringUtil.isNotNull(langFieldName)) {
						orderby = langFieldName;
					}
				}
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setWhereBlock(
					" fdTagName=:fdTagName and fdSubType=:fdSubType");
			hqlInfo.setParameter("fdTagName", "销假");
			hqlInfo.setParameter("fdSubType", cancelType);
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
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
			return getActionForward("cancelList", mapping, form, request,
					response);
		}
	}

	/**
	 * 查询列表JSON页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回data页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward overtimeData(ActionMapping mapping, ActionForm form,
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
			if (s_pageno != null && s_pageno.length() > 0
					&& Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setWhereBlock(" fdTagName='加班'");
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());

			// 把加班人放进列表中
			List<ThirdDingLeavelog> thirdDingLeavelogNew = putUserNameToThirdDingLeavelog(
					page.getList());
			page.setList(thirdDingLeavelogNew);
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
			return getActionForward("overtimeData", mapping, form, request,
					response);
		}
	}

	/**
	 * 查询销假列表JSON页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回data页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward cancelData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String cancelType = request.getParameter("cancelType");
		log.debug("cancelType before:" + cancelType);
		cancelType = getZHCancelType(cancelType);
		log.debug("cancelType:" + cancelType);

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
			if (s_pageno != null && s_pageno.length() > 0
					&& Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setWhereBlock(
					" fdTagName=:fdTagName and fdSubType=:fdSubType");
			hqlInfo.setParameter("fdTagName", "销假");
			hqlInfo.setParameter("fdSubType", cancelType);
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());

			// 把加班人放进列表中
			List<ThirdDingLeavelog> thirdDingLeavelogNew = putUserNameToThirdDingLeavelog(
					page.getList());
			page.setList(thirdDingLeavelogNew);
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
			return getActionForward("cancelData", mapping, form, request,
					response);
		}
	}

	private String getZHCancelType(String cancelType) {

		if (StringUtil.isNull(cancelType)) {
			return cancelType;
		}
		switch (cancelType) {
		case "leave":
			return "请假";
		case "business":
			return "外出";
		case "trip":
			return "出差";
		case "check":
			return "补卡";
		case "overtime":
			return "加班";
		case "switch":
			return "换班";
		default:
			return cancelType;
		}
	}

	/**
	 * 打开销假阅读页面。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回view页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward cancelView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("cancelView", mapping, form, request,
					response);
		}
	}

	/**
	 * 打开阅读页面。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回view页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward overtimeView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("overtimeView", mapping, form, request,
					response);
		}
	}

	/**
	 * 加班失败同步
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resyncOvertime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "成功");
		try {
			String fdId = request.getParameter("fdId");
			boolean flag = thirdDingLeavelogService.updateOvertimeSync(fdId);
			if (flag) {
				json.put("msg", "同步成功");
			} else {
				json.put("msg", "同步失败");
			}
		} catch (Exception e) {
			json.put("status", 0);
			json.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		return null;
	}

	// 将Ekp用户名放进列表中以展示
	private List<ThirdDingLeavelog>
			putUserNameToThirdDingLeavelog(
					List<ThirdDingLeavelog> thirdDingLeavelogs)
					throws Exception {

		List<ThirdDingLeavelog> thirdDingLeavelogNew = new ArrayList<ThirdDingLeavelog>();
		if (null != thirdDingLeavelogs && thirdDingLeavelogs.size() > 0) {
			String userId = null;
			ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
					.getBean("sysOrgPersonService");
			HQLInfo hqlInfo2 = new HQLInfo();
			hqlInfo2.setSelectBlock("fdName");
			String hql = "fdId = :userId";
			hqlInfo2.setWhereBlock(hql);
			for (int i = 0; i < thirdDingLeavelogs.size(); i++) {

				ThirdDingLeavelog thirdDingLeavelog = thirdDingLeavelogs
						.get(i);
				userId = thirdDingLeavelog.getFdEkpUserid();

				hqlInfo2.setParameter("userId", userId);
				String fdName = (String) sysOrgPersonService.findFirstOne(hqlInfo2);
				if (StringUtils.isNotBlank(fdName)) {
					thirdDingLeavelog.setFdEkpUsername(fdName);
				}
				thirdDingLeavelogNew.add(thirdDingLeavelog);
			}

		}
		return thirdDingLeavelogNew;
	}
}
