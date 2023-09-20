package com.landray.kmss.third.weixin.mutil.spi.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.weixin.mutil.spi.model.WxworkMutilMenuModel;
import com.landray.kmss.web.action.ActionMapping;

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
    public Class<WxworkMutilMenuModel> getModelClass() {
		return WxworkMutilMenuModel.class;
	}

	/**
	 * 所属企业微信标识
	 */
	public String fdWxKey;

	public String getFdWxKey() {
		return fdWxKey;
	}

	public void setFdWxKey(String fdWxKey) {
		this.fdWxKey = fdWxKey;
	}

}
