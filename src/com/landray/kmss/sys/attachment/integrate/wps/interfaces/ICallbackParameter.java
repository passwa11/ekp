package com.landray.kmss.sys.attachment.integrate.wps.interfaces;

/**
 * 调用WPS /api接口以后，用于封装WPS回调参数的接口
 * @author 陈进科
 * 2020年4月17日
 */
public interface ICallbackParameter {
    /**
     * 操作类型：清稿
     */
    public static final String OP_REVISION_CLEAN = "1";
    /**
     * 操作类型：套红
     */
    public static final String OP_REVISION_SETRED = "2";
    /**
     * 任务状态：成功
     */
    public static final String TASK_STATUS_OK = "1";
    
    /**
     * 任务状态：失败
     */
    public static final String TASK_STATUS_FAILED = "2";
    /**
     * 任务状态：转换中
     */
    public static final String TASK_STATUS_DOING = "0";

    /**
     * 任务ID，每次调用WPS接口都会产生
     * @return
     */
    public String getFdTaskId();

    /**
     * 任务ID
     * @param fdTaskId
     */
    public void setFdTaskId(String fdTaskId);

    /**
     * 任务状态  参照{@code TASK_STATUS_*}
     * @return
     */
    public String getFdStatus();

    /**
     * 任务状态
     * @param fdStatus
     */
    public void setFdStatus(String fdStatus);

    /**
     * 任务结果，它可能是一个JSON字符串，也可能是简单字符串
     * @return
     */
    public String getFdResult();

    public void setFdResult(String fdResult);

    /**
     * 文件在WPS的下载链接
     * @return
     */
    public String getFdDownloadUrl();

    public void setFdDownloadUrl(String fdDownloadUrl);

    
    public String getFdModelId();

    public void setFdModelId(String fdModelId) ;

    public String getFdModelName() ;

    public void setFdModelName(String fdModelName) ;

    /**
     * 任务类型：参见OP_REVISION_*
     * @return
     */
    public String getFdType() ;

    /**
     * 任务类型：参见OP_REVISION_*
     */
    public void setFdType(String fdType);
    
	public String getFdAttMainId();
    
	public void setFdAttMainId(String fdAttMainId);
    
    
}
