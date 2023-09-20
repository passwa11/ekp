package com.landray.kmss.sys.portal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @description: 附件主表
 * @author: wangjf
 * @time: 2021/7/2 5:43 下午
 * @version: 1.0
 */

public class SysAttMainDTO implements Serializable {

    private String fdId;
    private String fdName;

    /*
     * 文件名
     */
    private String fdFileName;

    /*
     * 文件类型
     */
    private String fdContentType;

    /*
     * 附件类型
     */
    private String fdAttType;

    /*
     * 创建时间
     */
    private Date docCreateTime;

    /*
     * 文件保存路径
     */
    private String fdFilePath;

    /*
     * 文件大小
     */
    private Double fdSize;

    private Long fdDataId;

    private String fdAttLocation;

    /*
     * 图片需压缩大小
     */
    private Integer width;

    private Integer height;
    /*
     * 是否按高宽比例压缩
     */
    private String proportion;
    /*
     * File表ID
     */
    private String fdFileId;
    /*
     * 主表域模型
     */
    private String fdModelName;
    /*
     * 主表ID
     */
    private String fdModelId;

    /*
     * 关键字
     */
    private String fdKey;

    public String getFdFileName() {
        return fdFileName;
    }

    public void setFdFileName(String fdFileName) {
        this.fdFileName = fdFileName;
    }

    public String getFdContentType() {
        return fdContentType;
    }

    public void setFdContentType(String fdContentType) {
        this.fdContentType = fdContentType;
    }

    public String getFdAttType() {
        return fdAttType;
    }

    public void setFdAttType(String fdAttType) {
        this.fdAttType = fdAttType;
    }

    public Date getDocCreateTime() {
        return docCreateTime;
    }

    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    public String getFdFilePath() {
        return fdFilePath;
    }

    public void setFdFilePath(String fdFilePath) {
        this.fdFilePath = fdFilePath;
    }

    public Double getFdSize() {
        return fdSize;
    }

    public void setFdSize(Double fdSize) {
        this.fdSize = fdSize;
    }

    public Long getFdDataId() {
        return fdDataId;
    }

    public void setFdDataId(Long fdDataId) {
        this.fdDataId = fdDataId;
    }

    public String getFdAttLocation() {
        return fdAttLocation;
    }

    public void setFdAttLocation(String fdAttLocation) {
        this.fdAttLocation = fdAttLocation;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }

    public String getFdFileId() {
        return fdFileId;
    }

    public void setFdFileId(String fdFileId) {
        this.fdFileId = fdFileId;
    }

    public String getFdModelName() {
        return fdModelName;
    }

    public void setFdModelName(String fdModelName) {
        this.fdModelName = fdModelName;
    }

    public String getFdModelId() {
        return fdModelId;
    }

    public void setFdModelId(String fdModelId) {
        this.fdModelId = fdModelId;
    }

    public String getFdKey() {
        return fdKey;
    }

    public void setFdKey(String fdKey) {
        this.fdKey = fdKey;
    }

    public String getFdId() {
        return fdId;
    }

    public void setFdId(String fdId) {
        this.fdId = fdId;
    }

    public String getFdName() {
        return fdName;
    }

    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysAttMainDTO that = (SysAttMainDTO) o;
        return Objects.equals(fdId, that.fdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fdId);
    }
}