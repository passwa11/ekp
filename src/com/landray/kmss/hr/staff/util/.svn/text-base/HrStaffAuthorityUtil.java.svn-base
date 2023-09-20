package com.landray.kmss.hr.staff.util;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.staff.model.HrStaffFileAuthor;
import com.landray.kmss.hr.staff.service.IHrStaffFileAuthorService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 档案权限
 * @author 胡耀华
 *
 */
public class HrStaffAuthorityUtil {
	
	private static IHrStaffFileAuthorService hrStaffFileAuthorService = null;

	private static IHrStaffFileAuthorService getHrStaffFileAuthorService() {
		if (hrStaffFileAuthorService == null) {
            hrStaffFileAuthorService = (IHrStaffFileAuthorService) SpringBeanUtil
                    .getBean("hrStaffFileAuthorService");
        }
		return hrStaffFileAuthorService;
	}

	private static IHrOrganizationElementService hrOrganizationElementService;

	public static IHrOrganizationElementService
			getHrOrganizationElementService() {
		if (hrOrganizationElementService == null) {
			hrOrganizationElementService = (IHrOrganizationElementService) SpringBeanUtil
					.getBean(
							"hrOrganizationElementService");
		}
		return hrOrganizationElementService;
	}
	private static final  String STAFF_PERSON_LOG = "hrStaffPersonInfoLog.fdTargets";
	private static final  String STAFF_PERSON = "hrStaffPersonInfo";
	private static final  String STAFF_PAYROLL = "hrStaffPayrollIssuance";
	private static final String STAFF_ENTRY = "hrStaffEntry";
	private static ISysOrgCoreService sysOrgCoreService = null;
	
