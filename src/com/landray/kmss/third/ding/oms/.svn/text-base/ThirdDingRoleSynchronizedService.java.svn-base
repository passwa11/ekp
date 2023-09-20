package com.landray.kmss.third.ding.oms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.organization.model.SysOrgGroup;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.organization.service.ISysOrgGroupService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 同步钉钉角色到ekp(定时任务)
 * 
 * @author chw
 *
 */
public class ThirdDingRoleSynchronizedService {

	private static boolean locked = false;

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingRoleSynchronizedService.class);

	private SysQuartzJobContext jobContext = null;

	private SynchroOrgDingRole2Ekp synchroOrgDingRole2Ekp;

	public SynchroOrgDingRole2Ekp getSynchroOrgDingRole2Ekp() {
		if (synchroOrgDingRole2Ekp == null) {
			synchroOrgDingRole2Ekp = (SynchroOrgDingRole2Ekp) SpringBeanUtil
					.getBean("synchroOrgDingRole2Ekp");
		}
		return synchroOrgDingRole2Ekp;
	}

	public void triggerSynchro(SysQuartzJobContext context) {
		this.jobContext = context;
		String temp = "";
		if (locked) {
			logger.debug("存在运行中的钉钉角色同步到到EKP同步任务，当前任务中断...");
			jobContext.logMessage("存在运行中的钉钉角色同步到到EKP同步任务，当前任务中断...");
			return;
		}
		if (!"true".equals(DingConfig.newInstance().getDingEnabled())) {
			temp = "钉钉集成已经关闭，故不同步数据";
			logger.debug(temp);
			context.logMessage(temp);
			return;
		}
		if (StringUtil.isNotNull(DingConfig.newInstance().getSyncSelection())) {
			if (!"2".equals(DingConfig.newInstance().getSyncSelection())) {
				temp = "钉钉组织架构接入已经关闭，故不同步数据";
				logger.debug(temp);
				context.logMessage(temp);
				return;
			}
		} else {
			if (!"true"
					.equals(DingConfig.newInstance().getDingOmsInEnabled())) {
				temp = "钉钉组织架构接入已经关闭，故不同步数据";
				logger.debug(temp);
				context.logMessage(temp);
				return;
			}
		}
		locked = true;
		try {
			context.logMessage("----开始同步钉钉角色-----");
			long caltime = System.currentTimeMillis();
			getSynchroOrgDingRole2Ekp().synDingRoles(context);
			temp = "同步角色到ekp耗时(秒)："
					+ (System.currentTimeMillis() - caltime) / 1000;
			logger.debug(temp);
			context.logMessage(temp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			locked = false;
		}
	}
}
