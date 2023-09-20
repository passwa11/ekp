package com.landray.kmss.sys.organization.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.log.model.SysLogOrganization;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.dao.ISysOrgOrgDao;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgOrg;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgOrgService;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class SysOrgOrgServiceImp extends SysOrgElementServiceImp implements
		ISysOrgOrgService {
	private ISysOrgOrgDao getDao() {
		return (ISysOrgOrgDao) getBaseDao();
	}

	private ISysOrgOrgDao sysOrgOrgDao = null;

	public ISysOrgOrgDao getSysOrgOrgDao() {
		if (sysOrgOrgDao == null) {
			sysOrgOrgDao = (ISysOrgOrgDao) SpringBeanUtil
					.getBean("sysOrgOrgDao");
		}
		return sysOrgOrgDao;
	}

	private ISysOrgElementService sysOrgElementService;

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	/**
	 * 将部门更新为机构
	 * 
	 * @param deptIds
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean updateDeptToOrg(String deptId) throws Exception {
		boolean flag = true;
		// 执行SQL语句系列更新数据库SYS组织架构
		if (getDao().setDeptToOrg(deptId)) {
			// 更新SYS组织架构层级关系
			getDao().updateRelation();
		} else {
			flag = false;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(IBaseModel modelObj) throws Exception {
		SysOrgOrg org = (SysOrgOrg) modelObj;
		if (org.getFdIsRelation() != null && org.getFdIsRelation()) {
			String oldOrgName = getSysOrgOrgDao().getOriginalName(
					modelObj.getFdId());
			List<SysOrgElement> list = org.getFdChildren();
			for (SysOrgElement sysOrgElement : list) {
				if (SysOrgConstant.ORG_TYPE_POST == sysOrgElement
						.getFdOrgType()) {
					if (sysOrgElement.getFdName().contains(oldOrgName + "_")) {
						sysOrgElement.setFdName(sysOrgElement.getFdName()
								.replace(oldOrgName + "_",
										org.getFdName() + "_"));
					}
				}
			}

		}
		super.update(modelObj);
	}

	@Override
	public void updateParentByOrgs(String[] orgIds, String parentId)
			throws Exception {
		SysOrgElement parent = (SysOrgElement) sysOrgElementService
				.findByPrimaryKey(parentId);
		for (String orgId : orgIds) {
			SysOrgOrg org = (SysOrgOrg) findByPrimaryKey(orgId);
			// 记录日志
			if (UserOperHelper.allowLogOper("changeDept", getModelName())) {
				UserOperContentHelper.putUpdate(org).putSimple("fdParent", org.getFdParent(), parent);
			}
			if (org != null) {
				org.setFdParent(parent);
				update(org);
				RequestContext requestContext = new RequestContext(Plugin.currentRequest());
				SysLogOrganization log = SysOrgUtil.buildSysLog(requestContext);
				Object[] params = new String[] { log.getFdOperator(), org.getFdName(),ResourceUtil.getString("sysOrgElement.org", "sys-organization")  };
				log.setFdDetails(ResourceUtil.getString("sysLogOrganization.changeDept.details", "sys-log",
						requestContext.getLocale(), params));// 设置详细信息
				log.setFdTargetId(org.getFdId());
				getSysLogOrganizationService().add(log);
			}
		}
	}

	@Override
	public boolean parentIsOrg(String deptId) throws Exception {
		SysOrgElement org = (SysOrgElement) sysOrgElementService
				.findByPrimaryKey(deptId);
		if(org.getHbmParent() != null
				&& SysOrgElement.ORG_TYPE_ORG != org.getHbmParent().getFdOrgType().intValue()) {
            return false;
        } else {
            return true;
        }
	}
	
	@Override
	public List getUnorganizedTypeOrg() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysOrgElement.fdName=:fdName and sysOrgElement.fdIsExternal=:fdIsExternal and sysOrgElement.fdIsAvailable=:fdIsAvailable");
		hqlInfo.setParameter("fdName", "无组织类型");
		hqlInfo.setParameter("fdIsExternal", true);
		hqlInfo.setParameter("fdIsAvailable", true);
		return sysOrgElementService.findList(hqlInfo);
	}
}
