package com.landray.kmss.sys.remind.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzModel;
import com.landray.kmss.sys.remind.forms.SysRemindMainTaskForm;

/**
 * 提醒任务
 * 
 * @author panyh
 * @date Jun 23, 2020
 */
public class SysRemindMainTask extends BaseModel implements ISysQuartzModel, ISysNotifyModel {

	/**
	 * 业务主文档类名
	 */
	private String fdModelName;

	/**
	 * 业务主文档ID
	 */
	private String fdModelId;

	/**
	 * 所属提醒ID
	 */
	private String fdRemindId;

	/**
	 * 发送标题
	 */
	private String fdSubject;

	/**
	 * 执行时间
	 */
	private Date fdRunTime;

	/**
	 * 触发器ID（如流程发布后提醒，在保存提醒任务时，需要把触发器也保存）
	 */
	private String fdTriggerId;

	/**
	 * 触发关键字（后期触发任务时需要用到）
	 */
	private String fdKey;

	/**
	 * 任务日志
	 */
	private List<SysRemindMainTaskLog> fdTaskLogs = new ArrayList<SysRemindMainTaskLog>();

	@Override
	public Class getFormClass() {
		return SysRemindMainTaskForm.class;
	}

	private ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}

		return toFormPropertyMap;
	}

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	public String getFdModelId() {
		return fdModelId;
	}

	public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}

	public String getFdRemindId() {
		return fdRemindId;
	}

	public void setFdRemindId(String fdRemindId) {
		this.fdRemindId = fdRemindId;
	}

	public String getFdSubject() {
		return fdSubject;
	}

	public void setFdSubject(String fdSubject) {
		this.fdSubject = fdSubject;
	}

	public Date getFdRunTime() {
		return fdRunTime;
	}

	public void setFdRunTime(Date fdRunTime) {
		this.fdRunTime = fdRunTime;
	}

	public String getFdTriggerId() {
		return fdTriggerId;
	}

	public void setFdTriggerId(String fdTriggerId) {
		this.fdTriggerId = fdTriggerId;
	}

	public List<SysRemindMainTaskLog> getFdTaskLogs() {
		return fdTaskLogs;
	}

	public void setFdTaskLogs(List<SysRemindMainTaskLog> fdTaskLogs) {
		this.fdTaskLogs = fdTaskLogs;
	}

	public String getFdKey() {
		return fdKey;
	}

	public void setFdKey(String fdKey) {
		this.fdKey = fdKey;
	}

}
