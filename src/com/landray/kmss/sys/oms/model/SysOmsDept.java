package com.landray.kmss.sys.oms.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.sys.oms.forms.SysOmsDeptForm;
import com.landray.kmss.util.DateUtil;

/**
  * 部门
  */
public class SysOmsDept extends SysOmsElement
		implements InterceptFieldEnabled {

    private static ModelToFormPropertyMap toFormPropertyMap;


    //部门管理员
    private String fdAuthAdmins;

    private String fdViewRange;

    /**
     *组织隐藏范围
     */
    private String fdHideRange;

	@Override
    public Class<SysOmsDeptForm> getFormClass() {
        return SysOmsDeptForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdCreateTime", new ModelConvertor_Common("fdCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdAlterTime", new ModelConvertor_Common("fdAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
		// super.recalculateFields();
    }

    /**
     * 管理员
     */
    public String getFdAuthAdmins() {
        return (String) readLazyField("fdAuthAdmins", this.fdAuthAdmins);
    }

    /**
     * 管理员
     */
    public void setFdAuthAdmins(String fdAuthAdmins) {
        this.fdAuthAdmins = (String) writeLazyField("fdAuthAdmins", this.fdAuthAdmins, fdAuthAdmins);
    }

    /**
     * 查看范围
     */
    public String getFdViewRange() {
        return (String) readLazyField("fdViewRange", this.fdViewRange);
    }

    /**
     * 查看范围
     */
    public void setFdViewRange(String fdViewRange) {
        this.fdViewRange = (String) writeLazyField("fdViewRange", this.fdViewRange, fdViewRange);
    }

	@Override
    public Integer getOrgType() {
		return 2;
	}

    /**
     * 隐藏范围
     */
    public String getFdHideRange() {
        return (String) readLazyField("fdHideRange", this.fdHideRange);
    }

    /**
     * 隐藏范围
     */
    public void setFdHideRange(String fdHideRange) {
        this.fdHideRange = (String) writeLazyField("fdHideRange", this.fdHideRange, fdHideRange);
    }
}
