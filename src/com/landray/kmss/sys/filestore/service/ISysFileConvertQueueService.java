package com.landray.kmss.sys.filestore.service;

import java.util.List;
import java.util.Map;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.cluster.remoting.protocol.Command;
import com.landray.kmss.sys.filestore.forms.SysFileConvertQueueParamForm;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.sunbor.web.tag.Page;

public interface ISysFileConvertQueueService extends IBaseService {

	/**
	 * 进队列
	 * 
	 * @throws Exception
	 */
	public void addQueue(String fileId, String fileName, String modelName, String modelId, String param,
			String attMainId) throws Exception;
	
	/**
	 * 进队列及判断是否队列已存在，存在则更新（为了某些模块重新生成pdf）
	 * 
	 * @throws Exception
	 */
	public void addQueueAndPdfUpdate(SysAttMain attMain, SysAttMain oldAttMain,String fileId, String fileName, String modelName, String modelId, String param,
			String attMainId) throws Exception;

	/**
	 * 进异步转换OFD队列
	 * 
	 * @param fileId
	 * @param fileName
	 * @param modelName
	 * @param modelId
	 * @param param
	 * @param attMainId
	 * @param serverUrl
	 *            服务器地址，HTML中CSS/IMG的根路径地址，HTML转PDF前检查是否能连通，若无法连通则替换为aspose服务器的dns配置地址
	 * @return
	 * @throws Exception
	 */
	public void addQueueToOFD(String fileId, String fileName, String modelName, String modelId,
			String param, String attMainId) throws Exception;
	
	/**
	 * 进异步转换OFD队列
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public void addFileQueueToOFD(SysAttMain sysAttMain, SysAttMain oldAttMain) throws Exception;

	/**
	 * 调用第三方厂商转换
	 * @throws Exception
	 */
	void addFileToQueue(SysAttMain sysAttMain, SysAttMain oldAttMain) throws Exception;
	
	
	/**
	 * 进异步转换PDF队列
	 * @param fileId
	 * @param fileName
	 * @param modelName
	 * @param modelId
	 * @param param
	 * @param attMainId
	 * @param serverUrl	服务器地址，HTML中CSS/IMG的根路径地址，HTML转PDF前检查是否能连通，若无法连通则替换为aspose服务器的dns配置地址
	 * @return
	 * @throws Exception
	 */
	public SysFileConvertQueue addQueueToPDF(String fileId, String fileName, String modelName, String modelId,
			String param, String attMainId, String serverUrl) throws Exception;


	public void addOrUpdateToPDFQueue(String fileId, String fileName, String modelName, String modelId,
									  String param, String attMainId, String serverUrl) throws Exception;


	/**
	 * 转换队列任务中，该model的所有附件是否全部转换成功
	 * @param modelId	主文档ID
	 * @param converterKey	为null则匹配所有
	 * @param converterType	为null则匹配所有
	 * @return	失败fileId列表
	 * @throws Exception
	 */
	public List<String> isAllSuccess(String modelId, String converterKey, String converterType) throws Exception;
	
	public void updateQueue(String fileId, String fileName, String modelName, String modelId, String param,
			String attMainId) throws Exception;

	public void saveReDistribute(String redistributeAll, String[] ids, String[] failureType) throws Exception;

	public void deleteQueues(String[] ids) throws Exception;

	public Page findOKPage(HQLInfo hqlInfo) throws Exception;

	public void setRemoteConvertQueue(Command receiveMessage, String statusType, String queueId, String clientId,
			String statusDesc, ISysFileConvertClientService clientService, ISysFileConvertLogService logService)
			throws Exception;

	public void setLocalConvertQueue(String statusType, String queueId, String failureInfo,
			ISysFileConvertLogService logService) throws Exception;

	public List<SysFileConvertQueue> getUnassignedTasks(String dispenser, String converterKey, Integer maxTaskNum);

	public void clearInvalid();

	public String getQueueFilePath(String attFileId, String attMainId);

	public ISysAttMainCoreInnerService getAttService();

	public ISysAttUploadService getAttUploadService();

	public boolean isExist(String queueId);

	public SysFileConvertQueueParamForm getParamForm(String fdIds) throws Exception;

	public void saveQueueParam(SysFileConvertQueueParamForm form) throws Exception;

	public String getPicResolution(SysFileConvertQueue convertQueue);

	public String getHighFidelity(SysFileConvertQueue convertQueue);

	public boolean supportHighFidelity(SysFileConvertQueue queue);

	public String getPicRectangle(SysFileConvertQueue convertQueue);
	
	public void updateAtt(SysFileConvertQueue convertQueue) throws Exception;
	
	public void updateAtt(Map<String,String> map,SysAttMain att) throws Exception;
	
	public String getQueueFilePath(String attFileId, String attMainId,
			Boolean isFullPath);
	
	/**
	 * 获取文件存储类型
	 * @param attFileId
	 * @return
	 * @throws Exception
	 */
	public String getQueueFileLocation(String attFileId);
	
	public void clearOldFiles(SysQuartzJobContext context) throws Exception;

	public void setLocalWpsConvertQueue(String statusType, String queueId,
			String failureInfo, ISysFileConvertLogService logService)
			throws Exception;

	void addQueueWithAttMainModelName(String fileId, String fileName, String modelName, String modelId, String param, String attMainId,
			String attMainModelName) throws Exception;

	/**
	 * 根据多个key查询队列
	 *
	 * @param dispenser
	 * @param converterKeys
	 * @param maxTaskNum
	 * @return
	 */
	List<SysFileConvertQueue> getUnassignedTasksByconverterKeys(String dispenser, String[] converterKeys, Integer maxTaskNum);

	/**
	 * 根据多个key查询队列的指定的转换器
	 *
	 * @param converterType  转换器
	 * @param converterKeys
	 * @return
	 */
	List<SysFileConvertQueue> getUnassignedTasksByKeysAndType(String converterType, String[] converterKeys,
															  Integer maxTaskNum, Object o);

	/**
	 * 查询队列
	 * @param fileId
	 * @param attMainId
	 * @param converterKeys
	 * @param convertType
	 * @return
	 * @throws Exception
	 */
	List<SysFileConvertQueue> getQueues(String fileId, String attMainId,
										String[] converterKeys,String convertType) throws Exception;
}
