package com.landray.kmss.sys.restservice.server.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerLog;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 日志Form
 * 
 * @author  
 */
public class SysRestserviceServerLogForm extends ExtendForm {

	/**
	 * 服务名称
	 */
	protected String fdName = null;

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
	 * 用户名
	 */
	protected String fdUserName = null;

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
	protected String fdClientIp = null;

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
	protected String fdStartTime = null;

	/**
	 * @return 启动时间
	 */
	public String getFdStartTime() {
		return fdStartTime;
	}

	/**
	 * @param fdStartTime
	 *            启动时间
	 */
	public void setFdStartTime(String fdStartTime) {
		this.fdStartTime = fdStartTime;
	}

	/**
	 * 结束时间
	 */
	protected String fdEndTime = null;

	/**
	 * @return 结束时间
	 */
	public String getFdEndTime() {
		return fdEndTime;
	}

	/**
	 * @param fdEndTime
	 *            结束时间
	 */
	public void setFdEndTime(String fdEndTime) {
		this.fdEndTime = fdEndTime;
	}

	/**
	 * 执行结果
	 */
	protected String fdExecResult = null;

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
	protected String fdErrorMsg = null;

	/**
	 * @return 错误信息
	 */
	public String getFdErrorMsg() {
		return fdErrorMsg;
	}

	/**
	 * @param fdErrorMsg
	 *            错误信息
	 */
	public void setFdErrorMsg(String fdErrorMsg) {
		this.fdErrorMsg = fdErrorMsg;
	}

	/**
	 * 运行时长(秒)
	 */
	protected String fdRunTime = null;

	/**
	 * @return 运行时长(秒)
	 */
	public String getFdRunTime() {
		return fdRunTime;
	}

	/**
	 * @param fdRunTime
	 *            运行时长(秒)
	 */
	public void setFdRunTime(String fdRunTime) {
		this.fdRunTime = fdRunTime;
	}

	/**
	 * 运行时长(毫秒)
	 */
	private String fdRunTimeMillis = null;;

	public String getFdRunTimeMillis() {
		return fdRunTimeMillis;
	}

	public void setFdRunTimeMillis(String fdRunTimeMillis) {
		this.fdRunTimeMillis = fdRunTimeMillis;
	}

	/**
	 * 访问路径
	 */
	private String fdOriginUri;

	/**
	 * 请求报头
	 */
	private String fdRequestHeader;

	/**
	 * 响应报头
	 */
	private String fdResponseHeader;
	
	/**
	 * 请求报文
	 */
	private String fdRequestMsg;

	/**
	 * 响应报文
	 */
	private String fdResponseMsg;

	public String getFdOriginUri() {
		return fdOriginUri;
	}

	public void setFdOriginUri(String fdOriginUri) {
		this.fdOriginUri = fdOriginUri;
	}
	
	public String getFdRequestHeader() {
		return fdRequestHeader;
	}

	public void setFdRequestHeader(String fdRequestHeader) {
		this.fdRequestHeader = fdRequestHeader;
	}

	public String getFdResponseHeader() {
		return fdResponseHeader;
	}

	public void setFdResponseHeader(String fdResponseHeader) {
		this.fdResponseHeader = fdResponseHeader;
	}

	public String getFdRequestMsg() {
		return fdRequestMsg;
	}

	public void setFdRequestMsg(String fdRequestMsg) {
		this.fdRequestMsg = fdRequestMsg;
	}

	public String getFdResponseMsg() {
		return fdResponseMsg;
	}

	public void setFdResponseMsg(String fdResponseMsg) {
		this.fdResponseMsg = fdResponseMsg;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdServiceName = null;
		fdServiceName = null;
		fdUserName = null;
		fdClientIp = null;
		fdStartTime = null;
		fdEndTime = null;
		fdExecResult = null;
		fdErrorMsg = null;
		fdRunTime = null;
		fdRunTimeMillis = null;
		fdOriginUri = null;
		fdRequestMsg = null;
		fdResponseMsg = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysRestserviceServerLog.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return toModelPropertyMap;
	}
}
