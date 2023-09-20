package com.landray.kmss.fssc.expense.model;

import java.util.List;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import java.util.Date;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import java.util.ArrayList;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.fssc.expense.forms.FsscExpenseItemConfigForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;

/**
  * 报销类型配置
  */
public class FsscExpenseItemConfig extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Boolean fdIsAvailable;

    private Date docCreateTime;

    private String fdName;

    private Boolean fdIsNeedBudget;

    private EopBasedataCompany fdCompany;

    private FsscExpenseCategory fdCategory;

    private SysOrgPerson docCreator;

    private List<EopBasedataExpenseItem> fdItemList = new ArrayList<EopBasedataExpenseItem>();

    @Override
    public Class<FsscExpenseItemConfigForm> getFormClass() {
        return FsscExpenseItemConfigForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdCompany.fdName", "fdCompanyName");
            toFormPropertyMap.put("fdCompany.fdId", "fdCompanyId");
            toFormPropertyMap.put("fdCategory.fdName", "fdCategoryName");
            toFormPropertyMap.put("fdCategory.fdId", "fdCategoryId");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("fdItemList", new ModelConvertor_ModelListToString("fdItemListIds:fdItemListNames", "fdId:fdName"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
    public Boolean getFdIsNeedBudget() {
        if(fdIsNeedBudget==null) {
            return false;
        }
        return this.fdIsNeedBudget;
    }

    /**
     * 是否必须有预算
     */
    public void setFdIsNeedBudget(Boolean fdIsNeedBudget) {
        this.fdIsNeedBudget = fdIsNeedBudget;
    }

    /**
     * 所属公司
     */
    public EopBasedataCompany getFdCompany() {
        return this.fdCompany;
    }

    /**
     * 所属公司
     */
    public void setFdCompany(EopBasedataCompany fdCompany) {
        this.fdCompany = fdCompany;
    }

    /**
     * 报销类别
     */
    public FsscExpenseCategory getFdCategory() {
        return this.fdCategory;
    }

    /**
     * 报销类别
     */
    public void setFdCategory(FsscExpenseCategory fdCategory) {
        this.fdCategory = fdCategory;
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
     * 费用类型
     */
    public List<EopBasedataExpenseItem> getFdItemList() {
        return this.fdItemList;
    }

    /**
     * 费用类型
     */
    public void setFdItemList(List<EopBasedataExpenseItem> fdItemList) {
        this.fdItemList = fdItemList;
    }
}
