package com.landray.kmss.third.ekp.java.notify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.ekp.java.notify.model.ThirdEkpJavaNotifyLog;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 待办推送日志
  */
public class ThirdEkpJavaNotifyLogForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docSubject;

    private String docCreateTime;

    private String fdNotifyId;

    private String fdReqData;

    private String fdResData;

    private String fdResTime;

    private String fdExpireTime;

    private String fdResult;

    private String fdErrMsg;

    private String fdMethod;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        docSubject = null;
        docCreateTime = null;
        fdNotifyId = null;
        fdReqData = null;
        fdResData = null;
        fdResTime = null;
        fdExpireTime = null;
        fdResult = null;
        fdErrMsg = null;
        fdMethod = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdEkpJavaNotifyLog> getModelClass() {
        return ThirdEkpJavaNotifyLog.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("fdNotifyId");
            toModelPropertyMap.put("fdResTime", new FormConvertor_Common("fdResTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.addNoConvertProperty("fdResult");
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
     * 发送时间
     */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 发送时间
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
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
     * 请求数据
     */
    public String getFdReqData() {
        return this.fdReqData;
    }

    /**
     * 请求数据
     */
    public void setFdReqData(String fdReqData) {
        this.fdReqData = fdReqData;
    }

    /**
     * 响应数据
     */
    public String getFdResData() {
        return this.fdResData;
    }

    /**
     * 响应数据
     */
    public void setFdResData(String fdResData) {
        this.fdResData = fdResData;
    }

    /**
     * 响应时间
     */
    public String getFdResTime() {
        return this.fdResTime;
    }

    /**
     * 响应时间
     */
    public void setFdResTime(String fdResTime) {
        this.fdResTime = fdResTime;
    }

    /**
     * 请求耗时
     */
    public String getFdExpireTime() {
        return this.fdExpireTime;
    }

    /**
     * 请求耗时
     */
    public void setFdExpireTime(String fdExpireTime) {
        this.fdExpireTime = fdExpireTime;
    }

    /**
     * 请求结果
     */
    public String getFdResult() {
        return this.fdResult;
    }

    /**
     * 请求结果
     */
    public void setFdResult(String fdResult) {
        this.fdResult = fdResult;
    }

    /**
     * 错误信息
     */
    public String getFdErrMsg() {
        return this.fdErrMsg;
    }

    /**
     * 错误信息
     */
    public void setFdErrMsg(String fdErrMsg) {
        this.fdErrMsg = fdErrMsg;
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

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
