package com.landray.kmss.km.archives.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.archives.model.KmArchivesDestroy;
import com.landray.kmss.km.archives.model.KmArchivesDestroyDetails;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.km.archives.service.IKmArchivesAppraiseDetailsService;
import com.landray.kmss.km.archives.service.IKmArchivesAppraiseService;
import com.landray.kmss.km.archives.service.IKmArchivesCategoryService;
import com.landray.kmss.km.archives.service.IKmArchivesDestroyDetailsService;
import com.landray.kmss.km.archives.service.IKmArchivesDestroyService;
import com.landray.kmss.km.archives.service.IKmArchivesMainService;
import com.landray.kmss.km.archives.util.KmArchivesUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;

public class KmArchivesDestroyOptionAction extends ExtendAction {

    private IKmArchivesMainService kmArchivesMainService;

	@Override
	public IKmArchivesMainService getServiceImp(HttpServletRequest request) {
        if (kmArchivesMainService == null) {
            kmArchivesMainService = (IKmArchivesMainService) getBean("kmArchivesMainService");
        }
        return kmArchivesMainService;
    }

	private IKmArchivesCategoryService kmArchivesCategoryService;

	public IKmArchivesCategoryService getCategoryServiceImp() {
		if (kmArchivesCategoryService == null) {
			kmArchivesCategoryService = (IKmArchivesCategoryService) getBean(
					"kmArchivesCategoryService");
		}
		return kmArchivesCategoryService;
	}
	
	private IKmArchivesAppraiseService kmArchivesAppraiseService;

	public IKmArchivesAppraiseService getKmArchivesAppraiseService() {
		if (kmArchivesAppraiseService == null) {
			kmArchivesAppraiseService = (IKmArchivesAppraiseService) getBean(
					"kmArchivesAppraiseService");
		}
		return kmArchivesAppraiseService;
	}

	private IKmArchivesAppraiseDetailsService kmArchivesAppraiseDetailsService;

	public IKmArchivesAppraiseDetailsService
			getKmArchivesAppraiseDetailsService() {
		if (kmArchivesAppraiseDetailsService == null) {
			kmArchivesAppraiseDetailsService = (IKmArchivesAppraiseDetailsService) getBean(
					"kmArchivesAppraiseDetailsService");
		}
		return kmArchivesAppraiseDetailsService;
	}

	private IKmArchivesDestroyService kmArchivesDestroyService;

	public IKmArchivesDestroyService getKmArchivesDestroyService() {
		if (kmArchivesDestroyService == null) {
			kmArchivesDestroyService = (IKmArchivesDestroyService) getBean(
					"kmArchivesDestroyService");
		}
		return kmArchivesDestroyService;
	}

	private IKmArchivesDestroyDetailsService kmArchivesDestroyDetailsService;

	public IKmArchivesDestroyDetailsService
			getKmArchivesDestroyDetailsService() {
		if (kmArchivesDestroyDetailsService == null) {
			kmArchivesDestroyDetailsService = (IKmArchivesDestroyDetailsService) getBean(
					"kmArchivesDestroyDetailsService");
		}
		return kmArchivesDestroyDetailsService;
	}

