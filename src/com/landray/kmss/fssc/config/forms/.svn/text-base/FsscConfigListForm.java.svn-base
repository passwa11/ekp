package com.landray.kmss.fssc.config.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.fssc.config.model.FsscConfigList;

/**
  * 物资清单
  */
public class FsscConfigListForm extends ExtendForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docCreateTime;

    private String fdName;

    private String fdCode;

    private String fdGoodsType;

    private String fdGoodsProperty;

    private String fdMinNum;

    private String fdSpec;

    private String fdUnit;

    private String fdPrice;

    private String docCreatorId;

    private String docCreatorName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        docCreateTime = null;
        fdName = null;
        fdCode = null;
        fdGoodsType = null;
        fdGoodsProperty = null;
        fdMinNum = null;
        fdSpec = null;
        fdUnit = null;
        fdPrice = null;
        docCreatorId = null;
        docCreatorName = null;
        super.reset(mapping, request);
    }

    public Class<FsscConfigList> getModelClass() {
        return FsscConfigList.class;
    }

    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
        }
        return toModelPropertyMap;
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
    public String getFdMinNum() {
        return this.fdMinNum;
    }

    /**
     * 最小起订量
     */
    public void setFdMinNum(String fdMinNum) {
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
    public String getFdPrice() {
        return this.fdPrice;
    }

    /**
     * 单价
     */
    public void setFdPrice(String fdPrice) {
        this.fdPrice = fdPrice;
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

    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
    
    private FormFile formFile;

   	public FormFile getFormFile() {
   		return formFile;
   	}

   	public void setFormFile(FormFile formFile) {
   		this.formFile = formFile;
   	}
}
