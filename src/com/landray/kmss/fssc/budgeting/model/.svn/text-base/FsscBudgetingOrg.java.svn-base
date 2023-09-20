package com.landray.kmss.fssc.budgeting.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.fssc.budgeting.forms.FsscBudgetingOrgForm;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

/**
  * 开启预算编制
  */
public class FsscBudgetingOrg extends BaseModel implements ISysNotifyModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;
    

    private Date docCreateTime;

    private SysOrgPerson docCreator;

    private List<SysOrgPerson> fdOrgs = new ArrayList<SysOrgPerson>();

    @Override
    public Class<FsscBudgetingOrgForm> getFormClass() {
        return FsscBudgetingOrgForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("fdOrgs", new ModelConvertor_ModelListToString("fdOrgIds:fdOrgNames", "fdId:fdName"));
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
     * 预算编制员工表
     */
    public List<SysOrgPerson> getFdOrgs() {
        return this.fdOrgs;
    }

    /**
     * 预算编制员工表
     */
    public void setFdOrgs(List<SysOrgPerson> fdOrgs) {
        this.fdOrgs = fdOrgs;
    }
    
    /*
    * 通知机制方法实现
    */
    private String fdNotifyType;

    public String getFdNotifyType() {
    return fdNotifyType;
    }

    public void setFdNotifyType(java.lang.String fdNotifyType) {
    this.fdNotifyType = fdNotifyType;
    }

}
