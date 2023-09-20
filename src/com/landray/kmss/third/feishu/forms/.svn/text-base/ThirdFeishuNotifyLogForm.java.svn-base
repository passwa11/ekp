package com.landray.kmss.third.feishu.forms;

import com.landray.kmss.util.DateUtil;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.feishu.model.ThirdFeishuNotifyLog;

/**
  * 待办推送日志
  */
public class ThirdFeishuNotifyLogForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docSubject;

    private String docCreateTime;

    private String fdMessageId;

    private String fdNotifyId;

    private String fdReqData;

    private String fdResData;

    private String fdRtnTime;

    private String fdExpireTime;

    private String fdResult;

    private String fdUrl;

    private String fdErrMsg;

    private Integer fdType;

    private String typeText;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docSubject = null;
        docCreateTime = null;
        fdMessageId = null;
        fdNotifyId = null;
        fdReqData = null;
        fdResData = null;
        fdRtnTime = null;
        fdExpireTime = null;
        fdResult = null;
        fdUrl = null;
        fdErrMsg = null;
        fdType = null;
        typeText = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdFeishuNotifyLog> getModelClass() {
        return ThirdFeishuNotifyLog.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("fdNotifyId");
            toModelPropertyMap.put("fdRtnTime", new FormConvertor_Common("fdRtnTime").setDateTimeType(DateUtil.TYPE_DATETIME));
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
     * 消息ID
     */
    public String getFdMessageId() {
        return this.fdMessageId;
    }

    /**
     * 消息ID
     */
    public void setFdMessageId(String fdMessageId) {
        this.fdMessageId = fdMessageId;
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
    public String getFdRtnTime() {
        return this.fdRtnTime;
    }

    /**
     * 响应时间
     */
    public void setFdRtnTime(String fdRtnTime) {
        this.fdRtnTime = fdRtnTime;
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
     * 日志类型：1-待办，2-审批
     * @return
     */
    public Integer getFdType() {
        return fdType;
    }

    public void setFdType(Integer fdType) {
        this.fdType = fdType;
    }

    /**
     * 日志类型显示名称
     * @return
     */
    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }
}
