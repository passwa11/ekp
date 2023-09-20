package com.landray.kmss.hr.ratify.transfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.hr.ratify.util.PluginUtil;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.sys.xform.base.service.ISysFormDbTableService;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
/**
 * 人事流程模板自定义表单映射表历史文档映射迁移
 */
public class HrRatifyXformDBTableChecker implements ISysAdminTransferChecker {
	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public SysAdminTransferCheckResult check(SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		try {
			ISysFormDbTableService sysFormDbTableService = (ISysFormDbTableService) SpringBeanUtil.getBean("sysFormDbTableService");
			String sql = "sysFormDbTable.fdModelName = 'com.landray.kmss.hr.ratify.model.HrRatifyMain'";
			List<String> keys = this.getKeysByConfigs();
			if(!keys.isEmpty()) {
				sql += " and " + HQLUtil.buildLogicIN("sysFormDbTable.fdKey", keys);
			}
			List<Long> list = sysFormDbTableService.getBaseDao().findValue("count(*)", sql, null);
			if (list.get(0) > 0) {
				return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
			}
		} catch (Exception e) {
			logger.error("检查是否执行过旧数据迁移为空异常", e);
		}
		return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
	}

	/**
	 * 获取人事流程所有的key
	 * @return
	 */
	private List<String> getKeysByConfigs(){
		List<String> keys = new ArrayList<String>();
		List<Map<String, String>> configs = PluginUtil.getConfig(PluginUtil.EXTENSION_TEMPLATE_POINT_ID);
		for (Map<String, String> config : configs) {
			String fdKey = config.get("value");
			if(com.landray.kmss.util.StringUtil.isNotNull(fdKey)) {
				keys.add(fdKey);
			}
		}
		return keys;
	}


}
