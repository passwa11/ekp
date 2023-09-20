package com.landray.kmss.hr.staff.service.spring;


import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import com.landray.kmss.hr.staff.model.HrStaffEntry;
import com.landray.kmss.hr.staff.service.IHrStaffEntryService;
import com.landray.kmss.hr.staff.service.IHrStaffFileAuthorService;
import com.landray.kmss.sys.authentication.filter.FilterContext;
import com.landray.kmss.sys.authentication.filter.HQLFragment;
import com.landray.kmss.sys.authentication.filter.IAuthenticationFilter;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.UserUtil;

public class HrStaffEntryAuthFilter implements IAuthenticationFilter {
	private IHrStaffEntryService hrStaffEntryService;
	private IHrStaffFileAuthorService hrStaffFileAuthorService;

	@Override
	public int getAuthHQLInfo(FilterContext ctx) throws Exception {
		Boolean filterFlag = Boolean.FALSE;
		String[] postId = UserUtil.getKMSSUser().getPostIds();
		String sql = "select a.fd_id,a.fd_name,d.fd_org_id from hr_staff_file_author_detail d left join hr_staff_file_author a on a.fd_id = d.fd_author_id where fd_org_id =:personId";
		if (postId != null && postId.length > 0) {
			List list = Arrays.asList(postId);
			sql = sql + " or " + HQLUtil.buildLogicIN("fd_org_id", list);
		}
		List<Object[]> authorDetails = hrStaffFileAuthorService.getBaseDao().getHibernateSession().createNativeQuery(sql).setString("personId",
						UserUtil.getKMSSUser().getPerson().getFdId())
				.list();
		if (authorDetails.size() == 0) {
            filterFlag = Boolean.TRUE;
        } else {

			String id = ctx.getParameter("fdId");
			if (StringUtil.isNotNull(id)) {
				HrStaffEntry entry = (HrStaffEntry) hrStaffEntryService
						.findByPrimaryKey(id);

				if (entry == null) {
                    filterFlag = Boolean.FALSE;
                }
				if (entry.getFdPlanEntryDept() == null) {
                    filterFlag = Boolean.TRUE;
                }

				// 处理DB2会多出一列的情况
				int i = 1;
				if (authorDetails.get(0)[0] instanceof BigInteger) {
					i++;
				}

				for (Object[] obj : authorDetails) {
					if (entry.getFdPlanEntryDept().getFdHierarchyId()
							.contains((String) obj[i])) {
						filterFlag = Boolean.FALSE;
					}
				}
				filterFlag = Boolean.TRUE;
			} else {
                filterFlag = Boolean.FALSE;
            }
		}
		ctx.setHqlFragment(new HQLFragment("1 != 1"));
		if(filterFlag){
			return FilterContext.RETURN_VALUE;
		}else{
			return FilterContext.RETRUN_CANCELFILTER;
		}
	}

	public IHrStaffEntryService getHrStaffEntryService() {
		return hrStaffEntryService;
	}

	public void setHrStaffEntryService(IHrStaffEntryService hrStaffEntryService) {
		this.hrStaffEntryService = hrStaffEntryService;
	}

	public IHrStaffFileAuthorService getHrStaffFileAuthorService() {
		return hrStaffFileAuthorService;
	}

	public void setHrStaffFileAuthorService(IHrStaffFileAuthorService hrStaffFileAuthorService) {
		this.hrStaffFileAuthorService = hrStaffFileAuthorService;
	}
	
}
