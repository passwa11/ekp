package com.landray.kmss.sys.portal.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * @description:
 * @author: wangjf
 * @time: 2021/6/14 4:49 下午
 * @version: 1.0
 */

public class SysPortalMainPageDTO implements Serializable {
    private String fdId;
    private String fdName;
    private Integer fdOrder;
    private String fdIcon;
    private boolean fdEnabled;
    private String fdTarget;
    private String fdMainId;
    private String fdPortalPageId;

    public String getFdName() {
        return fdName;
    }

    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    public Integer getFdOrder() {
        return fdOrder;
    }

    public void setFdOrder(Integer fdOrder) {
        this.fdOrder = fdOrder;
    }

    public String getFdIcon() {
        return fdIcon;
    }

    public void setFdIcon(String fdIcon) {
        this.fdIcon = fdIcon;
    }

    public boolean isFdEnabled() {
        return fdEnabled;
    }

    public void setFdEnabled(boolean fdEnabled) {
        this.fdEnabled = fdEnabled;
    }

    public String getFdTarget() {
        return fdTarget;
    }

    public void setFdTarget(String fdTarget) {
        this.fdTarget = fdTarget;
    }

    public String getFdId() {
        return fdId;
    }

    public void setFdId(String fdId) {
        this.fdId = fdId;
    }

    public String getFdMainId() {
        return fdMainId;
    }

    public void setFdMainId(String fdMainId) {
        this.fdMainId = fdMainId;
    }

    public String getFdPortalPageId() {
        return fdPortalPageId;
    }

    public void setFdPortalPageId(String fdPortalPageId) {
        this.fdPortalPageId = fdPortalPageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysPortalMainPageDTO that = (SysPortalMainPageDTO) o;
        return Objects.equals(fdId, that.fdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fdId);
    }
}