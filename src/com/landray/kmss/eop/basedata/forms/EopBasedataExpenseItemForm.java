package com.landray.kmss.eop.basedata.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 费用类型
  */
public class EopBasedataExpenseItemForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdTripType;

    private String fdDayCalType;

    private String fdCode;

    private String fdIsAvailable;

    private String docCreateTime;

    private String docAlterTime;

    private String fdOrder;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;

	protected String fdParentId;

	protected String fdParentName;

    private String fdCompanyListIds;

    private String fdCompanyListNames;

    private String fdBudgetItemIds;

    private String fdBudgetItemNames;

    private String fdAccountIds;

    private String fdAccountNames;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdTripType = null;
        fdDayCalType = null;
        fdCode = null;
        fdIsAvailable = null;
        docCreateTime = null;
        docAlterTime = null;
        fdOrder = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
		fdParentId = null;
		fdParentName = null;
        fdCompanyListIds=null;
        fdCompanyListNames = null;
        fdBudgetItemIds = null;
        fdBudgetItemNames = null;
        fdAccountIds = null;
        fdAccountNames = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataExpenseItem> getModelClass() {
        return EopBasedataExpenseItem.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
			toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel("fdParent", EopBasedataExpenseItem.class));
            toModelPropertyMap.put("fdCompanyListIds", new FormConvertor_IDsToModelList("fdCompanyList", EopBasedataCompany.class));
            toModelPropertyMap.put("fdBudgetItemIds", new FormConvertor_IDsToModelList("fdBudgetItems", EopBasedataBudgetItem.class));
            toModelPropertyMap.put("fdAccountIds", new FormConvertor_IDsToModelList("fdAccounts", EopBasedataAccounts.class));
        }
        return toModelPropertyMap;
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
     * 排序号
     */
    public String getFdOrder() {
        return this.fdOrder;
    }

    /**
     * 排序号
     */
    public void setFdOrder(String fdOrder) {
        this.fdOrder = fdOrder;
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
	 * @return 所属上级的ID
	 */
	public String getFdParentId() {
		return fdParentId;
	}

	/**
	 * @param fdParentId
	 *            所属上级的ID
	 */
	public void setFdParentId(String fdParentId) {
		this.fdParentId = fdParentId;
	}

	/**
	 * 所属上级的名称
	 */

	/**
	 * @return 所属上级的名称
	 */
	public String getFdParentName() {
		return fdParentName;
	}

	/**
	 * @param fdParentName
	 *            所属上级的名称
	 */
	public void setFdParentName(String fdParentName) {
		this.fdParentName = fdParentName;
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
     * 对应预算科目
     */
    public String getFdBudgetItemIds() {
        return this.fdBudgetItemIds;
    }

    /**
     * 对应预算科目
     */
    public void setFdBudgetItemIds(String fdBudgetItemIds) {
        this.fdBudgetItemIds = fdBudgetItemIds;
    }

    /**
     * 对应预算科目
     */
    public String getFdBudgetItemNames() {
        return this.fdBudgetItemNames;
    }

    /**
     * 对应预算科目
     */
    public void setFdBudgetItemNames(String fdBudgetItemNames) {
        this.fdBudgetItemNames = fdBudgetItemNames;
    }

    /**
     * 对应会计科目
     */
    public String getFdAccountIds() {
        return this.fdAccountIds;
    }

    /**
     * 对应会计科目
     */
    public void setFdAccountIds(String fdAccountIds) {
        this.fdAccountIds = fdAccountIds;
    }

    /**
     * 对应会计科目
     */
    public String getFdAccountNames() {
        return this.fdAccountNames;
    }

    /**
     * 对应会计科目
     */
    public void setFdAccountNames(String fdAccountNames) {
        this.fdAccountNames = fdAccountNames;
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
