package com.landray.kmss.tic.rest.connector.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.tic.rest.connector.forms.TicRestPrefixReqSettingForm;

/**
 * Rest前置请求配置
 * 
 */
@SuppressWarnings("serial")
public class TicRestPrefixReqSetting extends BaseModel {


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
	
	protected Boolean fdUseCustCt;
	
	protected String fdPrefixReqSettingClazz;
	
	protected String fdPrefixReqSettingParam;
	
	// 业务类型
	private String fdAppType;
	
	protected String fdPrefixReqSettingClazzParam;	

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

	public Boolean getFdUseCustCt() {
		return fdUseCustCt;
	}

	public void setFdUseCustCt(Boolean fdUseCustCt) {
		this.fdUseCustCt = fdUseCustCt;
	}
		
	public String getFdPrefixReqSettingClazz() {
		return fdPrefixReqSettingClazz;
	}

	public void setFdPrefixReqSettingClazz(String fdPrefixReqSettingClazz) {
		this.fdPrefixReqSettingClazz = fdPrefixReqSettingClazz;
	}

	public String getFdPrefixReqSettingParam() {
		return fdPrefixReqSettingParam;
	}

	public void setFdPrefixReqSettingParam(String fdPrefixReqSettingParam) {
		this.fdPrefixReqSettingParam = fdPrefixReqSettingParam;
	}

	@Override
    public Class getFormClass() {
		return TicRestPrefixReqSettingForm.class;
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

	public String getFdPrefixReqSettingClazzParam() {
		return fdPrefixReqSettingClazzParam;
	}

	public void setFdPrefixReqSettingClazzParam(String fdPrefixReqSettingClazzParam) {
		this.fdPrefixReqSettingClazzParam = fdPrefixReqSettingClazzParam;
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
