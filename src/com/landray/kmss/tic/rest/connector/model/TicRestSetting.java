package com.landray.kmss.tic.rest.connector.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.tic.rest.connector.forms.TicRestSettingForm;

/**
 * REST服务配置
 * 
 */
public class TicRestSetting extends BaseModel {
	

	/**
	 * 环境id
	 */
	private String fdEnviromentId;
	
	/**
	 * 源数据 id
	 */

	private String fdSourceId;

	/*
	 * 链接的超时时间(秒)
	 */
	protected Long fdConnRequestTimeout;

	/*
	 * 建立链接的超时时间
	 */
	protected Long fdConnTimeout;

	/*
	 * 默认NIO的socket超时
	 */
	protected Long fdSoTimeout;

	/**
	 * 是否使用代理服务器
	 */
	protected Boolean fdHttpProxy;

	/*
	 * 代理服务器地址
	 */
	protected String fdHttpProxyHost;

	/*
	 * 代理服务器端口
	 */
	protected String fdHttpProxyPort;

	/*
	 * 代理服务器用户名
	 */
	protected String fdHttpProxyUsername;

	/*
	 * 代理服务器密码
	 */
	protected String fdHttpProxyPassword;

	/**
	 * 标题
	 */
	protected String docSubject;


	/**
	 * 创建时间
	 */
	protected Date docCreateTime;


	/**
	 * 最后修改时间
	 */
	protected Date docAlterTime;

	/**
	 * 创建者
	 */
	protected SysOrgElement docCreator;

	/**
	 * 所属目录
	 */
	protected TicRestSettCategory settCategory;

	/**
	 *所属应用
	 */
	private String fdAppType;


	private static ModelToFormPropertyMap toFormPropertyMap;



	public void setFdConnRequestTimeout(Long fdConnRequestTimeout) {
		this.fdConnRequestTimeout = fdConnRequestTimeout;
	}

	public Long getFdConnRequestTimeout() {
		return fdConnRequestTimeout;
	}

	public void setFdConnTimeout(Long fdConnTimeout) {
		this.fdConnTimeout = fdConnTimeout;
	}

	public Long getFdConnTimeout() {
		return fdConnTimeout;
	}

	public void setFdSoTimeout(Long fdSoTimeout) {
		this.fdSoTimeout = fdSoTimeout;
	}

	public Long getFdSoTimeout() {
		return fdSoTimeout;
	}

	public Boolean getFdHttpProxy() {
		return fdHttpProxy;
	}

	public void setFdHttpProxy(Boolean fdHttpProxy) {
		this.fdHttpProxy = fdHttpProxy;
	}


	public String getFdHttpProxyHost() {
		return fdHttpProxyHost;
	}

	public void setFdHttpProxyHost(String fdHttpProxyHost) {
		this.fdHttpProxyHost = fdHttpProxyHost;
	}

	public String getFdHttpProxyPort() {
		return fdHttpProxyPort;
	}

	public void setFdHttpProxyPort(String fdHttpProxyPort) {
		this.fdHttpProxyPort = fdHttpProxyPort;
	}


	public String getFdHttpProxyUsername() {
		return fdHttpProxyUsername;
	}

	public void setFdHttpProxyUsername(String fdHttpProxyUsername) {
		this.fdHttpProxyUsername = fdHttpProxyUsername;
	}

	public String getFdHttpProxyPassword() {
		return fdHttpProxyPassword;
	}

	public void setFdHttpProxyPassword(String fdHttpProxyPassword) {
		this.fdHttpProxyPassword = fdHttpProxyPassword;
	}	

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
	 * @return 创建时间
	 */
	public Date getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            创建时间
	 */
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}


	/**
	 * @return 最后修改时间
	 */
	public Date getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            最后修改时间
	 */
	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}


	/**
	 * @return 创建者
	 */
	public SysOrgElement getDocCreator() {
		return docCreator;
	}

	/**
	 * @param docCreator
	 *            创建者
	 */
	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
	}

	@Override
    public Class getFormClass() {
		return TicRestSettingForm.class;
	}

	public TicRestSettCategory getSettCategory() {
		return settCategory;
	}

	public void setSettCategory(TicRestSettCategory settCategory) {
		this.settCategory = settCategory;
	}


	public String getFdAppType() {
		return fdAppType;
	}

	public void setFdAppType(String fdAppType) {
		this.fdAppType = fdAppType;
	}

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("settCategory.fdId", "settCategoryId");
			toFormPropertyMap.put("settCategory.fdName", "settCategoryName");
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
