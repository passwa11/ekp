package com.landray.kmss.sys.filestore.scheduler.remote;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.sys.cluster.remoting.InvokeCallback;
import com.landray.kmss.sys.cluster.remoting.ReadonlyResponse;
import com.landray.kmss.sys.cluster.remoting.protocol.Command;
import com.landray.kmss.sys.filestore.forms.SysFileConvertGlobalConfigForm;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.model.SysFileConvertClient;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.impl.AbstractQueueScheduler;
import com.landray.kmss.sys.filestore.scheduler.remote.interfaces.IDuplexCommunication;
import com.landray.kmss.sys.filestore.scheduler.remote.interfaces.IRemoteScheduler;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertConfigService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;
import com.landray.kmss.sys.filestore.util.SysFileStoreUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.FileMimeTypeUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;

public abstract class RemoteSchedulerImp extends AbstractQueueScheduler implements IRemoteScheduler {

	private IDuplexCommunication nettyCommunication = null;
	
	private ISysAttUploadService sysAttUploadService = null;
	
	private ISysFileConvertConfigService sysFileConvertConfigService = null;

	public abstract String getQueueConverterKey();

	public abstract String getQueueConverterType();

	private void distributeOfficeFile(final SysFileConvertClient queueClient, final SysFileConvertQueue convertQueue,
			String encryptionMode) {
		String fileId = convertQueue.getFdFileId();
		String attMainId = convertQueue.getFdAttMainId();
		String filePath = dataService.getFilePath(fileId, attMainId, true);
		
		if (StringUtil.isNull(filePath)) {
			try {
				dataService.setRemoteConvertQueue(null, "taskInvalid", convertQueue.getFdId(), "",
						"获取不到文件的路径，sys_att_file中fd_id为" + fileId + "的记录不存在或者sys_att_main中fd_id为" + attMainId
								+ "的记录不存在");
			} catch (Exception e) {
				logDebug("设置队列为无效队列出错", e);
			}
		} else {
			if (Thread.interrupted()) {
				logDebug("被中断，开始下一次分发");
				return;
			}
			
			String location = dataService.getFileLocation(fileId);
			
			Command fileInfoMessage = Command.createRequestCommand(queueClient.getClientMessageHandlerCode(), null);
			Map<String, String> convertFileInfos = new HashMap<String, String>();
			convertFileInfos.put("msgType", "fileInfo");
			convertFileInfos.put("fileId", fileId);
			convertFileInfos.put("queueId", convertQueue.getFdId());
			convertFileInfos.put("attMainId", attMainId);
			convertFileInfos.put("fileName", convertQueue.getFdFileName());
			convertFileInfos.put("filePath", filePath);
			convertFileInfos.put("converterFullKey", queueClient.getConverterFullKey());
			convertFileInfos.put("cryptionMode", StringUtil.isNotNull(encryptionMode) ? encryptionMode : "0");
			convertFileInfos.put("highFidelity", dataService.getHighFidelity(convertQueue));
			convertFileInfos.put("queueClientId", queueClient.getFdId());
			convertFileInfos.put("picResolution", dataService.getPicResolution(convertQueue));
			convertFileInfos.put("picRectangle", dataService.getPicRectangle(convertQueue));
			convertFileInfos.put("modelUrl", convertQueue.getFdModelUrl());
			convertFileInfos.put("contentType", FileMimeTypeUtil
					.getContentType(convertQueue.getFdFileName()));
			convertFileInfos.put("location", location);
			fileInfoMessage.setExtFields(convertFileInfos);
			try {
				if (Thread.interrupted()) {
					logDebug("被中断，开始下一次分发");
					return;
				}
				dataService.setRemoteConvertQueue(null, "taskAssigned", convertQueue.getFdId(), queueClient.getFdId(),
						"分配给转换服务【" + queueClient.toString() + "】");
				nettyCommunication.sendMessageToRemoteAsyn(
						queueClient.getClientIP() + ":" + queueClient.getClientPort(), fileInfoMessage, 60000,
						new InvokeCallback() {
							@Override
							public void operationComplete(ReadonlyResponse resp) {
								if (resp.isSendRequestOK()) {
									Command respCMD = resp.getResponse();
									if (!SysFileStoreUtil.isCommandSuccess(respCMD)) {
										logDebug("分配转换队列时接收转换服务消息不成功");// 基本执行不到
										try {
											dataService.setRemoteConvertQueue(null, "taskUnAssigned",
													convertQueue.getFdId(), queueClient.getFdId(),
													"转换服务添加任务出错-FailureType:" + respCMD != null
															? respCMD.getExtFields().get("failureType")
															: "" + ",FailureInfo:" + respCMD != null
																	? respCMD.getExtFields().get("failureInfo") : "");
										} catch (Exception e) {
											logDebug("设置队列为未分配出错");
										}
									}
								} else {
									logDebug("分配转换队列时发送消息到转换服务不成功");
									try {
										dataService.setRemoteConvertQueue(null, "taskUnAssigned",
												convertQueue.getFdId(), queueClient.getFdId(),
												"分配任务的时候发送消息到转换服务不成功，请检查转换服务【" + queueClient.toString() + "】");
									} catch (Exception e) {
										logDebug("设置队列为未分配出错");
									}
								}
							}
						});
			} catch (Exception e) {
				logger.warn("发送消息时出错："+e.getMessage()+"\r\n命令："+fileInfoMessage,e);
		        if (e.getMessage().contains("setConvertQueue")) {
		          logger.warn("设置队列为已分配出错",e);
		        } else {
		            logger.warn("发送消息到转换服务出错", e);
		        }
				try {
					dataService.setRemoteConvertQueue(null, "taskUnAssigned", convertQueue.getFdId(),
							queueClient.getFdId(),
							"分配任务的时候发送消息到转换服务不成功" + e.getMessage() + ",请检查转换服务【" + queueClient.toString() + "】");
				} catch (Exception ee) {
					logDebug("设置队列为未分配出错");
				}
			}
		}
	}

