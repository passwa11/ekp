package com.landray.kmss.sys.organization.event;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import org.springframework.context.ApplicationEvent;

/**
 * 组织架构变更事件
 *
 * @author 潘永辉 2021年09月29日
 */
public class SysOrgElementChangeEvent extends ApplicationEvent {
    private static final long serialVersionUID = -4871919078648158936L;

    /**
     * 变更对象
     */
    private SysOrgElement sysOrgElement;

    /**
     * 变更前层级ID
     */
    private String beforeHierarchyId;

    /**
     * 变量后层级ID
     */
    private String afterHierarchyId;

    public SysOrgElementChangeEvent(SysOrgElement sysOrgElement,
                                    String beforeHierarchyId,
                                    String afterHierarchyId) {
        super(sysOrgElement);
        this.sysOrgElement = sysOrgElement;
        this.beforeHierarchyId = beforeHierarchyId;
        this.afterHierarchyId = afterHierarchyId;
    }

    public SysOrgElement getSysOrgElement() {
        return sysOrgElement;
    }

    public String getBeforeHierarchyId() {
        return beforeHierarchyId;
    }

    public String getAfterHierarchyId() {
        return afterHierarchyId;
    }
}
