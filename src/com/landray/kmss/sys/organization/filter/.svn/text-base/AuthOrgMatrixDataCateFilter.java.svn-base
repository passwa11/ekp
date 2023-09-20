package com.landray.kmss.sys.organization.filter;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.sys.authentication.filter.FilterContext;
import com.landray.kmss.sys.authentication.filter.HQLFragment;
import com.landray.kmss.sys.authentication.filter.IAuthenticationFilter;
import com.landray.kmss.sys.organization.service.ISysOrgMatrixDataCateService;

/**
 * 矩阵分组维护者过滤器
 * 
 * @author panyh
 * @date Jul 25, 2020
 */
public class AuthOrgMatrixDataCateFilter implements IAuthenticationFilter {

	private ISysOrgMatrixDataCateService sysOrgMatrixDataCateService;

	public void setSysOrgMatrixDataCateService(ISysOrgMatrixDataCateService sysOrgMatrixDataCateService) {
		this.sysOrgMatrixDataCateService = sysOrgMatrixDataCateService;
	}

	@Override
	public int getAuthHQLInfo(FilterContext ctx) throws Exception {
		HQLFragment hqlFragment = new HQLFragment();
		List list = sysOrgMatrixDataCateService.findValue("hbmMatrix.fdId",
				"hbmElement.fdId = '" + ctx.getUser().getUserId() + "'", null);
		if (CollectionUtils.isNotEmpty(list)) {
			hqlFragment.setWhereBlock(ctx.getModelTable() + ".fdId" + " in (:matrixIds) ");
			hqlFragment.setParameter(new HQLParameter("matrixIds", list));
			ctx.setHqlFragment(hqlFragment);
			return FilterContext.RETURN_VALUE;
		}
		return FilterContext.RETURN_IGNOREME;
	}

}