    @Override
	public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		CriteriaValue cv = new CriteriaValue(request);
		String whereBlock = hqlInfo.getWhereBlock();
		String fdCategoryId = request.getParameter("categoryId");
		String keywords = request.getParameter("keywords");
		request.setAttribute("fdCategoryId", fdCategoryId);
		if (StringUtil.isNotNull(fdCategoryId)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" kmArchivesMain.docTemplate.fdId = :fdCategoryId ");
			hqlInfo.setParameter("fdCategoryId", fdCategoryId);
		}
		if (StringUtil.isNotNull(keywords)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"(kmArchivesMain.docSubject like :docSubject or kmArchivesMain.docNumber like :docNumber)");
			hqlInfo.setParameter("docSubject", "%" + keywords + "%");
			hqlInfo.setParameter("docNumber", "%" + keywords + "%");
		}

		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"(kmArchivesMain.fdDestroyed = false)");
		// // 过期范围
		// String expireRange = new KmArchivesConfig().getFdSoonExpireDate();
		// int range = StringUtil.isNull(expireRange) ? 0
		// : Integer.parseInt(expireRange);

		// if (range == 0) {// 没用设置过期范围 ，查找已过期数据
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"(kmArchivesMain.fdValidityDate <= :fdValidityDate)");
			hqlInfo.setParameter("fdValidityDate", new Date());
		// } else {
		// Calendar cal = Calendar.getInstance();
		// cal.add(Calendar.DATE, range);
		// whereBlock = StringUtil.linkString(whereBlock, " and ",
		// " ((kmArchivesMain.fdValidityDate > :minValidityDate and
		// kmArchivesMain.fdValidityDate < :maxValidityDate) or
		// (kmArchivesMain.fdValidityDate <= :fdValidityDate))");
		// hqlInfo.setParameter("minValidityDate", new Date());
		// hqlInfo.setParameter("maxValidityDate", cal.getTime());
		// hqlInfo.setParameter("fdValidityDate", new Date());
		// }

		// 未审批通过的不能进行鉴定和销毁
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"(kmArchivesMain.docStatus = :docStatus)");
		hqlInfo.setParameter("docStatus", SysDocConstant.DOC_STATUS_PUBLISH);
		// 查询只显示最新的版本
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"kmArchivesMain.docIsNewVersion = '1'");
		// 查询未鉴定中的档案
		HQLInfo appInfo = new HQLInfo();
		appInfo.setSelectBlock("kmArchivesAppraise.fdId");
		appInfo.setWhereBlock(
				"kmArchivesAppraise.docTemplate is not null and kmArchivesAppraise.docStatus not in ('00','30')");
		List appList = getKmArchivesAppraiseService().findList(appInfo);
		if (appList != null && appList.size() > 0) {
			HQLInfo detailInfo = new HQLInfo();
			detailInfo.setSelectBlock(
					"kmArchivesAppraiseDetails.fdArchives.fdId");
			detailInfo.setWhereBlock(HQLUtil.buildLogicIN(
					"kmArchivesAppraiseDetails.docMain.fdId", appList)
					+ " and kmArchivesAppraiseDetails.fdArchives is not null ");
			List valueList = getKmArchivesAppraiseDetailsService()
					.findList(detailInfo);
			if (valueList != null && valueList.size() > 0) {
				whereBlock = StringUtil.linkString(whereBlock, " and not ",
						HQLUtil.buildLogicIN("kmArchivesMain.fdId", valueList));
			}
		}
		// 查询未销毁中的档案
		HQLInfo destroyInfo = new HQLInfo();
		destroyInfo.setSelectBlock("kmArchivesDestroy.fdId");
		destroyInfo.setWhereBlock(
				"kmArchivesDestroy.docTemplate is not null and kmArchivesDestroy.docStatus not in ('00','30')");
		List destroyList = getKmArchivesDestroyService().findList(destroyInfo);
		if (destroyList != null && destroyList.size() > 0) {
			HQLInfo detailInfo = new HQLInfo();
			detailInfo.setSelectBlock(
					"kmArchivesDestroyDetails.fdArchives.fdId");
			detailInfo.setWhereBlock(HQLUtil.buildLogicIN(
					"kmArchivesDestroyDetails.docMain.fdId", destroyList)
					+ " and kmArchivesDestroyDetails.fdArchives is not null ");
			List valueList = getKmArchivesDestroyDetailsService()
					.findList(detailInfo);
			
			// 驳回状态下可重新选取当前销毁流程中已选择的档案
			String fdId = request.getParameter("fdCurDestroyId");
			if (StringUtil.isNotNull(fdId)) {
				KmArchivesDestroy fdDestroy = null;
				// 用HQL查询是避免新建的情况下抛异常
				HQLInfo desHqlInfo = new HQLInfo();
				desHqlInfo.setWhereBlock("kmArchivesDestroy.fdId = :fdDestroyId");
				desHqlInfo.setParameter("fdDestroyId", fdId);
//				List<KmArchivesDestroy> destList = getKmArchivesDestroyService().findList(desHqlInfo);
				Object one = getKmArchivesDestroyService().findFirstOne(desHqlInfo);
				if(one != null){
					fdDestroy = (KmArchivesDestroy) one;
				}
//				if (destList != null && !destList.isEmpty()) {
//					if (destList.get(0) != null) {
//						fdDestroy = destList.get(0);
//					}
//				}
				if (fdDestroy != null) {
					List<KmArchivesDestroyDetails> destroyDetails = fdDestroy.getFdDestroyDetails();
					for (KmArchivesDestroyDetails detail : destroyDetails) {
						if (valueList.contains(detail.getFdArchives().getFdId())) {
							valueList.remove(detail.getFdArchives().getFdId());
						}
					}
				}
			}
			
			if (valueList != null && valueList.size() > 0) {
				whereBlock = StringUtil.linkString(whereBlock, " and not ",
						HQLUtil.buildLogicIN("kmArchivesMain.fdId", valueList));
			}
		}
		hqlInfo.setWhereBlock(whereBlock);
		// HQLHelper.by(request).buildHQLInfo(hqlInfo, KmArchivesMain.class);
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		KmArchivesUtil.buildHql(request, cv, hqlInfo, KmArchivesMain.class);
    }

	/**
	 * 选择列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
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
			String fdId = request.getParameter("fdId");
			List<String> list = new ArrayList<String>();
			if (StringUtil.isNotNull(fdId)) {
				String[] str = fdId.split(",");
				for (int i = 0; i < str.length; i++) {
					list.add(str[i]);
				}
			}
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = 0;
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
			String whereBlock = list.size() > 0
					? HQLUtil.buildLogicIN("kmArchivesMain.fdId", list)
					: "1=0";
			hqlInfo.setWhereBlock(whereBlock);
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
			return getActionForward("checkList", mapping, form, request,
					response);
		}
	}

	/**
	 * 查询所有档案信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkArchList(ActionMapping mapping, ActionForm form,
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
			int rowsize = 0;
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
			request.setAttribute("selected", request.getParameter("selected"));
			request.setAttribute("fdCurDestroyId",
					request.getParameter("fdCurDestroyId"));
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
			return getActionForward("checkArchList", mapping, form, request,
					response);
		}
	}

	public ActionForward loadArchMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-listForApplication", true,
				getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String fdId = request.getParameter("fdId");
			List<String> list = new ArrayList<String>();
			if (StringUtil.isNotNull(fdId)) {
				String[] str = fdId.split(",");
				for (int i = 0; i < str.length; i++) {
					list.add(str[i]);
				}
			}
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			String whereBlock = list.size() > 0
					? HQLUtil.buildLogicIN("kmArchivesMain.fdId", list)
					: "1=0";
			hqlInfo.setWhereBlock(whereBlock);
			List valueList = getServiceImp(request).findList(hqlInfo);
			JSONArray jsonArr = KmArchivesUtil.toDestroyDataArray(valueList);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(jsonArr.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			messages.addError(e);
		}
		return null;
	}
}
