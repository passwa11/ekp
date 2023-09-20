package com.landray.kmss.fssc.config.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.fssc.config.forms.FsscConfigListForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;

/**
  * 物资清单
  */
public class FsscConfigList extends BaseModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date docCreateTime;

    private String fdName;

    private String fdCode;

    private String fdGoodsType;

    private String fdGoodsProperty;

    private Integer fdMinNum;

    private String fdSpec;

    private String fdUnit;

    private Double fdPrice;

    private SysOrgPerson docCreator;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    public Class<FsscConfigListForm> getFormClass() {
        return FsscConfigListForm.class;
    }

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

    public void recalculateFields() {
        super.recalculateFields();
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
     * 物资名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 物资名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 物资编码
     */
    public String getFdCode() {
        return this.fdCode;
    }

    /**
     * 物资编码
     */
    public void setFdCode(String fdCode) {
        this.fdCode = fdCode;
    }

    /**
     * 物资类别
     */
    public String getFdGoodsType() {
        return this.fdGoodsType;
    }

    /**
     * 物资类别
     */
    public void setFdGoodsType(String fdGoodsType) {
        this.fdGoodsType = fdGoodsType;
    }

    /**
     * 物资属性
     */
    public String getFdGoodsProperty() {
        return this.fdGoodsProperty;
    }

    /**
     * 物资属性
     */
    public void setFdGoodsProperty(String fdGoodsProperty) {
        this.fdGoodsProperty = fdGoodsProperty;
    }

    /**
     * 最小起订量
     */
    public Integer getFdMinNum() {
        return this.fdMinNum;
    }

    /**
     * 最小起订量
     */
    public void setFdMinNum(Integer fdMinNum) {
        this.fdMinNum = fdMinNum;
    }

    /**
     * 规格
     */
    public String getFdSpec() {
        return this.fdSpec;
    }

    /**
     * 规格
     */
    public void setFdSpec(String fdSpec) {
        this.fdSpec = fdSpec;
    }

    /**
     * 单位
     */
    public String getFdUnit() {
        return this.fdUnit;
    }

    /**
     * 单位
     */
    public void setFdUnit(String fdUnit) {
        this.fdUnit = fdUnit;
    }

    /**
     * 单价
     */
    public Double getFdPrice() {
        return this.fdPrice;
    }

    /**
     * 单价
     */
    public void setFdPrice(Double fdPrice) {
        this.fdPrice = fdPrice;
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

    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
