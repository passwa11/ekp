package com.landray.kmss.eop.basedata.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.eop.basedata.model.EopBasedataAuthorize;

/**
  * 提单转授权
  */
public class EopBasedataAuthorizeForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdDesc;

    private String fdIsAvailable;

    private String docCreateTime;

    private String docAlterTime;

    private String fdAuthorizedById;

    private String fdAuthorizedByName;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;

    private String fdToOrgIds;

    private String fdToOrgNames;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdDesc = null;
        fdIsAvailable = null;
        docCreateTime = null;
        docAlterTime = null;
        fdAuthorizedById = null;
        fdAuthorizedByName = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
        fdToOrgIds = null;
        fdToOrgNames = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataAuthorize> getModelClass() {
        return EopBasedataAuthorize.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdAuthorizedById", new FormConvertor_IDToModel("fdAuthorizedBy", SysOrgPerson.class));
            toModelPropertyMap.put("fdToOrgIds", new FormConvertor_IDsToModelList("fdToOrg", SysOrgPerson.class));
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
     * 授权人
     */
    public String getFdAuthorizedById() {
        return this.fdAuthorizedById;
    }

    /**
     * 授权人
     */
    public void setFdAuthorizedById(String fdAuthorizedById) {
        this.fdAuthorizedById = fdAuthorizedById;
    }

    /**
     * 授权人
     */
    public String getFdAuthorizedByName() {
        return this.fdAuthorizedByName;
    }

    /**
     * 授权人
     */
    public void setFdAuthorizedByName(String fdAuthorizedByName) {
        this.fdAuthorizedByName = fdAuthorizedByName;
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
     * 被授权人
     */
    public String getFdToOrgIds() {
        return this.fdToOrgIds;
    }

    /**
     * 被授权人
     */
    public void setFdToOrgIds(String fdToOrgIds) {
        this.fdToOrgIds = fdToOrgIds;
    }

    /**
     * 被授权人
     */
    public String getFdToOrgNames() {
        return this.fdToOrgNames;
    }

    /**
     * 被授权人
     */
    public void setFdToOrgNames(String fdToOrgNames) {
        this.fdToOrgNames = fdToOrgNames;
    }
}
