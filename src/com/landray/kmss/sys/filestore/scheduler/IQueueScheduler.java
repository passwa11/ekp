package com.landray.kmss.sys.filestore.scheduler;

public interface IQueueScheduler {
	public void stopScheduler();

	public void startScheduler();

	public void reDistribute();
}
