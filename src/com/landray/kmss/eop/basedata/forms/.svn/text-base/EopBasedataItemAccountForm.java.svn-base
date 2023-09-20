package com.landray.kmss.eop.basedata.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataItemAccount;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 待摊科目映射
  */
public class EopBasedataItemAccountForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdDesc;

    private String fdIsAvailable;

    private String docCreateTime;

    private String docAlterTime;

    private String fdCompanyListIds;

    private String fdCompanyListNames;

    private String fdExpenseItemId;

    private String fdExpenseItemName;

    private String fdAmortizeId;

    private String fdAmortizeName;

    private String fdAccrualsId;

    private String fdAccrualsName;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdDesc = null;
        fdIsAvailable = null;
        docCreateTime = null;
        docAlterTime = null;
        fdCompanyListIds=null;
        fdCompanyListNames = null;
        fdExpenseItemId = null;
        fdExpenseItemName = null;
        fdAmortizeId = null;
        fdAmortizeName = null;
        fdAccrualsId = null;
        fdAccrualsName = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataItemAccount> getModelClass() {
        return EopBasedataItemAccount.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdCompanyListIds", new FormConvertor_IDsToModelList("fdCompanyList", EopBasedataCompany.class));
            toModelPropertyMap.put("fdExpenseItemId", new FormConvertor_IDToModel("fdExpenseItem", EopBasedataExpenseItem.class));
            toModelPropertyMap.put("fdAmortizeId", new FormConvertor_IDToModel("fdAmortize", EopBasedataAccounts.class));
            toModelPropertyMap.put("fdAccrualsId", new FormConvertor_IDToModel("fdAccruals", EopBasedataAccounts.class));
        }
        return toModelPropertyMap;
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
     * 费用类型
     */
    public String getFdExpenseItemId() {
        return this.fdExpenseItemId;
    }

    /**
     * 费用类型
     */
    public void setFdExpenseItemId(String fdExpenseItemId) {
        this.fdExpenseItemId = fdExpenseItemId;
    }

    /**
     * 费用类型
     */
    public String getFdExpenseItemName() {
        return this.fdExpenseItemName;
    }

    /**
     * 费用类型
     */
    public void setFdExpenseItemName(String fdExpenseItemName) {
        this.fdExpenseItemName = fdExpenseItemName;
    }

    /**
     * 待摊科目
     */
    public String getFdAmortizeId() {
        return this.fdAmortizeId;
    }

    /**
     * 待摊科目
     */
    public void setFdAmortizeId(String fdAmortizeId) {
        this.fdAmortizeId = fdAmortizeId;
    }

    /**
     * 待摊科目
     */
    public String getFdAmortizeName() {
        return this.fdAmortizeName;
    }

    /**
     * 待摊科目
     */
    public void setFdAmortizeName(String fdAmortizeName) {
        this.fdAmortizeName = fdAmortizeName;
    }

    /**
     * 预提科目
     */
    public String getFdAccrualsId() {
        return this.fdAccrualsId;
    }

    /**
     * 预提科目
     */
    public void setFdAccrualsId(String fdAccrualsId) {
        this.fdAccrualsId = fdAccrualsId;
    }

    /**
     * 预提科目
     */
    public String getFdAccrualsName() {
        return this.fdAccrualsName;
    }

    /**
     * 预提科目
     */
    public void setFdAccrualsName(String fdAccrualsName) {
        this.fdAccrualsName = fdAccrualsName;
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
}
