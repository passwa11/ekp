package com.landray.kmss.third.ding.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import java.util.Date;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.third.ding.forms.ThirdDingCallbackLogForm;

/**
  * 钉钉事件回调日志
  */
public class ThirdDingCallbackLog extends BaseModel implements InterceptFieldEnabled {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdEventType;
    
    private String fdEventTypeTip;
    
	private String docContent;

    private Boolean fdIsSuccess;

    private Date docCreateTime;
    
    private Long fdEventTime;

	
	private String fdErrorInfo;

	@Override
    public Class<ThirdDingCallbackLogForm> getFormClass() {
        return ThirdDingCallbackLogForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 事件类型
     */
    public String getFdEventType() {
        return this.fdEventType;
    }

    /**
     * 事件类型
     */
    public void setFdEventType(String fdEventType) {
        this.fdEventType = fdEventType;
    }
    
    /**
     * 事件类型说明
     * @return
     */
    public String getFdEventTypeTip() {
		return fdEventTypeTip;
	}

	public void setFdEventTypeTip(String fdEventTypeTip) {
		this.fdEventTypeTip = fdEventTypeTip;
	}

    /**
     * 回调内容
     */
    public String getDocContent() {
        return (String) readLazyField("docContent", this.docContent);
    }

    /**
     * 回调内容
     */
    public void setDocContent(String docContent) {
        this.docContent = (String) writeLazyField("docContent", this.docContent, docContent);
    }

    /**
     * 回调是否成功
     */
    public Boolean getFdIsSuccess() {
        return this.fdIsSuccess;
    }

    /**
     * 回调是否成功
     */
    public void setFdIsSuccess(Boolean fdIsSuccess) {
        this.fdIsSuccess = fdIsSuccess;
    }

    /**
     * 创建时间
     */
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }
    
    public Long getFdEventTime() {
		return fdEventTime;
	}

	public void setFdEventTime(Long fdEventTime) {
		this.fdEventTime = fdEventTime;
	}

    
    public String getFdErrorInfo() {
		return fdErrorInfo;
	}

	public void setFdErrorInfo(String fdErrorInfo) {
		this.fdErrorInfo = fdErrorInfo;
	}
    
}
