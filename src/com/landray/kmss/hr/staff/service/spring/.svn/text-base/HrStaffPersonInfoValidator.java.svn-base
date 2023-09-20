package com.landray.kmss.hr.staff.service.spring;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffFileAuthorService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

/**
 * @author yezhengping 2018-09-29
 * 人事档案人员查询校验器
 */
public class HrStaffPersonInfoValidator implements IAuthenticationValidator {
	private IHrStaffFileAuthorService hrStaffFileAuthorService;

	public void setHrStaffFileAuthorService(
			IHrStaffFileAuthorService hrStaffFileAuthorService) {
		this.hrStaffFileAuthorService = hrStaffFileAuthorService;
	}
	
	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	public void setHrStaffPersonInfoService(
			IHrStaffPersonInfoService hrStaffPersonInfoService) {
		this.hrStaffPersonInfoService = hrStaffPersonInfoService;
	}
	
	private IHrOrganizationElementService hrOrganizationElementService;

	public IHrOrganizationElementService getHrOrganizationElementService() {
		if (hrOrganizationElementService == null) {
			hrOrganizationElementService = (IHrOrganizationElementService) SpringBeanUtil
					.getBean(
					"hrOrganizationElementService");
		}
		return hrOrganizationElementService;
	}

	@Override
	public boolean validate(ValidatorRequestContext validatorContext) throws Exception {
		String[] postId = UserUtil.getKMSSUser().getPostIds();
		String sql = "select a.fd_id,a.fd_name,d.fd_org_id from hr_staff_file_author_detail d left join hr_staff_file_author a on a.fd_id = d.fd_author_id where fd_org_id =:personId";
		if (postId != null && postId.length > 0) {
			List list = Arrays.asList(postId);
			sql = sql + " or " + HQLUtil.buildLogicIN("fd_org_id", list);
		}
		List<Object[]> authorDetails = hrStaffFileAuthorService.getBaseDao().getHibernateSession().createNativeQuery(sql).setString("personId",
						UserUtil.getKMSSUser().getPerson().getFdId())
				.list();
		if(authorDetails.size()==0) {
            return false;
        } else{
			
			String personInoId = validatorContext.getParameter("fdId");
			if(StringUtil.isNotNull(personInoId)){
				HrStaffPersonInfo hrPerson = (HrStaffPersonInfo)hrStaffPersonInfoService.findByPrimaryKey(personInoId);
				
				if(hrPerson==null) {
                    return true;
                }
				/*if (hrPerson.getFdOrgParent() == null)
					return false;*/
				
				List<String> orgIds = new ArrayList<String>();
				
				//处理DB2会多出一列的情况
				int i = 1;
				if(authorDetails.get(0)[0] instanceof BigInteger){
					i++;
				}
				
				String status = hrPerson.getFdStatus();
				String[] quitStatus = new String[] { "dismissal", "leave",
						"retire" };
				if (ArrayUtil.asList(quitStatus).contains(status)) {
					for (Object[] obj : authorDetails) {
						if (StringUtil.isNotNull((String) obj[i])) {
                            orgIds.add((String) obj[i]);
                        }
					}
					orgIds = this.getDeptIds(orgIds);
					if (orgIds.contains(hrPerson.getFdParent().getFdId())) {
						return true;
					}
				} else {
					for (Object[] obj : authorDetails) {
						if (hrPerson.getFdHierarchyId()
								.contains((String) obj[i])) {
							return true;
						}
						if (null != hrPerson.getFdOrgParent()
								&& hrPerson.getFdOrgParent().getFdHierarchyId()
										.contains((String) obj[i])) {
							return true;
						}
					}
				}

				return false;
			}else {
                return true;
            }
		}
	}

	private List<String> getDeptIds(List<String> deptIds) throws Exception {
		List<String> newDeptIds = new ArrayList<String>();
		newDeptIds.addAll(deptIds);
		for (String deptId : deptIds) {
			HrOrganizationElement element = (HrOrganizationElement) this
					.getHrOrganizationElementService()
					.findByPrimaryKey(deptId);
			if (!ArrayUtil.isEmpty(element.getFdChildren())) {
				List<HrOrganizationElement> childs = element
						.getFdChildren();
				List<String> childIds = new ArrayList<String>();
				for (HrOrganizationElement child : childs) {
					if (newDeptIds.contains(child.getFdId())) {
						continue;
					}
					childIds.add(child.getFdId());
				}
				if (!ArrayUtil.isEmpty(childIds)) {
					newDeptIds.addAll(this.getDeptIds(childIds));
				}
			}
		}
		return newDeptIds;
	}
}
