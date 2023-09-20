package com.landray.kmss.sys.filestore.scheduler.remote;

import com.landray.kmss.sys.filestore.scheduler.remote.interfaces.IRemoteCADScheduler;

public class RemoteCADSchedulerImp extends RemoteSchedulerImp
		implements IRemoteCADScheduler {

	@Override
	public String getThreadName() {
		return "RemoteScheduler-CAD";
	}

	@Override
	public String getQueueConverterKey() {
		return "cadToImg";
	}

	@Override
	public String getQueueConverterType() {
		return "转换CAD";
	}
}
