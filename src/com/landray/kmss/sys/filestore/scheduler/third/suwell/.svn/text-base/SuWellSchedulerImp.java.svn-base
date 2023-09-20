package com.landray.kmss.sys.filestore.scheduler.third.suwell;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import com.landray.kmss.sys.filestore.util.FileStoreConvertUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.sys.appconfig.model.BaseAppconfigCache;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.model.SysFileConvertConstant;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.impl.AbstractQueueScheduler;
import com.landray.kmss.sys.filestore.scheduler.third.suwell.interfaces.ISuWellScheduler;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;

import static com.landray.kmss.sys.filestore.constant.ConvertConstant.THIRD_CONVERTER_SHUKE;

/**
 * 数科转OFD的接口实现
 *
 *
 */
public class SuWellSchedulerImp extends AbstractQueueScheduler implements ISuWellScheduler {
	
	protected static final Log logger = LogFactory.getLog(SuWellSchedulerImp.class);

	private void distributeOfficeFile(SysFileConvertQueue convertQueue, String encryptionMode) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("=========进入distributeOfficeFile======");
			}
			
			String filePath = dataService.getFilePath(convertQueue.getFdFileId(), convertQueue.getFdAttMainId());
			if (logger.isDebugEnabled()) {
			    logger.debug("===filePath为:" + filePath);
			}
			
			if (StringUtil.isNotNull(filePath)) {
				officeToOFDExtend(convertQueue, encryptionMode);
			} else {
				logger.warn("数科OFD转换：文件路径为空.");
			}
		} catch (Throwable throwable) {
			logger.warn(throwable.getLocalizedMessage());
		}
	}

	/**
	 * 数科的旧接口，已放弃
	 * @param ofdTask
	 * @param encryptionMode
	 * @throws Exception
	 */
	private void officeToOfd(SysFileConvertQueue ofdTask, String encryptionMode) throws Exception {
		try {
			ISysAttUploadService sysAttUploadService = (ISysAttUploadService) SpringBeanUtil
					.getBean("sysAttUploadService");
			SysAttFile attFile = sysAttUploadService.getFileById(ofdTask.getFdFileId());
			// String officePath =
			// sysAttUploadService.getAbsouluteFilePath(attFile, true);

			String officeExtName = ofdTask.getFdFileExtName();
			if (Suwell.isOfdConvertEnable()) {
				// File office = new File(officePath);
				InputStream is = sysAttUploadService.getFileData(ofdTask.getFdFileId());
				Suwell.officeToOFD(is, attFile.getFdFilePath(), officeExtName);
				dataService.setRemoteConvertQueue(null, "taskAssigned",
						ofdTask.getFdId(), "",
						"分配任务的时候发送消息到转换服务不成功，请检查转换服务【数科转换服务】");
			//	ofdTask.setFdConvertStatus(SysFileConvertConstant.SUCCESS);
			}
		//	dataService.getQueueService().update(ofdTask);
		} catch (Exception e) {
			dataService.setRemoteConvertQueue(null, "taskUnAssigned", ofdTask.getFdId(),
					"",
					"分配任务的时候发送消息到转换服务不成功" + e.getMessage() + ",请检查转换服务【数科转换服务】");
			//ofdTask.setFdConvertStatus(SysFileConvertConstant.FAILURE);
			//dataService.getQueueService().update(ofdTask);
		}
	}
	
	/**
	 * 数科的新接口使用
	 * @param ofdTask
	 * @param encryptionMode
	 * @throws Exception
	 */
	private void officeToOFDExtend(SysFileConvertQueue ofdTask, String encryptionMode) throws Exception {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("=======进入officeToOfdExtend=====");
			}
			
			if (Suwell.isOfdConvertEnable()) {
				ISysAttUploadService sysAttUploadService = (ISysAttUploadService) SpringBeanUtil.getBean("sysAttUploadService");
				SysAttFile attFile = sysAttUploadService.getFileById(ofdTask.getFdFileId());
				if (logger.isDebugEnabled()) {
					logger.debug("数科转OFD文件源文件名：" + ofdTask.getFdFileName());
					logger.debug("数科转OFD文件源文件路径：" + attFile.getFdFilePath());
				}
				InputStream is = sysAttUploadService.getFileData(ofdTask.getFdFileId());
				Suwell.officeToOFDExtend(attFile, is); // 调用数科转换能力
				// 如果是数科异常，则捕获不到。默认也是设置为成功
				ofdTask.setFdConvertStatus(SysFileConvertConstant.SUCCESS);
				dataService.setRemoteConvertQueue(null, "taskAssigned", ofdTask.getFdId(),
						"",
						"数科新接口:分配任务的时候发送消息到转换服务成功。 如未找到OFD文件,请查看数科日志。");
				dataService.getQueueService().update(ofdTask);
			} else {
				logger.warn("数科转OFD服务未开启");
			}
		} catch (Exception e) {
			dataService.setRemoteConvertQueue(null, "taskUnAssigned", ofdTask.getFdId(),
					"",
					"数科新接口:分配任务的时候发送消息到转换服务不成功" + e.getMessage() + ",请检查转换服务【数科转换服务】");
			ofdTask.setFdConvertStatus(SysFileConvertConstant.FAILURE);
			dataService.getQueueService().update(ofdTask);
		}
	}

	@Override
	protected String getThreadName() {
		return "SuWellScheduler";
	}

	public void setDataService(ISysFileConvertDataService dataService) {
		this.dataService = dataService;
	}

	@Override
	protected void doDistributeConvertQueue(String encryptionMode) {
		// 没有勾选数据转换
	if(!FileStoreConvertUtil.whetherExecute(THIRD_CONVERTER_SHUKE,false)) {
		return;
	}

		TransactionStatus status = null;
		boolean success = false;
		try {
			status = TransactionUtils.beginNewTransaction();
			if (Suwell.isOfdConvertEnable()) {
				List<SysFileConvertQueue> unsignedTasks = dataService.getUnsignedTasks("remote", "toOFD");
				if (logger.isDebugEnabled()) {
					logger.debug("unsignedTasks的大小:" + unsignedTasks.size());
				}
				
				for (SysFileConvertQueue deliveryTaskQueue : unsignedTasks) {
					if (logger.isDebugEnabled()) {
						logger.debug("ConverterType:" + deliveryTaskQueue.getFdConverterType());
					}
					
					if(StringUtil.isNotNull(deliveryTaskQueue.getFdConverterType()) 
							&& "skofd".equals(deliveryTaskQueue.getFdConverterType()) && enableSuwell()) {
						distributeOfficeFile(deliveryTaskQueue, encryptionMode);
					}
				}
			}
			TransactionUtils.commit(status);
			success = true;
		} catch (Exception e) {
			success = false;
			if (status != null) {
                TransactionUtils.rollback(status);
            }

			logger.error("文件存储加密线程执行出错", e);
		}
	}

	/**
	 * 是否开启数科转换服务
	 * @return
	 */
	public boolean enableSuwell() {
		boolean enable = false;
		Map<String, String> dataMap = BaseAppconfigCache.getCacheData("com.landray.kmss.sys.filestore.model.SysFileConvertUrlConfig");
		String suwellConvretEnabled = "";
		if (!dataMap.isEmpty()) {
			suwellConvretEnabled = (String) dataMap.get("suwellConvretEnabled");
		}
		
		if(StringUtil.isNotNull(suwellConvretEnabled) && "true".equals(suwellConvretEnabled)) {
			enable = true;
		}
		
		return enable;
	}
}
