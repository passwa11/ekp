package com.landray.kmss.km.archives.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.archives.model.KmArchivesConfig;
import com.landray.kmss.km.archives.model.KmArchivesDetails;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.km.archives.service.IKmArchivesCategoryService;
import com.landray.kmss.km.archives.service.IKmArchivesDetailsService;
import com.landray.kmss.km.archives.service.IKmArchivesMainService;
import com.landray.kmss.km.archives.service.IKmArchivesTemplateService;
import com.landray.kmss.km.archives.util.KmArchivesConstant;
import com.landray.kmss.km.archives.util.KmArchivesUtil;
import com.landray.kmss.sys.category.interfaces.ConfigUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
 

public class KmArchBorrowOptionAction extends ExtendAction {

	private IKmArchivesMainService kmArchivesMainService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmArchivesMainService == null) {
			kmArchivesMainService = (IKmArchivesMainService) getBean(
					"kmArchivesMainService");
		}
		return kmArchivesMainService;
	}

	private IKmArchivesDetailsService kmArchivesDetailsService;

	public IKmArchivesDetailsService getKmArchivesDetailsService() {
		if (kmArchivesDetailsService == null) {
			kmArchivesDetailsService = (IKmArchivesDetailsService) getBean(
					"kmArchivesDetailsService");
		}
		return kmArchivesDetailsService;
	}

	private IKmArchivesTemplateService kmArchivesTemplateService;
	public IKmArchivesTemplateService getKmArchivesTemplateService() {
		if (kmArchivesTemplateService == null) {
			kmArchivesTemplateService = (IKmArchivesTemplateService) getBean(
					"kmArchivesTemplateService");
		}
		return kmArchivesTemplateService;
	}

	private IKmArchivesCategoryService kmArchivesCategoryService;

	public IKmArchivesCategoryService getKmArchivesCategoryService() {
		if (kmArchivesCategoryService == null) {
			kmArchivesCategoryService = (IKmArchivesCategoryService) getBean(
					"kmArchivesCategoryService");
		}
		return kmArchivesCategoryService;
	}

	@Override
	public void changeFindPageHQLInfo(HttpServletRequest request,
									  HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		String whereBlock = hqlInfo.getWhereBlock();

		String fdCategoryId = request.getParameter("categoryId");
		String keywords = request.getParameter("keywords");
		request.setAttribute("fdCategoryId", fdCategoryId);
		if (StringUtil.isNotNull(fdCategoryId)) {
			whereBlock = " kmArchivesMain.docTemplate.fdId = :fdCategoryId ";
			hqlInfo.setParameter("fdCategoryId", fdCategoryId);
		} else {
			boolean flag = ConfigUtil.auth(
					"com.landray.kmss.km.archives.model.KmArchivesCategory");
			if (flag) {
				HQLInfo hql = new HQLInfo();
				hql.setSelectBlock("kmArchivesCategory.fdId");
				List categoryIds = getKmArchivesCategoryService()
						.findList(hql);
				if (categoryIds != null && !categoryIds.isEmpty()) {
					whereBlock = StringUtil.linkString(whereBlock, " and ",
							HQLUtil.buildLogicIN(
									"kmArchivesMain.docTemplate.fdId",
									categoryIds));
				} else {
					whereBlock = "1=2";
				}
			} else {
				whereBlock = "1=1";
			}
		}
		if (StringUtil.isNotNull(keywords)) {
			whereBlock += " and (kmArchivesMain.docSubject like :docSubject or kmArchivesMain.docNumber like :docNumber)";
			hqlInfo.setParameter("docSubject", "%" + keywords + "%");
			hqlInfo.setParameter("docNumber", "%" + keywords + "%");
		}
		// 过期范围
		String expireRange = new KmArchivesConfig().getFdSoonExpireDate();
		int range = 0;
		if (StringUtil.isNotNull(expireRange)) {
			try {
				range = Integer.parseInt(expireRange);
			} catch (Exception e) {

			}
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, range);
		// 档案有效期大于当前时间与后台配置即将到期提醒的和并且档案为被销毁
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"(kmArchivesMain.fdValidityDate > :maxValidityDate or kmArchivesMain.fdValidityDate is null) and kmArchivesMain.fdDestroyed=:fdDestroyed and kmArchivesMain.docStatus =:docStatus");
		hqlInfo.setParameter("maxValidityDate", cal.getTime());
		hqlInfo.setParameter("fdDestroyed", false);
		hqlInfo.setParameter("docStatus", SysDocConstant.DOC_STATUS_PUBLISH);
		// 过滤当前用户借阅中的明细
		SysOrgPerson fdBorrower = UserUtil.getKMSSUser().getPerson();
		List<KmArchivesDetails> detailsList = (List<KmArchivesDetails>) getKmArchivesDetailsService()
				.findByFdBorrower(fdBorrower,
						new String[] {
								KmArchivesConstant.BORROW_STATUS_LOANING },
						null);
		if (!detailsList.isEmpty()) {
			List<String> modelIdList = new ArrayList<String>();
			for (KmArchivesDetails details : detailsList) {
				if(details.getFdArchives() != null) {
					modelIdList.add(details.getFdArchives().getFdId());
				}
			}
			whereBlock = StringUtil.linkString(whereBlock, " and not ",
					HQLUtil.buildLogicIN("kmArchivesMain.fdId", modelIdList));
		}
		// 查询只显示最新的版本
		whereBlock += " and kmArchivesMain.docIsNewVersion = '1'";
		
		// 查询流程模块的密级程度所对应的档案
		String fdTemplatId = request.getParameter("fdTemplatId");
		request.setAttribute("fdTemplatId", fdTemplatId);
		if(StringUtil.isNotNull(fdTemplatId)) {
			HQLInfo templateHql = new HQLInfo();
			templateHql.setSelectBlock("kmArchivesTemplate.listDenseLevel.fdId");
			templateHql.setWhereBlock("kmArchivesTemplate.fdId = :fdId");
			templateHql.setParameter("fdId", fdTemplatId);
			List<String> denseLevellist = getKmArchivesTemplateService().findValue(templateHql);
			if(denseLevellist != null && denseLevellist.size() > 0 ) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						HQLUtil.buildLogicIN(" kmArchivesMain.fdDense.fdId", denseLevellist));
			} else {
				whereBlock = StringUtil.linkString(whereBlock, " and ", " kmArchivesMain.fdDense is null");
			}
		}
		
		hqlInfo.setWhereBlock(whereBlock);
		KmArchivesUtil.buildHql(request, cv, hqlInfo, KmArchivesMain.class);
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
 
	/**
	 * 获取选择的列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void ajaxCheckList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-updateStopSalary", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdId = request.getParameter("fdMainId");
			if (StringUtil.isNotNull(fdId)) { 
				List<String> list = new ArrayList<String>();
				if (StringUtil.isNotNull(fdId)) {
					String[] str = fdId.split(",");
					for (int i = 0; i < str.length; i++) {
						if(StringUtil.isNotNull(str[i])) {
							list.add(str[i]);
						}
					}
				} 
				HQLInfo hqlInfo = new HQLInfo();
				String whereBlock = list.size() > 0 ? HQLUtil.buildLogicIN("kmArchivesMain.fdId", list): "1=0";
				hqlInfo.setWhereBlock(whereBlock);
				List resultList =getServiceImp(request).findList(hqlInfo); 
				JSONArray array=new JSONArray();
				if(resultList !=null) {  
					array=  KmArchivesUtil.toDataArraySimpl(resultList);
				}
				response.setCharacterEncoding("UTF-8");
		        response.getWriter().print(array);
		        response.getWriter().flush();
		        response.getWriter().close();
		       
			}
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
		}
		TimeCounter.logCurrentTime("Action-updateStopSalary", false,
				getClass());
 
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
			JSONArray jsonArr = KmArchivesUtil.toDataArray(valueList);
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
