package com.landray.kmss.hr.organization.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationRecentContact;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.web.action.ActionMapping;


/**
 * 最近联系人 Form
 * 
 * @author 
 * @version 1.0 2015-08-04
 */
public class HrOrganizationRecentContactForm extends ExtendForm {

	/**
	 * 创建时间
	 */
	protected String docCreateTime = null;
	
	/**
	 * @return 创建时间
	 */
	public String getDocCreateTime() {
		return docCreateTime;
	}
	
	/**
	 * @param docCreateTime 创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}
	
	/**
	 * 使用者的ID
	 */
	protected String fdUserId = null;
	
	/**
	 * @return 使用者的ID
	 */
	public String getFdUserId() {
		return fdUserId;
	}
	
	/**
	 * @param fdUserId 使用者的ID
	 */
	public void setFdUserId(String fdUserId) {
		this.fdUserId = fdUserId;
	}
	
	/**
	 * 使用者的名称
	 */
	protected String fdUserName = null;
	
	/**
	 * @return 使用者的名称
	 */
	public String getFdUserName() {
		return fdUserName;
	}
	
	/**
	 * @param fdUserName 使用者的名称
	 */
	public void setFdUserName(String fdUserName) {
		this.fdUserName = fdUserName;
	}
	
	/**
	 * 联系人的ID
	 */
	protected String fdContactId = null;
	
	/**
	 * @return 联系人的ID
	 */
	public String getFdContactId() {
		return fdContactId;
	}
	
	/**
	 * @param fdContactId 联系人的ID
	 */
	public void setFdContactId(String fdContactId) {
		this.fdContactId = fdContactId;
	}
	
	/**
	 * 联系人的名称
	 */
	protected String fdContactName = null;
	
	/**
	 * @return 联系人的名称
	 */
	public String getFdContactName() {
		return fdContactName;
	}
	
	/**
	 * @param fdContactName 联系人的名称
	 */
	public void setFdContactName(String fdContactName) {
		this.fdContactName = fdContactName;
	}
	
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		docCreateTime = null;
		fdUserId = null;
		fdUserName = null;
		fdContactId = null;
		fdContactName = null;
		
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return HrOrganizationRecentContact.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdUserId",
					new FormConvertor_IDToModel("fdUser",
						SysOrgElement.class));
			toModelPropertyMap.put("fdContactId",
					new FormConvertor_IDToModel("fdContact",
							HrOrganizationElement.class));
		}
		return toModelPropertyMap;
	}
}
