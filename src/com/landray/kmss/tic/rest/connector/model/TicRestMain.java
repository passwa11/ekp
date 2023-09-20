package com.landray.kmss.tic.rest.connector.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.tic.core.common.model.TicCoreFuncBase;
import com.landray.kmss.tic.core.mapping.constant.Constant;
import com.landray.kmss.tic.rest.connector.forms.TicRestMainForm;

/**
 * Rest服务请尔方法配置
 * 
 */
@SuppressWarnings("serial")
public class TicRestMain extends TicCoreFuncBase {

	/**
	 * 是否启用开放授权
	 */
	protected Boolean fdOauthEnable;


	/**
	 * 请求方法 GET/POST
	 */
	protected String fdReqMethod;

	/**
	 * 请求的URL地址
	 */
	protected String fdReqURL;

	/**
	 * 请求的参数 json array
	 */
	protected String fdReqParam;

	/**
	 * 备注
	 */
	protected String fdRemark;

	/**
	 * 授权配置
	 */
	protected TicRestAuth ticRestAuth;


	/**
	 * 请求配置
	 */
	protected TicRestSetting ticRestSetting;


	private String fdOriParaIn;

	private String fdOriParaOut;

	protected Boolean fdCookieEnable;

	protected TicRestCookieSetting ticRestCookieSetting;
	
	private Boolean fdPrefixReqEnable;

	private TicRestPrefixReqSetting ticRestPrefixReqSetting;

	private static ModelToFormPropertyMap toFormPropertyMap;

	//设置类型默认值
	public TicRestMain(){
		setFdFuncType(Constant.FD_TYPE_REST);
	}

	/**
	 * @return 是否启用开放授权
	 */
	public Boolean getFdOauthEnable() {
		return fdOauthEnable;
	}

	/**
	 * @param fdOauthEnable
	 *            是否启用开放授权
	 */
	public void setFdOauthEnable(Boolean fdOauthEnable) {
		this.fdOauthEnable = fdOauthEnable;
	}

	public String getFdReqMethod() {
		return fdReqMethod;
	}

	public void setFdReqMethod(String fdReqMethod) {
		this.fdReqMethod = fdReqMethod;
	}

	public String getFdReqURL() {
		return fdReqURL;
	}

	public void setFdReqURL(String fdReqURL) {
		this.fdReqURL = fdReqURL;
	}

	public String getFdReqParam() {
		return (String) readLazyField("fdReqParam", this.fdReqParam);
	}

	public void setFdReqParam(String fdReqParam) {
		this.fdReqParam = (String) writeLazyField("fdReqParam", this.fdReqParam, fdReqParam);
	}

	public String getFdRemark() {
		return fdRemark;
	}

	public void setFdRemark(String fdRemark) {
		this.fdRemark = fdRemark;
	}

	public TicRestAuth getTicRestAuth() {
		return ticRestAuth;
	}

	public void setTicRestAuth(TicRestAuth ticRestAuth) {
		this.ticRestAuth = ticRestAuth;
	}

	public TicRestSetting getTicRestSetting() {
		return ticRestSetting;
	}

	public void setTicRestSetting(TicRestSetting ticRestSetting) {
		this.ticRestSetting = ticRestSetting;
	}


	/**
	 * 传入参数
	 */
	public String getFdOriParaIn() {
		return (String) readLazyField("fdOriParaIn", this.fdOriParaIn);
	}

	/**
	 * 传入参数
	 */
	public void setFdOriParaIn(String fdOriParaIn) {
		this.fdOriParaIn = (String) writeLazyField("fdOriParaIn", this.fdOriParaIn, fdOriParaIn);
	}

	/**
	 * 传出参数
	 */
	public String getFdOriParaOut() {
		return (String) readLazyField("fdOriParaOut", this.fdOriParaOut);
	}

	/**
	 * 传出参数
	 */
	public void setFdOriParaOut(String fdOriParaOut) {
		this.fdOriParaOut = (String) writeLazyField("fdOriParaOut", this.fdOriParaOut, fdOriParaOut);
	}



	public Boolean getFdCookieEnable() {
		return fdCookieEnable;
	}

	public void setFdCookieEnable(Boolean fdCookieEnable) {
		this.fdCookieEnable = fdCookieEnable;
	}

	public TicRestCookieSetting getTicRestCookieSetting() {
		return ticRestCookieSetting;
	}

	public void setTicRestCookieSetting(TicRestCookieSetting ticRestCookieSetting) {
		this.ticRestCookieSetting = ticRestCookieSetting;
	}

	public Boolean getFdPrefixReqEnable() {
		return fdPrefixReqEnable;
	}

	public void setFdPrefixReqEnable(Boolean fdPrefixReqEnable) {
		this.fdPrefixReqEnable = fdPrefixReqEnable;
	}

	public TicRestPrefixReqSetting getTicRestPrefixReqSetting() {
		return ticRestPrefixReqSetting;
	}

	public void setTicRestPrefixReqSetting(TicRestPrefixReqSetting ticRestPrefixReqSetting) {
		this.ticRestPrefixReqSetting = ticRestPrefixReqSetting;
	}

	@Override
    public Class getFormClass() {
		return TicRestMainForm.class;
	}
	
	

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("ticRestSetting.fdId", "ticRestSettingId");
			toFormPropertyMap.put("ticRestSetting.docSubject", "ticRestSettingName");
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("ticRestAuth.fdId", "ticRestAuthId");
			toFormPropertyMap.put("ticRestAuth.docSubject", "ticRestAuthName");
			toFormPropertyMap.put("ticRestCookieSetting.fdId",
					"ticRestCookieSettingId");
			toFormPropertyMap.put("ticRestCookieSetting.docSubject",
					"ticRestCookieSettingName");
			
			toFormPropertyMap.put("ticRestPrefixReqSetting.fdId",
					"ticRestPrefixReqSettingId");
			toFormPropertyMap.put("ticRestPrefixReqSetting.docSubject",
					"ticRestPrefixReqSettingName");
		}
		return toFormPropertyMap;
	}

}
