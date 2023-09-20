package com.landray.kmss.km.comminfo.actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.DataAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.comminfo.model.KmComminfoMain;
import com.landray.kmss.km.comminfo.service.IKmComminfoCategoryService;
import com.landray.kmss.km.comminfo.service.IKmComminfoMainService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;

/**
 * 创建日期：2014-07-28
 * 
 * 创建人：王腾飞
 * */

public class KmComminfoMainIndexAction extends DataAction {
	protected IKmComminfoMainService kmComminfoMainService;
	protected IKmComminfoCategoryService kmComminfoCategoryService;

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		String categoryId = cv.poll("docCategory");
		String orderby = request.getParameter("orderby");
		String ordertype = request.getParameter("ordertype");
		String listCategoryId = request.getParameter("categoryId");		
		String whereBlock = StringUtil.isNull(hqlInfo.getWhereBlock()) ? "1=1"
				: hqlInfo.getWhereBlock();
		if(listCategoryId != null && listCategoryId.length() > 0){
			hqlInfo.setParameter("docCategory", listCategoryId);
			whereBlock += "and (kmComminfoMain.docCategory.fdId = :docCategory)";
		} else if (categoryId != null && categoryId.length() > 0) {
			hqlInfo.setParameter("docCategory", categoryId);
			whereBlock += "and (kmComminfoMain.docCategory.fdId = :docCategory)";
				}
		boolean isReserve = false;
		if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
			isReserve = true;
		}
		if (isReserve) {
            orderby += " desc";
        }
		hqlInfo.setOrderBy(orderby);

		if (SysOrgEcoUtil.IS_ENABLED_ECO && !UserUtil.getKMSSUser().isAdmin() && !UserUtil.checkRole("ROLE_COMMINFO_MAIN_READER")) {
			// 如果是外部组织，只能查看有权限的模板
			List orgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
			if (SysOrgEcoUtil.isExternal()) {
				whereBlock += " and kmComminfoMain.authAllReaders.fdId in (:orgIds)";
			} else {
				whereBlock += " and (kmComminfoMain.authAllReaders.fdId is null or kmComminfoMain.authAllReaders.fdId in (:orgIds))";
				orgIds.add(UserUtil.getEveryoneUser().getFdId());
			}
			hqlInfo.setParameter("orgIds", orgIds);
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
		CriteriaUtil.buildHql(cv, hqlInfo, KmComminfoMain.class);
	}

	public ActionForward listMobile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.list(mapping, form, request, response);

		Page p = (Page) request.getAttribute("queryPage");
		List<KmComminfoMain> list = p.getList();
		Set<String> cateIds = new HashSet<String>();

		for (KmComminfoMain kmComminfoMain : list) {
			if (kmComminfoMain.getDocCategory() != null) {
				cateIds.add(kmComminfoMain.getDocCategory().getFdId());
			}
		}
		JSONArray jsonArr = new JSONArray();
		List countList = new ArrayList();
		if (cateIds.size() > 0) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock(" count(kmComminfoMain.fdId),kmComminfoMain.docCategory.fdId ");
			StringBuffer whereBlock = new StringBuffer();
			whereBlock.append("kmComminfoMain.docCategory.fdId in (:docCateIds) ");
			String docCategoryId = request.getParameter("docCategoryId");
			if(StringUtil.isNotNull(docCategoryId)){
				whereBlock.append("and kmComminfoMain.docCategory.fdId = :docCategoryId ");
				hqlInfo.setParameter("docCategoryId", docCategoryId);
			}
			if (SysOrgEcoUtil.IS_ENABLED_ECO && !UserUtil.getKMSSUser().isAdmin() && !UserUtil.checkRole("ROLE_COMMINFO_MAIN_READER")) {
				// 如果是外部组织，只能查看有权限的模板
				List orgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
				if (SysOrgEcoUtil.isExternal()) {
					whereBlock.append(" and kmComminfoMain.authAllReaders.fdId in (:orgIds)");
				} else {
					whereBlock.append(" and  kmComminfoMain.authAllReaders.fdId in (:orgIds)");
					orgIds.add(UserUtil.getEveryoneUser().getFdId());
				}
				hqlInfo.setParameter("orgIds", orgIds);
			}
			else if(!UserUtil.getKMSSUser().isAdmin() && !UserUtil.checkRole("ROLE_COMMINFO_MAIN_READER"))
			{
				List orgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
				whereBlock.append(" and  kmComminfoMain.authAllReaders.fdId in (:orgIds)");
				orgIds.add(UserUtil.getEveryoneUser().getFdId());
				hqlInfo.setParameter("orgIds", orgIds);
			}
			whereBlock.append("group by kmComminfoMain.docCategory.fdId,kmComminfoMain.docCategory.fdOrder,kmComminfoMain.docCategory.docCreateTime order by kmComminfoMain.docCategory.fdOrder asc,kmComminfoMain.docCategory.docCreateTime ");
			hqlInfo.setParameter("docCateIds", cateIds);
			hqlInfo.setWhereBlock(whereBlock.toString());
			countList = kmComminfoMainService.findValue(hqlInfo);
		}
		request.setAttribute("countList", countList);
		return getActionForward("list", mapping, form, request, response);
	}

	@Override
	protected IBaseService getCategoryServiceImp(HttpServletRequest request) {
		if (kmComminfoCategoryService == null) {
            kmComminfoCategoryService = (IKmComminfoCategoryService) getBean("kmComminfoCategoryService");
        }
		return kmComminfoCategoryService;
	}

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmComminfoMainService == null) {
            kmComminfoMainService = (IKmComminfoMainService) getBean("kmComminfoMainService");
        }
		return kmComminfoMainService;
	}

	@Override
	protected String getParentProperty() {
		return "docCategory";
	}

}