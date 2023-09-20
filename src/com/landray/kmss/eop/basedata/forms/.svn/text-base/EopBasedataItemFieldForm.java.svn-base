package com.landray.kmss.eop.basedata.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataItemField;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 费用类型-显示字段维护
  */
public class EopBasedataItemFieldForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdFields;

    private String fdFieldOne;

    private String fdRequiredOne;

    private String fdFieldTwo;

    private String fdRequiredTwo;

    private String fdFiledThree;

    private String fdRequiredThree;

    private String fdFieldFour;

    private String fdRequiredFour;

    private String fdFieldFive;

    private String fdRequiredFive;

    private String docCreateTime;

    private String docAlterTime;

    private String fdCompanyListIds;

    private String fdCompanyListNames;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;

    private String fdItemIds;

    private String fdItemNames;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdFields = null;
        fdFieldOne = null;
        fdRequiredOne = null;
        fdFieldTwo = null;
        fdRequiredTwo = null;
        fdFiledThree = null;
        fdRequiredThree = null;
        fdFieldFour = null;
        fdRequiredFour = null;
        fdFieldFive = null;
        fdRequiredFive = null;
        docCreateTime = null;
        docAlterTime = null;
        fdCompanyListIds=null;
        fdCompanyListNames = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
        fdItemIds = null;
        fdItemNames = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataItemField> getModelClass() {
        return EopBasedataItemField.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdCompanyListIds", new FormConvertor_IDsToModelList("fdCompanyList", EopBasedataCompany.class));
            toModelPropertyMap.put("fdItemIds", new FormConvertor_IDsToModelList("fdItems", EopBasedataExpenseItem.class));
        }
        return toModelPropertyMap;
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
    public String getFdRequiredOne() {
        return this.fdRequiredOne;
    }

    /**
     * 是否必填1
     */
    public void setFdRequiredOne(String fdRequiredOne) {
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
    public String getFdRequiredTwo() {
        return this.fdRequiredTwo;
    }

    /**
     * 是否必填2
     */
    public void setFdRequiredTwo(String fdRequiredTwo) {
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
    public String getFdRequiredThree() {
        return this.fdRequiredThree;
    }

    /**
     * 是否必填3
     */
    public void setFdRequiredThree(String fdRequiredThree) {
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
    public String getFdRequiredFour() {
        return this.fdRequiredFour;
    }

    /**
     * 是否必填4
     */
    public void setFdRequiredFour(String fdRequiredFour) {
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
    public String getFdRequiredFive() {
        return this.fdRequiredFive;
    }

    /**
     * 是否必填5
     */
    public void setFdRequiredFive(String fdRequiredFive) {
        this.fdRequiredFive = fdRequiredFive;
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
     * 费用类型
     */
    public String getFdItemIds() {
        return this.fdItemIds;
    }

    /**
     * 费用类型
     */
    public void setFdItemIds(String fdItemIds) {
        this.fdItemIds = fdItemIds;
    }

    /**
     * 费用类型
     */
    public String getFdItemNames() {
        return this.fdItemNames;
    }

    /**
     * 费用类型
     */
    public void setFdItemNames(String fdItemNames) {
        this.fdItemNames = fdItemNames;
    }
}
