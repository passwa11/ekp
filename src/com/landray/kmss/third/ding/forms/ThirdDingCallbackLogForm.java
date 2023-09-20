package com.landray.kmss.third.ding.forms;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.ding.model.ThirdDingCallbackLog;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 钉钉事件回调日志
  */
public class ThirdDingCallbackLogForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdEventType;
    
    private String fdEventTypeTip;

	private String docContent;

    private String fdIsSuccess;

    private String docCreateTime;
    
    private Long fdEventTime;
    
	private String fdErrorInfo;


	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdEventType = null;
        docContent = null;
        fdIsSuccess = null;
        docCreateTime = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingCallbackLog> getModelClass() {
        return ThirdDingCallbackLog.class;
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
    /**事件类型说明
     * 
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
        return this.docContent;
    }

    /**
     * 回调内容
     */
    public void setDocContent(String docContent) {
        this.docContent = docContent;
    }

    /**
     * 回调是否成功
     */
    public String getFdIsSuccess() {
        return this.fdIsSuccess;
    }

    /**
     * 回调是否成功
     */
    public void setFdIsSuccess(String fdIsSuccess) {
        this.fdIsSuccess = fdIsSuccess;
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
