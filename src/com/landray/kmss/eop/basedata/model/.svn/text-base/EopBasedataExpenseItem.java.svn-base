package com.landray.kmss.eop.basedata.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseTreeModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataExpenseItemForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

/**
  * 费用类型
  */
public class EopBasedataExpenseItem extends BaseTreeModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdTripType;

    private String fdDayCalType;

    private String fdCode;

    private Boolean fdIsAvailable;

    private Date docCreateTime;

    private Date docAlterTime;

    private Integer fdOrder;

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;

    private List<EopBasedataCompany> fdCompanyList = new ArrayList<EopBasedataCompany>();

    private List<EopBasedataBudgetItem> fdBudgetItems = new ArrayList<EopBasedataBudgetItem>();

    private List<EopBasedataAccounts> fdAccounts = new ArrayList<EopBasedataAccounts>();

    @Override
    public Class<EopBasedataExpenseItemForm> getFormClass() {
        return EopBasedataExpenseItemForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
			toFormPropertyMap.put("fdParent.fdId", "fdParentId");
			toFormPropertyMap.put("fdParent.fdName", "fdParentName");
			toFormPropertyMap.put("fdCompanyList", new ModelConvertor_ModelListToString("fdCompanyListIds:fdCompanyListNames", "fdId:fdName"));
            toFormPropertyMap.put("fdBudgetItems", new ModelConvertor_ModelListToString("fdBudgetItemIds:fdBudgetItemNames", "fdId:fdName"));
            toFormPropertyMap.put("fdAccounts", new ModelConvertor_ModelListToString("fdAccountIds:fdAccountNames", "fdId:fdName"));
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
     * 编码
     */
    public String getFdCode() {
        return this.fdCode;
    }

    /**
     * 编码
     */
    public void setFdCode(String fdCode) {
        this.fdCode = fdCode;
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
     * 排序号
     */
    public Integer getFdOrder() {
        return this.fdOrder;
    }

    /**
     * 排序号
     */
    public void setFdOrder(Integer fdOrder) {
        this.fdOrder = fdOrder;
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

    /**
     * 对应预算科目
     */
    public List<EopBasedataBudgetItem> getFdBudgetItems() {
        return this.fdBudgetItems;
    }

    /**
     * 对应预算科目
     */
    public void setFdBudgetItems(List<EopBasedataBudgetItem> fdBudgetItems) {
        this.fdBudgetItems = fdBudgetItems;
    }

    /**
     * 对应会计科目
     */
    public List<EopBasedataAccounts> getFdAccounts() {
        return this.fdAccounts;
    }

    /**
     * 对应会计科目
     */
    public void setFdAccounts(List<EopBasedataAccounts> fdAccounts) {
        this.fdAccounts = fdAccounts;
    }
    /**
     * 商旅类型
     */
    public String getFdTripType() {
        return fdTripType;
    }
    /**
     * 商旅类型
     */
    public void setFdTripType(String fdTripType) {
        this.fdTripType = fdTripType;
    }
    /**
     * 天数计算规则
     */
    public String getFdDayCalType() {
        return fdDayCalType;
    }
    /**
     * 天数计算规则
     */
    public void setFdDayCalType(String fdDayCalType) {
        this.fdDayCalType = fdDayCalType;
    }
}
