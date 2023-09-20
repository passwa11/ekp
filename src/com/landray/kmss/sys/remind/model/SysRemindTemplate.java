package com.landray.kmss.sys.remind.model;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseCoreInnerModel;
import com.landray.kmss.sys.remind.forms.SysRemindTemplateForm;

/**
 * 提醒中心模板
 * 
 * @author panyh
 * @date Jun 23, 2020
 */
public class SysRemindTemplate extends BaseCoreInnerModel {

	/**
	 * 关联模板ID
	 */
	private String fdTemplateId;

	/**
	 * 关联模板名称
	 */
	private String fdTemplateName;

	/**
	 * 模块路径
	 */
	private String fdModuleUrl;

	/**
	 * 主文档模板属性
	 */
	private String fdTemplateProperty;

	/**
	 * 提醒设置
	 */
	private List<SysRemindMain> fdMains = new ArrayList<SysRemindMain>();

	public String getFdTemplateId() {
		return fdTemplateId;
	}

	public void setFdTemplateId(String fdTemplateId) {
		this.fdTemplateId = fdTemplateId;
	}

	public String getFdTemplateName() {
		return fdTemplateName;
	}

	public void setFdTemplateName(String fdTemplateName) {
		this.fdTemplateName = fdTemplateName;
	}

	public String getFdModuleUrl() {
		return fdModuleUrl;
	}

	public void setFdModuleUrl(String fdModuleUrl) {
		this.fdModuleUrl = fdModuleUrl;
	}

	public String getFdTemplateProperty() {
		return fdTemplateProperty;
	}

	public void setFdTemplateProperty(String fdTemplateProperty) {
		this.fdTemplateProperty = fdTemplateProperty;
	}

	public List<SysRemindMain> getFdMains() {
		return fdMains;
	}

	public void setFdMains(List<SysRemindMain> fdMains) {
		this.fdMains = fdMains;
	}

	@Override
	public Class getFormClass() {
		return SysRemindTemplateForm.class;
	}

	private ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdMains", new ModelConvertor_ModelListToFormList("fdMains"));
		}

		return toFormPropertyMap;
	}
}
