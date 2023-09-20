package com.landray.kmss.km.calendar.cms.interfaces;

/**
 * 共享设置接出Provider
 */
public interface ISyncOutAuthProvider {

	/**
	 * 是否要接出
	 */
	public boolean isNeedSyncro(String authId);

	/**
	 * 接出逻辑
	 */
	public void sync(String authId);

}
