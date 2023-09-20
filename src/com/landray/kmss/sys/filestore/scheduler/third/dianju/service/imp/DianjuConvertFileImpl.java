package com.landray.kmss.sys.filestore.scheduler.third.dianju.service.imp;

import com.landray.kmss.sys.filestore.convert.constant.HandleStatus;
import com.landray.kmss.sys.filestore.convert.domain.ConvertRequestQueue;
import com.landray.kmss.sys.filestore.convert.service.ConvertRequestHandle;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.queue.service.ConvertQueue;
import com.landray.kmss.sys.filestore.queue.util.InfoObserverUtil;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.builder.ConvertRequestHeaderBuilder;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.builder.ConvertRequestParameterBuilder;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.dto.DianjuConvertRequest;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.service.IDianjuConvertFile;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.util.ConfigUtil;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.util.FileUtil;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.util.FireThirdApplicatonUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import static com.landray.kmss.sys.filestore.scheduler.third.dianju.constant.ConstantParameter.*;

/**
 * 点聚转换服务(非熔断)
 */
public class DianjuConvertFileImpl implements IDianjuConvertFile {
	private static final Logger logger = LoggerFactory.getLogger(DianjuConvertFileImpl.class);
	// 是否是失败队列在执行
	private final AtomicBoolean isFailureConverting = new AtomicBoolean(false);
	// 锁
	private ReentrantLock handleQueueLock = new ReentrantLock();
	private ConvertQueue convertQueue = null;

	/**
	 * 转换队列信息
	 */
	private static ConvertQueue convertQueueInstance = null;
	private static ConvertQueue getConvertQueue() {
		if(convertQueueInstance == null) {
			convertQueueInstance = (ConvertQueue) SpringBeanUtil.getBean("convertQueueImpl");
		}

		return convertQueueInstance;
	}
	/**
	 * 转换失败的队列
	 */
	private static ConvertQueue failConvertQueue = null;
	private static ConvertQueue getFailConvertQueue() {
		if(failConvertQueue == null) {
			failConvertQueue = (ConvertQueue) SpringBeanUtil.getBean("failConvertQueueImpl");
		}

		return failConvertQueue;
	}

	/**
	 * 请求操作
	 */
	private static ConvertRequestHandle requestHandel = null;
	private static ConvertRequestHandle getRequestHandel() {
		if(requestHandel == null) {
			requestHandel = (ConvertRequestHandle) SpringBeanUtil.getBean("dianjuConvertRequestHandleImpl");
		}

		return requestHandel;
	}

