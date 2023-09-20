package com.landray.kmss.sys.filestore.service.spring;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import org.slf4j.Logger;
import org.hibernate.query.NativeQuery;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.attachment.model.SysAttBase;
import com.landray.kmss.sys.attachment.model.SysAttRtfData;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.cluster.remoting.protocol.Command;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.filestore.model.SysFileConvertClient;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.remote.interfaces.IDuplexCommunication;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertCallbackService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertClientService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertConfigService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertLogService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertQueueService;
import com.landray.kmss.sys.filestore.service.ISysFileViewerParamService;
import com.landray.kmss.sys.filestore.util.SysFileStoreUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;

@SuppressWarnings({ "unchecked", "unused" })
public class SysFileConvertDataServiceImp implements ISysFileConvertDataService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysFileConvertDataServiceImp.class);

	protected ISysFileConvertQueueService sysFileConvertQueueService = null;
	protected ISysFileConvertConfigService sysFileConvertConfigService = null;
	protected ISysFileConvertClientService sysFileConvertClientService = null;
	protected ISysFileConvertLogService sysFileConvertLogService = null;
	protected ISysFileViewerParamService sysFileViewerParamService = null;
	protected ISysFileViewerParamService sysFileService = null;
	protected ISysFileConvertCallbackService sysFileConvertCallbackService = null;
	private final ReentrantLock lock = new ReentrantLock();
	
	public ISysFileConvertCallbackService getFileConvertCallbackService() {
		if (sysFileConvertCallbackService == null) {
			sysFileConvertCallbackService = (ISysFileConvertCallbackService) SpringBeanUtil
					.getBean("sysFileConvertCallbackService");
		}
		return sysFileConvertCallbackService;
	}
	
	@Override
	public List<SysFileConvertQueue> getUnsignedTasks(String dispenser, String converterKey) {
		return getQueueService().getUnassignedTasks(dispenser, converterKey,
				Integer.valueOf(getConfigService().getGlobalConfigForm().getUnsignedTaskGetNum()));
	}

	@Override
	public List<SysFileConvertQueue> getUnassignedTasksByconverterKeys(String dispenser, String[] converterKeys) {
		return getQueueService().getUnassignedTasksByconverterKeys(dispenser, converterKeys,
				Integer.valueOf(getConfigService().getGlobalConfigForm().getUnsignedTaskGetNum()));
	}


	/**
	 * 根据多个key查询队列的指定的转换器
	 *
	 * @param converterType  转换器
	 * @param converterKeys
	 * @return
	 */
	@Override
	public List<SysFileConvertQueue> getUnassignedTasksByKeysAndType(String converterType, String[] converterKeys,
																	  Object o) {
		return getQueueService().getUnassignedTasksByKeysAndType(converterType, converterKeys,
				Integer.valueOf(getConfigService().getGlobalConfigForm().getUnsignedTaskGetNum()), o);
	}

	@Override
	public SysFileConvertClient getAliveAvailIdleClient(IDuplexCommunication communication,
			SysFileConvertQueue taskQueue) {
		return getClientService().getAliveAvailIdleClient(communication, taskQueue);
	}

	@Override
	public void setRemoteConvertQueue(Command receiveMessage, String statusType, String queueId,
			String clientId, String statusDesc) throws Exception {
		try{
			lock.tryLock(2000, TimeUnit.MILLISECONDS);
			getQueueService().setRemoteConvertQueue(receiveMessage, statusType, queueId, clientId, statusDesc,
					getClientService(), getLogService());
		}catch(Exception e){
			logger.error("获取锁超时");
		}finally{
			lock.unlock();
		}

	}

	@Override
	public synchronized void setLocalConvertQueue(String statusType, String queueId, String failureInfo)
			throws Exception {
		getQueueService().setLocalConvertQueue(statusType, queueId, failureInfo, getLogService());
	}

	@Override
	public synchronized void setLocalWpsConvertQueue(String statusType,
			String queueId, String failureInfo)
			throws Exception {
		getQueueService().setLocalWpsConvertQueue(statusType, queueId,
				failureInfo, getLogService());
	}

	@Override
	public boolean isClientRegistered(String clientIP, String clientPort, String clientProcessID) {
		return getClientService().isClientRegistered(clientIP, clientPort, clientProcessID);
	}

	@Override
	public String registerClient(Map<String, String> receiveInfos) throws Exception {
		return getClientService().registerClient(receiveInfos);
	}

	@Override
	public void enableDefaultConvertConfig() {
		getConfigService().enableDefaultConvertConfig();
	}

	@Override
	public void clearInvalidQueue() throws Exception {
		getQueueService().clearInvalid();
		// doClearInvalidQueue();
	}

	private void doClearInvalidQueue() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setRowSize(200);
		Page queuesPage = Page.getEmptyPage();
		try {
			queuesPage = getQueueService().findPage(hqlInfo);
		} catch (Exception e) {
			logger.info("获取队列出错", e);
		}
		int pageNo = 1;
		boolean pageNoCanAdd = true;
		List<SysFileConvertQueue> queues = null;
		while (pageNo <= queuesPage.getTotal()) {
			queues = queuesPage.getList();
			if (queues != null) {
				for (SysFileConvertQueue queue : queues) {
					if (isInvalidQueue(queue)) {
						pageNoCanAdd = false;
						getQueueService().delete(queue);
					}
				}
				queues.clear();
			}
			if (pageNoCanAdd) {
				pageNo++;
			} else {
				pageNoCanAdd = true;
			}
			if (pageNo <= queuesPage.getTotal()) {
				hqlInfo.setPageNo(pageNo);
				try {
					queuesPage = getQueueService().findPage(hqlInfo);
				} catch (Exception e) {
					logger.info("获取队列出错", e);
					break;
				}
			}
		}
	}

	private boolean isInvalidQueue(SysFileConvertQueue queue) throws Exception {
		boolean isInvalid = false;
		String attFileId = queue.getFdFileId();
		if (StringUtil.isNotNull(attFileId)) {
			isInvalid = getAttUploadService().getFileById(attFileId) == null;
		} else {
			String attMainId = queue.getFdAttMainId();
			if (StringUtil.isNotNull(attMainId)) {
				List<SysAttBase> atts = getAttService().findList("fdId='" + attMainId + "'", "");
				isInvalid = (atts == null || atts.size() == 0)
						&& getAttService().findRtfDataByPrimaryKey(attMainId) == null;
			}
		}
		return isInvalid;
	}

	@Override
	public void newRuleToQueue(String model) {
		logger.info(ResourceUtil.getString("oldDataToQueue_1", "sys-filestore"));
		Date nowStart = new Date();
		logger.info("旧附件入队列开始时间:" + DateUtil.convertDateToString(nowStart, DateUtil.PATTERN_DATETIME));
		doNewRuleToQueue(model);
		Date nowFinish = new Date();
		logger.info("旧附件入队列结束时间:" + DateUtil.convertDateToString(nowFinish, DateUtil.PATTERN_DATETIME));
		logger.info("耗时:" + (nowFinish.getTime() - nowStart.getTime()) / 1000 + "S");
		logger.info(ResourceUtil.getString("oldDataToQueue_2", "sys-filestore"));

	}

	private void doNewRuleToQueue(String model) {
		HQLInfo hqlInfo = new HQLInfo();
		if (StringUtil.isNotNull(model)) {
			hqlInfo.setWhereBlock("fdModelName like :modelName");
			hqlInfo.setParameter("modelName", formatModelName(model));
		}
		hqlInfo.setRowSize(200);
		Page attsPage = Page.getEmptyPage();
		try {
			attsPage = getAttService().findPage(hqlInfo);
		} catch (Exception e) {
			logger.info("获取待进队列的附件出错", e);
		}
		int pageTotal = attsPage.getTotal();
		int pageNo = 1;
		List<SysAttBase> atts = null;
		while (pageNo <= pageTotal) {
			atts = attsPage.getList();
			if (atts != null) {
				for (SysAttBase att : atts) {
					try {
						getQueueService().addQueue(att.getFdFileId(), att.getFdFileName(), att.getFdModelName(),
								att.getFdModelId(), "", att.getFdId());
					} catch (Exception e) {
						logger.info("附件【" + att.getFdFileName() + "】进队列出错", e);
					}
				}
			}
			pageNo++;
			if (pageNo <= pageTotal) {
				hqlInfo.setPageNo(pageNo);
				try {
					attsPage = getAttService().findPage(hqlInfo);
				} catch (Exception e) {
					logger.info("获取待进队列的附件出错", e);
					break;
				}
			}
		}
		doRtfDataToQueue(model);
	}

	private void doRtfDataToQueue(String model) {
		try {
			String getRtfFdIds = "select fd_id from sys_att_rtf_data";
			if (StringUtil.isNotNull(model)) {
				getRtfFdIds += " where fd_model_name like '%" + formatModelName(model) + "%'";
			}
			NativeQuery rtfFdIdsSQL = getAttService().getBaseDao().getHibernateSession().createNativeQuery(getRtfFdIds);
			List<String> fdIds = rtfFdIdsSQL.list();
			if (fdIds != null && fdIds.size() > 0) {
				SysAttRtfData rtfData = null;
				for (String rtfId : fdIds) {
					rtfData = getAttService().findRtfDataByPrimaryKey(rtfId);
					getQueueService().addQueue(rtfData.getFdFileId(), rtfData.getFdFileName(), rtfData.getFdModelName(),
							rtfData.getFdModelId(), "", rtfData.getFdId());
				}
			}
		} catch (Exception e) {
			logger.debug("RTF图片进队列出错");
		}
	}

	private String formatModelName(String modelName) {
		String result = modelName;
		result = result.replace("/", ".");
		if (result.charAt(0) == '.') {
			result = result.substring(1);
		}
		return "%" + result + "%";
	}

	@Override
	public ISysFileViewerParamService getViewerParamService() {
		if (sysFileViewerParamService == null) {
			sysFileViewerParamService = (ISysFileViewerParamService) SpringBeanUtil
					.getBean("sysFileViewerParamService");
		}
		return sysFileViewerParamService;
	}

	@Override
	public String getFilePath(String attFileId, String attMainId) {
		return getQueueService().getQueueFilePath(attFileId, attMainId);
	}
	
	@Override
	public String getFileLocation(String attFileId) {
		return getQueueService().getQueueFileLocation(attFileId);
	}
	
	@Override
	public String getFilePath(String attFileId, String attMainId,
			Boolean isFullPath) {
		return getQueueService().getQueueFilePath(attFileId, attMainId,
				isFullPath);
	}
	
	
	@Override
	public ISysAppConfigService getAppConfigService() {
		return getConfigService().getSysAppConfigService();
	}

	@Override
	public ISysFileConvertConfigService getConfigService() {
		if (sysFileConvertConfigService == null) {
			sysFileConvertConfigService = (ISysFileConvertConfigService) SpringBeanUtil
					.getBean("sysFileConvertConfigService");
		}
		return sysFileConvertConfigService;
	}

	@Override
	public String getModuleName(String attMainId) {
		String moduleName = "";
		try {
			Object obj = getAttService().findFirstOne("fdId='" + attMainId + "'", "");
			SysAttBase att = obj == null ? getAttService().findRtfDataByPrimaryKey(attMainId) : (SysAttBase) obj;
			if (att != null) {
				String modelClassName = att.getFdModelName();
				if (StringUtil.isNotNull(modelClassName)) {
					SysDictModel dictModel = SysDataDict.getInstance().getModel(modelClassName);
					if (dictModel != null) {
						String messageKey = dictModel.getMessageKey();
						if (StringUtil.isNotNull(messageKey)) {
							return ResourceUtil.getString(messageKey);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.info("获取所属模块出错");
		}
		return moduleName;
	}

	@Override
	public void resetConvertInfo() throws Exception {
		logger.info(ResourceUtil.getString("oldDataToQueue_1", "sys-filestore"));
		Date nowStart = new Date();
		logger.info("重置旧版转换开始时间:" + DateUtil.convertDateToString(nowStart, DateUtil.PATTERN_DATETIME));
		checkAttConvertConfig();
		deleteOldConvertDatas();
		deleteOldConvertFiles();
		Date nowFinish = new Date();
		logger.info("重置旧版转换结束时间:" + DateUtil.convertDateToString(nowFinish, DateUtil.PATTERN_DATETIME));
		logger.info("耗时:" + (nowFinish.getTime() - nowStart.getTime()) / 1000 + "S");
		logger.info(ResourceUtil.getString("oldDataToQueue_2", "sys-filestore"));
	}

	private void deleteOldConvertDatas() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdId");
		hqlInfo.setWhereBlock(
				"fdConverterKey != :converterKey1 and fdConverterKey != :converterKey2 and fdConverterKey != :converterKey3 and fdConverterKey != :converterKey5 and fdConverterKey != :converterKey6 and fdConverterKey != :converterKey7 and fdConverterKey != :converterKey8 and fdConverterKey != :converterKey9");
		hqlInfo.setParameter("converterKey1", "toHTML");
		hqlInfo.setParameter("converterKey2", "toSwf");
		hqlInfo.setParameter("converterKey3", "wordToPic");
		hqlInfo.setParameter("converterKey5", "videoToFlv");
		hqlInfo.setParameter("converterKey6", "image2thumbnail");
		hqlInfo.setParameter("converterKey7", "toPDF");
		hqlInfo.setParameter("converterKey8", "cadToImg");
		hqlInfo.setParameter("converterKey9", "videoToMp4");
		List<String> queueIds = getQueueService().findValue(hqlInfo);
		if (queueIds != null && queueIds.size() > 0) {
			String[] queueIdsArray = new String[queueIds.size()];
			queueIds.toArray(queueIdsArray);
			getQueueService().deleteQueues(queueIdsArray);
		}
	}

	private void checkAttConvertConfig() throws Exception {
		if (SysFileStoreUtil.getConvertConfigs().size() == 0) {
			throw new Exception("没有初始化转换配置,请初始化");
		}
	}

	private void deleteOldConvertFiles() {
		String attFileDir = ResourceUtil.getKmssConfigString("kmss.resource.path");
		deleteOldConvertFile(attFileDir);
	}

	private void deleteOldConvertFile(String attDir) {
		List<File> allConvertFilesDir = getConvertFilesDirFiles(attDir);
		for (File item : allConvertFilesDir) {
			deleteDir(item);
		}
	}

	private boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			File[] children = dir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return (!name.contains("videoToFlv")) && (!name.contains("toHTML"))
							&& (!name.contains("image2thumbnail")) && (!name.contains("toPDF"));
				}
			});
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(children[i]);
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	private List<File> getConvertFilesDirFiles(String attDir) {
		List<File> resultList = new ArrayList<File>();
		File attDirFile = new File(attDir);
		if (attDirFile.exists()) {
			File[] files = attDirFile.listFiles();
			if (files != null && files.length > 0) {
				for (File itemFile : files) {
					if (itemFile.isDirectory()) {
						if (itemFile.getName().contains("_convert")) {
							resultList.add(itemFile);
						} else {
							resultList.addAll(getConvertFilesDirFiles(itemFile.getAbsolutePath()));
						}
					}
				}
			}
		}
		return resultList;
	}

	@Override
	public void clearOldFileConvertInfo(String fileId) {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdId");
		hqlInfo.setWhereBlock("fdFileId = :attFileId");
		hqlInfo.setParameter("attFileId", fileId);
		try {
			List<String> queueIds = getQueueService().findValue(hqlInfo);
			if (queueIds != null && queueIds.size() > 0) {
				String[] queueIdsArray = new String[queueIds.size()];
				queueIds.toArray(queueIdsArray);
				getQueueService().deleteQueues(queueIdsArray);
			}
		} catch (Exception e) {
			logger.info("删除旧版文件转换信息出粗", e);
		}
	}

	@Override
	public ISysFileConvertClientService getClientService() {
		if (sysFileConvertClientService == null) {
			sysFileConvertClientService = (ISysFileConvertClientService) SpringBeanUtil
					.getBean("sysFileConvertClientService");
		}
		return sysFileConvertClientService;
	}

	@Override
	public ISysFileConvertLogService getLogService() {
		if (sysFileConvertLogService == null) {
			sysFileConvertLogService = (ISysFileConvertLogService) SpringBeanUtil.getBean("sysFileConvertLogService");
		}
		return sysFileConvertLogService;
	}

	@Override
	public ISysFileConvertQueueService getQueueService() {
		if (sysFileConvertQueueService == null) {
			sysFileConvertQueueService = (ISysFileConvertQueueService) SpringBeanUtil
					.getBean("sysFileConvertQueueService");
		}
		return sysFileConvertQueueService;
	}

	@Override
	public Integer getClientMaxPort(String clientIP) {
		return getClientService().getMaxPort(clientIP);
	}

	@Override
	public String getHighFidelity(SysFileConvertQueue convertQueue) {
		return getQueueService().getHighFidelity(convertQueue);
	}

	@Override
	public void refreshClients() {
		getClientService().refreshClients(false);
	}

	@Override
	public ISysAttMainCoreInnerService getAttService() {
		return getQueueService().getAttService();
	}

	@Override
	public ISysAttUploadService getAttUploadService() {
		return getQueueService().getAttUploadService();
	}

	@Override
	public Map<String, List<SysFileConvertClient>> getAvailConvertClients(String converterKey) {
		Map<String, List<SysFileConvertClient>> availMapClients = new HashMap<String, List<SysFileConvertClient>>();
		List<SysFileConvertClient> asposeClients = new ArrayList<SysFileConvertClient>();
		List<SysFileConvertClient> yozoClients = new ArrayList<SysFileConvertClient>();
		List<SysFileConvertClient> asposeLongClients = new ArrayList<SysFileConvertClient>();
		List<SysFileConvertClient> yozoLongClients = new ArrayList<SysFileConvertClient>();
		
		try {
			HQLInfo hqlInfo = new HQLInfo();
			StringBuffer whereBlock = new StringBuffer();
			if("videoToFlv".equals(converterKey)) {
				whereBlock.append("avail = :avail and (converterFullKey like :converterKey or converterFullKey like :videoToMp4)");
				hqlInfo.setParameter("videoToMp4", "%videoToMp4%");
				hqlInfo.setWhereBlock(whereBlock.toString());
			}else {
				hqlInfo.setWhereBlock("avail = :avail and converterFullKey like :converterKey");
			}
			hqlInfo.setParameter("avail", Boolean.TRUE);
			hqlInfo.setParameter("converterKey", "%" + converterKey + "%");
			// hqlInfo.setParameter("converterConfig", "%" +
			// "\"deleted\":\"false\"" + "%");// 非删除状态的转换服务
			List<SysFileConvertClient> availClients = getClientService().findList(hqlInfo);
			for (SysFileConvertClient item : availClients) {
				if (isAliveClient(item)) {
					if (item.getConverterFullKey().toLowerCase().contains("yozo")) {
						if(item.getIsLongTask()!=null && item.getIsLongTask()) {
                            yozoLongClients.add(item);
                        } else {
                            yozoClients.add(item);
                        }
					} else if (item.getConverterFullKey().toLowerCase().contains("aspose")) {
						if(item.getIsLongTask()!=null && item.getIsLongTask()) {
                            asposeLongClients.add(item);
                        } else {
                            asposeClients.add(item);
                        }
					}
				}else{
					getClientService().delete(item);
				}
			}
		} catch (Exception e) {
			//
		}
		availMapClients.put("aspose", asposeClients);
		availMapClients.put("asposeLong", asposeLongClients);
		availMapClients.put("yozo", yozoClients);
		availMapClients.put("yozoLong", yozoLongClients);
		return availMapClients;
	}
	
	
   /**
    * 是否升级
    */
	@Override
	public Boolean isConvertClientUpdate(String convertKey) {

		List<SysFileConvertClient> sysFileConvertClient = new ArrayList<SysFileConvertClient>();
		Boolean result = false;
		try {
			HQLInfo hqlInfo = new HQLInfo();
			StringBuffer whereBlock = new StringBuffer();
			
				hqlInfo.setWhereBlock("avail = :avail and converterFullKey like :converterKey");

			hqlInfo.setParameter("avail", Boolean.TRUE);
			hqlInfo.setParameter("converterKey", "%" + convertKey + "%");
			sysFileConvertClient = getClientService().findList(hqlInfo);
			if(sysFileConvertClient != null && sysFileConvertClient.size() > 0)
			{
				//历史兼容问题处理
				for(SysFileConvertClient sfcc : sysFileConvertClient)
				{
					if(sfcc.getIsUpgrade() == null || sfcc.getIsUpgrade() == false)
					{
						result = true;
						sfcc.setIsUpgrade(true);
						getQueueService().update(sfcc);
						
					}
				}
			}
			
		} catch (Exception e) {
			//
		}
		
		return result;
	}

	public boolean isAliveClient(SysFileConvertClient convertClient) {
		return getClientService().isAlive(convertClient);
	}

	@Override
	public boolean isExistQueue(String queueId) {
		return getQueueService().isExist(queueId);
	}

	@Override
	public String getPicResolution(SysFileConvertQueue convertQueue) {
		return getQueueService().getPicResolution(convertQueue);
	}

	@Override
	public String getPicRectangle(SysFileConvertQueue convertQueue) {
		return getQueueService().getPicRectangle(convertQueue);
	}

	@Override
	public void successCallback(Map<String, String> receiveInfos) throws Exception {
		getViewerParamService().setViewerParam(receiveInfos);
		IExtension[] extensions = Plugin.getExtensions("com.landray.kmss.sys.filestore.convert.callback", "*","callbackService");
		for(IExtension extension:extensions){
			ISysFileConvertCallbackService callbackService = Plugin.getParamValue(
					extension, "bean");
			callbackService.setSuccessCallback(receiveInfos);
		}
	}
}
