package com.landray.kmss.sys.organization.service.spring;

import java.util.List;
import java.util.Map;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.datainit.service.ISysDatainitProcessService;
import com.landray.kmss.sys.datainit.service.ISysDatainitSurroundInterceptor;
import com.landray.kmss.sys.datainit.service.spring.ProcessRuntime;
import com.landray.kmss.sys.organization.model.SysOrgRole;
import com.landray.kmss.sys.organization.model.SysOrgRoleConf;
import com.landray.kmss.sys.organization.model.SysOrgRoleLine;
import com.landray.kmss.sys.organization.service.ISysOrgRoleLineService;
import com.landray.kmss.sys.organization.service.ISysOrgRoleService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class SysOrgRoleConfDataInit implements ISysDatainitSurroundInterceptor {

	protected ISysDatainitProcessService sysDatainitProcessService;

	public ISysDatainitProcessService getSysDatainitProcessService() {
		if (sysDatainitProcessService == null) {
			sysDatainitProcessService = (ISysDatainitProcessService) SpringBeanUtil
					.getBean("sysDatainitProcessService");
		}
		return sysDatainitProcessService;
	}

	protected ISysOrgRoleService sysOrgRoleService;

	protected ISysOrgRoleLineService sysOrgRoleLineService;

	public SysOrgRoleConfDataInit() {
		this.sysOrgRoleService = (ISysOrgRoleService) SpringBeanUtil
				.getBean("sysOrgRoleService");
		this.sysOrgRoleLineService = (ISysOrgRoleLineService) SpringBeanUtil
				.getBean("sysOrgRoleLineService");
	}

	@Override
	public void beforeRestoreModelData(IBaseModel model,
			Map<String, Object> data, Map<String, IBaseModel> cache,
			ProcessRuntime processRuntime) throws Exception {
		return;
	}

	// 导出扩展处理
	@Override
    @SuppressWarnings("unchecked")
	public void beforeStoreModelData(IBaseModel model, Map<String, Object> data)
			throws Exception {
		if (model instanceof SysOrgRoleConf) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("sysOrgRole.fdRoleConf.fdId=:fdRoleConfId");
			hqlInfo.setParameter("fdRoleConfId", model.getFdId());
			List<SysOrgRole> sysOrgRoleList = sysOrgRoleService
					.findList(hqlInfo);
			if (!ArrayUtil.isEmpty(sysOrgRoleList)) {
				for (SysOrgRole sysOrgRole : sysOrgRoleList) {
					getSysDatainitProcessService().exportToFile(sysOrgRole);
				}
			}

			hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("sysOrgRoleLine.sysOrgRoleConf.fdId=:fdRoleLineConfId");
			hqlInfo.setParameter("fdRoleLineConfId", model.getFdId());
			List<SysOrgRoleLine> sysOrgRoleLineList = sysOrgRoleLineService
					.findList(hqlInfo);
			if (!ArrayUtil.isEmpty(sysOrgRoleLineList)) {
				for (SysOrgRoleLine sysOrgRoleLine : sysOrgRoleLineList) {
					getSysDatainitProcessService().exportToFile(sysOrgRoleLine);
				}
			}
		}
	}
}
