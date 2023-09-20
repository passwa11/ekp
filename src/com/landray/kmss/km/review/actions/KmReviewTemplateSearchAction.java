package com.landray.kmss.km.review.actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.DataAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.review.model.KmReviewTemplate;
import com.landray.kmss.km.review.service.IKmReviewTemplateService;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.service.ISysCategoryMainService;
import com.landray.kmss.sys.category.service.spring.SysCategoryAuthListService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;

/**
 * 适用于流程管理后台模板搜索
 * 
 * 潘永辉 创建日期 2016-05-30
 */
public class KmReviewTemplateSearchAction extends DataAction {
	protected IKmReviewTemplateService kmReviewTemplateService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmReviewTemplateService == null) {
            kmReviewTemplateService = (IKmReviewTemplateService) getBean("kmReviewTemplateService");
        }
		return kmReviewTemplateService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaUtil.buildHql(new CriteriaValue(request), hqlInfo,
				KmReviewTemplate.class);

		// 判断是否有“查看所有流程模板”角色
		if (!UserUtil.checkRole("ROLE_KMREVIEW_TEMPLATE_VIEW")) {
			List<String> ids = getIds(request);
			if (ids.size() > 0) {
				String whereBlock = hqlInfo.getWhereBlock();
				hqlInfo.setWhereBlock((StringUtil.isNull(whereBlock) ? "" : whereBlock + " and") + " kmReviewTemplate.fdId in (:fdIds)");
				hqlInfo.setParameter("fdIds", ids);
			} else {
				// 没有任何模板可读权限
				hqlInfo.setWhereBlock("1 != 1");
			}
		}
	}

	/**
	 * 获取有可阅读权限的模板ID
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List<String> getIds(HttpServletRequest request) throws Exception {
		List<String> ids = new ArrayList<String>();
		// 获取有权限的模板ID
		String sql = "select tpl.fd_id, tpl.fd_category_id from km_review_template tpl left join km_review_template_areader ta on ta.fd_template_id = tpl.fd_id where ta.auth_all_reader_id = :orgId or tpl.auth_reader_flag = :authReaderFlag";
		List<Object[]> templateIds = getServiceImp(request).getBaseDao().getHibernateSession().createNativeQuery(sql).setString("orgId",
						UserUtil.getUser().getFdId()).setParameter("authReaderFlag",Boolean.TRUE).list();

		// 获取可阅读权限的分类ID
		Set<String> categoryIds = new HashSet<String>();
		SysCategoryAuthListService sysCategoryAuthListService = (SysCategoryAuthListService) SpringBeanUtil.getBean("sysCategoryAuthListService");
		RequestContext requestInfo = new RequestContext();
		requestInfo.setParameter("modelName", "com.landray.kmss.km.review.model.KmReviewTemplate");
		requestInfo.setParameter("getTemplate", "2");
		requestInfo.setParameter("getReadCate", "true");
		List<Map<String, String>> categoryList = sysCategoryAuthListService.getDataList(requestInfo);
		for (Map<String, String> category : categoryList) {
			Set<Entry<String, String>> entrys = category.entrySet();
			for (Entry<String, String> entry : entrys) {
				categoryIds.add(entry.getValue());
			}
		}
		
		// 获取有分类权限的模板ID
		for (Object[] templateId : templateIds) {
			if (categoryIds.contains(templateId[1])) {
				ids.add((String) templateId[0]);
			}
		}
		return ids;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActionForward listChildren(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = super.listChildren(mapping, form, request, response);

		Page page = (Page) request.getAttribute("queryPage");
		List<KmReviewTemplate> list = page.getList();
		for (KmReviewTemplate template : list) {
			// 获取分类全路径
			String creatName = getFdCategoryName(template.getDocCategory());
			template.setCreategoryFullName(creatName);
		}

		return forward;
	}

	private String getFdCategoryName(SysCategoryMain sysCategoryMain) {
		String fdCategoryName = sysCategoryMain.getFdName();
		SysCategoryMain fdParent = (SysCategoryMain) sysCategoryMain.getFdParent();
		if (fdParent != null) {
			do {
				fdCategoryName = fdParent.getFdName() + "/" + fdCategoryName;
				fdParent = (SysCategoryMain) fdParent.getFdParent();
			} while (fdParent != null);
		}
		return fdCategoryName;
	}

	private ISysCategoryMainService categoryMainService;

	@Override
	protected IBaseService getCategoryServiceImp(HttpServletRequest request) {
		if (categoryMainService == null) {
            categoryMainService = (ISysCategoryMainService) getBean("sysCategoryMainService");
        }
		return categoryMainService;
	}

	@Override
	protected String getParentProperty() {
		return "docCategory";
	}
}
