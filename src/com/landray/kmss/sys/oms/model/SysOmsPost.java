package com.landray.kmss.sys.oms.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.sys.oms.forms.SysOmsPostForm;
import com.landray.kmss.util.DateUtil;

/**
  * 岗位
  */
public class SysOmsPost extends SysOmsElement implements InterceptFieldEnabled {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdPersons;

    @Override
    public Class<SysOmsPostForm> getFormClass() {
        return SysOmsPostForm.class;
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
     * 岗位成员
     */
    public String getFdPersons() {
        return (String) readLazyField("fdPersons", this.fdPersons);
    }

    /**
     * 岗位成员
     */
    public void setFdPersons(String fdPersons) {
        this.fdPersons = (String) writeLazyField("fdPersons", this.fdPersons, fdPersons);
    }

	@Override
    public Integer getOrgType() {
		return 4;
	}

}
