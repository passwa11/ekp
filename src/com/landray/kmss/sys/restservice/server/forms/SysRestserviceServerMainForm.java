package com.landray.kmss.sys.restservice.server.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerMain;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerPolicy;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 服务注册信息 Form
 * 
 * @author  
 */
@SuppressWarnings("serial")
public class SysRestserviceServerMainForm extends ExtendForm {

	/**
	 * 名称
	 */
	protected String fdName = null;

	/**
	 * @return 名称
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 服务接口类名
	 */
	protected String fdServiceClass = null;

	/**
	 * @return 服务接口类名
	 */
	public String getFdServiceClass() {
		return fdServiceClass;
	}

	/**
	 * @param fdServiceClass
	 *            服务接口类名
	 */
	public void setFdServiceClass(String fdServiceClass) {
		this.fdServiceClass = fdServiceClass;
	}

	/**
	 * 服务标识
	 */
	protected String fdServiceName = null;

	/**
	 * @return 服务标识
	 */
	public String getFdServiceName() {
		return fdServiceName;
	}

	/**
	 * @param fdServiceName
	 *            服务标识
	 */
	public void setFdServiceName(String fdServiceName) {
		this.fdServiceName = fdServiceName;
	}

	/**
	 * 服务文档链接
	 */
	protected String fdDocUrl = null;

	/**
	 * @return 服务文档链接
	 */
	public String getFdDocUrl() {
		return fdDocUrl;
	}

	/**
	 * @param fdDocUrl
	 *            服务文档链接
	 */
	public void setFdDocUrl(String fdDocUrl) {
		this.fdDocUrl = fdDocUrl;
	}

	/**
	 * 服务名称的资源key
	 */
	protected String fdResourceKey = null;

	/**
	 * @return 服务名称的资源key
	 */
	public String getFdResourceKey() {
		return fdResourceKey;
	}

	/**
	 * @param fdResourceKey
	 *            服务名称的资源key
	 */
	public void setFdResourceKey(String fdResourceKey) {
		this.fdResourceKey = fdResourceKey;
	}
	
	/**
	 * 访问路径
	 */
	protected String fdUriPrefix = null;

	/**
	 * @return 访问路径
	 */
	public String getFdUriPrefix() {
		return fdUriPrefix;
	}

	/**
	 * @param fdUriPrefix
	 *            访问路径
	 */
	public void setFdUriPrefix(String fdUriPrefix) {
		this.fdUriPrefix = fdUriPrefix;
	}

	/**
	 * 允许匿名访问
	 */
	protected String fdAnonymous = null;

	/**
	 * @return 允许匿名访问
	 */
	public String getFdAnonymous() {
		return fdAnonymous;
	}

	/**
	 * @param fdAnonymous
	 *            允许匿名访问
	 */
	public void setFdAnonymous(String fdAnonymous) {
		this.fdAnonymous = fdAnonymous;
	}

	/**
	 * 服务状态
	 */
	protected String fdServiceStatus = null;

	/**
	 * @return 服务状态
	 */
	public String getFdServiceStatus() {
		return fdServiceStatus;
	}

	/**
	 * @param fdServiceStatus
	 *            服务状态
	 */
	public void setFdServiceStatus(String fdServiceStatus) {
		this.fdServiceStatus = fdServiceStatus;
	}

	/**
	 * 启动类型
	 */
	protected String fdStartupType = null;

	/**
	 * @return 启动类型
	 */
	public String getFdStartupType() {
		return fdStartupType;
	}

	/**
	 * @param fdStartupType
	 *            启动类型
	 */
	public void setFdStartupType(String fdStartupType) {
		this.fdStartupType = fdStartupType;
	}

	/**
	 * 最大连接数
	 */
	protected String fdMaxConn = null;

	/**
	 * @return 最大连接数
	 */
	public String getFdMaxConn() {
		return fdMaxConn;
	}

	/**
	 * @param fdMaxConn
	 *            最大连接数
	 */
	public void setFdMaxConn(String fdMaxConn) {
		this.fdMaxConn = fdMaxConn;
	}

	/**
	 * 服务超时预警
	 */
	protected String fdTimeOut = null;

	/**
	 * @return 服务超时预警
	 */
	public String getFdTimeOut() {
		return fdTimeOut;
	}

	/**
	 * @param fdTimeOut
	 *            服务超时预警
	 */
	public void setFdTimeOut(String fdTimeOut) {
		this.fdTimeOut = fdTimeOut;
	}

	/**
	 * 备注
	 */
	protected String fdDescription = null;

	/**
	 * @return 备注
	 */
	public String getFdDescription() {
		return fdDescription;
	}

	/**
	 * @param fdDescription
	 *            备注
	 */
	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
	}

	/**
	 * 访问策略IDS
	 */
	protected String fdPolicyIds = null;

	/**
	 * @return 访问策略IDS
	 */
	public String getFdPolicyIds() {
		return fdPolicyIds;
	}

	/**
	 * @param fdPolicyIds
	 *            访问策略IDS
	 */
	public void setFdPolicyIds(String fdPolicyIds) {
		this.fdPolicyIds = fdPolicyIds;
	}

	/**
	 * 访问策略的名称列表
	 */
	protected String fdPolicyNames = null;

	/**
	 * @return 访问策略的名称列表
	 */
	public String getFdPolicyNames() {
		return fdPolicyNames;
	}

	/**
	 * @param fdPolicyNames
	 *            访问策略的名称列表
	 */
	public void setFdPolicyNames(String fdPolicyNames) {
		this.fdPolicyNames = fdPolicyNames;
	}

	/**
	 * 最大消息体长度
	 */
	protected String fdMaxBodySize = null;

	/**
	 * @return 最大消息体长度
	 */
	public String getFdMaxBodySize() {
		return fdMaxBodySize;
	}

	/**
	 * @param fdMaxBodySize 最大消息体长度
	 */
	public void setFdMaxBodySize(String fdMaxBodySize) {
		this.fdMaxBodySize = fdMaxBodySize;
	}
	
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdServiceClass = null;
		fdServiceName = null;
		fdDocUrl = null;
		fdUriPrefix = null;
		fdAnonymous = null;
		fdServiceStatus = null;
		fdStartupType = null;
		fdMaxConn = null;
		fdTimeOut = null;
		fdDescription = null;
		fdPolicyIds = null;
		fdPolicyNames = null;

		super.reset(mapping, request);
	}

	@Override
    public Class<SysRestserviceServerMain> getModelClass() {
		return SysRestserviceServerMain.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdPolicyIds",
					new FormConvertor_IDsToModelList("fdPolicy",
							SysRestserviceServerPolicy.class));
		}
		return toModelPropertyMap;
	}
}
