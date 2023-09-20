package com.landray.kmss.sys.restservice.server.model;

import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.restservice.server.forms.SysRestserviceServerMainForm;
import com.landray.kmss.web.annotation.RestApi;

/**

 * 对应{@link RestApi}的模型，以及一些其它信息
 * @author 陈进科
 * @since 1.0  2018年12月26日
 */
public class SysRestserviceServerMain extends BaseModel {

	/**
	 * 名称
	 */
	protected String fdName;

	/**
	 * 服务标识
	 */
	protected String fdServiceName;
	
	/**
	 * 服务文档链接
	 */
	protected String fdDocUrl;

	/**
	 * 服务名称的资源key
	 */
	protected String fdResourceKey;

	/**
	 * 服务接口类名
	 */
	protected String fdServiceClass;

	/**
	 * 访问路径
	 */
	protected String fdUriPrefix;

	/**
	 * 服务状态
	 */
	protected Integer fdServiceStatus;

	/**
	 * 启动类型
	 */
	protected String fdStartupType;

	/**
	 * 最大连接数
	 */
	protected Integer fdMaxConn;

	/**
	 * 服务超时预警
	 */
	protected Long fdTimeOut;

	/**
	 * 最大消息体长度
	 */
	protected Long fdMaxBodySize;
	
	/**
	 * 备注
	 */
	protected String fdDescription;

	/**
	 * 访问策略
	 */
	protected List<SysRestserviceServerPolicy> fdPolicy;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdResourceKey() {
		return fdResourceKey;
	}

	public void setFdResourceKey(String fdResourceKey) {
		this.fdResourceKey = fdResourceKey;
	}

	public String getFdServiceClass() {
		return fdServiceClass;
	}

	public void setFdServiceClass(String fdServiceClass) {
		this.fdServiceClass = fdServiceClass;
	}

	public String getFdDocUrl() {
		return fdDocUrl;
	}

	public void setFdDocUrl(String fdDocUrl) {
		this.fdDocUrl = fdDocUrl;
	}

	public String getFdUriPrefix() {
		return fdUriPrefix;
	}

	public void setFdUriPrefix(String fdUriPrefix) {
		this.fdUriPrefix = fdUriPrefix;
	}

	public Integer getFdServiceStatus() {
		return fdServiceStatus;
	}

	public void setFdServiceStatus(Integer fdServiceStatus) {
		this.fdServiceStatus = fdServiceStatus;
	}

	public String getFdStartupType() {
		return fdStartupType;
	}

	public void setFdStartupType(String fdStartupType) {
		this.fdStartupType = fdStartupType;
	}

	public Integer getFdMaxConn() {
		return fdMaxConn;
	}

	public void setFdMaxConn(Integer fdMaxConn) {
		this.fdMaxConn = fdMaxConn;
	}

	public Long getFdTimeOut() {
		return fdTimeOut;
	}

	public void setFdTimeOut(Long fdTimeOut) {
		this.fdTimeOut = fdTimeOut;
	}

	public String getFdDescription() {
		return fdDescription;
	}

	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
	}

	public List<SysRestserviceServerPolicy> getFdPolicy() {
		return fdPolicy;
	}

	public void setFdPolicy(List<SysRestserviceServerPolicy> fdPolicy) {
		this.fdPolicy = fdPolicy;
	}

	public Long getFdMaxBodySize() {
		return fdMaxBodySize;
	}

	public void setFdMaxBodySize(Long fdMaxBodySize) {
		this.fdMaxBodySize = fdMaxBodySize;
	}
	
	public String getFdServiceName() {
		return fdServiceName;
	}

	public void setFdServiceName(String fdServiceName) {
		this.fdServiceName = fdServiceName;
	}

	@Override
    public Class<SysRestserviceServerMainForm> getFormClass() {
		return SysRestserviceServerMainForm.class;
	}
	
	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdPolicy",
					new ModelConvertor_ModelListToString("fdPolicyIds:fdPolicyNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 190900990900L;
}
