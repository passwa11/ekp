package com.landray.kmss.sys.filestore.scheduler.third.foxit.dto;

/**
 * 请求参数
 */
public class BaseRequestDto  {
    /**
     *  是否授权: 0否 1是
     */
    private Integer isAuth;
    /**
     * 当isauth为1时，必传
     */
    private String token;
    /**
     * 接口调用是否异步: 0否 1是
     */
    private Integer isAsync;
    /**
     * 是否入库: 0否 1是
     */
    private Integer isSql;
    /**
     * 目标文件格式
     */
    private String targetFileType;
    /**
     * 超时时间,定位为秒
     */
    private Integer taskTimeOut;
    /**
     * 重试次数
     */
    private Integer retryTime;

    /**
     * 处理文件类型 convert--转换  factory--加工 ocr--识别
     */
    private String taskType;

    public Integer getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Integer isAuth) {
        this.isAuth = isAuth;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getIsAsync() {
        return isAsync;
    }

    public void setIsAsync(Integer isAsync) {
        this.isAsync = isAsync;
    }

    public Integer getIsSql() {
        return isSql;
    }

    public void setIsSql(Integer isSql) {
        this.isSql = isSql;
    }

    public String getTargetFileType() {
        return targetFileType;
    }

    public void setTargetFileType(String targetFileType) {
        this.targetFileType = targetFileType;
    }

    public Integer getTaskTimeOut() {
        return taskTimeOut;
    }

    public void setTaskTimeOut(Integer taskTimeOut) {
        this.taskTimeOut = taskTimeOut;
    }

    public Integer getRetryTime() {
        return retryTime;
    }

    public void setRetryTime(Integer retryTime) {
        this.retryTime = retryTime;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}
