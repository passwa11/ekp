package com.landray.kmss.third.ding.service;

/**
 * 考勤同步到钉钉失败重试定时任务
 * 
 * @author Terwer
 *
 */
public interface IThirdDingLeavelogSyncService {
	void updateLeaveSync() throws Exception;
}
