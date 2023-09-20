package com.landray.kmss.tic.rest.connector.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.tic.rest.connector.model.TicRestAuth;
import com.landray.kmss.web.action.ActionMapping;

/**
 * Rest授权配置
 * 
 */
public class TicRestAuthForm extends ExtendForm {
	

	/**
	 * 环境id
	 */
	private String fdEnviromentId;
	
	/**
	 * 源数据 id
	 */

	private String fdSourceId;
	
	/**
	 * 标题
	 */
	protected String docSubject = null;

	/**
	 * @return 标题
	 */
	public String getDocSubject() {
		return docSubject;
	}

	/**
	 * @param docSubject
	 *            标题
	 */
	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}
	/**
	 * 是否使用自定义获取开放授权的AccessToken
	 */
	protected String fdUseCustAt;

	/**
	 * @return 是否使用自定义获取开放授权的AccessToken
	 */
	public String getFdUseCustAt() {
		return fdUseCustAt;
	}

	/**
	 * @param fdUseCustAt
	 *            是否使用自定义获取开放授权的AccessToken
	 */
	public void setFdUseCustAt(String fdUseCustAt) {
		this.fdUseCustAt = fdUseCustAt;
	}
	
	/**
	 * 自定义获取开放授权的AccessToken的类
	 */
	protected String fdAccessTokenClazz;
	
	public String getFdAccessTokenClazz() {
		return fdAccessTokenClazz;
	}
	public void setFdAccessTokenClazz(String fdAccessTokenClazz) {
		this.fdAccessTokenClazz = fdAccessTokenClazz;  
	}
	
	/**
	 * 开放授权的获取AccessToken的地址
	 */
	protected String fdAccessTokenParam;
	
	public String getfdAccessTokenParam() {
		return fdAccessTokenParam;
	}

	public void setfdAccessTokenParam(String fdAccessTokenParam) {
		this.fdAccessTokenParam = fdAccessTokenParam;
	}


	/**
	 * 开放授权的AgentId
	 */
	protected String fdAgentId;
	
	public String getFdAgentId(){
		return fdAgentId;
	}

	public void setFdAgentId(String fdAgentId){
		this.fdAgentId=fdAgentId;
	}

	
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		docSubject = null;
		fdUseCustAt = null;
		fdAccessTokenClazz = null;
		fdAccessTokenParam = null;
		fdAgentId = null;
		fdEnviromentId=null;
		fdSourceId=null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return TicRestAuth.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return toModelPropertyMap;
	}

	// 业务类型
	private String fdAppType;

	public String getFdAppType() {
		return fdAppType;
	}

	public void setFdAppType(String fdAppType) {
		this.fdAppType = fdAppType;
	}
	
	public String getFdEnviromentId() {
		return fdEnviromentId;
	}

	public void setFdEnviromentId(String fdEnviromentId) {
		this.fdEnviromentId = fdEnviromentId;
	}

	public String getFdSourceId() {
		return fdSourceId;
	}

	public void setFdSourceId(String fdSourceId) {
		this.fdSourceId = fdSourceId;
	}
}
