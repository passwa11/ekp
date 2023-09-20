package com.landray.kmss.sys.filestore.scheduler.third.dianju.service.imp;

import com.landray.kmss.sys.filestore.constant.ConvertState;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.queue.service.ConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.constant.ConstantParameter;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.dto.ConvertRequestResultDTO;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.service.ConvertRequestResultExecutor;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.util.ConfigUtil;
import com.landray.kmss.sys.filestore.state.ConvertQueueInfo;
import com.landray.kmss.sys.filestore.state.ConvertQueueState;
import com.landray.kmss.sys.filestore.state.ConvertedStateFactory;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 失败处理
 */
public class FailedConvertResultExecutor implements ConvertRequestResultExecutor {
    private static final Logger logger = LoggerFactory.getLogger(FailedConvertResultExecutor.class);
    /**
     * 记录状态组件
     */
    private static ConvertedStateFactory convertedStateFactory = null;
    private ConvertedStateFactory getConvertedStateFactory() {
        if (convertedStateFactory == null) {
            convertedStateFactory = (ConvertedStateFactory) SpringBeanUtil
                    .getBean("convertedStateFactory");
        }

        return convertedStateFactory;
    }

    /**
     * 内存异常队列组件
     */
    private static ConvertQueue failConvertQueue = null;
    private static ConvertQueue getFailConvertQueue() {
        if(failConvertQueue == null) {
            failConvertQueue = (ConvertQueue) SpringBeanUtil.getBean("failConvertQueueImpl");
        }

        return failConvertQueue;
    }
    @Override
    public Boolean doResult(SysFileConvertQueue convertQueue, ConvertRequestResultDTO requestResultDTO)
            throws Exception {

        String desc = "分配任务的时候发送消息到转换服务不成功，请检查转换服务【点聚】:"
                + ConfigUtil.configValue(ConstantParameter.CONVERT_DIANJU_THIRD_URL);
        ConvertQueueState convertQueueState = getConvertedStateFactory()
                .getConvertedState(ConvertState.CONVERT_OTHER_FINISH_FAILURE);
        convertQueueState.updateConvertQueue(null, convertQueue,
                new ConvertQueueInfo("", desc));
        //加入队列
        getFailConvertQueue().put(convertQueue, ConstantParameter.CONVERT_DIANJU);
        if(logger.isDebugEnabled()) {
            logger.debug("点聚转换:点聚转换失败.队列ID:{}", convertQueue.getFdId());
        }
        return false;
    }
}
