package com.landray.kmss.sys.filestore.scheduler.third.dianju.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.filestore.constant.ConvertState;
import com.landray.kmss.sys.filestore.convert.cache.CallbackCache;
import com.landray.kmss.sys.filestore.convert.domain.ConvertRequestQueue;
import com.landray.kmss.sys.filestore.convert.domain.ConvertResponseQueue;
import com.landray.kmss.sys.filestore.convert.service.ConvertRequestHandle;
import com.landray.kmss.sys.filestore.convert.service.ConvertResponseHandle;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.api.FireThirdApplicationApi;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.dto.ConvertRequestResultDTO;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.dto.DianjuConvertRequest;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.dto.DianjuConvertResponse;
import com.landray.kmss.sys.filestore.state.ConvertQueueInfo;
import com.landray.kmss.sys.filestore.state.ConvertQueueState;
import com.landray.kmss.sys.filestore.state.ConvertedStateFactory;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.landray.kmss.sys.filestore.scheduler.third.dianju.constant.ConstantParameter.CONVERT_DIANJU;
import static com.landray.kmss.sys.filestore.scheduler.third.dianju.constant.RequestResponse.*;

/**
 * 请求转换接口实现
 */
public class DianjuConvertRequestHandleImpl implements ConvertRequestHandle<DianjuConvertRequest> {
    private static final Logger logger = LoggerFactory.getLogger(DianjuConvertRequestHandleImpl.class);

    /**
     * 请求点聚API组件
     * @return
     */
    private FireThirdApplicationApi fireThirdApplicationApi = null;
    private FireThirdApplicationApi getFireThirdApplicationApi() {
        if (fireThirdApplicationApi == null) {
            fireThirdApplicationApi =
                    (FireThirdApplicationApi) SpringBeanUtil.getBean("fireThirdApplicationApiImp");
        }

        return fireThirdApplicationApi;
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
     * 请求操作组件
     */
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
            convertedStateFactory = (ConvertedStateFactory) SpringBeanUtil.getBean("convertedStateFactory");
        }

