package com.landray.kmss.sys.attend.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTemplate;
import com.landray.kmss.sys.simplecategory.forms.SysSimpleCategoryAuthTmpForm;

public class SysAttendCategoryTemplateForm
		extends SysSimpleCategoryAuthTmpForm {
	@Override
    public Class getModelClass() {
		return SysAttendCategoryTemplate.class;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel(
					"fdParent", SysAttendCategoryTemplate.class));
		}
		return toModelPropertyMap;
	}

	@Override
    public String getAuthReaderNoteFlag() {
		return "2";
	}

}
