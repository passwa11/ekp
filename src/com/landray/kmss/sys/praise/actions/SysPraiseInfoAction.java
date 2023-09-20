package com.landray.kmss.sys.praise.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.praise.forms.SysPraiseInfoForm;
import com.landray.kmss.sys.praise.service.ISysPraiseInfoService;
import com.landray.kmss.sys.praise.service.ISysPraiseReplyConfigService;
import com.landray.kmss.sys.praise.util.SysPraiseInfoCommonUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import org.apache.commons.collections.CollectionUtils;

public class SysPraiseInfoAction extends ExtendAction {

	private ISysPraiseInfoService sysPraiseInfoService;

	@Override
	public ISysPraiseInfoService getServiceImp(HttpServletRequest request) {
		if (sysPraiseInfoService == null) {
			sysPraiseInfoService = (ISysPraiseInfoService) getBean("sysPraiseInfoService");
		}
		return sysPraiseInfoService;
	}

	private ISysAppConfigService sysAppConfigService;

	public ISysAppConfigService getSysAppConfigService() {
		if (sysAppConfigService == null) {
			sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
		}
		return sysAppConfigService;
	}

	private ISysPraiseReplyConfigService sysPraiseReplyConfigService;

	public ISysPraiseReplyConfigService getSysPraiseReplyConfigService() {
		if (sysPraiseReplyConfigService == null) {
			sysPraiseReplyConfigService = (ISysPraiseReplyConfigService) getBean("sysPraiseReplyConfigService");
		}
		return sysPraiseReplyConfigService;
	}

	private ISysOrgCoreService sysOrgCoreService;

