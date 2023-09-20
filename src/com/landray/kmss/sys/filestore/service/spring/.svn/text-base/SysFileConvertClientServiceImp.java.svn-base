package com.landray.kmss.sys.filestore.service.spring;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.cluster.remoting.InvokeCallback;
import com.landray.kmss.sys.cluster.remoting.ReadonlyResponse;
import com.landray.kmss.sys.cluster.remoting.RemotingClient;
import com.landray.kmss.sys.cluster.remoting.exception.RemotingException;
import com.landray.kmss.sys.cluster.remoting.netty.NettyRemotingClient;
import com.landray.kmss.sys.cluster.remoting.protocol.Command;
import com.landray.kmss.sys.filestore.dao.ISysFileConvertClientDao;
import com.landray.kmss.sys.filestore.model.SysFileConvertClient;
import com.landray.kmss.sys.filestore.model.SysFileConvertConfig;
import com.landray.kmss.sys.filestore.model.SysFileConvertConstant;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.remote.interfaces.IDuplexCommunication;
import com.landray.kmss.sys.filestore.service.ISysFileConvertClientService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertConfigService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertQueueService;
import com.landray.kmss.sys.filestore.util.SysFileStoreUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

@SuppressWarnings({ "unchecked" })
public class SysFileConvertClientServiceImp extends BaseServiceImp implements ISysFileConvertClientService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysFileConvertClientServiceImp.class);

	private ISysFileConvertQueueService queueService = null;
	private ISysFileConvertConfigService configService = null;

	public void setQueueService(ISysFileConvertQueueService queueService) {
		this.queueService = queueService;
	}

	public void setConfigService(ISysFileConvertConfigService configService) {
		this.configService = configService;
	}

	@Override
	public void delete(IBaseModel convertClient) throws Exception {
		super.delete(convertClient);
		// List<SysFileConvertQueue> clientQueues = queueService.findList(
		// "fdClientId='" + convertClient.getFdId() + "' and (fdConvertStatus=1
		// or fdConvertStatus=2)", "");
		// if (clientQueues != null && clientQueues.size() > 0) {
		// for (SysFileConvertQueue clientQueue : clientQueues) {
		// clientQueue.setFdConvertStatus(SysFileConvertConstant.UNASSIGNED);
		// clientQueue.setFdClientId("");
		// queueService.update(clientQueue);
		// }
		// }
	}

	@Override
	public Page findOKPage(HQLInfo hqlInfo) throws Exception {
		refreshClients(false);
		return findPage(hqlInfo);
	}

	@Override
	public void operateClients(String clientId, String cmd, String... cmdParams) throws Exception {
		SysFileConvertClient client = (SysFileConvertClient) findByPrimaryKey(clientId);
		if ("disable".equals(cmd)) {
			client.setAvail(false);
			update(client);
		} else if ("able".equals(cmd)) {
			client.setAvail(true);
			update(client);
		} else {
			if ("reboot".equals(cmd) || "close".equals(cmd)) {
				if ("close".equals(cmd)) {
					delete(client);
				}
				if ("reboot".equals(cmd)) {
					client.setAvail(false);
					update(client);
					List<SysFileConvertQueue> clientQueues = queueService.findList(
							"fdClientId='" + client.getFdId() + "' and (fdConvertStatus=1 or fdConvertStatus=2)", "");
					if (clientQueues != null && clientQueues.size() > 0) {
						for (SysFileConvertQueue clientQueue : clientQueues) {
							clientQueue.setFdConvertStatus(SysFileConvertConstant.UNASSIGNED);
							clientQueue.setFdClientId("");
							queueService.update(clientQueue);
						}
					}
				}
			} else if ("config".equals(cmd)) {
				JSONObject updateConfig = JSONObject.fromObject(client.getConverterConfig());
				updateConfig.put("taskCapacity", Integer.valueOf(cmdParams[0]));
				updateConfig.put("taskTimeout", Integer.valueOf(cmdParams[1]));
				updateConfig.put("logLevel", cmdParams[2]);
				((ISysFileConvertClientDao) getBaseDao()).updateConverterConfig(clientId, updateConfig.toString());
			}
			Command cmdMessage = getMessageToClient(client.getClientMessageHandlerCode(), cmd, cmdParams);
			getSimpleNettyClient().sendMessageOnAsync(client.getClientIP() + ":" + client.getClientPort(), cmdMessage,
					6000, new InvokeCallback() {
						@Override
						public void operationComplete(ReadonlyResponse resp) {
							// do nothing
							// only close client
						}
					});
		}
	}

	private RemotingClient simpleNettyClient = null;

	private RemotingClient getSimpleNettyClient() {
		if (simpleNettyClient == null) {
			simpleNettyClient = new NettyRemotingClient("fileStore");
			try {
				simpleNettyClient.start();
			} catch (RemotingException e) {
				logger.debug("启动简单通讯客户端失败", e);
			}
		}
		return simpleNettyClient;
	}

	private Command getMessageToClient(int messageHandlerCode, String cmd, String... cmdParams) {
		Command cmdMessage = Command.createRequestCommand(messageHandlerCode, null);
		cmdMessage.getExtFields().put("msgType", cmd);
		if (cmdParams != null && cmdParams.length == 3) {
			cmdMessage.getExtFields().put("taskCapacity", cmdParams[0]);
			cmdMessage.getExtFields().put("taskTimeout", cmdParams[1]);
			cmdMessage.getExtFields().put("logLevel", cmdParams[2]);
		}
		return cmdMessage;
	}

	@Override
	public String registerClient(Map<String, String> receiveInfos) throws Exception {
		SysFileConvertClient client = new SysFileConvertClient();
		String rtnClientID = "";
		String clientID = receiveInfos.get("clientID");
		if (StringUtil.isNotNull(clientID)) {
			client.setFdId(clientID);
		}
		client.setVersion(receiveInfos.get("version"));
		client.setConverterFullKey(receiveInfos.get("converterFullKey"));
		client.setAvail(Boolean.TRUE);
		client.setClientIP(receiveInfos.get("clientIP"));
		client.setClientPort(Integer.valueOf(receiveInfos.get("clientPort")));
		client.setClientMessageHandlerCode(Integer.valueOf(receiveInfos.get("clientMessageHandlerCode")));
		client.setTaskCapacity(Integer.valueOf(receiveInfos.get("taskCapacity")));
		client.setConverterVersion(receiveInfos.get("converterVersion"));
		client.setProcessID(Integer.valueOf(receiveInfos.get("processID")));
		client.setTaskConvertingNum(Integer.valueOf(receiveInfos.get("taskQueueSize")));
		client.setConverterConfig(receiveInfos.get("otherConfigs"));
		if("true".equals(receiveInfos.get("isLongTask"))) {
            client.setIsLongTask(true);
        } else {
            client.setIsLongTask(false);
        }
		try {
			SysFileConvertClient findClient = null;
			if (StringUtil.isNotNull(clientID)) {
				findClient = (SysFileConvertClient) findByPrimaryKey(clientID, SysFileConvertClient.class, true);
			}
			if (findClient == null) {
				findClient = (SysFileConvertClient)findFirstOne(
						" processID = " + Integer.valueOf(receiveInfos.get("processID")) + " and clientPort = "
								+ Integer.valueOf(receiveInfos.get("clientPort")) + " and clientIP='"
								+ receiveInfos.get("clientIP") + "'",
						"");
			}
			if (findClient != null) {
				findClient.setVersion(client.getVersion());
				findClient.setConverterFullKey(client.getConverterFullKey());
				findClient.setClientMessageHandlerCode(client.getClientMessageHandlerCode());
				findClient.setTaskCapacity(client.getTaskCapacity());
				findClient.setTaskConvertingNum(client.getTaskConvertingNum());
				findClient.setConverterVersion(client.getConverterVersion());
				findClient.setConverterConfig(client.getConverterConfig());
				findClient.setProcessID(client.getProcessID());
				findClient.setAvail(Boolean.TRUE);
				findClient.setClientIP(client.getClientIP());
				findClient.setClientPort(client.getClientPort());
				findClient.setIsLongTask(client.getIsLongTask());
				update(findClient);
				rtnClientID = findClient.getFdId();
			} else {
				add(client);
				rtnClientID = client.getFdId();
			}
			return rtnClientID;
		} catch (Exception e) {
			logger.info("registerClientError", e);
			throw new Exception("registerClientError", e);
		}
	}

	@Override
	public SysFileConvertClient getAliveAvailIdleClient(IDuplexCommunication communication,
			SysFileConvertQueue taskQueue) {
		SysFileConvertClient resultClient = null;
		SysFileConvertConfig queueConfig = configService.getQueueConfig(taskQueue);
		String taskQueueConverterType = "aspose";
		if (queueConfig != null) {
			String configType = queueConfig.getFdConverterType();
			if (StringUtil.isNotNull(configType)) {
				taskQueueConverterType = configType;
			}
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("avail = :avail");
		hqlInfo.setParameter("avail", Boolean.TRUE);
		try {
			List<SysFileConvertClient> findClients = findList(hqlInfo);
			for (SysFileConvertClient item : findClients) {
				if (!item.getConverterKey().equals(taskQueue.getFdConverterKey())) {
					continue;
				} else {
					if (taskQueueConverterType != null) {
						if (item.getConverterFullKey().toLowerCase().contains(taskQueueConverterType.toLowerCase())) {
							int runningTaskNum = getRunningTaskNum(item);
							if (runningTaskNum < item.getTaskCapacity()) {
								resultClient = item;
								item.setTaskConvertingNum(runningTaskNum);
								update(item);
								break;
							}
						}
					} else {
						resultClient = item;
						break;
					}
				}
			}
		} catch (Exception e) {
		}
		return resultClient;
	}

	private int getRunningTaskNum(SysFileConvertClient item) {
		int runnintTaskNum = item.getTaskConvertingNum();
		try {
			Command runningTaskNumResp = getSimpleNettyClient().sendMessageOnSync(
					item.getClientIP() + ":" + item.getClientPort(),
					getMessageToClient(item.getClientMessageHandlerCode(), "runningTaskNum"), 6000);
			if (SysFileStoreUtil.isCommandSuccess(runningTaskNumResp)) {
				runnintTaskNum = Integer.valueOf(runningTaskNumResp.getExtFields().get("runningTaskNum"));
			}
		} catch (Exception e) {
			logger.debug("获取转换服务正在转换的任务数失败", e);
		}
		return runnintTaskNum;
	}

	@Override
	public boolean isClientRegistered(String clientIP, String clientPort, String clientProcessID) {
		try {
			List<String> fdIds = findValue("fdId", " processID = " + Integer.valueOf(clientProcessID)
					+ " and clientPort = " + Integer.valueOf(clientPort) + " and clientIP='" + clientIP + "'", "");
			return (fdIds == null || fdIds.size() == 0) ? false : true;
		} catch (Exception e) {
			logger.info("验证转换服务是否注册出错", e);
			return false;
		}
	}

	@Override
	public Integer getMaxPort(String clientIP) {
		Integer clientMaxPort = SysFileStoreUtil.getClientConnectPort();
		try {
			List<SysFileConvertClient> clients = findList("clientIP='" + clientIP + "'", "");
			if (clients != null && clients.size() > 0) {
				for (SysFileConvertClient item : clients) {
					if (isAlive(item)) {
						if (clientMaxPort < item.getClientPort()) {
							clientMaxPort = item.getClientPort();
						}
					} else {
						delete(item);
					}
				}
			}
		} catch (Exception e) {
			logger.info("获取转换服务出错");
		}
		return clientMaxPort;
	}

	@Override
	public void refreshClients(boolean checkQueuesNum) {
		try {
			List<SysFileConvertClient> clients = findList("", "");
			if (clients != null && clients.size() > 0) {
				for (SysFileConvertClient client : clients) {
					if (!isAlive(client)) {
						delete(client);
					}
				}
			}
		} catch (Exception e) {
			//
		}
	}

	@Override
	public boolean isAlive(final SysFileConvertClient client) {
		final AtomicBoolean alive = new AtomicBoolean(false);
		Command isAliveCmd = Command.createRequestCommand(client.getClientMessageHandlerCode(), null);
		isAliveCmd.getExtFields().put("msgType", "isAlive");
		isAliveCmd.getExtFields().put("processID", "" + client.getProcessID());
		try {
			getSimpleNettyClient().sendMessageOnAsync(client.toString(), isAliveCmd, 6000, new InvokeCallback() {
				@Override
				public void operationComplete(ReadonlyResponse resp) {
					if (resp.isSendRequestOK()) {
						Command respCMD = resp.getResponse();
						if (SysFileStoreUtil.isCommandSuccess(respCMD)) {
							alive.set(true);
							String taskSize = respCMD.getExtFields().get("taskSize");
							if (StringUtil.isNotNull(taskSize)) {
								if (!taskSize.equals(client.getTaskConvertingNum().toString())) {
									try {
										client.setTaskConvertingNum(Integer.valueOf(taskSize));
										update(client);
									} catch (Throwable e) {
									}
								}
							}
						}
					}
				}
			});
		} catch (Throwable e) {
		}
		int secends = 6;
		while (alive.get() == false && secends > 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
			secends--;
		}
		return alive.get();
	}
}
