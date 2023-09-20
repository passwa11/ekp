package com.landray.kmss.sys.attachment.restservice.foxit.controller.dao;


import java.util.List;

/**
 * minio下载回调参数

 */
public class CommonCallbackParam extends CallbackBase {

    // 执行任务id
    private String taskId;

    // 文件信息
    private List<FileInfoMinioBase> files;

    // 任务下载路径: 针对整个任务打包下载地址
    private String downloadPath;

    private int code;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<FileInfoMinioBase> getFiles() {
        return files;
    }

    public void setFiles(List<FileInfoMinioBase> files) {
        this.files = files;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

}

