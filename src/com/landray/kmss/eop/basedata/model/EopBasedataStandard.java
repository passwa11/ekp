package com.landray.kmss.eop.basedata.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.forms.EopBasedataStandardForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

/**
  * 标准数据
  */
public class EopBasedataStandard extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Double fdMoney;

    private Boolean fdIsAvailable;

    private Date docCreateTime;

    private Date docAlterTime;

    private Integer fdOrder;

    private EopBasedataLevel fdLevel;

    private EopBasedataArea fdArea;

    private EopBasedataVehicle fdVehicle;

    private EopBasedataBerth fdBerth;

    private EopBasedataExpenseItem fdItem;
    
    private EopBasedataSpecialItem fdSpecialItem;

    private SysOrgPerson docCreator;

    private SysOrgPerson docAlteror;

    private EopBasedataCurrency fdCurrency;

    private List<EopBasedataCompany> fdCompanyList = new ArrayList<EopBasedataCompany>();
    
    private SysOrgPerson fdPerson;
    
    private SysOrgElement fdDept;
    
    private EopBasedataCity fdCity;

    @Override
    public Class<EopBasedataStandardForm> getFormClass() {
        return EopBasedataStandardForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdLevel.fdName", "fdLevelName");
            toFormPropertyMap.put("fdLevel.fdId", "fdLevelId");
            toFormPropertyMap.put("fdArea.fdArea", "fdAreaName");
            toFormPropertyMap.put("fdArea.fdId", "fdAreaId");
            toFormPropertyMap.put("fdVehicle.fdName", "fdVehicleName");
            toFormPropertyMap.put("fdVehicle.fdId", "fdVehicleId");
            toFormPropertyMap.put("fdBerth.fdName", "fdBerthName");
            toFormPropertyMap.put("fdBerth.fdId", "fdBerthId");
            toFormPropertyMap.put("fdItem.fdName", "fdItemName");
            toFormPropertyMap.put("fdItem.fdId", "fdItemId");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
            toFormPropertyMap.put("fdCurrency.fdName", "fdCurrencyName");
            toFormPropertyMap.put("fdCurrency.fdId", "fdCurrencyId");
            toFormPropertyMap.put("fdCompanyList", new ModelConvertor_ModelListToString("fdCompanyListIds:fdCompanyListNames", "fdId:fdName"));
            toFormPropertyMap.put("fdSpecialItem.fdDescription", "fdSpecialItemName");
            toFormPropertyMap.put("fdSpecialItem.fdId", "fdSpecialItemId");
            toFormPropertyMap.put("fdPerson.fdName", "fdPersonName");
            toFormPropertyMap.put("fdPerson.fdId", "fdPersonId");
            toFormPropertyMap.put("fdDept.fdName", "fdDeptName");
            toFormPropertyMap.put("fdDept.fdId", "fdDeptId");
            toFormPropertyMap.put("fdCity.fdName", "fdCityName");
            toFormPropertyMap.put("fdCity.fdId", "fdCityId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    public EopBasedataCity getFdCity() {
		return fdCity;
	}

	public void setFdCity(EopBasedataCity fdCity) {
		this.fdCity = fdCity;
	}

	public SysOrgElement getFdDept() {
		return fdDept;
	}

	public void setFdDept(SysOrgElement fdDept) {
		this.fdDept = fdDept;
	}

	/**
     * 标准额度
     */
    public Double getFdMoney() {
        return this.fdMoney;
    }

    /**
     * 标准额度
     */
    public void setFdMoney(Double fdMoney) {
        this.fdMoney = fdMoney;
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
     * 职级
     */
    public EopBasedataLevel getFdLevel() {
        return this.fdLevel;
    }

    /**
     * 职级
     */
    public void setFdLevel(EopBasedataLevel fdLevel) {
        this.fdLevel = fdLevel;
    }

    /**
     * 地域
     */
    public EopBasedataArea getFdArea() {
        return this.fdArea;
    }

    /**
     * 地域
     */
    public void setFdArea(EopBasedataArea fdArea) {
        this.fdArea = fdArea;
    }

    /**
     * 交通工具
     */
    public EopBasedataVehicle getFdVehicle() {
        return this.fdVehicle;
    }

    /**
     * 交通工具
     */
    public void setFdVehicle(EopBasedataVehicle fdVehicle) {
        this.fdVehicle = fdVehicle;
    }

    /**
     * 舱位
     */
    public EopBasedataBerth getFdBerth() {
        return this.fdBerth;
    }

    /**
     * 舱位
     */
    public void setFdBerth(EopBasedataBerth fdBerth) {
        this.fdBerth = fdBerth;
    }

    /**
     * 费用类型
     */
    public EopBasedataExpenseItem getFdItem() {
        return this.fdItem;
    }

    /**
     * 费用类型
     */
    public void setFdItem(EopBasedataExpenseItem fdItem) {
        this.fdItem = fdItem;
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
     * 币种
     */
    public EopBasedataCurrency getFdCurrency() {
        return this.fdCurrency;
    }

    /**
     * 币种
     */
    public void setFdCurrency(EopBasedataCurrency fdCurrency) {
        this.fdCurrency = fdCurrency;
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

	public EopBasedataSpecialItem getFdSpecialItem() {
		return fdSpecialItem;
	}

	public void setFdSpecialItem(EopBasedataSpecialItem fdSpecialItem) {
		this.fdSpecialItem = fdSpecialItem;
	}

	public SysOrgPerson getFdPerson() {
		return fdPerson;
	}

	public void setFdPerson(SysOrgPerson fdPerson) {
		this.fdPerson = fdPerson;
	}
}
