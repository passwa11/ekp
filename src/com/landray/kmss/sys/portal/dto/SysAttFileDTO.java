package com.landray.kmss.sys.portal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @description:SysAttFileDTO
 * @author: wangjf
 * @time: 2021/6/15 5:25 下午
 * @version: 1.0
 */
public class SysAttFileDTO implements Serializable {

    private String fdId;
    /**
     * 文件MD5码
     */
    private String fdMd5;
    /**
     * 文件大小
     */
    private Long fdFileSize;
    /**
     * 文件存储地址
     */
    private String fdFilePath;
    /**
     * 状态
     */
    private Integer fdStatus;
    /**
     * 创建时间
     */
    private Date docCreateTime;
    /**
     * 存储位置
     */
    private String fdAttLocation;

    public String getFdId() {
        return fdId;
    }

    public void setFdId(String fdId) {
        this.fdId = fdId;
    }

    public String getFdMd5() {
        return fdMd5;
    }

    public void setFdMd5(String fdMd5) {
        this.fdMd5 = fdMd5;
    }

    public Long getFdFileSize() {
        return fdFileSize;
    }

    public void setFdFileSize(Long fdFileSize) {
        this.fdFileSize = fdFileSize;
    }

    public String getFdFilePath() {
        return fdFilePath;
    }

    public void setFdFilePath(String fdFilePath) {
        this.fdFilePath = fdFilePath;
    }

    public Integer getFdStatus() {
        return fdStatus;
    }

    public void setFdStatus(Integer fdStatus) {
        this.fdStatus = fdStatus;
    }

    public Date getDocCreateTime() {
        return docCreateTime;
    }

    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    public String getFdAttLocation() {
        return fdAttLocation;
    }

    public void setFdAttLocation(String fdAttLocation) {
        this.fdAttLocation = fdAttLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysAttFileDTO that = (SysAttFileDTO) o;
        return Objects.equals(fdId, that.fdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fdId);
    }
}