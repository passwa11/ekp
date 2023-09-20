package com.landray.kmss.km.imeeting.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.imeeting.model.KmImeetingSyncBind;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 同步绑定信息 Form
 * 
 * @author
 * @version 1.0 2013-10-14
 */
@SuppressWarnings("serial")
public class KmImeetingSyncBindForm extends ExtendForm {

	/**
	 * 所属应用
	 */
	protected String fdAppKey = null;

	public String getFdAppKey() {
		return fdAppKey;
	}

	public void setFdAppKey(String fdAppKey) {
		this.fdAppKey = fdAppKey;
	}

	/**
	 * 同步时间戳
	 */
	protected String fdSyncTimestamp = null;

	/**
	 * @return 同步时间戳
	 */
	public String getFdSyncTimestamp() {
		return fdSyncTimestamp;
	}

	/**
	 * @param fdSyncTimestamp
	 *            同步时间戳
	 */
	public void setFdSyncTimestamp(String fdSyncTimestamp) {
		this.fdSyncTimestamp = fdSyncTimestamp;
	}

	/**
	 * 所属人员的ID
	 */
	protected String fdOwnerId = null;

	public String getFdOwnerId() {
		return fdOwnerId;
	}

	public void setFdOwnerId(String fdOwnerId) {
		this.fdOwnerId = fdOwnerId;
	}

	public String getFdOwnerName() {
		return fdOwnerName;
	}

	public void setFdOwnerName(String fdOwnerName) {
		this.fdOwnerName = fdOwnerName;
	}

	/**
	 * 所属人员的名称
	 */
	protected String fdOwnerName = null;

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdAppKey = null;
		fdSyncTimestamp = null;
		fdOwnerId = null;
		fdOwnerName = null;

		super.reset(mapping, request);
	}

	@Override
    @SuppressWarnings("unchecked")
	public Class getModelClass() {
		return KmImeetingSyncBind.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdOwnerId", new FormConvertor_IDToModel(
					"fdOwner", SysOrgElement.class));
		}
		return toModelPropertyMap;
	}
}
