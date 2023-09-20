package com.landray.kmss.sys.attend.service.spring;

import java.util.List;

import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.sys.attend.service.ISysAttendOrgService;
import com.landray.kmss.sys.authentication.filter.FilterContext;
import com.landray.kmss.sys.authentication.filter.HQLFragment;
import com.landray.kmss.sys.authentication.filter.IAuthenticationFilter;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 领导过滤器
 * 
 * @author admin
 *
 */
public class SysAttendOrgFilter implements IAuthenticationFilter {

	private ISysAttendOrgService sysAttendOrgService;

	@Override
	public int getAuthHQLInfo(FilterContext ctx) throws Exception {

		SysOrgElement element = ctx.getUser().getPerson();
		HQLFragment hqlFragment = new HQLFragment();
		if (element == null) {
			hqlFragment.setWhereBlock("1=2");
		} else {
			List deptIds = sysAttendOrgService.findDeptsByLeader(element);
			List personIds = sysAttendOrgService.findPersonsByLeader(element);
			String field = ctx.getParameter("field");
			String parentField = ctx.getParameter("parentfield");
			if (StringUtil.isNull(field)) {
				field = ctx.getModelTable();
			} else {
				field = ctx.getModelTable() + "." + field;
			}
			if (StringUtil.isNotNull(parentField)) {
				parentField = ctx.getModelTable() + "." + parentField;
			}
			StringBuffer whereBlock = new StringBuffer();
			if (deptIds != null && !deptIds.isEmpty() || !personIds.isEmpty()) {
				for (int i = 0; i < deptIds.size(); i++) {
					String deptId = "attend_auth_deptId" + i;
					String prefix = i > 0 ? " or " : " ";
					whereBlock.append(
							prefix + field + ".fdHierarchyId like :"
									+ deptId);
					if(StringUtil.isNotNull(parentField)) {
						whereBlock.append(" or ("+ parentField + " like :"
										+ deptId + " and "+field+".fdIsAvailable=false)");
					}
					HQLParameter hqlParameter = new HQLParameter(deptId,
							"%" + deptIds.get(i) + "%");
					hqlFragment.setParameter(hqlParameter);
				}
				if (!personIds.isEmpty()) {
					String prefix = StringUtil.isNotNull(whereBlock.toString())
							? " or " : " ";
					whereBlock.append(HQLUtil
							.buildLogicIN(prefix + field + ".fdId", personIds));
				}
				hqlFragment.setWhereBlock(whereBlock.toString());
			} else {
				hqlFragment.setWhereBlock("1=2");
			}
		}
		ctx.setHqlFragment(hqlFragment);
		return FilterContext.RETURN_VALUE;
	}

	public void
			setSysAttendOrgService(ISysAttendOrgService sysAttendOrgService) {
		this.sysAttendOrgService = sysAttendOrgService;
	}

}
