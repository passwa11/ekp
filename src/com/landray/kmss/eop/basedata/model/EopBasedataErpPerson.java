package com.landray.kmss.eop.basedata.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataErpPersonForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

/**
  * ERP人员
  */
public class EopBasedataErpPerson extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdCode;
    
    private String fdDeptCode;	//部门编码

    private Boolean fdIsAvailable;

    private Date docCreateTime;

    private Date docAlterTime;

    private SysOrgPerson fdOrg;

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;

    private List<EopBasedataCompany> fdCompanyList = new ArrayList<EopBasedataCompany>();

    @Override
    public Class<EopBasedataErpPersonForm> getFormClass() {
        return EopBasedataErpPersonForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdOrg.fdName", "fdOrgName");
            toFormPropertyMap.put("fdOrg.fdId", "fdOrgId");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
            toFormPropertyMap.put("fdCompanyList", new ModelConvertor_ModelListToString("fdCompanyListIds:fdCompanyListNames", "fdId:fdName"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
     * 人员编码
     */
    public String getFdCode() {
        return this.fdCode;
    }

    /**
     * 人员编码
     */
    public void setFdCode(String fdCode) {
        this.fdCode = fdCode;
    }
    
    /**
     * 部门编码
     */
    public String getFdDeptCode() {
        return this.fdDeptCode;
    }

    /**
     * 部门编码
     */
    public void setFdDeptCode(String fdDeptCode) {
        this.fdDeptCode = fdDeptCode;
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
     * 对应行政人员
     */
    public SysOrgPerson getFdOrg() {
        return this.fdOrg;
    }

    /**
     * 对应行政人员
     */
    public void setFdOrg(SysOrgPerson fdOrg) {
        this.fdOrg = fdOrg;
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
     * 启用公司
     */
    public List<EopBasedataCompany> getFdCompanyList() {
        return this.fdCompanyList;
    }

    /**
     * 启用公司
     */
    public void setFdCompanyList(List<EopBasedataCompany> fdCompanyList) {
        this.fdCompanyList = fdCompanyList;
    }
}
