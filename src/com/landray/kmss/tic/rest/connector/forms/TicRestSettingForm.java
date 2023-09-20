package com.landray.kmss.tic.rest.connector.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.tic.rest.connector.model.TicRestSettCategory;
import com.landray.kmss.tic.rest.connector.model.TicRestSetting;
import com.landray.kmss.web.action.ActionMapping;


/**
 * REST服务配置 Form
 * 
 */
public class TicRestSettingForm extends ExtendForm {
	
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
	 * @param docSubject 标题
	 */
	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}
	
	/**
	 * 创建时间
	 */
	protected String docCreateTime = null;
	
	/**
	 * @return 创建时间
	 */
	public String getDocCreateTime() {
		return docCreateTime;
	}
	
	/**
	 * @param docCreateTime 创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}
	
	/**
	 * 最后修改时间
	 */
	protected String docAlterTime = null;
	
	/**
	 * @return 最后修改时间
	 */
	public String getDocAlterTime() {
		return docAlterTime;
	}
	
	/**
	 * @param docAlterTime 最后修改时间
	 */
	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}
	
	/**
	 * 是否启用
	 */
	protected String fdEnable = null;
	
	/**
	 * @return 是否启用
	 */
	public String getFdEnable() {
		return fdEnable;
	}
	
	/**
	 * @param fdEnable 是否启用
	 */
	public void setFdEnable(String fdEnable) {
		this.fdEnable = fdEnable;
	}
	

	/**
	 * 创建者的ID
	 */
	protected String docCreatorId = null;
	
	/**
	 * @return 创建者的ID
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}
	
	/**
	 * @param docCreatorId 创建者的ID
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}
	
	/**
	 * 创建者的名称
	 */
	protected String docCreatorName = null;
	
	/**
	 * @return 创建者的名称
	 */
	public String getDocCreatorName() {
		return docCreatorName;
	}
	
	/**
	 * @param docCreatorName 创建者的名称
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}
	

	/*
	 * 链接的超时时间(秒)
	 */
	protected String fdConnRequestTimeout;
	public void setFdConnRequestTimeout(String fdConnRequestTimeout) {
		this.fdConnRequestTimeout = fdConnRequestTimeout;
	}

	public String getFdConnRequestTimeout() {
		return fdConnRequestTimeout;
	}
	
	/*
	 * 建立链接的超时时间
	 */
	protected String fdConnTimeout;
	public void setFdConnTimeout(String fdConnTimeout) {
		this.fdConnTimeout = fdConnTimeout;
	}

	public String getFdConnTimeout() {
		return fdConnTimeout;
	}

	/*
	 * 默认NIO的socket超时
	 */
	protected String fdSoTimeout;
	public void setFdSoTimeout(String fdSoTimeout) {
		this.fdSoTimeout = fdSoTimeout;
	}

	public String getFdSoTimeout() {
		return fdSoTimeout;
	}
	
	/**
	 * 是否使用代理服务器
	 */
	protected String fdHttpProxy;

	public String getFdHttpProxy() {
		return fdHttpProxy;
	}

	public void setFdHttpProxy(String fdHttpProxy) {
		this.fdHttpProxy = fdHttpProxy;
	}
	
	/*
	 * 代理服务器地址
	 */
	protected String fdHttpProxyHost;
	public String getFdHttpProxyHost() {
		return fdHttpProxyHost;
	}

	public void setFdHttpProxyHost(String fdHttpProxyHost) {
		this.fdHttpProxyHost = fdHttpProxyHost;
	}

	/*
	 * 代理服务器端口
	 */
	protected String fdHttpProxyPort;
	public String getFdHttpProxyPort() {
		return fdHttpProxyPort;
	}

	public void setFdHttpProxyPort(String fdHttpProxyPort) {
		this.fdHttpProxyPort = fdHttpProxyPort;
	}
	
	/*
	 * 代理服务器用户名
	 */
	protected String fdHttpProxyUsername;
	public String getFdHttpProxyUsername() {
		return fdHttpProxyUsername;
	}

	public void setFdHttpProxyUsername(String fdHttpProxyUsername) {
		this.fdHttpProxyUsername = fdHttpProxyUsername;
	}
	
	/*
	 * 代理服务器密码
	 */
	protected String fdHttpProxyPassword;
	public String getFdHttpProxyPassword() {
		return fdHttpProxyPassword;
	}

	public void setFdHttpProxyPassword(String fdHttpProxyPassword) {
		this.fdHttpProxyPassword = fdHttpProxyPassword;
	}	
	
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		docSubject = null;
		docCreateTime = null;
		docAlterTime = null;
		fdEnable = null;
		docCreatorId = null;
		docCreatorName = null;
		settCategoryId=null;
		settCategoryName=null;

		fdConnRequestTimeout = "3000";
		fdConnTimeout = "3000";
		fdSoTimeout = "5000";
		fdHttpProxy= "false";
		fdHttpProxyHost= null;
		fdHttpProxyPort=null;
		fdHttpProxyUsername= null;
		fdHttpProxyPassword = null;
		fdEnviromentId=null;
		fdSourceId=null;
		super.reset(mapping, request);
	}
	
	@Override
    public Class getModelClass() {
		return TicRestSetting.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId",
					new FormConvertor_IDToModel("docCreator",
						SysOrgElement.class));
			toModelPropertyMap.put("settCategoryId",
					new FormConvertor_IDToModel("settCategory",
							TicRestSettCategory.class));
		}
		return toModelPropertyMap;
	}
	
	
	//所属目录ID
	protected String settCategoryId=null;

	public String getSettCategoryId() {
		return settCategoryId;
	}

	public void setSettCategoryId(String settCategoryId) {
		this.settCategoryId = settCategoryId;
	}

	//所属目录Name
	protected String settCategoryName=null;

	public String getSettCategoryName() {
		return settCategoryName;
	}

	public void setSettCategoryName(String settCategoryName) {
		this.settCategoryName = settCategoryName;
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
