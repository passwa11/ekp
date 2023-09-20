package com.landray.kmss.sys.filestore.scheduler.third.foxit.service.impl;

import com.landray.kmss.sys.filestore.scheduler.third.foxit.dto.ConvertResponseDto;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.service.ConvertResultHandle;
import com.landray.kmss.util.SpringBeanUtil;

import static com.landray.kmss.sys.filestore.scheduler.third.foxit.constant.FoxitConstant.*;

/**
 * 处理的接口工厂：成功处理接口和失败处理接口
 */
public class ConvertResultHandleFactory {
    /**
     * 成功处理BEAN
     * @return
     */
    private static volatile  ConvertResultHandle convertSuccessHandle;
    private ConvertResultHandle getConvertSuccessHandle() {
        if(convertSuccessHandle == null) {
            convertSuccessHandle = (ConvertResultHandle) SpringBeanUtil.getBean("foxitConvertSuccessHandle");
        }
        return convertSuccessHandle;
    }

    /**
     * 失败处理BEAN
     */
    private static volatile  ConvertResultHandle convertFailureHandle;
    private ConvertResultHandle getConvertFailureHandle() {
        if(convertFailureHandle == null) {
            convertFailureHandle = (ConvertResultHandle) SpringBeanUtil.getBean("foxitConvertFailureHandle");
        }

        return convertFailureHandle;
    }

    /**
     * 选择处理的BEAN
     * @param convertResponseDto
     * @return
     */
    public ConvertResultHandle getConvertExecutor(ConvertResponseDto convertResponseDto) {
        String result = convertResponseDto.getStatus();
        if(FOXIT_SUCCESS.equalsIgnoreCase(result)) { // 成功
           return getConvertSuccessHandle();
        } else if(FOXIT_FAILURE.equalsIgnoreCase(result)) { // 失败
           return getConvertFailureHandle();
        }
       return null;
    }
}
