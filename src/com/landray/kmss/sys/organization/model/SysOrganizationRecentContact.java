package com.landray.kmss.sys.organization.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.forms.SysOrganizationRecentContactForm;

/**
 * 最近联系人
 * 
 * @author 
 * @version 1.0 2015-08-04
 */
public class SysOrganizationRecentContact extends BaseModel {

	/**
	 * 创建时间
	 */
	protected Date docCreateTime;
	
	/**
	 * @return 创建时间
	 */
	public Date getDocCreateTime() {
		return docCreateTime;
	}
	
	/**
	 * @param docCreateTime 创建时间
	 */
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}
	
	/**
	 * 使用者
	 */
	protected SysOrgElement fdUser;
	
	/**
	 * @return 使用者
	 */
	public SysOrgElement getFdUser() {
		return fdUser;
	}
	
	/**
	 * @param fdUser 使用者
	 */
	public void setFdUser(SysOrgElement fdUser) {
		this.fdUser = fdUser;
	}
	
	/**
	 * 联系人
	 */
	protected SysOrgElement fdContact;
	
	/**
	 * @return 联系人
	 */
	public SysOrgElement getFdContact() {
		return fdContact;
	}
	
	/**
	 * @param fdContact 联系人
	 */
	public void setFdContact(SysOrgElement fdContact) {
		this.fdContact = fdContact;
	}
	
	@Override
    public Class getFormClass() {
		return SysOrganizationRecentContactForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdUser.fdId", "fdUserId");
			toFormPropertyMap.put("fdUser.fdName", "fdUserName");
			toFormPropertyMap.put("fdContact.fdId", "fdContactId");
			toFormPropertyMap.put("fdContact.fdName", "fdContactName");
		}
		return toFormPropertyMap;
	}
	
	protected int version = 0;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
