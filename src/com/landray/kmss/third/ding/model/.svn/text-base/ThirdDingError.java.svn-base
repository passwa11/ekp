package com.landray.kmss.third.ding.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.third.ding.forms.ThirdDingErrorForm;
import com.landray.kmss.common.model.BaseModel;

/**
  * 钉钉异常处理
  */
public class ThirdDingError extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdContent;

    private String fdModelId;

    private String fdModelName;

    private Date fdCreateTime;

    private String fdErrorMsg;

    private String fdServiceName;

    private String fdServiceMethod;

    private String fdMethodParam;
    
    private Integer fdCount = new Integer(0);

    private String fdHandleError;
    
	@Override
    public Class<ThirdDingErrorForm> getFormClass() {
        return ThirdDingErrorForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdCreateTime", new ModelConvertor_Common("fdCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    public String getFdHandleError() {
		return fdHandleError;
	}

	public void setFdHandleError(String fdHandleError) {
		this.fdHandleError = fdHandleError;
	}

	public Integer getFdCount() {
		if(fdCount==null) {
            fdCount = new Integer(0);
        }
		return fdCount;
	}

	public void setFdCount(Integer fdCount) {
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
    public Date getFdCreateTime() {
        return this.fdCreateTime;
    }

    /**
     * 创建时间
     */
    public void setFdCreateTime(Date fdCreateTime) {
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
