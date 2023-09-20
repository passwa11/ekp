package com.landray.kmss.sys.attend.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attend.forms.SysAttendRestatLogForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

import java.util.Date;

/**
  * 重新统计日志表
 * @author 王京
 * @date 2021-01-14
  */
public class SysAttendRestatLog extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String docSubject;

    private Date docCreateTime;

    private Date docAlterTime;

    private Date fdBeginDate;

    private Date fdEndDate;

    private String fdCategoryName;

    private String fdStatUserNames;

    private Integer fdStatus;

    private Boolean fdCreateMiss;

    private SysOrgPerson docCreator;
    @Override
    public Class<SysAttendRestatLogForm> getFormClass() {
        return SysAttendRestatLogForm.class;
    }
    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdBeginDate", new ModelConvertor_Common("fdBeginDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdEndDate", new ModelConvertor_Common("fdEndDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
        }
        return toFormPropertyMap;
    }
    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 标题
     */
    public String getDocSubject() {
        return this.docSubject;
    }

    /**
     * 标题
     */
    public void setDocSubject(String docSubject) {
        this.docSubject = docSubject;
    }

    /**
     * 创建时间
     */
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
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
     * 统计开始时间
     */
    public Date getFdBeginDate() {
        return this.fdBeginDate;
    }

    /**
     * 统计开始时间
     */
    public void setFdBeginDate(Date fdBeginDate) {
        this.fdBeginDate = fdBeginDate;
    }

    /**
     * 统计结束时间
     */
    public Date getFdEndDate() {
        return this.fdEndDate;
    }

    /**
     * 统计结束时间
     */
    public void setFdEndDate(Date fdEndDate) {
        this.fdEndDate = fdEndDate;
    }

    /**
     * 考勤组
     */
    public String getFdCategoryName() {
        return (String) readLazyField("fdCategoryName", this.fdCategoryName);
    }

    /**
     * 考勤组
     */
    public void setFdCategoryName(String fdCategoryName) {
        this.fdCategoryName = (String) writeLazyField("fdCategoryName", this.fdCategoryName, fdCategoryName);
    }

    /**
     * 考勤组人员
     */
    public String getFdStatUserNames() {
        return (String) readLazyField("fdStatUserNames", this.fdStatUserNames);
    }

    /**
     * 考勤组人员
     */
    public void setFdStatUserNames(String fdStatUserNames) {
        this.fdStatUserNames = (String) writeLazyField("fdStatUserNames", this.fdStatUserNames, fdStatUserNames);
    }

    /**
     * 状态
     */
    public Integer getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 状态
     */
    public void setFdStatus(Integer fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 重新生成缺卡记录
     */
    public Boolean getFdCreateMiss() {
        return this.fdCreateMiss;
    }

    /**
     * 重新生成缺卡记录
     */
    public void setFdCreateMiss(Boolean fdCreateMiss) {
        this.fdCreateMiss = fdCreateMiss;
    }

    /**
     * 创建人
     */
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * 创建人
     */
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

}
