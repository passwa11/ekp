package com.landray.kmss.sys.filestore.scheduler.third.dianju.service.imp;

import com.landray.kmss.sys.filestore.convert.cache.CallbackCache;
import com.landray.kmss.sys.filestore.convert.constant.HandleStatus;
import com.landray.kmss.sys.filestore.convert.domain.ConvertResponseQueue;
import com.landray.kmss.sys.filestore.convert.service.ConvertResponseHandle;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.dto.ConvertObserverParameter;
import com.landray.kmss.sys.filestore.convert.domain.ConvertRequestQueue;
import com.landray.kmss.sys.filestore.convert.service.CallbackHandle;
import com.landray.kmss.sys.filestore.convert.service.ConvertRequestHandle;
import com.landray.kmss.sys.filestore.queue.service.ConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.builder.ConvertRequestHeaderBuilder;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.builder.ConvertRequestParameterBuilder;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.dto.DianjuConvertRequest;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.dto.DianjuConvertResponse;
import com.landray.kmss.sys.filestore.state.ConvertedStateFactory;
import com.landray.kmss.sys.filestore.util.FileStoreConvertUtil;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.landray.kmss.sys.filestore.constant.ConvertConstant.THIRD_CONVERTER_DIANJU;
import static com.landray.kmss.sys.filestore.scheduler.third.dianju.constant.ConstantParameter.*;
import static com.landray.kmss.sys.filestore.scheduler.third.dianju.constant.RequestResponse.*;

/**
 * 回调之后，处理成功缓存中的信息，下载文件  ConvertObserverParameter
 */
public class DianjuCallbackHandleImpl implements CallbackHandle<ConvertObserverParameter> {
    private static final Logger logger = LoggerFactory.getLogger(DianjuCallbackHandleImpl.class);
    private static ConvertRequestHandle requestHandel = null;
    private static ConvertRequestHandle getRequestHandel() {
        if(requestHandel == null) {
            requestHandel = (ConvertRequestHandle) SpringBeanUtil.getBean("dianjuConvertRequestHandleImpl");
        }

        return requestHandel;
    }

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
     * 响应操作组件
     */
    private ConvertResponseHandle convertResponseHandle = null;
    private ConvertResponseHandle getConvertResponseHandle() {
        if (convertResponseHandle == null) {
            convertResponseHandle =
                    (ConvertResponseHandle) SpringBeanUtil.getBean("dianjuConvertResponseHandleImpl");
        }

        return convertResponseHandle;
    }
    /**
     * 回调处理
     * @throws Exception
     */
    @Override
    public void doCallbackHandel(ConvertObserverParameter convertObserverParameter) throws Exception {
        if(!FileStoreConvertUtil.whetherExecute(THIRD_CONVERTER_DIANJU,false)) {
            if (logger.isDebugEnabled()) {
                logger.debug("点聚转换服务未开启, 不处理回调业务");
            }
            return;
        }
        String taskId = convertObserverParameter.getTaskId();
        String retCode = convertObserverParameter.getRetCode(); // 转换结果码
       // DianjuConvertResponse dianjuResponse = (DianjuConvertResponse) ConvertDriftResponseQueue.getInstance().get(taskId);
        SysFileConvertQueue deliveryTaskQueue = CallbackCache.getInstance().get(taskId);
        if(deliveryTaskQueue == null) {
            logger.warn("点聚回调回来没有找到对应的记录：taskId:{}", taskId);
            return;
        }

        // 失败处理
        if(!CONVERT_DIANJU_REST_CODE.equals(retCode)) {
            logger.error("点聚转换文件回调结果是失败。转换任务加入失败队列。结果码为：{}", retCode);
            // 加入队列统一处理
            DianjuConvertResponse convertResponse = (DianjuConvertResponse) new DianjuConvertResponse()
                    .setRequestResultDTO(null)
                    .setDeliveryTaskQueue(deliveryTaskQueue)
                    .setResult(DIANJU_FAILURE)
                    .setTaskId(taskId)
                    .setExpireTime(System.currentTimeMillis())
                    .setConvertResponseHandle(getConvertResponseHandle());

            // 加入响应队列
            ConvertResponseQueue.getInstance().offer(convertResponse);

           return;
        }

       // 成功回调后加入请求队列
        Map<String, Object> searchRequestParameter = new ConvertRequestParameterBuilder()
                .createSearchInfo(taskId).create(); // 请求参数
        Map<String, String> searchRequestHeader = new ConvertRequestHeaderBuilder()
                .configContentType().config(); // 请求头信息


        DianjuConvertRequest convertRequest = (DianjuConvertRequest) new DianjuConvertRequest(CONVERT_DIANJU)
                .setConvertRequestParameter(searchRequestParameter)
                .setConvertRequestHeader(searchRequestHeader)
                .setDeliveryTaskQueue(deliveryTaskQueue)
                .setTaskId(taskId)
                .setRequestType(HandleStatus.REQUEST_TYPE_CONVERT_INFO)
                .setExpireTime(System.currentTimeMillis())
                .setConvertRequestHandle(getRequestHandel());

        if(logger.isDebugEnabled()) {
            logger.debug("点聚回调之后需要再请求下载文件，请求的信息:{}", convertRequest.toString());
        }

        // 加入请求队列
        ConvertRequestQueue.getInstance().offer(convertRequest);
    }
}
