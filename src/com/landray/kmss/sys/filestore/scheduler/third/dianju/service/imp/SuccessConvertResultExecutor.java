package com.landray.kmss.sys.filestore.scheduler.third.dianju.service.imp;

import com.landray.kmss.sys.filestore.constant.ConvertState;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.model.SysFileConvertConstant;
import com.landray.kmss.sys.filestore.queue.service.ConvertQueue;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.queue.util.ConvertQueueCallbackExtensionUtil;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.api.FireThirdApplicationApi;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.constant.ConstantParameter;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.constant.DianjuApi;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.dto.ConvertRequestResultDTO;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.service.ConvertRequestResultExecutor;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.util.ConfigUtil;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.util.FireThirdApplicatonUtil;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.filestore.state.ConvertQueueInfo;
import com.landray.kmss.sys.filestore.state.ConvertQueueState;
import com.landray.kmss.sys.filestore.state.ConvertedStateFactory;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * 转换文件成功处理
 */
public class SuccessConvertResultExecutor implements ConvertRequestResultExecutor {

    private static final Logger logger = LoggerFactory.getLogger(SuccessConvertResultExecutor.class);
    private FireThirdApplicationApi fireThirdApplicationApi = null;

    /**
     * 请求接口组件
     * @return
     */
    private FireThirdApplicationApi getFireThirdApplicationApi() {
        if (fireThirdApplicationApi == null) {
            fireThirdApplicationApi =
                    (FireThirdApplicationApi) SpringBeanUtil
                            .getBean("fireThirdApplicationApiImp");
        }

        return fireThirdApplicationApi;
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
     * 转换结果处理
     *
     * @param requestResultDTO
     * @return
     * @throws Exception
     */
    @Override
    public Boolean doResult(SysFileConvertQueue convertQueue, ConvertRequestResultDTO requestResultDTO)
            throws Exception {

        Boolean result =  downloadFile(convertQueue, requestResultDTO.getBytes());
        if(logger.isDebugEnabled()) {
            logger.debug("点聚转换成功与否：{}", result);
        }

        if(result) {  //成功处理
            String desc =  "转换服务转换成功【点聚服务器】:"
                    + ConfigUtil.configValue(ConstantParameter.CONVERT_DIANJU_THIRD_URL);
            ConvertQueueState convertQueueState = getConvertedStateFactory()
                    .getConvertedState(ConvertState.CONVERT_OTHER_FINISH);
            convertQueueState.updateConvertQueue(null, convertQueue,
                    new ConvertQueueInfo("", desc));

            if(logger.isDebugEnabled()) {
                logger.debug("转换成功.队列ID:{}", convertQueue.getFdId());
            }

            // 通知沉淀业务
            convertQueue.setFdConvertStatus(SysFileConvertConstant.SUCCESS);
            ConvertQueueCallbackExtensionUtil.execute(convertQueue);

        } else {  // 失败处理
            String desc = "转换成功但是下载文件时失败，请检查转换服务【点聚】:"
                    + ConfigUtil.configValue(ConstantParameter.CONVERT_DIANJU_THIRD_URL);
            ConvertQueueState convertQueueState = getConvertedStateFactory()
                    .getConvertedState(ConvertState.CONVERT_OTHER_FINISH_FAILURE);
            convertQueueState.updateConvertQueue(null, convertQueue,
                    new ConvertQueueInfo("", desc));

            //加入失败队列
            getFailConvertQueue().put(convertQueue, ConstantParameter.CONVERT_DIANJU);
            if(logger.isDebugEnabled()) {
                logger.debug("转换成功但是下载文件时失败.队列ID:{}", convertQueue.getFdId());
            }
        }


        return result;
    }

    /**
     * 下载文件到磁盘
     * @param convertQueue
     * @param bytes
     * @return
     * @throws IOException
     */
    public Boolean downloadFile(SysFileConvertQueue convertQueue, byte[] bytes) throws IOException {
        Boolean result = false;
        InputStream in = null;
        try {
            String convertKey = convertQueue.getFdConverterKey();
            String convertFileName = FireThirdApplicatonUtil.getFileDownloadName(convertKey);
            ISysAttUploadService sysAttUploadService = (ISysAttUploadService)
                    SpringBeanUtil.getBean("sysAttUploadService");
            SysAttFile sysAsttFile = sysAttUploadService.getFileById(convertQueue.getFdFileId());

            if(sysAsttFile != null) {
                String fdPath = sysAsttFile.getFdFilePath() + DianjuApi.DIANJU_DOWNLOAD_FOLDER_NAME
                        + "/" + convertFileName;

                if (logger.isDebugEnabled()) {
                    logger.debug("点聚转换,输出路径:" + fdPath);
                }
                in = new ByteArrayInputStream(bytes);
                SysFileLocationUtil.getProxyService().writeFile(in,fdPath);
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(in != null) {
                in.close();
            }
        }

        return result;
    }

}
