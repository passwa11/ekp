package com.landray.kmss.sys.filestore.scheduler.third.foxit.service.impl;

import com.landray.kmss.sys.filestore.convert.domain.ConvertRequestQueue;
import com.landray.kmss.sys.filestore.convert.service.ConvertRequestHandle;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.queue.service.ConvertQueue;
import com.landray.kmss.sys.filestore.queue.util.InfoObserverUtil;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.constant.FoxitApi;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.dto.ConvertRequestDto;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.service.FoxitConvertFile;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.util.FoxitUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import static com.landray.kmss.sys.filestore.convert.constant.HandleStatus.REQUEST_TYPE_CONVERT;
import static com.landray.kmss.sys.filestore.scheduler.third.foxit.constant.FoxitConstant.FOXIT_SERVER_NAME;


/**
 * 实现自己的转换接口
 */
public class FoxitConvertFileImpl implements FoxitConvertFile {
    private static final Logger logger = LoggerFactory.getLogger(FoxitConvertFileImpl.class);
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
    private ConvertRequestHandle convertRequestHandle = null;
    private ConvertRequestHandle getConvertRequestHandle() {
        if (convertRequestHandle == null) {
            convertRequestHandle =
                    (ConvertRequestHandle) SpringBeanUtil.getBean("foxitConvertRequestHandleImpl");
        }

        return convertRequestHandle;
    }

    /**
     * 核心操作
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

                // 如果队列为空，则取失败队列
                if(convertQueue.size(FOXIT_SERVER_NAME) <= 0) {
                    convertQueue = getFailConvertQueue();
                    //设置为失败队列开始转换
                    if(convertQueue.size(FOXIT_SERVER_NAME) > 0) {
                        isFailureConverting.compareAndSet(false, true);
                    }
                }

                if(logger.isDebugEnabled()) {
                    logger.debug("等待转换的队列数量:{}", convertQueue.size(FOXIT_SERVER_NAME));
                }

                // 通知观察者加载数据
                if(convertQueue.size(FOXIT_SERVER_NAME) <= 0) {
                    if(logger.isDebugEnabled()) {
                        logger.debug("当前转换队列为0,通知观察者查询数据");
                    }

                    InfoObserverUtil.getInstance().infoObserver(new String[]{"toOFD", "toPDF"}, FOXIT_SERVER_NAME);
                    TransactionUtils.commit(status);
                    return;
                }

                doRequestConvert();

                if(logger.isDebugEnabled()) {
                    logger.debug("请求转换之后，当前队列数量：{}", getConvertQueue().size(FOXIT_SERVER_NAME));
                }

                // 设置当前不为失败队列
                isFailureConverting.compareAndSet(true, false);

            } finally {
                handleQueueLock.unlock();

                if(logger.isDebugEnabled()) {
                    logger.debug("释放锁");
                }
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
     * 请求转换
     * @throws Exception
     */
    public void doRequestConvert() throws Exception{
        while (convertQueue.size(FOXIT_SERVER_NAME) > 0) {
            // 正常转换队列有值并且当前正在转换失败队列，则切换为正常队列执行
            if(getConvertQueue().size(FOXIT_SERVER_NAME) > 0
                    && isFailureConverting.compareAndSet(true, false)) {
                logger.warn("队列有数量，失败队列停止转换,交换当前失败队列为另外一个队列");
                convertQueue = getConvertQueue();
                continue;
            }

            SysFileConvertQueue deliveryTaskQueue = convertQueue.take(FOXIT_SERVER_NAME);

            if (logger.isDebugEnabled()) {
                logger.debug("福昕转换服务队列ID:{},**AttMainId:{},**转换类型：{},**文件下载地址：{}",
                        deliveryTaskQueue.getFdId() ,
                        deliveryTaskQueue.getFdAttMainId(),
                        deliveryTaskQueue.getFdConverterType(),
                        deliveryTaskQueue.getFdFileDownUrl());
            }


            // 回调地址
            String callbackUrl = FoxitUtil.getSystemUrl() + FoxitApi.FOXIT_CALLBACK_URL;
            // ekp文件下载地址
            String url = FoxitUtil.getFilePath(deliveryTaskQueue.getFdFileName(),  deliveryTaskQueue.getFdAttMainId());
            // 转换队列对象
            ConvertRequestDto convertRequestDto = (ConvertRequestDto) new ConvertRequestDto(FOXIT_SERVER_NAME)
                    .setUrl(url)
                    .setFileId(deliveryTaskQueue.getFdId())
                    .setDeliveryTaskQueue(deliveryTaskQueue)
                    .setSrcFormat(deliveryTaskQueue.getFdFileExtName())
                    .setNewFormat(FoxitUtil.convertType(deliveryTaskQueue.getFdConverterKey()))
                    .setCallbackUrl(callbackUrl)
                    .setSync(false)
                    .setConvertRequestHandle(getConvertRequestHandle())
                    .setRequestType(REQUEST_TYPE_CONVERT);

            if(logger.isDebugEnabled()) {
                logger.debug("转换的对象已经生成:{}", convertRequestDto.toString());
            }

            // 加入请求队列
            ConvertRequestQueue.getInstance().offer(convertRequestDto);
        }
    }
}
