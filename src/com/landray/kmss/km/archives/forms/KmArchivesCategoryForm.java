package com.landray.kmss.km.archives.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.archives.model.KmArchivesCategory;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmTemplateForm;
import com.landray.kmss.sys.lbpmservice.support.forms.LbpmTemplateForm;
import com.landray.kmss.sys.number.forms.SysNumberMainMappForm;
import com.landray.kmss.sys.number.interfaces.ISysNumberForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.property.interfaces.ISysPropertyTemplateForm;
import com.landray.kmss.sys.property.model.SysPropertyTemplate;
import com.landray.kmss.sys.simplecategory.forms.SysSimpleCategoryAuthTmpForm;
import com.landray.kmss.sys.tag.forms.SysTagTemplateForm;
import com.landray.kmss.sys.tag.interfaces.ISysTagTemplateForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 档案管理类别
  */
public class KmArchivesCategoryForm extends SysSimpleCategoryAuthTmpForm
		implements ISysLbpmTemplateForm, ISysNumberForm, ISysTagTemplateForm,
		ISysPropertyTemplateForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docCreatorId;

    private AutoHashMap sysWfTemplateForms = new AutoHashMap(LbpmTemplateForm.class);

    private SysNumberMainMappForm sysNumberMainMappForm = new SysNumberMainMappForm();

    private AutoHashMap sysTagTemplateForms = new AutoHashMap(SysTagTemplateForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docCreatorId = null;
        sysWfTemplateForms.clear();
		fdSysPropTemplateId = null;
		fdSysPropTemplateName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<KmArchivesCategory> getModelClass() {
        return KmArchivesCategory.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel("fdParent", KmArchivesCategory.class));
            toModelPropertyMap.put("authReaderIds", new FormConvertor_IDsToModelList("authReaders", SysOrgElement.class));
            toModelPropertyMap.put("authEditorIds", new FormConvertor_IDsToModelList("authEditors", SysOrgElement.class));
            toModelPropertyMap.put("authTmpReaderIds", new FormConvertor_IDsToModelList("authTmpReaders", SysOrgElement.class));
            toModelPropertyMap.put("authTmpEditorIds", new FormConvertor_IDsToModelList("authTmpEditors", SysOrgElement.class));
            toModelPropertyMap.put("authTmpAttCopyIds", new FormConvertor_IDsToModelList("authTmpAttCopys", SysOrgElement.class));
            toModelPropertyMap.put("authTmpAttDownloadIds", new FormConvertor_IDsToModelList("authTmpAttDownloads", SysOrgElement.class));
            toModelPropertyMap.put("authTmpAttPrintIds", new FormConvertor_IDsToModelList("authTmpAttPrints", SysOrgElement.class));
			toModelPropertyMap.put("fdSysPropTemplateId",
					new FormConvertor_IDToModel("sysPropertyTemplate",
							SysPropertyTemplate.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 创建人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 创建人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    @Override
    public AutoHashMap getSysWfTemplateForms() {
        return sysWfTemplateForms;
    }

    @Override
    public SysNumberMainMappForm getSysNumberMainMappForm() {
        return this.sysNumberMainMappForm;
    }

    @Override
    public void setSysNumberMainMappForm(SysNumberMainMappForm sysNumberMainMappForm) {
        this.sysNumberMainMappForm = sysNumberMainMappForm;
    }

    @Override
    public AutoHashMap getSysTagTemplateForms() {
        return sysTagTemplateForms;
    }

	/**
	 * 属性模板id
	 */
	private String fdSysPropTemplateId = null;

	@Override
    public String getFdSysPropTemplateId() {
		return fdSysPropTemplateId;
	}

	@Override
    public void setFdSysPropTemplateId(String fdSysPropTemplateId) {
		this.fdSysPropTemplateId = fdSysPropTemplateId;
	}

	/**
	 * 属性模板name
	 */
	private String fdSysPropTemplateName = null;

	@Override
    public String getFdSysPropTemplateName() {
		return fdSysPropTemplateName;
	}

	@Override
    public void setFdSysPropTemplateName(String fdSysPropTemplateName) {
		this.fdSysPropTemplateName = fdSysPropTemplateName;
	}
}
