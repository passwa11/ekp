package com.landray.kmss.third.welink.forms;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.welink.model.ThirdWelinkPersonNoMapp;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 人员未匹配数据
  */
public class ThirdWelinkPersonNoMappForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docAlterTime;

    private String fdWelinkName;

    private String fdWelinkId;

    private String fdWelinkMobileNo;

    private String fdWelinkEmail;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docAlterTime = null;
        fdWelinkName = null;
        fdWelinkId = null;
        fdWelinkMobileNo = null;
        fdWelinkEmail = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWelinkPersonNoMapp> getModelClass() {
        return ThirdWelinkPersonNoMapp.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.addNoConvertProperty("fdWelinkName");
            toModelPropertyMap.addNoConvertProperty("fdWelinkId");
            toModelPropertyMap.addNoConvertProperty("fdWelinkMobileNo");
        }
        return toModelPropertyMap;
    }

    /**
     * 更新时间
     */
    public String getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(String docAlterTime) {
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
