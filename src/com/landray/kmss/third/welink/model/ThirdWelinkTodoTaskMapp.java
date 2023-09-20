package com.landray.kmss.third.welink.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.third.welink.forms.ThirdWelinkTodoTaskMappForm;
import com.landray.kmss.common.model.BaseModel;

/**
  * 待办任务映射表
  */
public class ThirdWelinkTodoTaskMapp extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String docSubject;

    private Date docCreateTime;

    private String fdTodoId;

    private String fdPersonId;

    private String fdTaskId;

    private String fdWelinkUserId;

    @Override
    public Class<ThirdWelinkTodoTaskMappForm> getFormClass() {
        return ThirdWelinkTodoTaskMappForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
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
     * 待办ID
     */
    public String getFdTodoId() {
        return this.fdTodoId;
    }

    /**
     * 待办ID
     */
    public void setFdTodoId(String fdTodoId) {
        this.fdTodoId = fdTodoId;
    }

    /**
     * EKP用户id
     */
    public String getFdPersonId() {
        return this.fdPersonId;
    }

    /**
     * EKP用户id
     */
    public void setFdPersonId(String fdPersonId) {
        this.fdPersonId = fdPersonId;
    }

    /**
     * 任务id
     */
    public String getFdTaskId() {
        return this.fdTaskId;
    }

    /**
     * 任务id
     */
    public void setFdTaskId(String fdTaskId) {
        this.fdTaskId = fdTaskId;
    }

    /**
     * welink用户id
     */
    public String getFdWelinkUserId() {
        return this.fdWelinkUserId;
    }

    /**
     * welink用户id
     */
    public void setFdWelinkUserId(String fdWelinkUserId) {
        this.fdWelinkUserId = fdWelinkUserId;
    }
}
