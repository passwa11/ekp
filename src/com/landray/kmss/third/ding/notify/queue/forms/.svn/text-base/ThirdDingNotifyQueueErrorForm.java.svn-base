package com.landray.kmss.third.ding.notify.queue.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.ding.notify.queue.model.ThirdDingNotifyQueueError;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 待办同步错误队列
  */
public class ThirdDingNotifyQueueErrorForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdSubject;

    private String fdAppName;

    private String fdMethod;

    private String fdJson;

    private String fdErrorMsg;

    private String fdFlag;

    private String fdMd5;

    private String fdCreateTime;

    private String fdSendTime;

    private String fdRepeatHandle;

    private String fdTodoId;

    private String fdDingUserId;

    private String fdUserId;

    private String fdUserName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdSubject = null;
        fdAppName = null;
        fdMethod = null;
        fdJson = null;
        fdErrorMsg = null;
        fdFlag = null;
        fdMd5 = null;
        fdCreateTime = null;
        fdSendTime = null;
        fdRepeatHandle = null;
        fdTodoId = null;
        fdDingUserId = null;
        fdUserId = null;
        fdUserName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingNotifyQueueError> getModelClass() {
        return ThirdDingNotifyQueueError.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdCreateTime", new FormConvertor_Common("fdCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("fdSendTime", new FormConvertor_Common("fdSendTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("fdUserId", new FormConvertor_IDToModel("fdUser", SysOrgElement.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 待办标题
     */
    public String getFdSubject() {
        return this.fdSubject;
    }

    /**
     * 待办标题
     */
    public void setFdSubject(String fdSubject) {
        this.fdSubject = fdSubject;
    }

    /**
     * 所属应用
     */
    public String getFdAppName() {
        return this.fdAppName;
    }

    /**
     * 所属应用
     */
    public void setFdAppName(String fdAppName) {
        this.fdAppName = fdAppName;
    }

    /**
     * 推送方法
     */
    public String getFdMethod() {
        return this.fdMethod;
    }

    /**
     * 推送方法
     */
    public void setFdMethod(String fdMethod) {
        this.fdMethod = fdMethod;
    }

    /**
     * 消息内容
     */
    public String getFdJson() {
        return this.fdJson;
    }

    /**
     * 消息内容
     */
    public void setFdJson(String fdJson) {
        this.fdJson = fdJson;
    }

    /**
     * 错误信息
     */
    public String getFdErrorMsg() {
        return this.fdErrorMsg;
    }

    /**
     * 错误信息
     */
    public void setFdErrorMsg(String fdErrorMsg) {
        this.fdErrorMsg = fdErrorMsg;
    }

    /**
     * 处理标识
     */
    public String getFdFlag() {
        return this.fdFlag;
    }

    /**
     * 处理标识
     */
    public void setFdFlag(String fdFlag) {
        this.fdFlag = fdFlag;
    }

    /**
     * MD5值
     */
    public String getFdMd5() {
        return this.fdMd5;
    }

    /**
     * MD5值
     */
    public void setFdMd5(String fdMd5) {
        this.fdMd5 = fdMd5;
    }

    /**
     * 创建时间
     */
    public String getFdCreateTime() {
        return this.fdCreateTime;
    }

    /**
     * 创建时间
     */
    public void setFdCreateTime(String fdCreateTime) {
        this.fdCreateTime = fdCreateTime;
    }

    /**
     * 发送时间
     */
    public String getFdSendTime() {
        return this.fdSendTime;
    }

    /**
     * 发送时间
     */
    public void setFdSendTime(String fdSendTime) {
        this.fdSendTime = fdSendTime;
    }

    /**
     * 重复处理次数
     */
    public String getFdRepeatHandle() {
        return this.fdRepeatHandle;
    }

    /**
     * 重复处理次数
     */
    public void setFdRepeatHandle(String fdRepeatHandle) {
        this.fdRepeatHandle = fdRepeatHandle;
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
     * 钉钉用户ID
     */
    public String getFdDingUserId() {
        return this.fdDingUserId;
    }

    /**
     * 钉钉用户ID
     */
    public void setFdDingUserId(String fdDingUserId) {
        this.fdDingUserId = fdDingUserId;
    }

    /**
     * 所属用户
     */
    public String getFdUserId() {
        return this.fdUserId;
    }

    /**
     * 所属用户
     */
    public void setFdUserId(String fdUserId) {
        this.fdUserId = fdUserId;
    }

    /**
     * 所属用户
     */
    public String getFdUserName() {
        return this.fdUserName;
    }

    /**
     * 所属用户
     */
    public void setFdUserName(String fdUserName) {
        this.fdUserName = fdUserName;
    }
}
