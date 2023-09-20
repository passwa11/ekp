package com.landray.kmss.sys.filestore.scheduler.third.foxit.service.impl;

import com.landray.kmss.sys.filestore.convert.service.ConvertResponseHandle;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.dto.ConvertResponseDto;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.service.ConvertResultHandle;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 请求响应处理
 */
public class ConvertResponseHandleImpl implements ConvertResponseHandle<ConvertResponseDto> {
    /**
     * 处理的工厂
     */
    private static volatile  ConvertResultHandleFactory convertResultHandleFactory = null;
    private ConvertResultHandleFactory getConvertResultHandleFactory(){
        if(convertResultHandleFactory == null) {
            convertResultHandleFactory =
                    (ConvertResultHandleFactory) SpringBeanUtil.getBean("convertResultHandleFactory");
        }

        return convertResultHandleFactory;
    }

    /**
     * 响应处理
     * @param convertResponseDto
     * @throws Exception
     */
    @Override
    public void doResponse(ConvertResponseDto convertResponseDto) throws Exception {
        ConvertResultHandle convertResultHandle = getConvertResultHandleFactory().getConvertExecutor(convertResponseDto);
        convertResultHandle.doConvertExecute(convertResponseDto);
    }
}
