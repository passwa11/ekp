package com.landray.kmss.sys.filestore.scheduler.remote;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.cluster.remoting.InvokeCallback;
import com.landray.kmss.sys.cluster.remoting.RemotingClient;
import com.landray.kmss.sys.cluster.remoting.RemotingServer;
import com.landray.kmss.sys.cluster.remoting.exception.RemotingConnectException;
import com.landray.kmss.sys.cluster.remoting.exception.RemotingException;
import com.landray.kmss.sys.cluster.remoting.exception.RemotingSendRequestException;
import com.landray.kmss.sys.cluster.remoting.exception.RemotingTimeoutException;
import com.landray.kmss.sys.cluster.remoting.netty.NettyClientConfig;
import com.landray.kmss.sys.cluster.remoting.netty.NettyRemotingClient;
import com.landray.kmss.sys.cluster.remoting.netty.NettyRemotingServer;
import com.landray.kmss.sys.cluster.remoting.netty.NettyServerConfig;
import com.landray.kmss.sys.cluster.remoting.protocol.Command;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.remote.interfaces.IDuplexCommunication;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertQueueService;
import com.landray.kmss.sys.filestore.util.SysFileStoreUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import io.netty.channel.ChannelHandlerContext;

public class ServerHeartBeatDuplexCommunication implements IDuplexCommunication {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ServerHeartBeatDuplexCommunication.class);

	public ServerHeartBeatDuplexCommunication(ISysFileConvertDataService dataService, int listenPort, int messageHandlerCode) {
		this.dataService = dataService;
		this.listenPort = listenPort;
		this.messageHandlerCode = messageHandlerCode;
	}

	private int listenPort;
	private int messageHandlerCode;
	private boolean ready = false;

	public int getListenPort() {
		return listenPort;
	}

	public void setListenPort(int listenPort) {
		this.listenPort = listenPort;
	}

	public int getMessageCode() {
		return messageHandlerCode;
	}

	public void setMessageCode(int messageCode) {
		this.messageHandlerCode = messageCode;
	}

	private void initialNettyWorkers() {
		NettyServerConfig serverConfig = new NettyServerConfig();
		serverConfig.setListenPort(SysFileStoreUtil.getClientRegisterPort());
		serverConfig.setServerWorkerThreads(6);
		nettyServer = new NettyRemotingServer("fileStore-heartBeat", serverConfig, new ServerChannelEventListener(dataService));
		nettyServer.registerProcessor(messageHandlerCode, this);

		NettyClientConfig clientConfig = new NettyClientConfig();
		clientConfig.setConnectTimeoutMillis(8000);
		clientConfig.setClientWorkerThreads(6);
		nettyClient = new NettyRemotingClient("fileStore-heartBeat");
	}

	private RemotingServer nettyServer = null;
	private RemotingClient nettyClient = null;

	private ISysFileConvertDataService dataService = null;

	protected void logWarn(String message) {
		if (logger != null && logger.isWarnEnabled()) {
			logger.warn(message);
		}
	}

	protected void logWarn(String message, Throwable throwable) {
		if (logger != null && logger.isWarnEnabled()) {
			logger.warn(message, throwable);
		}
	}

	@Override
	public Command processRequest(ChannelHandlerContext ctx, Command receiveMessage) throws RemotingException {
		logger.debug("应用接收到消息:" + receiveMessage);
		Map<String, String> receiveInfos = receiveMessage.getExtFields();
		Command response = Command.createResponseCommand(receiveMessage.getCode(), receiveMessage.getRemark(), null);
		String msgType = "";
		if (receiveInfos.containsKey("msgType")) {
			msgType = receiveInfos.get("msgType");
		}
		if ("clientInfo".equals(msgType)) {
			try {
				String clientID = dataService.registerClient(receiveInfos);
				response.getExtFields().put("success", "true");
				response.getExtFields().put("clientID", clientID);
			} catch (Exception e) {
				response.getExtFields().put("success", "false");
				response.getExtFields().put("failureType", "registerClient");
				response.getExtFields().put("failureInfo", e.getLocalizedMessage());
			}
		} else if ("isAlive".equals(msgType)) {
			// 能到这就说明能接收到消息就说明就是Live的
			response.getExtFields().put("success", "true");
			if (logger.isDebugEnabled()) {
				logger.debug("I'm live");
			}
		} else if ("amIRegistered".equals(msgType)) {
			String clientIP = receiveInfos.get("clientIP");
			String clientPort = receiveInfos.get("clientPort");
			String clientProcessID = receiveInfos.get("processID");
			boolean isRegistered = dataService.isClientRegistered(clientIP, clientPort, clientProcessID);
			response.getExtFields().put("success", isRegistered ? "true" : "false");
			if (isRegistered) {
				logger.debug("Yes,you are registered");
			}
		} else if ("getRegisteredMaxPort".equals(msgType)) {
			String clientIP = receiveInfos.get("clientIP");
			response.getExtFields().put("maxPort", dataService.getClientMaxPort(clientIP).toString());
			response.getExtFields().put("success", "true");
		}
//		} else if (msgType.equals("convertFinish")) {
//			logger.info("转换完成，接收转换结果:" + receiveMessage);
//			String convertFinishResult = receiveInfos.get("convertFinishResult");
//			String queueId = receiveInfos.get("queueId");
//			if (StringUtil.isNotNull(queueId) && dataService.isExistQueue(queueId)) {
//					try {
//						//成功回调
//						try {
//							if ("true".equals(convertFinishResult)) {
//								dataService.successCallback(receiveInfos);
//							}
//						} catch (Exception e) {
//							logger.error("保存结果出错", e);
//							response.getExtFields().put("failureType", "successCallback");
//							throw e;
//						}
//						//改变队列状态
//						try {
//							dataService.setRemoteConvertQueue(receiveMessage, null, null, null, null);
//						} catch (Exception e) {
//							logger.error("改变队列状态出错", e);
//							response.getExtFields().put("failureType", "taskStatus");
//							throw e;
//						}
//						response.getExtFields().put("success", "true");
//					} catch (Exception e) {
//						response.getExtFields().put("success", "false");
//						response.getExtFields().put("failureInfo", e.getLocalizedMessage());
//					}
//			} else {
//				response.getExtFields().put("success", "true");
//			}
//			
//			//保存压缩文件中解压的附件到数据库
//			if (StringUtil.isNotNull(queueId)){
//				try {
//					addFile(queueId);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
		logger.info("返回通讯结果:" + response);
		return response;
	}
	
	private ISysFileConvertQueueService queueService = null;
	
	public ISysFileConvertQueueService getQueueService() {
		if(queueService==null){
			queueService = (ISysFileConvertQueueService) SpringBeanUtil.getBean("sysFileConvertQueueService");
		}
		return queueService;
	}

	private void addFile(String fdId) throws Exception{
		SysFileConvertQueue queue = (SysFileConvertQueue) getQueueService().findByPrimaryKey(fdId,SysFileConvertQueue.class,true);
		getQueueService().updateAtt(queue);
	}

	@Override
	public void start() {
		if (!ready) {
			try {
				initialNettyWorkers();
				nettyServer.start();
				nettyClient.start();
				ready = true;
			} catch (Exception e) {
				logWarn("启动Netty通讯出错", e);
			}
		}
	}

	@Override
	public void stop() {
		if (nettyServer != null) {
			try {
				nettyServer.shutdown();
			} catch (RemotingException e) {
				logger.debug("停止Netty出错");
			}
		}
		if (nettyClient != null) {
			try {
				nettyClient.shutdown();
			} catch (RemotingException e) {
				logger.debug("停止Netty出错");
			}
		}
		ready = false;
	}

	@Override
	public boolean isAlive() {
		if (!ready) {
			logWarn("Netty通讯没有准备好");
			return false;
		}
		Command areYouAlive = Command.createRequestCommand(messageHandlerCode, null);
		areYouAlive.getExtFields().put("msgType", "isAlive");
		Command aliveResp;
		boolean isAlive = false;
		try {
			// 给自己发消息
			aliveResp = sendMessageToRemoteSyn("127.0.0.1:" + listenPort, areYouAlive, 3000);
			isAlive = SysFileStoreUtil.isCommandSuccess(aliveResp);
		} catch (Exception e) {
			logger.debug("isAlive出错", e);
			isAlive = false;
		}
		return isAlive;
	}

	@Override
	public void sendMessageToRemoteAsyn(String remoteAddress, Command sendMessage, long timeoutMill,
			InvokeCallback sendMessageCallback) throws InterruptedException, RemotingException {
		if (!ready) {
			logWarn("Netty通讯没有准备好");
			return;
		}
		nettyClient.sendMessageOnAsync(remoteAddress, sendMessage, timeoutMill, sendMessageCallback);
	}

	@Override
	public Command sendMessageToRemoteSyn(String remoteAddress, Command sendMessage, long timeoutMill)
			throws InterruptedException, RemotingConnectException, RemotingSendRequestException,
			RemotingTimeoutException {
		Command respMessage = null;
		if (!ready) {
			logWarn("Netty通讯没有准备好");
			return respMessage;
		}
		respMessage = nettyClient.sendMessageOnSync(remoteAddress, sendMessage, timeoutMill);
		return respMessage;
	}

	@Override
	public boolean isReady() {
		return ready;
	}
}