	private static ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
            sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
                    .getBean("sysOrgCoreService");
        }
		return sysOrgCoreService;
	}
	
	/**
	 * 获取当前用户所拥有的档案查看权限机构ID
	 * @return
	 * @throws Exception
	 */
	public static List<String> obtainOrgAuth() throws Exception{
		List<String> orgIds = new ArrayList<String>();
		List<HrStaffFileAuthor>  authorLsit = getHrStaffFileAuthorService().findList("1=1", null);
		for (HrStaffFileAuthor hrStaffFileAuthor : authorLsit) {
			List<SysOrgElement> orgList = hrStaffFileAuthor.getAuthorDetail();
			Boolean isHas = checkAuthorHasUser(orgList);
			if(isHas){
				orgIds.add(hrStaffFileAuthor.getFdName());//fdName存储了机构ID
			}
		}
		return orgIds;
	}
	
	
	/**
	 * 判断当前用户在某机构中是否授权
	 * @param orgList
	 * @return
	 * @throws Exception
	 */
	private static boolean checkAuthorHasUser(List<SysOrgElement> orgList) throws Exception{
		SysOrgElement user = UserUtil.getUser();
		List<SysOrgElement> authorPerList = getSysOrgCoreService().expandToPerson(orgList);
		return authorPerList.contains(user);
	}
	
	/**
	 * 封装权限过滤HQL
	 * @param whereBlock
	 * @param tableName 一定得是HrStaffPersonInfo 或者SYsOrgPersion对象。可以是子属性xxx.person
	 * @param hqlInfo
	 * @return
	 * @throws Exception
	 */
	public static StringBuffer builtAuthorityWhereBlock(StringBuffer whereBlock,String tableName,HQLInfo hqlInfo) throws Exception{
		Boolean isAdmin =UserUtil.getKMSSUser().isAdmin();
		String roleName="ROLE_HRSTAFF_READALL";
		
		if(isAdmin){
			//系统管理员直接放行
			return whereBlock;
		} 
		String[] postId = UserUtil.getKMSSUser().getPostIds();
		String sql = "select a.fd_id,a.fd_name,d.fd_org_id from hr_staff_file_author_detail d left join hr_staff_file_author a on a.fd_id = d.fd_author_id where fd_org_id =:personId";
		if (postId != null && postId.length > 0) {
			List list = Arrays.asList(postId);
			sql = sql + " or " + HQLUtil.buildLogicIN("fd_org_id", list);
		}
		List<Object[]> authorDetails = getHrStaffFileAuthorService().getBaseDao().getHibernateSession().createNativeQuery(sql).setString("personId",
						UserUtil.getKMSSUser().getPerson().getFdId())
				.list();
		List<String> orgIds = new ArrayList<String>();
		
		//处理DB2会多出一列的情况
		if(authorDetails.size()>0){
			int j = 1;
			if(authorDetails.get(0)[0] instanceof BigInteger){
				j++;
			}
			
			for(Object[] obj : authorDetails){
				if(StringUtil.isNotNull((String)obj[j])) {
                    orgIds.add((String)obj[j]);
                }
			}
		} 
		
		whereBlock.append(" and exists (");
		
		StringBuilder exitsHql = new StringBuilder(
				"select 1 from HrStaffPersonInfo hrPerson,hrStaffPersonInfoLog.fdTargets hrTargets where ");
		//表主键关联人员ID 
		exitsHql.append("hrTargets").append(".fdId=hrPerson.fdId and ( ");
		if(orgIds.isEmpty()){ 
			//当前人员能查询的机构数据为空则直接过滤
			//直接匹配自己
			exitsHql.append(" hrPerson.fdId=:currentUserId");
			hqlInfo.setParameter("currentUserId",UserUtil.getKMSSUser().getUserId()); 
		}else {
			List<String> orgWhereHqls=new ArrayList<String>();
			for (int i = 0; i < orgIds.size(); i++) {
				String orgId = orgIds.get(i); 
				orgWhereHqls.add(String.format("((hrPerson.fdOrgPerson is null and hrPerson.fdHierarchyId like :orgid_%s ) or hrPerson.fdOrgPerson.fdHierarchyId like :orgid_%s)",i,i));
				hqlInfo.setParameter("orgid_"+i, "%"+orgId+"%");
			}
			exitsHql.append(StringUtil.join(orgWhereHqls, " or ")); 
		}
		exitsHql.append(")");
		whereBlock.append(exitsHql.toString());
		whereBlock.append(" )");
		return whereBlock;
	}
	
	/**
	 * 封装权限过滤HQL
	 * @param whereBlock
	 * @param tableName
	 * @param hqlInfo
	 * @return
	 * @throws Exception
	 */
	public static StringBuffer builtWhereBlock(StringBuffer whereBlock,String tableName,HQLInfo hqlInfo) throws Exception{
		Boolean isAdmin =UserUtil.getKMSSUser().isAdmin();
		if(isAdmin || UserUtil.checkRole("ROLE_HRSTAFF_READALL")){
			//系统管理员或者拥有阅读人事档案信息角色直接放行
			return whereBlock;
		}
		//List<String> orgIds =obtainOrgAuth(); //由于放开了部门可配置，会存在性能问题 故不使用此方式  by叶正平
		String[] postId = UserUtil.getKMSSUser().getPostIds();
		String sql = "select a.fd_id,a.fd_name,d.fd_org_id from hr_staff_file_author_detail d left join hr_staff_file_author a on a.fd_id = d.fd_author_id where fd_org_id =:personId";
		if (postId != null && postId.length > 0) {
			List list = Arrays.asList(postId);
			sql = sql + " or " + HQLUtil.buildLogicIN("fd_org_id", list);
		}
		List<Object[]> authorDetails = getHrStaffFileAuthorService().getBaseDao().getHibernateSession().createNativeQuery(sql).setString("personId",
						UserUtil.getKMSSUser().getPerson().getFdId())
				.list();
		List<String> orgIds = new ArrayList<String>();
		
		//处理DB2会多出一列的情况
		if(authorDetails.size()>0){
			int j = 1;
			if(authorDetails.get(0)[0] instanceof BigInteger){
				j++;
			}
			
			for(Object[] obj : authorDetails){
				if(StringUtil.isNotNull((String)obj[j])) {
                    orgIds.add((String)obj[j]);
                }
			}
		}
		
		
		if(orgIds.isEmpty()){
			whereBlock.append(" and 1 = 2");//当前人员能查询的机构数据为空则直接过滤
			return whereBlock;
		}
		whereBlock.append(" and (");
		for (int i = 0; i < orgIds.size(); i++) {
			String orgId = orgIds.get(i);
			if((orgIds.size()-1) == i){
				if(STAFF_PERSON.equals(tableName) || STAFF_PERSON_LOG.equals(tableName)){
					String joinBlock = " left join "+tableName +".fdOrgPerson fdPerson";
					hqlInfo.setJoinBlock(joinBlock);
					whereBlock.append("("+ tableName+ ".fdOrgPerson is null and "+tableName+".fdHierarchyId like :orgid_"+i+") or fdPerson.fdHierarchyId like :orgid_"+i);
				}else if(STAFF_PAYROLL.equals(tableName)){
					whereBlock.append(tableName+".fdCreator.fdHierarchyId like :orgid_"+i);
				} else if (STAFF_ENTRY.equals(tableName)) {
					whereBlock.append(tableName
							+ ".fdPlanEntryDept.fdHierarchyId like :orgid_"
							+ i);
				}else{
					whereBlock.append(tableName+".fdPersonInfo.fdHierarchyId like :orgid_"+i);
				}
			}else{
				if(STAFF_PERSON.equals(tableName) || STAFF_PERSON_LOG.equals(tableName)){
					
					String joinBlock = " left join "+tableName +".fdOrgPerson fdPerson";
					hqlInfo.setJoinBlock(joinBlock);
					whereBlock.append("("+ tableName+ ".fdOrgPerson is null and "+tableName+".fdHierarchyId like :orgid_"+i+") or fdPerson.fdHierarchyId like :orgid_"+i+" or ");					

				}else if(STAFF_PAYROLL.equals(tableName)){
					whereBlock.append(tableName+".fdCreator.fdHierarchyId like :orgid_"+i+" or ");
				} else if (STAFF_ENTRY.equals(tableName)) {
					whereBlock.append(tableName
							+ ".fdPlanEntryDept.fdHierarchyId like :orgid_" + i
							+ " or ");
				}else{
					whereBlock.append(tableName+".fdPersonInfo.fdHierarchyId like :orgid_"+i+" or ");
				}
			}
			hqlInfo.setParameter("orgid_"+i, "%"+orgId+"%");
		}
		whereBlock.append(" )");
		return whereBlock;
	}

	/**
	 * 获取部门下的子部门
	 * 
	 * @throws Exception
	 */
	public static List<String> getchildDept(String fdDeptId) throws Exception {
		List<String> newList = new ArrayList<String>();
		newList.add(fdDeptId);
		HrOrganizationElement element = (HrOrganizationElement) getHrOrganizationElementService()
				.findByPrimaryKey(fdDeptId);
		if (!ArrayUtil.isEmpty(element.getFdChildren())) {
			List<HrOrganizationElement> childs = element
					.getFdChildren();
			List<String> childIds = new ArrayList<String>();
			for (HrOrganizationElement child : childs) {
				// 子级如果是部门或者机构
				if (child.getFdOrgType() == 1
						|| child.getFdOrgType() == 2) {
					childIds.add(child.getFdId());
				} else {
					continue;
				}
			}
			if (!ArrayUtil.isEmpty(childIds)) {
				newList.addAll(getDeptIds(childIds));
			}
		}
		return newList;
	}

	private static List<String> getDeptIds(List<String> deptIds)
			throws Exception {
		List<String> newDeptIds = new ArrayList<String>();
		newDeptIds.addAll(deptIds);
		for (String deptId : deptIds) {
			HrOrganizationElement element = (HrOrganizationElement) getHrOrganizationElementService()
					.findByPrimaryKey(deptId);
			if (!ArrayUtil.isEmpty(element.getFdChildren())) {
				List<HrOrganizationElement> childs = element
						.getFdChildren();
				List<String> childIds = new ArrayList<String>();
				for (HrOrganizationElement child : childs) {
					// 子级如果是部门或者机构
					if (child.getFdOrgType() == 1
							|| child.getFdOrgType() == 2) {
						childIds.add(child.getFdId());
					} else {
						continue;
					}
				}
				if (!ArrayUtil.isEmpty(childIds)) {
					newDeptIds.addAll(getDeptIds(childIds));
				}
			}
		}
		return newDeptIds;
	}

	/***
	 * 获取所有部门的离职人员信息。因为离职人员层级为置为0 所以使用此方法获取不同情况的离职人员
	 * 
	 * @param whereBlock
	 * @param list
	 * @param hqlInfo
	 * @return
	 * @throws Exception
	 */
	public static StringBuffer getLeavePerson(StringBuffer whereBlock,
			List list, HQLInfo hqlInfo) throws Exception {
		String staffWhereBlock = "hrStaffPersonInfo.fdHierarchyId like :fdDept0";
		for (int i = 0; i < list.size(); i++) {
			staffWhereBlock = StringUtil.linkString(staffWhereBlock, " or ",
					"hrStaffPersonInfo.fdHierarchyId like :fdDept" + i);
			hqlInfo.setParameter("fdDept" + i,
					"%" + list.get(i) + "%");
		}
		whereBlock.append(" and (" + staffWhereBlock);
		whereBlock.append(
				" or (hrStaffPersonInfo.fdHierarchyId = '0' and ");
		whereBlock.append(HQLUtil.buildLogicIN(
				"hrOrganizationElement.hbmParent.fdId",
				list) + "))");
		whereBlock.append(
				"and hrStaffPersonInfo.fdId=hrOrganizationElement.fdId ");

		String joinBlock = hqlInfo.getJoinBlock();
		if (joinBlock == null) {
			joinBlock = "";
		}
		joinBlock = ", com.landray.kmss.hr.organization.model.HrOrganizationElement hrOrganizationElement "
				+ joinBlock;
		hqlInfo.setJoinBlock(joinBlock);

		return whereBlock;
	}
}
