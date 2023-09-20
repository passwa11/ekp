package com.landray.kmss.tic.soap.connector.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.tic.core.common.model.TicCoreFuncBase;
import com.landray.kmss.tic.core.mapping.constant.Constant;
import com.landray.kmss.tic.soap.connector.forms.TicSoapMainForm;

/**
 * WEBSERVCIE服务函数
 * 
 * @author
 * @version 1.0 2012-08-06
 */
@SuppressWarnings("serial")
public class TicSoapMain extends TicCoreFuncBase {
	//设置类型默认值
	public TicSoapMain(){
		setFdFuncType(Constant.FD_TYPE_SOAP);
	}
	
	
//	/**
//	 * 标题
//	 */
//	protected String docSubject;
//
//	/**
//	 * @return 标题
//	 */
//	public String getDocSubject() {
//		return docSubject;
//	}
//
//	/**
//	 * @param docSubject
//	 *            标题
//	 */
//	public void setDocSubject(String docSubject) {
//		this.docSubject = docSubject;
//	}
//
//	/**
//	 * 文档状态
//	 */
//	protected String docStatus;
//
//	/**
//	 * @return 文档状态
//	 */
//	public String getDocStatus() {
//		return docStatus;
//	}
//
//	/**
//	 * @param docStatus
//	 *            文档状态
//	 */
//	public void setDocStatus(String docStatus) {
//		this.docStatus = docStatus;
//	}



	/**
	 * 映射模板
	 */
	protected String wsMapperTemplate;

	/**
	 * @return 映射模板
	 */
	public String getWsMapperTemplate() {
		return (String) readLazyField("wsMapperTemplate", wsMapperTemplate);
	}

	/**
	 * @param wsMapperTemplate
	 *            映射模板
	 */
	public void setWsMapperTemplate(String wsMapperTemplate) {
		this.wsMapperTemplate = (String) writeLazyField("wsMapperTemplate",
				this.wsMapperTemplate, wsMapperTemplate);
	}

	/**
	 * soap版本
	 */
	protected String wsSoapVersion;

	/**
	 * @return soap版本
	 */
	public String getWsSoapVersion() {
		return wsSoapVersion;
	}

	/**
	 * @param wsSoapVersion
	 *            soap版本
	 */
	public void setWsSoapVersion(String wsSoapVersion) {
		this.wsSoapVersion = wsSoapVersion;
	}

	/**
	 * 绑定函数
	 */
	protected String wsBindFunc;

	/**
	 * @return 绑定函数
	 */
	public String getWsBindFunc() {
		return wsBindFunc;
	}

	/**
	 * @param wsBindFunc
	 *            绑定函数
	 */
	public void setWsBindFunc(String wsBindFunc) {
		this.wsBindFunc = wsBindFunc;
	}

	/**
	 * 备注
	 */
	protected String wsMarks;

	/**
	 * @return 备注
	 */
	public String getWsMarks() {
		return wsMarks;
	}

	/**
	 * @param wsMarks
	 *            备注
	 */
	public void setWsMarks(String wsMarks) {
		this.wsMarks = wsMarks;
	}

	/**
	 * 函数说明
	 */
	protected String wsBindFuncInfo;

	/**
	 * @return 函数说明
	 */
	public String getWsBindFuncInfo() {
		return wsBindFuncInfo;
	}

	/**
	 * @param wsBindFuncInfo
	 *            函数说明
	 */
	public void setWsBindFuncInfo(String wsBindFuncInfo) {
		this.wsBindFuncInfo = wsBindFuncInfo;
	}


	/**
	 * 所属服务
	 */
	protected TicSoapSetting ticSoapSetting;


	public TicSoapSetting getTicSoapSetting() {
		return ticSoapSetting;
	}

	public void setTicSoapSetting(TicSoapSetting ticSoapSetting) {
		this.ticSoapSetting = ticSoapSetting;
	}



	@Override
    public Class getFormClass() {
		return TicSoapMainForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			// toFormPropertyMap.put("docCategory.fdId", "docCategoryId");
			// toFormPropertyMap.put("docCategory.fdName", "docCategoryName");
/*			toFormPropertyMap.put("wsServerSetting.fdId", "wsServerSettingId");
			toFormPropertyMap.put("wsServerSetting.docSubject",
					"wsServerSettingName");*/
//			修正属性名称
			toFormPropertyMap.put("ticSoapSetting.fdId", "wsServerSettingId");
			toFormPropertyMap.put("ticSoapSetting.docSubject",
					"wsServerSettingName");
			
			
			// toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			// toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
		}
		return toFormPropertyMap;
	}

}
