package com.landray.kmss.sys.remind.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.remind.model.SysRemindTemplateRelation;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 提醒模板关系
 * 
 * @author panyh
 * @date Jun 30, 2020
 */
public class SysRemindTemplateRelationForm extends ExtendForm {

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
	 * 主文档名称
	 */
	private String fdModelName;

	@Override
	public Class getModelClass() {
		return SysRemindTemplateRelation.class;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.fdTemplateName = null;
		this.fdModuleUrl = null;
		this.fdTemplateProperty = null;
		this.fdModelName = null;
		super.reset(mapping, request);
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

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

}
