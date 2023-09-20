package com.landray.kmss.third.welink.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.third.welink.forms.ThirdWelinkPersonNoMappForm;
import com.landray.kmss.common.model.BaseModel;

/**
  * 人员未匹配数据
  */
public class ThirdWelinkPersonNoMapp extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date docAlterTime;

    private String fdWelinkName;

    private String fdWelinkId;

    private String fdWelinkMobileNo;

    private String fdWelinkEmail;

    @Override
    public Class<ThirdWelinkPersonNoMappForm> getFormClass() {
        return ThirdWelinkPersonNoMappForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
     * welink人员名称
     */
    public String getFdWelinkName() {
        return this.fdWelinkName;
    }

    /**
     * welink人员名称
     */
    public void setFdWelinkName(String fdWelinkName) {
        this.fdWelinkName = fdWelinkName;
    }

    /**
     * welink人员ID
     */
    public String getFdWelinkId() {
        return this.fdWelinkId;
    }

    /**
     * welink人员ID
     */
    public void setFdWelinkId(String fdWelinkId) {
        this.fdWelinkId = fdWelinkId;
    }

    /**
     * welink手机号
     */
    public String getFdWelinkMobileNo() {
        return this.fdWelinkMobileNo;
    }

    /**
     * welink手机号
     */
    public void setFdWelinkMobileNo(String fdWelinkMobileNo) {
        this.fdWelinkMobileNo = fdWelinkMobileNo;
    }

    /**
     * welink邮箱
     */
    public String getFdWelinkEmail() {
        return this.fdWelinkEmail;
    }

    /**
     * welink邮箱
     */
    public void setFdWelinkEmail(String fdWelinkEmail) {
        this.fdWelinkEmail = fdWelinkEmail;
    }
}
