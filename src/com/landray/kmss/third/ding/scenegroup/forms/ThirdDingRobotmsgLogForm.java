package com.landray.kmss.third.ding.scenegroup.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingRobot;
import com.landray.kmss.third.ding.scenegroup.model.ThirdDingRobotmsgLog;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 机器人消息
  */
public class ThirdDingRobotmsgLogForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdResult;

    private String fdReqData;

    private String fdResData;

    private String fdUrl;

    private String fdErrMsg;

    private String fdReqTime;

    private String fdResTime;

    private String fdTitle;

    private String fdExpireTime;

    private String fdRobotId;

    private String fdRobotName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdResult = null;
        fdReqData = null;
        fdResData = null;
        fdUrl = null;
        fdErrMsg = null;
        fdReqTime = null;
        fdResTime = null;
        fdTitle = null;
        fdExpireTime = null;
        fdRobotId = null;
        fdRobotName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingRobotmsgLog> getModelClass() {
        return ThirdDingRobotmsgLog.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdReqTime", new FormConvertor_Common("fdReqTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("fdResTime", new FormConvertor_Common("fdResTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("fdRobotId", new FormConvertor_IDToModel("fdRobot", ThirdDingRobot.class));
        }
        return toModelPropertyMap;
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
     * 所属机器人
     */
    public String getFdRobotId() {
        return this.fdRobotId;
    }

    /**
     * 所属机器人
     */
    public void setFdRobotId(String fdRobotId) {
        this.fdRobotId = fdRobotId;
    }

    /**
     * 所属机器人
     */
    public String getFdRobotName() {
        return this.fdRobotName;
    }

    /**
     * 所属机器人
     */
    public void setFdRobotName(String fdRobotName) {
        this.fdRobotName = fdRobotName;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
