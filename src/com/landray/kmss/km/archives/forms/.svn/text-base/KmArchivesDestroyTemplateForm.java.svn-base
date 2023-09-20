package com.landray.kmss.km.archives.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.archives.model.KmArchivesDestroyTemplate;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmTemplateForm;
import com.landray.kmss.sys.lbpmservice.support.forms.LbpmTemplateForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 借阅申请模板
  */
public class KmArchivesDestroyTemplateForm extends ExtendAuthForm
		implements ISysLbpmTemplateForm {

    private static FormToModelPropertyMap toModelPropertyMap;

	private String fdName;

	private String docCreateTime;

    private String docCreatorId;

	private String docCreatorName;
	
	private String fdDefaultFlag = null;

    private AutoHashMap sysWfTemplateForms = new AutoHashMap(LbpmTemplateForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docCreatorId = null;
		fdName = null;
		docCreateTime = null;
		docCreatorName = null;
		fdDefaultFlag = null;
        sysWfTemplateForms.clear();
        super.reset(mapping, request);
    }

	@Override
    public Class<KmArchivesDestroyTemplate> getModelClass() {
		return KmArchivesDestroyTemplate.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("authReaderIds", new FormConvertor_IDsToModelList("authReaders", SysOrgElement.class));
            toModelPropertyMap.put("authEditorIds", new FormConvertor_IDsToModelList("authEditors", SysOrgElement.class));
        }
        return toModelPropertyMap;
    }

    /**
	 * 名称
	 */
	public String getFdName() {
		return this.fdName;
	}

	/**
	 * 名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 创建时间
	 */
	public String getDocCreateTime() {
		return this.docCreateTime;
	}

	/**
	 * 创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
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

	/**
	 * 创建人
	 */
	public String getDocCreatorName() {
		return this.docCreatorName;
	}

	/**
	 * 创建人
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

    @Override
    public AutoHashMap getSysWfTemplateForms() {
        return sysWfTemplateForms;
    }

	/*
	 * 所有人不可阅读标记
	 */
	protected String authNotReaderFlag = "false";

	public String getAuthNotReaderFlag() {
		return authNotReaderFlag;
	}

	public void setAuthNotReaderFlag(String authNotReaderFlag) {
		this.authNotReaderFlag = authNotReaderFlag;
	}

	/**
	 * 默认标记（0、非；1、是）
	 */
	public String getFdDefaultFlag() {
		return fdDefaultFlag;
	}

	/**
	 * 默认标记（0、非；1、是）
	 */
	public void setFdDefaultFlag(String fdDefaultFlag) {
		this.fdDefaultFlag = fdDefaultFlag;
	}
}
