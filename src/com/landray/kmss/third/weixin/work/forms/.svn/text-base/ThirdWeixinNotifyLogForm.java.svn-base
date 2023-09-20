package com.landray.kmss.third.weixin.work.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyLog;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;

/**
  * 待办推送日志
  */
public class ThirdWeixinNotifyLogForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdNotifyId;

    private String fdSubject;

    private String fdReqData;

    private String fdResData;

    private String fdReqDate;

    private String fdResDate;

    private String fdResult;

    private String fdUrl;

    private String fdErrMsg;

    private String fdMethod;

    private String fdApiType;

    private String fdExpireTime;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdNotifyId = null;
        fdSubject = null;
        fdReqData = null;
        fdResData = null;
        fdReqDate = null;
        fdResDate = null;
        fdResult = null;
        fdUrl = null;
        fdErrMsg = null;
        fdMethod = null;
        fdApiType = null;
        fdExpireTime = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWeixinNotifyLog> getModelClass() {
        return ThirdWeixinNotifyLog.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdReqDate", new FormConvertor_Common("fdReqDate").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("fdResDate", new FormConvertor_Common("fdResDate").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toModelPropertyMap;
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
     * 请求报文
     */
    public String getFdReqData() {
        return this.fdReqData;
    }

    /**
     * 请求报文
     */
    public void setFdReqData(String fdReqData) {
        this.fdReqData = fdReqData;
    }

    /**
     * 响应报文
     */
    public String getFdResData() {
        return this.fdResData;
    }

    /**
     * 响应报文
     */
    public void setFdResData(String fdResData) {
        this.fdResData = fdResData;
    }

    /**
     * 请求时间
     */
    public String getFdReqDate() {
        return this.fdReqDate;
    }

    /**
     * 请求时间
     */
    public void setFdReqDate(String fdReqDate) {
        this.fdReqDate = fdReqDate;
    }

    /**
     * 响应时间
     */
    public String getFdResDate() {
        return this.fdResDate;
    }

    /**
     * 响应时间
     */
    public void setFdResDate(String fdResDate) {
        this.fdResDate = fdResDate;
    }

    /**
     * 结果
     */
    public String getFdResult() {
        return this.fdResult;
    }

    /**
     * 结果
     */
    public void setFdResult(String fdResult) {
        this.fdResult = fdResult;
    }

    /**
     * 请求地址
     */
    public String getFdUrl() {
        return this.fdUrl;
    }

    /**
     * 请求地址
     */
    public void setFdUrl(String fdUrl) {
        this.fdUrl = fdUrl;
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
     * 请求方法
     */
    public String getFdMethod() {
        return this.fdMethod;
    }

    /**
     * 请求方法
     */
    public void setFdMethod(String fdMethod) {
        this.fdMethod = fdMethod;
    }

    /**
     * 接口类型
     */
    public String getFdApiType() {
        return this.fdApiType;
    }

    /**
     * 接口类型
     */
    public void setFdApiType(String fdApiType) {
        this.fdApiType = fdApiType;
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

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

    public String getFdCorpId() {
        return fdCorpId;
    }

    public void setFdCorpId(String fdCorpId) {
        this.fdCorpId = fdCorpId;
    }

    private String fdCorpId;
}
