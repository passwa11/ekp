package com.landray.kmss.sys.oms.in;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.oms.SysOMSConstant;
import com.landray.kmss.sys.oms.in.interfaces.IOMSSynchroInOtherProvider;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * 创建日期 2006-11-30
 * 
 * @author 吴兵 接入第三方平台定时同步服务
 */

public class OMSSynchroInOtherProviderRunner
		implements IOMSSynchroInOtherProviderRunner, SysOrgConstant, SysOMSConstant {

	public static String OMS_SYNCHRO_IN_BATCH_SIZE = "kmss.oms.in.batch.size";

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(OMSSynchroInOtherProviderRunner.class);

	private static boolean locked = false;

	@Override
    public void synchro(IOMSSynchroInOtherProvider provider, SysQuartzJobContext context) throws Exception {
		String temp = "";
		long alltime = System.currentTimeMillis();
		try {
			if (locked) {
				temp = "存在运行中的OMS数据库接入的同步任务，当前任务中断...";
				logger.info(temp);
				context.logMessage(temp);
				return;
			}
			if (provider == null) {
				temp = "OMS数据库接入的实现类为空，直接跳过";
				logger.info(temp);
				context.logMessage(temp);
				return;
			} else {
				long caltime = System.currentTimeMillis();
				provider.init();
				temp = "初始化数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
				logger.debug(temp);
				context.logMessage(temp);

				provider.handlerSynch(context);
			}
		} catch (Exception ex) {
			logger.error("OMS数据库接入的同步任务失败：" + ex);
			ex.printStackTrace();
			if (context != null) {
				context.logError(ex);
			}
		} finally {
			provider.terminate();
			temp = "整个任务总耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000;
			logger.debug(temp);
			context.logMessage(temp);
		}
	}

}
