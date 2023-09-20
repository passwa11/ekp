package com.landray.kmss.sys.portal.cache;

import com.landray.kmss.sys.cache.CacheConfig;
import com.landray.kmss.sys.cluster.interfaces.message.IMessage;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageQueue;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageReceiver;
import com.landray.kmss.sys.cluster.interfaces.message.UniqueMessageQueue;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ClassUtils;
import org.slf4j.Logger;

/**
 * 门户部件数据源缓存配置集群接收处理器
 *
 * @author 潘永辉
 * @dataTime 2022年2月12日 上午8:55:25
 */
public class PortalCacheConfigHandler implements IMessageReceiver {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PortalCacheConfigHandler.class);

	protected IMessageQueue messageQueue = new UniqueMessageQueue();

	@Override
	public IMessageQueue getMessageQueue() {
		return messageQueue;
	}

	@Override
	public void receiveMessage(IMessage message) throws Exception {
		if (message instanceof CacheConfigMessage) {
			CacheConfigMessage configMessage = (CacheConfigMessage) message;
			// 接收到缓存配置更新，需要同步到本节点
			if(logger.isDebugEnabled()) {
				StringBuffer str = new StringBuffer();
				str.append("接收到门户数据源缓存配置更新消息，消息内容【");
				str.append("缓存类名：").append(ArrayUtil.asList(configMessage.getModelNames()));
				if (configMessage.getMaxElementsInMemory() != null) {
					str.append("，").append("maxElementsInMemory：").append(configMessage.getMaxElementsInMemory());
				}
				if (configMessage.getCacheType() != null) {
					str.append("，").append("cacheType：").append(configMessage.getCacheType());
				}
				if (configMessage.getOverflowToDisk() != null) {
					str.append("，").append("overflowToDisk：").append(configMessage.getOverflowToDisk());
				}
				if (configMessage.getEternal() != null) {
					str.append("，").append("eternal：").append(configMessage.getEternal());
				}
				if (configMessage.getTimeToLiveSeconds() != null) {
					str.append("，").append("timeToLiveSeconds：").append(configMessage.getTimeToLiveSeconds());
				}
				if (configMessage.getTimeToIdleSeconds() != null) {
					str.append("，").append("timeToIdleSeconds：").append(configMessage.getTimeToIdleSeconds());
				}
				str.append("】。");
				logger.debug(str.toString());
			}
			for (String modelName : configMessage.getModelNames()) {
				Class<?> modelClass = ClassUtils.forName(modelName);
				CacheConfig config = CacheConfig.get(modelClass);
				if (configMessage.getMaxElementsInMemory() != null) {
					config.maxElementsInMemory = configMessage.getMaxElementsInMemory();
				}
				if (configMessage.getCacheType() != null) {
					config.cacheType = configMessage.getCacheType();
				}
				if (configMessage.getOverflowToDisk() != null) {
					config.overflowToDisk = configMessage.getOverflowToDisk();
				}
				if (configMessage.getEternal() != null) {
					config.eternal = configMessage.getEternal();
				}
				if (configMessage.getTimeToLiveSeconds() != null) {
					config.timeToLiveSeconds = configMessage.getTimeToLiveSeconds();
				}
				if (configMessage.getTimeToIdleSeconds() != null) {
					config.timeToIdleSeconds = configMessage.getTimeToIdleSeconds();
				}
			}
		}
	}

}
