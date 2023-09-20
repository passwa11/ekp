package com.landray.kmss.eop.basedata.forms;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.eop.basedata.model.EopBasedataMateCate;
import com.landray.kmss.eop.basedata.model.EopBasedataMateUnit;
import com.landray.kmss.eop.basedata.model.EopBasedataMaterial;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

import javax.servlet.http.HttpServletRequest;

/**
  * 物料
  */
public class EopBasedataMaterialForm extends ExtendAuthTmpForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String docCreateTime;

    private String fdSpecs;

    private String fdStatus;

    private String fdPrice;

    private String fdRemarks;

    private String docCreatorId;

    private String docCreatorName;

    private String fdUnitId;

    private String fdUnitName;

    private String fdCode;

    private String fdTypeId;

    private String fdTypeName;

    private String fdErpCode;
    private String fdParentId;
    private String fdParentName;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdName = null;
        docCreateTime = null;
        fdSpecs = null;
        fdStatus = null;
        fdPrice = null;
        fdRemarks = null;
        docCreatorId = null;
        docCreatorName = null;
        fdUnitId = null;
        fdUnitName = null;
        fdCode = null;
        fdTypeId = null;
        fdTypeName = null;
        fdErpCode = null;
        fdParentId = null;
        fdParentName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<EopBasedataMaterial> getModelClass() {
        return EopBasedataMaterial.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdUnitId", new FormConvertor_IDToModel("fdUnit", EopBasedataMateUnit.class));
            toModelPropertyMap.put("fdTypeId", new FormConvertor_IDToModel("fdType", EopBasedataMateCate.class));
            toModelPropertyMap.put("fdParentId", new FormConvertor_IDToModel("fdParent", EopBasedataMateCate.class));
        }
        return toModelPropertyMap;
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
     * 最近更新时间
     */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 最近更新时间
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
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
    public String getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 状态
     */
    public void setFdStatus(String fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 参考价
     */
    public String getFdPrice() {
        return this.fdPrice;
    }

    /**
     * 参考价
     */
    public void setFdPrice(String fdPrice) {
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
     * 最近更新人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 最近更新人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 最近更新人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 最近更新人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

    /**
     * 单位
     */
    public String getFdUnitId() {
        return this.fdUnitId;
    }

    /**
     * 单位
     */
    public void setFdUnitId(String fdUnitId) {
        this.fdUnitId = fdUnitId;
    }

    /**
     * 单位
     */
    public String getFdUnitName() {
        return this.fdUnitName;
    }

    /**
     * 单位
     */
    public void setFdUnitName(String fdUnitName) {
        this.fdUnitName = fdUnitName;
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
    public String getFdTypeId() {
        return this.fdTypeId;
    }

    /**
     * 物料类别
     */
    public void setFdTypeId(String fdTypeId) {
        this.fdTypeId = fdTypeId;
    }

    /**
     * 物料类别
     */
    public String getFdTypeName() {
        return this.fdTypeName;
    }

    /**
     * 物料类别
     */
    public void setFdTypeName(String fdTypeName) {
        this.fdTypeName = fdTypeName;
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
     * 导入专用
     */
    protected FormFile file = null;

    // 文件上传
    public FormFile getFile() {
        return this.file;
    }

    public void setFile(FormFile file) {
        this.file = file;
    }
	public String getFdParentId() {
		return fdParentId;
	}
	public void setFdParentId(String fdParentId) {
		this.fdParentId = fdParentId;
	}
	public String getFdParentName() {
		return fdParentName;
	}
	public void setFdParentName(String fdParentName) {
		this.fdParentName = fdParentName;
	}
}
