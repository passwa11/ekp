package com.landray.kmss.sys.filestore.scheduler.third.foxit.api;

import com.landray.kmss.sys.filestore.scheduler.third.foxit.dto.ConvertRequestDto;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.dto.ConvertResponseDto;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 请求第三方接口
 */
public interface FireFoxitApplicationApi {

    /**
     * 转换文件
     * @throws Exception
     */
    String doConvertFile(Map<String, List<File>> files, Map<String, String> filesInfos,  Map<String, String> headers)
            throws Exception;
    /**
     * 根据URL文件转换文件
     * @throws Exception
     */
    String doConvertFileByUrl(Map<String,Object> parameter, Map<String, String> header)
            throws Exception;

    /**
     * 下载已经转换好的文件
     * @return
     * @throws Exception
     */
    ConvertResponseDto downloadConvertedFile(ConvertRequestDto convertRequest)
            throws Exception;

}
