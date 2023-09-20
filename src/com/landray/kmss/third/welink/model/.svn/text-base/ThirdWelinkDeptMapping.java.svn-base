package com.landray.kmss.third.welink.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.welink.forms.ThirdWelinkDeptMappingForm;
import com.landray.kmss.util.DateUtil;

/**
  * 部门映射
  */
public class ThirdWelinkDeptMapping extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdWelinkId;

    private String fdWelinkName;

    private Date docAlterTime;

	private SysOrgElement fdEkpDept;

	private String fdWelinkDeptId;

    @Override
    public Class<ThirdWelinkDeptMappingForm> getFormClass() {
        return ThirdWelinkDeptMappingForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
			toFormPropertyMap.put("fdEkpDept.fdName", "fdEkpDeptName");
			toFormPropertyMap.put("fdEkpDept.fdId", "fdEkpDeptId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * welink部门ID
     */
    public String getFdWelinkId() {
        return this.fdWelinkId;
    }

    /**
     * welink部门ID
     */
    public void setFdWelinkId(String fdWelinkId) {
        this.fdWelinkId = fdWelinkId;
    }

    /**
     * welink部门名称
     */
    public String getFdWelinkName() {
        return this.fdWelinkName;
    }

    /**
     * welink部门名称
     */
    public void setFdWelinkName(String fdWelinkName) {
        this.fdWelinkName = fdWelinkName;
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

	public SysOrgElement getFdEkpDept() {
		return fdEkpDept;
	}

	public void setFdEkpDept(SysOrgElement fdEkpDept) {
		this.fdEkpDept = fdEkpDept;
	}

	public String getFdWelinkDeptId() {
		return fdWelinkDeptId;
	}

	public void setFdWelinkDeptId(String fdWelinkDeptId) {
		this.fdWelinkDeptId = fdWelinkDeptId;
	}

}
