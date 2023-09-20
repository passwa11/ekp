package com.landray.kmss.sys.attend.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.attend.forms.SysAttendDeviceExcForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.AutoHashMap;

/**
 * 设备异常记录
 * 
 * @author linxiuxian
 *
 */
public class SysAttendDeviceExc extends BaseModel implements IAttachment {

	/**
	 * 客户端类型kk,ding
	 */
	private String fdClientType;

	// 设备异常处理方式(camera:拍照备注,face:刷脸验证)
	private String fdDeviceExcMode;

	private SysOrgElement docCreator;

	private Date docCreateTime;
	private String fdMainId;

	public String getFdClientType() {
		return fdClientType;
	}

	public void setFdClientType(String fdClientType) {
		this.fdClientType = fdClientType;
	}

	public String getFdDeviceExcMode() {
		return fdDeviceExcMode;
	}

	public void setFdDeviceExcMode(String fdDeviceExcMode) {
		this.fdDeviceExcMode = fdDeviceExcMode;
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

	public String getFdMainId() {
		return fdMainId;
	}

	public void setFdMainId(String fdMainId) {
		this.fdMainId = fdMainId;
	}

	@Override
	public Class getFormClass() {
		return SysAttendDeviceExcForm.class;
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
	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);
	@Override
	public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}
}
