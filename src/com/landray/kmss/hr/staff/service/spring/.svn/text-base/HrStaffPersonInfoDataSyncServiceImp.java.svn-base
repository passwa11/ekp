package com.landray.kmss.hr.staff.service.spring;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;

import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.organization.service.IHrOrganizationPostService;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoLog;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoLogService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.ArrayUtil;

/**
 * 组织架构人员数据同步到人事档案
 * 
 * @author 潘永辉 2016-12-27
 * 
 */
public class HrStaffPersonInfoDataSyncServiceImp {

	private IHrStaffPersonInfoService hrStaffPersonInfoService;
	private IHrStaffPersonInfoLogService hrStaffPersonInfoLogService;
	private ISysOrgPersonService sysOrgPersonService;

	public void setHrStaffPersonInfoService(
			IHrStaffPersonInfoService hrStaffPersonInfoService) {
		this.hrStaffPersonInfoService = hrStaffPersonInfoService;
	}

	public void setHrStaffPersonInfoLogService(
			IHrStaffPersonInfoLogService hrStaffPersonInfoLogService) {
		this.hrStaffPersonInfoLogService = hrStaffPersonInfoLogService;
	}

	public void setSysOrgPersonService(
			ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	private IHrOrganizationElementService hrOrganizationElementService;

	public void setHrOrganizationElementService(IHrOrganizationElementService hrOrganizationElementService) {
		this.hrOrganizationElementService = hrOrganizationElementService;
	}

	private IHrOrganizationPostService hrOrganizationPostService;

	public void setHrOrganizationPostService(IHrOrganizationPostService hrOrganizationPostService) {
		this.hrOrganizationPostService = hrOrganizationPostService;
	}

	/**
	 * 数据同步
	 * 
	 * @param context
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void dataSynchronization(SysQuartzJobContext context)
			throws Exception {
		Session session = null;
		try {
			session = hrStaffPersonInfoService.getBaseDao()
					.getHibernateSession();

			// 查询需要同步的数据
			String selectSql = "select e.fd_id, e.fd_hierarchy_id, e.fd_name, p.fd_login_name from sys_org_element e,sys_org_person p"
					+ " where e.fd_id not in (select fd_id from hr_staff_person_info)"
					+ " and e.fd_is_available = ? and e.fd_is_business = ? and e.fd_org_type = ? and p.fd_id=e.fd_id";
			List<Object[]> list = session.createNativeQuery(selectSql)
					.setParameter(0, Boolean.TRUE)
					.setParameter(1, Boolean.TRUE).setParameter(2,
							SysOrgConstant.ORG_TYPE_PERSON).list();

			if (list.size() > 0) {
				int rows = 0;
				String[] ids = new String[list.size()];
				List<HrStaffPersonInfo> fdPersonInfos=new ArrayList<HrStaffPersonInfo>();
				for (int i = 0; i < list.size(); i++) {
					Object[] value = list.get(i);
					ids[i] = value[0].toString();
					HrStaffPersonInfo hrStaffPersonInfo = new HrStaffPersonInfo();
					hrStaffPersonInfo.setFdId(ids[i]);
					SysOrgPerson person = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(ids[i]);
					hrStaffPersonInfo.setFdOrgPerson(person);
					hrStaffPersonInfo.setFdStatus("official");
					hrStaffPersonInfo.setFdHierarchyId(value[1].toString());
					hrStaffPersonInfo.setFdName(value[2].toString());
					if (value[3] != null) {
                        hrStaffPersonInfo.setFdLoginName(value[3].toString());
                    }
					if (ids[i].equals(hrStaffPersonInfoService.add(hrStaffPersonInfo))) {
						rows++;
						fdPersonInfos.add(hrStaffPersonInfo);
					}
				}

				if (context != null) {
					context.logMessage("组织架构人员数据同步到人事档案：共" + rows + "条记录！");
				} 
				if(!ArrayUtil.isEmpty(fdPersonInfos)) {
					// 保存日志
					buildLog(fdPersonInfos);
				}
			}
		} catch (Exception e) {
			if (context != null) {
				context.logError("组织架构人员数据同步到人事档案异常！", e);
			}
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	private void buildLog(List<HrStaffPersonInfo> fdPersonInfos) throws Exception {
		HttpServletRequest request = Plugin.currentRequest();
		String syncString = null;
		if (request != null) { // 人为同步
			syncString = "系统同步 " + fdPersonInfos.size() + " 位员工。";
		} else {
			syncString = "系统自动同步 " + fdPersonInfos.size() + " 位员工。";
		}
		HrStaffPersonInfoLog log = hrStaffPersonInfoLogService.buildPersonInfoLog("sync", syncString); 
		log.setFdTargets(fdPersonInfos);

		if (request == null) {
			log.setFdIp("-");
			log.setFdBrowser("-");
			log.setFdEquipment("-");
		}
		hrStaffPersonInfoLogService.add(log);
	}
}
