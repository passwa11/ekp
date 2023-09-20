package com.landray.kmss.sys.attachment.integrate.dianju;

/**
 * 点聚保证请求参数
 */
public class DianjuSaveRequestVO {
    /**
     * 保存类型
     */
    private String type;

    /**
     * 附件ID
     */
    private String attMainId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 模型的Id
     */
    private String modelId;

    /**
     * 模型名称
     */
    private String modelName;

    public DianjuSaveRequestVO() {
    }

    public DianjuSaveRequestVO(String type, String attMainId,
                               String fileName, String modelId, String modelName) {
        this.type = type;
        this.attMainId = attMainId;
        this.fileName = fileName;
        this.modelId = modelId;
        this.modelName = modelName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAttMainId() {
        return attMainId;
    }

    public void setAttMainId(String attMainId) {
        this.attMainId = attMainId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