	@Override
	protected void doDistributeConvertQueue(String encryptionMode) {
		if (!nettyCommunication.isReady()) {
			logDebug("Netty通讯没有准备好");
			return;
		}
		logDebug("开始分发");
		if (Thread.interrupted()) {
			logDebug("被中断，开始下一次分发");
			return;
		}
		
		TransactionStatus status = null;
		Exception throwException = null;
		boolean success = false;
		try {
			status = TransactionUtils.beginNewTransaction();
		
			Map<String, List<SysFileConvertClient>> availMapClients = dataService
					.getAvailConvertClients(getQueueConverterKey());
			List<SysFileConvertClient> asposeClients = availMapClients.get("aspose");
			List<SysFileConvertClient> yozoClients = availMapClients.get("yozo");
			
			List<SysFileConvertClient> asposeLongClients = availMapClients.get("asposeLong");
			List<SysFileConvertClient> yozoLongClients = availMapClients.get("yozoLong");
			
			SysFileConvertClient queueClient = null;
			int asposeClientsSize = asposeClients.size();
			
			logger.debug("aspose普通转换客户端个数:" + asposeClientsSize);
			int yozoClientsSize = yozoClients.size();
			logger.debug("yozo普通转换客户端个数:" + yozoClientsSize);
			int asposeLongClientsSize = asposeLongClients.size();
			logger.debug("aspose长转换客户端个数:" + asposeLongClientsSize);
			int yozoLongClientsSize = yozoLongClients.size();
			logger.debug("yozo长转换客户端个数:" + yozoLongClientsSize);
			Random indexRandom = new Random();
			if (yozoClientsSize == 0 && asposeClientsSize == 0 && asposeLongClientsSize==0 && yozoLongClientsSize==0) {
				logDebug("没有启动【" + getQueueConverterType() + "】的转换服务");
			} else {
				List<SysFileConvertQueue> unsignedTasks = dataService.getUnsignedTasks("remote", getQueueConverterKey());
				if (Thread.interrupted()) {
					logDebug("被中断，开始下一次分发");
					if (status != null) {
						TransactionUtils.rollback(status);
					}
					return;
				}
				logDebug("开始遍历分发：" + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME + ":ss"));
				if(unsignedTasks.size()>0){
					sysAttUploadService = (ISysAttUploadService)SpringBeanUtil.getBean("sysAttUploadService");
					sysFileConvertConfigService = (ISysFileConvertConfigService) SpringBeanUtil.getBean("sysFileConvertConfigService");
				}
				
				for (SysFileConvertQueue deliveryTaskQueue : unsignedTasks) {
					queueClient = null;
					
					SysAttFile attFile = sysAttUploadService.getFileById(deliveryTaskQueue.getFdFileId());
					SysFileConvertGlobalConfigForm globalConfigForm = sysFileConvertConfigService.getGlobalConfigForm();
					Boolean bigger = false;
					if(attFile!=null && globalConfigForm!=null && StringUtil.isNotNull(globalConfigForm.getLongTaskSize())){		
						if(attFile.getFdFileSize() > Double.valueOf(globalConfigForm.getLongTaskSize()) * 1024 * 1024) {
                            bigger = true;
                        }
					}
					
					logger.debug("附件大小是否超过临界值:" + bigger);
					if(deliveryTaskQueue.getFdIsLongQueue()==null) {
                        deliveryTaskQueue.setFdIsLongQueue(false);
                    }
					
					
					if ("yozo".equals(deliveryTaskQueue.getFdConverterType())) {
						if(yozoLongClientsSize==0) {
                            bigger = false; //当没有长转换服务的时候，附件限制失效
                        }
						
						logger.debug("永中1");
						if((bigger || deliveryTaskQueue.getFdIsLongQueue()) && yozoLongClientsSize>0){//当附件过大或者被标示为长转换的时候，在长转换服务进行处理
							queueClient = yozoLongClients.get(indexRandom.nextInt(yozoLongClientsSize));
							logger.debug("永中2");
						}else if(!deliveryTaskQueue.getFdIsLongQueue() && yozoLongClientsSize==0 && yozoClientsSize>0){
							//未被标示为长转换又不存在转换服务的时候，使用普通转换
							queueClient = yozoClients.get(indexRandom.nextInt(yozoClientsSize));
							logger.debug("永中3");
						}else if(deliveryTaskQueue.getFdIsLongQueue() && yozoLongClientsSize==0){
							//被标示为长转换又不存在转换服务的时候不处理
							logger.debug("永中4");
							continue;
						}else if (yozoClientsSize > 0) {
							queueClient = yozoClients.get(indexRandom.nextInt(yozoClientsSize));
							logger.debug("永中5");
						}
					} else {
						if(asposeLongClientsSize==0) {
                            bigger = false; //当没有长转换服务的时候，附件限制失效
                        }
						logger.debug("aspose1");
						if((bigger || deliveryTaskQueue.getFdIsLongQueue()) && asposeLongClientsSize>0){//当附件过大或者被标示为长转换的时候，在长转换服务进行处理
							queueClient = asposeLongClients.get(indexRandom.nextInt(asposeLongClientsSize));
							logger.debug("aspose2");
						}else if(!deliveryTaskQueue.getFdIsLongQueue() && asposeLongClientsSize==0 && asposeClientsSize>0){
							//未被标示为长转换又不存在转换服务的时候，使用普通转换
							queueClient = asposeClients.get(indexRandom.nextInt(asposeClientsSize));
							logger.debug("aspose3");
						}else if(deliveryTaskQueue.getFdIsLongQueue() && asposeLongClientsSize==0){
							//被标示为长转换又不存在转换服务的时候不处理
							logger.debug("aspose4");
							continue;
						}else if (asposeClientsSize > 0) {
							queueClient = asposeClients.get(indexRandom.nextInt(asposeClientsSize));
							logger.debug("aspose5");
						}
					}
					if (queueClient != null) {
						logDebug("文件【" + deliveryTaskQueue.getFdFileName() + "】准备分发给【" + queueClient.toString() + "】");
						distributeOfficeFile(queueClient, deliveryTaskQueue, encryptionMode);
					} else {
						logDebug("对应的转换服务没有启动");
					}
					if (Thread.interrupted()) {
						logDebug("被中断，开始下一次分发");
						if (status != null) {
							TransactionUtils.rollback(status);
						}
						return;
					}
				}
				logDebug("结束遍历分发：" + DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATETIME + ":ss"));
				logDebug("分发队列数：" + unsignedTasks.size());
			}
		
			TransactionUtils.commit(status);
			success = true;
		} catch (Exception e) {
			success = false;
			throwException  = e;
			logger.error("文件存储加密线程执行出错", e);
		} finally {
			if (throwException != null && status != null) {
				TransactionUtils.rollback(status);
			}
		}
		
		
	}

	@Override
	public void reDistribute() {
		// 移除打断，打断逻辑里面涉及开关事务，导致死锁
		// runThread.interrupt();
		super.reDistribute();
	}
	

	@Override
	public void setNettyCommunication(IDuplexCommunication nettyCommunication) {
		this.nettyCommunication = nettyCommunication;
	}

	@Override
	public void setDataService(ISysFileConvertDataService dataService) {
		this.dataService = dataService;
	}
}
