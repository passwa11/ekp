package com.landray.kmss.third.welink.forms;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.welink.model.ThirdWelinkTodoTaskMapp;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 待办任务映射表
  */
public class ThirdWelinkTodoTaskMappForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docSubject;

    private String docCreateTime;

    private String fdTodoId;

    private String fdPersonId;

    private String fdTaskId;

    private String fdWelinkUserId;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docSubject = null;
        docCreateTime = null;
        fdTodoId = null;
        fdPersonId = null;
        fdTaskId = null;
        fdWelinkUserId = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWelinkTodoTaskMapp> getModelClass() {
        return ThirdWelinkTodoTaskMapp.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("fdTodoId");
            toModelPropertyMap.addNoConvertProperty("fdTaskId");
            toModelPropertyMap.addNoConvertProperty("fdWelinkUserId");
        }
        return toModelPropertyMap;
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
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(String docCreateTime) {
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
