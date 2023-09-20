package com.landray.kmss.hr.organization.oms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

public class SynchroOrgImp implements ISynchroOrg {

	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	private SynchroOrgEkpToHrImp synchroOrgEkpToHr;

	private SynchroOrgHrToEkpImp synchroOrgHrToEkp;
 
	public void setSynchroOrgEkpToHr(SynchroOrgEkpToHrImp synchroOrgEkpToHr) {
		this.synchroOrgEkpToHr = synchroOrgEkpToHr;
	}

	public void setSynchroOrgHrToEkp(SynchroOrgHrToEkpImp synchroOrgHrToEkp) {
		this.synchroOrgHrToEkp = synchroOrgHrToEkp;
	}

	/**
	 * EKP人员同步到HR人事档案
	 * @param context
	 * @author 王京
	 */
	@Override
	public void synchroPerson(SysQuartzJobContext context) {
		try {
			synchroOrgEkpToHr.synchroAddPersonEkpToHr(context);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
	}
	@Override
	public void synchroOrg(SysQuartzJobContext context) {
		try {
			HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
			//EKP - HR
			if ("true".equals(syncSetting.getEkpToHrEnable())) {
				synchroOrgEkpToHr.synchroEkpToHr(context);
				return;
			} else {
				logger.debug("EKP组织架构到人事组织架构同步已关闭，故不同步数据！");
				context.logMessage("EKP组织架构到人事组织架构同步已关闭，故不同步数据！");
			}
			//HR - EKP
			if ("true".equals(syncSetting.getHrToEkpEnable())) {
				synchroOrgHrToEkp.synchroHrToEkp(context);
				return;
			} else {
				logger.debug("人事组织架构到EKP组织架构同步已关闭，故不同步数据！");
				context.logMessage("人事组织架构到EKP组织架构同步已关闭，故不同步数据据！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
	}

}
