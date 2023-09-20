package com.landray.kmss.sys.remind.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.remind.model.SysRemindMain;
import com.landray.kmss.sys.remind.model.SysRemindMainReceiver;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 接收人
 * 
 * @author panyh
 * @date Jun 23, 2020
 */
public class SysRemindMainReceiverForm extends ExtendForm {

	/**
	 * 接收者类型
	 */
	private String fdType;

	/**
	 * 接收者ID（表单字段）
	 */
	private String fdReceiverId;

	/**
	 * 接收者名称（表单字段）
	 */
	private String fdReceiverName;

	/**
	 * 接收者（组织架构）
	 */
	private String fdReceiverOrgIds;
	private String fdReceiverOrgNames;

	/**
	 * 排序号
	 */
	private String fdOrder;

	/**
	 * 所属提醒
	 */
	private String fdRemindId;
	private String fdRemindName;

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	public String getFdReceiverId() {
		return fdReceiverId;
	}

	public void setFdReceiverId(String fdReceiverId) {
		this.fdReceiverId = fdReceiverId;
	}

	public String getFdReceiverName() {
		return fdReceiverName;
	}

	public void setFdReceiverName(String fdReceiverName) {
		this.fdReceiverName = fdReceiverName;
	}

	public String getFdReceiverOrgIds() {
		return fdReceiverOrgIds;
	}

	public void setFdReceiverOrgIds(String fdReceiverOrgIds) {
		this.fdReceiverOrgIds = fdReceiverOrgIds;
	}

	public String getFdReceiverOrgNames() {
		return fdReceiverOrgNames;
	}

	public void setFdReceiverOrgNames(String fdReceiverOrgNames) {
		this.fdReceiverOrgNames = fdReceiverOrgNames;
	}

	public String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	public String getFdRemindId() {
		return fdRemindId;
	}

	public void setFdRemindId(String fdRemindId) {
		this.fdRemindId = fdRemindId;
	}

	public String getFdRemindName() {
		return fdRemindName;
	}

	public void setFdRemindName(String fdRemindName) {
		this.fdRemindName = fdRemindName;
	}

	@Override
	public Class getModelClass() {
		return SysRemindMainReceiver.class;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.fdType = null;
		this.fdReceiverId = null;
		this.fdReceiverName = null;
		this.fdReceiverOrgIds = null;
		this.fdReceiverOrgNames = null;
		this.fdOrder = null;
		this.fdRemindId = null;
		this.fdRemindName = null;
		super.reset(mapping, request);
	}

	private FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdRemindId", new FormConvertor_IDToModel("fdRemind", SysRemindMain.class));
			toModelPropertyMap.put("fdReceiverOrgIds", new FormConvertor_IDsToModelList("fdReceiverOrgs", SysOrgElement.class));
		}
		return toModelPropertyMap;
	}

}
