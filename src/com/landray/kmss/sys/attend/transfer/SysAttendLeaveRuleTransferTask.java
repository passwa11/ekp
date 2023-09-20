package com.landray.kmss.sys.attend.transfer;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.attend.model.SysAttendConfig;
import com.landray.kmss.sys.attend.service.ISysAttendConfigService;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.IPage;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 把请假类型数据迁移到排班管理模块，统计方式默认为“按半天统计”
 *
 * @author cuiwj
 * @version 1.0 2018-08-31
 */
public class SysAttendLeaveRuleTransferTask
		implements ISysAdminTransferChecker, ISysAdminTransferTask {

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendLeaveRuleTransferTask.class);

	@Override
	public SysAdminTransferCheckResult
			check(SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		try {
			ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil
					.getBean("sysAdminTransferTaskService");
			ISysTimeLeaveRuleService sysTimeLeaveRuleService = (ISysTimeLeaveRuleService) SpringBeanUtil
					.getBean("sysTimeLeaveRuleService");
			String uuid = sysAdminTransferCheckContext.getUUID();
			List list = new ArrayList();
			list = sysAdminTransferTaskService.getBaseDao().findValue(null,
					"sysAdminTransferTask.fdUuid='" + uuid + "'", null);

			if (list.size() > 0) {
				SysAdminTransferTask sysAdminTransferTask = (SysAdminTransferTask) list
						.get(0);
				if (sysAdminTransferTask.getFdStatus() == 1) {
					return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
	}

	@Override
	public SysAdminTransferResult
			run(SysAdminTransferContext sysAdminTransferContext) {
		try {
			ISysAttendConfigService sysAttendConfigService = (ISysAttendConfigService) SpringBeanUtil
					.getBean("sysAttendConfigService");
			ISysTimeLeaveRuleService sysTimeLeaveRuleService = (ISysTimeLeaveRuleService) SpringBeanUtil
					.getBean("sysTimeLeaveRuleService");

			List list = (List) sysAttendConfigService.findFirstOne("", "");
			if (list != null && !list.isEmpty()) {
				SysAttendConfig sysAttendConfig = (SysAttendConfig) list;
				String fdOffType = sysAttendConfig.getFdOffType();
				if (StringUtil.isNotNull(fdOffType)) {
					JSONObject json = JSONObject.fromObject(fdOffType);
					Iterator itr = json.keys();
					while (itr.hasNext()) {
						try {
							String key = (String) itr.next();
							String leaveName = key;
							String leaveValue = json.getString(key);
							if (StringUtil.isNotNull(leaveName)
									&& StringUtil.isNotNull(leaveValue)) {
								SysTimeLeaveRule sysTimeLeaveRule = new SysTimeLeaveRule();
								sysTimeLeaveRule
										.setFdId(IDGenerator.generateID());
								sysTimeLeaveRule.setFdName(leaveName);
								sysTimeLeaveRule.setFdSerialNo(leaveValue);
								sysTimeLeaveRule.setFdOrder(
										Integer.parseInt(leaveValue));
								sysTimeLeaveRule.setFdIsAvailable(true);
								sysTimeLeaveRule.setFdStatType(2);// 按半天统计
								sysTimeLeaveRule.setFdStatDayType(1);// 按工作日计算
								sysTimeLeaveRule.setFdDayConvertTime(8);// 工时换算默认8小时算一天
								sysTimeLeaveRule.setDocCreateTime(new Date());
								sysTimeLeaveRule
										.setDocCreator(UserUtil.getUser());
								sysTimeLeaveRuleService.add(sysTimeLeaveRule);
							}
						} catch (Exception e) {
							logger.error("非法数据，忽略处理");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SysAdminTransferResult.OK;
	}

}
