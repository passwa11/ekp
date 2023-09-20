package com.landray.kmss.hr.staff.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.hr.staff.forms.HrStaffPersonInfoLogForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
 * 员工信息日志
 * 
 * @author 潘永辉 2017-1-7
 * 
 */
public class HrStaffPersonInfoLog extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Override
	public Class getFormClass() {
		return HrStaffPersonInfoLogForm.class;
	}

	// 操作者
	private SysOrgPerson fdCreator;
	// 操作时间
	private Date fdCreateTime;
	// 操作方法
	private String fdParaMethod;
	// 操作记录
	private String fdDetails;
	// 操作对象（员工信息ID），如果是同步操作，需要记录所有的员工信息
	private List<HrStaffPersonInfo> fdTargets = new ArrayList<HrStaffPersonInfo>();
	// IP地址
	private String fdIp;
	// 浏览器
	private String fdBrowser;
	// 设备
	private String fdEquipment;

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());

			toFormPropertyMap.put("fdCreator.fdId", "fdCreatorId");
			toFormPropertyMap.put("fdCreator.fdName", "fdCreatorName");

		}
		return toFormPropertyMap;
	}

	public boolean getIsAnonymous() {
		return "anonymous".equals(fdCreator.getFdLoginName());
	}

	public SysOrgPerson getFdCreator() {
		return fdCreator;
	}

	public void setFdCreator(SysOrgPerson fdCreator) {
		this.fdCreator = fdCreator;
	}

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
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

	public List<HrStaffPersonInfo> getFdTargets() {
		return fdTargets;
	}

	public void setFdTargets(List<HrStaffPersonInfo> fdTargets) {
		this.fdTargets = fdTargets;
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
