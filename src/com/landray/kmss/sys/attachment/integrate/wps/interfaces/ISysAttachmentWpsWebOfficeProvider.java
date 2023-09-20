package com.landray.kmss.sys.attachment.integrate.wps.interfaces;

public interface ISysAttachmentWpsWebOfficeProvider {

	public boolean isEnabled() throws Exception;

	public String getUrl() throws Exception;

	public String getAppid() throws Exception;

	public String getAppkey() throws Exception;

	
}
