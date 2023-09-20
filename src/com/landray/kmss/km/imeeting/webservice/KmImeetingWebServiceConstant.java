package com.landray.kmss.km.imeeting.webservice;

public interface KmImeetingWebServiceConstant {
    /**
     * 返回状态 0 失败 1 成功
     */
    public static final int RETURN_CONSTANT_STATUS_FAIL = 0;

    public static final int RETURN_CONSTANT_STATUS_SUCESS = 1;

    /**
     * 时间格式要求
     */
    public static final String DATE_TIME_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";

    /**
     * 调用函数名：会议室预定
     */

    public static final String METHOD_CONSTANT_NAME_BOOKLIST = "getBookList";
    public static final String METHOD_CONSTANT_NAME_ADDBOOK = "addImeetingBook";
    public static final String METHOD_CONSTANT_NAME_CANCELBOOK = "cancelImeetingBook";

    /**
     * 调用函数名：会议室资源
     */
    public static final String METHOD_CONSTANT_NAME_ADDRES="addImeetingRes";
    public static final String METHOD_CONSTANT_NAME_UPDATERES="updateKmImeetingRes";
    public static final String METHOD_CONSTANT_NAME_DELETERES="deleteKmImeetingRes";

}
