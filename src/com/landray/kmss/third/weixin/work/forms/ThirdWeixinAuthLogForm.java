package com.landray.kmss.third.weixin.work.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinAuthLog;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 登录认证日志
  */
public class ThirdWeixinAuthLogForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docCreateTime;

    private String fdUserId;

    private String fdExpireTime;

    private String fdReqDataOri;

    private String fdReqDataDecyed;

    private String fdUrl;

    private String fdResult;

    private String fdErrMsg;

    private String fdResDataOri;

    private String fdResDataEncryed;

    private String fdLogType;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        docCreateTime = null;
        fdUserId = null;
        fdExpireTime = null;
        fdReqDataOri = null;
        fdReqDataDecyed = null;
        fdUrl = null;
        fdResult = null;
        fdErrMsg = null;
        fdResDataOri = null;
        fdResDataEncryed = null;
        fdLogType = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWeixinAuthLog> getModelClass() {
        return ThirdWeixinAuthLog.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
        }
        return toModelPropertyMap;
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
     * 请求报文密文
     */
    public String getFdReqDataOri() {
        return this.fdReqDataOri;
    }

    /**
     * 请求报文密文
     */
    public void setFdReqDataOri(String fdReqDataOri) {
        this.fdReqDataOri = fdReqDataOri;
    }

    /**
     * 请求报文明文
     */
    public String getFdReqDataDecyed() {
        return this.fdReqDataDecyed;
    }

    /**
     * 请求报文明文
     */
    public void setFdReqDataDecyed(String fdReqDataDecyed) {
        this.fdReqDataDecyed = fdReqDataDecyed;
    }

    /**
     * URL地址
     */
    public String getFdUrl() {
        return this.fdUrl;
    }

    /**
     * URL地址
     */
    public void setFdUrl(String fdUrl) {
        this.fdUrl = fdUrl;
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
     * 响应报文明文
     */
    public String getFdResDataOri() {
        return this.fdResDataOri;
    }

    /**
     * 响应报文明文
     */
    public void setFdResDataOri(String fdResDataOri) {
        this.fdResDataOri = fdResDataOri;
    }

    /**
     * 响应报文密文
     */
    public String getFdResDataEncryed() {
        return this.fdResDataEncryed;
    }

    /**
     * 响应报文密文
     */
    public void setFdResDataEncryed(String fdResDataEncryed) {
        this.fdResDataEncryed = fdResDataEncryed;
    }

    /**
     * 日志类型
     */
    public String getFdLogType() {
        return this.fdLogType;
    }

    /**
     * 日志类型
     */
    public void setFdLogType(String fdLogType) {
        this.fdLogType = fdLogType;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
