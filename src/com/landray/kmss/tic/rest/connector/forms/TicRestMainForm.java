package com.landray.kmss.tic.rest.connector.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.tic.core.common.forms.TicCoreFuncBaseForm;
import com.landray.kmss.tic.rest.connector.model.TicRestAuth;
import com.landray.kmss.tic.rest.connector.model.TicRestCookieSetting;
import com.landray.kmss.tic.rest.connector.model.TicRestMain;
import com.landray.kmss.tic.rest.connector.model.TicRestPrefixReqSetting;
import com.landray.kmss.tic.rest.connector.model.TicRestSetting;
import com.landray.kmss.web.action.ActionMapping;

/**
 * Rest服务请尔方法配置
 * 
 */
public class TicRestMainForm extends TicCoreFuncBaseForm {
	
	/**
	 * 是否启用开放授权
	 */
	protected String fdOauthEnable;

	/**
	 * @return 是否启用开放授权
	 */
	public String getFdOauthEnable() {
		return fdOauthEnable;
	}

	/**
	 * @param fdOauthEnable
	 *            是否启用开放授权
	 */
	public void setFdOauthEnable(String fdOauthEnable) {
		this.fdOauthEnable = fdOauthEnable;
	}

	/**
	 * 请求方法 GET/POST
	 */
	protected String fdReqMethod;

	public String getFdReqMethod() {
		return fdReqMethod;
	}

	public void setFdReqMethod(String fdReqMethod) {
		this.fdReqMethod = fdReqMethod;
	}

	/**
	 * 请求的URL地址
	 */
	protected String fdReqURL;

	public String getFdReqURL() {
		return fdReqURL;
	}

	public void setFdReqURL(String fdReqURL) {
		this.fdReqURL = fdReqURL;
	}

	/**
	 * 请求的头信息 json array
	 */
	protected String fdReqParam;

	public String getFdReqParam() {
		return fdReqParam;
	}

	public void setFdReqParam(String fdReqParam) {
		this.fdReqParam = fdReqParam;
	}

	/**
	 * 备注
	 */
	protected String fdRemark;

	public String getFdRemark() {
		return fdRemark;
	}

	public void setFdRemark(String fdRemark) {
		this.fdRemark = fdRemark;
	}
	
	protected String ticRestAuthId = null;

	public String getTicRestAuthId() {
		return ticRestAuthId;
	}

	public void setTicRestAuthId(String ticRestAuthId) {
		this.ticRestAuthId = ticRestAuthId;
	}

	/**
	 * 请求配置的ID
	 */
	protected String ticRestSettingId = null;
	
	public String getTicRestSettingId() {
		return ticRestSettingId;
	}
	
	public void setTicRestSettingId(String ticRestSettingId) {
		this.ticRestSettingId = ticRestSettingId;
	}
	
	/**
	 * 请求配置的名称
	 */
	protected String ticRestSettingName = null;
	
	public String getTicRestSettingName() {
		return ticRestSettingName;
	}
	
	public void setTicRestSettingName(String ticRestSettingName) {
		this.ticRestSettingName = ticRestSettingName;
	}
	
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdOauthEnable = "false";
		fdCookieEnable = "false";
		fdPrefixReqEnable = "false";
		fdReqParam = null;
		fdReqMethod = null;
		fdReqURL = null;
		fdRemark = null;
		ticRestAuthId = null;
		ticRestSettingId = null;
		ticRestSettingName = null;
		fdOriParaIn = null;
		fdOriParaOut = null;
		ticRestCookieSettingId = null;
		ticRestCookieSettingName = null;
		
		ticRestPrefixReqSettingId = null;
		ticRestPrefixReqSettingName = null;
		
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return TicRestMain.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("ticRestSettingId",
					new FormConvertor_IDToModel("ticRestSetting",
							TicRestSetting.class));
			toModelPropertyMap.put("ticRestAuthId",
					new FormConvertor_IDToModel("ticRestAuth",
							TicRestAuth.class));
			toModelPropertyMap.put("ticRestCookieSettingId",
					new FormConvertor_IDToModel("ticRestCookieSetting",
							TicRestCookieSetting.class));
			toModelPropertyMap.put("ticRestPrefixReqSettingId",
					new FormConvertor_IDToModel("ticRestPrefixReqSetting",
							TicRestPrefixReqSetting.class));
		}
		return toModelPropertyMap;
	}

	  private String fdOriParaIn;

	    private String fdOriParaOut;

	    /**
	     * 传入参数
	     */
	    public String getFdOriParaIn() {
	        return fdOriParaIn;
	    }

	    /**
	     * 传入参数
	     */
	    public void setFdOriParaIn(String fdOriParaIn) {
	        this.fdOriParaIn = fdOriParaIn;
	    }

	    /**
	     * 传出参数
	     */
	    public String getFdOriParaOut() {
	        return fdOriParaOut;
	    }

	    /**
	     * 传出参数
	     */
	    public void setFdOriParaOut(String fdOriParaOut) {
	        this.fdOriParaOut =fdOriParaOut;
	    }
	   	
	protected String fdCookieEnable;
	
	protected String ticRestCookieSettingId = null;
	
	protected String ticRestCookieSettingName = null;
	
	private String fdPrefixReqEnable;
	
	private String ticRestPrefixReqSettingId = null;

	private String ticRestPrefixReqSettingName = null;
	
	public String getFdCookieEnable() {
		return fdCookieEnable;
	}

	public void setFdCookieEnable(String fdCookieEnable) {
		this.fdCookieEnable = fdCookieEnable;
	}


	public String getTicRestCookieSettingId() {
		return ticRestCookieSettingId;
	}

	public void setTicRestCookieSettingId(String ticRestCookieSettingId) {
		this.ticRestCookieSettingId = ticRestCookieSettingId;
	}

	public String getTicRestCookieSettingName() {
		return ticRestCookieSettingName;
	}

	public void setTicRestCookieSettingName(String ticRestCookieSettingName) {
		this.ticRestCookieSettingName = ticRestCookieSettingName;
	}

	public String getFdPrefixReqEnable() {
		return fdPrefixReqEnable;
	}

	public void setFdPrefixReqEnable(String fdPrefixReqEnable) {
		this.fdPrefixReqEnable = fdPrefixReqEnable;
	}

	public String getTicRestPrefixReqSettingId() {
		return ticRestPrefixReqSettingId;
	}

	public void setTicRestPrefixReqSettingId(String ticRestPrefixReqSettingId) {
		this.ticRestPrefixReqSettingId = ticRestPrefixReqSettingId;
	}

	public String getTicRestPrefixReqSettingName() {
		return ticRestPrefixReqSettingName;
	}

	public void setTicRestPrefixReqSettingName(String ticRestPrefixReqSettingName) {
		this.ticRestPrefixReqSettingName = ticRestPrefixReqSettingName;
	}
}
