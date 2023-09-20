package com.landray.kmss.third.feishu.forms;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.feishu.model.ThirdFeishuNotifyQueueErr;

/**
  * 待办推送失败队列
  */
public class ThirdFeishuNotifyQueueErrForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdSubject;

    private String fdMethod;

    private String fdData;

    private String fdErrMsg;

    private String fdRepeatHandle;

    private String fdFlag;

    private String fdMd5;

    private String docCreateTime;

    private String docAlterTime;

    private String fdFeishuUserId;

    private String fdSendTarget;

    private String fdNotifyId;

    private String fdPersonId;

    private String fdPersonName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdSubject = null;
        fdMethod = null;
        fdData = null;
        fdErrMsg = null;
        fdRepeatHandle = null;
        fdFlag = null;
        fdMd5 = null;
        docCreateTime = null;
        docAlterTime = null;
        fdFeishuUserId = null;
        fdSendTarget = null;
        fdNotifyId = null;
        fdPersonId = null;
        fdPersonName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdFeishuNotifyQueueErr> getModelClass() {
        return ThirdFeishuNotifyQueueErr.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("fdFlag");
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.addNoConvertProperty("fdNotifyId");
            toModelPropertyMap.put("fdPersonId", new FormConvertor_IDToModel("fdPerson", SysOrgElement.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 标题
     */
    public String getFdSubject() {
        return this.fdSubject;
    }

    /**
     * 标题
     */
    public void setFdSubject(String fdSubject) {
        this.fdSubject = fdSubject;
    }

    /**
     * 动作
     */
    public String getFdMethod() {
        return this.fdMethod;
    }

    /**
     * 动作
     */
    public void setFdMethod(String fdMethod) {
        this.fdMethod = fdMethod;
    }

    /**
     * 消息内容
     */
    public String getFdData() {
        return this.fdData;
    }

    /**
     * 消息内容
     */
    public void setFdData(String fdData) {
        this.fdData = fdData;
    }

    /**
     * 异常信息
     */
    public String getFdErrMsg() {
        return this.fdErrMsg;
    }

    /**
     * 异常信息
     */
    public void setFdErrMsg(String fdErrMsg) {
        this.fdErrMsg = fdErrMsg;
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
     * 待办MD5
     */
    public String getFdMd5() {
        return this.fdMd5;
    }

    /**
     * 待办MD5
     */
    public void setFdMd5(String fdMd5) {
        this.fdMd5 = fdMd5;
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
     * 飞书用户ID
     */
    public String getFdFeishuUserId() {
        return this.fdFeishuUserId;
    }

    /**
     * 飞书用户ID
     */
    public void setFdFeishuUserId(String fdFeishuUserId) {
        this.fdFeishuUserId = fdFeishuUserId;
    }

    /**
     * 推送目标
     */
    public String getFdSendTarget() {
        return this.fdSendTarget;
    }

    /**
     * 推送目标
     */
    public void setFdSendTarget(String fdSendTarget) {
        this.fdSendTarget = fdSendTarget;
    }

    /**
     * 待办ID
     */
    public String getFdNotifyId() {
        return this.fdNotifyId;
    }

    /**
     * 待办ID
     */
    public void setFdNotifyId(String fdNotifyId) {
        this.fdNotifyId = fdNotifyId;
    }

    /**
     * 所属用户
     */
    public String getFdPersonId() {
        return this.fdPersonId;
    }

    /**
     * 所属用户
     */
    public void setFdPersonId(String fdPersonId) {
        this.fdPersonId = fdPersonId;
    }

    /**
     * 所属用户
     */
    public String getFdPersonName() {
        return this.fdPersonName;
    }

    /**
     * 所属用户
     */
    public void setFdPersonName(String fdPersonName) {
        this.fdPersonName = fdPersonName;
    }
}
