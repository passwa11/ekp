package com.landray.kmss.sys.restservice.server.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.restservice.server.forms.SysRestserviceServerLogBackupForm;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
 * RestService日志备份表
 * 
 * @author  
 */
public class SysRestserviceServerLogBackup extends BaseModel implements InterceptFieldEnabled {

	/**
	 * 服务名称
	 */
	protected String fdName;

	/**
	 * @return 服务名称
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            服务名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 服务标识
	 */
	protected String fdServiceName;

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
	 * 用户名
	 */
	protected String fdUserName;

	/**
	 * @return 用户名
	 */
	public String getFdUserName() {
		return fdUserName;
	}

	/**
	 * @param fdUserName
	 *            用户名
	 */
	public void setFdUserName(String fdUserName) {
		this.fdUserName = fdUserName;
	}

	/**
	 * 客户端IP地址
	 */
	protected String fdClientIp;

	/**
	 * @return 客户端IP地址
	 */
	public String getFdClientIp() {
		return fdClientIp;
	}

	/**
	 * @param fdClientIp
	 *            客户端IP地址
	 */
	public void setFdClientIp(String fdClientIp) {
		this.fdClientIp = fdClientIp;
	}

	/**
	 * 启动时间
	 */
	protected Date fdStartTime;

	/**
	 * @return 启动时间
	 */
	public Date getFdStartTime() {
		return fdStartTime;
	}

	/**
	 * @param fdStartTime
	 *            启动时间
	 */
	public void setFdStartTime(Date fdStartTime) {
		this.fdStartTime = fdStartTime;
	}

	/**
	 * 结束时间
	 */
	protected Date fdEndTime;

	/**
	 * @return 结束时间
	 */
	public Date getFdEndTime() {
		return fdEndTime;
	}

	/**
	 * @param fdEndTime
	 *            结束时间
	 */
	public void setFdEndTime(Date fdEndTime) {
		this.fdEndTime = fdEndTime;
	}

	/**
	 * 执行结果
	 */
	protected String fdExecResult;

	/**
	 * @return 执行结果
	 */
	public String getFdExecResult() {
		return fdExecResult;
	}

	/**
	 * @param fdExecResult
	 *            执行结果
	 */
	public void setFdExecResult(String fdExecResult) {
		this.fdExecResult = fdExecResult;
	}

	/**
	 * 错误信息
	 */
	protected String fdErrorMsg;

	/**
	 * @return 错误信息
	 */
	public String getFdErrorMsg() {
		return (String) readLazyField("fdErrorMsg", fdErrorMsg);
	}

	/**
	 * @param fdErrorMsg
	 *            错误信息
	 */
	public void setFdErrorMsg(String fdErrorMsg) {
		this.fdErrorMsg = (String) writeLazyField("fdErrorMsg", this.fdErrorMsg, fdErrorMsg);
	}

	/**
	 * 运行时长(秒)
	 */
	protected Long fdRunTime;

	/**
	 * @return 运行时长(秒)
	 */
	public Long getFdRunTime() {
		return fdRunTime;
	}

	/**
	 * @param fdRunTime
	 *            运行时长(秒)
	 */
	public void setFdRunTime(Long fdRunTime) {
		this.fdRunTime = fdRunTime;
	}

	/**
	 * 运行时长(毫秒)
	 */
	private long fdRunTimeMillis;

	public long getFdRunTimeMillis() {
		return fdRunTimeMillis;
	}

	public void setFdRunTimeMillis(long fdRunTimeMillis) {
		this.fdRunTimeMillis = fdRunTimeMillis;
	}

	/**
	 * 访问路径
	 */
	private String fdOriginUri;

	public String getFdOriginUri() {
		return fdOriginUri;
	}

	public void setFdOriginUri(String fdOriginUri) {
		this.fdOriginUri = fdOriginUri;
	}

	/**
	 * 请求报头
	 */
	private String fdRequestHeader;
	
	public String getFdRequestHeader() {
		return fdRequestHeader;
	}

	public void setFdRequestHeader(String fdRequestHeader) {
		this.fdRequestHeader = fdRequestHeader;
	}

	/**
	 * 响应报头
	 */
	private String fdResponseHeader;

	public String getFdResponseHeader() {
		return fdResponseHeader;
	}

	public void setFdResponseHeader(String fdResponseHeader) {
		this.fdResponseHeader = fdResponseHeader;
	}

	/**
	 * 请求报文
	 */
	private String fdRequestMsg;

	public String getFdRequestMsg() {
		return (String) readLazyField("fdRequestMsg", fdRequestMsg);
	}

	public void setFdRequestMsg(String fdRequestMsg) {
		this.fdRequestMsg = (String) writeLazyField("fdRequestMsg", this.fdRequestMsg, fdRequestMsg);
	}

	/**
	 * 响应报文
	 */
	private String fdResponseMsg;

	public String getFdResponseMsg() {
		return (String) readLazyField("fdResponseMsg", fdResponseMsg);
	}

	public void setFdResponseMsg(String fdResponseMsg) {
		this.fdResponseMsg = (String) writeLazyField("fdResponseMsg", this.fdResponseMsg, fdResponseMsg);
	}

	@Override
    public Class getFormClass() {
		return SysRestserviceServerLogBackupForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}
}
