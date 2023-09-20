package com.landray.kmss.sys.attend.service.spring;

import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.sys.authentication.filter.FilterContext;
import com.landray.kmss.sys.authentication.filter.HQLFragment;
import com.landray.kmss.sys.authentication.filter.IAuthenticationFilter;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.StringUtil;

public class SysAttendSynDingReaderFilter implements IAuthenticationFilter {

	@Override
	public int getAuthHQLInfo(FilterContext ctx) throws Exception {
		SysOrgElement element = ctx.getUser().getPerson();
		HQLFragment hqlFragment = new HQLFragment();
		if (element == null) {
			hqlFragment.setWhereBlock("1=2");
		} else {
			String field = ctx.getParameter("field");
			if (StringUtil.isNotNull(field)) {
				StringBuffer whereBlock = new StringBuffer();
				String tableName = ctx.getModelTable();
				whereBlock.append(
						tableName + "." + field
								+ " like :readers_auth_personId");
				HQLParameter hqlParameter = new HQLParameter(
						"readers_auth_personId",
						"%" + element.getFdId() + "%");
				hqlFragment.setParameter(hqlParameter);
				hqlFragment.setWhereBlock(whereBlock.toString());
			} else {
				hqlFragment.setWhereBlock("1=2");
			}
		}
		ctx.setHqlFragment(hqlFragment);
		return FilterContext.RETURN_VALUE;
	}

}
