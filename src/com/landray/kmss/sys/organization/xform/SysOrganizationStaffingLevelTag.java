/**
 * 
 */
package com.landray.kmss.sys.organization.xform;

import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.taglib.xform.AbstractFieldElementTag;
import com.landray.kmss.web.taglib.xform.IFieldElementRender;
import com.landray.kmss.web.taglib.xform.mobile.AddressMobileRender;


@SuppressWarnings("serial")
public class SysOrganizationStaffingLevelTag extends AbstractFieldElementTag {

	protected String propertyId;

	/**
	 * @param 组织架构ID
	 */
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
		String formName = getFormName();
		if (StringUtil.isNotNull(formName)) {
			this.propertyId = formName + "." + this.propertyId;
		}
	}

	public String getPropertyId() {
		return propertyId;
	}

	protected String propertyName;

	/**
	 * @param 组织架构Name
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		String formName = getFormName();
		if (StringUtil.isNotNull(formName)) {
			this.propertyName = formName + "." + this.propertyName;
		}
	}

	public String getPropertyName() {
		return propertyName;
	}

	protected String idValue;

	public String getIdValue() {
		return idValue;
	}

	public void setIdValue(String idValue) {
		this.idValue = idValue;
	}

	protected String nameValue;

	public String getNameValue() {
		return nameValue;
	}

	public void setNameValue(String nameValue) {
		this.nameValue = nameValue;
	}


	@Override
	protected IFieldElementRender getFieldElementRender() {
		if (mobile) {
            return new AddressMobileRender(pageContext, this);
        }
		return new SysOrganizationStaffingLevelPcRender(pageContext, this);
	}

	@Override
	public void release() {
		super.release();
		propertyId = null;
		propertyName = null;
		idValue = null;
		nameValue = null;
	}
}
