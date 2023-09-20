package com.landray.kmss.sys.webservice2.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.webservice2.model.SysWebserviceMain;
import com.landray.kmss.sys.webservice2.model.SysWebserviceUser;

/**
 * 服务注册信息 Form
 * 
 * @author Jeff
 */
public class SysWebserviceMainForm extends ExtendForm {

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
	 * 服务实现类名
	 */
	protected String fdServiceBean = null;

	/**
	 * @return 服务实现类名
	 */
	public String getFdServiceBean() {
		return fdServiceBean;
	}

	/**
	 * @param fdServiceBean
	 *            服务实现类名
	 */
	public void setFdServiceBean(String fdServiceBean) {
		this.fdServiceBean = fdServiceBean;
	}

	/**
	 * 接口参数说明
	 */
	protected String fdServiceParam = null;

	/**
	 * @return 接口参数说明
	 */
	public String getFdServiceParam() {
		return fdServiceParam;
	}

	/**
	 * @param fdServiceParam
	 *            接口参数说明
	 */
	public void setFdServiceParam(String fdServiceParam) {
		this.fdServiceParam = fdServiceParam;
	}

	/**
	 * 访问路径
	 */
	protected String fdAddress = null;

	/**
	 * @return 访问路径
	 */
	public String getFdAddress() {
		return fdAddress;
	}

	/**
	 * @param fdAddress
	 *            访问路径
	 */
	public void setFdAddress(String fdAddress) {
		this.fdAddress = fdAddress;
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
	 * 记录消息报文调试日志
	 */
	protected String fdSoapMsgLogging = null;

	public String getFdSoapMsgLogging() {
		return fdSoapMsgLogging;
	}

	public void setFdSoapMsgLogging(String fdSoapMsgLogging) {
		this.fdSoapMsgLogging = fdSoapMsgLogging;
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
	 * 最大运行时长
	 */
	protected String fdTimeOut = null;

	/**
	 * @return 最大运行时长
	 */
	public String getFdTimeOut() {
		return fdTimeOut;
	}

	/**
	 * @param fdTimeOut
	 *            最大运行时长
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
	 * 用户ID的ID列表
	 */
	protected String fdUserIds = null;

	/**
	 * @return 用户ID的ID列表
	 */
	public String getFdUserIds() {
		return fdUserIds;
	}

	/**
	 * @param fdUserIds
	 *            用户ID的ID列表
	 */
	public void setFdUserIds(String fdUserIds) {
		this.fdUserIds = fdUserIds;
	}

	/**
	 * 用户ID的名称列表
	 */
	protected String fdUserNames = null;

	/**
	 * @return 用户ID的名称列表
	 */
	public String getFdUserNames() {
		return fdUserNames;
	}

	/**
	 * @param fdUserNames
	 *            用户ID的名称列表
	 */
	public void setFdUserNames(String fdUserNames) {
		this.fdUserNames = fdUserNames;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdServiceClass = null;
		fdServiceBean = null;
		fdServiceParam = null;
		fdAddress = null;
		fdAnonymous = null;
		fdServiceStatus = null;
		fdStartupType = null;
		fdSoapMsgLogging = null;
		fdMaxConn = null;
		fdTimeOut = null;
		fdDescription = null;
		fdUserIds = null;
		fdUserNames = null;

		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysWebserviceMain.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdUserIds",
					new FormConvertor_IDsToModelList("fdUser",
							SysWebserviceUser.class));
		}
		return toModelPropertyMap;
	}

	/**
	 * 最大连接数
	 */
	protected String fdMaxBodySize = null;

	/**
	 * @return 最大连接数
	 */
	public String getFdMaxBodySize() {
		return fdMaxBodySize;
	}

	/**
	 * @param fdMaxConn
	 *            最大连接数
	 */
	public void setFdMaxBodySize(String fdMaxBodySize) {
		this.fdMaxBodySize = fdMaxBodySize;
	}
}
