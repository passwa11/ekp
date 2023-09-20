package com.landray.kmss.km.archives.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.archives.model.KmArchivesDense;
import com.landray.kmss.km.archives.model.KmArchivesTemplate;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmTemplateForm;
import com.landray.kmss.sys.lbpmservice.support.forms.LbpmTemplateForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 借阅申请模板
  */
public class KmArchivesTemplateForm extends ExtendAuthForm
		implements ISysLbpmTemplateForm {

    private static FormToModelPropertyMap toModelPropertyMap;

	private String fdName;

	private String docCreateTime;

    private String docCreatorId;
    
    private String fdDefaultFlag = null;

	private String docCreatorName;
	
	private String listDenseLevelIds;
	
	private String listDenseLevelNames;
	   
    private AutoHashMap sysWfTemplateForms = new AutoHashMap(LbpmTemplateForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docCreatorId = null;
		fdName = null;
		fdDefaultFlag = null;
		docCreateTime = null;
		docCreatorName = null;
		listDenseLevelIds =null;
		listDenseLevelNames = null;
        sysWfTemplateForms.clear();
        super.reset(mapping, request);
    }

    @Override
    public Class<KmArchivesTemplate> getModelClass() {
        return KmArchivesTemplate.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("authReaderIds", new FormConvertor_IDsToModelList("authReaders", SysOrgElement.class));
            toModelPropertyMap.put("authEditorIds", new FormConvertor_IDsToModelList("authEditors", SysOrgElement.class));
            toModelPropertyMap.put("listDenseLevelIds", new FormConvertor_IDsToModelList("listDenseLevel",KmArchivesDense.class));
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
    
    /**
     * 密级程度
     */
    public String getListDenseLevelIds() {
		return listDenseLevelIds;
	}

    /**
     * 密级程度
     */
	public void setListDenseLevelIds(String listDenseLevelIds) {
		this.listDenseLevelIds = listDenseLevelIds;
	}

	/**
     * 密级程度
     */
	public String getListDenseLevelNames() {
		return listDenseLevelNames;
	}

	/**
     * 密级程度
     */
	public void setListDenseLevelNames(String listDenseLevelNames) {
		this.listDenseLevelNames = listDenseLevelNames;
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
