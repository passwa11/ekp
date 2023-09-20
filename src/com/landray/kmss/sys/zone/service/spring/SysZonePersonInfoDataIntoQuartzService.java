package com.landray.kmss.sys.zone.service.spring;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;

import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.zone.service.ISysZonePersonInfoDataIntoQuartzService;
import com.landray.kmss.sys.zone.service.ISysZonePersonInfoService;

public class SysZonePersonInfoDataIntoQuartzService implements
		ISysZonePersonInfoDataIntoQuartzService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysZonePersonInfoDataIntoQuartzService.class);

	protected ISysZonePersonInfoService sysZonePersonInfoService;

	public void setSysZonePersonInfoService(
			ISysZonePersonInfoService sysZonePersonInfoService) {
		this.sysZonePersonInfoService = sysZonePersonInfoService;
	}

	@Override
	public void updateInfoData(SysQuartzJobContext context) throws Exception {
		Session session = null;
		try {
			session = sysZonePersonInfoService.getBaseDao().getHibernateSession();
			// 仅同步内部组织
			String sql = "insert into sys_zone_person_info (fd_id, fd_attention_nums ,fd_fans_nums, fd_last_modified_time)"
					+ " select fd_id,?,?,? from sys_org_element"
					+ " where fd_id not in (select fd_id from sys_zone_person_info)  "
					+ " and fd_is_available=? and fd_is_business=? and fd_org_type=? and (fd_is_external is null or fd_is_external=?)";
			int rows = session.createNativeQuery(sql).addSynchronizedQuerySpace("sys_zone_person_info").setParameter(0, 0).setParameter(
							1, 0).setParameter(2, new Date()).setParameter(3, Boolean.TRUE)
					.setParameter(4, Boolean.TRUE).setParameter(5, SysOrgConstant.ORG_TYPE_PERSON)
					.setParameter(6, Boolean.FALSE).executeUpdate();
			context.logMessage("组织架构人员同步到员工黄页：共" + rows + "条记录！");
			// 对于之前同步的生态组织，需要删除
			String sql2 = "select count(fd_id) from sys_zone_person_info where fd_id in (select fd_id from sys_org_element where fd_is_external = ?)";
			Object count = session.createNativeQuery(sql2).setParameter(0, Boolean.TRUE).getSingleResult();
			if (Long.parseLong(count.toString()) > 0) {
				context.logMessage("找到生态人员：" + count + "个，执行删除操作！");
				// 有生态数据，需要删除
				sql2 = "delete from sys_zone_person_info where fd_id in (select fd_id from sys_org_element where fd_is_external = ?)";
				rows = session.createNativeQuery(sql2).addSynchronizedQuerySpace("sys_zone_person_info").setParameter(0, Boolean.TRUE).executeUpdate();
				context.logMessage("删除生态人员：" + rows + "个！");
			}
		} catch (Exception e) {
			logger.error("组织架构人员同步到员工黄页异常！", e);
			context.logError("组织架构人员同步到员工黄页异常！", e);
			throw e;
		}
	}

}
