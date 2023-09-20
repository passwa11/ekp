package com.landray.kmss.km.review.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseTemplateModel;
import com.landray.kmss.common.service.BaseTemplateTreeService;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.review.model.KmReviewTemplate;
import com.landray.kmss.km.review.service.IKmReviewTemplateService;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.category.service.SysCategoryAuthPlugin;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.StringUtil;

public class KmReviewTemplateTreeService extends BaseTemplateTreeService {

	private IKmReviewTemplateService kmReviewTemplateService;

	public void setKmReviewTemplateService(IKmReviewTemplateService kmReviewTemplateService) {
		this.kmReviewTemplateService = kmReviewTemplateService;
	}

	@Override
	protected IBaseService getServiceImp() {
		return kmReviewTemplateService;
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String categoryId = requestInfo.getParameter("categoryId");
		String type = requestInfo.getParameter("getType");
		String authType = requestInfo.getParameter("checkAuth");
		if (StringUtil.isNull(type)) {
            type = "category";
        }
		if (StringUtil.isNull(authType)) {
            authType = "";
        }

		IBaseService baseService = getServiceImp();
		String tableName = ModelUtil.getModelTableName(baseService.getModelName());
		IBaseTemplateModel template;
		List returnList = new ArrayList();
		if (StringUtil.isNotNull(categoryId)) {
			HQLInfo info = new HQLInfo();
			String whereString = "";
			if ("property".equalsIgnoreCase(type)) {
				whereString = tableName + ".docProperties.fdId=:categoryId";
				info.setParameter("categoryId", categoryId);
			} else {
				whereString = tableName + ".docCategory.fdId=:categoryId";
				info.setParameter("categoryId", categoryId);
			}
			
			// 过滤关闭的模板
			if (StringUtil.isNotNull(whereString)) {
				whereString += " and ";
			}
			whereString += "(" + tableName + ".fdIsAvailable is null or "
					+ tableName + ".fdIsAvailable = :fdIsAvailable)";
			info.setParameter("fdIsAvailable",Boolean.TRUE);
			info.setWhereBlock(whereString);
			info.setOrderBy(tableName + ".fdOrder asc,  " + tableName + ".fdId asc");

			SysCategoryAuthPlugin.getFilter(KmReviewTemplate.class.getName())
					.hqlFilter(requestInfo,
							info);

			if ("reader".equalsIgnoreCase(authType)) {
				info.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_READER);
				info.setCheckParam(SysAuthConstant.CheckType.AreaCheck, SysAuthConstant.AreaCheck.YES);
				info.setCheckParam(SysAuthConstant.CheckType.AreaIsolation, ISysAuthConstant.ISOLATION_SCENE_CATEGORY);
			} else if ("editor".equalsIgnoreCase(authType)) {
				info.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_EDITOR);
				info.setCheckParam(SysAuthConstant.CheckType.AreaCheck, SysAuthConstant.AreaCheck.YES);
				info.setCheckParam(SysAuthConstant.CheckType.AreaIsolation, SysAuthConstant.AreaIsolation.CHILD);
			} else {
				info.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_NONE);
				info.setCheckParam(SysAuthConstant.CheckType.AreaCheck, SysAuthConstant.AreaCheck.NO);
			}

			List list = baseService.findList(info);
			for (int i = 0; i < list.size(); i++) {
				template = (IBaseTemplateModel) list.get(i);
				Map node = new HashMap();
				node.put("value", template.getFdId());
				node.put("text", template.getFdName());
				node.put("nodeType", "TEMPLATE");
				node.put("isAutoFetch", "0");
				returnList.add(node);
			}
		}
		return returnList;
	}
}
