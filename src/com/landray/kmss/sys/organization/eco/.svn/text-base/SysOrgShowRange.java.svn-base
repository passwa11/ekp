package com.landray.kmss.sys.organization.eco;

/**
 * 组织查看权限范围
 * <p>
 * 用于人员可见性过滤
 *
 * @author panyh
 * @date Jul 7, 2020
 */
public class SysOrgShowRange extends SysOrgBaseRange {
    /**
     * 管理员，可以维护组织类型及所有子组织
     */
    public static final int ECO_ADMIN_TYPE_1 = 1;
    /**
     * 组织管理员，不能维护组织类型，只能维护自己创建的组织/岗位/人员
     */
    public static final int ECO_ADMIN_TYPE_2 = 2;
    /**
     * 负责人，可以维护管理该组织及子组织/岗位/人员
     */
    public static final int ECO_ADMIN_TYPE_3 = 3;

    /**
     * 外部组织管理员类型（1：管理员，可以维护组织类型及所有子组织。2：组织管理员，不能维护组织类型，只能维护自己创建的组织/岗位/人员。3：负责人，可以维护管理该组织及子组织/岗位/人员，4：内部组织管理员。）
     */
    private int adminType;

    /**
     * 是否“仅自己”
     */
    private boolean isSelf;

    public int getAdminType() {
        return adminType;
    }

    public void setAdminType(int adminType) {
        this.adminType = adminType;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean self) {
        isSelf = self;
    }

}
