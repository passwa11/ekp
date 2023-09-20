package com.landray.kmss.sys.portal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @description: 快捷方式
 * @author: wangjf
 * @time: 2021/6/16 9:47 上午
 * @version: 1.0
 */

public class SysPortalLinkDTO implements Serializable {

    private String fdId;
    /**
     * 名称
     */
    private String fdName;
    /**
     * 连接类型
     */
    private String fdType;
    /**
     * 创建时间
     */
    private Date docCreateTime;
    /**
     * 最后修改时间
     */
    private Date docAlterTime;
    /**
     * 匿名字段（0普通 1匿名）
     */
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

    public String getFdType() {
        return fdType;
    }

    public void setFdType(String fdType) {
        this.fdType = fdType;
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
        SysPortalLinkDTO that = (SysPortalLinkDTO) o;
        return Objects.equals(fdId, that.fdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fdId);
    }
}