package com.landray.kmss.sys.filestore.scheduler.third.wps.center.service.imp;

import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.attachment.integrate.wps.interfaces.ISysAttachmentWpsCenterOfficeProvider;
import com.landray.kmss.sys.filestore.constant.ConvertState;
import com.landray.kmss.sys.filestore.convert.cache.ConvertQueueCache;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.api.WPSCenterApi;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.cache.ConvertCallbackCache;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.cache.ConvertRedisCache;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.constant.WPSCenterConstant;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.domain.ConvertGoing;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.service.WPSCenterResultExecutor;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.util.WPSCenterFileUtil;
import com.landray.kmss.sys.filestore.state.ConvertQueueInfo;
import com.landray.kmss.sys.filestore.state.ConvertQueueState;
import com.landray.kmss.sys.filestore.state.ConvertedStateFactory;
import com.landray.kmss.sys.filestore.util.FileStoreConvertUtil;
import org.springframework.transaction.TransactionStatus;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.impl.AbstractQueueScheduler;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.service.IWpsCenterConvertScheduler;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;

import static com.landray.kmss.sys.filestore.constant.ConvertConstant.*;

/**
 * WPS中台转换
 *
 * @date 2021-05-08
 */
public class WpsCenterConvertSchedulerImp extends AbstractQueueScheduler implements IWpsCenterConvertScheduler {

	private static WpsCenterResultExecutorFactory wpsCenterResultExecutorFactory = null;
	private static WPSCenterApi wpsCenterApi = null;
	public void setDataService(ISysFileConvertDataService dataService) {
		this.dataService = dataService;
	}

	private WpsCenterResultExecutorFactory getWPSCenterResultExecutorFactory() {
		if (wpsCenterResultExecutorFactory == null) {
			wpsCenterResultExecutorFactory = (WpsCenterResultExecutorFactory) SpringBeanUtil.getBean("wpsCenterResultExecutorFactory");
		}
		return wpsCenterResultExecutorFactory;
	}

	private WPSCenterApi getWPSCenterApi() {
		if (wpsCenterApi == null) {
			wpsCenterApi = (WPSCenterApi) SpringBeanUtil.getBean("wpsCenterApiImpl");
		}
		return wpsCenterApi;
	}

	/**
	 * 记录状态组件
	 */
	private static ConvertedStateFactory convertedStateFactory = null;
	private ConvertedStateFactory getConvertedStateFactory() {
		if (convertedStateFactory == null) {
			convertedStateFactory = (ConvertedStateFactory) SpringBeanUtil.getBean("convertedStateFactory");
		}

		return convertedStateFactory;
	}

	private static ISysAttachmentWpsCenterOfficeProvider
			attachmentWpsCenterOfficeProvider= null;
	private  ISysAttachmentWpsCenterOfficeProvider getISysAttachmentWpsCenterOfficeProvider() {
		if(attachmentWpsCenterOfficeProvider == null) {
			attachmentWpsCenterOfficeProvider = (ISysAttachmentWpsCenterOfficeProvider) SpringBeanUtil
					.getBean("wpsCenterProvider");
		}

		return attachmentWpsCenterOfficeProvider;
	}

	@Override
	protected String getThreadName() {

		return "WpsCenterConvertScheduler";
	}

