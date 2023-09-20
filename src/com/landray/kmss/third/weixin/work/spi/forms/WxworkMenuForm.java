package com.landray.kmss.third.weixin.work.spi.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.weixin.work.spi.model.WxworkMenuModel;

public class WxworkMenuForm extends ExtendForm {
	protected String fdAgentId;
	
	public String getFdAgentId(){
		return fdAgentId;
	}
	
	public void setFdAgentId(String fdAgentId){
		this.fdAgentId=fdAgentId;
	}
	
	protected String fdAgentName;
	
	public String getFdAgentName(){
		return fdAgentName;
	}
	
	public void setFdAgentName(String fdAgentName){
		this.fdAgentName=fdAgentName;
	}
	
	private String fdMenuJson = null;

	public String getFdMenuJson() {
		return fdMenuJson;
	}

	public void setFdMenuJson(String fdMenuJson) {
		this.fdMenuJson = fdMenuJson;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdAgentId = null;
		fdAgentName = null;
		fdMenuJson = null;
		super.reset(mapping, request);
	}

	@Override
    public Class<WxworkMenuModel> getModelClass() {
		return WxworkMenuModel.class;
	}

}
