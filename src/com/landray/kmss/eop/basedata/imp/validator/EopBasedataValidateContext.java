package com.landray.kmss.eop.basedata.imp.validator;

public class EopBasedataValidateContext {
	private String fdName;
	private String fdBean;
	private String fdMessage;
	public String getFdName() {
		return fdName;
	}
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	public String getFdBean() {
		return fdBean;
	}
	public void setFdBean(String fdBean) {
		this.fdBean = fdBean;
	}
	public String getFdMessage() {
		return fdMessage;
	}
	public void setFdMessage(String fdMessage) {
		this.fdMessage = fdMessage;
	}
	public String getReplacedString(String... val){
		int i=0;
		String msg = this.getFdMessage();
		for(String o:val){
			msg = msg.replace("{"+(i++)+"}", o);
		}
		return msg;
	}
}
