package com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.api;

import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.domain.WPSCloudConvertResult;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.domain.WPSCloudUpLoadResult;

/**
 * 请请求WPS云接口代理
 */
public interface WPSCloudApi {
    /**
     * 上传文件到WPS
     * @param convertQueue 队列
     * @param wpsUrl WPS地址
     * @param url EKP地址
     * @return
     * @throws Exception
     */
    WPSCloudUpLoadResult uploadFileToWPSCloud(SysFileConvertQueue convertQueue,
                                              String wpsUrl, String url);

    /**
     * WPS转换文件
     * @param convertQueue 队列
     * @param convertId 转换ID
     * @param wpsUrl WPS请求地址
     * @return
     * @throws Exception
     */
    WPSCloudConvertResult convertFileFromWPSCloud(SysFileConvertQueue convertQueue,
                                                  String convertId, String wpsUrl);

    /**
     * 从WPS下载已经转换的文件
     * @param convertQueue 队列
     * @param downloadId 下载地址
     * @param serverUrl WPS请求地址
     * @return
     * @throws Exception
     */
    Boolean downloadConvertedFileFromWPSCloud(SysFileConvertQueue convertQueue, String downloadId,
                                              String serverUrl);
}
