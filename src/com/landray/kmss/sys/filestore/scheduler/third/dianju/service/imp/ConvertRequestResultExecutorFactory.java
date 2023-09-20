package com.landray.kmss.sys.filestore.scheduler.third.dianju.service.imp;

import com.landray.kmss.sys.filestore.scheduler.third.dianju.constant.RequestResponse;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.dto.ConvertRequestResultDTO;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.service.ConvertRequestResultExecutor;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 结果处理调用策略工厂
 */
public class ConvertRequestResultExecutorFactory {

    public ConvertRequestResultExecutor getConvertResultExecutor(ConvertRequestResultDTO requestResultDTO) {
        //成功
        if(requestResultDTO != null && RequestResponse.DIANJU_SUCCESS.equals(requestResultDTO.getStatusCode())) {
            SuccessConvertResultExecutor successConvertResultExecutor =
                    (SuccessConvertResultExecutor) SpringBeanUtil.getBean("successConvertResultExecutor");
            return successConvertResultExecutor;
        } else { // 失败
            FailedConvertResultExecutor failedConvertResultExecutor =
                    (FailedConvertResultExecutor) SpringBeanUtil.getBean("failedConvertResultExecutor");
            return failedConvertResultExecutor;
        }
    }
}
