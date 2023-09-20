package com.landray.kmss.km.archives.model;

import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.km.archives.forms.KmArchivesCategoryForm;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmTemplateModel;
import com.landray.kmss.sys.number.interfaces.ISysNumberModel;
import com.landray.kmss.sys.number.model.SysNumberMainMapp;
import com.landray.kmss.sys.property.interfaces.ISysPropertyTemplate;
import com.landray.kmss.sys.property.model.SysPropertyTemplate;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;
import com.landray.kmss.sys.tag.interfaces.ISysTagTemplateModel;
import com.landray.kmss.util.DateUtil;

/**
  * 档案管理类别
  */
public class KmArchivesCategory extends SysSimpleCategoryAuthTmpModel
		implements ISysLbpmTemplateModel, ISysNumberModel, ISysTagTemplateModel,
		ISysPropertyTemplate {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private List sysWfTemplateModels;

    private SysNumberMainMapp sysNumberMainMappModel = new SysNumberMainMapp();

    private List sysTagTemplates;

    @Override
    public Class<KmArchivesCategoryForm> getFormClass() {
        return KmArchivesCategoryForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.addNoConvertProperty("fdHierarchyId");
            toFormPropertyMap.addNoConvertProperty("authReaderFlag");
            toFormPropertyMap.put("fdParent.fdName", "fdParentName");
            toFormPropertyMap.put("fdParent.fdId", "fdParentId");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("authReaders", new ModelConvertor_ModelListToString("authReaderIds:authReaderNames", "fdId:fdName"));
            toFormPropertyMap.put("authEditors", new ModelConvertor_ModelListToString("authEditorIds:authEditorNames", "fdId:fdName"));
            toFormPropertyMap.put("authTmpReaders", new ModelConvertor_ModelListToString("authTmpReaderIds:authTmpReaderNames", "fdId:fdName"));
            toFormPropertyMap.put("authTmpEditors", new ModelConvertor_ModelListToString("authTmpEditorIds:authTmpEditorNames", "fdId:fdName"));
            toFormPropertyMap.put("authTmpAttCopys", new ModelConvertor_ModelListToString("authTmpAttCopyIds:authTmpAttCopyNames", "fdId:fdName"));
            toFormPropertyMap.put("authTmpAttDownloads", new ModelConvertor_ModelListToString("authTmpAttDownloadIds:authTmpAttDownloadNames", "fdId:fdName"));
            toFormPropertyMap.put("authTmpAttPrints", new ModelConvertor_ModelListToString("authTmpAttPrintIds:authTmpAttPrintNames", "fdId:fdName"));
			toFormPropertyMap.put("sysPropertyTemplate.fdId",
					"fdSysPropTemplateId");
			toFormPropertyMap.put("sysPropertyTemplate.fdName",
					"fdSysPropTemplateName");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    @Override
    public List getSysWfTemplateModels() {
        return this.sysWfTemplateModels;
    }

    @Override
    public void setSysWfTemplateModels(List sysWfTemplateModels) {
        this.sysWfTemplateModels = sysWfTemplateModels;
    }

    @Override
    public SysNumberMainMapp getSysNumberMainMappModel() {
        return this.sysNumberMainMappModel;
    }

    @Override
    public void setSysNumberMainMappModel(SysNumberMainMapp sysNumberMainMappModel) {
        this.sysNumberMainMappModel = sysNumberMainMappModel;
    }

    @Override
    public List getSysTagTemplates() {
        return this.sysTagTemplates;
    }

    @Override
    public void setSysTagTemplates(List sysTagTemplates) {
        this.sysTagTemplates = sysTagTemplates;
    }

	// 属性模板
	SysPropertyTemplate sysPropertyTemplate = null;

	@Override
    public SysPropertyTemplate getSysPropertyTemplate() {
		return sysPropertyTemplate;
	}

	@Override
    public void
			setSysPropertyTemplate(SysPropertyTemplate sysPropertyTemplate) {
		this.sysPropertyTemplate = sysPropertyTemplate;
	}
}
