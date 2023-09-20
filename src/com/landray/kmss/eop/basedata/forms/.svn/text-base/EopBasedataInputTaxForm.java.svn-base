package com.landray.kmss.eop.basedata.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataInputTax;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 进项税率
 */
public class EopBasedataInputTaxForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdIsAvailable;

    private String docCreateTime;

    private String docAlterTime;

    private String fdTaxRate;

    private String fdIsInputTax;

    private String fdDesc;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;

    private String fdItemId;

    private String fdItemName;

    private String fdAccountId;

    private String fdAccountName;

    private String fdCompanyListIds;

    private String fdCompanyListNames;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdIsAvailable = null;
        docCreateTime = null;
        docAlterTime = null;
        fdTaxRate = null;
        fdIsInputTax = null;
        fdDesc = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
        fdItemId = null;
        fdItemName = null;
        fdAccountId = null;
        fdAccountName = null;
        fdCompanyListIds=null;
        fdCompanyListNames = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataInputTax> getModelClass() {
        return EopBasedataInputTax.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdItemId", new FormConvertor_IDToModel("fdItem", EopBasedataExpenseItem.class));
            toModelPropertyMap.put("fdAccountId", new FormConvertor_IDToModel("fdAccount", EopBasedataAccounts.class));
            toModelPropertyMap.put("fdCompanyListIds", new FormConvertor_IDsToModelList("fdCompanyList", EopBasedataCompany.class));
        }
        return toModelPropertyMap;
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
     * 税率
     */
    public String getFdTaxRate() {
        return this.fdTaxRate;
    }

    /**
     * 税率
     */
    public void setFdTaxRate(String fdTaxRate) {
        this.fdTaxRate = fdTaxRate;
    }

    /**
     * 是否进项税抵扣
     */
    public String getFdIsInputTax() {
        return this.fdIsInputTax;
    }

    /**
     * 是否进项税抵扣
     */
    public void setFdIsInputTax(String fdIsInputTax) {
        this.fdIsInputTax = fdIsInputTax;
    }

    /**
     * 描述
     */
    public String getFdDesc() {
        return this.fdDesc;
    }

    /**
     * 描述
     */
    public void setFdDesc(String fdDesc) {
        this.fdDesc = fdDesc;
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
    public String getFdItemId() {
        return this.fdItemId;
    }

    /**
     * 费用类型
     */
    public void setFdItemId(String fdItemId) {
        this.fdItemId = fdItemId;
    }

    /**
     * 费用类型
     */
    public String getFdItemName() {
        return this.fdItemName;
    }

    /**
     * 费用类型
     */
    public void setFdItemName(String fdItemName) {
        this.fdItemName = fdItemName;
    }

    /**
     * 进项税科目
     */
    public String getFdAccountId() {
        return this.fdAccountId;
    }

    /**
     * 进项税科目
     */
    public void setFdAccountId(String fdAccountId) {
        this.fdAccountId = fdAccountId;
    }

    /**
     * 进项税科目
     */
    public String getFdAccountName() {
        return this.fdAccountName;
    }

    /**
     * 进项税科目
     */
    public void setFdAccountName(String fdAccountName) {
        this.fdAccountName = fdAccountName;
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
}
