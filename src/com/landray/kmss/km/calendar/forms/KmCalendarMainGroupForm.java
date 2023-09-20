package com.landray.kmss.km.calendar.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.calendar.model.KmCalendarMain;
import com.landray.kmss.km.calendar.model.KmCalendarMainGroup;
import com.landray.kmss.km.calendar.model.KmCalendarPersonGroup;
import com.landray.kmss.web.action.ActionMapping;

public class KmCalendarMainGroupForm extends ExtendForm {

	protected String fdGroupId = null;

	protected String fdGroupName = null;

	public String getFdGroupId() {
		return fdGroupId;
	}

	public void setFdGroupId(String fdGroupId) {
		this.fdGroupId = fdGroupId;
	}

	public String getFdGroupName() {
		return fdGroupName;
	}

	public void setFdGroupName(String fdGroupName) {
		this.fdGroupName = fdGroupName;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdGroupId = null;
		fdGroupName = null;
		fdMainIds = null;
		fdMainNames = null;
		super.reset(mapping, request);
	}

	protected String fdMainIds = null;

	protected String fdMainNames = null;

	public String getFdMainIds() {
		return fdMainIds;
	}

	public void setFdMainIds(String fdMainIds) {
		this.fdMainIds = fdMainIds;
	}

	public String getFdMainNames() {
		return fdMainNames;
	}

	public void setFdMainNames(String fdMainNames) {
		this.fdMainNames = fdMainNames;
	}

	@Override
	public Class getModelClass() {
		return KmCalendarMainGroup.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdGroupId", new FormConvertor_IDToModel(
					"fdGroup", KmCalendarPersonGroup.class));
			toModelPropertyMap.put("fdMainIds",
					new FormConvertor_IDsToModelList("fdMainList",
							KmCalendarMain.class));
		}
		return toModelPropertyMap;
	}
}
