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

/**
 * 判断是否有权限查看
 *
 * @author cuiwj
 * @version 1.0 2018-11-01
 */
public class SysAttendCateStatFilter implements IAuthenticationFilter {

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
				StringBuffer where = new StringBuffer();
				where.append(
						"sysAttendCategory.fdStatus in(1,2) ");
				where.append(
						" and (sysAttendCategory.fdAppId='' or sysAttendCategory.fdAppId is null) and sysAttendCategory.fdType=2");
				List<SysAttendCategory> cateList = sysAttendCategoryService
						.findList(where.toString(), null);
				List<String> cateIds = new ArrayList<String>();
				for (SysAttendCategory category : cateList) {
					if (sysAttendCategoryService.isStatSignReader(category)) {
						cateIds.add(category.getFdId());
					}
				}
				StringBuffer whereBlock = new StringBuffer();
				if (!cateIds.isEmpty()) {
					whereBlock.append(ctx.getModelTable() + "." + field
							+ " in (:cate_stat_Ids) ");
					HQLParameter hqlParameter = new HQLParameter(
							"cate_stat_Ids", cateIds);
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
