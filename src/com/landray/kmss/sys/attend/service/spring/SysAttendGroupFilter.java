package com.landray.kmss.sys.attend.service.spring;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.authentication.filter.FilterContext;
import com.landray.kmss.sys.authentication.filter.HQLFragment;
import com.landray.kmss.sys.authentication.filter.IAuthenticationFilter;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 判断是否组内成员
 *
 * @author cuiwj
 * @version 1.0 2018-11-01
 */
public class SysAttendGroupFilter implements IAuthenticationFilter {

	private ISysAttendCategoryService sysAttendCategoryService;

	public void setSysAttendCategoryService(
			ISysAttendCategoryService sysAttendCategoryService) {
		this.sysAttendCategoryService = sysAttendCategoryService;
	}

	@Override
	public int getAuthHQLInfo(FilterContext ctx) throws Exception {
		SysOrgElement element = ctx.getUser().getPerson();
		HQLFragment hqlFragment = new HQLFragment();
		if (element == null) {
			hqlFragment.setWhereBlock("1=2");
		} else {
			String field = ctx.getParameter("field");
			if (StringUtil.isNotNull(field)) {
				List<SysAttendCategory> list = sysAttendCategoryService
						.findList(
								"sysAttendCategory.fdIsAllowView=true and sysAttendCategory.fdType=2",
								null);
				List<String> cateIds = new ArrayList<String>();
				for (SysAttendCategory category : list) {
					if (UserUtil.checkUserModels(category.getFdTargets())) {
						cateIds.add(category.getFdId());
					}
				}
				StringBuffer whereBlock = new StringBuffer();
				if (!cateIds.isEmpty()) {
					whereBlock.append(ctx.getModelTable() + "." + field
							+ " in (:same_group_cateIds) ");
					HQLParameter hqlParameter = new HQLParameter(
							"same_group_cateIds", cateIds);
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

}
