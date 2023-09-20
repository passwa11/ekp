package com.landray.kmss.km.imeeting.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.imeeting.model.KmImeetingTopicCategory;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.category.model.SysCategoryProperty;
import com.landray.kmss.sys.number.forms.SysNumberMainMappForm;
import com.landray.kmss.sys.number.interfaces.ISysNumberForm;
import com.landray.kmss.sys.simplecategory.forms.SysSimpleCategoryAuthTmpForm;
import com.landray.kmss.sys.tag.forms.SysTagTemplateForm;
import com.landray.kmss.sys.tag.interfaces.ISysTagTemplateForm;
import com.landray.kmss.sys.workflow.base.forms.SysWfTemplateForm;
import com.landray.kmss.sys.workflow.interfaces.ISysWfTemplateForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 创建日期 2007-Sep-18
 * 
 * @author 王晖
 */
public class KmImeetingTopicCategoryForm extends SysSimpleCategoryAuthTmpForm implements
		ISysWfTemplateForm, IAttachmentForm, ISysTagTemplateForm, ISysNumberForm {

	/*
	 * 辅类别
	 */
	private String docPropertyNames = null;

	private String docPropertyIds = null;

	public String getDocPropertyIds() {
		return docPropertyIds;
	}

	public void setDocPropertyIds(String docPropertyIds) {
		this.docPropertyIds = docPropertyIds;
	}

	public String getDocPropertyNames() {
		return docPropertyNames;
	}

	public void setDocPropertyNames(String docPropertyNames) {
		this.docPropertyNames = docPropertyNames;
	}



	@Override
    public Class getModelClass() {
		return KmImeetingTopicCategory.class;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		docPropertyIds = null;
		docPropertyNames = null;
		sysWfTemplateForms.clear();
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());


			toModelPropertyMap.put("docPropertyIds",
					new FormConvertor_IDsToModelList("docProperties",
							SysCategoryProperty.class));

			toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel(
					"fdParent", KmImeetingTopicCategory.class));
		}
		return toModelPropertyMap;
	}



	private AutoHashMap sysWfTemplateForms = new AutoHashMap(
			SysWfTemplateForm.class);

	@Override
    public AutoHashMap getSysWfTemplateForms() {
		return sysWfTemplateForms;
	}

	/*
	 * 附件机制
	 */
	private AutoHashMap attachmentForms = new AutoHashMap(
			AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return attachmentForms;
	}

	public void setAttachmentForms(AutoHashMap autoHashMap) {
		this.attachmentForms = autoHashMap;
	}

	// =============标签机制开始===================
	private AutoHashMap sysTagTemplateForms = new AutoHashMap(
			SysTagTemplateForm.class);

	@Override
    public AutoHashMap getSysTagTemplateForms() {
		return sysTagTemplateForms;
	}
	// =============标签机制结束===================
	
	// ========== 编号机制 开始==========
 	private SysNumberMainMappForm sysNumberMainMappForm = new SysNumberMainMappForm();

 	@Override
    public SysNumberMainMappForm getSysNumberMainMappForm() {
 		return sysNumberMainMappForm;
 	}

 	@Override
    public void setSysNumberMainMappForm(SysNumberMainMappForm frm) {
 		sysNumberMainMappForm = frm;
 	}
 	// ========== 编号机制 结束==========
}
