package com.landray.kmss.eop.basedata.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.IBaseTreeModel;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.eop.basedata.forms.EopBasedataGoodForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.right.interfaces.ExtendAuthTmpModel;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ObjectUtil;

import java.util.Date;

/**
  * 商品明细
  */
public class EopBasedataGood extends ExtendAuthTmpModel implements IBaseTreeModel, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private Integer fdOrder;

    protected String fdHierarchyId = BaseTreeConstant.HIERARCHY_ID_SPLIT + getFdId() + BaseTreeConstant.HIERARCHY_ID_SPLIT;

    private String fdCode;

    private String fdDesc;

    private String fdWithTaxFlag;

    private Double fdTaxRate;

    private Date docAlterTime;

    private IBaseTreeModel fdParent;

    private SysOrgPerson docAlteror;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<EopBasedataGoodForm> getFormClass() {
        return EopBasedataGoodForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.addNoConvertProperty("fdHierarchyId");
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdParent.fdName", "fdParentName");
            toFormPropertyMap.put("fdParent.fdId", "fdParentId");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
            toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
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
     * 层级ID
     */
    @Override
    public String getFdHierarchyId() {
        return this.fdHierarchyId;
    }

    /**
     * 层级ID
     */
    @Override
    public void setFdHierarchyId(String fdHierarchyId) {
        this.fdHierarchyId = fdHierarchyId;
    }

    /**
     * 编码
     */
    public String getFdCode() {
        return this.fdCode;
    }

    /**
     * 编码
     */
    public void setFdCode(String fdCode) {
        this.fdCode = fdCode;
    }

    /**
     * 说明
     */
    public String getFdDesc() {
        return this.fdDesc;
    }

    /**
     * 说明
     */
    public void setFdDesc(String fdDesc) {
        this.fdDesc = fdDesc;
    }

    /**
     * 单价含税标志
     */
    public String getFdWithTaxFlag() {
        return this.fdWithTaxFlag;
    }

    /**
     * 单价含税标志
     */
    public void setFdWithTaxFlag(String fdWithTaxFlag) {
        this.fdWithTaxFlag = fdWithTaxFlag;
    }

    /**
     * 税率
     */
    public Double getFdTaxRate() {
        return this.fdTaxRate;
    }

    /**
     * 税率
     */
    public void setFdTaxRate(Double fdTaxRate) {
        this.fdTaxRate = fdTaxRate;
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

    /**
     * 父节点
     */
    @Override
    public IBaseTreeModel getFdParent() {
        return fdParent;
    }

    /**
     * 父节点
     */
    public void setFdParent(IBaseTreeModel parent) {
        IBaseTreeModel oldParent = getHbmParent();
        if (!ObjectUtil.equals(oldParent, parent)) {
            ModelUtil.checkTreeCycle(this, parent, "fdParent");
            setHbmParent(parent);
        }
    }

    /**
     * 父节点
     */
    public IBaseTreeModel getHbmParent() {
        return fdParent;
    }

    /**
     * 父节点
     */
    public void setHbmParent(IBaseTreeModel parent) {
        this.fdParent = parent;
    }

    /**
     * 修改人
     */
    public SysOrgPerson getDocAlteror() {
        return this.docAlteror;
    }

    /**
     * 修改人
     */
    public void setDocAlteror(SysOrgPerson docAlteror) {
        this.docAlteror = docAlteror;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