	/**
	 * 转换业务
	 * @param serviceName
	 */
	@Override
	public void doDistributeConvertQueue(String serviceName) {

		TransactionStatus status = null;
		Throwable throwable = null;
		try {
			status = TransactionUtils.beginNewTransaction();

			try {
				handleQueueLock.lock();
				// 取第一次上传的队列
				convertQueue = getConvertQueue();

				if(convertQueue.size(CONVERT_DIANJU) <= 0) {
					// 取失败队列
					convertQueue = getFailConvertQueue();

					// 设置是否是失败队列为是
					if(convertQueue.size(CONVERT_DIANJU) > 0) {
						isFailureConverting.compareAndSet(false, true);
					}
				}

				if(logger.isDebugEnabled()) {
					logger.debug("当时是否为转换失败队列：{}", isFailureConverting.get());
					logger.debug("转换当前队列数量：{}", getConvertQueue().size(CONVERT_DIANJU));
				}


				// 通知观察者加载数据
				if (convertQueue.size(CONVERT_DIANJU) <= 0){
					if(logger.isDebugEnabled()) {
						logger.debug("转换当前队列为0,通知观察者查询数据");
					}

					InfoObserverUtil.getInstance().infoObserver(new String[]{"toOFD", "toPDF"}, CONVERT_DIANJU);
					TransactionUtils.commit(status);
					return;
				}

				// 执行转换
				doRequestConvert();

				if(logger.isDebugEnabled()) {
                	logger.debug("请求转换之后，当前队列数量：{}", getConvertQueue().size(CONVERT_DIANJU));
				}

				// 设置当前不为失败队列
				isFailureConverting.compareAndSet(true, false);
			} finally {
				handleQueueLock.unlock();
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
	 * 执行转换业务
	 * @throws Exception
	 */
	public void doRequestConvert() throws Exception{
		while (convertQueue.size(CONVERT_DIANJU) > 0) {
			// 正常转换队列有值并且当前正在转换失败队列，则切换为正常队列执行
			if(getConvertQueue().size(CONVERT_DIANJU) > 0
					&& isFailureConverting.compareAndSet(true, false)) {
				logger.warn("有新队列数量，失败队列停止转换,前交换当前队列为新队列");
				convertQueue = getConvertQueue();
				continue;
			}

			SysFileConvertQueue deliveryTaskQueue = convertQueue.take(CONVERT_DIANJU);

			if (logger.isDebugEnabled()) {
				logger.debug("点聚转换服务队列ID:{},**AttMainId:{},**转换类型：{},**文件下载地址：{}",
						deliveryTaskQueue.getFdId() ,
						deliveryTaskQueue.getFdAttMainId(),
						deliveryTaskQueue.getFdConverterType(),
						deliveryTaskQueue.getFdFileDownUrl());
			}

			String taskId = UUID.randomUUID().toString(); //任务ID
			String selfUrl = URLEncoder.encode(FireThirdApplicatonUtil.getFilePath(deliveryTaskQueue.getFdFileName(),
					deliveryTaskQueue.getFdAttMainId())); //EKP下载地址
			if (logger.isDebugEnabled()) {
				logger.debug("EKP下载地址是：{}", selfUrl);
			}
			// 请求参数
			Map<String, Object> convertRequestParameter =
					getConvertRequestParameter(deliveryTaskQueue, taskId, selfUrl);

			if (logger.isDebugEnabled()) {
				logger.debug("点聚转换请求参数：{}", convertRequestParameter.toString());
			}

			// 请求头信息
			Map<String, String> convertRequestHeader = getConvertRequestHeader(convertRequestParameter);

			if (logger.isDebugEnabled()) {
				logger.debug("点聚转换请求头信息：{}", convertRequestHeader.toString());
			}


			DianjuConvertRequest convertRequest = (DianjuConvertRequest) new DianjuConvertRequest(CONVERT_DIANJU)
					.setConvertRequestParameter(convertRequestParameter)
					.setConvertRequestHeader(convertRequestHeader)
					.setDeliveryTaskQueue(deliveryTaskQueue)
					.setTaskId(taskId)
					.setRequestType(HandleStatus.REQUEST_TYPE_CONVERT)
					.setExpireTime(System.currentTimeMillis())
					.setConvertRequestHandle(getRequestHandel());

			if(logger.isDebugEnabled()) {
				logger.debug("请求转换的信息：{}", convertRequest.toString());
			}

			// 放入请求队列
			ConvertRequestQueue.getInstance().offer(convertRequest);


		}
	}

	/**
	 * 请求参数
	 * @param deliveryTaskQueue
	 * @param selfUrl
	 * @return
	 */
	public Map<String, Object> getConvertRequestParameter(SysFileConvertQueue deliveryTaskQueue, String taskId,String selfUrl) {
		return new ConvertRequestParameterBuilder()
				.createBaseData(ConfigUtil.configValue(CONVERT_DIANJU_SERVER_ID), taskId)
				.createMetaData()
				.createWaterMarkData()
				.createFileData(FileUtil.getFileName(deliveryTaskQueue.getFdFileName()),
						FileUtil.getFileType(deliveryTaskQueue.getFdFileName()),
						FileUtil.getFileConvertType(deliveryTaskQueue.getFdConverterKey()),
						selfUrl)
				.create();
	}

	/**
	 * 请求头信息
	 * @param convertRequestParameter
	 * @return
	 */
	public Map<String, String> getConvertRequestHeader(Map<String, Object> convertRequestParameter) {
		return new ConvertRequestHeaderBuilder()
				.configContentType()
				.configContentMd5(ConfigUtil.configValue(CONVERT_DIANJU_APP_ID),
						ConfigUtil.configValue(CONVERT_DIANJU_APP_KEY),
						convertRequestParameter)
				.configDate()
				.configAuthorization(ConfigUtil.configValue(CONVERT_DIANJU_APP_ID),
						ConfigUtil.configValue(CONVERT_DIANJU_APP_KEY),
						convertRequestParameter)
				.config();
	}

}
