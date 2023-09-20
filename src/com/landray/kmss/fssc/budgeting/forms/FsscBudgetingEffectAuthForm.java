package com.landray.kmss.fssc.budgeting.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingEffectAuth;
import com.landray.kmss.common.forms.ExtendForm;

/**
  * 预算生效权限
  */
public class FsscBudgetingEffectAuthForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdDesc;

    private String docCreateTime;

    private String docAlterTime;

    private String fdName;

    private String fdIsAvailable;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;

    private String fdPersonListIds;

    private String fdPersonListNames;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdDesc = null;
        docCreateTime = null;
        docAlterTime = null;
        fdName = null;
        fdIsAvailable = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
        fdPersonListIds = null;
        fdPersonListNames = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscBudgetingEffectAuth> getModelClass() {
        return FsscBudgetingEffectAuth.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdPersonListIds", new FormConvertor_IDsToModelList("fdPersonList", SysOrgPerson.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 说明
     */
    public String getFdDesc() {
        return this.fdDesc;
    }

    /**
     * 说明
     */
    public void setFdDesc(String fdDesc) {
        this.fdDesc = fdDesc;
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
     * 人员
     */
    public String getFdPersonListIds() {
        return this.fdPersonListIds;
    }

    /**
     * 人员
     */
    public void setFdPersonListIds(String fdPersonListIds) {
        this.fdPersonListIds = fdPersonListIds;
    }

    /**
     * 人员
     */
    public String getFdPersonListNames() {
        return this.fdPersonListNames;
    }

    /**
     * 人员
     */
    public void setFdPersonListNames(String fdPersonListNames) {
        this.fdPersonListNames = fdPersonListNames;
    }
}
