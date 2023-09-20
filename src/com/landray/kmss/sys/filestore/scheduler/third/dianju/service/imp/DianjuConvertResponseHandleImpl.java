package com.landray.kmss.sys.filestore.scheduler.third.dianju.service.imp;

import com.landray.kmss.sys.filestore.convert.service.ConvertResponseHandle;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.dto.ConvertRequestResultDTO;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.dto.DianjuConvertResponse;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.service.ConvertRequestResultExecutor;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 转换之后处理结果
 */
public class DianjuConvertResponseHandleImpl implements ConvertResponseHandle<DianjuConvertResponse> {
    private static final Logger logger = LoggerFactory.getLogger(DianjuConvertResponseHandleImpl.class);
    /**
     * 转换结果调用策略组件
     * @return
     */
    private ConvertRequestResultExecutorFactory convertRequestResultExecutorFactory = null;
    private ConvertRequestResultExecutorFactory getConvertRequestResultExecutorFactory() {
        if (convertRequestResultExecutorFactory == null) {
            convertRequestResultExecutorFactory =
                    (ConvertRequestResultExecutorFactory) SpringBeanUtil.getBean("convertRequestResultExecutorFactory");
        }

        return convertRequestResultExecutorFactory;
    }

    /**
     * 处理响应结果
     * @param convertResponse
     * @throws Exception
     */
    @Override
    public void doResponse(DianjuConvertResponse convertResponse) throws Exception {
        String result =  convertResponse.getResult();
        SysFileConvertQueue deliveryTaskQueue = convertResponse.getDeliveryTaskQueue();
        if(logger.isDebugEnabled()) {
            logger.debug("处理转换服务转换的结果是：{}" ,result);
        }

        ConvertRequestResultDTO requestResultDTO = convertResponse.getRequestResultDTO();
        ConvertRequestResultExecutor convertRequestResultExecutor =
                getConvertRequestResultExecutorFactory().getConvertResultExecutor(requestResultDTO);
        // 结果处理
        convertRequestResultExecutor.doResult(deliveryTaskQueue,requestResultDTO);
    }
}
