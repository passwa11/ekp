package com.landray.kmss.sys.portal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @description: SysAttMainDTO
 * @author: wangjf
 * @time: 2021/6/15 5:29 下午
 * @version: 1.0
 */

public class SysAttRtfDataDTO implements Serializable {
    private String fdId;
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
     * 创建人Id
     */
    private String fdCreatorId;
    private String fdFileId;

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

    public String getFdCreatorId() {
        return fdCreatorId;
    }

    public void setFdCreatorId(String fdCreatorId) {
        this.fdCreatorId = fdCreatorId;
    }

    public String getFdFileId() {
        return fdFileId;
    }

    public void setFdFileId(String fdFileId) {
        this.fdFileId = fdFileId;
    }

    public String getFdId() {
        return fdId;
    }

    public void setFdId(String fdId) {
        this.fdId = fdId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysAttRtfDataDTO that = (SysAttRtfDataDTO) o;
        return Objects.equals(fdId, that.fdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fdId);
    }
}