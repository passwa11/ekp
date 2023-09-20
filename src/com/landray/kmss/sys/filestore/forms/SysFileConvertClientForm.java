package com.landray.kmss.sys.filestore.forms;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.filestore.model.SysFileConvertClient;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

@SuppressWarnings("rawtypes")
public class SysFileConvertClientForm extends ExtendForm {

	private static final long serialVersionUID = 5851579108130593977L;

	@Override
	public Class getModelClass() {
		return SysFileConvertClient.class;
	}

	private String version;
	private String processID;
	private String clientIP;
	private String clientPort;
	private String clientMessageHandlerCode;
	private String converterFullKey;
	private String taskCapacity;
	private String taskConvertingNum;
	private String avail;
	private String converterVersion;
	private String converterConfig;
	private String taskTimeout;
	private String logLevel;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getProcessID() {
		return processID;
	}

	public void setProcessID(String processID) {
		this.processID = processID;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public String getClientPort() {
		return clientPort;
	}

	public void setClientPort(String clientPort) {
		this.clientPort = clientPort;
	}

	public String getClientMessageHandlerCode() {
		return clientMessageHandlerCode;
	}

	public void setClientMessageHandlerCode(String clientMessageHandlerCode) {
		this.clientMessageHandlerCode = clientMessageHandlerCode;
	}

	public String getConverterFullKey() {
		return converterFullKey;
	}

	public void setConverterFullKey(String converterFullKey) {
		this.converterFullKey = converterFullKey;
	}

	public String getTaskCapacity() {
		return StringUtil.isNull(taskCapacity) ? "6" : taskCapacity;
	}

	public void setTaskCapacity(String taskCapacity) {
		this.taskCapacity = taskCapacity;
	}

	public String getTaskConvertingNum() {
		return taskConvertingNum;
	}

	public void setTaskConvertingNum(String taskConvertingNum) {
		this.taskConvertingNum = taskConvertingNum;
	}

	public String getAvail() {
		return avail;
	}

	public void setAvail(String avail) {
		this.avail = avail;
	}

	public String getConverterVersion() {
		return converterVersion;
	}

	public void setConverterVersion(String converterVersion) {
		this.converterVersion = converterVersion;
	}

	public String getTaskTimeout() {
		if (StringUtil.isNull(taskTimeout)) {
			try {
				taskTimeout = JSONObject.fromObject(converterConfig).getString("taskTimeout");
			} catch (Exception e) {
				taskTimeout = "30";
			}
		}
		return taskTimeout;
	}

	public String getConverterConfig() {
		return converterConfig;
	}

	public void setConverterConfig(String converterConfig) {
		this.converterConfig = converterConfig;
	}

	public void setTaskTimeout(String taskTimeout) {
		this.taskTimeout = taskTimeout;
	}

	public String getLogLevel() {
		if (StringUtil.isNull(logLevel)) {
			try {
				logLevel = JSONObject.fromObject(converterConfig).getString("logLevel");
			} catch (Exception e) {
				logLevel = "INFO";
			}
		}
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
}
