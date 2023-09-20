package com.landray.kmss.sys.handover.model;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.handover.forms.SysHandoverConfigLogDetailForm;

/**
 * 工作交接明细
 * 
 * @author 缪贵荣
 * 
 */
@SuppressWarnings("serial")
public class SysHandoverConfigLogDetail extends BaseModel implements InterceptFieldEnabled{
	public static final Integer STATE_SUCC = 1;
	public static final Integer STATE_IGNORE = 2;

	/**
	 * 配置id
	 */
	private String fdModelId;

	public String getFdModelId() {
		return fdModelId;
	}

	public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}

	/**
	 * 配置model
	 */
	private String fdModelName;

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	/**
	 * 描述说明
	 */
	private String fdDescription;

	public String getFdDescription() {
		return (String) readLazyField("fdDescription", fdDescription);
	}

	public void setFdDescription(String fdDescription) {
		this.fdDescription = (String) writeLazyField("fdDescription",
				this.fdDescription, fdDescription);
	}
	
	/**
	 * 交接节点
	 */
	private String fdFactId;

	public String getFdFactId() {
		return fdFactId;
	}

	public void setFdFactId(String fdFactId) {
		this.fdFactId = fdFactId;
	}

	/**
	 * 请求路径
	 * 
	 */
	public String fdUrl;

	public String getFdUrl() {
		return fdUrl;
	}

	public void setFdUrl(String fdUrl) {
		this.fdUrl = fdUrl;
	}

	/**
	 * 所属日志
	 */
	private SysHandoverConfigLog fdLog;

	public SysHandoverConfigLog getFdLog() {
		return fdLog;
	}

	public void setFdLog(SysHandoverConfigLog fdLog) {
		this.fdLog = fdLog;
	}
	
	/**
	 * 状态：1、成功；2、忽略
	 * 
	 */
	public Integer fdState;

	public Integer getFdState() {
		return fdState;
	}

	public void setFdState(Integer fdState) {
		this.fdState = fdState;
	}

	@Override
    public Class<?> getFormClass() {
		return SysHandoverConfigLogDetailForm.class;
	}

}
