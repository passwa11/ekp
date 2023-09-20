package com.landray.kmss.eop.basedata.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 预算方案
  */
public class EopBasedataBudgetSchemeForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

	private String fdCode;

    private String fdIsAvailable;

    private String fdDimension;

    private String fdType;

    private String fdTarget;

    private String fdPeriod;

    private String fdOrder;

    private String docCreateTime;

    private String docAlterTime;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;
    
    private String fdCompanyIds;
    
    private String fdCompanyNames;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
		fdCode = null;
        fdIsAvailable = null;
        fdDimension = null;
        fdType = null;
        fdTarget = null;
        fdPeriod = null;
        fdOrder = null;
        docCreateTime = null;
        docAlterTime = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
        fdCompanyIds=null;
        fdCompanyNames=null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataBudgetScheme> getModelClass() {
        return EopBasedataBudgetScheme.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdCompanyIds", new FormConvertor_IDsToModelList("fdCompanys", EopBasedataCompany.class));
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
		return fdCode;
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
     * 维度
     */
    public String getFdDimension() {
        return this.fdDimension;
    }

    /**
     * 维度
     */
    public void setFdDimension(String fdDimension) {
        this.fdDimension = fdDimension;
    }

    /**
     * 控制方式
     */
    public String getFdType() {
        return this.fdType;
    }

    /**
     * 控制方式
     */
    public void setFdType(String fdType) {
        this.fdType = fdType;
    }

    /**
     * 预算对象
     */
    public String getFdTarget() {
        return this.fdTarget;
    }

    /**
     * 预算对象
     */
    public void setFdTarget(String fdTarget) {
        this.fdTarget = fdTarget;
    }

    /**
     * 期间
     */
    public String getFdPeriod() {
        return this.fdPeriod;
    }

    /**
     * 期间
     */
    public void setFdPeriod(String fdPeriod) {
        this.fdPeriod = fdPeriod;
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
     * 公司Ids
     */
	public String getFdCompanyIds() {
		return fdCompanyIds;
	}
	/**
     * 公司Ids
     */
	public void setFdCompanyIds(String fdCompanyIds) {
		this.fdCompanyIds = fdCompanyIds;
	}
	/**
     * 公司names
     */
	public String getFdCompanyNames() {
		return fdCompanyNames;
	}
	/**
     * 公司names
     */
	public void setFdCompanyNames(String fdCompanyNames) {
		this.fdCompanyNames = fdCompanyNames;
	}
    
    
}
