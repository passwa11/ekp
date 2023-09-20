package com.landray.kmss.tic.soap.connector.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.tic.core.common.forms.TicCoreFuncBaseForm;
import com.landray.kmss.tic.soap.connector.model.TicSoapMain;
import com.landray.kmss.tic.soap.connector.model.TicSoapSetting;
import com.landray.kmss.web.action.ActionMapping;


/**
 * WEBSERVCIE服务函数 Form
 * 
 * @author 
 * @version 1.0 2012-08-06
 */
public class TicSoapMainForm extends TicCoreFuncBaseForm {

	
	
	/**
	 * 映射模板
	 */
	protected String wsMapperTemplate = null;
	
	/**
	 * @return 映射模板
	 */
	public String getWsMapperTemplate() {
		return wsMapperTemplate;
	}
	
	/**
	 * @param wsMapperTemplate 映射模板
	 */
	public void setWsMapperTemplate(String wsMapperTemplate) {
		this.wsMapperTemplate = wsMapperTemplate;
	}
	
	/**
	 * soap版本
	 */
	protected String wsSoapVersion = null;
	
	/**
	 * @return soap版本
	 */
	public String getWsSoapVersion() {
		return wsSoapVersion;
	}
	
	/**
	 * @param wsSoapVersion soap版本
	 */
	public void setWsSoapVersion(String wsSoapVersion) {
		this.wsSoapVersion = wsSoapVersion;
	}
	
	/**
	 * 绑定函数
	 */
	protected String wsBindFunc = null;
	
	/**
	 * @return 绑定函数
	 */
	public String getWsBindFunc() {
		return wsBindFunc;
	}
	
	/**
	 * @param wsBindFunc 绑定函数
	 */
	public void setWsBindFunc(String wsBindFunc) {
		this.wsBindFunc = wsBindFunc;
	}
	
	/**
	 * 备注
	 */
	protected String wsMarks = null;
	
	/**
	 * @return 备注
	 */
	public String getWsMarks() {
		return wsMarks;
	}
	
	/**
	 * @param wsMarks 备注
	 */
	public void setWsMarks(String wsMarks) {
		this.wsMarks = wsMarks;
	}
	
	/**
	 * 函数说明
	 */
	protected String wsBindFuncInfo = null;
	
	/**
	 * @return 函数说明
	 */
	public String getWsBindFuncInfo() {
		return wsBindFuncInfo;
	}
	
	/**
	 * @param wsBindFuncInfo 函数说明
	 */
	public void setWsBindFuncInfo(String wsBindFuncInfo) {
		this.wsBindFuncInfo = wsBindFuncInfo;
	}
	
	
	/**
	 * 所属服务的ID
	 */
	protected String wsServerSettingId = null;
	
	/**
	 * @return 所属服务的ID
	 */
	public String getWsServerSettingId() {
		return wsServerSettingId;
	}
	
	/**
	 * @param wsServerSettingId 所属服务的ID
	 */
	public void setWsServerSettingId(String wsServerSettingId) {
		this.wsServerSettingId = wsServerSettingId;
	}
	
	/**
	 * 所属服务的名称
	 */
	protected String wsServerSettingName = null;
	
	/**
	 * @return 所属服务的名称
	 */
	public String getWsServerSettingName() {
		return wsServerSettingName;
	}
	
	/**
	 * @param wsServerSettingName 所属服务的名称
	 */
	public void setWsServerSettingName(String wsServerSettingName) {
		this.wsServerSettingName = wsServerSettingName;
	}
	
	
	
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		wsMapperTemplate = null;
		wsSoapVersion = null;
		wsBindFunc = null;
		wsMarks = null;
		wsBindFuncInfo = null;
		wsServerSettingId = null;
		wsServerSettingName = null;
		
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return TicSoapMain.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("wsServerSettingId",
//					修正属性名称
					new FormConvertor_IDToModel("ticSoapSetting",
							TicSoapSetting.class));
					
					
			toModelPropertyMap.put("docCreatorId",
					new FormConvertor_IDToModel("docCreator",
						SysOrgElement.class));
		}
		return toModelPropertyMap;
	}

	

}
