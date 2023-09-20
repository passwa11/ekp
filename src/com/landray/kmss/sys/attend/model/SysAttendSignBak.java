package com.landray.kmss.sys.attend.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attend.forms.SysAttendSignBakForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

import java.util.Date;

/**
  * 签到记录历史表
  */
public class SysAttendSignBak extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date docCreateTime;

    private String fdAddress;

    private String fdWifiName;

    private String fdType;

    private Boolean fdIsAvailable;

    private SysOrgPerson docCreator;
    /**
     * 考勤组ID
     */
    private String fdGroupId;

    /**
     * 打卡所属考勤日期
     */
    private Date fdBaseDate;
    @Override
    public Class<SysAttendSignBakForm> getFormClass() {
        return SysAttendSignBakForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    public String getFdGroupId() {
        return fdGroupId;
    }

    public void setFdGroupId(String fdGroupId) {
        this.fdGroupId = fdGroupId;
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
     * 打卡地点
     */
    public String getFdAddress() {
        return this.fdAddress;
    }

    /**
     * 打卡地点
     */
    public void setFdAddress(String fdAddress) {
        this.fdAddress = fdAddress;
    }

    /**
     * wifi名称
     */
    public String getFdWifiName() {
        return this.fdWifiName;
    }

    /**
     * wifi名称
     */
    public void setFdWifiName(String fdWifiName) {
        this.fdWifiName = fdWifiName;
    }

    /**
     * 打卡类型
     */
    public String getFdType() {
        return this.fdType;
    }

    /**
     * 打卡类型
     */
    public void setFdType(String fdType) {
        this.fdType = fdType;
    }

    /**
     * 是否有效
     */
    public Boolean getFdIsAvailable() {
        return this.fdIsAvailable;
    }

    /**
     * 是否有效
     */
    public void setFdIsAvailable(Boolean fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
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

    public Date getFdBaseDate() {
        return fdBaseDate;
    }

    public void setFdBaseDate(Date fdBaseDate) {
        this.fdBaseDate = fdBaseDate;
    }
}
