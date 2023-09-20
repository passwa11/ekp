package com.landray.kmss.sys.remind.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseCreateInfoModel;
import com.landray.kmss.sys.remind.forms.SysRemindMainForm;

import java.util.ArrayList;
import java.util.List;

/**
 * 提醒中心
 * 
 * @author panyh
 * @date Jun 23, 2020
 */
public class SysRemindMain extends BaseCreateInfoModel {

	/**
	 * 提醒项名称
	 */
	private String fdName;

	/**
	 * 是否过滤
	 */
	private Boolean fdIsFilter;

	/**
	 * 过滤器
	 */
	private String fdConditionId;
	private String fdConditionName;

	/**
	 * 通知类型
	 */
	private String fdNotifyType;

	/**
	 * 发送人类型
	 */
	private String fdSenderType;

	/**
	 * 发送人ID
	 */
	private String fdSenderId;

	/**
	 * 发送人名称
	 */
	private String fdSenderName;

	/**
	 * 发送标题
	 */
	private String fdSubjectId;
	private String fdSubjectName;

	/**
	 * 排序号
	 */
	private Integer fdOrder;

	/**
	 * 接收者
	 */
	private List<SysRemindMainReceiver> fdReceivers = new ArrayList<SysRemindMainReceiver>();

	/**
	 * 触发器
	 */
	private List<SysRemindMainTrigger> fdTriggers = new ArrayList<SysRemindMainTrigger>();

	/**
	 * 提醒模板
	 */
	private SysRemindTemplate fdTemplate;

	/**
	 * 是否启用
	 */
	private Boolean fdIsEnable;

	/**
	 * 不存数据库，只做前端标志
	 */
	private String deleteFlag;

	@Override
	public Class getFormClass() {
		return SysRemindMainForm.class;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public Boolean getFdIsFilter() {
		return fdIsFilter;
	}

	public void setFdIsFilter(Boolean fdIsFilter) {
		this.fdIsFilter = fdIsFilter;
	}

	public String getFdConditionId() {
		return fdConditionId;
	}

	public void setFdConditionId(String fdConditionId) {
		this.fdConditionId = fdConditionId;
	}

	public String getFdConditionName() {
		return fdConditionName;
	}

	public void setFdConditionName(String fdConditionName) {
		this.fdConditionName = fdConditionName;
	}

	public String getFdNotifyType() {
		return fdNotifyType;
	}

	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}

	public String getFdSenderType() {
		return fdSenderType;
	}

	public void setFdSenderType(String fdSenderType) {
		this.fdSenderType = fdSenderType;
	}

	public String getFdSenderId() {
		return fdSenderId;
	}

	public void setFdSenderId(String fdSenderId) {
		this.fdSenderId = fdSenderId;
	}

	public String getFdSenderName() {
		return fdSenderName;
	}

	public void setFdSenderName(String fdSenderName) {
		this.fdSenderName = fdSenderName;
	}

	public String getFdSubjectId() {
		return fdSubjectId;
	}

	public void setFdSubjectId(String fdSubjectId) {
		this.fdSubjectId = fdSubjectId;
	}

	public String getFdSubjectName() {
		return fdSubjectName;
	}

	public void setFdSubjectName(String fdSubjectName) {
		this.fdSubjectName = fdSubjectName;
	}

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	public SysRemindTemplate getFdTemplate() {
		return fdTemplate;
	}

	public void setFdTemplate(SysRemindTemplate fdTemplate) {
		this.fdTemplate = fdTemplate;
	}

	public List<SysRemindMainReceiver> getFdReceivers() {
		return fdReceivers;
	}

	public void setFdReceivers(List<SysRemindMainReceiver> fdReceivers) {
		this.fdReceivers = fdReceivers;
	}

	public List<SysRemindMainTrigger> getFdTriggers() {
		return fdTriggers;
	}

	public void setFdTriggers(List<SysRemindMainTrigger> fdTriggers) {
		this.fdTriggers = fdTriggers;
	}

	public Boolean getFdIsEnable() {
		return fdIsEnable;
	}

	public void setFdIsEnable(Boolean fdIsEnable) {
		this.fdIsEnable = fdIsEnable;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	private ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdReceivers", new ModelConvertor_ModelListToFormList("fdReceivers"));
			toFormPropertyMap.put("fdTriggers", new ModelConvertor_ModelListToFormList("fdTriggers"));
			toFormPropertyMap.put("fdTemplate.fdId", "fdTemplateId");
			toFormPropertyMap.put("docCreator.fdId", new ModelConvertor_Common("docCreatorId"));
			toFormPropertyMap.put("docCreator.fdName", new ModelConvertor_Common("docCreatorName"));
		}

		return toFormPropertyMap;
	}

}
