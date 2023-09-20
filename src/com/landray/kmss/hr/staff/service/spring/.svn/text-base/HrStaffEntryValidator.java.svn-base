package com.landray.kmss.hr.staff.service.spring;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import com.landray.kmss.hr.staff.model.HrStaffEntry;
import com.landray.kmss.hr.staff.service.IHrStaffEntryService;
import com.landray.kmss.hr.staff.service.IHrStaffFileAuthorService;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.UserUtil;

public class HrStaffEntryValidator implements IAuthenticationValidator {

	private IHrStaffFileAuthorService hrStaffFileAuthorService;

	public void setHrStaffFileAuthorService(
			IHrStaffFileAuthorService hrStaffFileAuthorService) {
		this.hrStaffFileAuthorService = hrStaffFileAuthorService;
	}

	private IHrStaffEntryService hrStaffEntryService;

	public void
			setHrStaffEntryService(IHrStaffEntryService hrStaffEntryService) {
		this.hrStaffEntryService = hrStaffEntryService;
	}

	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
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
            return false;
        } else {

			String id = validatorContext.getParameter("fdId");
			if (StringUtil.isNotNull(id)) {
				HrStaffEntry entry = (HrStaffEntry) hrStaffEntryService
						.findByPrimaryKey(id);

				if (entry == null) {
                    return true;
                }
				if (entry.getFdPlanEntryDept() == null) {
                    return false;
                }

				// 处理DB2会多出一列的情况
				int i = 1;
				if (authorDetails.get(0)[0] instanceof BigInteger) {
					i++;
				}

				for (Object[] obj : authorDetails) {
					if (entry.getFdPlanEntryDept().getFdHierarchyId()
							.contains((String) obj[i])) {
						return true;
					}
				}
				return false;
			} else {
                return true;
            }
		}
	}

}
