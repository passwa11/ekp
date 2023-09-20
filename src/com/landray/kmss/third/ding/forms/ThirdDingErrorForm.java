package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.ding.model.ThirdDingError;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 钉钉异常处理
  */
public class ThirdDingErrorForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdContent;

    private String fdModelId;

    private String fdModelName;

    private String fdCreateTime;

    private String fdErrorMsg;

    private String fdServiceName;

    private String fdServiceMethod;

    private String fdMethodParam;
    
    private String fdCount;
    
    private String fdHandleError;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdContent = null;
        fdModelId = null;
        fdModelName = null;
        fdCreateTime = null;
        fdErrorMsg = null;
        fdServiceName = null;
        fdServiceMethod = null;
        fdMethodParam = null;
        fdCount = "0";
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingError> getModelClass() {
        return ThirdDingError.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdCreateTime", new FormConvertor_Common("fdCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toModelPropertyMap;
    }

    public String getFdHandleError() {
		return fdHandleError;
	}

	public void setFdHandleError(String fdHandleError) {
		this.fdHandleError = fdHandleError;
	}
	
    public String getFdCount() {
		return fdCount;
	}

	public void setFdCount(String fdCount) {
		this.fdCount = fdCount;
	}
	
    /**
     * 功能名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 功能名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 消息内容
     */
    public String getFdContent() {
        return this.fdContent;
    }

    /**
     * 消息内容
     */
    public void setFdContent(String fdContent) {
        this.fdContent = fdContent;
    }

    /**
     * 业务Id
     */
    public String getFdModelId() {
        return this.fdModelId;
    }

    /**
     * 业务Id
     */
    public void setFdModelId(String fdModelId) {
        this.fdModelId = fdModelId;
    }

    /**
     * 业务模型名
     */
    public String getFdModelName() {
        return this.fdModelName;
    }

    /**
     * 业务模型名
     */
    public void setFdModelName(String fdModelName) {
        this.fdModelName = fdModelName;
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
     * 错误消息
     */
    public String getFdErrorMsg() {
        return this.fdErrorMsg;
    }

    /**
     * 错误消息
     */
    public void setFdErrorMsg(String fdErrorMsg) {
        this.fdErrorMsg = fdErrorMsg;
    }

    /**
     * 执行服务
     */
    public String getFdServiceName() {
        return this.fdServiceName;
    }

    /**
     * 执行服务
     */
    public void setFdServiceName(String fdServiceName) {
        this.fdServiceName = fdServiceName;
    }

    /**
     * 执行方法
     */
    public String getFdServiceMethod() {
        return this.fdServiceMethod;
    }

    /**
     * 执行方法
     */
    public void setFdServiceMethod(String fdServiceMethod) {
        this.fdServiceMethod = fdServiceMethod;
    }

    /**
     * 执行参数
     */
    public String getFdMethodParam() {
        return this.fdMethodParam;
    }

    /**
     * 执行参数
     */
    public void setFdMethodParam(String fdMethodParam) {
        this.fdMethodParam = fdMethodParam;
    }
}
