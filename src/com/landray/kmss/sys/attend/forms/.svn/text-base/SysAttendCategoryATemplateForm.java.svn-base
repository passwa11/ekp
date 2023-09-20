package com.landray.kmss.sys.attend.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.attend.model.SysAttendCategoryATemplate;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmTemplateForm;
import com.landray.kmss.sys.lbpmservice.support.forms.LbpmTemplateForm;
import com.landray.kmss.sys.simplecategory.forms.SysSimpleCategoryAuthTmpForm;
import com.landray.kmss.util.AutoHashMap;

public class SysAttendCategoryATemplateForm
		extends SysSimpleCategoryAuthTmpForm implements ISysLbpmTemplateForm {

	@Override
	public Class getModelClass() {
		return SysAttendCategoryATemplate.class;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		sysWfTemplateForms.clear();
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel(
					"fdParent", SysAttendCategoryATemplate.class));
		}
		return toModelPropertyMap;
	}

	private AutoHashMap sysWfTemplateForms = new AutoHashMap(
			LbpmTemplateForm.class);

	@Override
	public AutoHashMap getSysWfTemplateForms() {
		return sysWfTemplateForms;
	}

	@Override
	public String getAuthReaderNoteFlag() {
		return "2";
	}

}
