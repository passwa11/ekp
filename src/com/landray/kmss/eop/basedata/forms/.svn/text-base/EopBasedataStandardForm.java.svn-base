package com.landray.kmss.eop.basedata.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataArea;
import com.landray.kmss.eop.basedata.model.EopBasedataBerth;
import com.landray.kmss.eop.basedata.model.EopBasedataCity;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataLevel;
import com.landray.kmss.eop.basedata.model.EopBasedataSpecialItem;
import com.landray.kmss.eop.basedata.model.EopBasedataStandard;
import com.landray.kmss.eop.basedata.model.EopBasedataVehicle;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 标准数据
  */
public class EopBasedataStandardForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;
    
    private String fdCityId;
    
    private String fdCityName;
    
    private String fdDeptId;
    
    private String fdDeptName;
    
    private String fdPersonId;
    
    private String fdPersonName;
    
    private String fdSpecialItemId;
    
    private String fdSpecialItemName;

    private String fdMoney;

    private String fdIsAvailable;

    private String docCreateTime;

    private String docAlterTime;

    private String fdOrder;

    private String fdLevelId;

    private String fdLevelName;

    private String fdAreaId;

    private String fdAreaName;

    private String fdVehicleId;

    private String fdVehicleName;

    private String fdBerthId;

    private String fdBerthName;

    private String fdItemId;

    private String fdItemName;

    private String docCreatorId;

    private String docCreatorName;

    private String docAlterorId;

    private String docAlterorName;

    private String fdCurrencyId;

    private String fdCurrencyName;

    private String fdCompanyListIds;

    private String fdCompanyListNames;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	fdCityId = null;
    	fdCityName = null;
    	fdDeptId = null;
    	fdDeptName = null;
    	fdPersonId=null;
    	fdPersonName=null;
    	fdSpecialItemId = null;
    	fdSpecialItemName = null;
        fdMoney = null;
        fdIsAvailable = null;
        docCreateTime = null;
        docAlterTime = null;
        fdOrder = null;
        fdLevelId = null;
        fdLevelName = null;
        fdAreaId = null;
        fdAreaName = null;
        fdVehicleId = null;
        fdVehicleName = null;
        fdBerthId = null;
        fdBerthName = null;
        fdItemId = null;
        fdItemName = null;
        docCreatorId = null;
        docCreatorName = null;
        docAlterorId = null;
        docAlterorName = null;
        fdCurrencyId = null;
        fdCurrencyName = null;
        fdCompanyListIds=null;
        fdCompanyListNames = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataStandard> getModelClass() {
        return EopBasedataStandard.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdLevelId", new FormConvertor_IDToModel("fdLevel", EopBasedataLevel.class));
            toModelPropertyMap.put("fdAreaId", new FormConvertor_IDToModel("fdArea", EopBasedataArea.class));
            toModelPropertyMap.put("fdVehicleId", new FormConvertor_IDToModel("fdVehicle", EopBasedataVehicle.class));
            toModelPropertyMap.put("fdBerthId", new FormConvertor_IDToModel("fdBerth", EopBasedataBerth.class));
            toModelPropertyMap.put("fdItemId", new FormConvertor_IDToModel("fdItem", EopBasedataExpenseItem.class));
            toModelPropertyMap.put("fdCurrencyId", new FormConvertor_IDToModel("fdCurrency", EopBasedataCurrency.class));
            toModelPropertyMap.put("fdCompanyListIds", new FormConvertor_IDsToModelList("fdCompanyList", EopBasedataCompany.class));
            toModelPropertyMap.put("fdSpecialItemId", new FormConvertor_IDToModel("fdSpecialItem", EopBasedataSpecialItem.class));
            toModelPropertyMap.put("fdPersonId", new FormConvertor_IDToModel("fdPerson", SysOrgPerson.class));
            toModelPropertyMap.put("fdDeptId", new FormConvertor_IDToModel("fdDept", SysOrgElement.class));
            toModelPropertyMap.put("fdCityId", new FormConvertor_IDToModel("fdCity", EopBasedataCity.class));
        }
        return toModelPropertyMap;
    }

    public String getFdCityId() {
		return fdCityId;
	}

	public void setFdCityId(String fdCityId) {
		this.fdCityId = fdCityId;
	}

	public String getFdCityName() {
		return fdCityName;
	}

	public void setFdCityName(String fdCityName) {
		this.fdCityName = fdCityName;
	}

	public String getFdDeptId() {
		return fdDeptId;
	}

	public void setFdDeptId(String fdDeptId) {
		this.fdDeptId = fdDeptId;
	}

	public String getFdDeptName() {
		return fdDeptName;
	}

	public void setFdDeptName(String fdDeptName) {
		this.fdDeptName = fdDeptName;
	}

	public String getFdPersonId() {
		return fdPersonId;
	}

	public void setFdPersonId(String fdPersonId) {
		this.fdPersonId = fdPersonId;
	}

	public String getFdPersonName() {
		return fdPersonName;
	}

	public void setFdPersonName(String fdPersonName) {
		this.fdPersonName = fdPersonName;
	}

	/**
     * 标准额度
     */
    public String getFdMoney() {
        return this.fdMoney;
    }

    /**
     * 标准额度
     */
    public void setFdMoney(String fdMoney) {
        this.fdMoney = fdMoney;
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
     * 职级
     */
    public String getFdLevelId() {
        return this.fdLevelId;
    }

    /**
     * 职级
     */
    public void setFdLevelId(String fdLevelId) {
        this.fdLevelId = fdLevelId;
    }

    /**
     * 职级
     */
    public String getFdLevelName() {
        return this.fdLevelName;
    }

    /**
     * 职级
     */
    public void setFdLevelName(String fdLevelName) {
        this.fdLevelName = fdLevelName;
    }

    /**
     * 地域
     */
    public String getFdAreaId() {
        return this.fdAreaId;
    }

    /**
     * 地域
     */
    public void setFdAreaId(String fdAreaId) {
        this.fdAreaId = fdAreaId;
    }

    /**
     * 地域
     */
    public String getFdAreaName() {
        return this.fdAreaName;
    }

    /**
     * 地域
     */
    public void setFdAreaName(String fdAreaName) {
        this.fdAreaName = fdAreaName;
    }

    /**
     * 交通工具
     */
    public String getFdVehicleId() {
        return this.fdVehicleId;
    }

    /**
     * 交通工具
     */
    public void setFdVehicleId(String fdVehicleId) {
        this.fdVehicleId = fdVehicleId;
    }

    /**
     * 交通工具
     */
    public String getFdVehicleName() {
        return this.fdVehicleName;
    }

    /**
     * 交通工具
     */
    public void setFdVehicleName(String fdVehicleName) {
        this.fdVehicleName = fdVehicleName;
    }

    /**
     * 舱位
     */
    public String getFdBerthId() {
        return this.fdBerthId;
    }

    /**
     * 舱位
     */
    public void setFdBerthId(String fdBerthId) {
        this.fdBerthId = fdBerthId;
    }

    /**
     * 舱位
     */
    public String getFdBerthName() {
        return this.fdBerthName;
    }

    /**
     * 舱位
     */
    public void setFdBerthName(String fdBerthName) {
        this.fdBerthName = fdBerthName;
    }

    /**
     * 费用类型
     */
    public String getFdItemId() {
        return this.fdItemId;
    }

    /**
     * 费用类型
     */
    public void setFdItemId(String fdItemId) {
        this.fdItemId = fdItemId;
    }

    /**
     * 费用类型
     */
    public String getFdItemName() {
        return this.fdItemName;
    }

    /**
     * 费用类型
     */
    public void setFdItemName(String fdItemName) {
        this.fdItemName = fdItemName;
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
     * 币种
     */
    public String getFdCurrencyId() {
        return this.fdCurrencyId;
    }

    /**
     * 币种
     */
    public void setFdCurrencyId(String fdCurrencyId) {
        this.fdCurrencyId = fdCurrencyId;
    }

    /**
     * 币种
     */
    public String getFdCurrencyName() {
        return this.fdCurrencyName;
    }

    /**
     * 币种
     */
    public void setFdCurrencyName(String fdCurrencyName) {
        this.fdCurrencyName = fdCurrencyName;
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

	public String getFdSpecialItemId() {
		return fdSpecialItemId;
	}

	public void setFdSpecialItemId(String fdSpecialItemId) {
		this.fdSpecialItemId = fdSpecialItemId;
	}

	public String getFdSpecialItemName() {
		return fdSpecialItemName;
	}

	public void setFdSpecialItemName(String fdSpecialItemName) {
		this.fdSpecialItemName = fdSpecialItemName;
	}
}
