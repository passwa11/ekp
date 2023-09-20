package com.landray.kmss.sys.oms.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.sys.oms.forms.SysOmsGroupForm;
import com.landray.kmss.util.DateUtil;

/**
  * 群组
  */
public class SysOmsGroup extends SysOmsElement
		implements InterceptFieldEnabled {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdMembers;

    @Override
    public Class<SysOmsGroupForm> getFormClass() {
        return SysOmsGroupForm.class;
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
     * 群组成员
     */
    public String getFdMembers() {
        return (String) readLazyField("fdMembers", this.fdMembers);
    }

    /**
     * 群组成员
     */
    public void setFdMembers(String fdMembers) {
        this.fdMembers = (String) writeLazyField("fdMembers", this.fdMembers, fdMembers);
    }

	@Override
    public Integer getOrgType() {
		return 16;
	}
}
