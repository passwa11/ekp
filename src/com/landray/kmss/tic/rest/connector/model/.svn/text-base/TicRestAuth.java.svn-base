package com.landray.kmss.tic.rest.connector.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.tic.rest.connector.forms.TicRestMainForm;

/**
 * Rest授权配置
 * 
 */
@SuppressWarnings("serial")
public class TicRestAuth extends BaseModel {
	
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
	protected String docSubject;
	
	/**
	 * 是否使用自定义获取开放授权的AccessToken
	 */
	protected Boolean fdUseCustAt;

	
	/**
	 * 自定义获取开放授权的AccessToken的类
	 */
	protected String fdAccessTokenClazz;
	
	/**
	 * 开放授权的获取AccessToken的参数
	 */
	protected String fdAccessTokenParam;
	
	/**
	 * 开放授权的AgentId
	 */
	protected String fdAgentId;
	
	// 业务类型
	private String fdAppType;
	
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
	 * @return 是否使用自定义获取开放授权的AccessToken
	 */
	public Boolean getFdUseCustAt() {
		return fdUseCustAt;
	}

	/**
	 * @param fdUseCustAt
	 *            是否使用自定义获取开放授权的AccessToken
	 */
	public void setFdUseCustAt(Boolean fdUseCustAt) {
		this.fdUseCustAt = fdUseCustAt;
	}
	
	public String getFdAccessTokenClazz() {
		return fdAccessTokenClazz;
	}
	public void setFdAccessTokenClazz(String fdAccessTokenClazz) {
		this.fdAccessTokenClazz = fdAccessTokenClazz;  
	}
		
	public String getFdAccessTokenParam() {
		return fdAccessTokenParam;
	}

	public void setFdAccessTokenParam(String fdAccessTokenParam) {
		this.fdAccessTokenParam = fdAccessTokenParam;
	}

	public String getFdAgentId(){
		return fdAgentId;
	}

	public void setFdAgentId(String fdAgentId){
		this.fdAgentId=fdAgentId;
	}

	@Override
    public Class getFormClass() {
		return TicRestMainForm.class;
	}

	public String getFdAppType() {
		return fdAppType;
	}

	public void setFdAppType(String fdAppType) {
		this.fdAppType = fdAppType;
	}
	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
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
