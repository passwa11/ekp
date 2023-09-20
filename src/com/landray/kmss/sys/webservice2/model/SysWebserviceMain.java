package com.landray.kmss.sys.webservice2.model;

import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.webservice2.forms.SysWebserviceMainForm;

/**
 * WebService信息表
 * 
 * @author Jeff
 */
public class SysWebserviceMain extends BaseModel {

	/**
	 * 名称
	 */
	protected String fdName;

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
	protected String fdServiceClass;

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
	protected String fdServiceBean;

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
	protected String fdServiceParam;

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
	protected String fdAddress;

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
	protected Boolean fdAnonymous;

	/**
	 * @return 允许匿名访问
	 */
	public Boolean getFdAnonymous() {
		return fdAnonymous;
	}

	/**
	 * @param fdAnonymous
	 *            允许匿名访问
	 */
	public void setFdAnonymous(Boolean fdAnonymous) {
		this.fdAnonymous = fdAnonymous;
	}

	/**
	 * 服务状态
	 */
	protected Integer fdServiceStatus;

	/**
	 * @return 服务状态
	 */
	public Integer getFdServiceStatus() {
		return fdServiceStatus;
	}

	/**
	 * @param fdServiceStatus
	 *            服务状态
	 */
	public void setFdServiceStatus(Integer fdServiceStatus) {
		this.fdServiceStatus = fdServiceStatus;
	}

	/**
	 * 启动类型
	 */
	protected String fdStartupType;

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
	protected Boolean fdSoapMsgLogging;

	public Boolean getFdSoapMsgLogging() {
		return fdSoapMsgLogging;
	}

	public void setFdSoapMsgLogging(Boolean fdSoapMsgLogging) {
		this.fdSoapMsgLogging = fdSoapMsgLogging;
	}

	/**
	 * 最大连接数
	 */
	protected Integer fdMaxConn;

	/**
	 * @return 最大连接数
	 */
	public Integer getFdMaxConn() {
		return fdMaxConn;
	}

	/**
	 * @param fdMaxConn
	 *            最大连接数
	 */
	public void setFdMaxConn(Integer fdMaxConn) {
		this.fdMaxConn = fdMaxConn;
	}

	/**
	 * 最大运行时长
	 */
	protected Long fdTimeOut;

	/**
	 * @return 最大运行时长
	 */
	public Long getFdTimeOut() {
		return fdTimeOut;
	}

	/**
	 * @param fdTimeOut
	 *            最大运行时长
	 */
	public void setFdTimeOut(Long fdTimeOut) {
		this.fdTimeOut = fdTimeOut;
	}

	/**
	 * 备注
	 */
	protected String fdDescription;

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
	 * 用户ID
	 */
	protected List<SysWebserviceUser> fdUser;

	/**
	 * @return 用户ID
	 */
	public List<SysWebserviceUser> getFdUser() {
		return fdUser;
	}

	/**
	 * @param fdUser
	 *            用户ID
	 */
	public void setFdUser(List<SysWebserviceUser> fdUser) {
		this.fdUser = fdUser;
	}

	@Override
    public Class getFormClass() {
		return SysWebserviceMainForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdUser",
					new ModelConvertor_ModelListToString(
							"fdUserIds:fdUserNames", "fdId:fdUserName"));
		}
		return toFormPropertyMap;
	}

	public Long getFdMaxBodySize() {
		return fdMaxBodySize;
	}

	public void setFdMaxBodySize(Long fdMaxBodySize) {
		this.fdMaxBodySize = fdMaxBodySize;
	}

	private Long fdMaxBodySize;
}
