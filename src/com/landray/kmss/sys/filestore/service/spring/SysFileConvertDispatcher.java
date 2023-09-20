package com.landray.kmss.sys.filestore.service.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.cluster.interfaces.dispatcher.IDispatcher;
import com.landray.kmss.sys.cluster.interfaces.message.DefaultMessageReceiver;
import com.landray.kmss.sys.cluster.interfaces.message.IMessage;
import com.landray.kmss.sys.filestore.scheduler.IQueueScheduler;
import com.landray.kmss.util.ResourceUtil;

public class SysFileConvertDispatcher extends DefaultMessageReceiver implements
		IDispatcher {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(SysFileConvertDispatcher.class);
	private IQueueScheduler scheduler;

	public IQueueScheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(IQueueScheduler scheduler) {
		this.scheduler = scheduler;
	}

	@Override
	public void receiveMessage(IMessage message) throws Exception {
		// 接收到集群消息立即唤醒休眠线程
		this.getScheduler().reDistribute();
	}

	@Override
	public String getName() {
		return ResourceUtil.getString(
				"filestore.hint.SysFileConvertDispatcher.info1",
				"sys-filestore");
	}

	@Override
	public void start() {
		logger.debug(ResourceUtil.getString(
				"filestore.hint.SysFileConvertDispatcher.info2",
				"sys-filestore"));
		getScheduler().startScheduler();
	}

	@Override
	public void stop() {
		logger.debug(ResourceUtil.getString(
				"filestore.hint.SysFileConvertDispatcher.info3",
				"sys-filestore"));
		getScheduler().stopScheduler();
	}

}
