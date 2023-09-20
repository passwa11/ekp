package com.landray.kmss.sys.filestore.scheduler.remote;

import com.landray.kmss.sys.filestore.scheduler.remote.interfaces.IRemoteVideoScheduler;

public class RemoteVideoSchedulerImp extends RemoteSchedulerImp implements IRemoteVideoScheduler {

	@Override
	public String getThreadName() {
		return "RemoteScheduler-Video";
	}

	@Override
	public String getQueueConverterKey() {
		return "videoToMp4";
	}

	@Override
	public String getQueueConverterType() {
		return "转换视频";
	}
}
