package com.landray.kmss.sys.remind.forms;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.remind.model.SysRemindMain;
import com.landray.kmss.sys.remind.model.SysRemindTemplate;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 提醒中心
 * 
 * @author panyh
 * @date Jun 23, 2020
 */
public class SysRemindMainForm extends ExtendForm {

	/**
	 * 提醒项名称
	 */
	private String fdName;

	/**
	 * 是否过滤
	 */
	private String fdIsFilter;

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
	private String fdOrder;

	/**
	 * 接收者
	 */
	private List<SysRemindMainReceiverForm> fdReceivers = new AutoArrayList(SysRemindMainReceiverForm.class);

	/**
	 * 触发器
	 */
	private List<SysRemindMainTriggerForm> fdTriggers = new AutoArrayList(SysRemindMainTriggerForm.class);

	/**
	 * 提醒模板
	 */
	private String fdTemplateId;

	/**
	 * 是否启用
	 */
	private String fdIsEnable;

	/**
	 * 创建时间
	 */
	private String docCreateTime = null;

	/**
	 * 创建者
	 */
	private String docCreatorId = null;
	private String docCreatorName = null;

	/**
	 * 数据库不存该字段，只是用于删除标志
	 */
	private String deleteFlag;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdIsFilter() {
		return fdIsFilter;
	}

	public void setFdIsFilter(String fdIsFilter) {
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

	public String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	public List<SysRemindMainReceiverForm> getFdReceivers() {
		return fdReceivers;
	}

	public void setFdReceivers(List<SysRemindMainReceiverForm> fdReceivers) {
		this.fdReceivers = fdReceivers;
	}

	public List<SysRemindMainTriggerForm> getFdTriggers() {
		return fdTriggers;
	}

	public void setFdTriggers(List<SysRemindMainTriggerForm> fdTriggers) {
		this.fdTriggers = fdTriggers;
	}

	public String getFdTemplateId() {
		return fdTemplateId;
	}

	public void setFdTemplateId(String fdTemplateId) {
		this.fdTemplateId = fdTemplateId;
	}

	public String getFdIsEnable() {
		return fdIsEnable;
	}

	public void setFdIsEnable(String fdIsEnable) {
		this.fdIsEnable = fdIsEnable;
	}

	public String getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getDocCreatorId() {
		return docCreatorId;
	}

	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	public String getDocCreatorName() {
		return docCreatorName;
	}

	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	@Override
	public Class getModelClass() {
		return SysRemindMain.class;
	}

	private FormToModelPropertyMap toModelPropertyMap;

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdReceivers", new FormConvertor_FormListToModelList("fdReceivers", "fdRemind"));
			toModelPropertyMap.put("fdTriggers", new FormConvertor_FormListToModelList("fdTriggers", "fdRemind"));
			toModelPropertyMap.put("fdTemplateId", new FormConvertor_IDToModel("fdTemplate", SysRemindTemplate.class));
		}
		return toModelPropertyMap;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdIsFilter = null;
		fdConditionId = null;
		fdConditionName = null;
		fdNotifyType = null;
		fdSenderType = null;
		fdSenderId = null;
		fdSenderName = null;
		fdSubjectId = null;
		fdSubjectName = null;
		fdOrder = null;
		fdTemplateId = null;
		fdReceivers.clear();
		fdTriggers.clear();
		fdIsEnable = null;
		docCreateTime = null;
		docCreatorId = null;
		docCreatorName = null;
		cloneId = null;
		super.reset(mapping, request);
	}

	/**
	 * 克隆ID
	 */
	private String cloneId;

	public String getCloneId() {
		return cloneId;
	}

	public void setCloneId(String cloneId) {
		this.cloneId = cloneId;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}
