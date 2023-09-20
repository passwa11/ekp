package com.landray.kmss.eop.basedata.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseTreeModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataStandardSchemeForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

/**
  * 标准方案
  */
public class EopBasedataStandardScheme extends BaseTreeModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

	private String fdCode;

    private String fdDimension;
    
    private String fdTarget;

    private String fdType;
    
    private String fdForbid;

    private Integer fdOrder;

    private Boolean fdIsAvailable;

    private Date docCreateTime;

    private Date docAlterTime;

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;

    private List<EopBasedataCompany> fdCompanyList = new ArrayList<EopBasedataCompany>();

    private List<EopBasedataExpenseItem> fdItems = new ArrayList<EopBasedataExpenseItem>();

    @Override
    public Class<EopBasedataStandardSchemeForm> getFormClass() {
        return EopBasedataStandardSchemeForm.class;
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
            toFormPropertyMap.put("fdCompanyList", new ModelConvertor_ModelListToString("fdCompanyListIds:fdCompanyListNames", "fdId:fdName"));
            toFormPropertyMap.put("fdItems", new ModelConvertor_ModelListToString("fdItemIds:fdItemNames", "fdId:fdName"));
			toFormPropertyMap.put("fdParent.fdId", "fdParentId");
			toFormPropertyMap.put("fdParent.fdName", "fdParentName");
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
     * 费用类型
     */
    public List<EopBasedataExpenseItem> getFdItems() {
        return this.fdItems;
    }

    /**
     * 费用类型
     */
    public void setFdItems(List<EopBasedataExpenseItem> fdItems) {
        this.fdItems = fdItems;
    }

	public String getFdTarget() {
		return fdTarget;
	}

	public void setFdTarget(String fdTarget) {
		this.fdTarget = fdTarget;
	}
	/**
	 * 控制规则
	 * @return
	 */
	public String getFdForbid() {
		return fdForbid;
	}
	/**
	 * 控制规则
	 * @param fdForbid
	 */
	public void setFdForbid(String fdForbid) {
		this.fdForbid = fdForbid;
	}

	
}
