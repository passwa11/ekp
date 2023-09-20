package com.landray.kmss.sys.organization.model;

import com.landray.kmss.common.model.BaseModel;

import java.util.Date;

/**
 * 特权用户
 * <p>
 * 不受License人员注册（在线）数量限制，在计算注册（在线）数量时，需要排除这里的人员。
 * <p>
 * 相关逻辑：
 * 1. 新增领导A，校验注册人员数量（由于在新增时，该领导并未在此表中，无法对新增的领导A做排除操作）
 * 2. 当领导A注册成功后，加入到此表时，再次增加人员时，需要排除在此表中已存在的人员
 *
 * @author 潘永辉 2021年6月21日
 */
public class SysOrgPersonPrivilege extends BaseModel {

    @Override
    public Class getFormClass() {
        return null;
    }

    /**
     * 关联用户
     */
    private SysOrgPerson fdPerson;

    /**
     * 是否外部组织
     */
    protected Boolean fdIsExternal = new Boolean(true);

    /**
     * 创建时间
     */
    private Date docCreateTime;

    public SysOrgPerson getFdPerson() {
        return fdPerson;
    }

    public void setFdPerson(SysOrgPerson fdPerson) {
        this.fdPerson = fdPerson;
    }

    public Boolean getFdIsExternal() {
        return fdIsExternal;
    }

    public void setFdIsExternal(Boolean fdIsExternal) {
        this.fdIsExternal = fdIsExternal;
    }

    public Date getDocCreateTime() {
        return docCreateTime;
    }

    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }
}