	@Override
	protected void doDistributeConvertQueue(String encryptionMode) {

		// 转换中没有勾选wps中台  未开启WPS中台，则不转换
		if(!FileStoreConvertUtil.whetherExecute(THIRD_CONVERTER_WPS_CENTER,false)) {
			return;
		}

		TransactionStatus status = null;
		Throwable throwable = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			List<SysFileConvertQueue> tasks = dataService.getUnassignedTasksByKeysAndType(THIRD_CONVERTER_WPS_CENTER,
					new String[]{CONVERT_TO_OFD, CONVERT_TO_PDF}, null);

			for (SysFileConvertQueue deliveryTaskQueue : tasks) {
				if (rejectDealFileConvert(deliveryTaskQueue)) {
					continue;
				}

				writeLog(deliveryTaskQueue);
				String queueFileName = deliveryTaskQueue.getFdFileName();
				String taskId = UUID.randomUUID().toString(); //任务ID

				//下载地址
			    String filePath = WPSCenterFileUtil.getFilePath(queueFileName,  deliveryTaskQueue.getFdAttMainId());

				confirmTransaction(WPSCenterConstant.WPS_CENTER_BEGIN, false, deliveryTaskQueue);

				// 信息写入缓存给回调使用
				ConvertGoing convertGoing = new ConvertGoing(taskId,
						deliveryTaskQueue.getFdFileId(),
						deliveryTaskQueue.getFdAttMainId(),
						deliveryTaskQueue.getFdConverterKey());

				String jsonConvertGoing = JSONObject.toJSONString(convertGoing);
				if(logger.isDebugEnabled()) {
					logger.debug("WPS中台发起转换前,保存到redis的对象信息：" + jsonConvertGoing);
				}

				ConvertRedisCache.getInstance().put(taskId, jsonConvertGoing);
				if(logger.isDebugEnabled()) {
					logger.debug("转换服务的信息fileId:{}, attmainId:{},convertKey:{},taskId:{}, filePath:{}",
							deliveryTaskQueue.getFdFileId(),
							deliveryTaskQueue.getFdAttMainId(),
							deliveryTaskQueue.getFdConverterKey(),
							taskId, filePath);
				}

				// 放入缓存用于沉淀
				ConvertQueueCache.getInstance().put(taskId, deliveryTaskQueue);

				String object = JSONObject.toJSONString(deliveryTaskQueue);
				// 添加缓存中--用于回调
				ConvertCallbackCache.getInstance().put(taskId, object);
				// #166698 如果转换成功等待回调时，重新分配了，则需要删除回调中的缓存
				ConvertCallbackCache.getInstance().put(deliveryTaskQueue.getFdId(), taskId);
				//转换文件
				String result = getWPSCenterApi().doConvertFile(taskId, filePath,deliveryTaskQueue);
				if(logger.isDebugEnabled()) {
					logger.debug("转换服务转换的结果是：{}" ,result);
				}

				WPSCenterResultExecutor wpsCenterResultExecutor = getWPSCenterResultExecutorFactory()
						.getResultExecutor(result);
				wpsCenterResultExecutor.doResult(dataService, deliveryTaskQueue, taskId);
			}
				
			TransactionUtils.commit(status);

		} catch (Exception e) {
			throwable = e;
			logger.error("文件存储转换出错", e);
		}finally {
			if (throwable != null && status != null) {
				TransactionUtils.rollback(status);
			}
			
		}
		
	}


	/**
	 * 写日志
	 * @param deliveryTaskQueue
	 */
	public void writeLog(SysFileConvertQueue deliveryTaskQueue) {
		if (logger.isDebugEnabled()) {
			logger.debug("WPS中台转换服务队列ID:{}**AttMainId:{}**转换类型：{}**文件下载地址：{}",
					deliveryTaskQueue.getFdId(),
					deliveryTaskQueue.getFdAttMainId(),
					deliveryTaskQueue.getFdConverterType(),
					deliveryTaskQueue.getFdFileDownUrl());
		}
	}

	/**
	 *  .doc  .docx不合法文拒绝处理

	 */
	public Boolean rejectDealFileConvert(SysFileConvertQueue deliveryTaskQueue) throws Exception{
		String queueFileName = deliveryTaskQueue.getFdFileName();
		Boolean isContinue = false;
		if (queueFileName.equalsIgnoreCase(WPSCenterConstant.WPS_CENTER_FILE_DOC)
				|| queueFileName.equalsIgnoreCase(WPSCenterConstant.WPS_CENTER_FILE_DOCX)) {
			dataService.setRemoteConvertQueue(null, "taskInvalid",
					deliveryTaskQueue.getFdId(), "",
					"文件名：" + queueFileName + ",转换失败，因为文件名为【.doc】或【.docx】:"
							+  getISysAttachmentWpsCenterOfficeProvider().getWpsUrl2Converter());
			isContinue = true;
		}

		return isContinue;
	}


	/**
	 * 事务处理
	 * @param success 转换成功与否
	 * @param convertQueue
	 * @param status begin:开始进入转换  end 结束转换
	 */
	public void confirmTransaction(String status, boolean success, SysFileConvertQueue convertQueue) {
		try {

			String desc = "分配给转换服务【WPS中台】:" +  getISysAttachmentWpsCenterOfficeProvider().getWpsUrl2Converter();
			ConvertQueueState convertQueueState = getConvertedStateFactory().getConvertedState(ConvertState.CONVERT_STATE_TASK_ASSIGNED);
			convertQueueState.updateConvertQueue(null, convertQueue, new ConvertQueueInfo("", desc));

		} catch (Exception e) {
			logger.error("WPS中台转换更新表sys_File_Convert_Queue异常：{}", e.getStackTrace());
			logger.error(e.getMessage(), e);
		}
	}
}
