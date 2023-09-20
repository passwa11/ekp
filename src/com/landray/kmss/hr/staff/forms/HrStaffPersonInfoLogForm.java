package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoLog;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
 * 员工信息日志
 * 
 * @author 潘永辉 2017-1-7
 * 
 */
public class HrStaffPersonInfoLogForm extends ExtendForm {
	private static final long serialVersionUID = 1L;

	@Override
	public Class getModelClass() {
		return HrStaffPersonInfoLog.class;
	}

	// 操作者
	private String fdCreatorId;
	private String fdCreatorName;
	private String isAnonymous;
	// 操作时间
	private String fdCreateTime;
	// 操作方法
	private String fdParaMethod;
	// 操作记录
	private String fdDetails;
	// 操作对象（员工信息ID）
	private String fdTargetIds;
	private String fdTargetNames;
	// IP地址
	private String fdIp;
	// 浏览器
	private String fdBrowser;
	// 设备
	private String fdEquipment;

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());

			toModelPropertyMap.put("fdCreatorId", new FormConvertor_IDToModel(
					"fdCreator", SysOrgPerson.class));
			toModelPropertyMap.put("fdTargetIds",
					new FormConvertor_IDsToModelList("fdTargets",
							HrStaffPersonInfo.class));
		}
		return toModelPropertyMap;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.isAnonymous = null;
		this.fdCreatorId = null;
		this.fdCreatorName = null;
		this.fdCreateTime = null;
		this.fdParaMethod = null;
		this.fdDetails = null;
		this.fdTargetIds = null;
		this.fdTargetNames = null;
		this.fdIp = null;
		this.fdBrowser = null;
		this.fdEquipment = null;
	}

	public String getIsAnonymous() {
		return isAnonymous;
	}

	public void setIsAnonymous(String isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	public String getFdCreatorId() {
		return fdCreatorId;
	}

	public void setFdCreatorId(String fdCreatorId) {
		this.fdCreatorId = fdCreatorId;
	}

	public String getFdCreatorName() {
		return fdCreatorName;
	}

	public void setFdCreatorName(String fdCreatorName) {
		this.fdCreatorName = fdCreatorName;
	}

	public String getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(String fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	public String getFdParaMethod() {
		return fdParaMethod;
	}

	public void setFdParaMethod(String fdParaMethod) {
		this.fdParaMethod = fdParaMethod;
	}

	public String getFdDetails() {
		return fdDetails;
	}

	public void setFdDetails(String fdDetails) {
		this.fdDetails = fdDetails;
	}

	public String getFdTargetIds() {
		return fdTargetIds;
	}

	public void setFdTargetIds(String fdTargetIds) {
		this.fdTargetIds = fdTargetIds;
	}

	public String getFdTargetNames() {
		return fdTargetNames;
	}

	public void setFdTargetNames(String fdTargetNames) {
		this.fdTargetNames = fdTargetNames;
	}

	public String getFdIp() {
		return fdIp;
	}

	public void setFdIp(String fdIp) {
		this.fdIp = fdIp;
	}

	public String getFdBrowser() {
		return fdBrowser;
	}

	public void setFdBrowser(String fdBrowser) {
		this.fdBrowser = fdBrowser;
	}

	public String getFdEquipment() {
		return fdEquipment;
	}

	public void setFdEquipment(String fdEquipment) {
		this.fdEquipment = fdEquipment;
	}

}
