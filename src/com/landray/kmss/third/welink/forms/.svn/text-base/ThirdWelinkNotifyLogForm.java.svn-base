package com.landray.kmss.third.welink.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.welink.model.ThirdWelinkNotifyLog;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 待办推送日志
  */
public class ThirdWelinkNotifyLogForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docSubject;

    private String docCreateTime;

    private String fdNotifyId;

    private String fdReqData;

    private String fdResData;

    private String fdResTime;

    private String fdExpireTime;

    private String fdResult;

    private String fdUrl;

    private String fdErrMsg;

    private String fdUserId;

    private String fdUserName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docSubject = null;
        docCreateTime = null;
        fdNotifyId = null;
        fdReqData = null;
        fdResData = null;
        fdResTime = null;
        fdExpireTime = null;
        fdResult = null;
        fdUrl = null;
        fdErrMsg = null;
        fdUserId = null;
        fdUserName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdWelinkNotifyLog> getModelClass() {
        return ThirdWelinkNotifyLog.class;
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

	public String getFdSendType() {
		return fdSendType;
	}

	public void setFdSendType(String fdSendType) {
		this.fdSendType = fdSendType;
	}

	public String getFdMethod() {
		return fdMethod;
	}

	public void setFdMethod(String fdMethod) {
		this.fdMethod = fdMethod;
	}

	private String fdSendType;

	private String fdMethod;
}
