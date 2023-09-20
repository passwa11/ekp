package com.landray.kmss.sys.remind.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.BaseCoreInnerForm;
import com.landray.kmss.sys.remind.model.SysRemindTemplate;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 提醒中心模板
 * 
 * @author panyh
 * @date Jun 23, 2020
 */
public class SysRemindTemplateForm extends BaseCoreInnerForm {

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
	private AutoArrayList fdMains = new AutoArrayList(SysRemindMainForm.class);

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

	public AutoArrayList getFdMains() {
		return fdMains;
	}

	public void setFdMains(AutoArrayList fdMains) {
		this.fdMains = fdMains;
	}

	@Override
	public Class getModelClass() {
		return SysRemindTemplate.class;
	}

	private FormToModelPropertyMap toModelPropertyMap;

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdMains", new FormConvertor_FormListToModelList("fdMains", "fdTemplate"));
		}
		return toModelPropertyMap;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdTemplateId = null;
		fdTemplateName = null;
		fdMains.clear();
		fdModuleUrl = null;
		fdTemplateProperty = null;
		super.reset(mapping, request);
	}

}
