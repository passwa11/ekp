package com.landray.kmss.sys.attend.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendAuthSetting;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.web.action.ActionMapping;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-08-28
 */
public class SysAttendAuthSettingForm extends ExtendForm {

	private String fdElementIds;

	private String fdElementNames;

	private String fdAuthIds;

	private String fdAuthNames;

	public String getFdElementIds() {
		return fdElementIds;
	}

	public void setFdElementIds(String fdElementIds) {
		this.fdElementIds = fdElementIds;
	}

	public String getFdElementNames() {
		return fdElementNames;
	}

	public void setFdElementNames(String fdElementNames) {
		this.fdElementNames = fdElementNames;
	}

	public String getFdAuthIds() {
		return fdAuthIds;
	}

	public void setFdAuthIds(String fdAuthIds) {
		this.fdAuthIds = fdAuthIds;
	}

	public String getFdAuthNames() {
		return fdAuthNames;
	}

	public void setFdAuthNames(String fdAuthNames) {
		this.fdAuthNames = fdAuthNames;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		fdElementIds = null;
		fdElementNames = null;
		fdAuthIds = null;
		fdAuthNames = null;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdElementIds",
					new FormConvertor_IDsToModelList("fdElements",
							SysOrgElement.class));
			toModelPropertyMap.put("fdAuthIds",
					new FormConvertor_IDsToModelList("fdAuthList",
							SysOrgElement.class));
		}
		return toModelPropertyMap;
	}

	@Override
	public Class getModelClass() {
		return SysAttendAuthSetting.class;
	}

}
