package com.landray.kmss.fssc.budgeting.forms;

import javax.servlet.http.HttpServletRequest;


import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingOrg;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 开启预算编制
  */
public class FsscBudgetingOrgForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String docCreateTime;

    private String docCreatorId;

    private String docCreatorName;

    private String fdOrgIds;

    private String fdOrgNames;
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        docCreateTime = null;
        docCreatorId = null;
        docCreatorName = null;
        fdOrgIds = null;
        fdOrgNames = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscBudgetingOrg> getModelClass() {
        return FsscBudgetingOrg.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdOrgIds", new FormConvertor_IDsToModelList("fdOrgs", SysOrgPerson.class));
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

    /**
     * 预算编制员工表
     */
    public String getFdOrgIds() {
        return this.fdOrgIds;
    }

    /**
     * 预算编制员工表
     */
    public void setFdOrgIds(String fdOrgIds) {
        this.fdOrgIds = fdOrgIds;
    }

    /**
     * 预算编制员工表
     */
    public String getFdOrgNames() {
        return this.fdOrgNames;
    }

    /**
     * 预算编制员工表
     */
    public void setFdOrgNames(String fdOrgNames) {
        this.fdOrgNames = fdOrgNames;
    }
    
    /*
    * 通知机制
    */
    private String fdNotifyType = null;

    public String getFdNotifyType() {
    	return fdNotifyType;
    }

    public void setFdNotifyType(String fdNotifyType) {
    	this.fdNotifyType = fdNotifyType;
    }

}
