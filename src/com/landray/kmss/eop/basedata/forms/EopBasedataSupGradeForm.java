package com.landray.kmss.eop.basedata.forms;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.eop.basedata.model.EopBasedataSupGrade;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpForm;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
  * 供应商等级
  */
public class EopBasedataSupGradeForm extends ExtendAuthTmpForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String docCreateTime;

    private String fdStatus;

    private String fdOrder;

    private String fdCode;

    private String docCreatorId;

    private String docCreatorName;
    
    private String docAlterTime;
    
    private String docAlterorId;

    private String docAlterorName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        docCreateTime = null;
        fdStatus = null;
        fdOrder = null;
        fdCode = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterTime = null;
        docAlterorId = null;
        docAlterorName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataSupGrade> getModelClass() {
        return EopBasedataSupGrade.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
        }
        return toModelPropertyMap;
    }

    /**
     * 供应商等级名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 供应商等级名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 最近更新时间
     */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 最近更新时间
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 状态
     */
    public String getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 状态
     */
    public void setFdStatus(String fdStatus) {
        this.fdStatus = fdStatus;
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
     * 供应商等级编码
     */
    public String getFdCode() {
        return this.fdCode;
    }

    /**
     * 供应商等级编码
     */
    public void setFdCode(String fdCode) {
        this.fdCode = fdCode;
    }

    /**
     * 最近更新人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 最近更新人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 最近更新人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 最近更新人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

	public String getDocAlterTime() {
		return docAlterTime;
	}

	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	public String getDocAlterorId() {
		return docAlterorId;
	}

	public void setDocAlterorId(String docAlterorId) {
		this.docAlterorId = docAlterorId;
	}

	public String getDocAlterorName() {
		return docAlterorName;
	}

	public void setDocAlterorName(String docAlterorName) {
		this.docAlterorName = docAlterorName;
	}
    
    
}
