package com.landray.kmss.eop.basedata.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 项目
  */
public class EopBasedataProjectForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdCode;

    private String fdIsAvailable;

    private String docCreateTime;

    private String docAlterTime;
    
    private String fdType;

    private String fdCompanyListIds;

    private String fdCompanyListNames;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;

	protected String fdParentId;

	protected String fdParentName;
	
	protected String fdProjectManagerId;
	
	protected String fdProjectManagerName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdCode = null;
        fdIsAvailable = null;
        docCreateTime = null;
        docAlterTime = null;
        fdType=null;
        fdCompanyListIds=null;
        fdCompanyListNames = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
		fdParentId = null;
		fdParentName = null;
		fdProjectManagerId=null;
		fdProjectManagerName=null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataProject> getModelClass() {
        return EopBasedataProject.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdCompanyListIds", new FormConvertor_IDsToModelList("fdCompanyList", EopBasedataCompany.class));
			toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel("fdParent", EopBasedataProject.class));
			toModelPropertyMap.put("fdProjectManagerId", new FormConvertor_IDToModel("fdProjectManager", SysOrgPerson.class));
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
     * 编码
     */
    public String getFdCode() {
        return this.fdCode;
    }

    /**
     * 编码
     */
    public void setFdCode(String fdCode) {
        this.fdCode = fdCode;
    }

    /**
     * 是否有效
     */
    public String getFdIsAvailable() {
        return this.fdIsAvailable;
    }

    /**
     * 是否有效
     */
    public void setFdIsAvailable(String fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
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
     * 更新时间
     */
    public String getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(String docAlterTime) {
        this.docAlterTime = docAlterTime;
    }
    
    
    /**
     * 项目类型  1：核算项目，2：预算项目
     */
    public String getFdType() {
		return fdType;
	}

    /**
     * 项目类型  1：核算项目，2：预算项目
     */
	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	/**
     * 启用公司
     */
    public String getFdCompanyListIds() {
        return this.fdCompanyListIds;
    }

    /**
     * 启用公司
     */
    public void setFdCompanyListIds(String fdCompanyListIds) {
        this.fdCompanyListIds = fdCompanyListIds;
    }

    /**
     * 启用公司
     */
    public String getFdCompanyListNames() {
        return this.fdCompanyListNames;
    }

    /**
     * 启用公司
     */
    public void setFdCompanyListNames(String fdCompanyListNames) {
        this.fdCompanyListNames = fdCompanyListNames;
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

    /**
     * 修改人
     */
    public String getDocAlterorId() {
        return this.docAlterorId;
    }

    /**
     * 修改人
     */
    public void setDocAlterorId(String docAlterorId) {
        this.docAlterorId = docAlterorId;
    }

    /**
     * 修改人
     */
    public String getDocAlterorName() {
        return this.docAlterorName;
    }

    /**
     * 修改人
     */
    public void setDocAlterorName(String docAlterorName) {
        this.docAlterorName = docAlterorName;
    }

	/**
	 * @return 所属上级的ID
	 */
	public String getFdParentId() {
		return fdParentId;
	}

	/**
	 * @param fdParentId
	 *            所属上级的ID
	 */
	public void setFdParentId(String fdParentId) {
		this.fdParentId = fdParentId;
	}

	/**
	 * 所属上级的名称
	 */
	public String getFdParentName() {
		return fdParentName;
	}

	/**
	 * @param fdParentName
	 *            所属上级的名称
	 */
	public void setFdParentName(String fdParentName) {
		this.fdParentName = fdParentName;
	}

	/**
	 * 项目经理ID
	 */
	public String getFdProjectManagerId() {
		return fdProjectManagerId;
	}
	/**
	 * 项目经理ID
	 * @param fdProjectManagerId
	 */
	public void setFdProjectManagerId(String fdProjectManagerId) {
		this.fdProjectManagerId = fdProjectManagerId;
	}
	/**
	 * 项目经理名称
	 */
	public String getFdProjectManagerName() {
		return fdProjectManagerName;
	}
	/**
	 * 项目经理名称
	 * @param fdProjectManagerId
	 */
	public void setFdProjectManagerName(String fdProjectManagerName) {
		this.fdProjectManagerName = fdProjectManagerName;
	}
	
	
}
