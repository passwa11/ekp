package com.landray.kmss.sys.attachment.restservice.foxit.controller.dao;

import java.util.List;

/**
 * 文件信息
 */
public class FileInfoMinioBase extends CallbackBase {

    // 文件id
    private String fileId;

    // 文件list： 具体到某个文件，不可能是zip
    private List<MinioPathInfo> paths;

    // 文件下载路径：任务下的单个转换，有可能是zip, 有可能是单个文件
    private String downloadPath;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public List<MinioPathInfo> getPaths() {
        return paths;
    }

    public void setPaths(List<MinioPathInfo> paths) {
        this.paths = paths;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }
}
