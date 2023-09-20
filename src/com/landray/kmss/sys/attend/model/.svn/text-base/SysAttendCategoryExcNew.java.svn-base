package com.landray.kmss.sys.attend.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import java.util.Date;

/**
  * 考勤组的排除对象
 * @author wjØ
  */
public class SysAttendCategoryExcNew extends BaseModel{

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date fdBeginTime;

    private Date fdEndTime;

    private Date docAlterTime;

    private SysAttendHisCategory hisCategoryId;

    private SysOrgElement fdOrg;

    private SysOrgPerson docAlteror;

    /**
     * 生效开始时间
     */
    public Date getFdBeginTime() {
        return this.fdBeginTime;
    }

    /**
     * 生效开始时间
     */
    public void setFdBeginTime(Date fdBeginTime) {
        this.fdBeginTime = fdBeginTime;
    }

    /**
     * 生效结束时间
     */
    public Date getFdEndTime() {
        return this.fdEndTime;
    }

    /**
     * 生效结束时间
     */
    public void setFdEndTime(Date fdEndTime) {
        this.fdEndTime = fdEndTime;
    }

    /**
     * 更新时间
     */
    public Date getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(Date docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    /**
     * 考勤组信息
     */
    public SysAttendHisCategory getHisCategoryId() {
        return this.hisCategoryId;
    }

    /**
     * 考勤组信息
     */
    public void setHisCategoryId(SysAttendHisCategory hisCategoryId) {
        this.hisCategoryId = hisCategoryId;
    }

    /**
     * 考勤对象ID
     */
    public SysOrgElement getFdOrg() {
        return this.fdOrg;
    }

    /**
     * 考勤对象ID
     */
    public void setFdOrg(SysOrgElement fdOrg) {
        this.fdOrg = fdOrg;
    }

    /**
     * 修改人
     */
    public SysOrgPerson getDocAlteror() {
        return this.docAlteror;
    }

    /**
     * 修改人
     */
    public void setDocAlteror(SysOrgPerson docAlteror) {
        this.docAlteror = docAlteror;
    }

    @Override
    public Class getFormClass() {
        return null;
    }
}
