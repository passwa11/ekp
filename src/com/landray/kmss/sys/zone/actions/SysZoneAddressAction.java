package com.landray.kmss.sys.zone.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.DataAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLWrapper;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.zone.service.ISysZonePersonInfoService;
import com.landray.kmss.util.ArrayUtil; 
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import edu.emory.mathcs.backport.java.util.Arrays;

public class SysZoneAddressAction extends DataAction {

	@Override
	protected IBaseService getCategoryServiceImp(HttpServletRequest request) {
		return null;
	}

	@Override
	protected String getParentProperty() {
		return null;
	}

	protected ISysOrgPersonService sysOrgPersonService = null;

	@Override
	protected ISysOrgPersonService getServiceImp(HttpServletRequest request) {
		if (sysOrgPersonService == null) {
            sysOrgPersonService = (ISysOrgPersonService) getBean("sysOrgPersonService");
        }
		return sysOrgPersonService;
	}

	protected ISysOrgCoreService sysOrgCoreService = null;

	protected ISysOrgCoreService getSysOrgCoreServiceImp() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}
	
	protected ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService = null;

	protected ISysOrganizationStaffingLevelService getStaffingServiceImp(HttpServletRequest request) {
		if (sysOrganizationStaffingLevelService == null) {
            sysOrganizationStaffingLevelService = (ISysOrganizationStaffingLevelService) getBean("sysOrganizationStaffingLevelService");
        }
		return sysOrganizationStaffingLevelService;
	}
	
	protected ISysZonePersonInfoService sysZonePersonInfoService = null;

	protected ISysZonePersonInfoService getSysZonePersonInfoServiceImp() {
		if (sysZonePersonInfoService == null) {
			sysZonePersonInfoService = (ISysZonePersonInfoService) getBean("sysZonePersonInfoService");
		}
		return sysZonePersonInfoService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		// 自定义parent参数的逻辑
		String[] hbmParentIds = cv.polls("hbmParent");
		CriteriaUtil.buildHql(cv, hqlInfo, SysOrgPerson.class);
		String whereBlock = hqlInfo.getWhereBlock();
		SysOrgPerson anonymous = getSysOrgCoreServiceImp().getAnonymousPerson();
		String ids = "";
		if (anonymous != null) {
            ids = "'" + anonymous.getFdId() + "'";
        }
		ids = StringUtil.linkString(ids, ",", "'" + SysOrgConstant.ORG_PERSON_EVERYONE_ID + "'");
		whereBlock = StringUtil.linkString(whereBlock, " and ", " sysOrgPerson.fdId not in(" + ids + ")");
		String available = request.getParameter("available");
		String parent = request.getParameter("parent");
		String isAbandon = request.getParameter("abandon");
		String isBussiness = request.getParameter("bussiness");
		whereBlock = StringUtil.linkString(whereBlock, " and ", " sysOrgPerson.fdIsAvailable= :fdIsAvailable ");
		if ("false".equals(available)) {
			hqlInfo.setParameter("fdIsAvailable", Boolean.FALSE);
		} else {
			hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		}

		// 业务相关
		whereBlock = StringUtil.linkString(whereBlock, " and ", "sysOrgPerson.fdIsBusiness=:fdIsBusiness");
		if ("false".equals(isBussiness)) {
			hqlInfo.setParameter("fdIsBusiness", Boolean.FALSE);
		} else {
			hqlInfo.setParameter("fdIsBusiness", Boolean.TRUE);
		}
		if (parent != null) {
			if ("".equals(parent)) {
				whereBlock += " and sysOrgPerson.hbmParent is null ";
			} else {
				whereBlock += " and sysOrgPerson.hbmParent.fdId =:hbmParentFdId ";
				hqlInfo.setParameter("hbmParentFdId", parent);
			}
		}

		if (StringUtil.isNotNull(isAbandon)) {
			whereBlock += " and sysOrgPerson.fdIsAbandon= :isAbandon ";
			if ("true".equals(isAbandon)) {
				hqlInfo.setParameter("isAbandon", Boolean.TRUE);
			} else {
				hqlInfo.setParameter("isAbandon", Boolean.FALSE);
			}
		} else {
			whereBlock += " and (sysOrgPerson.fdIsAbandon =:notAbandon or sysOrgPerson.fdIsAbandon is null) ";
			hqlInfo.setParameter("notAbandon", Boolean.FALSE);
		}
		// 名字 邮箱 电话查询
		String personInfo = request.getParameter("q.fdPersonInfo");
		if (StringUtil.isNotNull(personInfo)) {
			String whereBlockPerson = "";
			personInfo = personInfo.trim();
			personInfo = personInfo.replaceAll("\\s+", "");
			// 邮件地址查询
			whereBlockPerson = StringUtil.linkString(whereBlockPerson, " or ", "sysOrgPerson.fdEmail like :personInfo");
			// 手机号码查询
			whereBlockPerson = StringUtil.linkString(whereBlockPerson, " or ", "sysOrgPerson.fdMobileNo like :personInfo");
			//姓名查询
			whereBlockPerson = StringUtil.linkString(whereBlockPerson, " or ", "sysOrgPerson.fdName like :personInfo");
			
			whereBlock = StringUtil.linkString(whereBlockPerson, " and ", "( " + whereBlock + " )");
			
			hqlInfo.setParameter("personInfo", "%" + personInfo + "%");
			
		}

		String[] fdIds = cv.get("_fdId");
		if (fdIds != null && fdIds.length > 0) {
			HQLWrapper hqlW = HQLUtil.buildPreparedLogicIN("sysOrgPerson.fdId", "sysOrgPerson" + "0_", Arrays.asList(fdIds));
			whereBlock = StringUtil.linkString(whereBlock, " and ", hqlW.getHql());
			hqlInfo.setParameter(hqlW.getParameterList());
		}
		hqlInfo.setWhereBlock(whereBlock);
		
		// 职级可见性过滤
		getStaffingServiceImp(request)
				.getPersonStaffingLevelFilterHQLInfo(hqlInfo);
		
		
		String  orderby = hqlInfo.getOrderBy();
		if(StringUtil.isNotNull(orderby)){
			if(orderby.trim().startsWith("fdOrder")) {
				orderby = getSysZonePersonInfoServiceImp()
							.nullLastHQL(orderby, "fdOrder", 
									orderby.trim().endsWith("desc"));
				orderby += " ,fdNamePinYin asc, fdCreateTime desc";
			} else if(orderby.trim().startsWith("fdNamePinYin")) {
				orderby += " ,fdOrder asc";
			}
		}
		hqlInfo.setOrderBy(orderby);
		
		
		if (hbmParentIds != null && hbmParentIds.length > 0) {
			this.buildOrgHQL(hqlInfo, hbmParentIds);
		}

	}

	@SuppressWarnings("unchecked")
	private void buildOrgHQL(HQLInfo hqlInfo, String[] parentIds) throws Exception {
		String whereBlock = null;
		StringBuilder blockBuilder = new StringBuilder();
		String key = "hbmParent";
		String shortName = "sysOrgPerson";
		ISysOrgElementService __service = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
		for (int i = 0; i < parentIds.length; i++) {
			String value = parentIds[i];
			String nkey = key + i;
			if (i > 0) {
				blockBuilder.append(" or ");
			}
			blockBuilder.append("(").append(shortName).append(".").append(key).append(".fdHierarchyId like :").append(nkey).append(") ");
			hqlInfo.setParameter(nkey, ((SysOrgElement) __service.findByPrimaryKey(value)).getFdHierarchyId() + "%");
		}

		whereBlock = blockBuilder.toString();

		// 查找出机构或部门下所属岗位的人员
		HQLInfo postHQL = new HQLInfo();
		String _whereBlock = SysOrgHQLUtil.buildWhereBlock(SysOrgConstant.ORG_TYPE_POST, "sysOrgElement.hbmParent.fdId in(:hbmParentId)", "sysOrgElement");
		postHQL.setParameter("hbmParentId", Arrays.asList(parentIds));
		postHQL.setWhereBlock(_whereBlock);
		postHQL.setSelectBlock("sysOrgElement.fdId");
		List<String> postIds = __service.findList(postHQL);

		if (!ArrayUtil.isEmpty(postIds)) {
			String sql = "select pp.fd_personid from sys_org_post_person pp where pp.fd_postid in(:postIds)";
			IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
			List<String> personList = (List<String>) baseDao.getHibernateSession().createNativeQuery(sql).setParameterList("postIds", postIds).list();
			if (!ArrayUtil.isEmpty(personList)) {
				whereBlock = StringUtil.linkString(whereBlock, " or ", "(sysOrgPerson.fdId in(:postPersonIds))");
				hqlInfo.setParameter("postPersonIds", personList);
			}
		}

		String oWhere = hqlInfo.getWhereBlock();
		hqlInfo.setWhereBlock(StringUtil.linkString(oWhere, " and ", whereBlock));
	}

}
