package com.landray.kmss.fssc.fee.model;

import java.util.List;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import java.util.ArrayList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.fssc.fee.forms.FsscFeeExpenseItemForm;

/**
  * 申请类型设置
  */
public class FsscFeeExpenseItem extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;
    
    private EopBasedataCompany fdCompany;

    private Boolean fdIsNeedBudget;

    private String fdName;

    private FsscFeeTemplate fdTemplate;

    private List<EopBasedataExpenseItem> fdItemList = new ArrayList<EopBasedataExpenseItem>();

    @Override
    public Class<FsscFeeExpenseItemForm> getFormClass() {
        return FsscFeeExpenseItemForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdTemplate.fdName", "fdTemplateName");
            toFormPropertyMap.put("fdTemplate.fdId", "fdTemplateId");
            toFormPropertyMap.put("fdCompany.fdName", "fdCompanyName");
            toFormPropertyMap.put("fdCompany.fdId", "fdCompanyId");
            toFormPropertyMap.put("fdItemList", new ModelConvertor_ModelListToString("fdItemListIds:fdItemListNames", "fdId:fdName"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    public EopBasedataCompany getFdCompany() {
		return fdCompany;
	}

	public void setFdCompany(EopBasedataCompany fdCompany) {
		this.fdCompany = fdCompany;
	}

	/**
     * 名称
     */
    public String getFdName() {
        return this.fdName;
    }
    /**
     * 是否必须有预算
     */
    public Boolean getFdIsNeedBudget() {
		return fdIsNeedBudget;
	}
    /**
     * 是否必须有预算
     */
	public void setFdIsNeedBudget(Boolean fdIsNeedBudget) {
		this.fdIsNeedBudget = fdIsNeedBudget;
	}

	/**
     * 名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 申请模板
     */
    public FsscFeeTemplate getFdTemplate() {
        return this.fdTemplate;
    }

    /**
     * 申请模板
     */
    public void setFdTemplate(FsscFeeTemplate fdTemplate) {
        this.fdTemplate = fdTemplate;
    }

    /**
     * 对应费用类型
     */
    public List<EopBasedataExpenseItem> getFdItemList() {
        return this.fdItemList;
    }

    /**
     * 对应费用类型
     */
    public void setFdItemList(List<EopBasedataExpenseItem> fdItemList) {
        this.fdItemList = fdItemList;
    }
}
