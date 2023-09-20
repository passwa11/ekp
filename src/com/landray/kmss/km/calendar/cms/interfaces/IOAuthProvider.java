package com.landray.kmss.km.calendar.cms.interfaces;

public interface IOAuthProvider {

	/**
	 * token是否有效，当用户未绑定或者token过期时返回false
	 */
	public boolean isTokenAvailable(String personId);

	/**
	 * 获取绑定url
	 * 
	 * @return
	 */
	public String getBindUrl();

	/**
	 * 获取取消绑定url
	 * 
	 * @return
	 */
	public String getDisBindUrl();

}
