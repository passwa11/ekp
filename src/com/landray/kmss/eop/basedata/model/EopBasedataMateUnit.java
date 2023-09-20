package com.landray.kmss.eop.basedata.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.eop.basedata.forms.EopBasedataMateUnitForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpModel;
import com.landray.kmss.util.DateUtil;

/**
  * 物料单位
  */
public class EopBasedataMateUnit extends ExtendAuthTmpModel {
	
	private static final long serialVersionUID = 1L;

	//EOP物料单位有效
	public static final Integer EOP_MATE_UNIT_IS_ENABLED = 0;

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private Integer fdStatus;

    private Integer fdOrder;

    private String fdCode;

    @Override
    public Class<EopBasedataMateUnitForm> getFormClass() {
        return EopBasedataMateUnitForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 单位名称
     */
    @Override
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 单位名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 状态
     */
    public Integer getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 状态
     */
    public void setFdStatus(Integer fdStatus) {
        this.fdStatus = fdStatus;
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
     * 单位编码
     */
    public String getFdCode() {
        return this.fdCode;
    }

    /**
     * 单位编码
     */
    public void setFdCode(String fdCode) {
        this.fdCode = fdCode;
    }
}
