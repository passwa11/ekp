package com.landray.kmss.sys.portal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @description: SysPortalNavDTO
 * @author: wangjf
 * @time: 2021/6/16 10:37 上午
 * @version: 1.0
 */

public class SysPortalNavDTO implements Serializable {

    private String fdId;
    /**
     * 名称
     */
    private String fdName;
    /**
     * 内容
     */
    private String fdContent;
    /**
     * 创建时间
     */
    private Date docCreateTime;
    /**
     * 最后修改时间
     */
    private Date docAlterTime;
    /**
     * 页面ID
     */
    private String fdPageId;
    private Boolean fdAnonymous = Boolean.FALSE;

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

    public String getFdContent() {
        return fdContent;
    }

    public void setFdContent(String fdContent) {
        this.fdContent = fdContent;
    }

    public Date getDocCreateTime() {
        return docCreateTime;
    }

    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    public Date getDocAlterTime() {
        return docAlterTime;
    }

    public void setDocAlterTime(Date docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    public String getFdPageId() {
        return fdPageId;
    }

    public void setFdPageId(String fdPageId) {
        this.fdPageId = fdPageId;
    }

    public Boolean getFdAnonymous() {
        return fdAnonymous;
    }

    public void setFdAnonymous(Boolean fdAnonymous) {
        this.fdAnonymous = fdAnonymous;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysPortalNavDTO that = (SysPortalNavDTO) o;
        return Objects.equals(fdId, that.fdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fdId);
    }
}