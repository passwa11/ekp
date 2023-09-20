package com.landray.kmss.sys.portal.dto;

import java.io.Serializable;

/**
 * @description: 文件存储
 * @author: wangjf
 * @time: 2021/6/17 9:36 上午
 * @version: 1.0
 */

public class SysFileStoreDTO implements Serializable {
    /**
     * 文件名称
     */
    private String fdId;
    /**
     * 包路径
     */
    private String packagePath;
    /**
     * 目标路径
     */
    private String targetPath;

    /**
     * 是否还需要处理标志，对于一般文件不设置值，系统拓展UI则还需要把文件拷贝至WEBROOT文件件中该值为extend
     */
    private String handleStatus;

    /**
     * 是否是压缩包,默认不是压缩包
     */
    private Boolean zipPackage = Boolean.FALSE;

    public String getFdId() {
        return fdId;
    }

    public void setFdId(String fdId) {
        this.fdId = fdId;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }

    public Boolean getZipPackage() {
        return zipPackage;
    }

    public void setZipPackage(Boolean zipPackage) {
        this.zipPackage = zipPackage;
    }


}