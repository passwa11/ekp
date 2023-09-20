package com.landray.kmss.sys.organization.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.log.model.SysLogOrganization;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.dao.ISysOrgDeptDao;
import com.landray.kmss.sys.organization.model.SysOrgDept;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgDeptService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class SysOrgDeptServiceImp extends SysOrgElementServiceImp implements ISysOrgDeptService {

	private ISysOrgDeptDao sysOrgDeptDao;

	public ISysOrgDeptDao getSysOrgDeptDao() {
		if (sysOrgDeptDao == null) {
			sysOrgDeptDao = (ISysOrgDeptDao) SpringBeanUtil.getBean("sysOrgDeptDao");
		}
		return sysOrgDeptDao;
	}

	protected ISysOrgElementService sysOrgElementService;

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(IBaseModel modelObj) throws Exception {
		// 判断是否需要更新岗位名称
		SysOrgDept dept = (SysOrgDept) modelObj;
		if (dept.getFdIsRelation() != null && dept.getFdIsRelation()) {
			String oldDeptName = getSysOrgDeptDao().getOriginalName(modelObj.getFdId());
			List<SysOrgElement> list = dept.getFdChildren();
			for (SysOrgElement element : list) {
				if (SysOrgConstant.ORG_TYPE_POST == element.getFdOrgType()) {
					// 判断岗位名称是否包含旧部门名称
					if (element.getFdName().contains(oldDeptName + "_")) {
						// 如果有包含，就替换
						element.setFdName(element.getFdName().replace(oldDeptName + "_", dept.getFdName() + "_"));
					}
				}
			}
		}

		super.update(modelObj);
	}

	@Override
	public void updateParentByDepts(String[] deptIds, String parentId)
			throws Exception {
		SysOrgElement parent = (SysOrgElement) sysOrgElementService
				.findByPrimaryKey(parentId);
		for (String deptId : deptIds) {
			SysOrgDept dept = (SysOrgDept) findByPrimaryKey(deptId);
			// 记录日志
			if (UserOperHelper.allowLogOper("changeDept", getModelName())) {
				UserOperContentHelper.putUpdate(dept).putSimple("fdParent", dept.getFdParent(), parent);
			}
			if (dept != null) {
				dept.setFdParent(parent);
				update(dept);
				RequestContext requestContext = new RequestContext(Plugin.currentRequest());
				SysLogOrganization log = SysOrgUtil.buildSysLog(requestContext);
				Object[] params = new String[] { log.getFdOperator(), dept.getFdName(),ResourceUtil.getString("sysOrgElement.dept", "sys-organization") };
				log.setFdDetails(ResourceUtil.getString("sysLogOrganization.changeDept.details", "sys-log",
						requestContext.getLocale(), params));// 设置详细信息
				log.setFdTargetId(dept.getFdId());
				getSysLogOrganizationService().add(log);
			}
		}
	}
}
