package com.landray.kmss.sys.portal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @description: SysPortalTopicDTO
 * @author: wangjf
 * @time: 2021/6/19 10:20 上午
 * @version: 1.0
 */

public class SysPortalTopicDTO implements Serializable {
    private String fdId;
    /**
     * 名称
     */
    private String fdName;
    /**
     * 门户Id
     */
    private String fdPortalId;
    /**
     * 头条链接
     */
    private String fdTopUrl;
    /**
     * 更新时间
     */
    private Date docAlterTime;
    /**
     * 创建时间
     */
    private Date docCreateTime;


    private Boolean fdAnonymous = Boolean.FALSE;

    /**
     * 素材库图片url
     */
    private String fdImg;

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

    public String getFdPortalId() {
        return fdPortalId;
    }

    public void setFdPortalId(String fdPortalId) {
        this.fdPortalId = fdPortalId;
    }

    public String getFdTopUrl() {
        return fdTopUrl;
    }

    public void setFdTopUrl(String fdTopUrl) {
        this.fdTopUrl = fdTopUrl;
    }

    public Date getDocAlterTime() {
        return docAlterTime;
    }

    public void setDocAlterTime(Date docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    public Date getDocCreateTime() {
        return docCreateTime;
    }

    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    public Boolean getFdAnonymous() {
        return fdAnonymous;
    }

    public void setFdAnonymous(Boolean fdAnonymous) {
        this.fdAnonymous = fdAnonymous;
    }

    public String getFdImg() {
        return fdImg;
    }

    public void setFdImg(String fdImg) {
        this.fdImg = fdImg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysPortalTopicDTO that = (SysPortalTopicDTO) o;
        return Objects.equals(fdId, that.fdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fdId);
    }
}