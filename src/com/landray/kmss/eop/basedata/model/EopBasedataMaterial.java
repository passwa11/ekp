package com.landray.kmss.eop.basedata.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.eop.basedata.forms.EopBasedataMaterialForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpModel;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ObjectUtil;

/**
  * 物料
  */
public class EopBasedataMaterial extends ExtendAuthTmpModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdSpecs;

    private Integer fdStatus;

    private Double fdPrice;

    private String fdRemarks;

    private EopBasedataMateUnit fdUnit;

    private String fdCode;

    private EopBasedataMateCate fdType;

    private String fdErpCode;
    private EopBasedataMaterial fdParent;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<EopBasedataMaterialForm> getFormClass() {
        return EopBasedataMaterialForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("fdUnit.fdName", "fdUnitName");
            toFormPropertyMap.put("fdUnit.fdId", "fdUnitId");
            toFormPropertyMap.put("fdType.fdName", "fdTypeName");
            toFormPropertyMap.put("fdType.fdId", "fdTypeId");
            toFormPropertyMap.put("fdParent.fdName", "fdParentName");
            toFormPropertyMap.put("fdParent.fdId", "fdParentId");
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
    @Override
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
     * 规格型号
     */
    public String getFdSpecs() {
        return this.fdSpecs;
    }

    /**
     * 规格型号
     */
    public void setFdSpecs(String fdSpecs) {
        this.fdSpecs = fdSpecs;
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
     * 参考价
     */
    public Double getFdPrice() {
        return this.fdPrice;
    }

    /**
     * 参考价
     */
    public void setFdPrice(Double fdPrice) {
        this.fdPrice = fdPrice;
    }

    /**
     * 物料描述
     */
    public String getFdRemarks() {
        return this.fdRemarks;
    }

    /**
     * 物料描述
     */
    public void setFdRemarks(String fdRemarks) {
        this.fdRemarks = fdRemarks;
    }

    /**
     * 单位
     */
    public EopBasedataMateUnit getFdUnit() {
        return this.fdUnit;
    }

    /**
     * 单位
     */
    public void setFdUnit(EopBasedataMateUnit fdUnit) {
        this.fdUnit = fdUnit;
    }

    /**
     * 物料编码
     */
    public String getFdCode() {
        return this.fdCode;
    }

    /**
     * 物料编码
     */
    public void setFdCode(String fdCode) {
        this.fdCode = fdCode;
    }

    /**
     * 物料类别
     */
    public EopBasedataMateCate getFdType() {
        return this.fdType;
    }

    /**
     * 物料类别
     */
    public void setFdType(EopBasedataMateCate fdType) {
        this.fdType = fdType;
    }

    /**
     * erp物料编码
     */
    public String getFdErpCode() {
        return fdErpCode;
    }

    /**
     * erp物料编码
     */
    public void setFdErpCode(String fdErpCode) {
        this.fdErpCode = fdErpCode;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
    /**
     * 父类别名称
     */
    public EopBasedataMaterial getFdParent() {
        return fdParent;
    }
    /**
     * 父类别名称
     */
    public void setFdParent(EopBasedataMaterial parent) {
    	EopBasedataMaterial oldParent = getHbmParent();
        if (!ObjectUtil.equals(oldParent, parent)) {
            ModelUtil.checkTreeCycle(this, parent, "fdParent");
            setHbmParent(parent);
        }
    }
    /**
     * 父类别名称
     */
    public EopBasedataMaterial getHbmParent() {
        return fdParent;
    }
    /**
     * 父类别名称
     */
    public void setHbmParent(EopBasedataMaterial parent) {
        this.fdParent = parent;
    }
}
