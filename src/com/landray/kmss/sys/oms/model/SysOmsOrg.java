package com.landray.kmss.sys.oms.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.sys.oms.forms.SysOmsOrgForm;
import com.landray.kmss.util.DateUtil;

/**
  * 机构
  */
public class SysOmsOrg extends SysOmsElement implements InterceptFieldEnabled {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdThisLeader;

    private String fdSuperLeader;

    private String fdAuthAdmins;

    private String fdViewRange;

    /**
     *组织隐藏范围
     */
    private String fdHideRange;


    @Override
    public Class<SysOmsOrgForm> getFormClass() {
        return SysOmsOrgForm.class;
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
     * 本级领导
     */
    @Override
    public String getFdThisLeader() {
        return this.fdThisLeader;
    }

    /**
     * 本级领导
     */
    @Override
    public void setFdThisLeader(String fdThisLeader) {
        this.fdThisLeader = fdThisLeader;
    }

    /**
     * 上级领导
     */
    @Override
    public String getFdSuperLeader() {
        return this.fdSuperLeader;
    }

    /**
     * 上级领导
     */
    @Override
    public void setFdSuperLeader(String fdSuperLeader) {
        this.fdSuperLeader = fdSuperLeader;
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
		return 1;
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
