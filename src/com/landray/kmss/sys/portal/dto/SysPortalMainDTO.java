package com.landray.kmss.sys.portal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @description: SysPortalMainDTO
 * @author: wangjf
 * @time: 2021/6/14 3:53 下午
 * @version: 1.0
 */

public class SysPortalMainDTO implements Serializable {

    private String fdId;

    /**
     * 名称
     */
    private String fdName;

    /**
     * 是否启用
     */
    private Boolean fdEnabled;

    /**
     * 排序号
     */
    private Integer fdOrder;

    /**
     * 语言
     */
    private String fdLang;

    private String fdIcon;
    private String fdTarget;

    private String fdLogo;
    private String fdTheme;
    private String fdHeaderId;
    private String fdHeaderVars;
    private String fdFooterId;
    private String fdFooterVars;


    /**
     * 最后修改时间
     */
    private Date docAlterTime;
    /**
     * 是否极简模式
     */
    private Boolean fdIsQuick;
    private String fdHierarchyId;

    private Boolean fdAnonymous = Boolean.FALSE;
    /**
     * 保存web路径，不做业务意义
     */
    private String contextPath;

    private String fdImg;

    public String getFdName() {
        return fdName;
    }

    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    public Boolean getFdEnabled() {
        return fdEnabled;
    }

    public void setFdEnabled(Boolean fdEnabled) {
        this.fdEnabled = fdEnabled;
    }

    public Integer getFdOrder() {
        return fdOrder;
    }

    public void setFdOrder(Integer fdOrder) {
        this.fdOrder = fdOrder;
    }

    public String getFdLang() {
        return fdLang;
    }

    public void setFdLang(String fdLang) {
        this.fdLang = fdLang;
    }

    public String getFdIcon() {
        return fdIcon;
    }

    public void setFdIcon(String fdIcon) {
        this.fdIcon = fdIcon;
    }

    public String getFdTarget() {
        return fdTarget;
    }

    public void setFdTarget(String fdTarget) {
        this.fdTarget = fdTarget;
    }

    public String getFdLogo() {
        return fdLogo;
    }

    public void setFdLogo(String fdLogo) {
        this.fdLogo = fdLogo;
    }

    public String getFdTheme() {
        return fdTheme;
    }

    public void setFdTheme(String fdTheme) {
        this.fdTheme = fdTheme;
    }

    public String getFdHeaderId() {
        return fdHeaderId;
    }

    public void setFdHeaderId(String fdHeaderId) {
        this.fdHeaderId = fdHeaderId;
    }

    public String getFdHeaderVars() {
        return fdHeaderVars;
    }

    public void setFdHeaderVars(String fdHeaderVars) {
        this.fdHeaderVars = fdHeaderVars;
    }

    public String getFdFooterId() {
        return fdFooterId;
    }

    public void setFdFooterId(String fdFooterId) {
        this.fdFooterId = fdFooterId;
    }

    public String getFdFooterVars() {
        return fdFooterVars;
    }

    public void setFdFooterVars(String fdFooterVars) {
        this.fdFooterVars = fdFooterVars;
    }

    public Date getDocAlterTime() {
        return docAlterTime;
    }

    public void setDocAlterTime(Date docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    public Boolean getFdIsQuick() {
        return fdIsQuick;
    }

    public void setFdIsQuick(Boolean fdIsQuick) {
        this.fdIsQuick = fdIsQuick;
    }

    public String getFdHierarchyId() {
        return fdHierarchyId;
    }

    public void setFdHierarchyId(String fdHierarchyId) {
        this.fdHierarchyId = fdHierarchyId;
    }

    public Boolean getFdAnonymous() {
        return fdAnonymous;
    }

    public void setFdAnonymous(Boolean fdAnonymous) {
        this.fdAnonymous = fdAnonymous;
    }

    public String getFdId() {
        return fdId;
    }

    public void setFdId(String fdId) {
        this.fdId = fdId;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
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
        SysPortalMainDTO that = (SysPortalMainDTO) o;
        return Objects.equals(fdId, that.fdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fdId);
    }
}