	public ISysOrgCoreService getOrgService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	/**
	 * 查看自己相关的赞赏不用权限过滤
	 */
	public ActionForward myData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return this.data(mapping, form, request, response);
	}

	@Override
	public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String fdModelId = request.getParameter("fdModelId");
		String fdType = request.getParameter("q.fdType");
		String mydoc = request.getParameter("q.mydoc");
		String fdPraisePerson = request.getParameter("q.fdPraisePerson");
		String fdTargetPerson = request.getParameter("q.fdTargetPerson");
		String fdCategoryId = request.getParameter("categoryId");
		String fdSourceId = request.getParameter("fdSourceId");
		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(whereBlock)) {
			whereBlock = " 1=1 ";
		}
		if (StringUtil.isNotNull(fdModelId)) {
			whereBlock += " and  sysPraiseInfo.fdTargetId = :fdModelId";
			hqlInfo.setParameter("fdModelId", fdModelId);
		}
		if (StringUtil.isNotNull(fdSourceId)) {
			whereBlock += " and  sysPraiseInfo.fdSourceId = :fdSourceId";
			hqlInfo.setParameter("fdSourceId", fdSourceId);
		}
		if (StringUtil.isNotNull(fdType)) {
			whereBlock += " and  sysPraiseInfo.fdType = :fdType";
			hqlInfo.setParameter("fdType", Integer.parseInt(fdType));
		}
		if (StringUtil.isNotNull(mydoc)) {
			if ("create".equals(mydoc)) {
				whereBlock += " and  sysPraiseInfo.fdPraisePerson.fdId = :userId";
				hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
			} else if ("receive".equals(mydoc)) {
				whereBlock += " and  sysPraiseInfo.fdTargetPerson.fdId = :userId";
				hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
			}
		}
		if (StringUtil.isNotNull(fdPraisePerson)) {
			whereBlock += " and  sysPraiseInfo.fdPraisePerson.fdId = :fdPraisePerson";
			hqlInfo.setParameter("fdPraisePerson", fdPraisePerson);
			if (StringUtil.isNotNull(mydoc) && "receive".equals(mydoc)) {
				whereBlock += " and  sysPraiseInfo.fdIsHideName = :fdIsHideName";
				hqlInfo.setParameter("fdIsHideName", "false");
			}
		}
		if (StringUtil.isNotNull(fdTargetPerson)) {
			whereBlock += " and  sysPraiseInfo.fdTargetPerson.fdId = :fdTargetPerson";
			hqlInfo.setParameter("fdTargetPerson", fdTargetPerson);
		}
		if (StringUtil.isNotNull(fdCategoryId)) {
			whereBlock += " and  sysPraiseInfo.docCategory.fdHierarchyId like (:fdCategoryId)";
			hqlInfo.setParameter("fdCategoryId", "%" + fdCategoryId + "%");
		}
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy("sysPraiseInfo.docCreateTime DESC");
	}

	@Override
	public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									HttpServletResponse response) throws Exception {
		SysPraiseInfoForm sysPraiseInfoForm = (SysPraiseInfoForm) super.createNewForm(mapping, form, request, response);
		((ISysPraiseInfoService) getServiceImp(request)).initFormSetting((IExtendForm) form,
				new RequestContext(request));
		String fdModelId = request.getParameter("fdModelId");
		String fdModelName = request.getParameter("fdModelName");
		sysPraiseInfoForm.setFdTargetId(fdModelId);
		sysPraiseInfoForm.setFdTargetName(fdModelName);
		sysPraiseInfoForm.setFdIsHideName("false");
		sysPraiseInfoForm
				.setDocCreateTime(DateUtil.convertDateToString(new Date(),
						DateUtil.TYPE_DATETIME,
						UserUtil.getKMSSUser().getLocale()));
		sysPraiseInfoForm.setFdPraisePersonId(UserUtil.getUser().getFdId());
		sysPraiseInfoForm.setFdPraisePersonName(UserUtil.getUser().getFdName());
		String fdTargetPersonId = request.getParameter("fdPersonId");
		if (StringUtil.isNotNull(fdTargetPersonId)) {
			SysOrgElement orgElement = getOrgService().findByPrimaryKey(fdTargetPersonId);
			sysPraiseInfoForm.setFdTargetPersonName(orgElement.getFdName());
			sysPraiseInfoForm.setFdTargetPersonId(fdTargetPersonId);
		}
		List<String> list = getServiceImp(request).getExtendTypes();
		request.setAttribute("extendTypes", list);
		Boolean isReply = getSysPraiseReplyConfigService().isOpenReply();
		if (isReply != null && isReply) {
			sysPraiseInfoForm.setIsReply(String.valueOf(true));
		} else {
			sysPraiseInfoForm.setIsReply(String.valueOf(false));
		}
		return sysPraiseInfoForm;
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							  HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			SysPraiseInfoForm sysPraiseInfoForm = (SysPraiseInfoForm) form;
			if (!"2".equals(sysPraiseInfoForm.getFdType())) {
                sysPraiseInfoForm.setFdRich(null);
            }
			getServiceImp(request).add(sysPraiseInfoForm,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			SysPraiseInfoForm sysPraiseInfoForm = (SysPraiseInfoForm) form;
			sysPraiseInfoForm.reset(mapping, request);
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							  HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("view", mapping, form, request, response);
		}
	}

	public ActionForward listDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}

			// 按多语言字段排序
			if (StringUtil.isNotNull(orderby) && form instanceof IExtendForm) {
				Class<?> modelClass = ((IExtendForm) form).getModelClass();
				if (modelClass != null) {
					String langFieldName = SysLangUtil.getLangFieldName(modelClass.getName(), orderby);
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
			changeFindPageHQLInfo(request, hqlInfo);
			String whereBlock = hqlInfo.getWhereBlock();
			if (StringUtil.isNotNull(whereBlock)) {
				whereBlock += " and sysPraiseInfo.fdPraisePerson.fdId != sysPraiseInfo.fdTargetPerson.fdId";
			} else {
				whereBlock = " sysPraiseInfo.fdPraisePerson.fdId != sysPraiseInfo.fdTargetPerson.fdId";
			}
			Date limitedTime = getServiceImp(request).getLastExecuteTime();

			if (limitedTime != null) {
				whereBlock += " and sysPraiseInfo.docCreateTime < :limitedTime";
				hqlInfo.setParameter("limitedTime", limitedTime);
			}

			String fdTimeType = request.getParameter("fdTimeType");
			Date startTime = null;
			if (StringUtil.isNotNull(fdTimeType)) {
				if (fdTimeType.equals(SysPraiseInfoCommonUtil.WEEK)) {
					startTime = SysPraiseInfoCommonUtil.getWeekTime(limitedTime);
				} else if (fdTimeType.equals(SysPraiseInfoCommonUtil.MONTH)) {
					startTime = SysPraiseInfoCommonUtil.getMonthTime(limitedTime);
				} else if (fdTimeType.equals(SysPraiseInfoCommonUtil.YEAR)) {
					startTime = SysPraiseInfoCommonUtil.getNowYearDate(limitedTime);
				}
			}
			if (startTime != null) {
				whereBlock += " and sysPraiseInfo.docCreateTime >= :startTime";
				hqlInfo.setParameter("startTime", startTime);
			}

			hqlInfo.setWhereBlock(whereBlock);
			Page page = getServiceImp(request).findPage(hqlInfo);

			request.setAttribute("queryPage", page);

			UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listDetail", mapping, form, request, response);
		}
	}

	@Override
	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							  HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			super.loadActionForm(mapping, form, request, response);

			// 判断是否显示回复框
			SysPraiseInfoForm sysPraiseInfoForm = (SysPraiseInfoForm) form;
			Boolean isShowReplyContainer = false;
			if ("true".equals(sysPraiseInfoForm.getIsReply())) {
				if ("true".equals(sysPraiseInfoForm.getFdIsHideName())) {
					if (UserUtil.getKMSSUser().isAdmin() || UserUtil.checkRole("ROLE_SYSPRAISEINFO_MAINTAINER")) {
						if (UserUtil.getUser().getFdId().equals(sysPraiseInfoForm.getFdTargetPersonId())
								&& !StringUtil.isNotNull(sysPraiseInfoForm.getReplyContent())) {
							isShowReplyContainer = true;
						}
					}
				} else {
					if (StringUtil.isNull(sysPraiseInfoForm.getReplyContent())
							&& sysPraiseInfoForm.getFdTargetPersonId().equals(UserUtil.getUser().getFdId())) {
						isShowReplyContainer = true;
					}
				}
			}
			request.setAttribute("isShowReplyContainer", isShowReplyContainer);

			// 获得常用语
			if (isShowReplyContainer) {
				String replyText = getSysPraiseReplyConfigService().checkReplyText();
				List<String> replyTextList = new ArrayList<String>();
				if (StringUtil.isNotNull(replyText)) {
					String[] strArr = replyText.split("\n");
					for (int i = 0; i < strArr.length; i++) {
						replyTextList.add(strArr[i]);
					}
				}
				request.setAttribute("replyTextList", replyTextList);
				sysPraiseInfoForm.setReplyContent(CollectionUtils.isNotEmpty(replyTextList)?replyTextList.get(0):"");
			}

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("view", mapping, form, request, response);
		}
	}

	public ActionForward saveReply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).saveReply(request);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

}
