package com.landray.kmss.tic.rest.client.error;

public class RestErrorKeys {
	private String codeKey ;
	
	private String succValue;
	
	private String msgKey ;

	private String header ;

	private String cookieStr;

	public void setCodeKey(String codeKey) {
		this.codeKey = codeKey;
	}
	
	public String getCodeKey() {
		return codeKey;
	}
		
	public void setSuccValue(String succValue) {
		this.succValue = succValue;
	}
	
	public String getSuccValue() {
		return succValue;
	}
		
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	
	public String getMsgKey() {
		return msgKey;
	}	
	
	
	public void setHeader(String header) {
		this.header = header;
	}
	
	public String getHeader() {
		return header;
	}	

	public String getCookieStr() {
		return cookieStr;
	}

	public void setCookieStr(String cookieStr) {
		this.cookieStr = cookieStr;
	}


}
