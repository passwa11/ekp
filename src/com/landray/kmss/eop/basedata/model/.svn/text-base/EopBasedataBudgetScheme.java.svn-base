package com.landray.kmss.eop.basedata.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataBudgetSchemeForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

/**
  * 预算方案
  */
public class EopBasedataBudgetScheme extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

	private String fdCode;

    private Boolean fdIsAvailable;

    private String fdDimension;

    private String fdType;

    private String fdTarget;

    private String fdPeriod;

    private Integer fdOrder;

    private Date docCreateTime;

    private Date docAlterTime;

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;
    
    private List<EopBasedataCompany> fdCompanys = new ArrayList<EopBasedataCompany>();

    @Override
    public Class<EopBasedataBudgetSchemeForm> getFormClass() {
        return EopBasedataBudgetSchemeForm.class;
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
            toFormPropertyMap.put("fdCompanys", new ModelConvertor_ModelListToString("fdCompanyIds:fdCompanyNames", "fdId:fdName"));
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
     * 公司
     */
	
	public List<EopBasedataCompany> getFdCompanys() {
		return fdCompanys;
	}
	
	 /**
     * 公司
     */
	
	public void setFdCompanys(List<EopBasedataCompany> fdCompanys) {
		this.fdCompanys = fdCompanys;
	}
    
}
