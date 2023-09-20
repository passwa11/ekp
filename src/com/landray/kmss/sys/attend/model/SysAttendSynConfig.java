package com.landray.kmss.sys.attend.model;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attend.forms.SysAttendSynConfigForm;

import java.util.Date;

public class SysAttendSynConfig extends BaseModel{

    private String fdSynType;
    
    private Boolean fdEnableRecord;
    
    private Boolean fdEnableCategory;
    
    private Date fdStartTime;
    
    private Date fdEndTime;
    
    private Date fdSyncTime;

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
    public Boolean getFdEnableRecord() {
        return fdEnableRecord;
    }

    /**
     * 是否开启考勤记录同步
     */
    public void setFdEnableRecord(Boolean fdEnableRecord) {
        this.fdEnableRecord = fdEnableRecord;
    }

    /**
     * 是否开启考勤规则同步
     * @return
     */
    public Boolean getFdEnableCategory() {
        return fdEnableCategory;
    }

    /**
     * 是否开启考勤规则同步
     */
    public void setFdEnableCategory(Boolean fdEnableCategory) {
        this.fdEnableCategory = fdEnableCategory;
    }

    /**
     * 同步开始时间
     * @return
     */
    public Date getFdStartTime() {
        return fdStartTime;
    }

    /**
     * 同步开始时间
     */
    public void setFdStartTime(Date fdStartTime) {
        this.fdStartTime = fdStartTime;
    }

    /**
     * 同步结束时间
     * @return
     */
    public Date getFdEndTime() {
        return fdEndTime;
    }

    /**
     * 同步结束时间
     */
    public void setFdEndTime(Date fdEndTime) {
        this.fdEndTime = fdEndTime;
    }

    /**
     * 最后同步时间
     * @return
     */
    public Date getFdSyncTime() {
        return fdSyncTime;
    }

    /**
     * 最后同步时间
     */
    public void setFdSyncTime(Date fdSyncTime) {
        this.fdSyncTime = fdSyncTime;
    }


    public String getFdSyncRateLimiter() {
        return fdSyncRateLimiter;
    }

    public void setFdSyncRateLimiter(String fdSyncRateLimiter) {
        this.fdSyncRateLimiter = fdSyncRateLimiter;
    }

    @Override
	public Class getFormClass() {
		return SysAttendSynConfigForm.class;
	}

}
