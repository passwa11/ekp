package com.landray.kmss.sys.filestore.scheduler.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.cluster.remoting.ChannelEventListener;
import com.landray.kmss.sys.cluster.remoting.netty.ReadonlyNettyChannel;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;

@SuppressWarnings("unused")
public class ServerChannelEventListener implements ChannelEventListener {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(ServerChannelEventListener.class);

	public ServerChannelEventListener(ISysFileConvertDataService dataService) {
		this.dataService = dataService;
	}

	private ISysFileConvertDataService dataService = null;

	@Override
	public void onChannelConnect(String remoteAddress, ReadonlyNettyChannel channel) {
		logger.debug(remoteAddress + " connect");
	}

	@Override
	public void onChannelClose(String remoteAddress, ReadonlyNettyChannel channel) {
		logger.debug(remoteAddress + " close");
	}

	@Override
	public void onChannelException(String remoteAddress, ReadonlyNettyChannel channel) {
		logger.debug(remoteAddress + " exception");
		// dataService.refreshClients();
	}

	@Override
	public void onChannelIdle(String remoteAddress, ReadonlyNettyChannel channel) {
		logger.debug(remoteAddress + " idle");
	}

}
