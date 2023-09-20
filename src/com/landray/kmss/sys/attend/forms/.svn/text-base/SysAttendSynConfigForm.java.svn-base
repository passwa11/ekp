package com.landray.kmss.sys.attend.forms;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendSynConfig;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class SysAttendSynConfigForm extends ExtendForm {

    private String fdSynType;
    
    private String fdEnableRecord;
    
    private String fdEnableCategory;
    
    private String fdStartTime;
    
    private String fdEndTime;
    
    private String fdSyncTime;

    //同步限速器，单位时间调用频率（时间单位为秒）
    private String fdSyncRateLimiter;
    
    /**
     * 同步类型
     */
    public String getFdSynType() {
        return fdSynType;
    }


    /**
     * 同步类型
     */
    public void setFdSynType(String fdSynType) {
        this.fdSynType = fdSynType;
    }

    /**
     * 是否开启考勤记录同步
     * @return
     */
    public String getFdEnableRecord() {
        return fdEnableRecord;
    }

    /**
     * 是否开启考勤记录同步
     */
    public void setFdEnableRecord(String fdEnableRecord) {
        this.fdEnableRecord = fdEnableRecord;
    }

    /**
     * 是否开启考勤规则同步
     * @return
     */
    public String getFdEnableCategory() {
        return fdEnableCategory;
    }

    /**
     * 是否开启考勤规则同步
     */
    public void setFdEnableCategory(String fdEnableCategory) {
        this.fdEnableCategory = fdEnableCategory;
    }

    /**
     * 同步开始时间
     * @return
     */
    public String getFdStartTime() {
        return fdStartTime;
    }

    /**
     * 同步开始时间
     */
    public void setFdStartTime(String fdStartTime) {
        this.fdStartTime = fdStartTime;
    }

    /**
     * 同步结束时间
     * @return
     */
    public String getFdEndTime() {
        return fdEndTime;
    }

    /**
     * 同步结束时间
     */
    public void setFdEndTime(String fdEndTime) {
        this.fdEndTime = fdEndTime;
    }

    /**
     * 最后同步时间
     * @return
     */
	public String getFdSyncTime() {
        return fdSyncTime;
    }

	/**
     * 最后同步时间
     */
    public void setFdSyncTime(String fdSyncTime) {
        this.fdSyncTime = fdSyncTime;
    }

    public String getFdSyncRateLimiter() {
        return fdSyncRateLimiter;
    }

    public void setFdSyncRateLimiter(String fdSyncRateLimiter) {
        this.fdSyncRateLimiter = fdSyncRateLimiter;
    }

    @Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
	    fdSynType = null;
	    fdEnableRecord = null;
	    fdEnableCategory = null;
	    fdStartTime = null;
	    fdEndTime = null;
	    fdSyncTime = null;
	    fdSyncRateLimiter = null;
		super.reset(mapping, request);
	}

	@Override
	public Class getModelClass() {
		return SysAttendSynConfig.class;
	}

}
