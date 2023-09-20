package com.landray.kmss.sys.portal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @description: SysPortalPageDTO
 * @author: wangjf
 * @time: 2021/6/14 3:44 下午
 * @version: 1.0
 */

public class SysPortalPageDTO implements Serializable {
    private String fdId;
    /**
     * 名称
     */
    private String fdName;
    /**
     * 类型
     */
    private String fdType;
    /**
     * 标题
     */
    private String fdTitle;
    private String fdUrl;
    private String fdTheme;
    private String fdIcon;
    private String fdUsePortal;
    /**
     * 最后修改时间
     */
    private Date docAlterTime;
    private Boolean fdAnonymous = Boolean.FALSE;

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

    public String getFdType() {
        return fdType;
    }

    public void setFdType(String fdType) {
        this.fdType = fdType;
    }

    public String getFdTitle() {
        return fdTitle;
    }

    public void setFdTitle(String fdTitle) {
        this.fdTitle = fdTitle;
    }

    public String getFdUrl() {
        return fdUrl;
    }

    public void setFdUrl(String fdUrl) {
        this.fdUrl = fdUrl;
    }

    public String getFdTheme() {
        return fdTheme;
    }

    public void setFdTheme(String fdTheme) {
        this.fdTheme = fdTheme;
    }

    public String getFdIcon() {
        return fdIcon;
    }

    public void setFdIcon(String fdIcon) {
        this.fdIcon = fdIcon;
    }

    public String getFdUsePortal() {
        return fdUsePortal;
    }

    public void setFdUsePortal(String fdUsePortal) {
        this.fdUsePortal = fdUsePortal;
    }

    public Date getDocAlterTime() {
        return docAlterTime;
    }

    public void setDocAlterTime(Date docAlterTime) {
        this.docAlterTime = docAlterTime;
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
        SysPortalPageDTO that = (SysPortalPageDTO) o;
        return Objects.equals(fdId, that.fdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fdId);
    }
}