package com.landray.kmss.sys.attend.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attend.forms.SysAttendDeviceForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * 用户打卡设备信息
 * 
 * @author linxiuxian
 *
 */
public class SysAttendDevice extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 设备号
	 */
	private String fdDeviceIds;

	/**
	 * 设备类型 kk,ding等
	 */
	private String fdClientType;

	private SysOrgElement docCreator;

	private Date docCreateTime;

	private Date docAlterTime;

	public String getFdDeviceIds() {
		return fdDeviceIds;
	}

	public void setFdDeviceIds(String fdDeviceIds) {
		this.fdDeviceIds = fdDeviceIds;
	}

	public String getFdClientType() {
		return fdClientType;
	}

	public void setFdClientType(String fdClientType) {
		this.fdClientType = fdClientType;
	}

	public SysOrgElement getDocCreator() {
		return docCreator;
	}

	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
	}

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public Date getDocAlterTime() {
		return docAlterTime;
	}

	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	@Override
	public Class getFormClass() {
		return SysAttendDeviceForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
		}
		return toFormPropertyMap;
	}
}
