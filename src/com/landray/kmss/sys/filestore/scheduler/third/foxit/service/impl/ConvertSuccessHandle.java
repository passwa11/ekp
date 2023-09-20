package com.landray.kmss.sys.filestore.scheduler.third.foxit.service.impl;

import com.landray.kmss.sys.filestore.constant.ConvertState;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.model.SysFileConvertConstant;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.queue.service.ConvertQueue;
import com.landray.kmss.sys.filestore.queue.util.ConvertQueueCallbackExtensionUtil;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.dto.ConvertResponseDto;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.service.ConvertResultHandle;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.util.FoxitUtil;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.filestore.state.ConvertQueueInfo;
import com.landray.kmss.sys.filestore.state.ConvertQueueState;
import com.landray.kmss.sys.filestore.state.ConvertedStateFactory;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.landray.kmss.sys.filestore.scheduler.third.foxit.constant.FoxitConstant.*;

/**
 * 转换成功
 */
public class ConvertSuccessHandle implements ConvertResultHandle {
    private static final Logger logger = LoggerFactory.getLogger(ConvertSuccessHandle.class);
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
     * 转换异常队列组件
     */
    private static ConvertQueue failConvertQueue = null;
    private static ConvertQueue getFailConvertQueue() {
        if(failConvertQueue == null) {
            failConvertQueue = (ConvertQueue) SpringBeanUtil.getBean("failConvertQueueImpl");
        }

        return failConvertQueue;
    }

    /**
     * 处理转换结果
     * @param convertResponseDto
     * @return
     * @throws Exception
     */
    @Override
    public Boolean doConvertExecute(ConvertResponseDto convertResponseDto) throws Exception {
        SysFileConvertQueue convertQueue =  convertResponseDto.getDeliveryTaskQueue();
        Boolean download  = downloadConvertedFile(convertResponseDto);
        Boolean result = false;

        if(download) {
            String desc =  "福昕转换成功【福昕服务器】:" + FoxitUtil.serverUrl(FOXIT_SERVER_URL);
            ConvertQueueState convertQueueState = getConvertedStateFactory()
                    .getConvertedState(ConvertState.CONVERT_OTHER_FINISH);
            convertQueueState.updateConvertQueue(null, convertQueue,
                    new ConvertQueueInfo("", desc));

            result = true;

            if(logger.isDebugEnabled()) {
                logger.debug("福昕转换成功.队列ID:{}", convertQueue.getFdId());
            }

            convertQueue.setFdConvertStatus(SysFileConvertConstant.SUCCESS);
            // 通知沉淀业务处理
            ConvertQueueCallbackExtensionUtil.execute(convertQueue);
        } else {  // TODO 最好是重新获取字节流，而不是转换
            String desc = "福昕转换失败，请检查转换服务:" + FoxitUtil.serverUrl(FOXIT_SERVER_URL);
            ConvertQueueState convertQueueState = getConvertedStateFactory()
                    .getConvertedState(ConvertState.CONVERT_OTHER_FINISH_FAILURE);
            convertQueueState.updateConvertQueue(null, convertQueue,
                    new ConvertQueueInfo("", desc));

            //放入失败队列
            getFailConvertQueue().put(convertQueue, FOXIT_SERVER_NAME);

            if(logger.isDebugEnabled()) {
                logger.debug("福昕转换失败,下载文件失败.队列ID:{}", convertQueue.getFdId());
            }

            result = false;
        }

        if(logger.isDebugEnabled()) {
            logger.debug("福昕转换，已经删除临时文件。成功与否状态:{}", result);
        }

        return result;
    }


    /**
     * 下载文件
     * @return
     */
    public Boolean downloadConvertedFile(ConvertResponseDto convertResponseDto) throws IOException {
        Boolean result = false;
        InputStream in = null;
        try {
            SysFileConvertQueue convertQueue = convertResponseDto.getDeliveryTaskQueue();
            String convertKey = convertQueue.getFdConverterKey();
            String convertFileName = FoxitUtil.getFileDownloadName(convertKey);
            ISysAttUploadService sysAttUploadService = (ISysAttUploadService)
                    SpringBeanUtil.getBean("sysAttUploadService");
            SysAttFile sysAsttFile = sysAttUploadService.getFileById(convertQueue.getFdFileId());

            if(sysAsttFile != null) {
                String fdPath = sysAsttFile.getFdFilePath() + "_convert" + "/" + convertFileName;

                if (logger.isDebugEnabled()) {
                    logger.debug("福昕转换,输出路径:{}", fdPath);
                }

                in = new ByteArrayInputStream(convertResponseDto.getBytes());
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
