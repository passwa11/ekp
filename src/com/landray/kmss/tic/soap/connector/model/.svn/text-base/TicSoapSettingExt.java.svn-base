package com.landray.kmss.tic.soap.connector.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.tic.soap.connector.forms.TicSoapSettingExtForm;

/**
 * WEBSERVICE服务配置扩展
 * 
 * @author 
 * @version 1.0 2012-08-06
 */
public class TicSoapSettingExt extends BaseModel {

	/**
	 * 扩展配置名字
	 */
	protected String fdWsExtName;
	
	/**
	 * @return 扩展配置名字
	 */
	public String getFdWsExtName() {
		return fdWsExtName;
	}
	
	/**
	 * @param fdWsExtName 扩展配置名字
	 */
	public void setFdWsExtName(String fdWsExtName) {
		this.fdWsExtName = fdWsExtName;
	}
	
	/**
	 * 扩展配置内容
	 */
	protected String fdWsExtValue;
	
	/**
	 * @return 扩展配置内容
	 */
	public String getFdWsExtValue() {
		return fdWsExtValue;
	}
	
	/**
	 * @param fdWsExtValue 扩展配置内容
	 */
	public void setFdWsExtValue(String fdWsExtValue) {
		this.fdWsExtValue = fdWsExtValue;
	}
	
	/**
	 * WEBSERVICE服务配置
	 */
	protected TicSoapSetting fdServer;
	
	/**
	 * @return WEBSERVICE服务配置
	 */
	public TicSoapSetting getFdServer() {
		return fdServer;
	}
	
	/**
	 * @param fdServer WEBSERVICE服务配置
	 */
	public void setFdServer(TicSoapSetting fdServer) {
		this.fdServer = fdServer;
	}
	
	@Override
    public Class getFormClass() {
		return TicSoapSettingExtForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdServer.fdId", "fdServerId");
			toFormPropertyMap.put("fdServer.docSubject", "fdServerName");
		}
		return toFormPropertyMap;
	}
}
