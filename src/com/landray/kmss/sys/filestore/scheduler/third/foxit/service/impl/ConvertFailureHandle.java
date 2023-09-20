package com.landray.kmss.sys.filestore.scheduler.third.foxit.service.impl;

import com.landray.kmss.sys.filestore.constant.ConvertState;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.queue.service.ConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.dto.ConvertResponseDto;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.service.ConvertResultHandle;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.util.FoxitUtil;
import com.landray.kmss.sys.filestore.state.ConvertQueueInfo;
import com.landray.kmss.sys.filestore.state.ConvertQueueState;
import com.landray.kmss.sys.filestore.state.ConvertedStateFactory;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.landray.kmss.sys.filestore.scheduler.third.foxit.constant.FoxitConstant.*;

/**
 * 转换失败处理：数据库的队列诈信息改为失败，同时加入转换的失败队列
 */
public class ConvertFailureHandle implements ConvertResultHandle {
    private static final Logger logger = LoggerFactory.getLogger(ConvertFailureHandle.class);

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

    /**
     * 处理转换
     * @param convertResponseDto
     * @return
     * @throws Exception
     */
    @Override
    public Boolean doConvertExecute(ConvertResponseDto convertResponseDto) throws Exception {
        SysFileConvertQueue  convertQueue = convertResponseDto.getDeliveryTaskQueue();
        String desc = "福昕转换失败，请检查转换服务:" + FoxitUtil.serverUrl(FOXIT_SERVER_URL);
        ConvertQueueState convertQueueState = getConvertedStateFactory()
                .getConvertedState(ConvertState.CONVERT_OTHER_FINISH_FAILURE);
        convertQueueState.updateConvertQueue(null, convertQueue,
                new ConvertQueueInfo("", desc));

        // 放入失败队列
        getFailConvertQueue().put(convertQueue, FOXIT_SERVER_NAME);
        if(logger.isDebugEnabled()) {
            logger.debug("福昕转换失败.队列ID:{}", convertQueue.getFdId());
        }

        if(logger.isDebugEnabled()) {
            logger.debug("福昕转换失败.已经删除临时文件");
        }

        return false;
    }
}
