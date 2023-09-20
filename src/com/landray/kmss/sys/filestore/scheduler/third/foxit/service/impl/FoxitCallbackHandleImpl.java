package com.landray.kmss.sys.filestore.scheduler.third.foxit.service.impl;

import com.landray.kmss.sys.filestore.convert.cache.CallbackCache;
import com.landray.kmss.sys.filestore.convert.constant.HandleStatus;
import com.landray.kmss.sys.filestore.convert.domain.ConvertRequestQueue;
import com.landray.kmss.sys.filestore.convert.domain.ConvertResponseQueue;
import com.landray.kmss.sys.filestore.convert.service.CallbackHandle;
import com.landray.kmss.sys.filestore.convert.service.ConvertRequestHandle;
import com.landray.kmss.sys.filestore.convert.service.ConvertResponseHandle;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.dto.ConvertRequestDto;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.dto.ConvertResponseDto;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.dto.FoxitObserverParameter;
import com.landray.kmss.sys.filestore.util.FileStoreConvertUtil;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static com.landray.kmss.sys.filestore.constant.ConvertConstant.THIRD_CONVERTER_FOXIT;
import static com.landray.kmss.sys.filestore.scheduler.third.foxit.constant.FoxitConstant.FOXIT_FAILURE;

/**
 * 福昕转换回调
 */
public class FoxitCallbackHandleImpl implements CallbackHandle<FoxitObserverParameter> {
    private static final Logger logger = LoggerFactory.getLogger(FoxitCallbackHandleImpl.class);
    private static ConvertRequestHandle requestHandel = null;
    private static ConvertRequestHandle getRequestHandel() {
        if(requestHandel == null) {
            requestHandel = (ConvertRequestHandle) SpringBeanUtil.getBean("foxitConvertRequestHandleImpl");
        }

        return requestHandel;
    }


    /**
     * 响应操作组件
     */
    private ConvertResponseHandle convertResponseHandle = null;
    private ConvertResponseHandle getConvertResponseHandle() {
        if (convertResponseHandle == null) {
            convertResponseHandle =
                    (ConvertResponseHandle) SpringBeanUtil.getBean("foxitConvertResponseHandleImpl");
        }

        return convertResponseHandle;
    }

    /**
     * 回调处理
     * @throws Exception
     */
    @Override
    public void doCallbackHandel(FoxitObserverParameter foxitObserverParameter) throws Exception {
        if(!FileStoreConvertUtil.whetherExecute(THIRD_CONVERTER_FOXIT,false)) {
            if (logger.isDebugEnabled()) {
                logger.debug("福昕转换服务未开启, 不处理回调业务");
            }

            return;
        }

        String fileId = foxitObserverParameter.getFileId();
        Integer retCode = foxitObserverParameter.getRetCode(); // 转换结果码
        SysFileConvertQueue deliveryTaskQueue = CallbackCache.getInstance().get(fileId);
        CallbackCache.getInstance().remove(fileId);

        if(deliveryTaskQueue == null) {
            logger.warn("福昕回调回来没有找到对应的记录：fileId:{}", fileId);
            return;
        }

        // 失败处理
        if(retCode != 0) {
            logger.error("福昕转换文件回调结果是失败。转换任务加入失败队列。结果码为：{}", retCode);
            // 加入队列统一处理
            ConvertResponseDto convertResponseDto = (ConvertResponseDto) new ConvertResponseDto()
                    .setDeliveryTaskQueue(deliveryTaskQueue)
                    .setStatus(FOXIT_FAILURE)
                    .setConvertResponseHandle(getConvertResponseHandle());
            ConvertResponseQueue.getInstance().offer(convertResponseDto);
            return;
        }


        ConvertRequestDto convertRequest = (ConvertRequestDto) new ConvertRequestDto(THIRD_CONVERTER_FOXIT)
                .setDeliveryTaskQueue(deliveryTaskQueue)
                .setFileId(fileId)
                .setDownloadConvertUrl(foxitObserverParameter.getDownloadUrl())
                .setRequestType(HandleStatus.REQUEST_TYPE_CONVERT_INFO)
                .setExpireTime(System.currentTimeMillis())
                .setConvertRequestHandle(getRequestHandel());

        if(logger.isDebugEnabled()) {
            logger.debug("福昕回调之后需要请求下载文件，请求的信息:{}", convertRequest.toString());
        }

        // 加入请求队列
        ConvertRequestQueue.getInstance().offer(convertRequest);
    }
}
