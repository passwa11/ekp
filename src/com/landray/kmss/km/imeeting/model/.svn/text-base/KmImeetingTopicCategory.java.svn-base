package com.landray.kmss.km.imeeting.model;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.km.imeeting.forms.KmImeetingTopicCategoryForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.number.interfaces.ISysNumberModel;
import com.landray.kmss.sys.number.model.SysNumberMainMapp;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;
import com.landray.kmss.sys.tag.interfaces.ISysTagTemplateModel;
import com.landray.kmss.sys.workflow.interfaces.ISysWfTemplateModel;
import com.landray.kmss.util.AutoHashMap;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
/**
 * 创建日期 2007-Sep-18
 * 
 * @author 王晖 模板设置
 */
public class KmImeetingTopicCategory extends SysSimpleCategoryAuthTmpModel implements
		ISysWfTemplateModel, IAttachment, InterceptFieldEnabled,
		ISysTagTemplateModel,ISysNumberModel {

	public KmImeetingTopicCategory() {
		super();
	}

	/*
	 * 多对多关联 辅类别
	 */
	protected List docProperties = new ArrayList();

	public List getDocProperties() {
		return docProperties;
	}

	public void setDocProperties(List fdProperties) {
		this.docProperties = fdProperties;
	}



	@Override
    public Class getFormClass() {
		return KmImeetingTopicCategoryForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());

			toFormPropertyMap.put("docProperties",
					new ModelConvertor_ModelListToString(
							"docPropertyIds:docPropertyNames", "fdId:fdName"));

		}
		return toFormPropertyMap;
	}



	/**
	 * 流程模板
	 */
	private List sysWfTemplateModels;

	@Override
    public List getSysWfTemplateModels() {
		return sysWfTemplateModels;
	}

	@Override
    public void setSysWfTemplateModels(List sysWfTemplateModels) {
		this.sysWfTemplateModels = sysWfTemplateModels;
	}

	// =====附件机制(开始)=====
	protected AutoHashMap attachmentForms = new AutoHashMap(
			AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return attachmentForms;
	}

	// =====附件机制(结束)=====



	// ======标签机制开始 =========
	private List sysTagTemplates;

	@Override
    public List getSysTagTemplates() {
		return sysTagTemplates;
	}

	@Override
    public void setSysTagTemplates(List sysTagTemplates) {
		this.sysTagTemplates = sysTagTemplates;
	}
	// =======标签机制开始==========
	
	// ========== 编号机制 开始==========
	private SysNumberMainMapp sysNumberMainMapp = new SysNumberMainMapp();

	@Override
    public SysNumberMainMapp getSysNumberMainMappModel() {
		return sysNumberMainMapp;
	}

	@Override
    public void setSysNumberMainMappModel(SysNumberMainMapp sysNumberMainMapp1) {
		this.sysNumberMainMapp = sysNumberMainMapp1;
	}
	// ========== 编号机制 结束==========
}
