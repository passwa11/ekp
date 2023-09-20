package com.landray.kmss.tic.rest.connector.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.tic.rest.connector.model.TicRestCookieSetting;
import com.landray.kmss.web.action.ActionMapping;

/**
 * Rest授权配置
 * 
 */
public class TicRestCookieSettingForm extends ExtendForm {
	
	
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

	protected String fdUseCustCt;

	public String getFdUseCustCt() {
		return fdUseCustCt;
	}

	public void setFdUseCustCt(String fdUseCustCt) {
		this.fdUseCustCt = fdUseCustCt;
	}

	/**
	 * 自定义获取开放授权的AccessToken的类
	 */
	protected String fdCookieSettingClazz;
	
	public String getFdCookieSettingClazz() {
		return fdCookieSettingClazz;
	}

	public void setFdCookieSettingClazz(String fdCookieSettingClazz) {
		this.fdCookieSettingClazz = fdCookieSettingClazz;
	}


	protected String fdCookieSettingParam;
	
	public String getFdCookieSettingParam() {
		return fdCookieSettingParam;
	}

	public void setFdCookieSettingParam(String fdCookieSettingParam) {
		this.fdCookieSettingParam = fdCookieSettingParam;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		docSubject = null;
		fdUseCustCt = null;
		fdCookieSettingClazz = null;
		fdCookieSettingParam = null;
		fdEnviromentId=null;
		fdSourceId=null;		
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return TicRestCookieSetting.class;
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
