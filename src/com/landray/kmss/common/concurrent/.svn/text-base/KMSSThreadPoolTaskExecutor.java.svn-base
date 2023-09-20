package com.landray.kmss.common.concurrent;

import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 公共线程池
 * 配置优先从kmssconfig.properties中取
 *
 * @author 严明镜
 * @version 1.0 2021年04月09日
 */
public class KMSSThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

	@Override
	public void setCorePoolSize(int corePoolSize) {
		String config = ResourceUtil.getKmssConfigString("common.thread.task.corePoolSize");
		if (StringUtil.isNotNull(config)) {
			corePoolSize = Integer.parseInt(config);
		}
		super.setCorePoolSize(corePoolSize);
	}

	@Override
	public void setMaxPoolSize(int maxPoolSize) {
		String config = ResourceUtil.getKmssConfigString("common.thread.task.maxPoolSize");
		if (StringUtil.isNotNull(config)) {
			maxPoolSize = Integer.parseInt(config);
		}
		super.setMaxPoolSize(maxPoolSize);
	}

	@Override
	public void setQueueCapacity(int queueCapacity) {
		String config = ResourceUtil.getKmssConfigString("common.thread.task.queueCapacity");
		if (StringUtil.isNotNull(config)) {
			queueCapacity = Integer.parseInt(config);
		}
		super.setQueueCapacity(queueCapacity);
	}

	@Override
	public void setKeepAliveSeconds(int keepAliveSeconds) {
		String config = ResourceUtil.getKmssConfigString("common.thread.task.keepAliveSeconds");
		if (StringUtil.isNotNull(config)) {
			keepAliveSeconds = Integer.parseInt(config);
		}
		super.setKeepAliveSeconds(keepAliveSeconds);
	}
}
