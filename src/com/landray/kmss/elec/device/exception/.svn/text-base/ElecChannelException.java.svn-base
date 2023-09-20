package com.landray.kmss.elec.device.exception;

import com.landray.kmss.elec.device.client.ElecChannelErrorCodeEnum;

/**
*@author yucf
*@date  2019年7月9日
*@Description
*/

public class ElecChannelException extends Exception {
	
	private static final long serialVersionUID = 1L;

	private String code;
	
	private String desc;


	public ElecChannelException() {
		super();
	}

	public ElecChannelException(String code, String desc) {
		super();
		this.code = code;
		this.desc = desc;
	}
	
	public ElecChannelException(ElecChannelErrorCodeEnum errorCode) {
		super();
		this.code = errorCode.getCode();
		this.desc = errorCode.getDesc();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
