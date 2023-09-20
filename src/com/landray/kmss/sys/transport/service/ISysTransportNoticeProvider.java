package com.landray.kmss.sys.transport.service;


import com.landray.kmss.sys.transport.model.SysTransportImportConfig;


public interface ISysTransportNoticeProvider {

	/**
	 * 获取view页面的相关提示，如无需提示，返回null
	 * 
	 * @return 返回需要提示的文字内容
	 */
	public String getViewPageNotice(SysTransportImportConfig config);

	
}
