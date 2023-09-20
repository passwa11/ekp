package com.landray.kmss.fssc.expense.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.fssc.expense.model.FsscExpenseItemConfig;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.fssc.expense.model.FsscExpenseCategory;

/**
  * 报销类型配置
  */
public class FsscExpenseItemConfigForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdIsAvailable;

    private String docCreateTime;

    private String fdName;

    private String fdIsNeedBudget;

    private String fdCompanyId;

    private String fdCompanyName;

    private String fdCategoryId;

    private String fdCategoryName;

    private String docCreatorId;

    private String docCreatorName;

    private String fdItemListIds;

    private String fdItemListNames;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdIsAvailable = null;
        docCreateTime = null;
        fdName = null;
        fdIsNeedBudget = null;
        fdCompanyId = null;
        fdCompanyName = null;
        fdCategoryId = null;
        fdCategoryName = null;
        docCreatorId = null;
        docCreatorName = null;
        fdItemListIds = null;
        fdItemListNames = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscExpenseItemConfig> getModelClass() {
        return FsscExpenseItemConfig.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdCompanyId", new FormConvertor_IDToModel("fdCompany", EopBasedataCompany.class));
            toModelPropertyMap.put("fdCategoryId", new FormConvertor_IDToModel("fdCategory", FsscExpenseCategory.class));
            toModelPropertyMap.put("fdItemListIds", new FormConvertor_IDsToModelList("fdItemList", EopBasedataExpenseItem.class));
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
     * 是否必须有预算
     */
    public String getFdIsNeedBudget() {
        return this.fdIsNeedBudget;
    }

    /**
     * 是否必须有预算
     */
    public void setFdIsNeedBudget(String fdIsNeedBudget) {
        this.fdIsNeedBudget = fdIsNeedBudget;
    }

    /**
     * 所属公司
     */
    public String getFdCompanyId() {
        return this.fdCompanyId;
    }

    /**
     * 所属公司
     */
    public void setFdCompanyId(String fdCompanyId) {
        this.fdCompanyId = fdCompanyId;
    }

    /**
     * 所属公司
     */
    public String getFdCompanyName() {
        return this.fdCompanyName;
    }

    /**
     * 所属公司
     */
    public void setFdCompanyName(String fdCompanyName) {
        this.fdCompanyName = fdCompanyName;
    }

    /**
     * 报销类别
     */
    public String getFdCategoryId() {
        return this.fdCategoryId;
    }

    /**
     * 报销类别
     */
    public void setFdCategoryId(String fdCategoryId) {
        this.fdCategoryId = fdCategoryId;
    }

    /**
     * 报销类别
     */
    public String getFdCategoryName() {
        return this.fdCategoryName;
    }

    /**
     * 报销类别
     */
    public void setFdCategoryName(String fdCategoryName) {
        this.fdCategoryName = fdCategoryName;
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
     * 费用类型
     */
    public String getFdItemListIds() {
        return this.fdItemListIds;
    }

    /**
     * 费用类型
     */
    public void setFdItemListIds(String fdItemListIds) {
        this.fdItemListIds = fdItemListIds;
    }

    /**
     * 费用类型
     */
    public String getFdItemListNames() {
        return this.fdItemListNames;
    }

    /**
     * 费用类型
     */
    public void setFdItemListNames(String fdItemListNames) {
        this.fdItemListNames = fdItemListNames;
    }
}
