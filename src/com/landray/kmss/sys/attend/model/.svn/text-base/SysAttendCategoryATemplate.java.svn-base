package com.landray.kmss.sys.attend.model;

import java.util.List;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.attend.forms.SysAttendCategoryATemplateForm;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmTemplateModel;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;

/**
 * 考勤组分类
 * 
 * @author admin
 *
 */
public class SysAttendCategoryATemplate extends SysSimpleCategoryAuthTmpModel
		implements ISysLbpmTemplateModel {

	@Override
    public Class getFormClass() {
		return SysAttendCategoryATemplateForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
		}
		return toFormPropertyMap;
	}

	private List sysWfTemplateModels;

	@Override
    public List getSysWfTemplateModels() {
		return sysWfTemplateModels;
	}

	@Override
    public void setSysWfTemplateModels(List sysWfTemplateModels) {
		this.sysWfTemplateModels = sysWfTemplateModels;
	}

}
