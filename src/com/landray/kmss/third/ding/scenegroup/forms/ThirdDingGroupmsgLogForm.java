package com.landray.kmss.third.ding.scenegroup.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingGroupmsgLog;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingScenegroupMapp;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 群消息
  */
public class ThirdDingGroupmsgLogForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdTitle;

    private String fdResult;

    private String fdUrl;

    private String fdReqData;

    private String fdResData;

    private String fdReqTime;

    private String fdResTime;

    private String fdExpireTime;

    private String fdErrMsg;

    private String fdGroupId;

    private String fdGroupName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdTitle = null;
        fdResult = null;
        fdUrl = null;
        fdReqData = null;
        fdResData = null;
        fdReqTime = null;
        fdResTime = null;
        fdExpireTime = null;
        fdErrMsg = null;
        fdGroupId = null;
        fdGroupName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingGroupmsgLog> getModelClass() {
        return ThirdDingGroupmsgLog.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdReqTime", new FormConvertor_Common("fdReqTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("fdResTime", new FormConvertor_Common("fdResTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("fdGroupId", new FormConvertor_IDToModel("fdGroup", ThirdDingScenegroupMapp.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 标题
     */
    public String getFdTitle() {
        return this.fdTitle;
    }

    /**
     * 标题
     */
    public void setFdTitle(String fdTitle) {
        this.fdTitle = fdTitle;
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
     * 接口地址
     */
    public String getFdUrl() {
        return this.fdUrl;
    }

    /**
     * 接口地址
     */
    public void setFdUrl(String fdUrl) {
        this.fdUrl = fdUrl;
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
    public String getFdReqTime() {
        return this.fdReqTime;
    }

    /**
     * 请求时间
     */
    public void setFdReqTime(String fdReqTime) {
        this.fdReqTime = fdReqTime;
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
     * 所属群
     */
    public String getFdGroupId() {
        return this.fdGroupId;
    }

    /**
     * 所属群
     */
    public void setFdGroupId(String fdGroupId) {
        this.fdGroupId = fdGroupId;
    }

    /**
     * 所属群
     */
    public String getFdGroupName() {
        return this.fdGroupName;
    }

    /**
     * 所属群
     */
    public void setFdGroupName(String fdGroupName) {
        this.fdGroupName = fdGroupName;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
