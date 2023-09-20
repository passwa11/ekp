package com.landray.kmss.sys.organization.filter;

import com.landray.kmss.sys.authentication.filter.FilterContext;
import com.landray.kmss.sys.authentication.filter.HQLFragment;
import com.landray.kmss.sys.authentication.filter.IAuthenticationFilter;

public class AuthOrgNullReaderFilter implements IAuthenticationFilter {

	@Override
    public int getAuthHQLInfo(FilterContext ctx) {
		if (ctx.getUser().isAnonymous()) {
			ctx.setHqlFragment(new HQLFragment("1=2"));
			return FilterContext.RETURN_VALUE;
		}

		String field = ctx.getParameter("field");
		HQLFragment hqlFragment = new HQLFragment();
		String tableName = ctx.getModelTable();
		String joinBlock = " left join " + tableName + "." + field + " as "
				+ field + "_1";
		String whereBlock = field + "_1 is null";
		hqlFragment.setJoinBlock(joinBlock);
		hqlFragment.setWhereBlock(whereBlock);
		ctx.setHqlFragment(hqlFragment);
		return FilterContext.RETURN_VALUE;
	}

}
