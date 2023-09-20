package com.landray.kmss.sys.remind.model;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.remind.forms.SysRemindMainReceiverForm;

/**
 * 接收者
 * 
 * @author panyh
 * @date Jun 23, 2020
 */
public class SysRemindMainReceiver extends BaseModel {

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
	private List<SysOrgElement> fdReceiverOrgs = new ArrayList<SysOrgElement>();

	/**
	 * 排序号
	 */
	private Integer fdOrder;

	/**
	 * 所属提醒
	 */
	private SysRemindMain fdRemind;

	@Override
	public Class getFormClass() {
		return SysRemindMainReceiverForm.class;
	}

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

	public List<SysOrgElement> getFdReceiverOrgs() {
		return fdReceiverOrgs;
	}

	public void setFdReceiverOrgs(List<SysOrgElement> fdReceiverOrgs) {
		this.fdReceiverOrgs = fdReceiverOrgs;
	}

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	public SysRemindMain getFdRemind() {
		return fdRemind;
	}

	public void setFdRemind(SysRemindMain fdRemind) {
		this.fdRemind = fdRemind;
	}

	private ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdReceiverOrgs", new ModelConvertor_ModelListToString("fdReceiverOrgIds:fdReceiverOrgNames", "fdId:fdName"));
			toFormPropertyMap.put("fdRemind.fdId", "fdRemindId");
			toFormPropertyMap.put("fdRemind.fdName", "fdRemindName");
		}

		return toFormPropertyMap;
	}

}
