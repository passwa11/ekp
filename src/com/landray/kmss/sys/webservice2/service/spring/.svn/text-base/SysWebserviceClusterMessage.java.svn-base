package com.landray.kmss.sys.webservice2.service.spring;

import com.landray.kmss.sys.cluster.interfaces.message.IMessage;

/**
 * 集群同步消息
 * 
 * @author Jeff
 */
public class SysWebserviceClusterMessage implements IMessage {
	private static final long serialVersionUID = 4449548310962091907L;

	public final static int OPT_STOP = 0;

	public final static int OPT_START = 1;

	private String serviceId;

	private int operation;

	public SysWebserviceClusterMessage() {

	}

	public SysWebserviceClusterMessage(int operation, String serviceId) {
		this.operation = operation;
		this.serviceId = serviceId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public int getOperation() {
		return operation;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	@Override
    public String toString() {
		return "opt=" + operation + ", id=" + serviceId;
	}
}
