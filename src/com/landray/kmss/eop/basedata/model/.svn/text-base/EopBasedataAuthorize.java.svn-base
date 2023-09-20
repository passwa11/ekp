package com.landray.kmss.eop.basedata.model;

import java.util.ArrayList;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import java.util.Date;
import java.util.List;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.eop.basedata.forms.EopBasedataAuthorizeForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;

/**
  * 提单转授权
  */
public class EopBasedataAuthorize extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdDesc;

    private Boolean fdIsAvailable;

    private Date docCreateTime;

    private Date docAlterTime;

    private SysOrgPerson fdAuthorizedBy;

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;

    private List<SysOrgPerson> fdToOrg = new ArrayList<SysOrgPerson>();

    @Override
    public Class<EopBasedataAuthorizeForm> getFormClass() {
        return EopBasedataAuthorizeForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdAuthorizedBy.fdName", "fdAuthorizedByName");
            toFormPropertyMap.put("fdAuthorizedBy.fdId", "fdAuthorizedById");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
            toFormPropertyMap.put("fdToOrg", new ModelConvertor_ModelListToString("fdToOrgIds:fdToOrgNames", "fdId:fdName"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
    public Boolean getFdIsAvailable() {
        return this.fdIsAvailable;
    }

    /**
     * 是否有效
     */
    public void setFdIsAvailable(Boolean fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
    }

    /**
     * 创建时间
     */
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 更新时间
     */
    public Date getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(Date docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    /**
     * 授权人
     */
    public SysOrgPerson getFdAuthorizedBy() {
        return this.fdAuthorizedBy;
    }

    /**
     * 授权人
     */
    public void setFdAuthorizedBy(SysOrgPerson fdAuthorizedBy) {
        this.fdAuthorizedBy = fdAuthorizedBy;
    }

    /**
     * 创建人
     */
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * 创建人
     */
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

    /**
     * 修改人
     */
    public SysOrgPerson getDocAlteror() {
        return this.docAlteror;
    }

    /**
     * 修改人
     */
    public void setDocAlteror(SysOrgPerson docAlteror) {
        this.docAlteror = docAlteror;
    }

    /**
     * 被授权人
     */
    public List<SysOrgPerson> getFdToOrg() {
        return this.fdToOrg;
    }

    /**
     * 被授权人
     */
    public void setFdToOrg(List<SysOrgPerson> fdToOrg) {
        this.fdToOrg = fdToOrg;
    }
}
