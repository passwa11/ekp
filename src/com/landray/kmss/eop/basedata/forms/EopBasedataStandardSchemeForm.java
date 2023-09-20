package com.landray.kmss.eop.basedata.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataStandardScheme;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 标准方案
  */
public class EopBasedataStandardSchemeForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;
    
    private String fdForbid;
    
    private String fdTarget;

    private String fdName;

	private String fdCode;

    private String fdDimension;

    private String fdType;

    private String fdOrder;

    private String fdIsAvailable;

    private String docCreateTime;

    private String docAlterTime;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;

    private String fdCompanyListIds;

    private String fdCompanyListNames;

    private String fdItemIds;

    private String fdItemNames;

	protected String fdParentId;

	protected String fdParentName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	fdForbid = null;
    	fdTarget = null;
        fdName = null;
		fdCode = null;
        fdDimension = null;
        fdType = null;
        fdOrder = null;
        fdIsAvailable = null;
        docCreateTime = null;
        docAlterTime = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
        fdCompanyListIds=null;
        fdCompanyListNames = null;
        fdItemIds = null;
        fdItemNames = null;
		fdParentId = null;
		fdParentName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataStandardScheme> getModelClass() {
        return EopBasedataStandardScheme.class;
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
			toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel("fdParent", EopBasedataStandardScheme.class));
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
     * 控制维度
     */
    public String getFdDimension() {
        return this.fdDimension;
    }

    /**
     * 控制维度
     */
    public void setFdDimension(String fdDimension) {
        this.fdDimension = fdDimension;
    }

    /**
     * 控制策略
     */
    public String getFdType() {
        return this.fdType;
    }

    /**
     * 控制策略
     */
    public void setFdType(String fdType) {
        this.fdType = fdType;
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

	public String getFdTarget() {
		return fdTarget;
	}

	public void setFdTarget(String fdTarget) {
		this.fdTarget = fdTarget;
	}

	public String getFdForbid() {
		return fdForbid;
	}

	public void setFdForbid(String fdForbid) {
		this.fdForbid = fdForbid;
	}
}
