package com.landray.kmss.sys.filestore.scheduler.third.dianju.api;

import com.landray.kmss.sys.filestore.scheduler.third.dianju.dto.ConvertRequestResultDTO;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.dto.DianjuConvertRequest;
import java.util.Map;

/**
 * 访问第三方应用API
 */
public interface FireThirdApplicationApi {

    /**
     * 请求文件转换
     *
     * @param convertRequestParameter 请求参数
     * @param convertRequestHeader    请求头
     * @return
     * @throws Exception
     */
    ConvertRequestResultDTO convertFile(Map<String, Object> convertRequestParameter, Map<String, String> convertRequestHeader) throws Exception;

    /**
     * 请求文件转换
     *
     * @param convertRequestParameter 请求参数
     * @param convertRequestHeader    请求头
     * @return
     * @throws Exception
     */
    ConvertRequestResultDTO searchConvertInfo(Map<String, Object> convertRequestParameter, Map<String, String> convertRequestHeader) throws Exception;

    /**
     * 下载文件
     *
     * @return
     */
    ConvertRequestResultDTO downloadConvertedFile(DianjuConvertRequest convertRequest) throws Exception;
}
