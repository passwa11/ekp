package com.landray.kmss.km.smissive.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.DataAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.common.model.IBaseTreeModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.smissive.model.KmSmissiveMain;
import com.landray.kmss.km.smissive.service.IKmSmissiveMainService;
import com.landray.kmss.km.smissive.service.IKmSmissiveTemplateService;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2013-10-31
 * 
 * @author 谭又豪
 */
public class KmSmissiveMainIndexAction extends DataAction {
	// 获取类别
	protected IKmSmissiveTemplateService kmSmissiveTemplateService;
	protected IKmSmissiveMainService kmSmissiveMainService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmSmissiveMainService == null) {
            kmSmissiveMainService = (IKmSmissiveMainService) getBean("kmSmissiveMainService");
        }
		return kmSmissiveMainService;
	}

	@Override
	protected IBaseService getCategoryServiceImp(HttpServletRequest request) {
		if (kmSmissiveTemplateService == null) {
            kmSmissiveTemplateService = (IKmSmissiveTemplateService) getBean("kmSmissiveTemplateService");
        }
		return kmSmissiveTemplateService;
	}

	@Override
	protected String getParentProperty() {
		return "fdTemplate";
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		StringBuilder hql = new StringBuilder(" 1=1 ");
		CriteriaValue cv = new CriteriaValue(request);
		String docProperties = cv.poll("docProperties");
		if (StringUtil.isNotNull(docProperties)) {
			hql
					.append(" and kmSmissiveMain.docProperties.fdId = :docProperties");
			hqlInfo.setParameter("docProperties", docProperties);
		}
		String docCategory = cv.poll("docCategory");
		if (StringUtil.isNotNull(docCategory)) {
			IBaseTreeModel treeModel = (IBaseTreeModel) getCategoryServiceImp(request).findByPrimaryKey(docCategory);
			hql.append(" and kmSmissiveMain.fdTemplate.fdHierarchyId like :_treeHierarchyId");
			hqlInfo.setParameter("_treeHierarchyId", treeModel.getFdHierarchyId() + "%");
		}
		CriteriaUtil.buildHql(cv, hqlInfo, KmSmissiveMain.class);
		if (StringUtil.isNotNull(hqlInfo.getWhereBlock())) {
			hql.append(" and ").append(hqlInfo.getWhereBlock());
		}
		hqlInfo.setWhereBlock(hql.toString());
		buildHomeZoneHql(cv, hqlInfo, request);
		// 发布状态隐藏list页面审批人信息
		List<HQLParameter> hqlParmeters = hqlInfo.getParameterList();
		String status = null;
		for (int i = 0; i < hqlParmeters.size(); i++) {
			if ("docStatus".equals(hqlParmeters.get(i).getName())) {
				status = hqlParmeters.get(i).getValue().toString();
			}
		}
		request.setAttribute("docStatus", status);
	}

	private void buildHomeZoneHql(CriteriaValue cv, HQLInfo hqlInfo,
			HttpServletRequest request) {
		String whereStr = hqlInfo.getWhereBlock();
		StringBuilder where = new StringBuilder(
				StringUtil.isNull(whereStr) ? "1=1 " : whereStr);

		String self = cv.poll("selfdoc");
		String tadoc = cv.poll("tadoc");
		boolean isSelfDoc = StringUtil.isNotNull(self);
		String mydoc = isSelfDoc ? self : tadoc;
		String userId = isSelfDoc ? UserUtil.getUser().getFdId() : request
				.getParameter("userid");

		if (StringUtil.isNull(userId) || StringUtil.isNull(mydoc)) {
			return;
		}

		if (StringUtil.isNotNull(mydoc)) {
			mydoc = mydoc.toLowerCase();
			if ("create".equals(mydoc)) {
				where.append(" and kmSmissiveMain.docCreator.fdId=:docCreator");
				hqlInfo.setParameter("docCreator", userId);
				hqlInfo.setWhereBlock(where.toString());
			} else if ("sign".equals(mydoc)) {
				where.append(" and kmSmissiveMain.fdIssuer.fdId=:docCreator");
				hqlInfo.setParameter("docCreator", userId);
				hqlInfo.setWhereBlock(where.toString());

			} else if ("approval".equals(mydoc) && isSelfDoc) {
				SysFlowUtil.buildLimitBlockForMyApproval("kmSmissiveMain",
						hqlInfo);
			} else if ("approved".equals(mydoc) && isSelfDoc) {
				SysFlowUtil.buildLimitBlockForMyApproved("kmSmissiveMain",
						hqlInfo);
			}
		}
	}

}
