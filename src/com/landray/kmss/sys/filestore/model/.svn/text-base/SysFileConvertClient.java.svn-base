package com.landray.kmss.sys.filestore.model;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.filestore.forms.SysFileConvertClientForm;

@SuppressWarnings("rawtypes")
public class SysFileConvertClient extends BaseModel {

	private static final long serialVersionUID = 2304511133804486001L;

	@Override
	public Class getFormClass() {
		return SysFileConvertClientForm.class;
	}

	private String version;
	private Integer processID;
	private String clientIP;
	private Integer clientPort;
	private Integer clientMessageHandlerCode;
	private String converterFullKey;
	private Integer taskCapacity;
	private Integer taskConvertingNum = new Integer(0);
	private Boolean avail;
	private String converterVersion;
	private String converterConfig;
	private Boolean isLongTask;
	private Boolean isUpgrade;//是否升级

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getConverterFullKey() {
		return converterFullKey;
	}

	public String getConverterKey() {
		return converterFullKey.split("-")[0];
	}

	public void setConverterFullKey(String converterFullKey) {
		this.converterFullKey = converterFullKey;
	}

	public Integer getTaskCapacity() {
		return taskCapacity;
	}

	public void setTaskCapacity(Integer taskCapacity) {
		this.taskCapacity = taskCapacity;
	}

	public Integer getTaskConvertingNum() {
		return taskConvertingNum;
	}

	public synchronized void addTaskConvertingNum() {
		++taskConvertingNum;
	}

	public synchronized void subTaskConvertingNum() {
		--taskConvertingNum;
	}

	public Integer getProcessID() {
		return processID;
	}

	public void setProcessID(Integer processID) {
		this.processID = processID;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public Integer getClientPort() {
		return clientPort;
	}

	public void setClientPort(Integer clientPort) {
		this.clientPort = clientPort;
	}

	public Integer getClientMessageHandlerCode() {
		return clientMessageHandlerCode;
	}

	public void setClientMessageHandlerCode(Integer clientMessageHandlerCode) {
		this.clientMessageHandlerCode = clientMessageHandlerCode;
	}

	public Boolean getAvail() {
		return avail;
	}

	public void setAvail(Boolean avail) {
		this.avail = avail;
	}

	public String getConverterVersion() {
		return converterVersion;
	}

	public void setConverterVersion(String converterVersion) {
		this.converterVersion = converterVersion;
	}

	public void setTaskConvertingNum(Integer taskConvertingNum) {
		this.taskConvertingNum = taskConvertingNum;
	}

	public boolean isAlive() {
		boolean result = false;
		Socket clientBind = null;
		try {
			clientBind = new Socket();
			SocketAddress socketAddress = new InetSocketAddress(clientIP, clientPort);
			clientBind.connect(socketAddress, 1500);
			result = true;
		} catch (Exception e) {
			result = false;
		} finally {
			if (clientBind != null) {
				try {
					clientBind.close();
				} catch (IOException e1) {
					//
				}
			}
		}
		return result;
	}

	@Override
	public String toString() {
		return clientIP + ":" + clientPort;
	}

	public String getConverterConfig() {
		return converterConfig;
	}

	public void setConverterConfig(String converterConfig) {
		this.converterConfig = converterConfig;
	}

	public Boolean getIsLongTask() {
		return isLongTask;
	}

	public void setIsLongTask(Boolean isLongTask) {
		this.isLongTask = isLongTask;
	}

	public Boolean getIsUpgrade() {
		return isUpgrade;
	}

	public void setIsUpgrade(Boolean isUpgrade) {
		this.isUpgrade = isUpgrade;
	}

	
}
