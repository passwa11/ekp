package com.landray.kmss.tic.rest.connector.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.tic.rest.connector.forms.TicRestCookieSettingForm;

/**
 * Rest授权配置
 * 
 */
@SuppressWarnings("serial")
public class TicRestCookieSetting extends BaseModel {
	
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
	
	protected String fdCookieSettingClazz;
	
	protected String fdCookieSettingParam;
	
	// 业务类型
	private String fdAppType;
	
	protected String fdCookieSettingClazzParam;	

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
		
	public String getFdCookieSettingClazz() {
		return fdCookieSettingClazz;
	}

	public void setFdCookieSettingClazz(String fdCookieSettingClazz) {
		this.fdCookieSettingClazz = fdCookieSettingClazz;
	}

	public String getFdCookieSettingParam() {
		return fdCookieSettingParam;
	}

	public void setFdCookieSettingParam(String fdCookieSettingParam) {
		this.fdCookieSettingParam = fdCookieSettingParam;
	}

	@Override
    public Class getFormClass() {
		return TicRestCookieSettingForm.class;
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

	public String getFdCookieSettingClazzParam() {
		return fdCookieSettingClazzParam;
	}

	public void setFdCookieSettingClazzParam(String fdCookieSettingClazzParam) {
		this.fdCookieSettingClazzParam = fdCookieSettingClazzParam;
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
