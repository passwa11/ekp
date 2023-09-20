package com.landray.kmss.sys.filestore.scheduler.third.wps.center.service.imp;

import com.landray.kmss.sys.attachment.integrate.wps.interfaces.ISysAttachmentWpsCenterOfficeProvider;
import com.landray.kmss.sys.filestore.constant.ConvertState;
import com.landray.kmss.sys.filestore.convert.cache.ConvertQueueCache;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.cache.ConvertCallbackCache;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.cache.ConvertRedisCache;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.service.WPSCenterResultExecutor;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;
import com.landray.kmss.sys.filestore.state.ConvertQueueInfo;
import com.landray.kmss.sys.filestore.state.ConvertQueueState;
import com.landray.kmss.sys.filestore.state.ConvertedStateFactory;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

/**
 * 转换失败处理
 */
public class WPSCenterFailResultExecutor implements WPSCenterResultExecutor {
    private static final Logger logger = LoggerFactory.getLogger(WPSCenterFailResultExecutor.class);
    /**
     * 记录状态组件
     */
    private static ConvertedStateFactory convertedStateFactory = null;
    public ConvertedStateFactory getConvertedStateFactory() {
        if (convertedStateFactory == null) {
            convertedStateFactory = (ConvertedStateFactory) SpringBeanUtil.getBean("convertedStateFactory");
        }

        return convertedStateFactory;
    }

    private static ISysAttachmentWpsCenterOfficeProvider
            attachmentWpsCenterOfficeProvider= null;
    public  ISysAttachmentWpsCenterOfficeProvider getISysAttachmentWpsCenterOfficeProvider() {
        if(attachmentWpsCenterOfficeProvider == null) {
            attachmentWpsCenterOfficeProvider = (ISysAttachmentWpsCenterOfficeProvider) SpringBeanUtil
                    .getBean("wpsCenterProvider");
        }

        return attachmentWpsCenterOfficeProvider;
    }

    @Override
    public Boolean doResult(ISysFileConvertDataService dataService, SysFileConvertQueue deliveryTaskQueue, String taskId) throws Exception {

            if(logger.isDebugEnabled()) {
                logger.debug("删除redis中的taskId:{}", taskId);
            }
            //清除缓存
            ConvertRedisCache.getInstance().remove(taskId);
            ConvertQueueCache.getInstance().remove(taskId);
            ConvertCallbackCache.getInstance().remove(taskId);
            ConvertCallbackCache.getInstance().remove(deliveryTaskQueue.getFdId());

            Integer convertNum = deliveryTaskQueue.getFdConvertNumber();
            ConvertQueueState convertQueueState = getConvertedStateFactory().
                    getConvertedState(ConvertState.CONVERT_STATE_TASK_UNASSIGNED);

//            String desc = "分配任务的时候发送消息到转换服务不成功，请检查转换服务【WPS中台】:"
//                    + getISysAttachmentWpsCenterOfficeProvider().getWpsUrl2Converter();
//
//            if(convertNum != null && convertNum >= 3) {
//                logger.error("taskId:{}, queueId:{}, 转换次数大于3次，因此，标记为失败队列.", taskId, deliveryTaskQueue.getFdId());
//                desc = "转换失败，请检查转换服务【WPS中台】:"
//                        + getISysAttachmentWpsCenterOfficeProvider().getWpsUrl2Converter();
//                convertQueueState = getConvertedStateFactory().
//                        getConvertedState(ConvertState.CONVERT_OTHER_FINISH_FAILURE);
//            }

            String desc = "转换失败，请检查转换服务【WPS中台】:"
                    + getISysAttachmentWpsCenterOfficeProvider().getWpsUrl2Converter();
            convertQueueState = getConvertedStateFactory().
                    getConvertedState(ConvertState.CONVERT_OTHER_FINISH_FAILURE);

            convertQueueState.updateConvertQueue(null, deliveryTaskQueue, new ConvertQueueInfo("", desc));
            if (logger.isDebugEnabled()) {
                logger.debug("WPS中台转换:WPS转换失败.队列ID:{}", deliveryTaskQueue.getFdId());
            }

            return true;

    }
}
