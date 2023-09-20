package com.landray.kmss.sys.filestore.scheduler.third.foxit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.filestore.constant.ConvertState;
import com.landray.kmss.sys.filestore.convert.cache.CallbackCache;
import com.landray.kmss.sys.filestore.convert.domain.ConvertRequestQueue;
import com.landray.kmss.sys.filestore.convert.domain.ConvertResponseQueue;
import com.landray.kmss.sys.filestore.convert.service.ConvertRequestHandle;
import com.landray.kmss.sys.filestore.convert.service.ConvertResponseHandle;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.api.FireFoxitApplicationApi;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.dto.ConvertRequestDto;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.dto.ConvertResponseDto;
import com.landray.kmss.sys.filestore.state.ConvertQueueInfo;
import com.landray.kmss.sys.filestore.state.ConvertQueueState;
import com.landray.kmss.sys.filestore.state.ConvertedStateFactory;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.landray.kmss.sys.filestore.convert.constant.HandleStatus.REQUEST_TYPE_DOWNLOAD;
import static com.landray.kmss.sys.filestore.scheduler.third.foxit.constant.FoxitConstant.FOXIT_FAILURE;
import static com.landray.kmss.sys.filestore.scheduler.third.foxit.constant.FoxitConstant.FOXIT_SERVER_NAME;

/**
 * 转换请求处理
 */
public class ConvertRequestHandleImpl implements ConvertRequestHandle<ConvertRequestDto> {
    private static final Logger logger = LoggerFactory.getLogger(ConvertRequestHandleImpl.class);
    /**
     *  转换返回码
     */
    private static final String CONVERT_RESULT_CODE = "0";

    /**
     * 请求福昕API组件
     * @return
     */
    private FireFoxitApplicationApi fireFoxitApplicationApi = null;
    private FireFoxitApplicationApi getFireFoxitApplicationApi() {
        if (fireFoxitApplicationApi == null) {
            fireFoxitApplicationApi =
                    (FireFoxitApplicationApi) SpringBeanUtil.getBean("fireFoxitApplicationApiImp");
        }

        return fireFoxitApplicationApi;
    }

    /**
     * 请求响应操作
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
     * 转换后文件下载
     * @param convertRequestDto
     * @throws Exception
     */
    @Override
    public void doConvert(ConvertRequestDto convertRequestDto) throws Exception {

        SysFileConvertQueue deliveryTaskQueue  = convertRequestDto.getDeliveryTaskQueue();
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("url", convertRequestDto.getUrl());
        parameters.put("fileId", convertRequestDto.getFileId());
        parameters.put("srcFormat", convertRequestDto.getSrcFormat());
        parameters.put("newFormat", convertRequestDto.getNewFormat());
        parameters.put("callbackUrl", convertRequestDto.getCallbackUrl());

        if(logger.isDebugEnabled()) {
            logger.debug("请求转换参数信息：{}", JSONObject.toJSONString(parameters));
        }

        Map<String, String> header = new HashMap<String, String>(); //HTTP标题头
        header.put("Content-Type", "application/json");

        String result = "";
        try{
            // 队列状态更新为已经分配
            ConvertQueueState convertQueueState = getConvertedStateFactory()
                    .getConvertedState(ConvertState.CONVERT_STATE_TASK_ASSIGNED);
            convertQueueState.updateConvertQueue(null, deliveryTaskQueue,
                    new ConvertQueueInfo("", "福昕转换:任务已分配"));

            // 请求Foxit转换
            result = getFireFoxitApplicationApi().doConvertFileByUrl(parameters, header);
        } catch (Exception e){
            failConvertResponse(deliveryTaskQueue);
            throw e;
        }

        if(logger.isDebugEnabled()) {
            logger.debug("请求转换的结果是:{}", result);
        }

        if(StringUtil.isNull(result)) {
            failConvertResponse(deliveryTaskQueue);
            return;
        }

        JSONObject jsonObject = JSONObject.parseObject(result);
        String data = jsonObject.getString("code");
        if(StringUtil.isNull(data) || !CONVERT_RESULT_CODE.equals(data)) {
            failConvertResponse(deliveryTaskQueue);
            return;
        }

        // 等待回调
        CallbackCache.getInstance().put(deliveryTaskQueue.getFdId(), deliveryTaskQueue);
    }

    /**
     * 获取转换的信息
     * @param convertRequest
     * @throws Exception
     */
    @Override
    public void getConvertInfo(ConvertRequestDto convertRequest) throws Exception {
        SysFileConvertQueue deliveryTaskQueue  = convertRequest.getDeliveryTaskQueue();
        // 请求下载地址
        String downloadUrl = convertRequest.getDownloadConvertUrl();

        if(StringUtil.isNull(downloadUrl)) {
            // 加入失败响应
            failConvertResponse(deliveryTaskQueue);
            return;
        }

        ConvertRequestDto cvtRequest = (ConvertRequestDto) new ConvertRequestDto(FOXIT_SERVER_NAME)
                .setDeliveryTaskQueue(deliveryTaskQueue)
                .setDownloadConvertUrl(downloadUrl)
                .setFileId(convertRequest.getFileId())
                .setRequestType(REQUEST_TYPE_DOWNLOAD)
                .setExpireTime(System.currentTimeMillis())
                .setConvertRequestHandle(getConvertRequestHandle());


        if(logger.isDebugEnabled()) {
            logger.debug("下载文件请求信息：{}", JSONObject.toJSONString(cvtRequest));
        }

        ConvertRequestQueue.getInstance().offer(cvtRequest);
    }

    /**
     * 失败后，放入响应队列，状态是失败
     * @param deliveryTaskQueue
     */
    public void failConvertResponse(SysFileConvertQueue deliveryTaskQueue) {
        ConvertResponseDto convertResponseDto = (ConvertResponseDto) new ConvertResponseDto()
                .setDeliveryTaskQueue(deliveryTaskQueue)
                .setStatus(FOXIT_FAILURE)
                .setConvertResponseHandle(getConvertResponseHandle());
        ConvertResponseQueue.getInstance().offer(convertResponseDto);

    }

    /**
     * 下载文件
     * @param convertRequestDto
     * @throws Exception
     */
    @Override
    public void doConvertDownload(ConvertRequestDto convertRequestDto) throws Exception {
        SysFileConvertQueue deliveryTaskQueue = convertRequestDto.getDeliveryTaskQueue();
        ConvertResponseDto convertResponseDto  = null;

        if(logger.isDebugEnabled()) {
            logger.debug("请求福昕下载文件，发起请求信息:{}", JSONObject.toJSONString(deliveryTaskQueue));
        }

        try {
            convertResponseDto = getFireFoxitApplicationApi().downloadConvertedFile(convertRequestDto);
        } catch (Exception e) {
            failConvertResponse(deliveryTaskQueue);
            throw e;
        }

        if(convertResponseDto == null || FOXIT_FAILURE.equalsIgnoreCase(convertResponseDto.getStatus())) {
            failConvertResponse(deliveryTaskQueue);
            return;
        }

        convertResponseDto.setConvertResponseHandle(getConvertResponseHandle());
        // 放入响应队列来读取字节并持久到磁盘
        ConvertResponseQueue.getInstance().offer(convertResponseDto);

    }

    /**
     * 在熔断的情况下，将已经分配的队列改变为失败
     * @param convertRequest
     * @throws Exception
     */
    @Override
    public void convertFailure(ConvertRequestDto convertRequest) throws Exception {
        failConvertResponse(convertRequest.getDeliveryTaskQueue());
    }
}
