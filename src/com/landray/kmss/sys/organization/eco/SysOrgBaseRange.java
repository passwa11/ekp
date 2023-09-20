package com.landray.kmss.sys.organization.eco;

import com.landray.kmss.util.StringUtil;

import java.io.Serializable;

/**
 * 组织范围（基础）
 *
 * @author Henry Pan
 * @date 2021/11/9 15:55
 * @email diypyh@163.com
 */
public class SysOrgBaseRange implements Serializable {
    /**
     * ID
     */
    private String fdId;
    /**
     * 父ID
     */
    private String fdParentId;
    /**
     * 名称
     */
    private String fdName;
    /**
     * 层级ID
     */
    private String fdHierarchyId;
    /**
     * 组织类型
     */
    private int fdOrgType;
    /**
     * 是否生态
     */
    private boolean isExternal;

    public String getFdId() {
        return fdId;
    }

    public void setFdId(String fdId) {
        this.fdId = fdId;
    }

    public String getFdParentId() {
        return fdParentId;
    }

    public void setFdParentId(String fdParentId) {
        this.fdParentId = fdParentId;
    }

    public String getFdName() {
        return fdName;
    }

    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    public String getFdHierarchyId() {
        return fdHierarchyId;
    }

    public void setFdHierarchyId(String fdHierarchyId) {
        this.fdHierarchyId = fdHierarchyId;
    }

    public int getFdOrgType() {
        return fdOrgType;
    }

    public void setFdOrgType(int fdOrgType) {
        this.fdOrgType = fdOrgType;
    }

    public boolean isExternal() {
        return isExternal;
    }

    public void setExternal(boolean external) {
        isExternal = external;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SysOrgBaseRange) {
            if (StringUtil.isNotNull(this.fdId)) {
                return this.fdId.equals(((SysOrgBaseRange) obj).getFdId());
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (StringUtil.isNotNull(this.fdId)) {
            return this.fdId.hashCode();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "SysOrgBaseRange{" +
                "fdId='" + fdId + '\'' +
                ", fdName='" + fdName + '\'' +
                ", fdOrgType=" + fdOrgType +
                ", isExternal=" + isExternal +
                '}';
    }
}
