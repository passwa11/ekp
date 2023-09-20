package com.landray.kmss.sys.attend.model;

import java.util.Date;

import com.landray.kmss.common.model.BaseModel;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

public class SysAttendSynDingQueueError extends BaseModel implements
		InterceptFieldEnabled {

	/**
	 * 开始时间
	 */
	private Date fdStartTime;

	/**
	 * 结束时间
	 */
	private Date fdEndTime;

	/**
	 * 异常信息
	 */
	private String fdErrorMsg;

	/**
	 * 重复处理次数
	 */
	private Integer fdRepeatHandle;

	/**
	 * 处理标识(失败:1,发送中:0)
	 */
	private String fdFlag;

	private Date fdCreateTime;

	/**
	 * 最终同步时间
	 */
	private Date fdSynTime;
	private String fdMD5;
	private String fdUserIds;

	public String getFdUserIds() {
		return fdUserIds;
	}

	public void setFdUserIds(String fdUserIds) {
		this.fdUserIds = fdUserIds;
	}

	@Override
	public Class getFormClass() {
		return null;
	}

    public Date getFdStartTime() {
        return fdStartTime;
    }

    public void setFdStartTime(Date fdStartTime) {
        this.fdStartTime = fdStartTime;
    }

    public Date getFdEndTime() {
        return fdEndTime;
    }

    public void setFdEndTime(Date fdEndTime) {
        this.fdEndTime = fdEndTime;
    }

    public String getFdErrorMsg() {
        return fdErrorMsg;
    }

    public void setFdErrorMsg(String fdErrorMsg) {
        this.fdErrorMsg = fdErrorMsg;
    }

    public Integer getFdRepeatHandle() {
        return fdRepeatHandle;
    }

    public void setFdRepeatHandle(Integer fdRepeatHandle) {
        this.fdRepeatHandle = fdRepeatHandle;
    }

    public String getFdFlag() {
        return fdFlag;
    }

    public void setFdFlag(String fdFlag) {
        this.fdFlag = fdFlag;
    }

    public Date getFdCreateTime() {
        return fdCreateTime;
    }

    public void setFdCreateTime(Date fdCreateTime) {
        this.fdCreateTime = fdCreateTime;
    }

    public Date getFdSynTime() {
        return fdSynTime;
    }

    public void setFdSynTime(Date fdSynTime) {
        this.fdSynTime = fdSynTime;
    }

	public String getFdMD5() {
		return fdMD5;
	}

	public void setFdMD5(String fdMD5) {
		this.fdMD5 = fdMD5;
	}

}
