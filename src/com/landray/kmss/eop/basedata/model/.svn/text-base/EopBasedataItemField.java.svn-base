package com.landray.kmss.eop.basedata.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataItemFieldForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

/**
  * 费用类型-显示字段维护
  */
public class EopBasedataItemField extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdFields;

    private String fdFieldOne;

    private Boolean fdRequiredOne;

    private String fdFieldTwo;

    private Boolean fdRequiredTwo;

    private String fdFiledThree;

    private Boolean fdRequiredThree;

    private String fdFieldFour;

    private Boolean fdRequiredFour;

    private String fdFieldFive;

    private Boolean fdRequiredFive;

    private Date docCreateTime;

    private Date docAlterTime;

    private List<EopBasedataCompany> fdCompanyList = new ArrayList<EopBasedataCompany>();

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;

    private List<EopBasedataExpenseItem> fdItems = new ArrayList<EopBasedataExpenseItem>();

    @Override
    public Class<EopBasedataItemFieldForm> getFormClass() {
        return EopBasedataItemFieldForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdCompanyList", new ModelConvertor_ModelListToString("fdCompanyListIds:fdCompanyListNames", "fdId:fdName"));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
            toFormPropertyMap.put("fdItems", new ModelConvertor_ModelListToString("fdItemIds:fdItemNames", "fdId:fdName"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 显示字段
     */
    public String getFdFields() {
        return this.fdFields;
    }

    /**
     * 显示字段
     */
    public void setFdFields(String fdFields) {
        this.fdFields = fdFields;
    }

    /**
     * 预留字段1
     */
    public String getFdFieldOne() {
        return this.fdFieldOne;
    }

    /**
     * 预留字段1
     */
    public void setFdFieldOne(String fdFieldOne) {
        this.fdFieldOne = fdFieldOne;
    }

    /**
     * 是否必填1
     */
    public Boolean getFdRequiredOne() {
        return this.fdRequiredOne;
    }

    /**
     * 是否必填1
     */
    public void setFdRequiredOne(Boolean fdRequiredOne) {
        this.fdRequiredOne = fdRequiredOne;
    }

    /**
     * 预留字段2
     */
    public String getFdFieldTwo() {
        return this.fdFieldTwo;
    }

    /**
     * 预留字段2
     */
    public void setFdFieldTwo(String fdFieldTwo) {
        this.fdFieldTwo = fdFieldTwo;
    }

    /**
     * 是否必填2
     */
    public Boolean getFdRequiredTwo() {
        return this.fdRequiredTwo;
    }

    /**
     * 是否必填2
     */
    public void setFdRequiredTwo(Boolean fdRequiredTwo) {
        this.fdRequiredTwo = fdRequiredTwo;
    }

    /**
     * 预留字段3
     */
    public String getFdFiledThree() {
        return this.fdFiledThree;
    }

    /**
     * 预留字段3
     */
    public void setFdFiledThree(String fdFiledThree) {
        this.fdFiledThree = fdFiledThree;
    }

    /**
     * 是否必填3
     */
    public Boolean getFdRequiredThree() {
        return this.fdRequiredThree;
    }

    /**
     * 是否必填3
     */
    public void setFdRequiredThree(Boolean fdRequiredThree) {
        this.fdRequiredThree = fdRequiredThree;
    }

    /**
     * 预留字段4
     */
    public String getFdFieldFour() {
        return this.fdFieldFour;
    }

    /**
     * 预留字段4
     */
    public void setFdFieldFour(String fdFieldFour) {
        this.fdFieldFour = fdFieldFour;
    }

    /**
     * 是否必填4
     */
    public Boolean getFdRequiredFour() {
        return this.fdRequiredFour;
    }

    /**
     * 是否必填4
     */
    public void setFdRequiredFour(Boolean fdRequiredFour) {
        this.fdRequiredFour = fdRequiredFour;
    }

    /**
     * 预留字段5
     */
    public String getFdFieldFive() {
        return this.fdFieldFive;
    }

    /**
     * 预留字段5
     */
    public void setFdFieldFive(String fdFieldFive) {
        this.fdFieldFive = fdFieldFive;
    }

    /**
     * 是否必填5
     */
    public Boolean getFdRequiredFive() {
        return this.fdRequiredFive;
    }

    /**
     * 是否必填5
     */
    public void setFdRequiredFive(Boolean fdRequiredFive) {
        this.fdRequiredFive = fdRequiredFive;
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
     * 费用类型
     */
    public List<EopBasedataExpenseItem> getFdItems() {
        return this.fdItems;
    }

    /**
     * 费用类型
     */
    public void setFdItems(List<EopBasedataExpenseItem> fdItems) {
        this.fdItems = fdItems;
    }
}
