package com.landray.kmss.sys.attend.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

import java.util.Date;

/**
  * 考勤组变更记录表
 * @author wj
  */
public class SysAttendHisCategory extends BaseModel {

    /**
     * 考勤组名称冗余存储
     */
    private String fdName;

    private String fdCategoryId;

    /**
     * 历史版本的开始时间
     */
    private Date fdBeginTime;
    /**
     * 历史版本的结束时间
     */
    private Date fdEndTime;

    private Date docAlterTime;
    private SysOrgElement fdManager;
    private SysOrgPerson docAlteror;
    /**
     * 是否有效
     */
    private Boolean fdIsAvailable;
    /**
     * 签到类型【1、考勤组，2是签到组】冗余存储
     */
    private Integer fdType;

    /**
     * 外勤是否需审批。0不审批，1需审批 冗余存储，因为有业务直接关联
     */
    private Integer fdOsdReviewType;

    /**
     * 人员所在考勤组所属层级，非数据库字段
     */
    private Integer level;

    public Integer getFdOsdReviewType() {
        return fdOsdReviewType;
    }

    public void setFdOsdReviewType(Integer fdOsdReviewType) {
        this.fdOsdReviewType = fdOsdReviewType;
    }
    /**
     * @return 签到类型
     */
    public Integer getFdType() {
        return this.fdType;
    }

    /**
     * @param fdType 签到类型
     */
    public void setFdType(Integer fdType) {
        this.fdType = fdType;
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

    public String getFdName() {
        return fdName;
    }

    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    public String getFdCategoryId() {
        return fdCategoryId;
    }

    public void setFdCategoryId(String fdCategoryId) {
        this.fdCategoryId = fdCategoryId;
    }

    public Date getFdBeginTime() {
        return fdBeginTime;
    }

    public void setFdBeginTime(Date fdBeginTime) {
        this.fdBeginTime = fdBeginTime;
    }

    public Date getFdEndTime() {
        return fdEndTime;
    }

    public void setFdEndTime(Date fdEndTime) {
        this.fdEndTime = fdEndTime;
    }

    public Boolean getFdIsAvailable() {
        return fdIsAvailable;
    }

    public void setFdIsAvailable(Boolean fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
    }

    private static ModelToFormPropertyMap toFormPropertyMap;

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
        }
        return toFormPropertyMap;
    }

    public SysOrgElement getFdManager() {
        return fdManager;
    }

    public void setFdManager(SysOrgElement fdManager) {
        this.fdManager = fdManager;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 考勤组配置内容
     */
    private String fdCategoryContent;


    /**
     * 配置内容
     */
    public String getFdCategoryContent() {
        return (String) readLazyField("fdCategoryContent", this.fdCategoryContent);
    }

    /**
     * 配置内容
     */
    public void setFdCategoryContent(String fdCategoryContent) {
        this.fdCategoryContent = (String) writeLazyField("fdCategoryContent", this.fdCategoryContent, fdCategoryContent);
    }

    /**
     * 将信息迁移到表
     */
    private SysAttendHisCategoryContent fdCategoryContentNew;

    public SysAttendHisCategoryContent getFdCategoryContentNew() {
        return fdCategoryContentNew;
    }

    public void setFdCategoryContentNew(SysAttendHisCategoryContent fdCategoryContentNew) {
        this.fdCategoryContentNew = fdCategoryContentNew;
    }
}
