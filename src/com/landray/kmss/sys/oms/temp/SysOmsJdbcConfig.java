package com.landray.kmss.sys.oms.temp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.sys.oms.OMSPlugin;
import com.landray.kmss.sys.oms.service.ISysOmsTempJdbcQuartzTaskService;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;

/**
 * jdbc 组织接入
 * 
 * @author yuLiang
 * @version 1.0 创建时间：2019年12月20日
 */
public class SysOmsJdbcConfig extends BaseAppConfig {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(SysOmsJdbcConfig.class);

	public SysOmsJdbcConfig() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getJSPUrl() {
		// TODO Auto-generated method stub
		return "/sys/oms/sys_oms_temp_trx/sysOmsTempTrx_edit.jsp";
	}

	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("sys-oms:oms.setting");
	}

	@Override
	public void save() throws Exception {
		String isall = getDataMap().get("kmss.oms.in.db.isall");
		
		//业务代码
		String omsInEnable = getDataMap().get("kmss.oms.in.db.enabled");
		if ("true".equals(omsInEnable)) {
			String name = OMSPlugin.getOtherOmsInEnabledKey("db");
			if (name != null) {
				KmssMessages messages = new KmssMessages();
				messages.addError(new KmssMessage("sys-oms:oms.in.hasEnabled", name, name));
				throw new KmssRuntimeException(messages);
			} else {
				if ("1".equals(isall)) {
					getDataMap().put("kmss.oms.temp.syn.synTimestamp", null);
				}
				super.save();
			}
		} else {
			if ("1".equals(isall)) {
				getDataMap().put("kmss.oms.temp.syn.synTimestamp", null);
			}
			super.save();
		}
		
		// 根据全量接入的字段来执行组织接入
		try {
			if ("1".equals(isall)) {
				ISysOmsTempJdbcQuartzTaskService service = (ISysOmsTempJdbcQuartzTaskService) SpringBeanUtil
						.getBean("sysOmsTempJdbcQuartzTaskService");
				service.runJob();
			}
		} catch (Exception e) {
			logger.error("执行数据库全量接入失败：", e);
		}
	}

}
