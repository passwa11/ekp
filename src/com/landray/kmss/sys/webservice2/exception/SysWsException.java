package com.landray.kmss.sys.webservice2.exception;

import com.landray.kmss.util.ResourceUtil;

/**
 * 自定义异常
 * 
 * @author Jeff
 * 
 */
public class SysWsException extends Exception {

	public SysWsException() {
		super();
	}

	public SysWsException(String key) {
		super(ResourceUtil.getString(key, "sys-webservice2"));
	}

	public SysWsException(String key, Object arg) {
		super(ResourceUtil.getString(key, "sys-webservice2", null, arg));
	}

	public SysWsException(String key, Object[] args) {
		super(ResourceUtil.getString(key, "sys-webservice2", null, args));
	}
}
