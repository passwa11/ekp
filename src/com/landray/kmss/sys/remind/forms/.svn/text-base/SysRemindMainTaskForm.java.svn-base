package com.landray.kmss.sys.remind.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.remind.model.SysRemindMainTask;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 提醒任务
 * 
 * @author panyh
 * @date Jun 27, 2020
 */
public class SysRemindMainTaskForm extends ExtendForm {

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

	@Override
	public Class getModelClass() {
		return SysRemindMainTask.class;
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.fdModelName = null;
		this.fdModelId = null;
		this.fdRemindId = null;
		this.fdSubject = null;
		this.fdRunTime = null;
		this.fdTriggerId = null;
		this.fdKey = null;
		super.reset(mapping, request);
	}

	private FormToModelPropertyMap toModelPropertyMap;

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return toModelPropertyMap;
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

	public String getFdKey() {
		return fdKey;
	}

	public void setFdKey(String fdKey) {
		this.fdKey = fdKey;
	}

}
