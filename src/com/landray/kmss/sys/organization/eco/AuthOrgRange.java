package com.landray.kmss.sys.organization.eco;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 组织查看范围 与 隐藏设置
 *
 * @author panyh
 * @date Jul 9, 2020
 */
public class AuthOrgRange implements Serializable {

    /**
     * 是否外部组织（当前登录用户）
     */
    private boolean isExternal;

    /**
     * 是否无限制（这里的无限制是指没有查看范围，但是并不代表可以查看所有组织，因为有些隐藏组织是不可见的）
     */
    private boolean isUnlimited;

    /**
     * 仅自己（只能查看自己）
     */
    private boolean isSelf;

    /**
     * 在根据节点显示我的部门
     */
    private boolean isShowMyDept;

    /**
     * 可查看范围组织（包含有使用权限和只读取）
     * <p>
     * 这里的查看范围是指后台配置，限制某些人员只能看到某些组织
     */
    private Set<SysOrgShowRange> authRanges = new HashSet<>();

    /**
     * 其它可查看的机构（如父机构隐藏，子机构可看）
     * <p>
     * 由于机构权限不继承（导致层级ID不能体现上下级机构），对于父机构隐藏来说，子机构还是可见，但是在地址本树节点应该要显示出来
     */
    private Set<SysOrgShowRange> authOtherRanges = new HashSet<>();

    /**
     * 其它可查看的机构ID
     */
    private Set<String> authOtherRootIds = new HashSet<>();

    /**
     * 我所在的组织（包含跨岗组织）
     */
    private Set<SysOrgMyDeptRange> myDepts = new HashSet<>();

    /**
     * 我所在的组织ID
     */
    private Set<String> myDeptIds = new HashSet<>();

    /**
     * 管理员范围
     */
    private Set<SysOrgShowRange> adminRanges = new HashSet<>();

    /**
     * 隐藏组织
     */
    private Set<SysOrgBaseRange> hideRanges = new HashSet<>();

    /**
     * 隐藏的子组织
     */
    private Set<String> subHideHids = new HashSet<>();

    /**
     * 根组织ID（机构/部门）
     */
    private Set<String> rootDeptIds = new HashSet<>();

    /**
     * 根组织ID（岗位/人员）
     */
    private Set<String> rootPersonIds = new HashSet<>();

    /**
     * 允许查看生态组织（对于内部用户，如果没有查看限制时，可以查看未隐藏的一级生态组织）
     */
    private Set<SysOrgShowRange> authOuterRanges = new HashSet<>();

    public boolean isExternal() {
        return isExternal;
    }

    public void setExternal(boolean external) {
        isExternal = external;
    }

    public boolean isUnlimited() {
        return isUnlimited;
    }

    public void setUnlimited(boolean unlimited) {
        isUnlimited = unlimited;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean self) {
        isSelf = self;
    }

    public boolean isShowMyDept() {
        return isShowMyDept;
    }

    public void setShowMyDept(boolean showMyDept) {
        isShowMyDept = showMyDept;
    }

    public Set<SysOrgShowRange> getAuthRanges() {
        return authRanges;
    }

    public void setAuthRanges(Set<SysOrgShowRange> authRanges) {
        this.authRanges = authRanges;
    }

    public Set<SysOrgShowRange> getAuthOtherRanges() {
        return authOtherRanges;
    }

    public void setAuthOtherRanges(Set<SysOrgShowRange> authOtherRanges) {
        this.authOtherRanges = authOtherRanges;
    }

    public Set<String> getAuthOtherRootIds() {
        return authOtherRootIds;
    }

    public void setAuthOtherRootIds(Set<String> authOtherRootIds) {
        this.authOtherRootIds = authOtherRootIds;
    }

    public Set<SysOrgMyDeptRange> getMyDepts() {
        return myDepts;
    }

    public void setMyDepts(Set<SysOrgMyDeptRange> myDepts) {
        this.myDepts = myDepts;
    }

    public Set<String> getMyDeptIds() {
        return myDeptIds;
    }

    public void setMyDeptIds(Set<String> myDeptIds) {
        this.myDeptIds = myDeptIds;
    }

    public Set<SysOrgShowRange> getAdminRanges() {
        return adminRanges;
    }

    public void setAdminRanges(Set<SysOrgShowRange> adminRanges) {
        this.adminRanges = adminRanges;
    }

    public Set<SysOrgBaseRange> getHideRanges() {
        return hideRanges;
    }

    public void setHideRanges(Set<SysOrgBaseRange> hideRanges) {
        this.hideRanges = hideRanges;
    }

    public Set<String> getSubHideHids() {
        return subHideHids;
    }

    public void setSubHideHids(Set<String> subHideHids) {
        this.subHideHids = subHideHids;
    }

    public Set<String> getRootDeptIds() {
        return rootDeptIds;
    }

    public void setRootDeptIds(Set<String> rootDeptIds) {
        this.rootDeptIds = rootDeptIds;
    }

    public Set<String> getRootPersonIds() {
        return rootPersonIds;
    }

    public void setRootPersonIds(Set<String> rootPersonIds) {
        this.rootPersonIds = rootPersonIds;
    }

    public Set<SysOrgShowRange> getAuthOuterRanges() {
        return authOuterRanges;
    }

    public void setAuthOuterRanges(Set<SysOrgShowRange> authOuterRanges) {
        this.authOuterRanges = authOuterRanges;
    }
}
