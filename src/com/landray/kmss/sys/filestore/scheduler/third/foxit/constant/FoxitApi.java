package com.landray.kmss.sys.filestore.scheduler.third.foxit.constant;

/**
 * API接口信息
 */
public class FoxitApi {
    /**
     * 转换
     */
    public static final String FOXIT_API_CONVERT = "/open/convertor";

    /**
     * 通过Url转换
     */
    public static final String FOXIT_API_CONVERT_BY_URL = "/api/convert/urlSubmit";
    /**
     * 下载
     */
    public static final String FOXIT_API_DOWNLOAD = "/open/file/download";

    /**
     * 回调接口
     */
    public static final String FOXIT_CALLBACK_URL = "/api/sys-attachment/foxit/convert/callback";

    private FoxitApi() {

    }
}
