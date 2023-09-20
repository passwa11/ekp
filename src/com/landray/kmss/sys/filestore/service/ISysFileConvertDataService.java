package com.landray.kmss.sys.filestore.service;

import java.util.List;
import java.util.Map;

import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.cluster.remoting.protocol.Command;
import com.landray.kmss.sys.filestore.model.SysFileConvertClient;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.remote.interfaces.IDuplexCommunication;

public interface ISysFileConvertDataService {

	public List<SysFileConvertQueue> getUnsignedTasks(String dispenser, String converterKey);

	public SysFileConvertClient getAliveAvailIdleClient(IDuplexCommunication communication,
			SysFileConvertQueue taskQueue);

	public void setRemoteConvertQueue(Command receiveMessage, String statusType, String queueId, String clientId,
			String statusDesc) throws Exception;

	public void setLocalConvertQueue(String statusType, String queueId, String failureInfo) throws Exception;

	public void setLocalWpsConvertQueue(String statusType, String queueId,
			String failureInfo) throws Exception;

	public boolean isClientRegistered(String clientIP, String processID, String clientProcessID);

	public String registerClient(Map<String, String> receiveInfos) throws Exception;

	public void enableDefaultConvertConfig();

	public void clearInvalidQueue() throws Exception;

	public void newRuleToQueue(String model);

	public ISysFileViewerParamService getViewerParamService();

	public String getFilePath(String fileId, String attMainId);

	public ISysAppConfigService getAppConfigService();

	public ISysFileConvertConfigService getConfigService();

	public ISysFileConvertClientService getClientService();

	public ISysFileConvertLogService getLogService();

	public ISysFileConvertQueueService getQueueService();

	public ISysAttMainCoreInnerService getAttService();

	public ISysAttUploadService getAttUploadService();

	public String getModuleName(String attMainId);

	public void resetConvertInfo() throws Exception;

	public void clearOldFileConvertInfo(String fileId);

	public Integer getClientMaxPort(String clientIP);

	public String getHighFidelity(SysFileConvertQueue convertQueue);

	public void refreshClients();

	public Map<String, List<SysFileConvertClient>> getAvailConvertClients(String converterKey);

	public boolean isExistQueue(String queueId);

	public String getPicResolution(SysFileConvertQueue convertQueue);

	public String getPicRectangle(SysFileConvertQueue convertQueue);
	
	/**
	 * 获取文件完整路径
	 * 
	 * @param attFileId
	 * @param attMainId
	 * @param isFullPath
	 * @return
	 */
	public String getFilePath(String attFileId, String attMainId,
			Boolean isFullPath);
	
	/**
	 * 获取文件存储类型
	 * 
	 * @param attFileId
	 * @return
	 */
	public String getFileLocation(String attFileId);

	/**
	 * convertFinish成功结果回调
	 * @param receiveInfos
	 * @throws Exception 
	 */
	public void successCallback(Map<String, String> receiveInfos) throws Exception;
	
	/**
	 * 通过key获取转换服务信息是否升级
	 * @param likeConvertKey
	 * @return
	 */
	public Boolean isConvertClientUpdate(String convertKey);
	/**
	 * 根据多个key查询队列
	 *
	 * @param dispenser
	 * @param converterKeys
	 * @return
	 */
	 List<SysFileConvertQueue> getUnassignedTasksByconverterKeys(String dispenser, String[] converterKeys);

	/**
	 * 根据多个key查询队列的指定的转换器
	 *
	 * @param converterType  转换器
	 * @param converterKeys
	 * @return
	 */
	List<SysFileConvertQueue> getUnassignedTasksByKeysAndType(String converterType, String[] converterKeys, Object o);
}