        return convertedStateFactory;
    }

    /**
     * 处理转换
     * @throws Exception
     */
    @Override
    public void doConvert(DianjuConvertRequest convertRequest) throws Exception {
        // 请求参数信息
        Map<String, Object> convertRequestParameter = convertRequest.getConvertRequestParameter();
        // 请求头信息
        Map<String, String> convertRequestHeader = convertRequest.getConvertRequestHeader();
        SysFileConvertQueue deliveryTaskQueue  = convertRequest.getDeliveryTaskQueue();
        String taskId = convertRequest.getTaskId();
        try {
            if(logger.isDebugEnabled()) {
                logger.debug("请求点聚转换的参数信息：{}, 头信息：{}, 任务ID：{}",
                        convertRequestParameter.toString(),
                        convertRequestHeader.toString(), taskId);
            }

            // 转换文件
            ConvertRequestResultDTO requestResultDTO = null;
            try {
                // 队列状态更新为已经分配
                ConvertQueueState convertQueueState = getConvertedStateFactory()
                        .getConvertedState(ConvertState.CONVERT_STATE_TASK_ASSIGNED);
                convertQueueState.updateConvertQueue(null, deliveryTaskQueue,
                        new ConvertQueueInfo("", "点聚转换:任务已分配"));

                requestResultDTO = getFireThirdApplicationApi()
                        .convertFile(convertRequestParameter, convertRequestHeader);
            } catch (Exception e) {
                failureResponse(deliveryTaskQueue, taskId);
                throw e;
            }

            String result =  requestResultDTO.getStatusCode();
            if(logger.isDebugEnabled()) {
                logger.debug("转换服务转换的结果是：{}" , result);
            }

            if(!DIANJU_SUCCESS.equalsIgnoreCase(result)) {
                logger.warn("【点聚】转换不成功,标记为失败转换......");
                failureResponse(deliveryTaskQueue, taskId);
                return;
            }

            ConvertRequestResultDTO convertRequestResultDTO = new ConvertRequestResultDTO();
            convertRequestResultDTO.setStatusCode(result);

            DianjuConvertResponse convertResponse = (DianjuConvertResponse) new DianjuConvertResponse()
                    .setRequestResultDTO(convertRequestResultDTO)
                    .setDeliveryTaskQueue(deliveryTaskQueue)
                    .setResult(result)
                    .setTaskId(taskId)
                    .setExpireTime(System.currentTimeMillis())
                    .setConvertResponseHandle(getConvertResponseHandle());

            if(logger.isDebugEnabled()) {
                logger.debug("加入等待回调请求信息：{}",  convertResponse.toString());
            }

            // 加入队列统一处理, 等待回调
           // ConvertDriftResponseQueue.getInstance().put(taskId, convertResponse);
            CallbackCache.getInstance().put(taskId, deliveryTaskQueue);

        } catch (Exception e) {
            // 加入队列统一处理
            failureResponse(deliveryTaskQueue, taskId);

            logger.error("转换出错：{}", e);
            throw e;
        }
    }

    /**
     * 查询转换信息
     * @param convertRequest
     * @throws Exception
     */
    @Override
    public void getConvertInfo(DianjuConvertRequest convertRequest) throws Exception {
        // 请求参数
        Map<String, Object> searchRequestParameter = convertRequest.getConvertRequestParameter();
        // 请求头
        Map<String, String> searchRequestHeader =  convertRequest.getConvertRequestHeader();

        if(logger.isDebugEnabled()) {
            logger.debug("查询转换信息参数:{}, 头信息：{}", searchRequestParameter.toString(),
                    searchRequestHeader.toString());
        }

        ConvertRequestResultDTO requestResultDTO = null;
        SysFileConvertQueue deliveryTaskQueue  = convertRequest.getDeliveryTaskQueue();
        try {
            requestResultDTO = getFireThirdApplicationApi()
                    .searchConvertInfo(searchRequestParameter, searchRequestHeader);
        } catch (Exception e) {
            failureResponse(deliveryTaskQueue, requestResultDTO.getTaskId());
            throw e;
        }


        if(logger.isDebugEnabled()) {
            logger.debug("查询信息结果：{}", requestResultDTO.toString());
        }

        // 请求下载地址
        String downloadUrl = requestResultDTO.getDownloadUrl();


        if(StringUtil.isNull(downloadUrl)) {
            // 加入失败响应
            failureResponse(deliveryTaskQueue, requestResultDTO.getTaskId());
            return;
        }


        DianjuConvertRequest cvtRequest = (DianjuConvertRequest) new DianjuConvertRequest(CONVERT_DIANJU)
                .setConvertRequestParameter(searchRequestParameter)
                .setConvertRequestHeader(searchRequestHeader)
                .setDeliveryTaskQueue(deliveryTaskQueue)
                .setDownloadUrl(downloadUrl)
                .setTaskId(requestResultDTO.getTaskId())
                .setRequestType(DIANJU_REQUEST_TYPE_DOWNLOAD)
                .setExpireTime(System.currentTimeMillis())
                .setConvertRequestHandle(getRequestHandel());


        if(logger.isDebugEnabled()) {
            logger.debug("下载文件请求信息：{}", JSONObject.toJSONString(cvtRequest));
        }

        ConvertRequestQueue.getInstance().offer(cvtRequest);
    }

    /**
     * 下载文件
     * @throws Exception
     */
    @Override
    public void doConvertDownload(DianjuConvertRequest convertRequest) throws Exception {
        ConvertRequestResultDTO convertRequestResultDTO = null;
        try {
            convertRequestResultDTO = getFireThirdApplicationApi()
                    .downloadConvertedFile(convertRequest);
        } catch (Exception e) {
            failureResponse(convertRequest.getDeliveryTaskQueue(), convertRequest.getTaskId());
            throw e;
        }

        DianjuConvertResponse dianjuResponse = (DianjuConvertResponse) new DianjuConvertResponse()
                .setRequestResultDTO(convertRequestResultDTO)
                .setConvertResponseHandle(getConvertResponseHandle())
                .setDeliveryTaskQueue(convertRequest.getDeliveryTaskQueue())
                .setTaskId(convertRequest.getTaskId())
                .setExpireTime(System.currentTimeMillis());

        ConvertResponseQueue.getInstance().offer(dianjuResponse);
    }
    /**
     * 在熔断的情况下，将已经分配的队列改变为失败
     * @param convertRequest
     * @throws Exception
     */
    @Override
    public void convertFailure(DianjuConvertRequest convertRequest) throws Exception {
        failureResponse(convertRequest.getDeliveryTaskQueue(), convertRequest.getTaskId());
    }


    /**
     * 放入失败队列
     * @param deliveryTaskQueue
     * @param taskId
     */
    public void failureResponse(SysFileConvertQueue deliveryTaskQueue, String taskId) {
        // 加入队列统一处理
        DianjuConvertResponse convertResponse = (DianjuConvertResponse) new DianjuConvertResponse()
                .setRequestResultDTO(null)
                .setDeliveryTaskQueue(deliveryTaskQueue)
                .setResult(DIANJU_FAILURE)
                .setTaskId(taskId)
                .setExpireTime(System.currentTimeMillis())
                .setConvertResponseHandle(getConvertResponseHandle());

        ConvertResponseQueue.getInstance().offer(convertResponse);

    }


}
