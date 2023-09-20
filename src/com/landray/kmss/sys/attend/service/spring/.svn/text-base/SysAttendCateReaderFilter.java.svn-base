package com.landray.kmss.sys.attend.service.spring;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.authentication.filter.FilterContext;
import com.landray.kmss.sys.authentication.filter.HQLFragment;
import com.landray.kmss.sys.authentication.filter.IAuthenticationFilter;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.StringUtil;

public class SysAttendCateReaderFilter implements IAuthenticationFilter {

	private ISysAttendCategoryService sysAttendCategoryService;

	@Override
	public int getAuthHQLInfo(FilterContext ctx) throws Exception {

		SysOrgElement element = ctx.getUser().getPerson();
		HQLFragment hqlFragment = new HQLFragment();
		if (element == null) {
			hqlFragment.setWhereBlock("1=2");
		} else {
			List cateIds = new ArrayList();
			String field = ctx.getParameter("field");
			String cateType = ctx.getParameter("cateType");

			if (StringUtil.isNotNull(field)) {

				if (StringUtil.isNotNull(cateType)
						&& ("1".equals(cateType) || "2".equals(cateType))) {
					cateIds = sysAttendCategoryService.findCateIdsByReaderId(
							element.getFdId(),
							Integer.parseInt(cateType));
				} else {
					cateIds = sysAttendCategoryService.findCateIdsByReaderId(
							element.getFdId(), 1);
					cateIds.addAll(
							sysAttendCategoryService.findCateIdsByReaderId(
									element.getFdId(), 2));
				}

				StringBuffer whereBlock = new StringBuffer();
				if (cateIds != null && !cateIds.isEmpty()) {
					whereBlock.append(ctx.getModelTable() + "." + field
							+ " in (:readers_auth_cateIds) ");
					HQLParameter hqlParameter = new HQLParameter(
							"readers_auth_cateIds",
							cateIds);
					hqlFragment.setParameter(hqlParameter);
					hqlFragment.setWhereBlock(whereBlock.toString());
				} else {
					hqlFragment.setWhereBlock("1=2");
				}

			} else {
				hqlFragment.setWhereBlock("1=2");
			}
		}
		ctx.setHqlFragment(hqlFragment);
		return FilterContext.RETURN_VALUE;
	}

	public void setSysAttendCategoryService(
			ISysAttendCategoryService sysAttendCategoryService) {
		this.sysAttendCategoryService = sysAttendCategoryService;
	}

}
